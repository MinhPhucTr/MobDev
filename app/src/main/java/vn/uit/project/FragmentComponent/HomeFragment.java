package vn.uit.project.FragmentComponent;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.uit.project.APIComponent.ApiClient;
import vn.uit.project.APIComponent.ApiInterface;
import vn.uit.project.AssetComponent.Asset;
import vn.uit.project.AssetComponent.Attributes;
import vn.uit.project.AssetComponent.Main;
import vn.uit.project.AssetComponent.Value;
import vn.uit.project.AssetComponent.WeatherData;
import vn.uit.project.AssetComponent.Wind;
import vn.uit.project.Database;
import vn.uit.project.R;
import vn.uit.project.TempByDate.TempByDate;

public class HomeFragment extends Fragment {
    TextView texTotalLocation1, texTotalLocation3, texTotalLocation2, texNumberic;
    ApiInterface apiInterface;
    TextView texAverageHome;
    Button butAddDevice;
    List<Asset> listAsset = new ArrayList<>();
    List<String> listAssetName = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        Bundle mBundle = this.getArguments();
        listAsset = (List<Asset>) mBundle.getSerializable("LISTASSET");
        initial(view);
        clickButtonAddDevice();
        return view;
    }

    private void initial(View view)
    {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        texTotalLocation1 = view.findViewById(R.id.texTotalLocation1);
        texTotalLocation3 = view.findViewById(R.id.texTotalLocation3);
        texTotalLocation2 = view.findViewById(R.id.texTotalLocation2);
        texAverageHome = view.findViewById(R.id.texAverageHome);
        texNumberic = view.findViewById(R.id.texNumberic);
        butAddDevice = view.findViewById(R.id.butAddDevice);

        texNumberic.setText(listAsset.size() + "");

        for(Asset mAsset : listAsset)
            listAssetName.add(mAsset.getName());
    }

    private void clickButtonAddDevice()
    {
        butAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog mDialog = new Dialog(getContext());
                mDialog.setContentView(R.layout.list_assets);
                ListView lisAssets = mDialog.findViewById(R.id.lisAssets);
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listAssetName);
                lisAssets.setAdapter(adapter);
                lisAssets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getContext(), listAssetName.get(i), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), getUpdateTime(listAssetName.get(i)), Toast.LENGTH_SHORT).show();
                        showWeatherDetails(listAssetName.get(i));
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
    }

    private void showWeatherDetails(String name)
    {
        for(Asset mAsset : listAsset)
        {
            if(mAsset.getName().equals(name))
            {
                texTotalLocation1.setText(mAsset.getAttributes().getWeatherData().getValue().getMain().getTemp() + "");
                texTotalLocation2.setText(mAsset.getAttributes().getWeatherData().getValue().getMain().getHumidity() + "");
                texTotalLocation3.setText(mAsset.getAttributes().getWeatherData().getValue().getWind().getSpeed() + "");
                break;
            }
        }
    }

    public String parseUpdateTime(long timestamp)
    {
        String result = "";
        try {
            Date updateTime = new Date(timestamp);
            SimpleDateFormat sim = new SimpleDateFormat("'Updated on:'EEE',' MMM dd',' YYYY h:mm a", Locale.ENGLISH);
            result = sim.format(updateTime);
        }
        catch (Exception e)
        {
            Log.d("ERROR", e.getMessage());
        }
        return result;
    }

    private String getUpdateTime(String name)
    {
        String result = "";
        for(Asset mAsset : listAsset)
        {
            if(mAsset.getName().equals(name))
            {
                long timestamp = mAsset.getAttributes().getWeatherData().getTimestamp();
                result = parseUpdateTime(timestamp);
               break;
            }
        }
        return result;
    }
}
