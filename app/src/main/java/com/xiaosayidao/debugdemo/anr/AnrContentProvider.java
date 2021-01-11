package com.xiaosayidao.debugdemo.anr;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class AnrContentProvider extends ContentProvider {
    public static final String TAG = "AnrContentProvider";
    public static final int TIME_OUT = 5000;

    public boolean onCreate() {
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        AnrUtils.waitTime(10000, " query ");
        return null;
    }

    public String getType(Uri uri) {
        AnrUtils.waitTime(10000, " getType ");
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
