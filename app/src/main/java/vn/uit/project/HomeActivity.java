package vn.uit.project;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.uit.project.APIComponent.ApiClient;
import vn.uit.project.APIComponent.ApiInterface;
import vn.uit.project.AssetComponent.Asset;
import vn.uit.project.AssetComponent.ValueLocation;
import vn.uit.project.FragmentComponent.DeviceFragment;
import vn.uit.project.FragmentComponent.HomeFragment;
import vn.uit.project.FragmentComponent.MapFragment;

public class HomeActivity extends FragmentActivity {
    BottomNavigationView botNav;
    List<Asset> listAsset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        initialBotNav();
        callAPICurrentAsset();
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
        mBundle.putSerializable("LISTASSET", (Serializable) listAsset);
        fragment.setArguments(mBundle);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void callAPICurrentAsset()
    {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Asset>> callAPICurrentAsset = apiInterface.getCurrentAsset();
        callAPICurrentAsset.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
               listAsset = new ArrayList<>();
                for(Asset mAsset : response.body())
                {
                    if(!isAssetLocationNull(mAsset)) {
                        listAsset.add(mAsset);
                    }
                }
                replaceFragment(new HomeFragment());
            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {

            }
        });
    }

    private boolean isAssetLocationNull(Asset asset)
    {
        ValueLocation value = asset.getAttributes().getLocation().getValue();
        if(value != null)
            return false;
        return true;
    }
}