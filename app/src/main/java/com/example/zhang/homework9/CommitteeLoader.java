package com.example.zhang.homework9;

import android.content.AsyncTaskLoader;

/**
 * Created by zhang on 11/30/2016.
 */
import android.annotation.TargetApi;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

@TargetApi(11)
public class CommitteeLoader extends AsyncTaskLoader<List<Committee>>{
    private String mUrl;
    public CommitteeLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<Committee> loadInBackground(){
        if(mUrl == null){
            return null;
        }

        List<Committee> committees = QueryUtils.fetchCommitteeData(mUrl);
        return committees;
    }
}
