package com.example.inforpet;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class DBManager extends Service {

    final String TAG = "InforPet";
    SQLiteDatabase database;

    public void createDatabase(String str)
    {
        try{
            database = openOrCreateDatabase(str, MODE_PRIVATE, null);
            Log.i(TAG, "데이터 베이스가 생성되었습니다.");
        }
        catch (Exception e) {
            Log.i(TAG, "데이터 베이스 생성 오류 :: " + e.toString());
            e.printStackTrace();
        }
    }

    public void createCompanyTable(String str)
    {
        if(database == null) {
            Log.i(TAG, "데이터 베이스가 없습니다.");
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

        Log.i(TAG, "Company 테이블이 생성되었습니다.");
    }

    public void createReviewTable(SQLiteDatabase database)
    {
        if(database == null) {
            Log.i(TAG, "DBManager createReivewTable() 데이터 베이스가 없습니다.");
            return;
        }

        String query="create table if not exists review ("
                + " id INTEGER PRIMARY KEY autoincrement, "
                + " id_company TEXT, "
                + " date DATE, "
                + " rating INTEGER, "
                + " user VARCHAR(20), "
                + " context TEXT)";
        database.execSQL(query);
        Log.i(TAG, "DBManager createReviewTable() Review 테이블이 생성되었습니다.");
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

    public void insertReviewTable(SQLiteDatabase database, String id_company, String date, String user, float rating, String context)
    {
        String tableName = "reviewTable";

        if(database == null) {
            Toast.makeText(this, "database == null", Toast.LENGTH_LONG).show();
            return;
        }

        if(tableName == null) {
            Toast.makeText(this, "tableName == null", Toast.LENGTH_LONG).show();
            return;
        }

        String query="insert into " + tableName
                + " (Id_company, Date, Rating, User, Content) "
                + " values "
                + "('"+ id_company +"', '"+ date +"', '"+ user +"', '"+ rating +"', '"+ context +"')";
        database.execSQL(query);

        Toast.makeText(this, "테이블 삽입 : "+id_company+", "+date+", "+user+", "+rating+", "+context , Toast.LENGTH_LONG).show();

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
                String business_state = cursor.getString(1);
                String business_category = cursor.getString(2);
                String tel = cursor.getString(3);
                String address = cursor.getString(4);
                String road_name_address = cursor.getString(5);
                String business_name = cursor.getString(6);
                double x = cursor.getDouble(7);
                double y = cursor.getDouble(8);

            }
            cursor.close();
        }
        catch (Exception e)
        {e.printStackTrace();}
    }

    public void selectReviewTableAll(SQLiteDatabase database, String get_id_company) {

        String tableName = "review";
        String query = "select id, id_company, date, rate, user, content from "+ tableName + "where id_company = " + get_id_company;

        if(database == null) {
            Log.i(TAG, "DBManager selectReviewTableAll() 데이터 베이스가 없습니다.");
            return;
        }

        if(tableName == null) {
            Log.i(TAG, "DBManager selectReviewTableAll() 테이블 이름이 없습니다.");
            return;
        }

        try {
            Cursor cursor = database.rawQuery(query,null);
            for(int i = 0; i<cursor.getColumnCount(); i++) {
                cursor.moveToNext();

                int id = cursor.getInt(0);
                String id_company = cursor.getString(1);
                String date = cursor.getString(2);
                String rate = cursor.getString(3);
                String user = cursor.getString(4);
                String context = cursor.getString(5);

                Log.i(TAG, "DBManager selectReviewTableAll() 데이터 받아옴 :: " + id + " " + id_company + " " + date + " " + rate + " " + user + " " + context);
            }
            cursor.close();
        }
        catch (Exception e)
        {e.printStackTrace();}


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
