package com.bjshDkl.agriculture.insectDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.resource.model.MinimizingTechniqueModel;
import com.squareup.picasso.Picasso;

public class InsectMinimizeDetailActivity extends AppCompatActivity {

    MinimizingTechniqueModel minimizingTechniqueModel;

    ImageView imageView;
    TextView title;
    TextView description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_minimize_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        minimizingTechniqueModel = (MinimizingTechniqueModel) getIntent().getSerializableExtra("data");

        imageView = (ImageView) findViewById(R.id.imageView);
        title = (TextView) findViewById(R.id.title);
        description = (TextView)findViewById(R.id.description);

        title.setText(minimizingTechniqueModel.getTitle());
        description.setText(minimizingTechniqueModel.getDescription());
        Picasso.get()
                .load(minimizingTechniqueModel.getImage())
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
