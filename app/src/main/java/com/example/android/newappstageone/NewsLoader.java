package com.example.android.newappstageone;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by abhijeetb on 05/06/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;
    private String errorMessage;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        HttpHandler sh = new HttpHandler();


        List<News> newsList = HttpHandler.fetchEarthquakeData(mUrl);
        return newsList;
    }



    @Override
    public void deliverResult(List<News> news) {
        super.deliverResult(news);

    }
}
