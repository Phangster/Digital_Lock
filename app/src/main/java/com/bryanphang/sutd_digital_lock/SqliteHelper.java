package com.bryanphang.sutd_digital_lock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SqliteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase readableDb;
    private static SqliteHelper sqliteHelper;
    private SQLiteDatabase writeableDb;

    //DATABASE NAME
    public static final String DATABASE_NAME = "loopwiki.com";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ACCESS = "access";


    //TABLE COLUMNS

    //User table
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //Access table
    public static final String KEY_FINDBY_NAME = "findname";
    public static final String KEY_LOCK_NUM = "locknum";
    public static final String KEY_DATETIME_FROM = "datetimefrom";
    public static final String KEY_DATETIME_TO = "datetimeto";




    //SQL for creating table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_ACCESS = " CREATE TABLE " + TABLE_ACCESS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_FINDBY_NAME + " TEXT, "
            + KEY_LOCK_NUM + " TEXT, "
            + KEY_DATETIME_FROM + " TEXT, "
            + KEY_DATETIME_TO + " TEXT"
            + " ) ";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_ACCESS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_ACCESS);

    }


    //USER TABLE CALLS

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }


    //ACCESS TABLE CALLS

    public void addAccess(Access access) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        values.put(KEY_FINDBY_NAME, access.findname);
        values.put(KEY_LOCK_NUM, access.locknum);
        values.put(KEY_DATETIME_FROM, access.datetimefrom);
        values.put(KEY_DATETIME_TO, access.datetimeto);

        // insert row
        db.insert(TABLE_ACCESS, null, values);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_ACCESS, null);
        return result;
    }

    /*ADDED BY JIREH */

    static SqliteHelper createSqliteHelper(Context context) {
        if (sqliteHelper == null) {
            sqliteHelper = new SqliteHelper(context);
        }
        return sqliteHelper;
    }

    public CharaData queryOneRow(int position){
        if (readableDb == null) {
            readableDb = getReadableDatabase();
        }
        Cursor cursor = getAllData();
        return getDataFromCursor(position, cursor);
    }

    public long queryNumRows(){
        if (readableDb == null) {
            readableDb = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(readableDb, TABLE_ACCESS);
    }

    private CharaData getDataFromCursor(int position, Cursor cursor){

        String name = null;
        String lockid = null;
        String fromDate = null;
        String toDate = null;

        //move to given row
        cursor.moveToPosition(position);

        //name
        int nameIndex = cursor.getColumnIndex(KEY_FINDBY_NAME);
        //for text data type: cursor.getString
        name = cursor.getString(nameIndex);

        //lock ID
        int lockidIndex = cursor.getColumnIndex(KEY_LOCK_NUM);
        lockid = cursor.getString(lockidIndex);

        //fromDate
        int fromDateIndex = cursor.getColumnIndex(KEY_DATETIME_FROM);
        fromDate = cursor.getString(fromDateIndex);

        //toDate
        int toDateIndex = cursor.getColumnIndex(KEY_DATETIME_TO);
        toDate = cursor.getString(toDateIndex);

        return new CharaData(name, lockid, fromDate, toDate);
    }

    public int deleteOneRow(String name) {
        if (writeableDb == null) {
            writeableDb = getWritableDatabase();
        }
        String WHERE_CLAUSE = KEY_FINDBY_NAME + " = ?";
        String[] WHERE_ARGS = {name};
        int rowsDeleted = writeableDb.delete(TABLE_ACCESS, WHERE_CLAUSE, WHERE_ARGS);
        return rowsDeleted;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ACCESS, "ID = ?", new String[] {id});
    }

    static class CharaData {
        private String name;
        private String lockid;
        private String fromDate;
        private String toDate;

        public CharaData(String name, String lockid, String fromDate, String toDate) {
            this.name = name;
            this.lockid = lockid;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        public String getName() {
            return name;
        }

        public String getLockid() {
            return lockid;
        }

        public String getFromDate() {
            return fromDate;
        }

        public String getToDate() {
            return toDate;
        }
    }
}