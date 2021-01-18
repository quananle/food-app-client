package com.leanhquan.deliveryfoodver2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.leanhquan.deliveryfoodver2.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "DeliveryFoodAppver2.db";
    private static final  int DB_VER = 3;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductID", "ProductName", "ProductCount", "ProductPrice", "Discount", "Image"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> resutl = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                resutl.add(new Order(cursor.getString(cursor.getColumnIndex("ProductID")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("ProductCount")),
                        cursor.getString(cursor.getColumnIndex("ProductPrice")),
                        cursor.getString(cursor.getColumnIndex("Discount")),
                        cursor.getString(cursor.getColumnIndex("Image"))
                ));
            }while (cursor.moveToNext());
        }
        return resutl;
    }

    public  void addTocart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductID,ProductName,ProductCount,ProductPrice,Discount,Image) VALUES('%s','%s','%s','%s','0','%s');",
                order.getProductID(),
                order.getProductName(),
                order.getProductCount(),
                order.getProductPrice(),
                order.getImage());
        db.execSQL(query);
    }

    public  void clearCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public int getCountCart() {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT (*) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query, null);
        if (cursor .moveToFirst()){
            do {
                count = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return count;
    }


}
