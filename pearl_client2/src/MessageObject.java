/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mumin-PC
 */
public class MessageObject {
    int width,height,StartX,StartY;
    String Message;
    boolean IsImage,Selected,IsIgnored;
    
    
    MessageObject()
    {
       width=height=StartY=StartX=0;
       Message=null;
       IsImage=Selected=IsIgnored=false;
       
    }
}
