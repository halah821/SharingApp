package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity implements Observer{
    private EditText username,email;
    private Context context;
    private ContactList contact_list= new ContactList();
    private ContactListController contact_list_controller = new ContactListController(contact_list);
    private Contact contact;
    private ContactController contact_controller ;
    private int pos;
    private boolean on_create_update = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        context = getApplicationContext();
        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);

        contact_list_controller.addObserver(this);
        contact_list_controller.loadContacts(context);
        on_create_update = false;



    }


    public void saveContact(View view){
        if ( !validateInput() ) {
            return;
        }
        // End EditContactActivity
        finish();
    }
    public void deleteContact(View view){
        // Delete contact
        boolean success = contact_list_controller.deleteContact(contact,context);
        if (!success){
            return;
        }
        // End EditContactActivity
        finish();
    }

    /**
     * Called when the activity is destroyed, thus we remove this activity as a listener
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        contact_list_controller.removeObserver(this);
    }

    /**
     * Only need to update the view from the onCreate method
     */
    @Override
    public void update() {
        if (on_create_update) {

            contact = contact_list_controller.getContact(pos);
            contact_controller = new ContactController(contact);

            username = (EditText) findViewById(R.id.username);
            email = (EditText) findViewById(R.id.email);

            // Update the view
            username.setText(contact_controller.getUsername());
            email.setText(contact_controller.getEmail());
        }
    }
    public boolean validateInput() {
        // Input validation goes here...
        // ...
        boolean success = false;
        String email_str = email.getText().toString();
        if (email_str.equals("")) {
            email.setError("Empty field!");
            return success;
        }
        if (!email_str.contains("@")){
            email.setError("Must be an email address!");
            return success;
        }
        String username_str = username.getText().toString();
        // Check that username is unique AND username is changed (Note: if username was not changed
        // then this should be fine, because it was already unique.)
        if (!contact_list_controller.isUsernameAvailable(username_str) &&
                !(contact_controller.getUsername().equals(username_str))) {
            username.setError("Username already taken!");
            return success;
        }
        // Reuse the contact id
        String id_str = contact_controller.getId();
        Contact updated_contact = new Contact(username_str, email_str, id_str);
        // Edit item
         success = contact_list_controller.editContact(contact,updated_contact,context);
        return success;
    }
}