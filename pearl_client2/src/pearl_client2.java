


import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

  public class pearl_client2 
 {

  
    public static void main(String[] args) {
        Chat_Client client =new Chat_Client();
        
    }
    
 
}
class Chat_Client extends JFrame implements CommonSettings ,Runnable,Serializable,KeyListener
{ 
 String UserName, UserRoom,ServerName,ChatLogo,BannerName,ProxyHost,ServerData,SplitString;
 StringBuffer stringbuffer;
 StringTokenizer Tokenizer;
 
 boolean StartFlag,IsProxy;
 int ServerPort,ProxyPort,IconCount,TotalUserCount,ILoop,G_ILoop;
 Image ImgLogo,ImgBanner;
 Image [] iconarray;
 Color[] colormap;
 JLabel InfoLabel;
 JTextField TxtMessage;
 Toolkit toolkit;
 Dimension dimension;
 JMenuItem loginitem,disconnectionitem,seperatoritem,quititem,aboutitem;
 MediaTracker tracker;
 Font textfont;
 Socket socket;
 MessageCanvas messagecanvas;
 InformationDialog dialog;
 Thread thread;
 DataInputStream datainputstream;
 DataOutputStream dataoutputstream;
 JButton CmdSend,CmdExit;
 TapPanel tappanel;
 protected int PrivateWindowCount;
 protected PrivateChat[] privatewindow;
    Chat_Client()
    {
       
      toolkit= Toolkit.getDefaultToolkit();
      if(toolkit.getScreenSize().getWidth()>1600)
         {
            
                setSize(778,575);
         }
        else
       {
         setSize((int)toolkit.getScreenSize().getWidth(),(int)toolkit.getScreenSize().getHeight());
         setResizable(false);
         dimension=getSize();
         setLayout(new BorderLayout());
         setTitle("");
         addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent evt)
            {
           //  DisconnectChat();
              System.exit(0);
            }});
             JMenuBar menubar=new JMenuBar();
               JMenu loginmenu= new JMenu("Login");
               JMenu aboutmenu= new JMenu("Help");
             
             loginitem =new JMenuItem("login");
            // loginitem.addActionListener(this);
             disconnectionitem=new JMenuItem("Disconnect");
             seperatoritem=new JMenuItem("--");
             quititem=new JMenuItem("quit");
           
             loginmenu.add(loginitem);
             loginmenu.add(disconnectionitem);
             loginmenu.add(seperatoritem);
             loginmenu.add(quititem);
            
          
            aboutitem=new JMenuItem("about");
            aboutmenu.add(aboutitem);
            menubar.add(loginmenu);
            menubar.add(aboutmenu);
            setJMenuBar(menubar);
            setVisible(true);
            /********* setting parameters*/
            UserName="";
            UserRoom="";
            IconCount=21;
            ChatLogo="images/logo.gif";
            BannerName="images/logo.gif1";
            IsProxy=false;
            /*******Assigning Global Colors*******************/
           
            colormap=new Color[MAX_COLOR];
            //Background 
            colormap[0]=new Color(224,236,254);
            //info color background
            colormap[1]=new Color(255,153,0);
             //button foreground
            colormap[2]=Color.black;
              //button background
            colormap[3]=new Color(224,236,254);
               //button
            colormap[4]=new Color(255,153,0);
                //message canvas
            colormap[5]=Color.black;
                 //top panel backgroiund
            colormap[6]=Color.YELLOW;
                  //label text color
            colormap[7]=Color.WHITE;
            /********LOADING IMAGES*********/
            tracker=new MediaTracker(this);
            int imagecount=0;
            
           ImgLogo= toolkit.getImage(ChatLogo);
            imagecount++;
            tracker.addImage(ImgLogo, imagecount);
            
             ImgBanner=toolkit.getImage(BannerName);
              tracker.addImage(ImgBanner, imagecount);
              imagecount++;
              /********************/
              
              /*******************LOADING ICONS***********************/
              iconarray=new Image[IconCount];
              for(ILoop=0;ILoop<IconCount;ILoop++)
              {
                iconarray[ILoop]=toolkit.getImage("Icons/pic"+ILoop+".gif");
                tracker.addImage(iconarray [ILoop],imagecount);
                imagecount++;
              }
          try {
              /***********************************************/
              /************************************/
              //pending code...
              /**********************************/
              initializeDesign();
          } catch (IOException ex) {
              Logger.getLogger(Chat_Client.class.getName()).log(Level.SEVERE, null, ex);
          }
       }
    }
    public void initializeDesign() throws IOException
    {
      setBackground(colormap[0]);
      Font font=new Font("Dialog",Font.BOLD,14);
      textfont =new Font("Dialog",0,14);
     setFont(font);
     /*********************TOp panel coding***************************/
     JPanel toppanel=new JPanel(new BorderLayout());
     toppanel.setBackground(colormap[6]);
     
             
     JPanel logopanel=new ImagePanel(this,ImgLogo); 
     toppanel.add("East",logopanel);
     
     JPanel bannerpanel=new ImagePanel(this,ImgBanner); 
     toppanel.add("West",bannerpanel);
     
     add("North",toppanel);
     /******************INFORMATION LABEL PANEL CODING**************************/
      JPanel centralpanel=new JPanel(new BorderLayout());
      JPanel infopanel=new JPanel(new BorderLayout());
      infopanel.setBackground(colormap[1]);
      InfoLabel=new JLabel();
     // InfoLabel.setAlignment(1);
      updateInfoLabel();
      InfoLabel.setForeground(colormap[7]);
      infopanel.add("Center",InfoLabel);
      centralpanel.add("North",infopanel);

   
    /**************MESSAGE CANVAS**********************************/
           JPanel messagepanel =new JPanel(new BorderLayout());
           LoginToChat();
            
            }
    public void updateInfoLabel()
    {
      stringbuffer=new StringBuffer();
      stringbuffer.append("User Name :");
      stringbuffer.append(UserName);
      stringbuffer.append("          ");
      stringbuffer.append("Room  Name :");
      stringbuffer.append(UserRoom);
      stringbuffer.append("        ");
      stringbuffer.append("NO. of Users  :");
      stringbuffer.append(TotalUserCount);
      stringbuffer.append("          ");
      InfoLabel.setText(stringbuffer.toString());
    }
    private void LoginToChat() 
    {
       dialog=new InformationDialog(this);
       if(dialog.IsConnect==true)
       {
          UserName=dialog.textUsername.getText();
          ServerName=dialog.textServerfield.getText();
          ServerPort=Integer.parseInt(dialog.textServerport.getText());
          if(dialog.isProxyCheckBox.getState()==true)
          {
             IsProxy=true;
             ProxyHost=dialog.textProxyHost.getText();
             ProxyPort=Integer.parseInt(dialog.textProxyPort.getText());
          
          }
       }
        else
        {
          IsProxy=false;
        }
     try {
         connectToServer();
     } catch (IOException ex) {
         Logger.getLogger(Chat_Client.class.getName()).log(Level.SEVERE, null, ex);
     }
    
    }
    public void connectToServer() throws UnknownHostException, IOException
    {
        SocksSocket socket;
        messagecanvas.ClearAll();
        messagecanvas.AddMessageToMessageObject("Connecting to server....Please wait", MESSAGE_TYPE_ADMIN);
       try{
        if(IsProxy)
        {
          
            SocksSocketImplFactory factory=new SocksSocketImplFactory(ProxyHost,ProxyPort);
            SocksSocket.setSocketImplFactory(factory);
           
                socket=new SocksSocket(ServerName,ServerPort);
                socket.setSoTimeOut(0);
        }
        else
        {
          socket=new SocksSocket(ServerName,ServerPort);
        }
        dataoutputstream=new DataOutputStream(socket.getOutputStream());
        SendMessageToServer("hello"+UserName);
        datainputstream=new DataInputStream(socket.getInputStream());
        StartFlag=true;
        thread=new Thread(this);
        thread.start();
        EnableAll();
    }
       catch(IOException _IOExc){
           QuitConnection(QUIT_TYPE_NULL);
       }
    }
    private void SendMessageToServer(String Message)
    {
     try {
         dataoutputstream.writeBytes(Message+"\r\n");
     } catch (IOException ex) {
         Logger.getLogger(Chat_Client.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    
    public void InitializeAppletComponents()
    {
        DisableAll();
        LoginToChat();
    }
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource().equals(CmdSend))
        {
            if(!(TxtMessage.getText().trim().equals("")))
            {
                SendMessage();
            }
        }
        if((evt.getSource().equals(CmdExit))||(evt.getSource().equals(quititem)))
        {
            DisconnectChat();
            System.exit(0);
        }
         if(evt.getSource().equals(loginitem))
                 {
                   LoginToChat();  
                 }
         if(evt.getSource().equals(loginitem))
                 {
                   DisconnectChat();  
                 }
        if(evt.getSource().equals(aboutitem))
                 {
                  MessageBox messagebox=new MessageBox(this,false);
                  messagebox.AddMessage("~~13"+PRODUCT_NAME);
                  messagebox.AddMessage("~~~  Developed By Ertugrul...~~~");
                  messagebox.AddMessage(COMPANY_NAME);
                 }
    }
        public void keyPressed(KeyEvent evt)
        {
            if((evt.getKeyCode()==10)&&(!(TxtMessage.getText().trim().equals(""))))
                 {
                     SendMessage();
                 }
         } 
         public void keyTyped(KeyEvent evt){}
         public void keyReleased(KeyEvent evt){}
         
        
private void SendMessage()
{
    SendMessageToServer("MESS"+UserRoom+"~"+UserName+":"+TxtMessage.getText());
    messagecanvas.AddMessageToMessageObject(UserName+":"+TxtMessage.getText(),MESSAGE_TYPE_DEFAULT);
    TxtMessage.setText("");
    TxtMessage.requestFocus();
            
}


private void QuitConnection(int QuitType)
{
  if(socket!=null)
  {
    try{
         if(QuitType==QUIT_TYPE_DEFAULT)
             SendMessageToServer("QUIT"+UserName+"~"+UserRoom);
         if(QuitType==QUIT_TYPE_KICK)
             SendMessageToServer("KICK"+UserName+"~"+UserRoom);
          socket.close();
          socket=null;
          tappanel.ClearAll();
    }catch(IOException IOEXc){}
    
  }
  if(thread!=null)
  {
   thread.stop();
   thread=null;
  }
  DisableAll();
  StartFlag=false;
  SetAppletStatus("Admin:CONNECTION TO THE SERVER CLOSED");
}
private void EnableAll()
{
 TxtMessage.setEnabled(true);
  CmdSend.setEnabled(true);
  tappanel.enable(true);
  disconnectionitem.setEnabled(true);
  loginitem.setEnabled(false);
}
private void DisconnectChat()
{
   if(socket!=null)
   {
     messagecanvas.AddMessageToMessageObject("CONNECTION TO THE SERVER CLOSED", MESSAGE_TYPE_ADMIN);
     QuitConnection(QUIT_TYPE_DEFAULT);
   }
}
private void SetAppletStatus(String Message)
{
  if(messagecanvas!=null)
      messagecanvas.AddMessageToMessageObject(Message, MESSAGE_TYPE_ADMIN);
}
private void DisableAll()
{
  TxtMessage.setEnabled(false);
  CmdSend.setEnabled(false);
  tappanel.enable(false);
  disconnectionitem.setEnabled(false);
  loginitem.setEnabled(true);
  UserName="";
  UserRoom="";
  TotalUserCount=0;
}

    @Override
    public void run() {
        while(thread!=null)
        {
          try{
              ServerData=datainputstream.readLine();
              if(ServerData.startsWith("LIST"))
              {
                Tokenizer=new StringTokenizer(ServerData.substring(5),";");
                TotalUserCount=Tokenizer.countTokens();
                updateInfoLabel();
                tappanel.UserCanvas.ClearAll();
                while(Tokenizer.hasMoreTokens())
                {
                  tappanel.UserCanvas.AddListToMessageObject(Tokenizer.nextToken());
                }
                messagecanvas.ClearAll();
                messagecanvas.AddMessageToMessageObject("Welcome To the"+UserRoom+"Room", MESSAGE_TYPE_JOIN);
              
              }
              if(ServerData.startsWith("ROOM"))
              {
               Tokenizer=new StringTokenizer(ServerData.substring(5),";");
               UserRoom=Tokenizer.nextToken();
               updateInfoLabel();
               tappanel.RoomCanvas.ClearAll();
               tappanel.RoomCanvas.AddListenToMessageObject(UserRoom);
               while(Tokenizer.hasMoreTokens())
               {
                 tappanel.RoomCanvas.AddListenToMessageObject(Tokenizer.nextToken());
                 
               }
              }
            if(ServerData.startsWith("Add"))
            {
             TotalUserCount++;
             updateInfoLabel();
             SplitString=ServerData.substring(5);
             EnablePrivateWindow(SplitString);
             tappanel.UserCanvas.AddListenToMessageObject(SplitString);
             messagecanvas.AddMessageToMessageObject(SplitString+"joins chat...", MESSAGE_TYPE_JOIN);
             
            }
          if(ServerData.startsWith("EXIS"))
          {
            messagecanvas.AddMessageToMessageObject("Username Already exists...Try Again With some othername!", MESSAGE_TYPE_JOIN);
            thread=null;
            QuitConnection(QUIT_TYPE_NULL);
          }
          if(ServerData.startsWith("REMO"))
          {
             SplitString=ServerData.substring(5);
             RemoveUserFromPrivateChat(SplitString);
             messagecanvas.AddMessageToMessageObject(SplitString+"has been logged out from chat", MESSAGE_TYPE_LEAVE);
             TotalUserCount--;
             updateInfoLabel();
          
          }
          if(ServerData.startsWith("MESS"))
          {
              if(!(tappanel.UserCanvas.IsIgnoredUser(ServerData.substring(5,ServerData.indexOf(";")))));
              messagecanvas.AddMessageToMessageObject(ServerData.substring(5), MESSAGE_TYPE_DEFAULT);
          }
          if(ServerData.startsWith("KICK"))
          {
            messagecanvas.AddMessageToMessageObject("You are kicked out from chat from flooding the message!",MESSAGE_TYPE_ADMIN);
            thread=null;
            QuitConnection(QUIT_TYPE_KICK);
          }
          if(ServerData.startsWith("INKI"))
          {
             SplitString=ServerData.substring(5);
             tappanel.UserCanvas.RemoveListItem(SplitString);
             RemoveUserFromPrivateChat(SplitString);
             messagecanvas.AddMessageToMessageObject(SplitString+"has been kicked out from chat",MESSAGE_TYPE_ADMIN);
             TotalUserCount--;
             updateInfoLabel();
          
          }
           if(ServerData.startsWith("CHRO"))
          {
             UserRoom=ServerData.substring(5);
          
          }
            if(ServerData.startsWith("JORO"))
          {
             SplitString=ServerData.substring(5);
             tappanel.UserCanvas.AddListItemToMessageObject(SplitString);
             RemoveUserFromPrivateChat(SplitString);
             TotalUserCount++;
             updateInfoLabel();
             messagecanvas.AddMessageToMessageObject(SplitString+"joins chat",MESSAGE_TYPE_ADMIN);
             
          
          }
             if(ServerData.startsWith("LERO"))
          {
             SplitString=ServerData.substring(5,ServerData.indexOf("~"));
             tappanel.UserCanvas.RemoveListItem(SplitString);
              messagecanvas.AddMessageToMessageObject(SplitString+"has leaves "+UserName+"Room and joins into"+ServerData.substring(ServerData.indexOf("~")+1)+"Room",MESSAGE_TYPE_ADMIN);
           
            
              TotalUserCount--;
             updateInfoLabel();
          
          }
              if(ServerData.startsWith("ROCO"))
          {
             SplitString=ServerData.substring(5,ServerData.indexOf("~"));
             tappanel.TxtUserCount.setText("Total Users in"+SplitString+":"+ServerData.substring(5,ServerData.indexOf("~")));
             
          
          }
               if(ServerData.startsWith("PRIV"))
          {
             SplitString=ServerData.substring(5,ServerData.indexOf("~"));
           if(!(tappanel.UserCanvas.IsIgnoredUser(SplitString)))
           {
             boolean PrivateFlag=false;
             for(G_ILoop=0;G_ILoop<PrivateWindowCount;G_ILoop++)
             {
               if(privatewindow[G_ILoop].UserName.equals(SplitString))
               {
                 privatewindow[G_ILoop].AddMessageToMessageCanvas(ServerData.substring(5));
                  privatewindow[G_ILoop].show();
                   privatewindow[G_ILoop].requestFocus();
                   PrivateFlag=true;
                   break;
                   
               }
             }
             if(!PrivateFlag)
             {
               if(PrivateWindowCount>=MAX_PRIVATE_WINDOW)
               {
                 messagecanvas.AddMessageToMessageObject("you are exceeding private window limit!So you may loose some messages", MESSAGE_TYPE_ADMIN);
               }
              else
               {
                 privatewindow[PrivateWindowCount++]=new PrivateChat(this,SplitString);
                 privatewindow[PrivateWindowCount-1].AddMessageToMessageCanvas(ServerData.substring(5));
                 privatewindow[PrivateWindowCount-1].show();
                 privatewindow[PrivateWindowCount-1].requestFocus();
               }
                }
             }
           }
          }catch(Exception exc){messagecanvas.AddMessageToMessageObject(exc.getMessage(),MESSAGE_TYPE_ADMIN );QuitConnection(QUIT_TYPE_DEFAULT);}
          }
        
        }
    private void EnablePrivateWindow(String ToUserName)
    {
      for(G_ILoop=0;G_ILoop<PrivateWindowCount;G_ILoop++)
      {
        if(privatewindow[G_ILoop].UserName.equals(ToUserName))
        {
          privatewindow[G_ILoop].messagecanvas.AddMessagetoMessageObject(ToUserName+"is currently online!",MESSAGE_TYPE_ADMIN);
          privatewindow[G_ILoop].DisableAll();
          return;
        }
      }
    }
    private void RemoveUserFromPrivateChat(String ToUserName)
    {
       for(G_ILoop=0;G_ILoop<PrivateWindowCount;G_ILoop++)
       {
          if(privatewindow[G_ILoop].UserName.equals(ToUserName))
          {
          privatewindow[G_ILoop].messagecanvas.AddMessagetoMessageObject(ToUserName+"is currently offline!",MESSAGE_TYPE_ADMIN);
         privatewindow[G_ILoop].DisableAll();
          return;
          }
       }

        }
    protected void SentPrivateMessageToServer(String Message,String ToUserName)
    {
      SendMessageToServer("PRIV"+ToUserName+"~"+UserName+":"+Message);
    }
    protected void RemovePrivateWindow(String ToUserName)
    {
      int m_UserIndex=0;
      for(G_ILoop=0;G_ILoop<PrivateWindowCount;G_ILoop++)
      {
         m_UserIndex++;
         if(privatewindow[G_ILoop].UserName.equals(ToUserName)) break;
      }
      for(int m_iLoop=m_UserIndex;m_iLoop<PrivateWindowCount;m_iLoop++)
      {
        privatewindow[m_iLoop]=privatewindow[m_iLoop+1];
      }
      PrivateWindowCount--;
    }
    protected void ChangeRoom()
    {
      if(tappanel.RoomCanvas.SelectedUser.equals(""))
      {
        messagecanvas.AddMessageToMessageObject("Ivalid room selection", MESSAGE_TYPE_ADMIN);
        return;
      }
       if(tappanel.RoomCanvas.SelectedUser.equals(UserRoom))
      {
        messagecanvas.AddMessageToMessageObject("You are already in that room", MESSAGE_TYPE_ADMIN);
        return;
      }
       SendMessageToServer("CHRO"+UserName+"~"+tappanel.RoomCanvas.SelectedUser);
    }
    protected void GetRoomUserCount(String RoomName)
    {
      SendMessageToServer("ROCO"+RoomName);
    }
    protected void AddImageToTextField(String ImageName)
    {
      if(TxtMessage.getText()==null||TxtMessage.getText().equals(""))
          TxtMessage.setText("~~"+ImageName+"~~");
      else
          TxtMessage.setText(TxtMessage.getText()+""+"~~"+ImageName+"");
    }
}