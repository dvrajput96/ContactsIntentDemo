package com.example.deepak.contactsintentdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Created by Deepak on 07-Apr-18.
 */

public class Boast {
    private static volatile Boast globalBoast = null;

    private Toast internalToast;

    /**
     * initialize toast object
     */
    private Boast(Toast toast) {
        // null check
        if (toast == null) {
            throw new NullPointerException(
                    "Boast.Boast(Toast) requires a non-null parameter.");
        }

        internalToast = toast;
    }

    /**
     * toast with duration , String msg
     */
    @SuppressLint("ShowToast")
    public static Boast makeText(Context context, CharSequence text,
                                 int duration) {
        return new Boast(Toast.makeText(context, text, duration));
    }

    /**
     * toast with duration , int msg resource
     */
    @SuppressLint("ShowToast")
    public static Boast makeText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        return new Boast(Toast.makeText(context, resId, duration));
    }

    /**
     * toast with String msg and duration : LENGTH_SHORT
     */
    @SuppressLint("ShowToast")
    public static Boast makeText(Context context, CharSequence text) {
        return new Boast(Toast.makeText(context, text, Toast.LENGTH_SHORT));
    }

    /**
     * toast with int msg  resource and duration : LENGTH_SHORT
     */
    @SuppressLint("ShowToast")
    public static Boast makeText(Context context, int resId)
            throws Resources.NotFoundException {
        return new Boast(Toast.makeText(context, resId, Toast.LENGTH_SHORT));
    }
    /**
     * toast with String msg and duration : LENGTH_SHORT
     */
    public static void showText(Context context, CharSequence text, int duration) {
        Boast.makeText(context, text, duration).show();
    }

    /**
     * toast with int msg resource and duration
     * @throws Resources.NotFoundException
     */
    public static void showText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        Boast.makeText(context, resId, duration).show();
    }

    /**
     * toast with character sequence msg  and duration : LENGTH_SHORT
     */
    public static void showText(Context context, CharSequence text) {
        Boast.makeText(context, "" + text, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast with int msg resourcee and duration : LENGTH_SHORT
     */
    public static void showText(Context context, int resId)
            throws Resources.NotFoundException {
        Boast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * cancel toast
     */
    public void cancel() {
        internalToast.cancel();
    }

    /**
     * show toast
     */
    public void show() {
        show(true);
    }

    public void show(boolean cancelCurrent) {
        // cancel current
        if (cancelCurrent && (globalBoast != null)) {
            globalBoast.cancel();
        }

        // save an instance of this current notification
        setGlobalBoast(this);

        internalToast.show();
    }

    private static void setGlobalBoast(Boast boast) {
        globalBoast = boast;
    }

}