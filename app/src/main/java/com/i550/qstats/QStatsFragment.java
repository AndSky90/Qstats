package com.i550.qstats;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i550.qstats.databinding.FChampionsBinding;
import com.i550.qstats.databinding.FGlobalBinding;
import com.i550.qstats.databinding.FMatchesBinding;
import com.i550.qstats.databinding.FMedalsBinding;
import com.i550.qstats.databinding.FModesBinding;
import com.i550.qstats.databinding.FWeaponsBinding;
import java.util.Arrays;
import java.util.List;

public class QStatsFragment extends Fragment {
    public QStatsFragment() {}

    private int pageNumber;
    private final List<Integer> mFragmentList = Arrays.asList(R.layout.f_global, R.layout.f_medals, R.layout.f_modes, R.layout.f_weapons, R.layout.f_matches, R.layout.f_champions);

    public static QStatsFragment newStatsFragment(int page) {
        QStatsFragment fragment = new QStatsFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(mFragmentList.get(pageNumber), container, false);
    //    MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
       // switch (pageNumber) {
       //     case 0: {
          //      View result = inflater.inflate(mFragmentList.get(pageNumber), container, false);

        if(pageNumber==0) {
            MyViewModel model0 = ViewModelProviders.of(this).get(MyViewModel.class);
                FGlobalBinding binding0 = DataBindingUtil.bind(result);
                binding0.setVm(model0);}


            //case 4: {
           //     View result = inflater.inflate(mFragmentList.get(pageNumber), container, false);
               if(pageNumber==4) {MyViewModel model4 = ViewModelProviders.of(this).get(MyViewModel.class);
                FMatchesBinding binding4 = DataBindingUtil.bind(result);
                binding4.setVm(model4);
            }

          /*  case 1: {
                FMedalsBinding binding1 = DataBindingUtil.bind(result);
                binding1.setVm(model);
            }
            case 2: {
                FModesBinding binding2 = DataBindingUtil.bind(result);
                binding2.setVm(model);
            }
            case 3: {
                FWeaponsBinding binding3 = DataBindingUtil.bind(result);
                binding3.setVm(model);
            }

            case 5: {
                FChampionsBinding binding5 = DataBindingUtil.bind(result);
                binding5.setVm(model);
            }*/

            //    TestViewModel testModel = ViewModelProviders.of(this).get(TestViewModel.class);
            //    binding.setTvm(testModel);
       // }
        return result;
    }
}



        /*
        можно в onResume вписать notifyDataSetChanged(у адаптера)
        какое событие произошло? модель поменялась! надо листенер изменения модели ->
        binding.executePendingBindings()
        binding.notifyPropertyChanged();
        binding.notify();
        может быть что похуй, биндинг узнал но не перерисовал, а надо перерисовать и надо нотифай для адаптера
        ProfileActivityBinding binding = ProfileActivityBinding.inflate(layoutInflater, viewGroup, false);
        */


