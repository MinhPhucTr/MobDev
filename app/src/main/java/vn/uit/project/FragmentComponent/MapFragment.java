package vn.uit.project.FragmentComponent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.uit.project.APIComponent.ApiClient;
import vn.uit.project.APIComponent.ApiInterface;
import vn.uit.project.AssetComponent.Asset;
import vn.uit.project.DeviceInfoActivity;
import vn.uit.project.MapComponent.CustomInfo;
import vn.uit.project.MapComponent.Map;
import vn.uit.project.R;

public class MapFragment extends Fragment {
    MapView mapView;
    ApiInterface apiInterface;
    MapController mapController;
    List<Asset> listAsset = new ArrayList<>();
    Thread mThread;
    TextView texTemp, texAir, texHumidity;
    Button butViewMore;
    Context ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ctx = getContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        View view = inflater.inflate(R.layout.activity_map, container, false);
        Bundle mBundle = this.getArguments();
        listAsset = (List<Asset>) mBundle.getSerializable("LISTASSET");
        initial(view);
        setUpMap();
        return view;
    }

    private void initial(View view)
    {
        butViewMore = view.findViewById(R.id.butViewMore);
        texTemp = view.findViewById(R.id.texTemp);
        texAir = view.findViewById(R.id.texAir);
        texHumidity = view.findViewById(R.id.texHumidity);
        mapView = view.findViewById(R.id.mapMap);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mapController = (MapController) mapView.getController();
    }

    private void setUpMap() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mapView.setBuiltInZoomControls(true);
                mapView.setMultiTouchControls(true);
                Call<Map> callAPIMap = apiInterface.getMap();
                callAPIMap.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        Map mMap = response.body();
                        double[] mCenter = mMap.getOptions().getMyDefault().getCenter();
                        double[] mBounds = mMap.getOptions().getMyDefault().getBounds();
                        int zoom = mMap.getOptions().getMyDefault().getZoom();
                        int minZoom = mMap.getOptions().getMyDefault().getMinZoom();
                        int maxZoom = mMap.getOptions().getMyDefault().getMaxZoom();
                        mapController.setZoom((double)zoom);
                        mapController.setCenter(new GeoPoint(mCenter[1], mCenter[0]));
                        mapView.zoomToBoundingBox(new BoundingBox(mBounds[3], mBounds[2], mBounds[1], mBounds[0]), true);
                        mapView.setMaxZoomLevel((double) maxZoom);
                        mapView.setMinZoomLevel((double) minZoom);
                        mapView.setScrollableAreaLimitDouble(new BoundingBox(mBounds[3], mBounds[2], mBounds[1], mBounds[0]));
                        for(Asset mAsset : listAsset)
                        {
                            addMarker(mAsset);
                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        Log.d("ERROR", t.toString());
                    }
                });
            }
        });
        mThread.start();
    }

    private void setUpMarkerDetail(Marker mMarker, Asset asset)
    {
        mMarker.setTitle(asset.getName());
        mMarker.setSubDescription(asset.getAttributes().getWeatherData().getValue().getName());
        mMarker.setSnippet(String.format("[%s, %s]",
                new DecimalFormat("##.##")
                        .format(asset.getAttributes().getLocation()
                                .getValue().getCoordinates()[1]),
                new DecimalFormat("##.##")
                        .format(asset.getAttributes().getLocation()
                                .getValue().getCoordinates()[0])));
        mMarker.setInfoWindow(new CustomInfo(mapView));
        mMarker.setInfoWindowAnchor(Marker.ANCHOR_CENTER, -0.1f);

        // region Marker Click
        mMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                marker.showInfoWindow();
                texTemp.setText(String.format("%s °C" ,asset.getAttributes().getWeatherData()
                        .getValue().getMain().getTemp()));
                texAir.setText(String.format("%s m/s", asset.getAttributes().getWeatherData()
                        .getValue().getWind().getSpeed()));
                texHumidity.setText(String.format("%s g.m³", asset.getAttributes().getWeatherData()
                        .getValue().getMain().getHumidity()));

                if(marker.isInfoWindowOpen()){
                    butViewMore.setVisibility(View.VISIBLE);
                    ViewMoreButtonClick();
                }
                else{
                    butViewMore.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });
        // endregion

    }

    private void addMarker(Asset mAsset)
    {
        if(isAssetLocationNull(mAsset) == false)
        {
        double[] coordinates = mAsset.getAttributes().getLocation().getValue().getCoordinates();
        Marker mMarker = new Marker(mapView);
        mMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mMarker.setIcon(getResources().getDrawable(R.drawable.marker, null));
        mMarker.setPosition(new GeoPoint(coordinates[1], coordinates[0]));
        setUpMarkerDetail(mMarker, mAsset);
        mapView.getOverlays().add(mMarker);
        }
    }

    private boolean isAssetLocationNull(Asset mAsset)
    {
        if(mAsset.getAttributes().getLocation().getValue() != null)
            return false;
        return true;
    }


    private void ViewMoreButtonClick(){
        butViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent assetDetail = new Intent(getContext(), DeviceInfoActivity.class);
                startActivity(assetDetail);
                Toast.makeText(ctx, "View more", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
