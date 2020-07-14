package com.example.inforpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WriteReviewActivity extends AppCompatActivity {

    TextView review, review_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        review_title = findViewById(R.id.reviewTitle);
        review = findViewById(R.id.review);

        Button button = findViewById(R.id.button_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록하기를 누르면 제목, 내용 DB에 저장하고 액티비티 종료
                String title = review_title.getText().toString();
                String context = review.getText().toString();

                finish();
            }
        });
    }
}