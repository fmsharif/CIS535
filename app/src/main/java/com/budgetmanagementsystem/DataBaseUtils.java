package com.budgetmanagementsystem;

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

    public static User GetUserByID(Context context, int userid)
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

    public static Goal GetGoalByID(Context context, int userid)
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
        goal.UserID = cursor.getInt(i++);
        goal.StartDate = Date.valueOf(cursor.getString(i++));
        goal.StartBalance = cursor.getDouble(i++);
        goal.EndDate = Date.valueOf(cursor.getString(i++));
        goal.EndBalance = cursor.getDouble(i++);
        cursor.close();
        db.close();

        return goal;
    }

    public static Transaction GetTransactionByID(Context context, int transactionID)
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
}
