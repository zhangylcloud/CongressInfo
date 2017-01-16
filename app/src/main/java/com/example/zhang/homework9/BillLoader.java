package com.example.zhang.homework9;
import android.annotation.TargetApi;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;
import android.content.AsyncTaskLoader;

/**
 * Created by zhang on 11/30/2016.
 */
@TargetApi(11)
public class BillLoader extends AsyncTaskLoader<List<Bill>> {
    private String mUrl;
    public BillLoader(Context context, String url){

        super(context);
        //Log.d("zyl", "onCreate 10");
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<Bill> loadInBackground() {
        //Log.d("zyl", "onCreate 8");
        if(mUrl == null){
            return null;
        }
        List<Bill> bills = QueryUtils.fetchBillData(mUrl);
        //Log.d("zyl", "onCreate 9");
        return bills;
    }

}
