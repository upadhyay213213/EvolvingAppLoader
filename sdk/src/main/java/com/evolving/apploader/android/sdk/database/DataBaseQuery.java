package com.evolving.apploader.android.sdk.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.evolving.apploader.android.sdk.model.ProvisionalOffer;

import java.util.ArrayList;

/**
 * Class to interact with Database via SQL Queries
 */
public class DataBaseQuery {

    public static void addProductToDataBase(ProvisionalOffer productInfo,Context ctx) {
           DataBaseHelper.init(ctx);
          String query ="INSERT INTO ProvisionalOffer (Type,Package,Label,Description,IconUrl,Url,Rating,Developer)VALUES "
                            + "(?,?,?,?,?,?,?,?)";
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

        } catch (Exception e) {
            //todo
        }
        return statement;
    }


    public static ArrayList<ProvisionalOffer> getProvisionalOffer(Context ctx) {
        DataBaseHelper.init(ctx);
        ArrayList<ProvisionalOffer> productList = new ArrayList<>();
        String query = "SELECT * FROM ProvisionalOffer";
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


    public static void deleteProductFromDatabase(String packageName,Context ctx) {
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        String query = "Delete from ProvisionalOffer WHERE Package='" + packageName+ "'";
        db.execSQL(query);
    }
}
