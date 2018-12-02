package com.i550.qstats;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.i550.qstats.databinding.TestDataBind;
import java.util.Arrays;
import java.util.List;

public class QStatsFragment extends Fragment {
    public QStatsFragment() {
    }

    private static final String TAG = "qStats";
    private int pageNumber;
    private final List<Integer> mFragmentList = Arrays.asList(R.layout.f_champions, R.layout.f_medals, R.layout.f_modes, R.layout.f_weapons, R.layout.f_matches, R.layout.f_profile_summary);

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

        if (pageNumber==0) {
            TestViewModel testModel = ViewModelProviders.of(this).get(TestViewModel.class);
            MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
            TestDataBind binding = DataBindingUtil.bind(result);
            binding.setTvm(testModel);
            binding.setVm(model);
            //binding.notify();

            /*
            binding.notifyPropertyChanged();
            можно в onResume вписать notifyDataSetChanged(у адаптера)

            какое событие произошло? модель поменялась! надо листенер изменения модели ->
           binding.executePendingBindings();

            может быть что похуй, биндинг узнал но не перерисовал, а надо перерисовать и надо нотифай для адаптера

           myViewModel.getData()
                    .observe(this, new Observer<DataGlobal>() {
                        @Override
                        public void onChanged(@Nullable DataGlobal data) {
                            if (data!=null){}
                            // Update your UI with the loaded data.
                            // Returns cached data automatically after a configuration change,
                            // and will be fired again if underlying Live Data object is modified.
                            invalidate()
                        }
                    });
*/
          }
                // ProfileActivityBinding binding = ProfileActivityBinding.inflate(layoutInflater, viewGroup, false);
                return result;
    }
}