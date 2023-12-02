package com.example.petcare;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    FrameLayout map;
    GoogleMap gMap;
    Location currentLocation;
    Marker marker;
    GoogleApi fusedClient;
    private static final int REQUEST_CODE = 101;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        map = findViewById(R.id.map);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        // Ініціалізація
        MapsInitializer.initialize(getApplicationContext());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String loc = searchView.getQuery().toString();
                if (loc == null) {
                    Toast.makeText(MapsActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(loc, 10);
                        if (addressList.size() > 0) {
                            LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                            if (marker != null) {
                                marker.remove();
                            }
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(loc);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 100);
                            gMap.animateCamera(cameraUpdate);
                            marker = gMap.addMarker(markerOptions);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        Log.i("myLog", "" + location.getLatitude());

                        currentLocation = location;
                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        assert supportMapFragment != null;
                        supportMapFragment.getMapAsync(MapsActivity.this);

                    }

                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions myLocation = new MarkerOptions().position(latLng).title("My Current Location");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(myLocation);

        for (MarkerOptions markerOptions : petStores) {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            googleMap.addMarker(markerOptions);
        }

        for (MarkerOptions markerOptions1 : veterinaryClinics) {
            markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            googleMap.addMarker(markerOptions1);
        }

//
//        LatLng x = new LatLng(48.625611274735576, 22.299921126054674);
//        MarkerOptions z = new MarkerOptions().position(x).title("petstore");
//        googleMap.addMarker(z);
    }


    private static final List<MarkerOptions> petStores = new ArrayList<MarkerOptions>() {{
        add(new MarkerOptions().position(new LatLng(48.62542969000418, 22.298797426646942)).title("PetStore \"Лев\""));
        add(new MarkerOptions().position(new LatLng(48.62562573855381, 22.299964358581995)).title("PetStore \"Master Zoo\""));
        add(new MarkerOptions().position(new LatLng(48.63117518489437, 22.27789727313587)).title("PetStore\"ROCKY and company\""));
        add(new MarkerOptions().position(new LatLng(48.60947944468801, 22.30521371643051)).title("PetStore \"Loyal friends\""));
        add(new MarkerOptions().position(new LatLng(48.6183623765551, 22.290557604594103)).title("PetStore \"Nature\""));
        add(new MarkerOptions().position(new LatLng(48.614258484810975, 22.29385423363614)).title("PetStore \"Майло\""));
        add(new MarkerOptions().position(new LatLng(48.617283644487976, 22.287759591135963)).title("PetStore \"CatDog\""));
        add(new MarkerOptions().position(new LatLng(48.613411796153635, 22.29486011161702)).title("PetStore \"Зоотовари\""));
        add(new MarkerOptions().position(new LatLng(48.607806518451284, 22.28815815261031)).title("PetStore \"Дружок\""));
        add(new MarkerOptions().position(new LatLng(48.604873115871, 22.28730411962668)).title("PetStore \"Майло\""));
        add(new MarkerOptions().position(new LatLng(48.603529308501095, 22.28887403503361)).title("PetStore \"Зоосвіт\""));
        add(new MarkerOptions().position(new LatLng(48.603223657411064, 22.28777048229624)).title("PetStore \"Фауна\""));
        add(new MarkerOptions().position(new LatLng(48.604249337724994, 22.285927843653376)).title("PetStore \"NAUTILUS\""));
        add(new MarkerOptions().position(new LatLng(48.60743488706748, 22.283064345612114)).title("PetStore \"NAUTILUS\""));
        add(new MarkerOptions().position(new LatLng(48.61040928306425, 22.27082096327793)).title("PetStore \"Зоомаркет\""));
        add(new MarkerOptions().position(new LatLng(48.616806712426616, 22.265139707579138)).title("PetStore \"Фауна\""));
    }};

    private static final List<MarkerOptions> veterinaryClinics = new ArrayList<MarkerOptions>() {{
        add(new MarkerOptions().position(new LatLng(48.63567258739457, 22.27708166717623)).title("Veterinary Clinics \"Цімбор\""));
        add(new MarkerOptions().position(new LatLng(48.63359808346921, 22.280867983081443)).title("Veterinary Clinics \"Pet.Medica\""));
        add(new MarkerOptions().position(new LatLng(48.62739020928784, 22.306084950611194)).title("Veterinary Clinics \"LicoVet\""));
        add(new MarkerOptions().position(new LatLng(48.61582455790382, 22.309699956871132)).title("Veterinary Clinics \"Ужгородська обласна державна лікарня ветеринарної медицини\""));
        add(new MarkerOptions().position(new LatLng(48.60518589775583, 22.28684678723742)).title("Veterinary Clinics \"ВЕТ сервіс\""));
        add(new MarkerOptions().position(new LatLng(48.613811924689365, 22.265732437715076)).title("Veterinary Clinics \"ДІВЕТ\""));
        add(new MarkerOptions().position(new LatLng(48.59428775758702, 22.273972184012617)).title("Veterinary Clinic"));
        add(new MarkerOptions().position(new LatLng(48.63071920116731, 22.24564805649268)).title("Veterinary Clinics \"Барбос\""));
        add(new MarkerOptions().position(new LatLng(48.61986934326202, 22.299279862637317)).title("Veterinary pharmacy"));

    }};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }
}