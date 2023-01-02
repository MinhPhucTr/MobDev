package vn.uit.project.MapComponent;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import vn.uit.project.R;

public class MapClick implements MapEventsReceiver {
    MapView mapView;
    TextView texTemp, texAir, texHumidity;
    Button butViewMore;

    public MapClick(MapView mapView, TextView texTemp, TextView texAir,
                    TextView texHumidity, Button butViewMore) {
        this.mapView = mapView;
        this.texTemp = texTemp;
        this.texAir = texAir;
        this.texHumidity = texHumidity;
        this.butViewMore = butViewMore;
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
//        InfoWindow.closeAllInfoWindowsOn(mapView);
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        InfoWindow.closeAllInfoWindowsOn(mapView);
        texTemp.setText(R.string.temp);
        texHumidity.setText(R.string.humidity);
        texAir.setText(R.string.air);
        butViewMore.setVisibility(View.INVISIBLE);
        //InfoWindow.closeAllInfoWindowsOn(mapView);
        return true;
    }
}
