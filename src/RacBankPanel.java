import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;


public class RacBankPanel extends JPanel
{
	int top,left,bottom,right;
	
	RacBankPanel(int top,int left,int bottom,int right) 
	{
		// TODO Auto-generated constructor stub
		this.top=top;
		this.left=left;
		this.bottom=bottom;
		this.right=right;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(new Color(80,80,120));
	}
	
	public Insets getInsets()
	{
		return new Insets(top, left, bottom, right);
	}
	

}
