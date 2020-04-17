package com.bjshDkl.agriculture.weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bjshDkl.agriculture.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeeklyWeatherFragment extends Fragment {

    private RecyclerView rvListWeather;
    private ArrayList<Weather> listWeather;
    private RecyclerAdapterWeather adapterWeather;
    private final String city = "kathmandu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listWeather = new ArrayList<>();
        new FetchWeeklyWeatherTask(this.getActivity(), city).execute();

        View rootView = inflater.inflate(R.layout.fragment_weekly_weather, container, false);

        rvListWeather = (RecyclerView) rootView.findViewById(R.id.rvListWeather);
        adapterWeather = new RecyclerAdapterWeather(getActivity(), listWeather);
        rvListWeather.setAdapter(adapterWeather);
        rvListWeather.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListWeather.setNestedScrollingEnabled(false);
        rvListWeather.setHasFixedSize(false);
        return rootView;
    }

    private class FetchWeeklyWeatherTask extends AsyncTask<Void, Void, JSONArray> {
//        private String OPEN_WEATHER_MAP_API ="http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric";
        private String OPEN_WEATHER_MAP_API ="http://api.openweathermap.org/data/2.5/forecast?id=524901&q=kathmandu&APPID=47387e868a2b213ca74ae5a76da4f953      ";
        private ProgressDialog progressDialog;
        private Activity activity;
        private String city;

        public FetchWeeklyWeatherTask(Activity activity, String city) {
            this.activity = activity;
            this.city = city;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            try {
                URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("x-api-key", activity.getString(R.string.open_weather_maps_app_id));

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer json = new StringBuffer(1024);
                String tmp;
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());

                if (data.getInt("cod") != 200) {
                    return null;
                }

                return data.getJSONArray("list");
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            progressDialog.dismiss();

            try {
                if (jsonArray == null)
                    Toast.makeText(activity, activity.getString(R.string.error_location), Toast.LENGTH_LONG).show();
                else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Weather newWeather = new Weather();
                        newWeather.parseFromWeekly(jsonArray.getJSONObject(i));
                        listWeather.add(newWeather);
                    }
                    adapterWeather.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
