package com.evolving.apploader.android.sdk.database;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Database Helper class for setting up the database
 * @Author Nandan Upadhyay
 */
 public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "evolsdkdatabase.sqlite";
    private static final int DB_VERSION = 1;

    public static String PRODUCT_TABLE = "ProvisionalOffer";
    public static String PRODUCT_TABLE_TEMP = "ProvisionalOffer_TEMP";
    private static String DB_PATH = null;
    private static DataBaseHelper sDbHelper;
    private static SQLiteDatabase sDbInstance = null;
    public static final String PRODUCT_TABLE_TEMP_COUNT ="productInfoTempCount" ;



    private static final String SQL_CREATE_PRODUCT_INFO_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRODUCT_TABLE
            + "(Type VARCHAR, Package VARCHAR, Label VARCHAR, Description VARCHAR, IconUrl VARCHAR, Url VARCHAR, Rating INTEGER, Developer VARCHAR , App_Insatlled VARCHAR ,Index_app VARCHAR)";


    private static final String SQL_CREATE_PRODUCT_INFO_TEMP_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRODUCT_TABLE_TEMP
            + "(Type VARCHAR, Package VARCHAR, Label VARCHAR, Description VARCHAR, IconUrl VARCHAR, Url VARCHAR, Rating INTEGER, Developer VARCHAR , App_Insatlled VARCHAR ,Index_app VARCHAR)";



    private static final String SQL_CREATE_PRODUCT_INFO_TEMP_TABLE_COUNT = "CREATE TABLE IF NOT EXISTS "
            + PRODUCT_TABLE_TEMP_COUNT
            + "(ProductCount INTEGER)";


    public static DataBaseHelper init(Context context) {
        try {
            Context applicationContext = context.getApplicationContext();
            sDbHelper = new DataBaseHelper(applicationContext, DB_VERSION);
            sDbInstance = sDbHelper.getWritableDatabase();
        } catch (SQLException sqe) {
            sDbInstance.close();
            /*
			 * If an exception occurs, the previous connection is closed and new
			 * connection is opened.
			 */
            sDbInstance = sDbHelper.getWritableDatabase();
        }
        return sDbHelper;
    }


    private void createDatabase(Context context) {
        boolean dbExists = databaseExists(context);
        if (!dbExists) {
            sDbInstance = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            sDbInstance.execSQL(SQL_CREATE_PRODUCT_INFO_TABLE);
            sDbInstance.execSQL(SQL_CREATE_PRODUCT_INFO_TEMP_TABLE);
            sDbInstance.execSQL(SQL_CREATE_PRODUCT_INFO_TEMP_TABLE_COUNT);
            sDbInstance.close();
        }
    }

    public static void createTemptable(Context context) {
            sDbInstance = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            sDbInstance.execSQL(SQL_CREATE_PRODUCT_INFO_TEMP_TABLE);
            sDbInstance.close();
    }

    public static SQLiteDatabase getSqliteDatabase() {
        if (sDbInstance == null) {
            throw new RuntimeException(
                    "DatabaseHelper.init() must be called before calling this method.");
        }
        return sDbInstance;
    }

    private DataBaseHelper(Context context, int databaseVersion) {
        super(context, DB_NAME, null, databaseVersion);
        sDbHelper = this;
        if (!databaseExists(context)) {
            try {
                createDatabase(context);
            } catch (Exception e) {
            }
        } else {
        }
    }


    /**
     * Check if the database already copied to the device.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean databaseExists(Context context) {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }


    public void deleteDatabase() {
        String outFileName = DB_PATH + DB_NAME;
        File databaseFile = new File(outFileName);
        try {
            databaseFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cursor executeSelectQuery(SQLiteDatabase db, String query, String[] selectionArgs) {
        return db.rawQuery(query, selectionArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }


}
