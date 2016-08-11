package es.craftsmanship.toledo.katangapp.maps;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Manuel de la Peña
 */
public class GoogleMapsCameraHelper {

    public static CameraUpdate getCameraUpdate(LatLng position, int zoom) {
        CameraPosition cameraPosition = CameraPosition.builder()
            .target(position)
            .zoom(zoom)
            .build();

        return CameraUpdateFactory.newCameraPosition(cameraPosition);
    }

}