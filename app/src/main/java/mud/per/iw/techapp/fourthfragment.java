package mud.per.iw.techapp;




        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapView;
        import com.google.android.gms.maps.MapsInitializer;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.maps.android.clustering.ClusterManager;

        import androidx.appcompat.app.AlertDialog;
        import androidx.fragment.app.Fragment;

public class fourthfragment extends Fragment implements
        GoogleMap.OnMarkerClickListener
{

    View view;
    Button firstButton;
    MapView mMapView;
    private GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fourthfragment, container, false);

        String strlat=getArguments().getString("lat");
        String strlon=getArguments().getString("longi");
        String strdesc=getArguments().getString("desc");
        String straddr=getArguments().getString("addr");
        Log.wtf("icw",strlon);
// get the reference of Button
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                mMap.setOnMarkerClickListener(fourthfragment.this::onMarkerClick);

                // For showing a move to my location button
                // googleMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                // For dropping a marker at a point on the Map

                if(!strlat.equals("null")&&!strlon.equals("null")) {
                    LatLng sydney = new LatLng(Double.parseDouble(strlat), Double.parseDouble(strlon));

                    googleMap.addMarker(new MarkerOptions().position(sydney).title(strdesc).snippet(straddr).icon(BitmapDescriptorFactory.fromResource(R.drawable.rodent)));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(8).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                // For zooming automatically to the location of the marker

                view.findViewById(R.id.progressBar).setVisibility(View.GONE);

            }




        });
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Contact Us", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                showMapTypeSelectorDialog();

            }
        });




        return view;
    }
    private static final CharSequence[] MAP_TYPE_ITEMS =
            {"Road Map", "Satellite", "Terrain", "Hybrid"};

    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = "Select Map Type";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = googleMap.getMapType() - 1;

        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.

                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 1:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            default:
                                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                        dialog.dismiss();
                    }
                }
        );

        // Build the dialog and show it.
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        Log.wtf("icw","Map clicked");
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);

        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


}
