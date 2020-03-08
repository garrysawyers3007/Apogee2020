package com.bitspilani.apogeear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bitspilani.apogeear.Adapters.ViewPagerAdapter;
import com.bitspilani.apogeear.Fragments.Home;
import com.bitspilani.apogeear.Fragments.Leaderboard;
import com.bitspilani.apogeear.Fragments.Map;
import com.bitspilani.apogeear.Fragments.More;
import com.bitspilani.apogeear.Fragments.Profile;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.blox.graphview.Graph;
import de.blox.graphview.ViewHolder;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    //BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    BubbleNavigationLinearView bubbleNavigationLinearView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        fluidBottomNavigation.items =
//                listOf(
//                        FluidBottomNavigationItem(
//                                getString(R.string.news),
//                                ContextCompat.getDrawable(this, R.drawable.ic_news)),
//                        FluidBottomNavigationItem(
//                                getString(R.string.inbox),
//                                ContextCompat.getDrawable(this, R.drawable.ic_inbox)),
//                        FluidBottomNavigationItem(
//                                getString(R.string.calendar),
//                                ContextCompat.getDrawable(this, R.drawable.ic_calendar)),
//                        FluidBottomNavigationItem(
//                                getString(R.string.chat),
//                                ContextCompat.getDrawable(this, R.drawable.ic_chat)),
//                        FluidBottomNavigationItem(
//                                getString(R.string.profile),
//                                ContextCompat.getDrawable(this, R.drawable.ic_profile)));


        // bottomNavigationView=findViewById(R.id.bottom_nav);
        bubbleNavigationLinearView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewpager);

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

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem()!=2)
            viewPager.setCurrentItem(2);
        else
            super.onBackPressed();
    }

}




