package com.example.deepak.contactsintentdemo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Deepak on 07-Apr-18.
 */
@Entity
public class ClsContacts implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "contactName")
    private String contactName;

    @ColumnInfo(name = "ContactNumber")
    private String contactNumber;

   /* @ColumnInfo(name = "image_url")
    private Uri photo; */

    @ColumnInfo(name = "image_url")
    private String pic;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
