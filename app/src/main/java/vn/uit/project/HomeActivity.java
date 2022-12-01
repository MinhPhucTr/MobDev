package vn.uit.project;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.uit.project.FragmentComponent.DeviceFragment;
import vn.uit.project.FragmentComponent.HomeFragment;
import vn.uit.project.FragmentComponent.MapFragment;

public class HomeActivity extends FragmentActivity {
    BottomNavigationView botNav;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        Toast.makeText(HomeActivity.this, username, Toast.LENGTH_SHORT).show();
        replaceFragment(new HomeFragment());
        initialBotNav();
    }

    private void initialBotNav() {
        botNav = findViewById(R.id.botNav);
        botNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.map:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.devices:
                    replaceFragment(new DeviceFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle mBundle = new Bundle();
        mBundle.putString("USERNAME", username);
        fragment.setArguments(mBundle);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}