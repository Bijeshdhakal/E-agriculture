package com.bjshDkl.agriculture.weather;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bjshDkl.agriculture.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapterWeather extends RecyclerView.Adapter<RecyclerAdapterWeather.ViewHolderWeather> {
    private Activity activity;
    private ArrayList<Weather> list;

    public RecyclerAdapterWeather(Activity activity, ArrayList<Weather> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public ViewHolderWeather onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_www, viewGroup, false);
        ViewHolderWeather holder = new ViewHolderWeather(view, activity);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderWeather viewHolder, final int i) {
        Weather weather = list.get(i);
        SimpleDateFormat df = new SimpleDateFormat("EEEE (MMMM dd, yyyy)", Locale.US);
        viewHolder.txtHour.setText(df.format(weather.getDate()));

//        double temperatureCenti = (weather.getTemperature()-273.15)*1.8;
//        viewHolder.txtTemperature.setText(weather.getTemperature() - 273.15 + " ℃");

        double temperature = (weather.getTemperature() - 273.15) ;
        String temperatureString = String.format("%.2f", temperature);
        viewHolder.txtTemperature.setText(temperatureString + " ℃");

        viewHolder.txtPressure.setText(activity.getString(R.string.weather_pressure) + ": " + weather.getPressure() + " hPa");
        viewHolder.txtHumidity.setText(activity.getString(R.string.weather_humidity) + ": " + weather.getHumidity() + " %");
        viewHolder.txtDescription.setText(weather.getDescription());
        viewHolder.txtWind.setText(activity.getString(R.string.weather_wind) + ": " + weather.getWind() + " m/s");


        if (weather.getDescription().contains("cloud")) {

            viewHolder.txtIcon.setImageResource(R.drawable.ic_cloudy);
        } else if (weather.getDescription().contains("clear")) {
            viewHolder.txtIcon.setImageResource(R.drawable.ic_sunny);


        } else if (weather.getDescription().contains("rain")) {

            viewHolder.txtIcon.setImageResource(R.drawable.ic_rain);
        } else if (weather.getDescription().contains("scattered")) {
            viewHolder.txtIcon.setImageResource(R.drawable.ic_cloudy);

        } else {
            viewHolder.txtIcon.setImageResource(R.drawable.ic_windy);
        }
//        viewHolder.txtIcon.setText(activity.getString(weather.getWeatherIconId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolderWeather extends RecyclerView.ViewHolder {
        Activity activity;
        TextView txtHour;
        TextView txtDescription, txtWind, txtPressure, txtHumidity;
        TextView txtTemperature;
        ImageView txtIcon;

        public ViewHolderWeather(View itemView, Activity activity) {
            super(itemView);
            this.activity = activity;
            txtHour = (TextView) itemView.findViewById(R.id.txtHour);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtWind = (TextView) itemView.findViewById(R.id.txtWind);
            txtPressure = (TextView) itemView.findViewById(R.id.txtPressure);
            txtHumidity = (TextView) itemView.findViewById(R.id.txtHumidity);

            txtTemperature = (TextView) itemView.findViewById(R.id.txtTemperature);
            txtIcon = (ImageView) itemView.findViewById(R.id.txtIcon);
//            txtIcon.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/"));
        }
    }
}