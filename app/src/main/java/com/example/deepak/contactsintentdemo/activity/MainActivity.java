package com.example.deepak.contactsintentdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.deepak.contactsintentdemo.R;
import com.example.deepak.contactsintentdemo.adapter.ContactsAdapter;
import com.example.deepak.contactsintentdemo.database.Dao.ContactsDao;
import com.example.deepak.contactsintentdemo.database.MyDatabase;
import com.example.deepak.contactsintentdemo.model.ClsContacts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_CONTACT = 101;

    @BindView(R.id.btnAddContacts)
    FloatingActionButton btnAddContacts;

    @BindView(R.id.recyclerviewContacts)
    RecyclerView recyclerViewContacts;

    private String contactName;
    private String contactNumber;
    private String contactPhoto;


    private ContactsAdapter contactsAdapter;

    private List<ClsContacts> clsContactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * initialize views
     */

    private void initView() {

        clsContactsList = new ArrayList<>();
        contactsAdapter = new ContactsAdapter(clsContactsList, MainActivity.this);
        initializeRecyclerView();
        showSavedDataFromRoomDatabase();

        btnAddContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContacts();
            }
        });

    }

    private void showSavedDataFromRoomDatabase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // MyDatabase myDatabase = Room.inMemoryDatabaseBuilder(MainActivity.this, MyDatabase.class).build(); // Get an Instance of Database class //defined above
                MyDatabase myDatabase = MyDatabase.getDatabase(MainActivity.this);
                ContactsDao contactsDao = myDatabase.contactsDao();// Get DAO object
                clsContactsList = contactsDao.getAll();
                contactsAdapter.add(clsContactsList);
                recyclerViewContacts.setAdapter(contactsAdapter);
            }
        }).start();
    }

    private void openContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_CONTACT:
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            contactNumber = phones.getString(phones.getColumnIndex("data1"));
                        }
                        contactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        contactPhoto = c.getString(c.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                        Log.e("TAG", "onActivityResult: " + contactName + "" + contactNumber + "" + contactPhoto);

                    }
                    ClsContacts clsContacts = new ClsContacts();
                    clsContacts.setContactName(contactName);
                    clsContacts.setContactNumber(contactNumber);
                    if (contactPhoto != null) {
                        clsContacts.setPic(contactPhoto);
                    }
                    clsContactsList.add(clsContacts);

                    // Now you have the phone number
                    contactsAdapter.add(clsContactsList);
                    insertDataIntoRoomDatabase(clsContacts);
                    recyclerViewContacts.setAdapter(contactsAdapter);

                }
                break;
        }
    }

    /**
     * @param clsContacts insert data into room database
     */

    private void insertDataIntoRoomDatabase(final ClsContacts clsContacts) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // MyDatabase myDatabase = Room.inMemoryDatabaseBuilder(MainActivity.this, MyDatabase.class).build(); // Get an Instance of Database class //defined above
                MyDatabase myDatabase1 = MyDatabase.getDatabase(MainActivity.this);
                ContactsDao contactsDao = myDatabase1.contactsDao();// Get DAO object
                // contactsDao.insertAll(clsContactsList);
                contactsDao.insert(clsContacts);
            }
        }).start();

    }


    /**
     * initialize recycler view
     */
    private void initializeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewContacts.setLayoutManager(layoutManager);
        recyclerViewContacts.setHasFixedSize(true);
    }

    /**
     * @param position long click on recyclerview to delete the contact through it's position
     */

    public void onLongClick(final int position) {
        // Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?");
        builder.setTitle("Delete");
        builder.setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MyDatabase myDatabase = MyDatabase.getDatabase(MainActivity.this);
                                ContactsDao contactsDao = myDatabase.contactsDao();// Get DAO object
                                contactsAdapter.removeAt(position);
                                contactsDao.delete(clsContactsList.get(position));
                            }
                        }).start();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }
}
