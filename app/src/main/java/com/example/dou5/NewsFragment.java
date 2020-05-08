package com.example.dou5;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class NewsFragment extends Fragment {
    private RecyclerView recyclerView1;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = rootView.findViewById(R.id.progressBar1);
        recyclerView1 =  rootView.findViewById(R.id.recyclerView1);

        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ParseAdapter(parseItems, getActivity());
        recyclerView1.setAdapter(adapter);
        Content content = new Content();
        content.execute();

        return rootView ;

    }
    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url = "https://dou-ufa.ru/news";

                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.row.item");
                int size = data.size();
                Log.d("doc", "doc: "+doc);
                Log.d("data", "data: "+data);
                Log.d("size", ""+size);
                for (int i = 0; i < size; i++) {
                    String imgUrl = data.select("div.row.item")
                            .select("img")
                            .eq(i)
                            .attr("src");

                    String title = data.select("h4.title")
                            .select("a")
                            .eq(i)
                            .text();
                    String detailUrl = data.select("h4.title")
                            .select("a")
                            .eq(i)
                            .attr("href");

                    parseItems.add(new ParseItem(imgUrl, title, detailUrl));
                    Log.d( "items", "img: " + imgUrl + " . title: " + title+". detailUrl"+detailUrl);

                }

            } catch (IOException e) {
                e.printStackTrace();

            }


            return null;
        }
    }
}


