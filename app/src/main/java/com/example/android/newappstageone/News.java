package com.example.android.newappstageone;

/**
 * Created by abhijeetb on 05/06/2018.
 */

public class News {
    private String mTitle;
    private String mDate;
    private String mUrl;
    private String mSectionName;

    public News(String title, String date, String url, String sectionName) {
        mTitle = title;
        mDate = date;
        mUrl = url;
        mSectionName = sectionName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getSectionName() {
        return mSectionName;
    }
}
