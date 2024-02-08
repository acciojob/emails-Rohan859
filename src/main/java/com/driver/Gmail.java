package com.driver;

import java.net.DatagramSocket;
import java.util.*;




public class Gmail extends Email
{

    int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)

   LinkedList<String>inbox=new LinkedList<>();
    LinkedList<String>trash=new LinkedList<>();

    HashMap<Date,Integer>numberOfMails=new HashMap<>();
    HashMap<String,String>inboxMap=new HashMap<>();
    HashMap<String,String>trashMap=new HashMap<>();

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

        if(inbox.size()>=inboxCapacity)
        {
            String msg=inbox.removeFirst();//got the oldest message and removed from the inbox
            inboxMap.remove(msg);//removed from the inbox hashmap

            trash.add(msg); //add the oldest message in trash
            trashMap.put(msg,date+"-"+sender);//added into trashmap


            inbox.add(message); //then add new message in the inbox
            inboxMap.put(message,date+"-"+sender);//added new message into inboxmap
        }
        else
        {
            inbox.add(message);
            inboxMap.put(message,date+"-"+sender);//added new message into inboxmap
        }

        numberOfMails.put(date,numberOfMails.getOrDefault(date,0)+1);
    }

    public void deleteMail(String message)
    {
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing

        if(inbox.contains(message)) //if message found in the inbox
        {
            inbox.remove(message); //removed from the inbox
            String value=inboxMap.get(message);
            inboxMap.remove(message);//removed from inboxmap
            trash.add(message); //afterthat deleted message moved to trash
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

        return inbox.getLast();
    }

    public String findOldestMessage()
    {
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox

        if(inbox.size()==0) //if inbox is empty
        {
            return null;
        }

        return inbox.getFirst();

    }

    public int findMailsBetweenDates(Date start, Date end)
    {
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date

        int first=0;
        int second=0;

        if(numberOfMails.containsKey(start))
        {
            first=numberOfMails.get(start);
        }

        if(numberOfMails.containsKey(end))
        {
            second=numberOfMails.get(end);
        }

        return first+second;
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
