package com.i550.qstats;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


public class TestViewModel extends AndroidViewModel {


    public TestViewModel(@NonNull Application application) {
        super(application);
    }
        private static final String TAG = "qStatsA";

        private static Test test = new Test();

        public static Test getTest () {
            return test;
        }

        public static void setTest (Test test){
            TestViewModel.test = test;
        }

       /* public static void updateTest (){
            String u = test.getJopa();
            test.setJopa(u + "14");
            setTest(test);
            Log.i(TAG, "update14" +"\n" );

        }*/
    }

