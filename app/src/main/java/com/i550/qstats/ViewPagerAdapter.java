package com.i550.qstats;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import java.util.Arrays;
import java.util.List;
public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private final List<String> mFragmentTitleList = Arrays.asList("Champions","Medals","Modes","Weapons","Matches");

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return QStatsFragment.newStatsFragment(position);
    }

    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }



}