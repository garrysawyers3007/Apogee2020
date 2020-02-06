package com.bitspilani.apogeear.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bitspilani.apogeear.Fragments.Home;
import com.bitspilani.apogeear.Fragments.Leaderboard;
import com.bitspilani.apogeear.Fragments.Map;
import com.bitspilani.apogeear.Fragments.More;
import com.bitspilani.apogeear.Fragments.Profile;
import com.bitspilani.apogeear.R;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        switch (position){
            case R.id.home :
                fragment=new Home();
                break;

            case R.id.map:
                fragment=new Map();
                break;
            case R.id.profile:
                fragment=new Profile();
                break;

            case R.id.more:
                fragment=new More();
                break;
            case R.id.leaderboard:
                fragment=new Leaderboard();
                break;
            default:
                fragment=new Home();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
