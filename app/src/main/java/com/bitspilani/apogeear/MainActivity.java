package com.bitspilani.apogeear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.bitspilani.apogeear.Adapters.ViewPagerAdapter;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    Snackbar snackbar;
    ViewPagerAdapter viewPagerAdapter;
    CoordinatorLayout coordinatorLayout;

    public static int TYPE_SLOW = -1;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    BubbleNavigationLinearView bubbleNavigationLinearView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        registerInternetCheckReceiver();
        bubbleNavigationLinearView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewpager);
        coordinatorLayout=findViewById(R.id.myCoordinatorLayout);
        //  bottomNavigationView.setSelectedItemId(R.id.home);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(2);

        viewPager.setOffscreenPageLimit(2);
        //viewPager.setPageTransformer(true,new FadeOutTransformation());

        bubbleNavigationLinearView.setCurrentActiveItem(2);
        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position,true);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                bubbleNavigationLinearView.setCurrentActiveItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home :
//                        viewPager.setCurrentItem(2);
//                        break;
//                    case R.id.map:
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case R.id.profile:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.more:
//                        viewPager.setCurrentItem(4);
//                        break;
//                    case R.id.leaderboard:
//                        viewPager.setCurrentItem(3);
//                        break;
//                }
//                return false;
//            }});
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//            @Override
//            public void onPageSelected(int position) {
//                bottomNavigationView.getMenu().getItem(position).setChecked(true);
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    private boolean loadFragment(Fragment fragment) {
//        //switching fragment
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragment)
//                    .commit();
//            return true;
//        }
//        return false;
//    }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem()!=2)
            viewPager.setCurrentItem(2);
        else
            super.onBackPressed();
    }

    /**
     *  Method to register runtime broadcast receiver to show snackbar alert for internet connection..
     */
    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }

    /**
     *  Runtime Broadcast receiver inner class to capture internet connectivity events
     */
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status,false);
        }
    };

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == TYPE_WIFI) {
                if(activeNetwork.getSubtype()== TelephonyManager.NETWORK_TYPE_CDMA)
                    return TYPE_SLOW;
                return TYPE_WIFI;
            }

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if(activeNetwork.getSubtype()== TelephonyManager.NETWORK_TYPE_CDMA)
                    return TYPE_SLOW;
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }else if (conn==TYPE_SLOW){
            status = "Poorly connected to Internet";
        }
        return status;
    }
    private void setSnackbarMessage(String status,boolean showBar) {
        String internetStatus="";
        if(status.equalsIgnoreCase("Wifi enabled")||status.equalsIgnoreCase("Mobile data enabled")){
            internetStatus="Online";
        }else if (status.equalsIgnoreCase("Poorly connected to Internet")){
            internetStatus="Poor Connection";
        }
        else {
            internetStatus="Offline";
        }
        snackbar = Snackbar
                .make(coordinatorLayout, internetStatus, Snackbar.LENGTH_SHORT);
        if(internetStatus.equals("Online"))
            snackbar.setBackgroundTint(Color.parseColor("#4ECE60"));

        snackbar.show();
    }

}




