package com.example.inforpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import net.daum.android.map.coord.MapCoord;
import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import kr.hyosang.coordinate.CoordPoint;
import kr.hyosang.coordinate.TransCoord;

public class MainActivity extends AppCompatActivity implements MapView.POIItemEventListener {

    MapView mapView;
    MapPOIItem marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        CoordPoint pt = new CoordPoint(196470.449471829, 455970.888515918);
        CoordPoint gwsPt = TransCoord.getTransCoord(pt, TransCoord.COORD_TYPE_TM, TransCoord.COORD_TYPE_WGS84);

        Log.i("geoCode", "x :: " + gwsPt.x +" y :: " + gwsPt.y);

        marker = new MapPOIItem();
        marker.setItemName("퍼피앤피플");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(gwsPt.y, gwsPt.x));      //      mapPointWithGeoCoord
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        //mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.addPOIItem(marker);
        mapView.setPOIItemEventListener(this);
    }


    //카카오 지도 위 MapPOIItem에 커스텀 말풍선 interface를 implement받은 class이다.
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter
    {

        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter()
        {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem mapPOIItem) {

            //((TextView)mCalloutBalloon.findViewById(R.id.textViewTitle)).setText(mapPOIItem.getItemName());
            //((TextView)mCalloutBalloon.findViewById(R.id.textViewTel)).setText(mapPOIItem.getItemName());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem mapPOIItem) {
            return null;
        }
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        //마커 클릭 이벤트
        Toast.makeText(MainActivity.this, "onPOIItemSelected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //말풍선 클릭 이벤트
        //activity_review.xml을 띄우기
        //넘겨줄 정보
        //1. 선택된 업체 ID값

        Intent reviewIntent = new Intent(this, ReviewActivity.class);
        reviewIntent.putExtra("id","id");
        startActivity(reviewIntent);
        Toast.makeText(MainActivity.this, "onCalloutBalloonOfPOIItemTouched", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Toast.makeText(MainActivity.this, "onCalloutBalloonOfPOIItemTouched", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        Toast.makeText(MainActivity.this, "onDraggablePOIItemMoved", Toast.LENGTH_SHORT).show();
    }
}