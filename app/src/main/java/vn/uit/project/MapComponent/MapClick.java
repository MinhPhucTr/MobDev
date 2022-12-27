package vn.uit.project.MapComponent;

import android.app.Activity;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class MapClick implements MapEventsReceiver {
    MapView mapView;

    public MapClick(MapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
//        InfoWindow.closeAllInfoWindowsOn(mapView);
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        //InfoWindow.closeAllInfoWindowsOn(mapView);
        return true;
    }
}
