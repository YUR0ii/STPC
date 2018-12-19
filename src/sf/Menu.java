package sf;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Menu extends JFrame
{
	static charSelect css;
	Menu()
	{
//		this.add(new charSelect(new charBox[] {new charBox(new Ryu()),new charBox(new ChunLi())}));
		
		this.setSize(400,300);
		this.setResizable(false);
		this.setVisible(true);
		
//		Timer t = new Timer();
		
	}
	
	class selectBox extends Rectangle
	{
		int selected = 0;
		selectBox()
		{
		}
	}
	
	class charBox extends Rectangle
	{
		BufferedImage icon;

		charBox(Character c) {
			this.setSize(100, 100);
			this.icon = c.selectIcon;
		}
	}

	class charSelect extends JPanel
	{
		charBox[] characters;
		
		charSelect(charBox[] characters)
		{
			this.characters = characters;
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			
			
			charBox current;
			for(int i = 0; i < characters.length; i++)
			{
				current = characters[i];
				current.setLocation(i*100, 200);
				g2.setColor(new Color(255/(i+1), 0, 0));
				g2.fill(characters[i]);
				g2.drawImage(current.icon, current.x, current.y, 100, 100, rootPane);
			}
		}
	}
	
	static void Update()
	{
		css.repaint();
	}
	
	public static void main(String[] args)
	{
//		new Menu();
	}
}
