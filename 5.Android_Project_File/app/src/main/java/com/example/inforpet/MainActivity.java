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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import kr.go.localdata.client.DatasReceive;
import kr.go.localdata.client.ReceiveLocalDatas;
import kr.hyosang.coordinate.CoordPoint;
import kr.hyosang.coordinate.TransCoord;

public class MainActivity extends AppCompatActivity implements MapView.POIItemEventListener {

    public static final String TAG = "InforPet";

    MapView mapView;
    MapPOIItem marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        /*marker = new MapPOIItem();
        marker.setItemName("퍼피앤피플");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(gwsPt.y, gwsPt.x));      //      mapPointWithGeoCoord
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);*/

        //mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        //mapView.addPOIItem(marker);
        mapView.setPOIItemEventListener(this);

        xml_Parse();
    }

    /*
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
    */

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

    private void xml_Parse() {
        InputStream inputStream=getResources().openRawResource(R.raw.testdata);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        XmlPullParser xmlPullParser;

        try {
            boolean flag = false;

            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(reader);

            Integer eventType = new Integer(xmlPullParser.getEventType());

            Company c;
            //순서 ( start tag -> text -> end tag -> text )
            while(eventType != xmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        //여기서 부터 작업치기
                        switch (xmlPullParser.getName())
                        {
                            case "row" :
                                c = new Company();
                                break;
                            case "bplcNm" :
                                break;
                            case "x" :
                                break;
                            case "y":
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(xmlPullParser.getName().equals("row"));          //끝
                            //makeMarker();
                        break;
                    case XmlPullParser.TEXT:

                        break;
                }

                try {
                    eventType = xmlPullParser.next();
                }catch(IOException e){
                    e.getStackTrace();
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try{
            if(reader !=null) reader.close();
            if(inputStreamReader !=null) inputStreamReader.close();
            if(inputStream !=null)inputStream.close();;

        }catch (Exception e){}
    }

    private void makeMarker(Company company)
    {
        MapPOIItem marker;

        CoordPoint pt = new CoordPoint(company.getX(), company.getY());
        CoordPoint gwsPt = TransCoord.getTransCoord(pt, TransCoord.COORD_TYPE_TM, TransCoord.COORD_TYPE_WGS84);

        marker = new MapPOIItem();
        marker.setItemName(company.getBplcNm());
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(gwsPt.y, gwsPt.x));      //      mapPointWithGeoCoord
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
    }

    /*
    private void getData()
    {
        //1. ReceiveLocalDatas 객체 생성
        ReceiveLocalDatas dr = new ReceiveLocalDatas();

        //2. 파라미터 설정
        // auth_key - 인증키
        // api_type- api유형
        // resultype - 결과형태
        // lastModTsBgn - 최종수정일자(시작일)
        // lastModTsEnd - 최종수정일자(종료일)
        // pageIndex - 페이지번호
        // pageSize - 페이지당 출력 개수

        //신청 API 인증키
        String auth_key = "z5x=g6BiqWf0sRJ7S2ZyvpSYDrFVApiRf5Tzotpj1/g=";

        //신청 API 유형
        String api_type = "GR0";

        //결과평태 설정 - XML / JSON
        String resultType = "XML";


        //receiveOpenMonthDatas() 호출 시 날짜 파라미터 (YYYYMMDD 형식으로 입력)
        String lastModTsBgn = "20100711";
        String lastModTsEnd = "20200712";

        //호출 시 페이지 관련 변수
        int pageIndex = 1;
        int pageSize = 20;

        try
        {
            //reveiveTotalCnt() - 제공 받을 데이터 총건수를 가져오는 메소드(M-receiveOpenMonthDatas 호출 시 사용, D-receiveOpenDatDatas 호출 시 사용)
            DatasReceive totalcnt = dr.receiveTotalCnt(api_type, auth_key,"M", lastModTsBgn, lastModTsEnd);
            int totalCnt = totalcnt.getReqTotalCnt();

            if(totalCnt == -1)
            {
                //서버응답 확인
                Log.i(TAG, "서버응답 확인 Response Code :: " + totalcnt.getResultCode());
                Log.i(TAG, "서버응답 확인 Response Message :: " + totalcnt.getMsg());
            }
            else
            {
                //pageIndex 값을 1씩 늘려가면서 반복 호출 하기 위한 변수
                int forNum = (int)Math.ceil((double)totalCnt/pageSize);
                for(pageIndex = 1; pageIndex < forNum+1; pageIndex++)
                {
                    //receiveOpenDayDatas() - 전일 변동분의 자료를 가져오는 메소드
                    //DatasReceive rld = dr.receiveOpenDayDatas(api_type, auth_key, resultType, pageIndex, pageSize );

                    //receiveOpenMonthDatas() - 현재 월 변동분의 자료를 가져오는 메소드 : 검색가능 날짜범위 - 전월 24일 ~ 현재 일자 2일 전까지
                    DatasReceive rld = dr.receiveOpenMonthDatas(api_type, auth_key, resultType, lastModTsBgn, lastModTsEnd, pageIndex, pageSize);

                    if(rld.getResult() != 0){

                        if(rld.getResult() == -1){
                            System.out.println("### 서버 접속 실패 : 잠시 후 다시 이용바랍니다.");
                        }

                        //오류 결과 확인
                        Log.i(TAG,"Response code : "+rld.getResultCode());
                        Log.i(TAG,"Response Msg : "+rld.getMsg());
                    }

                    //서버 응답 확인
                    Log.i(TAG,"Response code : "+rld.getResultCode());
                    Log.i(TAG,"Response Msg : "+rld.getMsg());

                    //결과 확인
                    Log.i(TAG,"Response ResultData : "+rld.getResultData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    class Company
    {
        private String bplcNm;      //사업장명
        private float x;            //x
        private float y;            //y


        public Company(){};

        public Company(String bplcNm, float x, float y)
        {
            this.bplcNm = bplcNm;
            this.x = x;
            this.y = y;
        }

        public String getBplcNm() {
            return bplcNm;
        }

        public void setBplcNm(String bplcNm) {
            this.bplcNm = bplcNm;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}