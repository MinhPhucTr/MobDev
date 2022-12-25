package vn.uit.project.FragmentComponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.uit.project.APIComponent.ApiClient;
import vn.uit.project.APIComponent.ApiInterface;
import vn.uit.project.R;

public class DeviceFragment extends Fragment {
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
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

}
