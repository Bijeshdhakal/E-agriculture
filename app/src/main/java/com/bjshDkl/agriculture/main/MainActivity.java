package com.bjshDkl.agriculture.main;

import android.content.Intent;
import android.os.Bundle;

import com.bjshDkl.agriculture.BuildConfig;
import com.bjshDkl.agriculture.R;
import com.bjshDkl.agriculture.analysis.AnalyisActivity;
import com.bjshDkl.agriculture.forum.ForumFragment;
import com.bjshDkl.agriculture.forum.chat.login.LoginActivity;
import com.bjshDkl.agriculture.newsOffers.NewsOfferFragment;
import com.bjshDkl.agriculture.price.PriceFragment;
import com.bjshDkl.agriculture.resource.ResourceFragment;
import com.bjshDkl.agriculture.utils.HandleSSLConnection;
import com.bjshDkl.agriculture.weather.WeatherFragment;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.material.navigation.NavigationView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;
import com.onesignal.OneSignal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static SpaceNavigationView spaceNavigationView;
    private FragmentManager fragmentManager;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HandleSSLConnection.handleSSLHandshake();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, new NewsOfferFragment()).commit();


        bindActivity();

    }

    private void bindActivity() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_sort_black_24dp);
        toggle.setDrawerSlideAnimationEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }

        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        setUpBottomNavigation();
    }

    private void setUpBottomNavigation() {

        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("Weather", R.drawable.ic_weather));
        spaceNavigationView.addSpaceItem(new SpaceItem("Pricings", R.drawable.ic_pricing));
        spaceNavigationView.addSpaceItem(new SpaceItem("Forum", R.drawable.ic_forum));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setActiveSpaceItemColor(getResources().getColor(R.color.colorPrimary));
        spaceNavigationView.setSpaceBackgroundColor(getResources().getColor(R.color.space_white));

        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_resource);
        spaceNavigationView.setCentreButtonColor(getResources().getColor(R.color.colorPrimary));
        spaceNavigationView.setActiveCentreButtonIconColor(getResources().getColor(R.color.colorWhite));
        spaceNavigationView.setInActiveCentreButtonIconColor(getResources().getColor(R.color.colorWhite));

        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(MainActivity.this, "Resources", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemName, Toast.LENGTH_SHORT).show();
            }
        });


        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                fragmentManager.beginTransaction().replace(R.id.mainFrame, new ResourceFragment()).commit();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    fragmentManager.beginTransaction().replace(R.id.mainFrame, new NewsOfferFragment()).commit();

                } else if (itemIndex == 1) {
                    fragmentManager.beginTransaction().replace(R.id.mainFrame, new WeatherFragment()).commit();

                } else if (itemIndex == 2) {
                    fragmentManager.beginTransaction().replace(R.id.mainFrame, new PriceFragment()).commit();

                } else if (itemIndex == 3) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    fragmentManager.beginTransaction().replace(R.id.mainFrame, new ForumFragment()).commit();

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navHome) {
            fragmentManager.beginTransaction().replace(R.id.mainFrame, new NewsOfferFragment()).commit();
        } else if (id == R.id.navWeather) {
            fragmentManager.beginTransaction().replace(R.id.mainFrame, new WeatherFragment()).commit();

        } else if (id == R.id.navResource) {
            fragmentManager.beginTransaction().replace(R.id.mainFrame, new ResourceFragment()).commit();

        } else if (id == R.id.navPricing) {
            fragmentManager.beginTransaction().replace(R.id.mainFrame, new PriceFragment()).commit();

        } else if (id == R.id.navForum) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        } else if (id == R.id.navPrediction) {
            startActivity(new Intent(MainActivity.this, AnalyisActivity.class));

        }


        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
