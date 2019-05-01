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

public class Menu extends JFrame
{
	public final int scale = 3;

	private enum State {TITLE, CHAR_SELECT, STAGE_SELECT, IN_GAME};
	private State gs;



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
					BufferedImage img = ImageIO.read(new File("img/charselect.png"));

					Image scaled_img = img.getScaledInstance(
						img.getWidth() * scale,
						img.getHeight() * scale,
						0
					);

					g.drawImage(
						scaled_img,
						(this.getWidth() - img.getWidth() * scale) / 2,
						(this.getHeight() - img.getHeight() * scale) / 10,
						null
					);
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
		public void keyPressed(KeyEvent arg0)
		{
			if (gs == State.TITLE)
			{
				gs = State.CHAR_SELECT;
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
		new Menu();
	}
}
