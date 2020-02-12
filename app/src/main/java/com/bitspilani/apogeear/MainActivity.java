package com.bitspilani.apogeear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavigationTabBar bottomNavigationView = (NavigationTabBar) findViewById(R.id.bottom_nav);

        final ArrayList<NavigationTabBar.Model> models5 = new ArrayList<>();
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person), ContextCompat.getColor(this, R.color.back_shade1))
                        .badgeTitle("Profile")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.maps_icon), ContextCompat.getColor(this, R.color.back_shade1))
                        .badgeTitle("Map")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home), ContextCompat.getColor(this, R.color.back_shade1))
                        .badgeTitle("Home")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_leaderboard), ContextCompat.getColor(this, R.color.back_shade1))
                        .badgeTitle("Leaderboard")
                        .title("title")
                        .build()
        );
        models5.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_more), ContextCompat.getColor(this, R.color.back_shade1)
                )
                        .badgeTitle("More")
                        .title("title")
                        .build()
        );
        bottomNavigationView.setModels(models5);

        // bottomNavigationView=findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewpager);

        //  bottomNavigationView.setSelectedItemId(R.id.home);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(2);

        bottomNavigationView.setViewPager(viewPager, 1);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                for (int i = 0; i < bottomNavigationView.getModels().size(); i++) {
                    bottomNavigationView.getModels().get(i).showBadge();
                }
                bottomNavigationView.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        bottomNavigationView.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < bottomNavigationView.getModels().size(); i++) {
                    final NavigationTabBar.Model model = bottomNavigationView.getModels().get(i);
                    bottomNavigationView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
        viewPager.setCurrentItem(2);
    }


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




