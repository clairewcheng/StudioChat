package com.example.claire.studiochat;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference messages = database.getReference("messages");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.send_button);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.message_entry);
                ChatMessage message = new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getDisplayName());
                // Read the input and push the data to Firebase database
                messages.child("messages").child("ID:" + input.getText()).setValue(message);
                //messages.push().setValue(message);
                input.setText("");
            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}
