package com.bjshDkl.agriculture.newsOffers;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.forum.chat.login.LoginActivity;
import com.bjshDkl.agriculture.main.MainActivity;
import com.bjshDkl.agriculture.newsOffers.adapters.ProgramViewPagerAdapter;
import com.bjshDkl.agriculture.newsOffers.model.ProgramModel;
import com.bjshDkl.agriculture.radio.RadioActivity;
import com.bjshDkl.agriculture.resource.adapter.NewsReyclerViewAdapter;
import com.bjshDkl.agriculture.newsOffers.model.NewsModel;
import com.bjshDkl.agriculture.utils.Constants;
import com.bjshDkl.agriculture.weather.WeatherFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsOfferFragment extends Fragment implements View.OnClickListener {

    ViewPager programsViewPager;
    DotsIndicator programDotsIndicator;

    FirebaseDatabase database;
    DatabaseReference programDb;

    ArrayList<ProgramModel> programModels = new ArrayList<>();


    RecyclerView newsRecyclerView;
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    private RequestQueue newsRequestQueue;
    private StringRequest newsStringRequest;

    LinearLayout radioLL;
    LinearLayout forumLL;
    LinearLayout weatherLL;

    private FragmentManager fragmentManager;

    public NewsOfferFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_offer, container, false);

        bindFragment(rootView);

        fragmentManager = getActivity().getSupportFragmentManager();


        initPrograms();
        initNews();

        return rootView;
    }

    private void initNews() {
        newsRequestQueue = Volley.newRequestQueue(getActivity());
        newsStringRequest = new StringRequest(Request.Method.GET, Constants.newsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject baseObject = new JSONObject(response);
                    JSONArray articlesArray = baseObject.getJSONArray("articles");

                    for (int i = 0; i < articlesArray.length(); i++) {
                        JSONObject newsObject = articlesArray.getJSONObject(i);

                        NewsModel newsModel = new NewsModel();
                        newsModel.setAuthor(newsObject.getString("author"));
                        newsModel.setTitle(newsObject.getString("title"));
                        newsModel.setDescription(newsObject.getString("description"));
                        newsModel.setImageUrl(newsObject.getString("urlToImage"));
                        newsModel.setUrl(newsObject.getString("url"));
                        newsModel.setPublishedDate(newsObject.getString("publishedAt"));

                        JSONObject source = newsObject.getJSONObject("source");
                        newsModel.setSource(source.getString("name"));

                        newsModelArrayList.add(newsModel);
                    }

                    NewsReyclerViewAdapter adapter = new NewsReyclerViewAdapter(getActivity(), newsModelArrayList);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                    newsRecyclerView.setLayoutManager(manager);
                    newsRecyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("volleyError", "Error :" + error.toString());
            }
        });

        newsRequestQueue.add(newsStringRequest);
    }


    private void initPrograms() {


        programDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ProgramModel programModel = new ProgramModel();
                    programModel = dataSnapshot1.getValue(ProgramModel.class);
                    programModels.add(programModel);
                }

                ProgramViewPagerAdapter programViewPagerAdapter = new ProgramViewPagerAdapter(getActivity(), programModels);
                programsViewPager.setAdapter(programViewPagerAdapter);

                programDotsIndicator.setViewPager(programsViewPager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void bindFragment(View rootView) {

        database = FirebaseDatabase.getInstance();
        programDb = database.getReference("events");

        programsViewPager = (ViewPager) rootView.findViewById(R.id.programsViewPager);
        programDotsIndicator = (DotsIndicator) rootView.findViewById(R.id.dots_indicator);
        newsRecyclerView = (RecyclerView) rootView.findViewById(R.id.newsRecyclerView);

        radioLL = (LinearLayout) rootView.findViewById(R.id.radioLL);
        radioLL.setOnClickListener(this);
        forumLL = (LinearLayout) rootView.findViewById(R.id.forumLL);
        forumLL.setOnClickListener(this);
        weatherLL = (LinearLayout) rootView.findViewById(R.id.weatherLL);
        weatherLL.setOnClickListener(this);
//        programModels.clear();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radioLL:
                startActivity(new Intent(getActivity(), RadioActivity.class));
                break;
            case R.id.forumLL:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;

            case R.id.weatherLL:
                fragmentManager.beginTransaction().replace(R.id.mainFrame, new WeatherFragment()).commit();
                break;
        }

    }
}
