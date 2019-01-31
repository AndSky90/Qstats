package com.i550.qstats.Fragments;

public class SelectorChampionFragment extends QStatsFragment {
/*
    public SelectorChampionFragment(){}        //перегрузить на переопределенный конструктор потом

    private SelectorChampionFragment instance;

    public SelectorChampionFragment getInstance()
    {
        if (instance==null)
            instance = new SelectorChampionFragment();
        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.f_champions_selector, container, false);
        ListView listViewChampions = result.findViewById(R.id.list_view_champions);
        ArrayAdapter<String> championsAdapter = new ChampionsAdapter(getContext(), 0, championNames, NUMBER_SELECTED_CHAMPION);
        if (listViewChampions != null) {
            listViewChampions.setAdapter(championsAdapter);
            listViewChampions.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                NUMBER_SELECTED_CHAMPION = i;
                Log.i("qStatsFragment", "NUMBER_SELECTED_CHAMPION = " + NUMBER_SELECTED_CHAMPION);
                ViewPager vp = getActivity().findViewById(R.id.viewpager);
                vp.getAdapter().notifyDataSetChanged();
            });
        }
        return result;
    }*/
}
