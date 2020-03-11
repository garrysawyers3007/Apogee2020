package com.bitspilani.apogeear.Fragments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bitspilani.apogeear.MainActivity;
import com.bitspilani.apogeear.R;

import com.bitspilani.arapogee.UnityPlayerActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.SphericalUtil;
import com.vuforia.INIT_FLAGS;
import com.vuforia.Vuforia;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private Location currentLocation;
    private MarkerOptions markerOptions1;
    private boolean rmvMark = false;
    private List<Marker> m = new ArrayList<>();
    private double RADIUS = 200f;
    private Button navBtn,removeMarkerBtn;
    FirebaseFirestore db;
    private int i,j,k;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    private static final String TAG = "MapFragment";
    private static final int LOCATION_PERMISSION_REQUEST_CODE =1234;
    private static final float DEFAULT_ZOOM = 17.5f;

    private Boolean mLocationPermissionsGranted = false;
    public Map() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        // Inflate the layout for this fragment
        navBtn = rootView.findViewById(R.id.nav_btn);
        removeMarkerBtn = rootView.findViewById(R.id.remove_marker);
        db=FirebaseFirestore.getInstance();

        db.collection("Events").get();

        getLocationPermission();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Theme customization
        try { // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            Objects.requireNonNull(getActivity()), R.raw.dark_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        if (mLocationPermissionsGranted){
            getDeviceLocation();

            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setCompassEnabled(false);

           addCoinsForEvents();
           addUniversalCoins();
        }else{
            showGPSDisabledAlertToUser();
        }
        mapClickListener();
    }

    private void addUniversalCoins() {
        db.collection("Coins").document("Universal Coins").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                List<Double> latA, lngA, latB, lngB, latC, lngC;

                latA = (List<Double>) documentSnapshot.get("latA");
                lngA = (List<Double>) documentSnapshot.get("lngA");
                latB = (List<Double>) documentSnapshot.get("latB");
                lngB = (List<Double>) documentSnapshot.get("lngB");
                latC = (List<Double>) documentSnapshot.get("latC");
                lngC = (List<Double>) documentSnapshot.get("lngC");

                int coinsa =  Integer.parseInt(documentSnapshot.get("CoinsA").toString().trim());
                int coinsb =  Integer.parseInt(documentSnapshot.get("CoinsB").toString().trim());
                int coinsc =  Integer.parseInt(documentSnapshot.get("CoinsC").toString().trim());

                for (i=0;i<coinsa;i++){
                    LatLng latLng = new LatLng(latA.get(i),lngA.get(i));
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.coinimage)));
                //    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                Location l = new Location("");
                                l.setLatitude(marker.getPosition().latitude);
                                l.setLongitude(marker.getPosition().longitude);
                                double d = (double) currentLocation.distanceTo(l);
                                if (d<RADIUS) {
                                    Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getContext(), "Go close to coin", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
                }
                for (j=0;j<coinsb;j++){
                    LatLng latLng = new LatLng(latB.get(j),lngB.get(j));
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.coin_green)));
                   // map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                Location l = new Location("");
                                l.setLatitude(marker.getPosition().latitude);
                                l.setLongitude(marker.getPosition().longitude);
                                double d = (double) currentLocation.distanceTo(l);
                                if (d<RADIUS) {
                                    Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getContext(), "Go close to coin", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
                }
                for (k=0;k<coinsc;k++){
                    LatLng latLng = new LatLng(latC.get(k),lngC.get(k));
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.coin_red)));
                 //   map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                    Location l = new Location("");
                                    l.setLatitude(marker.getPosition().latitude);
                                    l.setLongitude(marker.getPosition().longitude);
                                    double d = (double) currentLocation.distanceTo(l);
                                    if (d<RADIUS) {
                                        Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getContext(), "Go close to coin", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                            }
                        });
                    }
                }
        });
    }

    private void mapClickListener() {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                // on click marker appereance
                markerOptions1 = new MarkerOptions();
                markerOptions1.position(latLng);
                m.add(0,map.addMarker(markerOptions1));
                for (int i=0;i<m.size();i++){
                    if (i==0) m.get(i).setVisible(true);
                    else m.get(i).setVisible(false);
                }
                rmvMark = true;
                if (rmvMark==true) {
                    removeMarkerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i=0;i<m.size();i++){
                                m.get(i).setVisible(false);
                            }
                            rmvMark = false;
                            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(@NonNull Marker marker) {
                                    Location l = new Location("");
                                    l.setLatitude(marker.getPosition().latitude);
                                    l.setLongitude(marker.getPosition().longitude);
                                    double d = (double) currentLocation.distanceTo(l);
                                    if (d<RADIUS) {
                                        Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getContext(), "Go close to coin", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                }
                            });
                        }
                    });
                }else{
                    removeMarkerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Please place a marker to remove", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                navBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rmvMark) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" + latLng.latitude + "," + latLng.longitude); //// "geo:28.356478,75.583785?q=28.456388,75.583964"
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);
                                }
                            }, 1000);
                        }else{
                            navBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(), "Place the marker", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return false;
                    }
                });
            }
        });
    }


    private void addCoinsForEvents(){
        db.collection("Events").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document:queryDocumentSnapshots){
                    double lat = document.getDouble("lat");
                    double lng = document.getDouble("long");

                    LatLng latLng = new LatLng(lat,lng);
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.coinimage)));
                 //   map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    if (rmvMark==true){
                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                               return true;
                            }
                        });
                    }else{
                        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                Location l = new Location("");
                                l.setLatitude(marker.getPosition().latitude);
                                l.setLongitude(marker.getPosition().longitude);
                                double d = (double) currentLocation.distanceTo(l);
                                if (d<RADIUS) {
                                    Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getContext(), "Go close to coin", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
                    }
                }
            }
        });
    }

    private void getDeviceLocation(){
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        try{
            if (mLocationPermissionsGranted){
                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"onComplete: found location");
                            currentLocation = (Location)task.getResult();
                            if (currentLocation != null) {
                                cameraZoom(currentLocation);
                            }else{
                                showGPSDisabledAlertToUser();
                            }
                        }else{
                            Log.d(TAG,"onComplete: current Location is null");
                            Toast.makeText(getActivity(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG,"getDeviceLocation: SecurityException: "+ e.getMessage());
        }
    }


    private void initMap(){
        // Obtain the SupportMapFragment and get notified when map is ready to be used
        assert getFragmentManager() != null;
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void cameraZoom(Location location){
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude())) // Sets the new camera position
                .zoom(DEFAULT_ZOOM) // Sets the zoom
                .bearing(0) // Rotate the camera
                .tilt(70) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                // Code to execute when the animateCamera task has finished
            }
            @Override
            public void onCancel() {
                // Code to execute when the user has canceled the animateCamera task
            }
        });
    }

    private void getLocationPermission(){
        Log.d("isnull","Null");

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }else{
            mLocationPermissionsGranted = true;
            initMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        return;
                    }
                }
                mLocationPermissionsGranted = true;
                // initialize our map
                initMap();
            }
        }
    }

    // gps dialog box
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
