package com.example.zhang.homework9;

import android.annotation.TargetApi;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by zhang on 11/29/2016.
 */
@TargetApi(11)
public class LegislatorLoader extends AsyncTaskLoader<List<Legislator>>{
    private String mUrl;
    public LegislatorLoader(Context context, String url){

        super(context);
        //Log.d("zyl", "onCreate 10");
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Legislator> loadInBackground() {
        //Log.d("zyl", "onCreate 8");
        if(mUrl == null){
            return null;
        }
        List<Legislator> legislators = QueryUtils.fetchLegislatorData(mUrl);
        //Log.d("zyl", "onCreate 9");
        return legislators;
    }
}
