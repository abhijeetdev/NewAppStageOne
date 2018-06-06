package com.example.android.newappstageone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhijeetb on 05/06/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News news = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
        TextView sectionNameTextView = listItemView.findViewById(R.id.section_name_text_view);
        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);

        titleTextView.setText(news.getTitle());
        sectionNameTextView.setText(news.getSectionName());
        dateTextView.setText(news.getDate());

        return listItemView;
    }
}
