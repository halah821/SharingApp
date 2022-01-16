package com.example.sharingapp;

import android.content.Context;

public class EditContactCommand extends Command{
    private ContactList contact_list;
    private Contact old_contact;
    private Contact new_contact;
    private Context context;
    public EditContactCommand(ContactList contact_list, Contact new_contact,Contact old_contact, Context context){
        this.contact_list = contact_list;
        this.new_contact = new_contact;
        this.old_contact = old_contact;
        this.context = context;
    }
    @Override
    public void execute() {
        contact_list.deleteContact(old_contact);
        contact_list.addContact(new_contact);
        setIsExecuted(contact_list.saveContacts(context));
    }
}
