
package com.managmentdic.adapters;

/**
 * Created by Payam on 8/23/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataAdapter {
    SQLiteDatabase database_ob;
    DataBaseHelper openHelper_ob;
    Context context;

    public DataAdapter(Context c) {
        context = c;
    }

    public DataAdapter opnToRead() {
        openHelper_ob = new DataBaseHelper(context);
        database_ob = openHelper_ob.getReadableDatabase();
        return this;

    }

    public DataAdapter opnToWrite() {
        openHelper_ob = new DataBaseHelper(context);
        database_ob = openHelper_ob.getWritableDatabase();
        return this;

    }

    public void Close() {
        database_ob.close();
    }

    public Cursor selectall() {
        String[] cols = {"English", "Farsi"};
        opnToRead();
        Cursor c = database_ob.query("Vocab", cols, null,
                null, null, null, null);
        return c;

    }
    public Cursor LikeQuery(String search) {
        String[] cols = {"English", "Farsi"};
        String[] args = {search + "%", search + "%"};
        opnToRead();
        Cursor c = database_ob.query("Vocab", cols, "English LIKE ? or Farsi LIKE ?",
                args, null, null, null);
         return c;

    }


}