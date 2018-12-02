package com.i550.qstats;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class Test extends BaseObservable {
    Test(){}

    private static String jopa = "kgkgy";

    void setJopa(String jopa) {
        this.jopa = jopa;
        notifyPropertyChanged(BR.tvm);
    }

@Bindable
    public String getJopa() {
        return jopa;
    }



}
