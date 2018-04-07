package com.example.deepak.contactsintentdemo.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Deepak on 07-Apr-18.
 */

public class ClsContacts implements Serializable {

    private String contactName;
    private String contactNumber;
    private Uri photo;

    public ClsContacts(String contactName, String contactNumber, Uri photo) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.photo = photo;
    }

    public ClsContacts() {
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
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
}
