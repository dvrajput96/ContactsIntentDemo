package com.example.deepak.contactsintentdemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.deepak.contactsintentdemo.database.Dao.ContactsDao;
import com.example.deepak.contactsintentdemo.model.ClsContacts;

/**
 * Created by Deepak on 07-Apr-18.
 */
@Database(entities = {ClsContacts.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase INSTANCE;

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "word_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    public abstract ContactsDao contactsDao();

}
