package com.budgetmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;

public class DataBaseUtils extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.budgetmanagementsystem/databases/";
    private static String DB_NAME = "database"; //yourDB file name
    private SQLiteDatabase db;
    private Context context;

    //region OVERRIDES
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();

        super.close();
    }
    //endregion

    // CONSTRUCTOR
    public DataBaseUtils(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    //region DATABASE UTILITY FUNCTIONS
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.
        }
        if (checkDB != null) {

            checkDB.close();
        }
        return checkDB != null;
    }

    public void createDataBase() throws Exception {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }
    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void copyDataBase() throws IOException {

        //Open your local db as the input file
        InputStream myInput = context.getAssets().open(DB_NAME + ".db");

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db file that was created by DBHelper as an output file
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams or the output file
        myOutput.flush();//write to output file
        myOutput.close();
        myInput.close();

    }

    public static SQLiteDatabase getDB(Context context)
    {
        DataBaseUtils dbUtil = new DataBaseUtils(context);
        try {
            dbUtil.createDataBase();}
        catch(Exception ex){
            ex.printStackTrace();}
        try{
            dbUtil.openDataBase();}
        catch(SQLException ex){
            ex.printStackTrace();}

        SQLiteDatabase db = dbUtil.getWritableDatabase();

        return db;
    }
    //endregion

    public static boolean UserExists(Context context, String username, String password)
    {
        return GetUserByLogin(context, username, password) != null;
    }

    public static User GetUserByLogin(Context context, String username, String password)
    {
        SQLiteDatabase db = getDB(context);

        // Hash the password before using DB
        password = Hash.hashPassword(password).get();

        String table = "user";
        String[] cols = {"userid", "username", "password"};
        String select = "username=? AND password=?";
        String[] selArgs = {username, password};

        Cursor cursor = db.query(table, cols, select, selArgs, null, null, null);
        User user = null;
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            user = new User();
            int i = 0;
            user.UserID = cursor.getInt(i++);
            user.Username = cursor.getString(i++);
            user.Password = cursor.getString(i++);
        }
        cursor.close();
        db.close();

        return user;
    }

    public static User GetUserByID(Context context, long userid)
    {
        SQLiteDatabase db = getDB(context);

        String table = "user";
        String[] cols = {"userid", "username", "password"};
        String select = "userid=?";
        String[] selArgs = {userid+""};

        Cursor cursor = db.query(table, cols, select, selArgs, null, null, null);
        cursor.moveToFirst();
        User user = new User();
        int i = 0;
        user.UserID = cursor.getInt(i++);
        user.Username = cursor.getString(i++);
        user.Password = cursor.getString(i++);
        cursor.close();
        db.close();

        return user;
    }

    public static long SaveUser(Context context, User user)
    {
        SQLiteDatabase db = getDB(context);
        boolean exists = user.UserID > 0;

        // Hash the password before using DB
        user.Password = Hash.hashPassword(user.Password).get();

        String table = "user";
        ContentValues values = new ContentValues();
        values.put("username", user.Username);
        values.put("password", user.Password);
        String where = "userid=?";
        String[] args = {user.UserID+""};

        if(exists)
        {
            db.update(table, values, where, args);
            db.close();
            return user.UserID;
        }
        else
        {
            long userid = db.insert(table, null, values);
            db.close();
            return userid;
        }
    }

    public static Goal GetGoalByID(Context context, long userid)
    {
        SQLiteDatabase db = getDB(context);

        String table = "goal";
        String[] cols = {"userid", "startdate", "startbalance", "enddate", "endbalance"};
        String select = "userid=?";
        String[] selArgs = {userid+""};

        Cursor cursor = db.query(table, cols, select, selArgs, null, null, null);
        cursor.moveToFirst();
        Goal goal = new Goal();
        int i = 0;
        if(cursor.getCount() > 0)
        {
            goal.UserID = cursor.getInt(i++);
            goal.StartDate = Date.valueOf(cursor.getString(i++));
            goal.StartBalance = cursor.getDouble(i++);
            goal.EndDate = Date.valueOf(cursor.getString(i++));
            goal.EndBalance = cursor.getDouble(i++);
        }
        else
        {
            return null;
        }
        cursor.close();
        db.close();

        return goal;
    }

    public static long SaveGoal(Context context, Goal goal)
    {
        SQLiteDatabase db = getDB(context);

        String table = "goal";
        String columns[] = {"userid"};
        ContentValues values = new ContentValues();
        values.put("startdate", goal.StartDate.toString());
        values.put("startbalance", goal.StartBalance);
        values.put("enddate", goal.EndDate.toString());
        values.put("endbalance", goal.EndBalance);
        String where = "userid=?";
        String[] args = {goal.UserID+""};

        Cursor cursor = db.query(table, columns, where, args, null, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        if(exists)
        {
            db.update(table, values, where, args);
            db.close();
            return goal.UserID;
        }
        else
        {
            long userid = db.insert(table, null, values);
            db.close();
            return userid;
        }
    }

    public static Transaction GetTransactionByID(Context context, long transactionID)
    {
        SQLiteDatabase db = getDB(context);

        String table = "[transaction]";
        String[] cols = {"transactionid", "userid", "transactionname", "transactionamount", "transactiondate"};
        String select = "transactionid=?";
        String[] selArgs = {transactionID+""};

        Cursor cursor = db.query(table, cols, select, selArgs, null, null, null);
        cursor.moveToFirst();
        Transaction transaction = new Transaction();
        int i = 0;
        transaction.TransactionID = cursor.getInt(i++);
        transaction.UserID = cursor.getInt(i++);
        transaction.TransactionName = cursor.getString(i++);
        transaction.TransactionAmount = cursor.getDouble(i++);
        transaction.TransactionDate = Date.valueOf(cursor.getString(i++));
        cursor.close();
        db.close();

        return transaction;
    }

    public static Transaction[] GetTransactionsByUser(Context context, long userID)
    {
        SQLiteDatabase db = getDB(context);

        String table = "[transaction]";
        String[] cols = {"transactionid", "userid", "transactionname", "transactionamount", "transactiondate"};
        String select = "userid=?";
        String[] selArgs = {userID+""};
        String orderBy = "transactiondate";

        Cursor cursor = db.query(table, cols, select, selArgs, null, null, orderBy);
        cursor.moveToFirst();

        Transaction[] transactions = new Transaction[cursor.getCount()];

        if(cursor.getCount() == 0)
            return transactions;

        do
        {
            Transaction transaction = new Transaction();
            int i = 0;
            transaction.TransactionID = cursor.getInt(i++);
            transaction.UserID = cursor.getInt(i++);
            transaction.TransactionName = cursor.getString(i++);
            transaction.TransactionAmount = cursor.getDouble(i++);
            transaction.TransactionDate = Date.valueOf(cursor.getString(i++));

            transactions[cursor.getPosition()] = transaction;
        } while(cursor.moveToNext());

        cursor.close();
        db.close();

        return transactions;
    }

    public static long SaveTransaction(Context context, Transaction trans)
    {
        SQLiteDatabase db = getDB(context);
        boolean exists = trans.TransactionID > 0;

        String table = "[transaction]";
        ContentValues values = new ContentValues();
        values.put("userid", trans.UserID);
        values.put("transactionname", trans.TransactionName);
        values.put("transactionamount", trans.TransactionAmount);
        values.put("transactiondate", trans.TransactionDate.toString());
        String where = "transactionid=?";
        String[] args = {trans.TransactionID+""};

        if(exists)
        {
            db.update(table, values, where, args);
            db.close();
            return trans.TransactionID;
        }
        else
        {
            long transid = db.insert(table, null, values);
            db.close();
            return transid;
        }
    }

    public static void DeleteTransaction(Context context, Transaction trans)
    {
        SQLiteDatabase db = getDB(context);

        String table = "[transaction]";
        String where = "transactionid=?";
        String[] args = {trans.TransactionID+""};

        db.delete(table, where, args);

        db.close();
    }
}
