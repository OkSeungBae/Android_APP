package com.example.inforpet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

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
                + " date DATE, "
                + " rating INTERGE, "
                + " user VARCHAR(20), "
                + " context TEXT)";
        database.execSQL(query);
    }

    public void insertCompanyTable(int id, String business_state)
    {
        String tableName = "CompayTable";

        if(database == null) {
            return;
        }

        if(tableName == null) {
            return;
        }

        String query="insert into " + tableName
                + " (name, age, mobile) "
                + " values "
                + "('"+ Name +"', "+ Integer.parseInt(Age) +", '"+ Mobile +"')";
        database.execSQL(query);

    }

    public void insertReviewTable(String user, float rating, String context)
    {
        String tableName = "ReviewTable";

        if(database == null) {
            return;
        }

        if(tableName == null) {
            return;
        }

        String query="insert into " + tableName
                + " (name, age, mobile) "
                + " values "
                + "('"+ Name +"', "+ Integer.parseInt(Age) +", '"+ Mobile +"')";
        database.execSQL(query);

    }

    public void deleteCompanyTable(int id)
    {

    }

    public void deleteReviewTable(int id)
    {

    }

    public void selectAll() {

        String tableName = editText_tb.getText().toString();
        String query = "select _id, name, age, mobile from "+ tableName;

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

                println("레코드#"+ i +" : "+ id +", "+ name +", "+ age +", "+ mobile);
            }
            cursor.close();
        }
        catch (Exception e)
        {e.printStackTrace();}
    }



}
