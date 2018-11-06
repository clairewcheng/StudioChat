package com.example.claire.studiochat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText emailField;
    private EditText passwordField;
    private Button createAccountButton;
    private Button goToSignInButton;

    private User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailField = (EditText) findViewById(R.id.email_text_entry);
        passwordField = (EditText) findViewById(R.id.password_text_entry);
        goToSignInButton = (Button) findViewById(R.id.sign_in_button);
        createAccountButton= (Button) findViewById(R.id.create_account_button);

        goToSignInButton.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount() {
        _user = new User();
        final String email = emailField.getText().toString().trim();
        _user.setEmail(email);
        String password = passwordField.getText().toString().trim();
        _user.setPassword(password);

        if(TextUtils.isEmpty(email)) {
            //send error message email field is empty
            Toast.makeText(this, "Please enter valid email...", Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(password)) {
            //send error message password field is empty

            Toast.makeText(this, "Please enter valid password...", Toast.LENGTH_SHORT).show();

            return;
        }

        mAuth.createUserWithEmailAndPassword (email, password)
                .addOnCompleteListener (this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            // call to change activity to main application landing.
                            Toast.makeText(CreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

                            // Add new user to database
                            //TODO: change to using unique uid
                            String uid = email.substring(0, email.indexOf("."));

                            // navigate to logged in screen
                            Intent intentSignUP = new Intent(getApplicationContext(),ChatActivity.class);
                            intentSignUP.putExtra("userType", _user.getUserType());
                            startActivity(intentSignUP);

                        }
                        if(!task.isSuccessful()){
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(CreateAccountActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view == createAccountButton) {
            createAccount();

        }

        if (view == goToSignInButton) {
            // navigate to logged in screen(currently causing app crash
            Intent intentSignUP = new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intentSignUP);
        }

    }
}
