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

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> Fragment = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Profile();
            case 1:
                return new Map();
            case 2 :
                return new Home();
            case 3:
                return new Leaderboard();
            case 4:
                return new More();
            default:
                return new Home();
        }
    }

    public void add(Fragment Frag) {
        Fragment.add(Frag);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
