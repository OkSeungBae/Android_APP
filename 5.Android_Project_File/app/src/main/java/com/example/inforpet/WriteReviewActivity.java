package com.example.inforpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteReviewActivity extends AppCompatActivity {

    //DB메니져
    DBManager dbManager;
    SQLiteDatabase database;

    RatingBar ratingBar;
    TextView review_context, review_user;
    String id_company = "04";

    //기존에 있던 widget
    TextView name_reivew_write, address_reivew_write, callnum_review_write;

    //받는 데이터
    private String bplcNm, rdnWhlAddr, siteTel, opnSvcId, mgtNo;
    private int opnSfTeamCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        //데이터 베이스 연결
        createDatabase();

        review_user = findViewById(R.id.reviewUser);
        review_context = findViewById(R.id.reviewContext);
        ratingBar = findViewById(R.id.ratingBar_review);

        //리소스 매칭
        name_reivew_write = findViewById(R.id.name_review_write);
        address_reivew_write = findViewById(R.id.address_review_write);
        callnum_review_write = findViewById(R.id.callnum_review_write);

        Intent getIntent = getIntent();

        opnSvcId = getIntent.getStringExtra("opnSvcId");
        opnSfTeamCode = getIntent.getIntExtra("opnSfTeamCode", 0);
        mgtNo = getIntent.getStringExtra("mgtNo");
        //PK
        bplcNm = getIntent.getStringExtra("bplcNm");
        rdnWhlAddr = getIntent.getStringExtra("rdnWhlAddr");
        siteTel = getIntent.getStringExtra("siteTel");

        name_reivew_write.setText(bplcNm);
        address_reivew_write.setText("주소 : " + rdnWhlAddr);
        callnum_review_write.setText("전화번호 : " + siteTel);

        dbManager = new DBManager();

        Button button = findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록하기를 누르면 제목, 내용 DB에 저장하고 액티비티 종료

                String user = review_user.getText().toString();
                float rating = ratingBar.getRating();
                String context = review_context.getText().toString();

                dbManager.insertReviewTable( database, mgtNo, Date(), user, rating, context);
                //dbManager.selectReviewTableAll();
                finish();
            }
        });
    }

    public String Date() {
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.KOREA);
//        Calendar calendar = Calendar.getInstance(Locale.KOREA);
//        String date = dateFormat.format(calendar.getTime());

        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", Locale.KOREA );
        Date currentTime = new Date ( );
        String date = formatter.format ( currentTime );

        return date;
    }

    private void createDatabase()
    {
        try {
            database = openOrCreateDatabase(MainActivity.TAG, MODE_PRIVATE, null);
            Log.i(MainActivity.TAG, "WriteReivewActivity createDatabase() :: 데이터베이스 생성 또는 열기 성공" );
        }catch (Exception e)
        {
            Log.i(MainActivity.TAG, "WriteReivewActivity createDatabase() :: 데이터베이스 생성 실패" );
            e.printStackTrace();
        }
    }
}