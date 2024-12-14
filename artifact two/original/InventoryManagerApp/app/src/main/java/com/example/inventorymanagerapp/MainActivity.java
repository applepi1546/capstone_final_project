package com.example.inventorymanagerapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.*;

public class MainActivity extends AppCompatActivity
{

    private LoginDB loginDB;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonSignUp;

    public void openMain(View view, String _user) //open the home screen
    {
        InventoryDB _inventory; //start the inventory Database
        Intent intent = new Intent(this, home_screen.class);
        intent.putExtra("username", _user);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //  layout file

        loginDB = new LoginDB(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) // Login button
            {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (loginDB.checkUser(username, password)) // Check user
                {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    openMain(v, username);
                }
                else // Invalid username or password
                {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) // Sign up button
            {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) // if username or password is empty
                {
                    Toast.makeText(MainActivity.this, "Username and password are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (loginDB.checkUser(username, password)) // Check user
                {
                    Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.length() < 8) // Password length
                {
                    Toast.makeText(MainActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (username.length() < 4) // Username length
                {
                    Toast.makeText(MainActivity.this, "Username must be at least 4 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginDB.addUser(username, password); // Add user
                Toast.makeText(MainActivity.this, "User Login Added", Toast.LENGTH_SHORT).show();

                editTextUsername.setText("");
                editTextPassword.setText("");
                if (loginDB.checkUser(username, password)) // Check user
                {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    openMain(v, username); //open the home screen
                }
            }
        });
    }

}
