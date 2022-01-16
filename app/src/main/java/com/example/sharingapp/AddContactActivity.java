package com.example.sharingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class AddContactActivity extends AppCompatActivity {
    private ContactList contact_list = new ContactList();
    private ContactListController contact_list_controller = new ContactListController(contact_list);
    private Context context;
    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        context = getApplicationContext();
        contact_list_controller.loadContacts(context);

    }
    public void saveContact(View view) {

        if (!validateInput()){
            return;
        }
// End AddContactActivity
        finish();
    }
    public boolean validateInput() {
        // Input validation goes here...
        // ...
        boolean success = false;
        String username_str = username.getText().toString();
        String email_str = email.getText().toString();
        if (username_str.equals("")) {
            username.setError("Empty field!");
            return success;
        }
        if (email_str.equals("")) {
            email.setError("Empty field!");
            return success;
        }
        if (!email_str.contains("@")){
            email.setError("Must be an email address!");
            return success;
        }
        if (!contact_list_controller.isUsernameAvailable(username_str)){
            username.setError("Username already taken!");
            return success;
        }
        Contact contact = new Contact(username_str, email_str, null);
        // Add contact
        success =  contact_list_controller.addContact(contact,context);
        return success;
    }
}