package com.example.a7;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<News> {
    private Context context;
    private int newsLayoutID;


    public NewsListAdapter(@NonNull Context context, int newsLayoutID, @NonNull List<News> news) {
        super(context, newsLayoutID, news);

        this.context = context;
        this.newsLayoutID = newsLayoutID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(newsLayoutID, parent, false);
        }

         TextView tvTitle = convertView.findViewById(R.id.tv_title);
         TextView tvDescription = convertView.findViewById(R.id.tv_description);
         TextView tvYear = convertView.findViewById(R.id.tv_year);

         News news = getItem(position);

//         tvTitle.setText(news.getTitle() + "," + news.getDescription());   we can do this too
        //and this tvYear.setText(Integer.toString(news.getPubDate()));
         tvTitle.setText(news.getTitle());
         tvDescription.setText(news.getDescription());
         tvYear.setText(news.getPubDate());


        return convertView;
    }
}

//tv_title