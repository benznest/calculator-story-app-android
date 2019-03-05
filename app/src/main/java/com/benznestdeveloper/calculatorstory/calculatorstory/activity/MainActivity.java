package com.benznestdeveloper.calculatorstory.calculatorstory.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyAdmob;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyCache;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyCalculatorStartup;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.AboutDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.SettingDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.ConstantFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.DateBetweenCalculationFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.EquationFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.ScientificCalculatorFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.StandardCalculatorFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.AreaConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.DataConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.EnergyConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.LengthConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.PowerConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.PressureConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.SpeedConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.TemperatureConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.TimeConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.VolumeConverterFragment;
import com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter.WeightConverterFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import static com.benznestdeveloper.calculatorstory.calculatorstory.MyConstantApp.TAG_FRAGMENT_CONVERTER_LENGTH;
import static com.benznestdeveloper.calculatorstory.calculatorstory.MyConstantApp.TAG_FRAGMENT_STANDARD_CALCULATOR;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AdView adViewMobile;
    private InterstitialAd intAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
        initAds();

        if (savedInstanceState == null) {
            int startup = MyCache.getStartupCalculator(MainActivity.this);
            if (startup == MyCalculatorStartup.STANDARD_CALCULATOR) {
                setFragment(StandardCalculatorFragment.newInstance(), TAG_FRAGMENT_STANDARD_CALCULATOR);
            } else if (startup == MyCalculatorStartup.SCIENTIFIC_CALCULATOR) {
                setFragment(ScientificCalculatorFragment.newInstance());
            } else if (startup == MyCalculatorStartup.BETWEEN_DATE_CALCULATION) {
                setFragment(DateBetweenCalculationFragment.newInstance());
            }
        }
    }

    private void initAds() {
        adViewMobile = (AdView) findViewById(R.id.adView_mobile);
        adViewMobile.loadAd(MyAdmob.getAdsDefault());
        adViewMobile.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adViewMobile.setVisibility(View.GONE);
            }
        });

        AdRequest.Builder adBuilder = new AdRequest.Builder();
        AdRequest adRequest = adBuilder.build();
        intAd = new InterstitialAd(this);
        intAd.setAdUnitId("ca-app-pub-8439082072622445/5253231538");
        intAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });
        intAd.loadAd(adRequest);
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, fragment, "")
                .commit();
    }

    private void setFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_container, fragment, tag)
                .commit();
    }

    private void initInstance() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void onClick(View view) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (intAd.isLoaded()) {
                intAd.show();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openSettingDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettingDialog() {
        SettingDialog dialog = new SettingDialog(MainActivity.this);
        dialog.setOnSettingUpdateListener(new SettingDialog.OnSettingUpdateListener() {
            @Override
            public void onLanguageChanged() {
                recreate();
            }
        });
        dialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_standard_calculator) {
            setFragment(StandardCalculatorFragment.newInstance(), TAG_FRAGMENT_STANDARD_CALCULATOR);
        } else if (id == R.id.nav_scientific_calculator) {
            setFragment(ScientificCalculatorFragment.newInstance());
        } else if (id == R.id.nav_date_calculation) {
            setFragment(DateBetweenCalculationFragment.newInstance());
        } else if (id == R.id.nav_equation) {
            setFragment(EquationFragment.newInstance());
        } else if (id == R.id.nav_constant) {
            setFragment(ConstantFragment.newInstance());
        } else if (id == R.id.nav_length_converter) {
            setFragment(LengthConverterFragment.newInstance(), TAG_FRAGMENT_CONVERTER_LENGTH);
        } else if (id == R.id.nav_temperature_converter) {
            setFragment(TemperatureConverterFragment.newInstance());
        } else if (id == R.id.nav_area) {
            setFragment(AreaConverterFragment.newInstance());
        } else if (id == R.id.nav_speed) {
            setFragment(SpeedConverterFragment.newInstance());
        } else if (id == R.id.nav_data) {
            setFragment(DataConverterFragment.newInstance());
        } else if (id == R.id.nav_weight) {
            setFragment(WeightConverterFragment.newInstance());
        } else if (id == R.id.nav_volume) {
            setFragment(VolumeConverterFragment.newInstance());
        } else if (id == R.id.nav_time) {
            setFragment(TimeConverterFragment.newInstance());
        } else if (id == R.id.nav_power) {
            setFragment(PowerConverterFragment.newInstance());
        } else if (id == R.id.nav_pressure) {
            setFragment(PressureConverterFragment.newInstance());
        } else if (id == R.id.nav_energy) {
            setFragment(EnergyConverterFragment.newInstance());
        }

        else if (id == R.id.nav_about) {
            openAboutDialog();
        } else if (id == R.id.nav_feedback) {
            MyUtil.openPlayStore(MainActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openAboutDialog() {
        AboutDialog dialog = new AboutDialog(MainActivity.this);
        dialog.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
