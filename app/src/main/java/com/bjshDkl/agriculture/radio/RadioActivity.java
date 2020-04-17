package com.bjshDkl.agriculture.radio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.radio.player.PlaybackStatus;
import com.bjshDkl.agriculture.radio.player.RadioManager;
import com.bjshDkl.agriculture.radio.util.Shoutcast;
import com.bjshDkl.agriculture.radio.util.ShoutcastHelper;
import com.bjshDkl.agriculture.radio.util.ShoutcastListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;


public class RadioActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;

    ImageButton trigger;

    ListView listView;

    TextView textView;

    View subPlayer;

    RadioManager radioManager;

    String streamURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_radio);

        radioManager = RadioManager.with(this);

        bindActivity();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shoutcast shoutcast = (Shoutcast) parent.getItemAtPosition(position);
                if (shoutcast == null) {

                    return;

                }

                textView.setText(shoutcast.getName());

                subPlayer.setVisibility(View.VISIBLE);


                streamURL = shoutcast.getUrl();
                Log.d("radiourl",streamURL);

                radioManager.playOrPause(streamURL);
            }
        });
    }

    private void bindActivity() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        trigger = (ImageButton) findViewById(R.id.playTrigger);
        trigger.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new ShoutcastListAdapter(this, ShoutcastHelper.retrieveShoutcasts(this)));

        textView = (TextView) findViewById(R.id.name);
        subPlayer = (View) findViewById(R.id.sub_player);

        setSupportActionBar(toolbar);



    }

    @Override
    public void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        radioManager.unbind();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        radioManager.bind();
    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Subscribe
    public void onEvent(String status) {

        switch (status) {

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();

                break;

        }

        trigger.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.ic_pause_black
                : R.drawable.ic_play_arrow_black);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.playTrigger) {
            if (TextUtils.isEmpty(streamURL))
            radioManager.playOrPause(streamURL);
        }
    }
}
