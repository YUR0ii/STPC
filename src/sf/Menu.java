package sf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Menu extends JFrame
{
	public final int scale = 3;

	private enum State {TITLE, CHAR_SELECT, STAGE_SELECT, IN_GAME};
	private State gs;

	// row + 6 * col
	private int p1sel;
	private int p2sel;

	public Menu()
	{
		gs = State.TITLE;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new STPCPanel();
		this.add(panel);
		this.addKeyListener(new STPCKeyListener());

		this.setSize(12 * 32 * scale, 7 * 32 * scale);
		this.setResizable(false);
		this.setTitle("STPC");
		this.setVisible(true);
	}

	private class STPCPanel extends JPanel
	{
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			this.setBackground(new Color(0, 0, 80));

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
					BufferedImage[] images = new BufferedImage[2];
					images[0] = ImageIO.read(new File("img/charselect.png"));
					images[1] = ImageIO.read(new File("img/selection_boxes/p1.png"));

					Image[] scaled_images = Arrays.stream(images).map(img ->
						img.getScaledInstance(
							img.getWidth() * scale,
							img.getHeight() * scale,
							0
						)
					).toArray(Image[]::new);

					g.drawImage(
						scaled_images[0],
						(this.getWidth() - images[0].getWidth() * scale) / 2,
						(this.getHeight() - images[0].getHeight() * scale) / 10,
						null
					);

					int originx = 0;
					int originy = 0;
					int incx = 20 * scale;
					int incy = 30 * scale;

					g.drawImage(scaled_images[1], originx + incy * (p1sel % 6), originy + incy * (p1sel / 6), null);
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
				Menu.this.repaint();
				p1sel = 1;
				p2sel = 4;
			}
			else if (gs == State.CHAR_SELECT)
			{
				Menu.this.repaint();
				switch (e.getKeyCode())
				{
				// TODO: verify that the new selection is valid
				case KeyEvent.VK_W:
					p1sel -= 6;
					break;
				case KeyEvent.VK_S:
					p1sel += 6;
					break;
				case KeyEvent.VK_A:
					p1sel -= 1;
					break;
				case KeyEvent.VK_D:
					p1sel += 1;
					break;
				case KeyEvent.VK_UP:
					p2sel -= 6;
					break;
				case KeyEvent.VK_DOWN:
					p2sel += 6;
					break;
				case KeyEvent.VK_LEFT:
					p2sel -= 1;
					break;
				case KeyEvent.VK_RIGHT:
					p2sel += 1;
					break;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) { }

		@Override
		public void keyTyped(KeyEvent arg0) { }
	}

	public static void main(String[] args)
	{
		new Menu();
	}
}
