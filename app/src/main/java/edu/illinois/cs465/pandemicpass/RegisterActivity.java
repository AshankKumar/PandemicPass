package edu.illinois.cs465.pandemicpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextConfirmPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextFirstName = (EditText) findViewById(R.id.firstName);
        editTextLastName = (EditText) findViewById(R.id.lastName);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.register) {
            registerUser();
        }
    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (firstName.isEmpty()) {
            editTextFirstName.setError("First Name is required.");
            editTextFirstName.requestFocus();
        }
        else if (lastName.isEmpty()) {
            editTextLastName.setError("Last Name is required.");
            editTextLastName.requestFocus();
        }
        else if (email.isEmpty()) {
            editTextEmail.setError("Email is required.");
            editTextEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email is not valid.");
            editTextEmail.requestFocus();
        }
        else if (password.isEmpty()) {
            editTextPassword.setError("Password is required.");
            editTextPassword.requestFocus();
        }
        else if (password.length() < 8) {
            editTextPassword.setError("Password must be at least 8 characters.");
            editTextPassword.requestFocus();
        }
        else if (!confirmPassword.equals(password)) {
            editTextConfirmPassword.setError("Confirmation does not match password.");
            editTextConfirmPassword.requestFocus();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(firstName, lastName, email);

                                FirebaseDatabase.getInstance()
                                        .getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    // will probably remove the toast for success and just redirect instead
                                                    Toast.makeText(RegisterActivity.this, "Successfully registered user.", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    // start new activity to home screen and destroy this one instead of keeping in stack
                                                    startActivity(new Intent(RegisterActivity.this, HomeScreenActivity.class));
                                                    RegisterActivity.this.finish();
                                                }
                                                else {
                                                    Toast.makeText(RegisterActivity.this, "Failed to register user.", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                }

                                            }
                                        });

                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to register user.", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });


        }
    }
}