
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin
 */
class TapPanel extends JPanel implements CommonSettings,ActionListener{
  Chat_Client chatclient;
  protected JTextField TxtUserCount;
  ScrollView ImageScrollView,UserScrollView,RoomScrollView;
  protected ImageCanvas imagecanvas;
  protected ListViewCanvas UserCanvas,ListCanvas,RoomCanvas;
  JButton  CmdChangeRoom,CmdIgnoreUser,CmdSendDirect;
                TapPanel(Chat_Client parent)
        {
          chatclient=parent;
          JPanel tappanel=new JPanel(new BorderLayout());
          CardLayout card =new CardLayout();
          JPanel MainPanel=new JPanel(card);
          
          JPanel UserPanel= new JPanel(new BorderLayout());
          UserCanvas=new ListViewCanvas(chatclient,USER_CANVAS);
          UserScrollView =new ScrollView(UserCanvas,true,true,TAPPANEL_CANVAS_WIDTH,TAPPANEL_CANVAS_HEIGHT,SCROLL_BAR_SIZE);
            UserCanvas.scrollview=UserScrollView();
            UserPanel.add("Center",UserScrollView);
            JPanel   UserButtonPanel = new JPanel(new BorderLayout());
	  CmdSendDirect = new CustomButton(chatclient,"Send Direct Message");
	  CmdSendDirect.addActionListener(this);
	  UserButtonPanel.add("North",CmdSendDirect);
	  CmdIgnoreUser = new CustomButton(chatclient,"Ignore User");
	  CmdIgnoreUser.addActionListener(this);
	  UserButtonPanel.add("Center",CmdIgnoreUser);
	  UserPanel.add("South",UserButtonPanel);
	  
	  /********Room Panel Coding Starts***********/
	 JPanel RoomPanel = new JPanel(new BorderLayout());
	  RoomCanvas = new ListViewCanvas(chatclient,ROOM_CANVAS);
	  
	  RoomScrollView = new ScrollView(RoomCanvas,true,true,TAPPANEL_CANVAS_WIDTH,TAPPANEL_CANVAS_HEIGHT,SCROLL_BAR_SIZE);
	  RoomCanvas.scrollview = RoomScrollView;	  
	  RoomPanel.add("Center",RoomScrollView);	  
	  
	 JPanel  RoomButtonPanel = new JPanel(new BorderLayout());
	  JPanel RoomCountPanel = new JPanel(new BorderLayout());
	  JLabel LblCaption = new JLabel("ROOM COUNT",1);
	  RoomCountPanel.add("North",LblCaption);
	  TxtUserCount = new JTextField();
	  TxtUserCount.setEditable(false);
	  RoomCountPanel.add("Center",TxtUserCount);	  	  
	  RoomButtonPanel.add("Center",RoomCountPanel);
	  
	  CmdChangeRoom = new CustomButton(chatclient,"Change Room");
	  CmdChangeRoom.addActionListener(this);
	  RoomButtonPanel.add("South",CmdChangeRoom);
	  
	  RoomPanel.add("South",RoomButtonPanel);
	  
	  
	  /********Image Panel Coding Starts***********/
	  JPanel ImagePanel = new JPanel(new BorderLayout());
	  
	  imagecanvas = new ImageCanvas(chatclient);
	  ImageScrollView = new ScrollView(imagecanvas,true,true,TAPPANEL_CANVAS_WIDTH,TAPPANEL_CANVAS_HEIGHT,SCROLL_BAR_SIZE);
	  imagecanvas.scrollview = ImageScrollView;
	  /**********Add Icons into MessageObject *********/
	  imagecanvas.AddIconsToMessageObject();
	  ImagePanel.add("Center",ImageScrollView);
	  
	  /*********Add All the Panel in to Main Panel*********/
	  MainPanel.add("UserPanel",UserPanel);
	  MainPanel.add("RoomPanel",RoomPanel);
	  MainPanel.add("ImagePanel",ImagePanel);
	  card.show(MainPanel,"UserPanel");
	  BorderPanel borderpanel = new BorderPanel(this,chatclient,card,MainPanel,TAPPANEL_WIDTH,TAPPANEL_HEIGHT);
	  
	  borderpanel.addTab("USERS","UserPanel");
	  borderpanel.addTab("ROOMS","RoomPanel");
	  borderpanel.addTab("EMOTICONS","ImagePanel");
	  
	  tappanel.add(borderpanel);
	  add("Center",tappanel);	  		  
	  
	  
	  /********Common Things***********/	  	    	  
	}
	
	/***********Action Listener coding **********/
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource().equals(CmdChangeRoom))
		{
			/******** Change Room Coding *********/
			chatclient.ChangeRoom();	
		}
		
		if(evt.getSource().equals(CmdIgnoreUser))
		{			
			if(evt.getActionCommand().equals("Ignore User"))
			{
				UserCanvas.IgnoreUser(true);				
			}
			else
			{
				UserCanvas.IgnoreUser(false);					
			}
		}
		
		if(evt.getSource().equals(CmdSendDirect))
		{
			UserCanvas.SendDirectMessage();	
		}					
	}
	

       
  
    
}
