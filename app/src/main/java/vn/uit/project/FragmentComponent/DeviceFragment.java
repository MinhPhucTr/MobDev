package vn.uit.project.FragmentComponent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.uit.project.APIComponent.ApiClient;
import vn.uit.project.APIComponent.ApiInterface;
import vn.uit.project.AssetComponent.Asset;
import vn.uit.project.DeviceInfo;
import vn.uit.project.ListView.AssetItem;
import vn.uit.project.ListView.AssetListAdapter;
import vn.uit.project.R;

public class DeviceFragment extends Fragment {
    ApiInterface apiInterface;
    TextView tvTotalDevice;
    List<Asset> listAsset = new ArrayList<>();
    ArrayList<AssetItem> assetItems;
    ListView lvAssetList;
    AssetListAdapter assetListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_devices, container, false);
        initial(view);

        Bundle mBundle = this.getArguments();
        listAsset = (List<Asset>) mBundle.getSerializable("LISTASSET");

        SetUp();
        return view;
    }

    private void SetUp(){
        tvTotalDevice.setText(String.format("%s", listAsset.size()));
        assetItems = new ArrayList<>();
        // Them du lieu vao ArrayList<AssetItem>
        for(Asset asset : listAsset){
            AssetItem assetItem = new AssetItem(
                    asset.getName(),
                    String.format("%s", new DecimalFormat("##.####")
                            .format(asset.getAttributes().getLocation()
                                    .getValue().getCoordinates()[1])),
                    String.format("%s", new DecimalFormat("##.####")
                            .format(asset.getAttributes().getLocation()
                                    .getValue().getCoordinates()[0]))
            );
            assetItems.add(assetItem);
        }
        assetListAdapter = new AssetListAdapter(assetItems);
        lvAssetList.setAdapter(assetListAdapter);

        lvAssetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent assetDetail = new Intent(getContext(), DeviceInfo.class);
                assetDetail.putExtra("Asset name", listAsset.get(i).getName());
                String sLocation = String.format("[%s, %s]",
                        new DecimalFormat("##.####")
                                .format(listAsset.get(i).getAttributes().getLocation()
                                        .getValue().getCoordinates()[1]),
                        new DecimalFormat("##.####")
                                .format(listAsset.get(i).getAttributes().getLocation()
                                        .getValue().getCoordinates()[0]));
                assetDetail.putExtra("Location", sLocation);
                assetDetail.putExtra("Temperature", listAsset.get(i).getAttributes().getWeatherData().getValue()
                        .getMain().getTemp() + " °C");
                assetDetail.putExtra("Humidity", listAsset.get(i).getAttributes().getWeatherData().getValue()
                        .getMain().getHumidity() + " g.m³");
                assetDetail.putExtra("Air", listAsset.get(i).getAttributes().getWeatherData().getValue()
                        .getWind().getSpeed() + " m/s");
                assetDetail.putExtra("Area name", listAsset.get(i).getAttributes().getWeatherData().getValue().getName());

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                assetDetail.putExtra("Last Update", sdf.format(listAsset.get(i).getAttributes()
                        .getWeatherData().getTimestamp()) + "");

                startActivity(assetDetail);
            }
        });
    }

    private void initial(View view)
    {
        tvTotalDevice = view.findViewById(R.id.texNumberic);
        lvAssetList = view.findViewById(R.id.lvAssetList);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

}
