
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin
 */
class MessageBox extends Dialog implements ActionListener,CommonSettings {
    Chat_Client chatclient;
    JButton CmdOk,CmdCancel;
    MessageCanvas messagecanvas;
    ScrollView MessageScrollView;
    MessageBox(Chat_Client Parent,boolean okcan)
    {
        super(Parent,"Information",false);
        chatclient=Parent;
        
    }
    protected void AddMessage(String message)
    {
        
    }
    private void addOkCancelPanel(boolean okcan)
    {
      JPanel panel=new JPanel();
      panel.setLayout(new FlowLayout());
      createOkButton(panel);
      if(okcan==true)
          createCancelButton(panel);
      add("SOUTH",panel);
    }
    private void createOkButton(JPanel panel)
    {
      CmdOk=new CustomButton(chatclient,"ok");
      panel.add(CmdOk);
      CmdOk.addActionListener(this);
    }
        private void createCancelButton(JPanel panel)
    {
      CmdCancel=new CustomButton(chatclient,"cancel");
      panel.add(CmdCancel);
      CmdCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==CmdOk)
        {
         dispose();
        }
         if(e.getSource()==CmdCancel)
        {
         dispose();
        }
          }
    
}
