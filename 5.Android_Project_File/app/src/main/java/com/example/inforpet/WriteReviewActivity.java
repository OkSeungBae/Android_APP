package com.example.inforpet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteReviewActivity extends AppCompatActivity {

    RatingBar ratingBar;
    TextView review_context, review_user;
    DBManager dbManager;
    String id_company = "04";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        review_user = findViewById(R.id.reviewUser);
        review_context = findViewById(R.id.reviewContext);
        ratingBar = findViewById(R.id.ratingBar_review);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_ATOP);

        Button button = findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록하기를 누르면 제목, 내용 DB에 저장하고 액티비티 종료

                String user = review_user.getText().toString();
                float rating = ratingBar.getRating();
                String context = review_context.getText().toString();

                dbManager.insertReviewTable( id_company, Date(), user, rating, context);
                dbManager.selectReviewTableAll();
                finish();
            }
        });
    }

    public String Date() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.KOREA);
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        String date = dateFormat.format(calendar.getTime());

//        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
//        Date currentTime = new Date ( );
//        String date = formatter.format ( currentTime );

        return date;
    }
}