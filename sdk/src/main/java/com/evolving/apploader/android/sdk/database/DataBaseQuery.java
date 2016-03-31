package com.evolving.apploader.android.sdk.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.evolving.apploader.android.sdk.model.ProvisionOfferModel;
import com.evolving.apploader.android.sdk.model.ProvisionalOffer;

import java.util.ArrayList;

/**
 * Class to interact with Database via SQL Queries
 */
public class DataBaseQuery {

    public static void addProductToDataBase(ProvisionalOffer productInfo, Context ctx) {
        DataBaseHelper.init(ctx);
        String query = "INSERT INTO ProvisionalOffer (Type,Package,Label,Description,IconUrl,Url,Rating,Developer,App_Insatlled,Index_app)VALUES "
                + "(?,?,?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        SQLiteStatement statement = db.compileStatement(query);
        statement = bindValuesToStatement(productInfo, statement);
        statement.execute();
        statement.close();
    }


    private static SQLiteStatement bindValuesToStatement(ProvisionalOffer productInfo,
                                                         SQLiteStatement statement) {
        try {
            statement.bindString(1, productInfo.getmType());
            statement.bindString(2, productInfo.getmPackage());
            statement.bindString(3, productInfo.getmLabel());
            statement.bindString(4, productInfo.getmDescription());
            statement.bindString(5, productInfo.getmIconUrl());
            statement.bindString(6, productInfo.getmUrl());
            statement.bindDouble(7, productInfo.getmRating());
            statement.bindString(8, productInfo.getmDeveloper());
            statement.bindString(9,productInfo.getmIsAppInsatlled());
            statement.bindString(10,productInfo.getmIndex());
        } catch (Exception e) {
            //todo
        }
        return statement;
    }

    private static SQLiteStatement bindValuesToStatementTemp(ProvisionOfferModel productInfo,
                                                         SQLiteStatement statement) {
        try {
            statement.bindString(1, productInfo.getmType());
            statement.bindString(2, productInfo.getmPackage());
            statement.bindString(3, productInfo.getmLabel());
            statement.bindString(4, productInfo.getmDescription());
            statement.bindString(5, productInfo.getmIconUrl());
            statement.bindString(6, productInfo.getmUrl());
            statement.bindDouble(7, productInfo.getmRating());
            statement.bindString(8, productInfo.getmDeveloper());
            statement.bindString(9,productInfo.getmIsAppInsatlled());
            statement.bindString(10,productInfo.getmIndex());
        } catch (Exception e) {
            //todo
        }
        return statement;
    }
    public static ArrayList<ProvisionalOffer> getProvisionalOnlyUnInstalledApps(String appInstallStatus, Context context) {
        DataBaseHelper.init(context);
        ArrayList<ProvisionalOffer> productList = new ArrayList<>();
        String query = "SELECT * FROM " + DataBaseHelper.PRODUCT_TABLE + " WHERE App_Installed = "
                + appInstallStatus+" ORDER BY Index_app ASC";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        Cursor cur = DataBaseHelper.executeSelectQuery(db, query, null);
        while (cur.moveToNext()) {
            ProvisionalOffer product = new ProvisionalOffer();
            product.setmLabel(cur.getString(cur.getColumnIndexOrThrow("Label")));
            product.setmDescription(cur.getString(cur.getColumnIndexOrThrow("Description")));
            product.setmDeveloper(cur.getString(cur.getColumnIndexOrThrow("Developer")));
            product.setmIconUrl(cur.getString(cur.getColumnIndexOrThrow("IconUrl")));
            product.setmRating(cur.getInt(cur.getColumnIndexOrThrow("Rating")));
            product.setmPackage(cur.getString(cur.getColumnIndexOrThrow("Package")));
            product.setmUrl(cur.getString(cur.getColumnIndexOrThrow("Url")));
            product.setmType(cur.getString(cur.getColumnIndexOrThrow("Type")));
            productList.add(product);
        }
        cur.close();
        return productList;
    }


    public static ArrayList<ProvisionalOffer> getProvisionalOffer(Context ctx) {
        DataBaseHelper.init(ctx);
        ArrayList<ProvisionalOffer> productList = new ArrayList<>();
        String query = "SELECT * FROM " + DataBaseHelper.PRODUCT_TABLE +" ORDER BY Index_app ASC" ;//+ " ORDER BY Index"
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        Cursor cur = DataBaseHelper.executeSelectQuery(db, query, null);
        while (cur.moveToNext()) {
            ProvisionalOffer product = new ProvisionalOffer();
            product.setmLabel(cur.getString(cur.getColumnIndexOrThrow("Label")));
            product.setmDescription(cur.getString(cur.getColumnIndexOrThrow("Description")));
            product.setmDeveloper(cur.getString(cur.getColumnIndexOrThrow("Developer")));
            product.setmIconUrl(cur.getString(cur.getColumnIndexOrThrow("IconUrl")));
            product.setmRating(cur.getInt(cur.getColumnIndexOrThrow("Rating")));
            product.setmPackage(cur.getString(cur.getColumnIndexOrThrow("Package")));
            product.setmUrl(cur.getString(cur.getColumnIndexOrThrow("Url")));
            product.setmType(cur.getString(cur.getColumnIndexOrThrow("Type")));
            productList.add(product);
        }
        cur.close();
        return productList;
    }


    public static void removeAll(Context ctx) {
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        String query = "Delete from ProductInfo";
        db.execSQL(query);
    }


    public static void deleteProductFromDatabase(String packageName, Context ctx) {
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        String query = "Delete from ProvisionalOffer WHERE Package='" + packageName + "'";
        db.execSQL(query);
    }


    //Add the product response from GCM to temporary table
    public static void addProductToTempDataBase(ProvisionOfferModel productInfo, Context ctx) {
        DataBaseHelper.init(ctx);
        String query = "INSERT INTO " + DataBaseHelper.PRODUCT_TABLE_TEMP+"(Type,Package,Label,Description,IconUrl,Url,Rating,Developer)VALUES "
                + "(?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        SQLiteStatement statement = db.compileStatement(query);
        statement = bindValuesToStatementTemp(productInfo, statement);
        statement.execute();
        statement.close();


        if (isGCMCountAndProductInfoTempCountSame()) {
            reNameTablePRODUCT_INFO_TEMPtoPRODUCT_INFO_TABLE(ctx);

        }

    }

    //Add product count got from GCM
    public static void addProductTableCount(int count, Context ctx) {
        DataBaseHelper.init(ctx);

        String query = "INSERT INTO "+ DataBaseHelper.PRODUCT_TABLE_TEMP_COUNT+" (ProductCount)VALUES "
                + "(?)";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindDouble(1, count);
        statement.execute();
        statement.close();

    }

    private static int getProductCountFromGCM() {
        int productCount = 0;
        String query = "SELECT ProductCount FROM "+DataBaseHelper.PRODUCT_TABLE_TEMP_COUNT;
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        Cursor cur = DataBaseHelper.executeSelectQuery(db, query, null);
        while (cur.moveToNext()) {
            productCount = cur.getInt(cur.getColumnIndexOrThrow("ProductCount"));
        }
        cur.close();
        return productCount;

    }

    private static int getCountOfTempProductInfo() {
        int productCount = 0;
        String query = "SELECT  COUNT (*) as count FROM "+DataBaseHelper.PRODUCT_TABLE_TEMP;
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        Cursor cur = DataBaseHelper.executeSelectQuery(db, query, null);
        while (cur.moveToNext()) {
            productCount = cur.getInt(cur.getColumnIndexOrThrow("count"));
        }
        cur.close();
        return productCount;
    }

    /**
     * check if product count from gcm and inserted product count is same
     *
     * @return
     */
    private static boolean isGCMCountAndProductInfoTempCountSame() {
        if (getCountOfTempProductInfo() == getProductCountFromGCM()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *reNameProductInfoTempToProductInfo
     */

    private static void reNameTablePRODUCT_INFO_TEMPtoPRODUCT_INFO_TABLE(Context context) {
        deletProductInfoTable();
        removeGCMCount();
        ReNameProvisionalOffer_TEMPtoProvisionalOffer(context);

    }
    private static void deletProductInfoTable(){
        String query = "DROP TABLE IF EXISTS "+DataBaseHelper.PRODUCT_TABLE;
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        db.execSQL(query);
    }

    public static void removeGCMCount() {
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        String query = "Delete from "+DataBaseHelper.PRODUCT_TABLE_TEMP_COUNT;
        db.execSQL(query);
    }
    private static void ReNameProvisionalOffer_TEMPtoProvisionalOffer(Context context){
        String query = "ALTER TABLE "+DataBaseHelper.PRODUCT_TABLE_TEMP+ " RENAME TO "+DataBaseHelper.PRODUCT_TABLE;
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        db.execSQL(query);
        DataBaseHelper.createTemptable(context);
    }

    public static void updateProviosnalOffer(String packageName,String istalledStatus,Context ctx){
        DataBaseHelper.init(ctx);
        String query = "UPDATE "+DataBaseHelper.PRODUCT_TABLE+" SET App_Installed =? WHERE Package=?";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindString(1, istalledStatus);
        statement.bindString(2, packageName);
        statement.execute();
        statement.close();
    }

}
