package vn.uit.project.FragmentComponent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
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
    TextView texTotalTemp, texTotalAir, texTotalHumidity;
    ApiInterface apiInterface;
    Database mDatabase;
    String username;
    LineChart chart;
    TextView texAverageHome;
    ArrayList<TempByDate> finalOutputString = new ArrayList<>();
    Gson gson = new Gson();
    boolean ready = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        mDatabase = new Database(getContext());
        Bundle mBundle = this.getArguments();
        username = mBundle.getString("USERNAME");
        texTotalTemp = view.findViewById(R.id.texTotalTemp);
        texTotalAir = view.findViewById(R.id.texTotalAir);
        texTotalHumidity = view.findViewById(R.id.texTotalHumidity);
        chart = view.findViewById(R.id.chart);
        texAverageHome = view.findViewById(R.id.texAverageHome);
        getAssetWeatherData();
        return view;
    }

    private void getAssetWeatherData()
    {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Asset> callAPIAsset = apiInterface.getAsset("6H4PeKLRMea1L0WsRXXWp9");
        callAPIAsset.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset mAsset = response.body();
                Attributes mAttributes = mAsset.getAttributes();
                WeatherData mWeatherData = mAttributes.getWeatherData();
                Value mValue = mWeatherData.getValue();
                Main mMain = mValue.getMain();
                Wind mWind = mValue.getWind();
                texTotalTemp.setText(mMain.getTemp() + "");
                texTotalHumidity.setText(mMain.getHumidity() + "");
                texTotalAir.setText(mWind.getSpeed() + "");
                LocalDate mLocalDate = getLatestDate();
                if(isSameDate(mLocalDate) == false)
                {
                    TempByDate mTempByDate = new TempByDate(mMain.getTemp());
                    finalOutputString.add(mTempByDate);
                    updateTempToDB(finalOutputString, username);
                }
                LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "TEMPERATURE IN WEEK");
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(lineDataSet1);
                LineData data = new LineData(dataSets);
                chart.setData(data);
                chart.invalidate();
                double averageTemp = calculateAverageTemp();
                texAverageHome.append(" " + String.format("%.3f", averageTemp));
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("ERROR", t.toString());
            }
        });
    }

    private boolean isSameDate(LocalDate mLocalDate)
    {
        LocalDate now = LocalDate.now();
        if((mLocalDate.getDayOfMonth() == now.getDayOfMonth()) && (mLocalDate.getMonthValue() == now.getMonthValue()) && (mLocalDate.getYear() == now.getYear()))
            return true;
        return false;
    }

    public void updateTempToDB(ArrayList<TempByDate> finalOutputString, String username)
    {
        String tempByDateStr = gson.toJson(finalOutputString);
        mDatabase.updateTempByDate(username, tempByDateStr);
    }

    private LocalDate getLatestDate()
    {
        String tempByDateRoot = mDatabase.getTempByDate(username);
        Type type = new TypeToken<ArrayList<TempByDate>>() {}.getType();
        finalOutputString = gson.fromJson(tempByDateRoot, type);
        TempByDate tempByDateObj = finalOutputString.get(finalOutputString.size() - 1);
        LocalDate mLocalDate = LocalDate.parse(tempByDateObj.getLocalDate());
        ready = true;
        return mLocalDate;
    }

    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        int i = 0;
        ArrayList<TempByDate> tempList = finalOutputString;
        if(tempList.size() > 7)
        {
            tempList.clear();
            for(int index = tempList.size() -7; index <= tempList.size() - 1; index ++)
            {
                tempList.add(finalOutputString.get(index));
            }
        }
        for(TempByDate temp : tempList)
        {
            dataVals.add(new Entry(i, (float) temp.getTemp()));
            i++;
        }
        return dataVals;
    }

    private double calculateAverageTemp()
    {
        double average = 0;
        int count = 0;
            ArrayList<TempByDate> tempList = finalOutputString;
            if (tempList.size() > 7) {
                tempList.clear();
                for (int index = tempList.size() - 7; index <= tempList.size() - 1; index++) {
                    tempList.add(finalOutputString.get(index));
                }
            }
            for (TempByDate temp : tempList) {
                average += temp.getTemp();
                count++;
            }
            average /= count;
        return average;
    }
}
