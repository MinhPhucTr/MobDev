package vn.uit.project.MapComponent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import vn.uit.project.R;

public class CustomInfo extends InfoWindow {
    /**
     * layout that must contain these ids: bubble_title,bubble_description,
     *                    bubble_subdescription, bubble_image
     * mapView
     */
    MapView mapView;
    public CustomInfo(MapView mapView) {
        super(R.layout.info_marker, mapView);
        this.mapView = mapView;
    }

    @Override
    public void setRelatedObject(Object relatedObject) {
        super.setRelatedObject(relatedObject);
    }

    @Override
    public void onOpen(Object item) {
//        super.onOpen(item);

        Marker marker = (Marker) item;

        TextView tvTitle = (TextView) mView.findViewById(R.id.bubble_title);
        TextView tvDescription = (TextView) mView.findViewById(R.id.bubble_description);
        TextView tvSubDescription = (TextView) mView.findViewById(R.id.bubble_subdescription);

        tvTitle.setText(marker.getTitle());
        tvDescription.setText(marker.getSnippet());
        tvSubDescription.setText(marker.getSubDescription());
    }

    @Override
    public void onClose() {

    }


}
