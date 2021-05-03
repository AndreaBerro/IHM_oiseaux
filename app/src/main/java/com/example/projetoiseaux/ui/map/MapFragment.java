package com.example.projetoiseaux.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.UploadBird;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.projetoiseaux.ui.map.IGPSActivity.REQUEST_PERMISSION_GPS;

public class MapFragment extends Fragment {
    private IGPSActivity igpsActivity;
    private MapView map;
    private GPSUtils gps;
    private ArrayList<UploadBird> listNearBird = new ArrayList<UploadBird>() {{add(new UploadBird("salut",new GeoPoint(43.615554,7.071800),new Date(), 1001010));}};


    @Override
    /**
     * Call the method that creating callback after being attached to parent activity
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Create callback to parent activity
        try {
            igpsActivity = (IGPSActivity) getActivity();
            igpsActivity.updateGps(this);
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement IGPSActivity");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);

        //todo : check the permission of GPS / the Etat of GPS
        // A class for a GPS Listener
        Log.d("gps", "in MapFragment --> getActivity" + getActivity() +"\n Context--->" + getContext());

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_GPS);
        } else if (this.gps == null){
            this.gps = new GPSUtils(getContext(), getActivity(), this);
        }

        // this.gps = new GPS(root, getContext(), getActivity()); // not work if

        Configuration.getInstance().load(root.getContext(),
                PreferenceManager.getDefaultSharedPreferences( root.getContext() ));

        map = root.findViewById( R.id.map );
        map.setTileSource( TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls( false );
        GeoPoint startPoint = new GeoPoint(43.615544,7.071800);
        IMapController mapController = map.getController();
        mapController.setZoom( 14.0 );

        if(gps != null) {
            mapController.setCenter(gps.getCurrentPosition()); // set center to currentLocation
        } else {
            mapController.setCenter(startPoint);
        }

        ArrayList<OverlayItem> items = new ArrayList<>();
        items.addAll(getBirdOverlayItem());
        OverlayItem home = new OverlayItem("Polytech","QG",new GeoPoint(43.615544,7.071800));
        Drawable m = home.getMarker(0);
        items.add(home);

        ItemizedOverlayWithFocus<OverlayItem> mOververlay = new ItemizedOverlayWithFocus<OverlayItem>(root.getContext(), items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });
        mOververlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOververlay);

        return root;
    }

    private List<OverlayItem> getBirdOverlayItem() {
        ArrayList<OverlayItem> result = new ArrayList<>();
        for (UploadBird uploadBird : listNearBird) {
            OverlayItem temp = new OverlayItem(uploadBird.getName(),uploadBird.getDate().toString(),uploadBird.getGeoPoint());
            Drawable zorua = temp.getMarker(0);
            result.add(temp);
        }
        return result;
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    public void refocus(){
        if(gps == null){
            gps = new GPSUtils(getContext(), getActivity(), this);
        }
        map.getController().setCenter(gps.getCurrentPosition());
    }


}