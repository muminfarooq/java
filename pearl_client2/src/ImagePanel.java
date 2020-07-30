
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;




/*******panel which will draw a image**********/
public class ImagePanel extends JPanel implements CommonSettings{
    Chat_Client Parent;
    Image DisplayImage;
    
      ImagePanel(Chat_Client client,Image image)
      {
         setLayout(null);
         Parent=client;
         DisplayImage=image;
         int XPos=image.getWidth(this);
         int YPos =image.getHeight(this);
         setBounds(0,0,XPos+TOP_PANEL_START_POS,YPos+TOP_PANEL_START_POS);
  
      }
      public void paint(Graphics graphics)
      {
        graphics.drawImage(DisplayImage,TOP_PANEL_START_POS,TOP_PANEL_START_POS ,this);
        
      
      }
}
