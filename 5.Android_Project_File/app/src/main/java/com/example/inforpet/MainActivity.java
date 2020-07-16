package com.example.inforpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import kr.hyosang.coordinate.CoordPoint;
import kr.hyosang.coordinate.TransCoord;

public class MainActivity extends AppCompatActivity implements MapView.POIItemEventListener {

    private int searchMode;

    final int SEARCH_BPLCM = 21;
    final int SEARCH_RDADD = 22;
    final int SEARCH_WHLADD= 23;

    private Spinner spinner;

    private int mapMode;

    final int MAP_00 = 100;
    final int MAP_01 = 11;
    final int MAP_02 = 12;
    final int MAP_03 = 13;
    final int MAP_04 = 14;
    final int MAP_05 = 15;
    final int MAP_06 = 16;

    private MapPOIItem[][] mapModeMarekr;

    final int STEP_NONE = 0;
    final int STEP_opnSvcId = 1;
    final int STEP_opnSfTeamCode = 2;
    final int STEP_mgtNo = 3;
    final int STEP_trdStateGbn = 4;
    final int STEP_siteTel = 5;
    final int STEP_siteWhlAddr = 6;
    final int STEP_rdnWhlAddr = 7;
    final int STEP_bplcNm = 8;
    final int STEP_x = 9;
    final int STEP_y = 10;

    public static final String TAG = "InforPet";

    private DrawerLayout drawerLayout;
    private View drawerView;
    private SearchView searchView;

    MapView mapView;

    //test
    Button btnOpenNavi;

    //네비게이션 버튼
    Button btnPet[];
    Button recommandBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = new MapView(this);
        mapMode = MAP_00;
        mapModeMarekr = new MapPOIItem[6][];

        final ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //인플레이션 ( 리소스 매칭 )
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerView = findViewById(R.id.drawer);
        searchView = findViewById(R.id.searchView);
        spinner = findViewById(R.id.spinnder);

        ArrayList<String> spinnerItem = new ArrayList<String>();
        spinnerItem.add("업체명");
        spinnerItem.add("도로명주소");
        spinnerItem.add("주소");

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItem));
        searchMode = SEARCH_BPLCM;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        searchMode = SEARCH_BPLCM;
                        break;
                    case 1:
                        searchMode = SEARCH_RDADD;
                        break;
                    case 2:
                        searchMode = SEARCH_WHLADD;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //입력받은 문자열 처리
                MapPOIItem[] markers = null;
                switch (mapMode)
                {
                    case MAP_00:
                        Toast.makeText(MainActivity.this, "카달로그를 선택하세요", Toast.LENGTH_SHORT).show();
                        return true;
                    case MAP_01:
                        if(mapModeMarekr[0] == null)
                        {
                            //mapModeMarekr[0] = new MapPOIItem[mapView.getPOIItems().length];
                            mapModeMarekr[0] = mapView.getPOIItems();
                        }
                        markers = mapModeMarekr[0];
                        break;
                    case MAP_02:
                        if(mapModeMarekr[1] == null)
                        {
                            mapModeMarekr[1] = mapView.getPOIItems();
                        }
                        markers = mapModeMarekr[1];
                        break;
                    case MAP_03:
                        if(mapModeMarekr[2] == null)
                        {
                            mapModeMarekr[2] = mapView.getPOIItems();
                        }
                        markers = mapModeMarekr[2];
                        break;
                    case MAP_04:
                        if(mapModeMarekr[3] == null)
                        {
                            mapModeMarekr[3] = mapView.getPOIItems();
                        }
                        markers = mapModeMarekr[3];
                        break;
                    case MAP_05:
                        if(mapModeMarekr[4] == null)
                        {
                            mapModeMarekr[4] = mapView.getPOIItems();
                        }
                        markers = mapModeMarekr[4];
                        break;
                    case MAP_06:
                        if(mapModeMarekr[5] == null)
                        {
                            mapModeMarekr[5] = mapView.getPOIItems();
                        }
                        markers = mapModeMarekr[5];
                        break;
                }

                mapView.removeAllPOIItems();
                boolean isSearch = false;
                for(int i=0; i<markers.length; i++)
                {
                    Company c = (Company)markers[i].getUserObject();
                    switch (searchMode)
                    {
                        case SEARCH_BPLCM:
                            if(c.getBplcNm().contains(s))
                            {
                                mapView.addPOIItem(markers[i]);
                                isSearch = true;
                            }
                            break;
                        case SEARCH_RDADD:
                            if(c.getRdnWhlAddr().contains(s))
                            {
                                mapView.addPOIItem(markers[i]);
                                isSearch = true;
                            }
                            break;
                        case SEARCH_WHLADD:
                            if(c.getSiteWhlAddr().contains(s))
                            {
                                mapView.addPOIItem(markers[i]);
                                isSearch = true;
                            }
                            break;
                    }
                }
                if(!isSearch)
                    Toast.makeText(MainActivity.this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //입력란에 문자열이 바뀔 때 처리
                return false;
            }
        });

        btnOpenNavi = findViewById(R.id.btnNavi);
        btnPet = new Button[6];
        btnPet[0] = drawerView.findViewById(R.id.btnPet01);
        btnPet[1] = drawerView.findViewById(R.id.btnPet02);
        btnPet[2] = drawerView.findViewById(R.id.btnPet03);
        btnPet[3] = drawerView.findViewById(R.id.btnPet04);
        btnPet[4] = drawerView.findViewById(R.id.btnPet05);
        btnPet[5] = drawerView.findViewById(R.id.btnPet06);

        recommandBtn = drawerView.findViewById(R.id.recommandBtn_main);

        recommandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recommandIntent = new Intent(getApplicationContext(), RecommandActivity.class);
                startActivity(recommandIntent);
            }
        });

        btnOpenNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerView);

                /*mapView.removeAllPOIItems();
                xmlCall(R.raw.testdata);*/
            }
        });

        //네이게이션의 버튼 리스너 등록
        for(int i=0; i<6; i++)
        {
            setNaviButtonListener(i);
        }

        //리스너 등록 ( 네이게이션 )
        drawerLayout.setDrawerListener(listener);


        initMap();

        mapView.setPOIItemEventListener(this);
    }

    private void setNaviButtonListener(int index)
    {
        final int i = index;
        btnPet[index].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.removeAllPOIItems();
                switch (i)
                {
                    case 0:
                        mapMode = MAP_01;
                        xmlCall(R.raw.data0);
                        break;
                    case 1:
                        mapMode = MAP_02;
                        xmlCall(R.raw.data1);
                        break;
                    case 2:
                        mapMode = MAP_03;
                        xmlCall(R.raw.data2);
                        break;
                    case 3:
                        mapMode = MAP_04;
                        xmlCall(R.raw.data3);
                        break;
                    case 4:
                        mapMode = MAP_05;
                        xmlCall(R.raw.data4);
                        break;
                    case 5:
                        mapMode = MAP_06;
                        xmlCall(R.raw.data5);
                        break;
                }
                drawerLayout.closeDrawer(drawerView);
            }
        });
    }
    //DrawerLayout리스너
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };


    public void initMap()
    {
        //맵 중심점 변경 ( 3d혁신센터)
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.877266, 128.735755), true);

        //줌 레벨 변경
        mapView.setZoomLevel(6, true);
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

    private void xmlCall(int xmlFileId) {

        InputStream inputStream=getResources().openRawResource(xmlFileId);
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
            int step_mode = STEP_NONE;

            String opnSvcId="";    //개발서비스ID  ex) "02_03_06_P" (primary key)
            int opnSfTeamCode=0;  //개밤자치단체코드  (primary key)
            String mgtNo="";          //관리번호 (primary key)
            String trdStateGbn=""; //영업상태구분코드  ex) 01, 02, 03, 04
            String siteTel="";     //소재지 전화
            String siteWhlAddr=""; //소재지 전체주소
            String rdnWhlAddr="";  //도로명 주소
            String bplcNm="";      //사업장명
            double x=0;            //x
            double y=0;            //y

            //순서 ( start tag -> text -> end tag -> text )
            while(eventType != xmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String startTag = xmlPullParser.getName();
                        switch (startTag)
                        {
                            case "row" :
                                //객체생성
                                break;
                            case "opnSvcId" :
                                step_mode = STEP_opnSvcId;
                                break;
                            case "opnSfTeamCode" :
                                step_mode = STEP_opnSfTeamCode;
                                break;
                            case "mgtNo" :
                                step_mode = STEP_mgtNo;
                                break;
                            case "trdStateGbn" :
                                step_mode = STEP_trdStateGbn;
                                break;
                            case "siteTel" :
                                step_mode = STEP_siteTel;
                                break;
                            case "siteWhlAddr" :
                                step_mode = STEP_siteWhlAddr;
                                break;
                            case "rdnWhlAddr" :
                                step_mode = STEP_rdnWhlAddr;
                                break;
                            case "bplcNm" :
                                //업체명
                                step_mode = STEP_bplcNm;
                                break;
                            case "x" :
                                //x좌표
                                step_mode = STEP_x;
                                break;
                            case "y" :
                                //y좌표
                                step_mode = STEP_y;
                                break;
                            default:
                                step_mode = STEP_NONE;
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = xmlPullParser.getName();
                        if(endTag.equals("row"))
                        {
                            //하나의 블록이 끝남
                            //리스트에 해당 company 등록하거나 또는 map에 띄우기
                            if(trdStateGbn.equals("01"))     //영업 정상일 때
                            {
                                c = new Company(opnSvcId, opnSfTeamCode, mgtNo, trdStateGbn, siteTel, siteWhlAddr, rdnWhlAddr, bplcNm, x, y);
                                makeMarker(c);
                            }

                        }
                        step_mode = STEP_NONE;

                        break;
                    case XmlPullParser.TEXT:
                        String text = xmlPullParser.getText();
                        switch (step_mode)
                        {
                            case STEP_opnSvcId :
                                opnSvcId = text;
                                break;
                            case STEP_opnSfTeamCode :
                                opnSfTeamCode = Integer.parseInt(text);
                                break;
                            case STEP_mgtNo :
                                mgtNo = text;
                                break;
                            case STEP_trdStateGbn :
                                trdStateGbn = text;
                                break;
                            case STEP_siteTel :
                                siteTel = text;
                                break;
                            case STEP_siteWhlAddr :
                                siteWhlAddr = text;
                                break;
                            case STEP_rdnWhlAddr :
                                rdnWhlAddr = text;
                                break;
                            case STEP_bplcNm :
                                bplcNm = text;
                                break;
                            case STEP_x :
                                x = Double.parseDouble(text);
                                break;
                            case STEP_y :
                                y = Double.parseDouble(text);
                                break;
                        }
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
        //marker에 company데이터 전송
        marker.setUserObject(company);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(gwsPt.y, gwsPt.x));      //      mapPointWithGeoCoord
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);
    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        //마커 클릭 이벤트
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //말풍선 클릭 이벤트
        //activity_review.xml을 띄우기
        //넘겨줄 정보
        //1. 선택된 업체 ID값
        Company getCompany = (Company)mapPOIItem.getUserObject();

        Intent reviewIntent = new Intent(this, ReviewActivity.class);

        //PK
        reviewIntent.putExtra("opnSvcId",getCompany.getOpnSvcId());
        reviewIntent.putExtra("opnSfTeamCode",getCompany.getOpnSfTeamCode());
        reviewIntent.putExtra("mgtNo",getCompany.getMgtNo());
        //PK

        reviewIntent.putExtra("bplcNm",getCompany.getBplcNm());
        reviewIntent.putExtra("rdnWhlAddr",getCompany.getRdnWhlAddr());
        reviewIntent.putExtra("siteTel",getCompany.getSiteTel());
        startActivity(reviewIntent);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
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
}