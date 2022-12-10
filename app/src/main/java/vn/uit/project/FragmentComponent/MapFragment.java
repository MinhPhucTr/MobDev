package vn.uit.project.FragmentComponent;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.uit.project.APIComponent.ApiClient;
import vn.uit.project.APIComponent.ApiInterface;
import vn.uit.project.AssetComponent.Asset;
import vn.uit.project.AssetComponent.Attributes;
import vn.uit.project.AssetComponent.Coord;
import vn.uit.project.AssetComponent.Value;
import vn.uit.project.AssetComponent.WeatherData;
import vn.uit.project.MainActivity;
import vn.uit.project.MapComponent.CustomInfo;
import vn.uit.project.MapComponent.Default;
import vn.uit.project.MapComponent.Map;
import vn.uit.project.MapComponent.Option;
import vn.uit.project.R;

public class MapFragment extends Fragment {
    MapView mapView;
    TextView texAssetName;
    ImageView imaTemp, imaAir, imaWater;
    ApiInterface apiInterface;
    IMapController iMapController;
    GeoPoint markerPoint;
    Marker mMarker = null;
    double lat = 0, lon = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //AssetId: 6H4PeKLRMea1L0WsRXXWp9
        Context ctx = getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        View view = inflater.inflate(R.layout.activity_map, container, false);
        mapView = view.findViewById(R.id.mapMap);
        imaTemp = view.findViewById(R.id.displayTemp);
        imaAir = view.findViewById(R.id.displayAir);
        imaWater = view.findViewById(R.id.displayWater);
        texAssetName = view.findViewById(R.id.texAssetName);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        getMap();
        getAssetDetails();
        return view;
    }

    private void MarkerClick() {

    }

    private void getMap() {
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        iMapController = mapView.getController();
        Call<Map> callAPIMap = apiInterface.getMap();
        callAPIMap.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                Map mMap = response.body();
                Option mOption = mMap.getOptions();
                Default mDefault = mOption.getMyDefault();
                /*List<Double> centerList = new ArrayList<>();
                double[] centerArray = mDefault.getCenter();
                for(Double d : centerArray)
                    centerList.add(d);
                List<Double> boundList = new ArrayList<>();
                double[] boundArray = mDefault.getBounds();
                for(Double d : boundArray)
                    boundList.add(d);*/
                double zoom = mDefault.getZoom();
                double maxZoom = mDefault.getMaxZoom();
                double minZoom = mDefault.getMinZoom();
                /*GeoPoint startPoint = new GeoPoint(centerList.get(1), centerList.get(0));
                markerPoint = new GeoPoint(boundList.get(3), boundList.get(2));*/
                markerPoint = new GeoPoint(lat, lon);
                iMapController.setZoom(zoom);
                mapView.setMaxZoomLevel(maxZoom);
                mapView.setMinZoomLevel(minZoom);
                mapView.setExpectedCenter(markerPoint);
                mMarker = new Marker(mapView);
                mMarker.setPosition((GeoPoint) markerPoint);
                mMarker.setIcon(getResources().getDrawable(R.drawable.marker, null));
                mMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                mapView.getOverlays().add(mMarker);

                mMarker.setSubDescription("321");
                mMarker.setSnippet("123");
                mMarker.setTitle("Temperature");
                mMarker.setInfoWindow(new CustomInfo(mapView));
                mMarker.setInfoWindowAnchor(Marker.ANCHOR_CENTER, -0.1f);

                // region Marker Click
                mMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        marker.showInfoWindow();
                        String displayImageView = texAssetName.getText().toString();
                        switch (displayImageView) {
                            case "Weather Asset": {
                                imaWater.setVisibility(View.VISIBLE);
                            }
                        }
                        return false;
                    }
                });
                // endregion
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("ERROR", t.toString());
            }
        });
    }


    private void getAssetDetails() {
        Call<Asset> callAPIAsset = apiInterface.getAsset("6H4PeKLRMea1L0WsRXXWp9");
        callAPIAsset.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset mAsset = response.body();
                texAssetName.setText(mAsset.getName().trim());
                Attributes mAttributes = mAsset.getAttributes();
                WeatherData mWeatherData = mAttributes.getWeatherData();
                Value mValue = mWeatherData.getValue();
                Coord mCoord = mValue.getCoord();
                lat = mCoord.getLat();
                lon = mCoord.getLon();
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
    }
}
