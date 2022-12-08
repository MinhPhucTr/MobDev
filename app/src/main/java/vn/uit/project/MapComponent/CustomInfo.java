package vn.uit.project.MapComponent;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

public class CustomInfo extends MarkerInfoWindow {
    /**
     * @param layoutResId layout that must contain these ids: bubble_title,bubble_description,
     *                    bubble_subdescription, bubble_image
     * @param mapView
     */
    public CustomInfo(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    @Override
    public void onOpen(Object item) {
        super.onOpen(item);
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}
