package com.i550.qstats;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;
import android.util.Log;


public class MyViewModel extends AndroidViewModel /*implements Observable */{

    private static final String TAG = "qS";

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    public static DataGlobal dataGlobal = new DataGlobal();

    public static DataGlobal getDataGlobal() {
        return dataGlobal;
    }

    public static void setDataGlobal(DataGlobal dataGlobal) {
        MyViewModel.dataGlobal = dataGlobal;
        Log.i(TAG, "Set Object: " + MyViewModel.dataGlobal.getTotal_championusage() +"\n" );
        ChangeChampionusage cc = MyViewModel.getDataGlobal().getChange_championusage();
        cc.setDOOM_SLAYER("sdfg");
        MyViewModel.getDataGlobal().setChange_championusage(cc);
      // notifyChange();

    }


/*    private transient PropertyChangeRegistry mCallbacks;
    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    public void notifyChange() {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, 0, null);
    }*/






}
/*
  public LiveData<DataGlobal> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadDataFromServer();
            Log.i(TAG, "String Object: " + data);
        }
        return data;
    }


    public void loadDataFromServer() {
        new AsyncTaskGlobal().execute();
    }
    private void loadData() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                ArrayList<String> result = new ArrayList<>(0);
                String myFeed = getApplication().getString(R.string.my_feed);
                try {
                    URL url = new URL(myFeed);
                    // Create a new HTTP URL connection
                    URLConnection connection = url.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream in = httpConnection.getInputStream();
                        // Process the input stream to generate our result list
                        // result = processStream(in);
                    }
                    httpConnection.disconnect();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "Malformed URL Exception.", e);
                } catch (IOException e) {
                    Log.e(TAG, "IO Exception.", e);
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<String> data) {
                // Update the Live Data data value.
                data.setValue(data);
            }
        }.execute();
    }







}*/
