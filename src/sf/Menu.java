package sf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame
{
	public final static int scale = 3;
	public final static int fps = 20;
	public final static int width = 12 * 32 * scale;
	public final static int height = 7 * 32 * scale;

	private enum State {TITLE, CHAR_SELECT, STAGE_SELECT, IN_GAME};
	private State gs;

	private final int p1confirmkey = KeyEvent.VK_R;
	private final int p2confirmkey = KeyEvent.VK_NUMPAD7;
	private final int startkey = KeyEvent.VK_ENTER;

	private int p1sel;
	private int p2sel;
	private boolean p1confirmed;
	private boolean p2confirmed;
	private boolean stageconfirmed;

	private long start;
	private BufferedImage[] images;

	public Menu()
	{
		gs = State.TITLE;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new STPCPanel();
		this.add(panel);
		this.addKeyListener(new STPCKeyListener());

		this.setSize(width, height);
		this.setResizable(false);
		this.setTitle("STPC");
		this.setVisible(true);

		start = System.nanoTime();
	}

	private class STPCPanel extends JPanel
	{
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int frame = (int) (fps * (System.nanoTime() - start) / 1_000_000_000);
			this.setBackground(new Color(0, 0, 80));
			g.drawString(Integer.toString(frame % fps), 7, 15);

			try {

				if (gs == State.TITLE)
				{
					BufferedImage img = ImageIO.read(new File("img/titlescreen.png"));

					Image scaled_img = img.getScaledInstance(
						img.getWidth() * scale,
						img.getHeight() * scale,
						0
					);

					g.drawImage(
						scaled_img,
						(this.getWidth() - img.getWidth() * scale) / 2,
						(this.getHeight() - img.getHeight() * scale) / 2,
						null
					);
				}
				else if (gs == State.CHAR_SELECT)
				{
					// TODO: character names
					// TODO: don't reload images every frame (not much practical difference but its bad practice)

					images = new BufferedImage[5];
					images[0] = ImageIO.read(new File("img/charselect.png"));

					images[1] = ImageIO.read(new File("img/selection_boxes/" +
						(p1confirmed ? (frame / 2 % 2 == 0 ? "p1f1.png" : "p1f2.png") : "p1.png")));
					images[2] = ImageIO.read(new File("img/selection_boxes/" +
						(p2confirmed ? (frame / 2 % 2 == 0 ? "p2f1.png" : "p2f2.png") : "p2.png")));

					images[3] = ImageIO.read(new File("img/sprites/c" + p1sel + ".png"));
					images[4] = ImageIO.read(new File("img/sprites/c" + p2sel + ".png"));

					double[] scaling_factor = {1, 1, 1, 1.5, 1.5};
					Image[] scaled_images = new Image[5];

					// upscale all of the images
					for (int i = 0; i < 5; i++)
					{
						scaled_images[i] = images[i].getScaledInstance(
							(int) (images[i].getWidth() * scale * scaling_factor[i]),
							(int) (images[i].getHeight() * scale * scaling_factor[i]),
							0
						);
					}

					int halign = (this.getHeight() - images[0].getHeight() * scale) / 10;

					// character list
					g.drawImage(
						scaled_images[0],
						(this.getWidth() - images[0].getWidth() * scale) / 2,
						halign,
						null
					);

					int startx = (this.getWidth() - images[0].getWidth() * scale) / 2;
					int p1starty = (this.getWidth() - images[0].getWidth() * scale) / 32;
					int p2starty = p1starty + 4 * scale;
					int incx = 21 * scale;
					int incy = 32 * scale;

					// selection boxes
					g.drawImage(scaled_images[2], startx + incx * (p2sel % 7), p2starty + incy * (p2sel / 7), null);
					g.drawImage(scaled_images[1], startx + incx * (p1sel % 7), p1starty + incy * (p1sel / 7), null);

					// large display
					g.drawImage(scaled_images[3], scale * (0 + 20), halign, null);
					g.drawImage(scaled_images[4], scale * (300 - 20), halign, null);
				}
				else if (gs == State.STAGE_SELECT)
				{
					images = new BufferedImage[2];
					images[0] = ImageIO.read(new File("img/stageselect.png"));

					// TODO: lots more to do to get more stages

					images[1] = ImageIO.read(new File("img/stages/s0" + (stageconfirmed ? "f" : "") + ".png"));

					double[] scaling_factor = {1, 1};
					Image[] scaled_images = new Image[2];

					// upscale all of the images
					for (int i = 0; i < 2; i++)
					{
						scaled_images[i] = images[i].getScaledInstance(
							(int) (images[i].getWidth() * scale * scaling_factor[i]),
							(int) (images[i].getHeight() * scale * scaling_factor[i]),
							0
						);
					}

					// text
					g.drawImage(
						scaled_images[0],
						(this.getWidth() - images[0].getWidth() * scale) / 2,
						(this.getWidth() - images[0].getWidth() * scale) / 4,
						null
					);

					// stages
					g.drawImage(scaled_images[1], width / 2 - 12 * scale, height / 2 - scale * 5, null);
				}
				else if (gs == State.IN_GAME)
				{
					// "working on it" -ian
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class STPCKeyListener implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (gs == State.TITLE)
			{
				gs = State.CHAR_SELECT;
				p1sel = 1;
				p2sel = 4;
				p1confirmed = false;
				p2confirmed = false;
				Menu.this.repaint();
			}
			else if (gs == State.CHAR_SELECT)
			{
				int p1inc = 0;
				int p2inc = 0;

				switch (e.getKeyCode())
				{
				case KeyEvent.VK_W:		p1inc = -7;	break;
				case KeyEvent.VK_S:		p1inc = 7;	break;
				case KeyEvent.VK_A:		p1inc = -1;	break;
				case KeyEvent.VK_D:		p1inc = 1;	break;
				case KeyEvent.VK_UP:	p2inc = -7;	break;
				case KeyEvent.VK_DOWN:	p2inc = 7;	break;
				case KeyEvent.VK_LEFT:	p2inc = -1;	break;
				case KeyEvent.VK_RIGHT:	p2inc = 1;	break;

				case p1confirmkey:
					p1confirmed = !p1confirmed;
					break;

				case p2confirmkey:
					p2confirmed = !p2confirmed;
					break;

				case startkey:
					if (p1confirmed && p2confirmed) {
						gs = State.STAGE_SELECT;
					}

					break;
				}

				int p1selbak = p1sel;
				int p2selbak = p2sel;
				if (!p1confirmed) p1sel += p1inc;
				if (!p2confirmed) p2sel += p2inc;

				if (p1sel % 7 == 6 || p1sel > 20 || p1sel < 1 || p1sel == 5) {
					p1sel = p1selbak;
				}

				if (p2sel % 7 == 6 || p2sel > 20 || p2sel < 1 || p2sel == 5) {
					p2sel = p2selbak;
				}

				Menu.this.repaint();
			}
			else if (gs == State.STAGE_SELECT)
			{
				switch (e.getKeyCode())
				{
					case p1confirmkey:
					case p2confirmkey:
						stageconfirmed = !stageconfirmed;
						break;

					case startkey:
						if (stageconfirmed) {
							gs = State.IN_GAME;
						}

						break;
				}

				Menu.this.repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) { }

		@Override
		public void keyTyped(KeyEvent arg0) { }
	}

	public static void main(String[] args)
	{
		Menu m = new Menu();
		while (true)
		{
			long st = System.nanoTime();
			while (System.nanoTime() - st < 1_000_000_000 / fps);
			m.repaint();
		}
	}
}

