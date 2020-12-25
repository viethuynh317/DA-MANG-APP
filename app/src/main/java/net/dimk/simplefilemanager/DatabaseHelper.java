package net.dimk.simplefilemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "REGISTER_USER";
    private static final String TABLE_NAME = "User";
    private static final String ID = "Id";
    private static final String USERNAME = "UserName";
    private static final String PASSWORD = "Password";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY," + USERNAME
                + " TEXT NOT NULL UNIQUE," + PASSWORD + " TEXT NOT NULL" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
     onCreate(db);
    }

    public int getUserCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(USERNAME, user.getUserName());
        value.put(PASSWORD, user.getPassword());
        db.insert(TABLE_NAME, null, value);
        db.close();
    }
    public ArrayList<User> getAllUser(String userName){
        ArrayList<User> userArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        if(userName != null) {
            selectQuery += " WHERE UserName = '" + userName + "'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setUserName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                userArrayList.add(user);
            }while (cursor.moveToNext());
        }
        return userArrayList;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

}
