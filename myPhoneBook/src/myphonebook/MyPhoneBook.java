

package myphonebook;

import java.util.Scanner;


public class MyPhoneBook {

   private static Scanner scanner=new Scanner(System.in);
   private static MobilePhone mobilephone=new MobilePhone("898900-");
    public static void main(String[] args) {
        
        boolean quit=false;
        startPhone();
        printActions() ;
        while(!quit)
        {
            System.out.println("\nEnter action :(6 to show available actions)");
            int action=scanner.nextInt();
            scanner.nextLine();
            switch(action)
            {
                case 0:
                    System.out.println("Shutting down..");
                    quit=true;
                    break;
                case 1:
                    printContacts();
                    break;
                case 2:
                    addContact();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    removeContact();
                    break;
                case 5:
                    
                    queryContact();
                    break;
                case 6:
                    printActions();
                    break;
                    
            }
        }
               
        
                
         }
    private static void  addContact()
    {
        System.out.println("Enter new ContactName");
        String name=scanner.nextLine();
        System.out.println("Enter new ContactNo");
        String Phonenumber=scanner.nextLine();
        Contact contact=Contact.createContact(name, Phonenumber);
        if(mobilephone.addNewContact(contact))
        {
            System.out.println("New contact added "+" name "+name +" phonenumber "+ Phonenumber);
        }else{
            System.out.println("Cannot add"+name+"already on file");
        }
    }
    private static void updateContact()
    {
        System.out.println("Enter existing name");
        String name=scanner.nextLine();
        Contact existingcontactrecord=mobilephone.queryContact(name);
        if(existingcontactrecord==null)
        {
            System.out.println("Cannot found");
            return;
                  
        }
        System.out.println("enter new name");
        String newName=scanner.nextLine();
        System.out.println("enter new number");
        String Newnumber=scanner.nextLine();
         Contact newContact=Contact.createContact(newName, Newnumber);
         if(mobilephone.updateContact(existingcontactrecord, newContact))
         {
             System.out.println("Successfully updated");
         }else{
             System.out.println("error while updating");
         }
              
    }
    private static void removeContact()
    {
       System.out.println("Enter existing name");
        String name=scanner.nextLine();
        Contact existingcontactrecord=mobilephone.queryContact(name);
        if(existingcontactrecord==null)
        {
            System.out.println("Cannot found");
            return;
                  
        }
        if(mobilephone.removeContact(existingcontactrecord))
        {
            System.out.println("contact deleted successfully");
        }else{
            System.out.println("error while deleting");
        }
    }
    private static void  queryContact()
    {
       System.out.println("Enter existing name");
        String name=scanner.nextLine();
        Contact existingcontactrecord=mobilephone.queryContact(name);
        if(existingcontactrecord==null)
        {
            System.out.println("Cannot found");
            return;
                  
        }
        System.out.println("name"+existingcontactrecord.getName()+"phone number"+existingcontactrecord.getPhoneNumber());
    }
     private static void printContacts()
    {
       mobilephone.printContacts();
    }
    private static void startPhone()
    {
        System.out.println("Starting Phone....");
    }
    private static void printActions()
    {
        System.out.println("\nAvailable Actions :\nPress");
        System.out.println("0  -to shutdown\n"+
                           "1  -to printConatct\n "+
                           "2  - to add a new contact\n"+
                           "3  - to update a existing contact\n"+
                           "4  - to remove an existing contact\n"+
                           "5  - to query if an existing contact exits\n"+
                           "6  - to print instructions\n");
        System.out.println("Choose your action:  ");
    }
}
