/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myphonebook;

import java.util.ArrayList;


public class MobilePhone {
    private String phoneNumber;
    private ArrayList<Contact> myContacts;
      public MobilePhone(String number)
      {
          this.phoneNumber=number;
        this.myContacts=new ArrayList<Contact>();
      }
      public boolean addNewContact(Contact contact)
      {
       if(findContact(contact.getName())>=0)
       {
       
           System.out.println("Conatct is already on file");
           return false;
       }
       myContacts.add(contact);
       return true;
      }
      private int findContact(Contact contact)
      {
        return this.myContacts.indexOf(contact);
      }
      private int findContact(String contactName)
      {
       for(int i=0; i<myContacts.size();i++)
       {
         Contact contact=this.myContacts.get(i);
         if(contact.getName().equals(contactName))
         {
             return i;
         }
       }
       return -1;
      }
      public boolean updateContact(Contact oldcontact,Contact newcontact)
      {
        int foundposition=findContact(oldcontact);
            if(foundposition<0)
            {
                System.out.println(oldcontact.getName()+"was not found...");
                return false;
            }
            this.myContacts.set(foundposition, newcontact);
            System.out.println(oldcontact.getName()+" replaced successfully with "+newcontact.getName());
            return true;
            
      }
      public boolean removeContact(Contact contact)
      { int foundposition=findContact(contact);
            if(foundposition<0)
            {
                System.out.println(contact.getName()+"was not found...");
                return false;
            }
         this.myContacts.remove(contact);
          System.out.println(contact.getName()+" was successfully deleted");
          return true;
      }
      public String queryContact(Contact contact)
      {
        if(findContact(contact)>=0)
        {
         return contact.getName();
        }
        return null;
      }
      public Contact queryContact(String name)
      {
        int pos=findContact(name);
        if(pos>=0)
        {
          return this.myContacts.get(pos);
        }
        return null;
      }
      public void printContacts()
      {
          System.out.println("Contact List..");
          for (int i=0;i<this.myContacts.size();i++)
          {
              System.out.println((i+1)+" ."+this.myContacts.get(i).getName()+"-->"+this.myContacts.get(i).getPhoneNumber());
              
          
          }
      }
}
