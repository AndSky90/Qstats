package com.i550.qstats;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


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