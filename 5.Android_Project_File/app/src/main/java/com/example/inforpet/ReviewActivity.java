package com.example.inforpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    TextView name, address, call;
    float rating_avg;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingBar = findViewById(R.id.ratingBar_reput);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        rating_avg = 4;
        ratingBar.setRating(rating_avg); //DB에 있는 rating 정보의 평균값을 가져옴

        Button button = findViewById(R.id.button_review);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //작성하기 버튼누르면 '리뷰작성' 액티비티 띄움
                Intent intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                startActivity(intent);
            }
        });

    }
}