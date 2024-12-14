package com.example.inventorymanagerapp;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.*;
import android.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class home_screen extends Activity {

    private FloatingActionButton addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen); // layout file
        String username = getIntent().getStringExtra("username"); // get username
        loadInventoryData(username); // load inventory data
        addItemButton = findViewById(R.id.newItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) // add item button
            {
                Intent intent = new Intent(home_screen.this, new_item.class); // new item activity
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        ImageView settings = findViewById(R.id.settingsButton); // settings button
        settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) // settings button
            {
                Intent intent = new Intent(home_screen.this, settings.class); // settings activity
                intent.putExtra("username", username);
                startActivity(intent); // start activity
            }
        });
    }

    private void loadInventoryData(String username) // load inventory data
    {
        InventoryDB inventoryDB = new InventoryDB(this);
        final Cursor[] cursor = {inventoryDB.getInventory(username)}; // get inventory
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        if (cursor[0].getCount() == 0) // if no data
        {
            TableRow emptyRow = new TableRow(this);
            tableLayout.addView(emptyRow);
        }
        else
        {
            while (cursor[0].moveToNext()) // while there is data
            {

                int itemId = cursor[0].getInt(cursor[0].getColumnIndexOrThrow(InventoryDB.COLUMN_ID));
                String itemName = cursor[0].getString(cursor[0].getColumnIndexOrThrow(InventoryDB.COLUMN_ITEM_NAME));
                String itemQuantity = cursor[0].getString(cursor[0].getColumnIndexOrThrow(InventoryDB.COLUMN_QUANTITY));

                TableRow row = new TableRow(this);

                TextView itemNameView = new TextView(this);
                itemNameView.setText(itemName);
                itemNameView.setGravity(Gravity.CENTER);
                row.addView(itemNameView);

                TextView itemQuantityView = new TextView(this);
                itemQuantityView.setText(itemQuantity);
                itemQuantityView.setGravity(Gravity.CENTER);
                row.addView(itemQuantityView);
                float weightSum = 4;

                TableRow.LayoutParams PbuttonParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
                TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2);


                Button minusButton = new Button(this); // minus button
                minusButton.setLayoutParams(PbuttonParams);
                minusButton.setText("-");
                minusButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ED2F4C")));
                minusButton.setTag(itemId);
                row.addView(minusButton);
                final Cursor finalCursor = cursor[0];
                minusButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) // minus button
                    {
                        int itemId = (int) v.getTag();
                        int quantity = Integer.parseInt(itemQuantityView.getText().toString());
                        String itemName = itemNameView.getText().toString();
                            if (quantity >= 0) // if quantity is greater than 0
                            {
                                int new_quantity = quantity - 1; // decrease quantity
                                inventoryDB.updateItem(itemId, username, itemName, new_quantity); // update item
                                itemQuantityView.setText(String.valueOf(new_quantity)); // set quantity
                                tableLayout.requestLayout(); // request layout
                            }
                            if (quantity < 3) // if quantity is less than 3
                            {
                                sendSmsMessage(itemName, quantity + 1); // send sms message
                            }
                    }
                });

                Button plusButton = new Button(this); // plus button
                plusButton.setLayoutParams(PbuttonParams);
                plusButton.setText("+");
                plusButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#63D400")));
                plusButton.setTag(itemId);
                row.addView(plusButton);
                plusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int itemId = (int) v.getTag();
                        int quantity = Integer.parseInt(itemQuantityView.getText().toString());
                        String itemName = itemNameView.getText().toString();
                        if (quantity >= 0) // if quantity is greater than or equal 0
                        {
                            int new_quantity = quantity + 1; // increase quantity
                            inventoryDB.updateItem(itemId, username, itemName, new_quantity); // update item
                            itemQuantityView.setText(String.valueOf(new_quantity)); // set quantity
                            tableLayout.requestLayout(); // request layout
                        }

                    }

                });

                Button deleteButton = new Button(this); // delete button
                deleteButton.setLayoutParams(buttonParams);
                deleteButton.setText("Delete");
                deleteButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                row.addView(deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(home_screen.this); // alert dialog
                        builder.setTitle("Delete Item");
                        builder.setMessage("Are you sure you want to delete this item?"); //ask user if they want to delete item
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) // if yes
                            {
                                if (cursor[0] != null && cursor[0].moveToFirst()) {
                                    int itemId = cursor[0].getInt(cursor[0].getColumnIndexOrThrow(InventoryDB.COLUMN_ID));
                                    try // try to delete item
                                    {
                                        inventoryDB.deleteItem(itemId);
                                        tableLayout.removeView(row);
                                    }
                                    catch (Exception e) // catch exception
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        builder.setNegativeButton("No", null); // if no
                        builder.show();
                    }
                });

                row.setWeightSum(weightSum);
                tableLayout.addView(row);
            }
        }

    }

    private void sendSmsMessage(String item, int threshold) // send sms message
    {
        SmsManager smsManager= getApplicationContext().getSystemService(SmsManager.class);
        LoginDB loginDB = new LoginDB(home_screen.this);
        String username = getIntent().getStringExtra("username");
        String phoneNumber = loginDB.getPhoneNumber(username);
        if (phoneNumber == null || phoneNumber.isEmpty()) // if phone number is empty
        {
            return;
        }
        else // send sms message
        {
        String messageFormat = "Item %s has fallen below %d items";
        @SuppressLint("DefaultLocale") String message = String.format(messageFormat, item, threshold);
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
    }



}
