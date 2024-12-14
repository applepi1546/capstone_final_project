package com.example.inventorymanagerapp;
import android.Manifest;
import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class settings extends Activity {
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    EditText number;
    private Button buttonLogout, buttonDeleteAccount, buttonSaveChanges;
    private Switch switchSMS;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String SWITCH_STATE_KEY = "switchState";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstTime = !prefs.contains(SWITCH_STATE_KEY);
        boolean switchState = prefs.getBoolean(SWITCH_STATE_KEY, false); // get switch state

        String username = getIntent().getStringExtra("username"); // get username

        ImageView back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain(v, username);
            } // back button
        });
        buttonLogout = findViewById(R.id.logoutButton);
        buttonDeleteAccount = findViewById(R.id.deleteButton);
        buttonSaveChanges = findViewById(R.id.saveButton);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin(v);
            } // logout button
        });

        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount(v, username);
            } // delete account button
        });

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges(v, username);
            } // save changes button
        });
        number = findViewById(R.id.editTextPhone);
        switchSMS = findViewById(R.id.switch1);

        if (isFirstTime) // if first time
        {
            switchState = false;
            saveSwitchState(switchState);
            switchSMS.setChecked(switchState); // set switch state
        }
        else
        {
            switchSMS.setChecked(switchState);
            number.setVisibility(switchState ? View.VISIBLE : View.INVISIBLE);
        }

        LoginDB loginDB = new LoginDB(settings.this);
        String phone = loginDB.getPhoneNumber(username);
        number.setText(phone);

        switchSMS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // switch SMS
                number.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                saveSwitchState(isChecked);
                if (isChecked) // if checked
                {
                    LoginDB loginDB = new LoginDB(settings.this);
                    String phone = loginDB.getPhoneNumber(username);
                    number.setText(phone); // show phone number
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    private void saveChanges(View v, String username) { // save changes
        if (switchSMS.isChecked()) {
            LoginDB loginDB = new LoginDB(this);
            loginDB.updatePhoneNumber(username, number.getText().toString()); // update phone number
            if (number.getText().toString().isEmpty())  // if phone number is empty
            {
                Toast.makeText(this, "Phone Number can not be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (number.getText().toString().length() != 10) // if phone number is not 10 digits
            {
                Toast.makeText(this, "Phone Number must be 10 digits", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Phone Number Saved", Toast.LENGTH_SHORT).show(); // save phone number
            saveSwitchState(true);
            openMain(v, username);
        }
        else // if not checked
        {
            LoginDB loginDB = new LoginDB(this);
            loginDB.updatePhoneNumber(username, ""); // update phone number to empty
            Toast.makeText(this, "Phone Number Deleted From System", Toast.LENGTH_SHORT).show();
            saveSwitchState(false);
            openMain(v, username);
        }
    }

    private void deleteAccount(View v, String username) { // delete account
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete your account?"); // ask user if they want to delete account
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // if yes
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginDB loginDB = new LoginDB(settings.this);
                InventoryDB inventoryDB = new InventoryDB(settings.this);
                loginDB.deleteUser(username); //    delete user
                inventoryDB.deleteInventory(username); // delete inventory
                openLogin(v); // open login
            }
        });
        builder.setNegativeButton("No", null); // if no
        builder.show();
    }

    private void openLogin(View v) { // sign out
        Intent intent = new Intent(this, MainActivity.class); // open login
        startActivity(intent);
    }

    public void openMain(View view, String _user) { // open home screen
        InventoryDB _inventory;
        Intent intent = new Intent(this, home_screen.class);
        intent.putExtra("username", _user);
        startActivity(intent);
    }
    private void saveSwitchState(boolean isChecked) { // save switch state
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SWITCH_STATE_KEY, isChecked);
        editor.apply();
    }


}
