package com.depauw.repairshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {



    private static DBHelper instance;

    private static final String DB_NAME = "vehicleRepair.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_VEHICLE = "Vehicle";
    private static final String COL_VEHICLE_VID = "Vid";
    private static final String COL_VEHICLE_YEAR = "Year";
    private static final String COL_VEHICLE_MODEL = "Model";
    private static final String COL_VEHICLE_PRICE = "Price";
    private static final String COL_VEHICLE_NEW = "New";

    private static final String TABLE_REPAIR = "Repair";
    private static final String COL_REPAIR_RID = "Rid";
    private static final String COL_REPAIR_VEHICLE = "Vehicle";
    private static final String COL_REPAIR_DATE = "Date";
    private static final String COL_REPAIR_COST = "Cost";
    private static final String COL_REPAIR_DESCRIPTION = "Description";
    private static final String COL_REPAIR_VID = "R_Vid";


    private DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_VEHICLE + " (" +
                    COL_VEHICLE_VID + " INTEGER NOT NULL," +
                    COL_VEHICLE_YEAR + " TEXT NOT NULL," +
                    COL_VEHICLE_MODEL + " TEXT NOT NULL," +
                    COL_VEHICLE_PRICE + " NUMERIC NOT NULL," +
                    COL_VEHICLE_NEW + " INTEGER NOT NULL," +
                    "PRIMARY KEY ( " + COL_VEHICLE_VID + " AUTOINCREMENT)" +
                    ")";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_REPAIR + " (" +
                COL_REPAIR_RID + " INTEGER NOT NULL," +
                COL_REPAIR_VEHICLE + " TEXT NOT NULL," +
                COL_REPAIR_DATE + " TEXT NOT NULL," +
                COL_REPAIR_COST + " NUMERIC NOT NULL," +
                COL_REPAIR_DESCRIPTION + " TEXT NOT NULL," +
                COL_REPAIR_VID + " TEXT NOT NULL," +
                "PRIMARY KEY ( " + COL_REPAIR_RID + " AUTOINCREMENT)" +
                "FOREIGN KEY ( " + COL_REPAIR_VID + ") REFERENCES " + TABLE_VEHICLE + "(" + COL_VEHICLE_VID + ")" +
                ")";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int removeRepair(int rid)
    {
        SQLiteDatabase db = getWritableDatabase();

        String where = String.format("%s LIKE '%s'", COL_REPAIR_RID, rid);
        int numRows = db.delete(TABLE_REPAIR, where, null);
        db.close();
        return numRows;
    }

    public long insertRepair(Repair r)
    {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();


        cv.put(COL_REPAIR_VEHICLE, r.getVehicle());
        cv.put(COL_REPAIR_DATE, r.getDate());
        cv.put(COL_REPAIR_COST, r.getCost());
        cv.put(COL_REPAIR_DESCRIPTION, r.getDescription());
        cv.put(COL_REPAIR_VID, r.getVid());
        System.out.println("R VID " + r.getVid());
        long result = db.insert(TABLE_REPAIR, null, cv);
        db.close();

        return result;
    };

    public long insertVehicle(Vehicle v)
    {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_VEHICLE_YEAR, v.getYear());
        cv.put(COL_VEHICLE_MODEL, v.getModel());
        cv.put(COL_VEHICLE_PRICE, v.getPrice());
        cv.put(COL_VEHICLE_NEW, v.isNew());
        long result = db.insert(TABLE_VEHICLE, null, cv);
        db.close();

        return result;
    };

    public ArrayList<RepairWithVehicle> returnRepairWithVehicles(String inquiry)
    {
        ArrayList<RepairWithVehicle> rvechicles = new ArrayList<RepairWithVehicle>();

        SQLiteDatabase db = getReadableDatabase();




        String sql = "SELECT * FROM " + TABLE_VEHICLE + " " +
                    "INNER JOIN " + TABLE_REPAIR + " " +
                    "ON " + TABLE_VEHICLE + "." + COL_VEHICLE_VID + "=" + TABLE_REPAIR + "." +COL_REPAIR_VID + " ";

        if(inquiry.length() > 0)
        {
            sql += "WHERE " + TABLE_REPAIR + "." + COL_REPAIR_DESCRIPTION + " LIKE '" + inquiry + "'";
        }




        Cursor cursor = db.rawQuery(sql, null);

        int idx_v_vid = cursor.getColumnIndex(COL_VEHICLE_VID);
        int idx_v_model = cursor.getColumnIndex(COL_VEHICLE_MODEL);
        int idx_v_new = cursor.getColumnIndex(COL_VEHICLE_NEW);
        int idx_v_price = cursor.getColumnIndex(COL_VEHICLE_PRICE);
        int idx_v_year = cursor.getColumnIndex(COL_VEHICLE_YEAR);



        int idx_r_rid = cursor.getColumnIndex(COL_REPAIR_RID);
        int idx_r_cost = cursor.getColumnIndex(COL_REPAIR_COST);
        int idx_r_date = cursor.getColumnIndex(COL_REPAIR_DATE);
        int idx_r_vehicle = cursor.getColumnIndex(COL_REPAIR_VEHICLE);
        int idx_r_vid = cursor.getColumnIndex(COL_REPAIR_VID);
        int idx_r_description = cursor.getColumnIndex(COL_REPAIR_DESCRIPTION);

        if(cursor.moveToFirst())
        {
            do {
                int v_vid = cursor.getInt(idx_v_vid);
                String v_model = cursor.getString(idx_v_model);
                Boolean v_new = intToBool(cursor.getInt(idx_v_new));
                float v_price = cursor.getFloat(idx_v_price);
                String v_year = cursor.getString(idx_v_year);

                int r_rid = cursor.getInt(idx_r_rid);
                float r_cost = cursor.getFloat(idx_r_cost);
                String r_date = cursor.getString(idx_r_date);
                String r_vehicle = cursor.getString(idx_r_vehicle);
                int r_vid = cursor.getInt(idx_r_vid);
                String r_description = cursor.getString(idx_r_description);

                Vehicle veh = new Vehicle(v_vid, v_year, v_model, v_price, v_new);
                Repair rep = new Repair(r_rid, r_vehicle, r_date, r_cost, r_description, r_vid);

                RepairWithVehicle repWVeh = new RepairWithVehicle(rep, veh);
                rvechicles.add(repWVeh);

            }while (cursor.moveToNext());
        }

        return rvechicles;

    }



    public ArrayList<Vehicle> returnVehicles()
    {
        ArrayList<Vehicle> Vehicles = new ArrayList<Vehicle>();

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_VEHICLE;

        Cursor cursor = db.rawQuery(sql, null);

        int idx_vid = cursor.getColumnIndex(COL_VEHICLE_VID);
        int idx_model = cursor.getColumnIndex(COL_VEHICLE_MODEL);
        int idx_new = cursor.getColumnIndex(COL_VEHICLE_NEW);
        int idx_price = cursor.getColumnIndex(COL_VEHICLE_PRICE);
        int idx_year = cursor.getColumnIndex(COL_VEHICLE_YEAR);


        if(cursor.moveToFirst())
        {
            do
            {
                int i_vid = cursor.getInt(idx_vid);
                String i_model = cursor.getString(idx_model);
                Boolean i_new = intToBool(cursor.getInt(idx_new));
                float i_price = cursor.getFloat(idx_price);
                String i_year = cursor.getString(idx_year);


                Vehicle newVehicle = new Vehicle(i_vid, i_year, i_model, i_price, i_new);
                Vehicles.add(newVehicle);
            }while(cursor.moveToNext());
        }

        return Vehicles;
    }

    public boolean intToBool(int a)
    {
        return a==1 ? true : false;
    }

    public static DBHelper getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DBHelper(context);
        }
        return instance;
    }




}
