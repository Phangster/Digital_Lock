package com.bryanphang.sutd_digital_lock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase readableDb;
    private static SqliteHelper sqliteHelper;
    private SQLiteDatabase writeableDb;

    //DATABASE NAME
    public static final String DATABASE_NAME = "locklock.com";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ACCESS = "access";
    public static final String TABLE_LOCK = "lock";

    //TABLE COLUMNS

    //User table
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //Access table
    public static final String KEY_ACCESSTABLE_ID = "id";
    public static final String KEY_FINDBY_NAME = "findname";
    public static final String KEY_LOCK_NUM = "locknum";
    public static final String KEY_DATETIME_FROM = "datetimefrom";
    public static final String KEY_DATETIME_TO = "datetimeto";

    //Lock table
    public static final String KEY_LOCKTABLE_ID = "id";
    public static final String KEY_OWNER_NAME = "ownername";
    public static final String KEY_PROPERTY = "property";
    public static final String KEY_LOCK_DT_FROM = "lockdtfrom";
    public static final String KEY_LOCK_DT_TO = "lockdtto";
    public static final String KEY_LOCK_PASSWORD = "lockpassword";



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
            + KEY_ACCESSTABLE_ID + " INTEGER PRIMARY KEY, "
            + KEY_FINDBY_NAME + " TEXT, "
            + KEY_LOCK_NUM + " TEXT, "
            + KEY_DATETIME_FROM + " TEXT, "
            + KEY_DATETIME_TO + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_LOCK = " CREATE TABLE " + TABLE_LOCK
            + " ( "
            + KEY_LOCKTABLE_ID + " INTEGER PRIMARY KEY, "
            + KEY_OWNER_NAME + " TEXT, "
            + KEY_PROPERTY + " TEXT, "
            + KEY_LOCK_DT_FROM + " TEXT, "
            + KEY_LOCK_DT_TO + " TEXT, "
            + KEY_LOCK_PASSWORD + " TEXT "
            + " ) ";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_ACCESS);
        sqLiteDatabase.execSQL(SQL_TABLE_LOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_ACCESS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_LOCK);

    }

    //Populate database of lock upon login
    public void populateData(){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_OWNER_NAME, "Jin Kiat");
        initialValues.put(KEY_PROPERTY, "Jin Kiat's Mansion");
        initialValues.put(KEY_LOCK_DT_FROM, "16th December 2018");
        initialValues.put(KEY_LOCK_DT_TO, "16th January 2019");
        initialValues.put(KEY_LOCK_PASSWORD, "hello world");
        db.insert(TABLE_LOCK, null, initialValues);
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

    /* FOR ACCESS TABLE */

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_ACCESS, null);
        return result;
    }

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
        String dataid = null;

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

        //id
        int dataidIndex = cursor.getColumnIndex(KEY_ACCESSTABLE_ID);
        dataid = cursor.getString(dataidIndex);

        return new CharaData(name, lockid, fromDate, toDate, dataid);
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

    public int deleteRowByTableID(String id) {
        if (writeableDb == null) {
            writeableDb = getWritableDatabase();
        }
        String WHERE_CLAUSE = KEY_ACCESSTABLE_ID + " = ?";
        String[] WHERE_ARGS = {id};
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
        private String dataid;

        public CharaData(String name, String lockid, String fromDate, String toDate, String dataid) {
            this.name = name;
            this.lockid = lockid;
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.dataid = dataid;
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

        public String getDataid() {
            return dataid;
        }
    }

    public Cursor getAllDataLock(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_LOCK, null);
        return result;
    }

    public LockData queryOneRowLock(int position){
        if (readableDb == null) {
            readableDb = getReadableDatabase();
        }
        Cursor cursor = getAllDataLock();
        return getDataFromCursorLock(position, cursor);
    }

    public long queryNumRowsLock(){
        if (readableDb == null) {
            readableDb = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(readableDb, TABLE_LOCK);
    }

    private LockData getDataFromCursorLock(int position, Cursor cursor){
//        String lockid = null;
        String name = null;
        String property = null;
        String fromDate = null;
        String toDate = null;
        String password = null;

        //move to given row
        cursor.moveToPosition(position);

        if (cursor.moveToFirst()) {
            //name
            int ownerNameIndex = cursor.getColumnIndex(KEY_OWNER_NAME);
            //for text data type: cursor.getString
            name = cursor.getString(ownerNameIndex);

            //lock ID
            int propertyIndex = cursor.getColumnIndex(KEY_PROPERTY);
            property = cursor.getString(propertyIndex);

            //fromDate
            int lockFromDateIndex = cursor.getColumnIndex(KEY_LOCK_DT_FROM);
            fromDate = cursor.getString(lockFromDateIndex);

            //toDate
            int lockToDateIndex = cursor.getColumnIndex(KEY_LOCK_DT_TO);
            toDate = cursor.getString(lockToDateIndex);

            //password
            int passwordIndex = cursor.getColumnIndex(KEY_LOCK_PASSWORD);
            password = cursor.getString(passwordIndex);
        }


        return new LockData(name, property, fromDate, toDate, password);
    }

    static class LockData {
        private String ownername;
        private String property;
        private String lockdtfrom;
        private String lockdtto;
        private String keylockpassword;

        public LockData(String ownername, String property, String lockdtfrom, String lockdtto, String keylockpassword) {
            this.ownername = ownername;
            this.property = property;
            this.lockdtfrom = lockdtfrom;
            this.lockdtto = lockdtto;
            this.keylockpassword = keylockpassword;
        }

        public String getName() {
            return ownername;
        }

        public String getProperty() {
            return property;
        }

        public String getFromDate() {
            return lockdtfrom;
        }

        public String getToDate() {
            return lockdtto;
        }

        public String getKeylockpassword() {
            return keylockpassword;
        }
    }
}
