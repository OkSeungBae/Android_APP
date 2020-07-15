package com.example.inforpet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class DBManager extends AppCompatActivity {

    SQLiteDatabase database;

    public void createDatabase(String str)
    {
        try{
            database = openOrCreateDatabase(str, MODE_PRIVATE, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCompanyTable(String str)
    {
        if(database == null) {
            return;
        }

        String query="create table if not exists "+ str + "("
                + " id INTER PRIMARY KEY autoincrement, "
                + " business_state VARCHAR(4), "
                + " business_category VARCHAR(10), "
                + " tel VARCHEAR(13), "
                + " address TEXT, "
                + " road_name_address TEXT, "
                + " business_name VARCHAR(20), "
                + " x DOUBLE, "
                + " Y DOUBLE ";
        database.execSQL(query);
    }

    public void createReviewTable(String str)
    {
        if(database == null) {
            return;
        }

        String query="create table if not exists "+ str + "("
                + " id INTER PRIMARY KEY autoincrement, "
                + " id_company INTERGERd"
                + " date DATE_TIME, "
                + " rating INTERGE, "
                + " user VARCHAR(20), "
                + " context TEXT)";
        database.execSQL(query);
    }

    public void insertCompanyTable(String business_state, String business_category, String tel, String address, String road_name_address, String business_name, double x, double y)//받아오는것 정확히
    {
        String tableName = "companyTable";

        if(database == null) {
            return;
        }

        if(tableName == null) {
            return;
        }

        String query="insert into " + tableName
                + " (Business_state, Business_category, Tel, Address, Road_name_address, Business_name , X, Y) "
                + " values "
                + "('"+ business_state +"', '"+ business_category +"', '"+ tel +"', '"+ address +"', '"+ road_name_address +"', '"+ business_name +"', '"+ x +"', '"+ y +"')";
        database.execSQL(query);

    }

    public void insertReviewTable(String id_company, String date, String user, float rating, String context)
    {
        String tableName = "reviewTable";

        if(database == null) {
            return;
        }

        if(tableName == null) {
            return;
        }

        String query="insert into " + tableName
                + " (Id_company, Date, Rating, User, Content) "
                + " values "
                + "('"+ id_company +"', '"+ date +"', '"+ user +"', '"+ rating +"', '"+ context +"')";
        database.execSQL(query);

    }

    public void deleteCompanyTable(int id)
    {
        String query = "DELETE FROM companyTable WHERE " + "id = '" + id + "'";
    }

    public void deleteReviewTable(int id)
    {
        String query = "DELETE FROM reviewTable WHERE " + "id = '" + id + "'";
    }

    public void selectCompanyTableAll() {

        String tableName = "companyTable";
        String query = "select id, business_state, business_category, tel, address, road_name_address, business_name, x, y from "+ tableName;

        if(database == null) {
            return;
        }

        if(tableName == null) {
            return;
        }

        try {
            Cursor cursor = database.rawQuery(query,null);
            for(int i = 0; i<cursor.getColumnCount(); i++) {
                cursor.moveToNext();

                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                String mobile = cursor.getString(3);



            }
            cursor.close();
        }
        catch (Exception e)
        {e.printStackTrace();}
    }



}
