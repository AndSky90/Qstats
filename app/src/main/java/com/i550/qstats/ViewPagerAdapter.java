package com.i550.qstats;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.i550.qstats.Fragments.QStatsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return QStatsFragment.newStatsFragment(position);
    }

    public int getItemPosition(@NonNull Object object) {        //здесь нужна магия чтобы обновлялись не все страницы
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 5;
    }
}