package org.techtown.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.techtown.diary.data.WeatherItem;
import org.techtown.diary.data.WeatherResult;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener, OnRequestListener, MyApplication.OnResponseListener{

    private static final String TAG = "MainActivity";

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    BottomNavigationView bottomNavigation;

    Location currentLocation;
    GPSListener gpsListener;

    String currentWeather;
    String currentAddress;
    String currentDateString;
    Date currentDate;

    int locationCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndPermission.with(this)
                .runtime()
                .permission(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)
                .start();

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        Toast.makeText(getApplicationContext(), "첫 번째 탭 선택됨.", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

                        return true;
                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(), "두 번째 탭 선택됨.", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();

                        return true;
                    case R.id.tab3:
                        Toast.makeText(getApplicationContext(), "세 번째 탭 선택됨.", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();

                        return true;
                }
                return false;
            }
        });
    }

    public void onTabSelected(int position){
        if(position == 0){
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if(position == 1){
            bottomNavigation.setSelectedItemId(R.id.tab2);
        } else if(position == 2){
            bottomNavigation.setSelectedItemId(R.id.tab3);
        }
    }

    public void onRequest(String command){
        if(command != null ){
            if(command.equals("getCurrentLocation")){
                getCurrentLocation();
            }
        }
    }

    public void getCurrentLocation(){
        currentDate = new Date();
        currentDateString  = AppConstants.dateFormat3.format(currentDate);
        if(fragment2 != null ){
            fragment2.setDateString(currentDateString);
        }

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            currentLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(currentLocation!= null){
                double latitude = currentLocation.getLatitude();
                double longitude = currentLocation.getLongitude();

                String message = "Last Loction -> Latitude : "+latitude +"\nLongitude : "+longitude;
                println(message);

                getCurrentWeather();
                getCurrentAddress();
            }

            gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
            println("Current location requested");
        } catch(SecurityException e){
            e.printStackTrace();
        }
    }

    public void stopLocationService(){
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            manager.removeUpdates(gpsListener);
            println("Current location requested");
        } catch(SecurityException e){
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            currentLocation = location;

            locationCount++;

            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "Current Loction -> Latitude : "+latitude +"\nLongitude : "+longitude;
            println(message);

            getCurrentWeather();
            getCurrentAddress();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    public void getCurrentAddress(){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(),1);
        } catch(Exception e){
            e.printStackTrace();
        }

        if(addresses != null && addresses.size()>0){
            currentAddress = null;
            Address address = addresses.get(0);

            if(address.getLocality() != null){
                currentAddress = address.getLocality();
            }

            if(address.getSubLocality() != null){
                if(currentAddress != null ){
                    currentAddress += " "+address.getSubLocality();
                } else {
                    currentAddress = address.getSubLocality();
                }
            }

            String adminArea = address.getAdminArea();
            String country = address.getCountryName();
            println("Address : "+country + " " +adminArea + " "+ currentAddress);

            if(fragment2 != null){
                fragment2.setAddress(currentAddress);
            }
        }
    }

    public void getCurrentWeather(){
        Map<String, Double> gridMap = GridUtil.getGrid(currentLocation.getLatitude(), currentLocation.getLongitude());

        double gridX = gridMap.get("x");
        double gridY = gridMap.get("y");
        println("x -> "+gridX +", y -> "+gridY);

        sendLocalWeatherReq(gridX, gridY);
    }

    public void sendLocalWeatherReq(double gridX, double gridY){
        String url ="https://www.kma.go.kr/wid/queryDFS.jsp";

        url += "?gridx="+Math.round(gridX);
        url += "&gridy="+Math.round(gridY);

        Map<String, String> params = new HashMap<String, String>();
        MyApplication.send(AppConstants.REQ_WEATHER_BY_GRID, Request.Method.GET, url, params, this);
    }

    public void processResponse(int requestCode, int responseCode, String response){
        if(responseCode == 200){
            if(requestCode == AppConstants.REQ_WEATHER_BY_GRID){
                XmlParserCreator parserCreator = new XmlParserCreator() {
                    @Override
                    public XmlPullParser createParser() {
                        try {
                            return XmlPullParserFactory.newInstance().newPullParser();
                        } catch (Exception e){
                            throw new RuntimeException(e);
                        }
                    }
                };

                GsonXml gsonXml = new GsonXmlBuilder()
                        .setXmlParserCreator(parserCreator)
                        .setSameNameLists(true)
                        .create();

                WeatherResult weather = gsonXml.fromXml(response, WeatherResult.class);

                try {
                    Date tmDate = AppConstants.dateFormat.parse(weather.header.tm);
                    String tmDateText = AppConstants.dateFormat2.format(tmDate);
                    println("기준 시간 : "+tmDateText);

                    for(int i=-0; i<weather.body.datas.size(); i++){
                        WeatherItem item = weather.body.datas.get(i);
                        println("#"+i+" 시간 : "+item.hour+"시, "+ item.day+"일째");
                        println("  날씨 : "+item.wfKor);
                        println("  기온 : "+item.temp+" C");
                        println(  "강수확률 : "+item.pop+"%");

                        println("debug 1 : " + (int) Math.round(item.ws*10));
                        float ws = Float.valueOf(String.valueOf((int)Math.round(item.ws*10)))/10.0f;
                        println("  풍속 : "+ws+" m/s");
                    }

                    WeatherItem item = weather.body.datas.get(0);
                    currentWeather = item.wfKor;
                    if(fragment2 != null){
                        fragment2.setWeather(item.wfKor);
                    }

                    if(locationCount > 0){
                        stopLocationService();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            } else {
                println("Unknown request code : "+requestCode);
            }

        } else {
            println("Failure response code : "+ responseCode);
        }
    }

    private void println(String data){
        Log.d(TAG, data);
    }
}