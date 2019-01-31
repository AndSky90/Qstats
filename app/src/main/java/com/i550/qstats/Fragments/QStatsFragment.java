package com.i550.qstats.Fragments;

import androidx.fragment.app.Fragment;

public class QStatsFragment extends Fragment {

    public QStatsFragment() { }

    static int NUMBER_SELECTED_CHAMPION = 0;


    public static QStatsFragment newStatsFragment(int page) {
        switch (page) {
            case 0:
                return new FragmentGlobal();
            case 1:
                return new FragmentModes();
            case 2:
                return new FragmentMedals();
            case 3:
                return new FragmentWeapons();
            case 4:
                return new FragmentMatches();
            default:
                return null;
        }
    }
}






