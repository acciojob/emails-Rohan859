package com.driver;



import java.net.DatagramSocket;
import java.util.*;


class myMessage
{
    Date date;
    String sender;
    String message;

    public myMessage(Date date, String sender, String message)
    {
        this.date = date;
        this.sender = sender;
        this.message = message;


    }


}

public class Gmail extends Email
{

    int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)


   LinkedList<myMessage>inbox=new LinkedList<>();
    LinkedList<myMessage>trash=new LinkedList<>();

    HashMap<Date,Integer>numberOfMails=new HashMap<>();
    HashMap<String,myMessage>inboxMap=new HashMap<>();
    HashMap<String,myMessage>trashMap=new HashMap<>();
    HashMap<Date,Integer>dateMap=new HashMap<>();

    public Gmail(String emailId, int inboxCapacity)
    {
        super(emailId);
        this.inboxCapacity=inboxCapacity;
    }

    public void receiveMail(Date date, String sender, String message)
    {
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.

       // String msg=date+""+sender+""+message;

        myMessage myMsg=new myMessage(date,sender,message);
        if(inbox.size()>=inboxCapacity)
        {

            myMessage msg=inbox.removeFirst();//got the oldest message and removed from the inbox
            inboxMap.remove(msg.message);//removed from the inbox hashmap

            trash.add(msg); //add the oldest message in trash
            trashMap.put(msg.message,msg);//added into trashmap


            inbox.add(myMsg); //then add new message in the inbox
            inboxMap.put(message,myMsg);//added new message into inboxmap

            //dateMap.put(date,dateMap.getOrDefault(date,0)+1);
        }
        else
        {
            inbox.add(myMsg);
            inboxMap.put(message,myMsg);//added new message into inboxmap
            //dateMap.put(date,dateMap.getOrDefault(date,0)+1);
        }


        numberOfMails.put(date,numberOfMails.getOrDefault(date,0)+1);
    }

    public void deleteMail(String message)
    {
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        if(inbox.size()==0)
        {
            return;
        }

        if(inbox.contains(message)) //if message found in the inbox
        {
            inbox.remove(message); //removed from the inbox
            myMessage value=inboxMap.get(message);

            inboxMap.remove(value.message);//removed from inboxmap
            trash.add(value); //afterthat deleted message moved to trash
            trashMap.put(message,value);//added into trashmap


        }
    }


    public String findLatestMessage()
    {
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox

        if(inbox.size()==0) //inbox is empty
        {
            return null;
        }

        return inbox.getLast().message;
    }

    public String findOldestMessage()
    {
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox

        if(inbox.size()==0) //if inbox is empty
        {
            return null;
        }

        return inbox.getFirst().message;

    }

    public int findMailsBetweenDates(Date start, Date end)
    {
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int count=0;
        for(myMessage x:inbox)
        {
            if(x.date.equals(start) && x.date.after(start) && x.date.equals(end) && x.date.before(end))
            {
                count++;
            }
        }
        return count;

//        int first=0;
//        int second=0;
//
//        if(numberOfMails.containsKey(start))
//        {
//            first=numberOfMails.get(start);
//        }
//
//        if(numberOfMails.containsKey(end))
//        {
//            second=numberOfMails.get(end);
//        }
//
//        return first+second;
    }

    public int getInboxSize()
    {
        // Return number of mails in inbox
        return inbox.size();
    }

    public int getTrashSize()
    {
        // Return number of mails in Trash
        return trash.size();
    }

    public void emptyTrash()
    {
        // clear all mails in the trash
        trash.clear();
    }

    public int getInboxCapacity()
    {
        // Return the maximum number of mails that can be stored in the inbox
        return inboxCapacity;
    }
}
