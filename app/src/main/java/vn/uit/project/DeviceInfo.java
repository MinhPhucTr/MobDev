package vn.uit.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeviceInfo extends AppCompatActivity {
    TextView tvLocation, tvTemp, tvAir, tvHumidity, tvAreaName, tvLastUpdate, tvAssetName;
    Button butBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        Init();

        Intent intent = getIntent();
        tvAssetName.setText(intent.getStringExtra("Asset name"));
        tvLocation.setText(intent.getStringExtra("Location"));
        tvTemp.setText(intent.getStringExtra("Temperature"));
        tvAir.setText(intent.getStringExtra("Air"));
        tvHumidity.setText(intent.getStringExtra("Humidity"));
        tvAreaName.setText(intent.getStringExtra("Area name"));
        tvLastUpdate.setText(intent.getStringExtra("Last Update"));

        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceInfo.super.onBackPressed();
            }
        });
    }

    private void Init(){
        tvLocation = (TextView) findViewById(R.id.texPosition);
        tvTemp = (TextView) findViewById(R.id.texTempInfo);
        tvAir = (TextView) findViewById(R.id.texAirInfo);
        tvHumidity = (TextView) findViewById(R.id.texHumidityInfo);
        tvAreaName = (TextView) findViewById(R.id.texArea);
        tvLastUpdate = (TextView) findViewById(R.id.texLastUpdate);
        tvAssetName = (TextView) findViewById(R.id.texAssetName);

        butBack = (Button) findViewById(R.id.butBack);
    }
}