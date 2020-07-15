package com.example.inforpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    ScrollView scrollView;

    ArrayList<Reviewer> reviewList;

    //DB
    DBManager dbManager;
    SQLiteDatabase database;

    TextView name, address, call;
    float rating_avg;
    RatingBar ratingBar;

    //받는 데이터
    private String bplcNm, rdnWhlAddr, siteTel, opnSvcId, mgtNo;
    private int opnSfTeamCode;

    //리사이클 뷰 관련
    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        //PK
        opnSvcId = intent.getStringExtra("opnSvcId");
        opnSfTeamCode = intent.getIntExtra("opnSfTeamCode", 0);
        mgtNo = intent.getStringExtra("mgtNo");
        //PK
        bplcNm = intent.getStringExtra("bplcNm");
        rdnWhlAddr = intent.getStringExtra("rdnWhlAddr");
        siteTel = intent.getStringExtra("siteTel");

        //데이터 베이스 연결
        createDatabase();

        dbManager = new DBManager();

        //review table만들기
        dbManager.createReviewTable(database);

        scrollView = findViewById(R.id.ScrollView_reivew);
        name = findViewById(R.id.name_review);
        address = findViewById(R.id.address_review);
        call = findViewById(R.id.callnum_review);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView 의 layout 크기 고정
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        name.setText(bplcNm);
        address.setText("주소 : " + rdnWhlAddr);
        call.setText("전화번호 : " + siteTel);


        ratingBar = findViewById(R.id.ratingBar_reput);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);


        Button button = findViewById(R.id.button_review);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //작성하기 버튼누르면 '리뷰작성' 액티비티 띄움
                Intent writeReviewintent = new Intent(getApplicationContext(), WriteReviewActivity.class);

                //PK
                writeReviewintent.putExtra("opnSvcId",opnSvcId);
                writeReviewintent.putExtra("opnSfTeamCode",opnSfTeamCode);
                writeReviewintent.putExtra("mgtNo",mgtNo);
                //PK

                writeReviewintent.putExtra("bplcNm",bplcNm);
                writeReviewintent.putExtra("rdnWhlAddr",rdnWhlAddr);
                writeReviewintent.putExtra("siteTel",siteTel);
                startActivity(writeReviewintent);
            }
        });
    }

    @Override
    protected void onStart() {
        //review Table에 정보 다 받아오기
        reviewList = dbManager.selectReviewTableAll(database, mgtNo);

        reviewAdapter = new ReviewAdapter();
        reviewAdapter.setItems(reviewList);
        /*
        reviewAdapter.addItem(new Reviewer("kim", 4.5f, "2020-07-15", "동물병원 좋아용"));
        reviewAdapter.addItem(new Reviewer("park", 4.5f, "2020-07-10", "동물병원 싫어용"));
        */

        recyclerView.setAdapter(reviewAdapter);

        rating_avg = reviewAdapter.getRatingAvg();
        ratingBar.setRating(rating_avg); //DB에 있는 rating 정보의 평균값을 가져옴
        super.onStart();
    }

    private void createDatabase()
    {
        try {
            database = openOrCreateDatabase(MainActivity.TAG, MODE_PRIVATE, null);
            Log.i(MainActivity.TAG, "ReivewActivity createDatabase() :: 데이터베이스 생성 또는 열기 성공" );
        }catch (Exception e)
        {
            Log.i(MainActivity.TAG, "ReivewActivity createDatabase() :: 데이터베이스 생성 실패" );
            e.printStackTrace();
        }
    }
}