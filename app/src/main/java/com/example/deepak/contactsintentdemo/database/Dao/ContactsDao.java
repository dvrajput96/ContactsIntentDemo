package com.example.deepak.contactsintentdemo.database.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.deepak.contactsintentdemo.model.ClsContacts;

import java.util.List;

/**
 * Created by Deepak on 07-Apr-18.
 */
@Dao
public interface ContactsDao {

    @Query("SELECT * FROM ClsContacts")
    List<ClsContacts> getAll();

    /*@Query("SELECT * FROM Contacts WHERE name LIKE :name LIMIT 1")
    Contacts findByName(String name);*/

    @Insert
    void insertAll(List<ClsContacts> contacts);

    @Insert
    void insert(ClsContacts clsContacts);

    @Delete
    void delete(ClsContacts clsContacts);

    @Query("DELETE FROM ClsContacts WHERE uid = :uid")
    int delete(final int uid);

    @Update
    void update(ClsContacts... contacts);

    @Delete
    void delete(ClsContacts... contacts);

}
