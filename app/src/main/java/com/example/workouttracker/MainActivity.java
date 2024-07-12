package com.example.workouttracker;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Fragment workoutsFragment = new WorkoutsFragment();
        Fragment statisticsFragment = new StatisticsFragment();
        Fragment profileFragment = new ProfileFragment();
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_workouts) {
                    selectedFragment = workoutsFragment;
                } else if (itemId == R.id.navigation_statistics) {
                    selectedFragment = statisticsFragment;
                } else if (itemId == R.id.navigation_profile) {
                    selectedFragment = profileFragment;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
                return true;
            }
        });

        // Set the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new WorkoutsFragment()).commit();
        }
    }
}
