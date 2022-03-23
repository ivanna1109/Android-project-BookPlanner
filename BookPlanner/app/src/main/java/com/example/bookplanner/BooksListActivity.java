package com.example.bookplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bookplanner.aboutapp.AboutAppActivity;
import com.example.bookplanner.aboutapp.AboutAppListFragment;
import com.example.bookplanner.classes.MenuClass;
import com.example.bookplanner.database.Book;
import com.example.bookplanner.database.UserRepository;
import com.example.bookplanner.fragments.ReadingBookFragment;
import com.example.bookplanner.retrofit.GoTQuote;
import com.example.bookplanner.retrofit.RestApiInterface;
import com.example.bookplanner.services.MusicService;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksListActivity extends AppCompatActivity {

    private LinearLayout activityLayout;
    private UserRepository userRep;
    private MenuClass meni;
    private List<Book> books;

    //za shake
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private double accelCurrValue;
    private double accelPrevValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.menulogo));

        activityLayout = findViewById(R.id.bookListLayout);
        userRep = new UserRepository(getApplicationContext());
        meni = new MenuClass(getApplicationContext());

        //senzor za muziku
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accelCurrValue = 0.0;
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                accelPrevValue = accelCurrValue;
                accelCurrValue = Math.sqrt((x*x + y*y + z*z));
                double changeInValues = Math.abs(accelCurrValue - accelPrevValue);
                if (changeInValues > 11){
                    if (isMyServiceRunning(MusicService.class)){
                        stopService(new Intent(getApplicationContext(), MusicService.class));
                    }
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        books = userRep.getBooks();

        replaceFragment(new ReadingBookFragment(), "ReadingBooksFragment");
    }

    //za senzore
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void retrofitQuotes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApiInterface apiInterface = retrofit.create(RestApiInterface.class);
        Call<GoTQuote> currentQuote =  apiInterface.getGoTQuote();

        currentQuote.enqueue(new Callback<GoTQuote>() {
            @Override
            public void onResponse(Call<GoTQuote> call, Response<GoTQuote> response) {
                final GoTQuote returnedQuote = response.body();
                showSnackBar(returnedQuote);
                //Toast.makeText(getApplicationContext(), "GoT Quote\n"+returnedQuote.getCharacter()+": \n"+returnedQuote.getQuote(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GoTQuote> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure quote", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSnackBar(GoTQuote returnedQuote) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Snackbar gotBar = Snackbar.make(activityLayout, returnedQuote.getCharacter()+": "+returnedQuote.getQuote(), Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar snack1 = Snackbar.make(activityLayout,"Get another quote in menu!", Snackbar.LENGTH_SHORT);
                        View viewS = snack1.getView();
                        viewS.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_blue));
                        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snack1.getView();
                        snackbarLayout.setMinimumWidth(dm.widthPixels);
                        snackbarLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        snack1.show();
                    }
                });
        View view = gotBar.getView();
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue));
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) gotBar.getView();
        snackbarLayout.setMinimumHeight(150);
        snackbarLayout.setMinimumWidth(dm.widthPixels);
        gotBar.show();
    }

    private void replaceFragment(Fragment currFragment, String tag) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, currFragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        if (userRep.getLoggedUser().gotFan == 0){
            MenuItem quote = menu.findItem(R.id.quote);
            MenuItem music = menu.findItem(R.id.music);
            quote.setVisible(false);
            music.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.showMap:
                Intent map = new Intent(this, MapActivity.class);
                startActivity(map);
                finish();
                return true;
            case R.id.music:
                if (!isMyServiceRunning(MusicService.class)) {
                    Log.e("Book List activity", "Ne svira muzika");
                    startService(new Intent(this, MusicService.class));
                }else {
                    Log.e("Book List activity", "Svira muzika, zaustavlja je klik");
                    stopService(new Intent(this, MusicService.class));
                }
                return true;
            case R.id.quote:
                retrofitQuotes();
                return true;
            case R.id.about_app:
                Intent about_app = new Intent(getApplication(), AboutAppActivity.class);
                startActivity(about_app);
                return true;
            case R.id.logOut:
                if (meni.logOut()){
                    Intent login = new Intent(getApplication(), LoginActivity.class);
                    startActivity(login);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}