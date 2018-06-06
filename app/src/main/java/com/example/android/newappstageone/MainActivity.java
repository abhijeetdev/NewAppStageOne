package com.example.android.newappstageone;

import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String TAG = HttpHandler.class.getSimpleName();

    private static final int NEWS_LOADER_ID = 1;
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/search?q=debates&api-key=test";

    private ArrayAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list_view);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
                        mAdapter.clear();
                        LoaderManager loaderManager = getSupportLoaderManager();
                        loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);

                    }
                }
        );

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getItemAtPosition(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                startActivity(intent);

            }
        });
        checkNetworkAvailability();

    }

    private void checkNetworkAvailability() {
        if (isOnline()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_network,
                    Toast.LENGTH_LONG).show();
        }
    }


    public boolean isOnline() {
        Boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        mAdapter.clear();
        swipeRefreshLayout.setRefreshing(false);

        if (newsList != null && !newsList.isEmpty()) {
            mAdapter.addAll(newsList);
        } else {
            checkNetworkAvailability();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
