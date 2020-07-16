package com.example.inforpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecommandActivity extends AppCompatActivity {

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
        textViewName = findViewById(R.id.name_recommand);
        textViewAddr = findViewById(R.id.address_recommand);
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
                switch (position)
                {
                    case 0:
                        textViewName.setText("롯데마트 율하점 쿨펫 동물병원");
                        textViewAddr.setText("대구광역시 동구 율하동 1117 롯데마트 율하점 4층");
                        break;
                    case 1:
                        textViewName.setText("더문 동물병원");
                        textViewAddr.setText("대구광역시 북구 내곡동 81로 더문 동물병원 1층");
                        break;
                    case 2:
                        textViewName.setText("24시 범어 동물의료센터");
                        textViewAddr.setText("대구광역시 수성구 달구벌대로 2354");
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                //시작페이지
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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