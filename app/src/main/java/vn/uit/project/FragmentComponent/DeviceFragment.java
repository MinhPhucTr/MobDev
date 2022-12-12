package vn.uit.project.FragmentComponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import vn.uit.project.R;

public class DeviceFragment extends Fragment {
    TextView texTotalTemp, texTotalHumidity, texTotalAir;
    ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_devices, container, false);
        initial(view);
        return view;
    }

    private void initial(View view)
    {
        texTotalTemp = view.findViewById(R.id.texTotalTemp);
        texTotalHumidity = view.findViewById(R.id.texTotalHumidity);
        texTotalAir = view.findViewById(R.id.texTotalAir);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

}
