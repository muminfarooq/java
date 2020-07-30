
import java.awt.*;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin-PC
 * canvas is a container jus like panel
 */

public class MessageCanvas extends Canvas implements CommonSettings{
    Chat_Client client;
  
    Dimension dimension;
    ArrayList MessageArray;
    int total_width;
    int Message_count;
    int total_height;
    int Xoffset;
    int Yoffset;
    int horizontalspace;
    int LLoop;
    Image offimage;
    Font Usernamefont;
    Font Text_font;
    Graphics offgraphics;
    FontMetrics fontmetrics;
    ScrollView scrollview;
    StringTokenizer stringtokenizer;
       String tokenstring;
       MessageObject messageobject;
       MessageCanvas(Chat_Client parent)
       {
           client=parent;
           dimension=client.getSize();
           MessageArray=new ArrayList();
           Message_count=0;
           total_width=0;
           horizontalspace=2;
           Usernamefont=client.getFont();
           Text_font=client.getFont();
           setFont(client.getFont());
           fontmetrics=client.getFontMetrics(client.getFont());
        
           
       }
        protected void ClearAll()
        {
          MessageArray.clear();
          Message_count=0;
          total_height=0;
          total_width=0;
         // ScrollView.
       }
        protected void AddMessageToMessageObject(String Message,int Messagetype)
        {
          String m_Message="";
           stringtokenizer=new StringTokenizer(Message,"");
         while(stringtokenizer.hasMoreTokens())
         {
            tokenstring=stringtokenizer.nextToken();
            if(fontmetrics.stringWidth(m_Message+tokenstring)<dimension.width)
            {
               m_Message=m_Message+tokenstring+"";
            }
             else
             {
                 AddMessage(m_Message,Messagetype);       
             }
         }
         AddMessage(m_Message,Messagetype);
        }
        private void AddMessage(String Message, int Messagetype)
        {
          int m_startY=DEFAULT_MESSAGE_CANVAS_POSITION;
          if(MessageArray.size()>0)
          {
             messageobject =(MessageObject)MessageArray.get(MessageArray.size()-1);
             m_startY=messageobject.StartY+messageobject.height;
          }
          messageobject=new MessageObject();
          messageobject.Message=Message;
          messageobject.StartY=m_startY;
          messageobject.Messagetype=Messagetype;
          /*********************IS IMAGE************************/
          if(Message.indexOf("~~")>=0)
          {
            messageobject.IsImage=true;
            messageobject.width=fontmetrics.stringWidth(Message);
            messageobject.height=fontmetrics.getHeight()+fontmetrics.getDescent();
            
          }
          MessageArray.add(messageobject);
          Message_count++;
          total_width=Math.max(total_width,messageobject.width);
          total_height=m_startY+messageobject.height;
          //scrollview.
          int m_height=total_height-Yoffset;
          if(m_height>dimension.height)
          {
            Yoffset=total_height-dimension.height;
          }
          //scrollview.
         // scrollview.
         repaint();
        }
        private void paintFrame(Graphics graphics)
        {
         if(Message_count<1) return;
         int m_YPos=Yoffset+dimension.height;
         int m_StartPos=0;
         int m_listArraySize=MessageArray.size();
         for(LLoop=0;LLoop<Message_count&&m_StartPos<m_YPos;LLoop++)
         {
            if(m_listArraySize<LLoop)return;
            messageobject=(MessageObject)MessageArray.get(LLoop);
            if(messageobject.StartY>=Yoffset)
            {
              PaintMessageIntoCanvas(graphics,messageobject);
              m_StartPos=messageobject.StartY;
              
            }
         }
        if(LLoop<Message_count)
        {
             messageobject=(MessageObject)MessageArray.get(LLoop);
         PaintMessageIntoCanvas(graphics,messageobject);
        }
        }
        private void PaintMessageIntoCanvas(Graphics graphics,MessageObject messageobject)
        {
          graphics.setColor(Color.BLACK);
          int m_YPos=messageobject.StartY-Yoffset;
          int m_XPos=5-Xoffset;
          int customwidth=0;
          String Message=messageobject.Message;
          if(Message.indexOf(":")>=0)
          {
            graphics.setFont(Usernamefont);
            fontmetrics=client.getGraphics().getFontMetrics();
            String m_UserName=Message.substring(0,Message.indexOf(":"+1));
            graphics.drawString(m_UserName, m_XPos+customwidth,m_YPos);
            customwidth+=fontmetrics.stringWidth(m_UserName)+horizontalspace;
            Message=Message.substring(Message.indexOf(":")+1);
   
          }
          ////*****************************setfont text/************************************
          
          
          
          
          ////*****************************end/************************************
          //****************************print image***********************************////
          if(messageobject.IsImage==true)
          {
            stringtokenizer=new StringTokenizer(Message," ");
            while(stringtokenizer.hasMoreTokens())
            {
                tokenstring=stringtokenizer.nextToken();
                if(tokenstring.indexOf("~~")>=0)
                {
                   int m_ImageIndex=Integer.parseInt(tokenstring.substring(2));
                   if((m_ImageIndex>=0)&&(m_ImageIndex<client.IconCount))
                   {
                    
                      graphics.drawString(m_UserName, m_XPos+customwidth, m_YPos);
                      
                   }
                }
            
            }
          
          }
        }
}
