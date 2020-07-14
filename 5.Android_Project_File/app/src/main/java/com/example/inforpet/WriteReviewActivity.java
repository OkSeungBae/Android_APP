package com.example.inforpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class WriteReviewActivity extends AppCompatActivity {

    RatingBar ratingBar;
    TextView review_context, review_user;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        review_user = findViewById(R.id.reviewUser);
        review_context = findViewById(R.id.reviewContext);
        ratingBar = findViewById(R.id.ratingBar_review);

        Button button = findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록하기를 누르면 제목, 내용 DB에 저장하고 액티비티 종료

                String user = review_user.getText().toString();
                float rating = ratingBar.getRating();
                String context = review_context.getText().toString();

                dbManager.insertReviewTable(user, rating, context);

                finish();
            }
        });
    }
}