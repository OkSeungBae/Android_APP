package com.example.inforpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecommandActivity extends AppCompatActivity {

    private int selectedPosition;

    GestureDetector detector;

    ViewPager viewPager;

    TextView textViewTitle, textViewName, textViewAddr;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommand);

        viewPager = findViewById(R.id.viewPager_recommand);
        viewPager.setOffscreenPageLimit(3);

        textViewTitle = findViewById(R.id.title_recommand);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //선택된 페이지
                selectedPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                //시작페이지
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return false;
            }
        });

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {

                Intent intent;
                switch (selectedPosition)
                {
                    case 0:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.simkongcat.com/"));
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wefluffy.co.kr/"));
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vuumpet.co.kr/"));
                        startActivity(intent);
                        break;
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
        /*viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(RecommandActivity.this, "클릭됨 :: " + selectedPosition, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (selectedPosition)
                {
                    case 0:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.simkongcat.com/"));
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wefluffy.co.kr/"));
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vuumpet.co.kr/"));
                        startActivity(intent);
                        break;
                }
            }
        });*/

        FragmentManager fragmentManager = getSupportFragmentManager();

        MyPagerAdapter adapter = new MyPagerAdapter(fragmentManager);

        FragmentRecommand1 fragmentRecommand = new FragmentRecommand1();
        FragmentRecommand2 fragmentRecommand2 = new FragmentRecommand2();
        FragmentRecommand3 fragmentRecommand3 = new FragmentRecommand3();

        adapter.addItem(fragmentRecommand);
        adapter.addItem(fragmentRecommand2);
        adapter.addItem(fragmentRecommand3);
        viewPager.setAdapter(adapter);
    }

}
class MyPagerAdapter extends FragmentStatePagerAdapter
{
    ArrayList<Fragment> items = new ArrayList<Fragment>();

    public MyPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    public void addItem(Fragment fragment)
    {
        items.add(fragment);
    }
    public void addItems(Fragment[] fragments)
    {
        for(int i=0; i<fragments.length; i++)
        {
            addItem(fragments[i]);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}