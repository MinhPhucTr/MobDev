package vn.uit.project.FragmentComponent;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.uit.project.APIComponent.ApiClient;
import vn.uit.project.APIComponent.ApiInterface;
import vn.uit.project.AssetComponent.Asset;
import vn.uit.project.Database.WeatherDatabase;
import vn.uit.project.R;
import vn.uit.project.Spinner.SpinnerItem;
import vn.uit.project.Spinner.SpinnerItemAdapter;
import vn.uit.project.WeatherByDate.AirByDate;
import vn.uit.project.WeatherByDate.DataByDate;
import vn.uit.project.WeatherByDate.HumByDate;

public class HomeFragment extends Fragment {
    TextView texTotalLocation1, texTotalLocation3, texTotalLocation2, texNumberic, texAverageValue1, texLastUpdated1,  texTemperature;
    ApiInterface apiInterface;
    Button butAddDevice;
    LineChart chartTemp;
    Spinner spinner;
    int choiceSpinner = 0;
    Gson gson;
    List<Asset> listAsset = new ArrayList<>();
    List<String> listAssetName = new ArrayList<>();
    List<DataByDate> listTemp = new ArrayList<>();
    List<DataByDate> listHumi = new ArrayList<>();
    List<DataByDate> listAir = new ArrayList<>();
    List<DataByDate> listTempValue = new ArrayList<>();
    List<DataByDate> listHumValue = new ArrayList<>();
    List<DataByDate> listAirValue = new ArrayList<>();
    List<SpinnerItem> listSpinnerItem = new ArrayList<>();
    SpinnerItemAdapter adapter;
    Type typeTempByDate = new TypeToken<ArrayList<DataByDate>>() {
    }.getType();
    WeatherDatabase db;
    Thread updateThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        Bundle mBundle = this.getArguments();
        listAsset = (List<Asset>) mBundle.getSerializable("LISTASSET");
        initial(view);
        clickButtonAddDevice();
        clickSpinner();
        return view;
    }

    private void initial(View view) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        texTotalLocation1 = view.findViewById(R.id.texTotalLocation1);
        texTotalLocation3 = view.findViewById(R.id.texTotalLocation3);
        texTotalLocation2 = view.findViewById(R.id.texTotalLocation2);
        texAverageValue1 = view.findViewById(R.id.texAverageValue);
        texTemperature = view.findViewById(R.id.texTemperature);
        texNumberic = view.findViewById(R.id.texNumberic);
        texLastUpdated1 = view.findViewById(R.id.texLastUpdated);
        butAddDevice = view.findViewById(R.id.butAddDevice);
        chartTemp = view.findViewById(R.id.chart1);
        spinner = view.findViewById(R.id.spinner);
        spinner.setEnabled(false);
        for(SpinnerItem spinnerItem : SpinnerItem.values())
            listSpinnerItem.add(spinnerItem);
        adapter = new SpinnerItemAdapter(getContext(), R.layout.item_selected, listSpinnerItem);
        spinner.setAdapter(adapter);

        gson = new Gson();

        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                db = new WeatherDatabase(getContext());
                for (Asset mAsset : listAsset) {
                    listAssetName.add(mAsset.getName());
                    if (!db.isAssetExisted(mAsset.getName())) {
                        addNewAssets(mAsset);
                    }
                    updateData(mAsset);
                }
            }
        });
        updateThread.start();
    }

    private void clickButtonAddDevice() {
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
                        showWeatherDetails(listAssetName.get(i));
                         listTempValue = getDataDetail(listAssetName.get(i), "temp");
                         listHumValue = getDataDetail(listAssetName.get(i), "humidity");
                         listAirValue = getDataDetail(listAssetName.get(i), "air");
                        spinner.setEnabled(true);
                        spinner.setSelection(0);
                        setUpChart(chartTemp, listTempValue, "°C");
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
    }

    private void showWeatherDetails(String name) {
        for (Asset mAsset : listAsset) {
            if (mAsset.getName().equals(name)) {
                texNumberic.setText(mAsset.getName());
                texTotalLocation1.setText(mAsset.getAttributes().getWeatherData().getValue().getMain().getTemp() + "");
                texTotalLocation2.setText(mAsset.getAttributes().getWeatherData().getValue().getMain().getHumidity() + "");
                texTotalLocation3.setText(mAsset.getAttributes().getWeatherData().getValue().getWind().getSpeed() + "");
                break;
            }
        }
    }

    public String parseUpdateTime(Date mDate) {
        String result = "";
        try {
            SimpleDateFormat sim = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);
            result = sim.format(mDate);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
        return result;
    }

    private void setUpChart(LineChart chart, List<DataByDate> listDataByDate, String label) {
        int i = -6;
        LineDataSet lineDataSet;
        List<Double> listValue = new ArrayList<>();
        List<Entry> dataVals = new ArrayList<Entry>();
        String startDate = parseUpdateTime(listDataByDate.get(0).getUpdateTime());
        String lastDate = parseUpdateTime(listDataByDate.get(listDataByDate.size() - 1).getUpdateTime());
        for (DataByDate mDataByDate : listDataByDate) {
            dataVals.add(new Entry(i, (float) mDataByDate.getValue()));
            listValue.add(mDataByDate.getValue());
            i++;
        }
        String averageValue = String.format("%.2f", getAverage(listValue));
        if(label.equals("°C")) {
            lineDataSet = new LineDataSet(dataVals, "Temperature");
            texTemperature.setText(getString(R.string.temperature));
            texTemperature.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.device_thermostat_outlined, null), null, null, null);
        }
        else if(label.equals("g.m³")) {
            lineDataSet = new LineDataSet(dataVals, "Humidity");
            texTemperature.setText(getString(R.string.humidity));
            texTemperature.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.water_drop_outlined, null), null, null, null);
        }
        else {
            lineDataSet = new LineDataSet(dataVals, "Air");
            texTemperature.setText(getString(R.string.air));
            texTemperature.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.air_outlined, null), null, null, null);
        }
        texLastUpdated1.setText("From: " + startDate + "\nTo:      " + lastDate);
        texAverageValue1.setText(" " + averageValue + " " + label);
        lineDataSet.setValueTextSize(11);
        lineDataSet.setLineWidth(4);
        XAxis xAsis = chart.getXAxis();
        xAsis.setTextSize((float)10);
        xAsis.setLabelCount(7, true);
        YAxis yAxisLeft = chart.getAxisLeft();
        YAxis yAxisRight = chart.getAxisRight();
        yAxisLeft.setLabelCount(5, true);
        yAxisRight.setLabelCount(5, true);
        yAxisLeft.setDrawLabels(false);
        yAxisRight.setDrawLabels(false);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        LineData lineData = new LineData(lineDataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

    private void addNewAssets(Asset mAsset) {
        String name = mAsset.getName();
        DataByDate mTempByDate = new DataByDate(mAsset.getAttributes().getWeatherData().getValue().getMain().getTemp(), new Date(mAsset.getAttributes().getWeatherData().getTimestamp()));
        DataByDate mHumiByDate = new HumByDate(mAsset.getAttributes().getWeatherData().getValue().getMain().getHumidity(), new Date(mAsset.getAttributes().getWeatherData().getTimestamp()));
        DataByDate mAirByDate = new AirByDate(mAsset.getAttributes().getWeatherData().getValue().getWind().getSpeed(), new Date(mAsset.getAttributes().getWeatherData().getTimestamp()));
        listTemp.clear();
        listHumi.clear();
        listAir.clear();
        listTemp.add(mTempByDate);
        listHumi.add(mHumiByDate);
        listAir.add(mAirByDate);
        String listTempText = gson.toJson(listTemp);
        String listHumiText = gson.toJson(listHumi);
        String listAirText = gson.toJson(listAir);
        db.addAsset(name, listTempText, listHumiText, listAirText);
    }

    private void updateData(Asset mAsset) {
        String listTempText = db.getTempDetails(mAsset.getName());
        String listHumiText = db.getHumiDetails(mAsset.getName());
        String listAirText = db.getAirDetails(mAsset.getName());
        listTemp = gson.fromJson(listTempText, typeTempByDate);
        DataByDate latestValue = listTemp.get(listTemp.size() - 1);
        if (!isSameDate(latestValue.getUpdateTime(), mAsset)) {
            listHumi = gson.fromJson(listHumiText, typeTempByDate);
            listAir = gson.fromJson(listAirText, typeTempByDate);
            DataByDate mDataByDate = new DataByDate(mAsset.getAttributes().getWeatherData().getValue().getMain().getTemp(), new Date(mAsset.getAttributes().getWeatherData().getTimestamp()));
            DataByDate mHumiByDate = new HumByDate(mAsset.getAttributes().getWeatherData().getValue().getMain().getHumidity(), new Date(mAsset.getAttributes().getWeatherData().getTimestamp()));
            DataByDate mAirByDate = new AirByDate(mAsset.getAttributes().getWeatherData().getValue().getWind().getSpeed(), new Date(mAsset.getAttributes().getWeatherData().getTimestamp()));
            listTemp.add(mDataByDate);
            listHumi.add(mHumiByDate);
            listAir.add(mAirByDate);
            listTempText = gson.toJson(listTemp);
            listHumiText = gson.toJson(listHumi);
            listAirText = gson.toJson(listAir);
            db.updateData(mAsset.getName(), listTempText, listHumiText, listAirText);
        }
    }

    private boolean isSameDate(Date mDate, Asset mAsset) {
        long timestamp = mAsset.getAttributes().getWeatherData().getTimestamp();
        Date current = new Date(timestamp);
        SimpleDateFormat sim = new SimpleDateFormat("MM:dd:yyyy", Locale.ENGLISH);
        String str1 = sim.format(mDate);
        String str2 = sim.format(current);
        if (str1.equals(str2))
            return true;
        return false;
    }

    private List<DataByDate> getDataDetail(String assetName, String attributeName) {
        List<DataByDate> listDataValue = new ArrayList<>();
        String listDataText;
        if(attributeName.equals("temp"))
            listDataText = db.getTempDetails(assetName);
        else if(attributeName.equals("humidity"))
            listDataText = db.getHumiDetails(assetName);
        else
            listDataText = db.getAirDetails(assetName);
        listTemp = gson.fromJson(listDataText, typeTempByDate);
        if (listTemp.size() > 7) {
            for (int i = listTemp.size() - 7; i < listTemp.size(); i++) {
                listDataValue.add(listTemp.get(i));
            }
        } else {
            for (DataByDate mDataByDate : listTemp) {
                listDataValue.add(mDataByDate);
            }
        }
        return listDataValue;
    }

    private double getAverage(List<Double> listValue) {
        double sum = 0;
        for (double d : listValue)
            sum += d;
        sum /= listValue.size();
        return sum;
    }

    private void clickSpinner()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(listTempValue.size() > 0) {
                    if (i == 0) {
                        setUpChart(chartTemp, listTempValue, "°C");
                    } else if (i == 1)
                        setUpChart(chartTemp, listHumValue, "g.m³");
                    else
                        setUpChart(chartTemp, listAirValue, "m/s");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
