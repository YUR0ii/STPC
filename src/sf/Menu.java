package sf;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame
{
	Menu()
	{
		this.add(new charSelect(new charBox[] {new charBox(new Ryu()),new charBox(new Ryu())}));
		
		this.setSize(500,500);
		this.setVisible(true);
	}
	
	class charBox extends Rectangle
	{
		charBox(Character c)
		{
			this.setSize(100, 100);
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
			
			for(int i = 0; i < characters.length; i++)
			{
				characters[i].setLocation(i * 100, 0);
				g2.setColor(new Color(255/(i+1), 0, 0));
				g2.fill(characters[i]);
			}
		}
	}
	
	public static void main(String[] args)
	{
//		new Menu();
		new Game(new Ryu(), new Ryu());
	}
}
