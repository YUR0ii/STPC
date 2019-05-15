package sf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Menu extends JFrame
{
	public final static int scale = 3;
	public final static int menu_fps = 20;
	public final static int width = 12 * 32 * scale;
	public final static int height = 7 * 32 * scale;

	private enum State {TITLE, CHAR_SELECT, STAGE_SELECT, IN_GAME};
	private State gs;

	private final int p1confirmkey = KeyEvent.VK_R;
	private final int p2confirmkey = KeyEvent.VK_NUMPAD7;
	private final int startkey = KeyEvent.VK_ENTER;

	private int p1sel;
	private int p2sel;
	private boolean ai;
	private int ssel;

	private boolean p1confirmed;
	private boolean p2confirmed;

	private long start;
	private BufferedImage[] images;

	private static Map<Integer, Class> character_map = new HashMap<>();
	private static Map<Integer, Class> stage_map = new HashMap<>();

	static
	{
		character_map.put(1, sf.chars.Ryu.class);
		character_map.put(2, sf.chars.EHonda.class);
		character_map.put(3, sf.chars.Blanka.class);
		character_map.put(4, sf.chars.Guile.class);

		character_map.put(7, sf.chars.Balrog.class);
		character_map.put(8, sf.chars.Ken.class);
		character_map.put(9, sf.chars.ChunLi.class);
		character_map.put(10, sf.chars.Zangief.class);
		character_map.put(11, sf.chars.Dhalsim.class);
		character_map.put(12, sf.chars.Sagat.class);

		character_map.put(14, sf.chars.Vega.class);
		character_map.put(15, sf.chars.THawk.class);
		character_map.put(16, sf.chars.FeiLong.class);
		character_map.put(17, sf.chars.DeeJay.class);
		character_map.put(18, sf.chars.Cammy.class);
		character_map.put(19, sf.chars.MBison.class);

		stage_map.put(0, sf.stages.RyuStage.class);
		// TODO: e honda stage
		stage_map.put(1, sf.stages.BlankaStage.class);
		stage_map.put(2, sf.stages.KenStage.class);
		stage_map.put(3, sf.stages.GuileStage.class);
		stage_map.put(4, sf.stages.BalrogStage.class);
		stage_map.put(5, sf.stages.ChunLiStage.class);
		stage_map.put(6, sf.stages.ZangiefStage.class);
		stage_map.put(7, sf.stages.DhalsimStage.class);
		stage_map.put(8, sf.stages.MBisonStage.class);
		stage_map.put(9, sf.stages.SagatStage.class);
		stage_map.put(10, sf.stages.VegaStage.class);
		stage_map.put(11, sf.stages.THawkStage.class);
		stage_map.put(12, sf.stages.FeiLongStage.class);
		stage_map.put(13, sf.stages.DeeJayStage.class);
		stage_map.put(14, sf.stages.CammyStage.class);
	}

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
			int frame = (int) (menu_fps * (System.nanoTime() - start) / 1_000_000_000);
			this.setBackground(new Color(0, 0, 80));
			g.drawString(Integer.toString(frame % menu_fps), 7, 15);

			// TODO: don't reload images every frame (not much practical difference but it's bad practice)

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
					// TODO: character names (and possibly country flags)
					// TODO: large display on the right side should be flipped

					images = new BufferedImage[6];
					images[0] = ImageIO.read(new File("img/charselect.png"));

					images[1] = ImageIO.read(new File("img/selection_boxes/" +
						(p1confirmed ? (frame / 2 % 2 == 0 ? "p1f1.png" : "p1f2.png") : "p1.png")));
					images[2] = ImageIO.read(new File("img/selection_boxes/" +
						(p2confirmed ? (frame / 2 % 2 == 0 ? "p2f1.png" : "p2f2.png") : "p2.png")));

					images[3] = ImageIO.read(new File("img/char_sprites/c" + p1sel + ".png"));
					images[4] = ImageIO.read(new File("img/char_sprites/c" + p2sel + ".png"));

					images[5] = ImageIO.read(new File("img/ai.png"));

					double[] scaling_factor = {1, 1, 1, 1.5, 1.5, 1};
					Image[] scaled_images = new Image[6];

					// upscale all of the images
					for (int i = 0; i < 6; i++)
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

					// ai
					if (ai)
					{
						g.drawImage(scaled_images[5], scale * 305, scale * 135, null);
					}
				}
				else if (gs == State.STAGE_SELECT)
				{
					// TODO: England sprite has a very small inaccuracy
					// TODO: fix Japan and Brazil sprites

					images = new BufferedImage[16];
					images[0] = ImageIO.read(new File("img/stageselect.png"));

					for (int i = 0; i < 15; i++)
					{
						images[i+1] = ImageIO.read(new File("img/stage_sprites/s" + i + (ssel == i ? "f" : "") + ".png"));
					}

					Image[] scaled_images = new Image[16];

					// upscale all of the images
					for (int i = 0; i < 16; i++)
					{
						scaled_images[i] = images[i].getScaledInstance(
							images[i].getWidth() * scale,
							images[i].getHeight() * scale,
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

					// top row of stages
					int wpos = 50 * scale;
					for (int i = 1; i < 9; i++)
					{
						g.drawImage(scaled_images[i], wpos, height / 2 - scale * 5, null);
						wpos += 15 * scale + scaled_images[i].getWidth(null);
					}

					// bottom row of stages
					wpos = 50 * scale;
					for (int i = 9; i < 16; i++)
					{
						g.drawImage(scaled_images[i], wpos, height / 2 + scale * 30, null);
						wpos += 15 * scale + scaled_images[i].getWidth(null);
					}
				}
				else if (gs == State.IN_GAME)
				{
					try {
						Class<Character> p1 = character_map.get(p1sel);
						Class<Character> p2 = character_map.get(p2sel);
						Class<Stage> stage = stage_map.get(ssel);
						new Game(p1.newInstance(), p2.newInstance(), stage.newInstance(), !ai);

					} catch (NullPointerException e) {
						System.err.println("Invalid selection");
					}

					newgame();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	// brings the player to the character selection screen
	private void newgame()
	{
		gs = State.CHAR_SELECT;

		p1sel = 1;
		p2sel = 4;
		ai = false;
		ssel = 0;

		p1confirmed = false;
		p2confirmed = false;

		Menu.this.repaint();
	}

	private class STPCKeyListener implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (gs == State.TITLE)
			{
				newgame();
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

				case KeyEvent.VK_NUMPAD8: ai = !ai;	break;

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

				if (character_map.containsKey(p1sel + p1inc)) {
					p1sel += p1inc;
				}

				if (character_map.containsKey(p2sel + p2inc)) {
					p2sel += p2inc;
				}

				Menu.this.repaint();
			}
			else if (gs == State.STAGE_SELECT)
			{
				int sinc = 0;

				switch (e.getKeyCode())
				{
				case KeyEvent.VK_A:		sinc = -1;	break;
				case KeyEvent.VK_D:		sinc = 1;	break;
				case KeyEvent.VK_LEFT:	sinc = -1;	break;
				case KeyEvent.VK_RIGHT:	sinc = 1;	break;

				case startkey:
					gs = State.IN_GAME;
					break;
				}

				if (stage_map.containsKey(ssel + sinc)) {
					ssel += sinc;
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
			while (System.nanoTime() - st < 1_000_000_000 / menu_fps);
			m.repaint();
		}
	}
}
