package com.bjshDkl.agriculture.resource;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.resource.adapter.ArticleRecyclerViewAdapter;
import com.bjshDkl.agriculture.resource.adapter.InsectMinimizingAdapter;
import com.bjshDkl.agriculture.resource.adapter.YoutubeVideoAdapter;
import com.bjshDkl.agriculture.resource.model.ArticleModel;
import com.bjshDkl.agriculture.resource.model.MinimizingTechniqueModel;
import com.bjshDkl.agriculture.resource.model.YoutubeVideo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResourceFragment extends Fragment {


    RecyclerView youTubeRecyclerView;
    RecyclerView articleRecyclerView;
    RecyclerView insectControlRecyclerView;

    ArrayList<YoutubeVideo> youtubeVideos = new ArrayList<>();
    ArrayList<ArticleModel> articleModels = new ArrayList<>();
    ArrayList<MinimizingTechniqueModel> minimizingTechniqueModels = new ArrayList<>();


    FirebaseDatabase database;
    DatabaseReference articleDB;
    DatabaseReference youtubeDB;
    DatabaseReference insectDb;


    public ResourceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_resource, container, false);

        bindFragment(rootView);

        initInsectMinimization();
        initArticle();
        initYoutubeVideos();

        return rootView;

    }

    private void initInsectMinimization() {
        insectDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot insect : dataSnapshot.getChildren()) {
                    MinimizingTechniqueModel minimizingTechniqueModel = new MinimizingTechniqueModel();
                    minimizingTechniqueModel = insect.getValue(MinimizingTechniqueModel.class);
                    minimizingTechniqueModels.add(minimizingTechniqueModel);
                }

                LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                InsectMinimizingAdapter adapter = new InsectMinimizingAdapter(getActivity(), minimizingTechniqueModels);

                insectControlRecyclerView.setLayoutManager(manager);
                insectControlRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initArticle() {

        articleDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot article : dataSnapshot.getChildren()) {
                    ArticleModel articleModel = new ArticleModel();
                    articleModel = article.getValue(ArticleModel.class);

                    articleModels.add(articleModel);
                }

                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                ArticleRecyclerViewAdapter articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(getActivity(), articleModels);

                articleRecyclerView.setLayoutManager(manager);
                articleRecyclerView.setAdapter(articleRecyclerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void initYoutubeVideos() {
        youtubeDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot youtubeDataSnapshot : dataSnapshot.getChildren()) {
                    YoutubeVideo youtubeVideo = new YoutubeVideo();
                    youtubeVideo = youtubeDataSnapshot.getValue(YoutubeVideo.class);

                    youtubeVideos.add(youtubeVideo);
                }

                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
                YoutubeVideoAdapter youtubeVideoAdapter = new YoutubeVideoAdapter(getActivity(), youtubeVideos);

                youTubeRecyclerView.setLayoutManager(gridLayoutManager);
                youTubeRecyclerView.setAdapter(youtubeVideoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void bindFragment(View rootView) {

        database = FirebaseDatabase.getInstance();
        articleDB = database.getReference("articles");
        youtubeDB = database.getReference("videos");
        insectDb = database.getReference("Insects");

        youTubeRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtubeVideosRecyclerView);
        articleRecyclerView = (RecyclerView) rootView.findViewById(R.id.articleRecyclerView);
        insectControlRecyclerView = (RecyclerView) rootView.findViewById(R.id.insectControlRecyclerView);

    }

}
