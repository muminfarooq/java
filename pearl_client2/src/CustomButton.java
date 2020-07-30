
import java.awt.Button;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin
 */

   class CustomButton extends JButton
{
	public CustomButton(Chat_Client Parent, String label)
	{
		chatclient = Parent;
		setLabel(label);
		setBackground(chatclient.colormap[3]);
	    setForeground(chatclient.colormap[2]);		
	}
Chat_Client chatclient;
}
    

