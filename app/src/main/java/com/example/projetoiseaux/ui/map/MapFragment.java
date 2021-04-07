package com.example.projetoiseaux.ui.map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.projetoiseaux.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    private MapView map;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        Configuration.getInstance().load(root.getContext(),
                PreferenceManager.getDefaultSharedPreferences( root.getContext() ));

        map = root.findViewById( R.id.map );
        map.setTileSource( TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls( false );
        GeoPoint startPoint = new GeoPoint(43.615544,7.071800);
        IMapController mapController = map.getController();
        mapController.setZoom( 14.0 );
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> items = new ArrayList<>();
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


}