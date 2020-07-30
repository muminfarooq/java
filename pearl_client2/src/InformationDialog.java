

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin-PC
 */
public class InformationDialog extends Dialog implements ActionListener, CommonSettings
{
   boolean IsConnect;
   Chat_Client client; 
   JTextField textServerfield,textServerport,textProxyHost,textProxyPort,textUsername;
   JButton cmdOk,cmdCancel;
   Checkbox isProxyCheckBox;
   Properties properties;
   
       InformationDialog(Chat_Client parent) 
       {
         super(parent,PRODUCT_NAME+"-Login",true);
        client= parent;
        setFont(client.textfont);
        setLayout(new BorderLayout());
        IsConnect=false;
        properties=new Properties();
        
       try {
           properties.load(this.getClass().getClassLoader().getResourceAsStream("dataproperties.properties"));
       } catch (IOException ex) {
           Logger.getLogger(InformationDialog.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
        
        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e)
        {
          setVisible(false);

        }
        });
        JPanel ButtonPanel=new JPanel(new GridLayout(7, 2, 15, 30));
        ButtonPanel.setBackground(client.colormap[3]);
        JLabel lblusername=new JLabel("NICKNAME");
        textUsername=new JTextField(properties.getProperty(chatusername));
        ButtonPanel.add(lblusername);
        ButtonPanel.add(textUsername);
        
        JLabel serverlbl=new JLabel("server name");
        textServerfield=new JTextField();
        if(properties.getProperty("chatname")!=null)
        
           textServerfield.setText(properties.getProperty(chatservername));
        
       
        else
        
           textServerfield.setText("chatkashmir.dns2go.com");
           ButtonPanel.add(lblusername);
           ButtonPanel.add(textServerfield);
           
           JLabel lblserverport=new JLabel("server port");
           textServerport=new JTextField();
             if(properties.getProperty("chatserverport")!=null)
        
           textServerfield.setText(properties.getProperty(chatserverport));
        
        
        
        else
        
           textServerfield.setText("9906");
           ButtonPanel.add(lblserverport);
           ButtonPanel.add(textServerport);
         
           
            JLabel lblProxy=new JLabel("Proxy ");
          isProxyCheckBox=new Checkbox();
          isProxyCheckBox.setState((Boolean.valueOf(properties.getproperty(chatproxystate))).booleanValue());
    
          ButtonPanel.add(lblProxy);
          ButtonPanel.add(isProxyCheckBox);
    
          
              JLabel lblProxyHost=new JLabel("Proxy host(socks: )");
          textProxyHost=new JTextField();
          textProxyHost.setText(properties.getProperty(chatproxyhost));
          
    
          ButtonPanel.add(lblProxyHost);
          ButtonPanel.add(textProxyHost);
          
           
              JLabel lblProxyPort=new JLabel("Proxy Port(socks: )");
          textProxyPort=new JTextField();
          textProxyPort.setText(properties.getProperty(chatproxyport));
          
    
          ButtonPanel.add(lblProxyPort);
          ButtonPanel.add(textProxyPort);
    
            cmdOk=new JButton("connect");
            cmdOk.addActionListener(this);
            cmdCancel=new JButton("Quit");
            cmdCancel.addActionListener(this);
            add("Center",ButtonPanel);
            JPanel EmptyNorthpanel=new JPanel();
            EmptyNorthpanel.setBackground(client.colormap[3]);
            add("North",EmptyNorthpanel);
             
                       JPanel EmptySouthpanel=new JPanel();
            EmptySouthpanel.setBackground(client.colormap[3]);
            add("South",EmptySouthpanel);
                    
                       JPanel EmptyEastpanel=new JPanel();
            EmptyEastpanel.setBackground(client.colormap[3]);
            add("East",EmptyEastpanel);
                            JPanel EmptyWestpanel=new JPanel();
            EmptyWestpanel.setBackground(client.colormap[3]);
            add("West",EmptyWestpanel);
            
           setSize(250,400);
           client.show();////depreciated method
           show();
       
        }
        
       
      
        
        
    @Override
     public void actionPerformed(ActionEvent e) {
              IsConnect=true;
               FileOutputStream fout=null;
        
         if(e.getSource().equals(cmdOk))
            {
             
             try {
                 fout=new FileOutputStream(new File("data.properties"));
             } catch (FileNotFoundException ex) {
                 }
                if(isProxyCheckBox.getState()==true)
                {
                    properties.setProperty("chatproxystate", "true");
                    
                }
            
                else{
                   properties.setProperty("chatproxystate", "false");
                }
                   properties.setProperty("chatusername", "false");
                      properties.setProperty("chatservername",   textServerfield.getText());

                         properties.setProperty("chatproxyhost", textProxyHost.getText());
                            properties.setProperty("chatproxyport", textProxyPort.getText());
                               properties.setProperty("chatserverport", textServerport.getText());
                               properties.save(fout,PRODUCT_NAME);
                
                               dispose();
     }
          if(e.getSource().equals(cmdCancel))
         {
         IsConnect=false;
         }
            }
}
        
            
         
        

