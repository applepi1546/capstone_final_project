package com.example.inventorymanagerapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class new_item extends Activity {

    private EditText editTextItemName;
    private EditText editTextItemQuantity;
    private Button addItemButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        String username = getIntent().getStringExtra("username");
        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemQuantity = findViewById(R.id.editTextItemQuantity);
        addItemButton = findViewById(R.id.addItemButton);

        addItemButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) // add item button
            {
                String itemName = editTextItemName.getText().toString().trim();
                String itemQuantity = editTextItemQuantity.getText().toString().trim();

                if (itemName.isEmpty() || itemQuantity.isEmpty()) // if empty
                {
                    Toast.makeText(new_item.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                addToDatabase(username, itemName, Integer.parseInt(itemQuantity)); // add to database
            }
        });
        ImageView back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain(v, username);
            } // back button
        });
    }

    public void openMain(View view, String _user) //open the home screen
    {
        InventoryDB _inventory;
        Intent intent = new Intent(this, home_screen.class);
        intent.putExtra("username", _user);
        startActivity(intent);
    }

    private void addToDatabase(String user, String itemName, int quantity) { // add to database
        InventoryDB inventoryDB = new InventoryDB(this);

        inventoryDB.addItem(user, itemName, quantity);

        Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
        openMain(null, user);
        finish();
    }



}
