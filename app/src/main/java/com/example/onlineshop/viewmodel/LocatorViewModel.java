package com.example.onlineshop.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocatorViewModel extends AndroidViewModel {

    public static final String TAG = "Locator";
    private FusedLocationProviderClient mFusedLocationClient;

    private MutableLiveData<Location> mMyLocation = new MutableLiveData<>();

    public LiveData<Location> getMyLocation() {
        return mMyLocation;
    }

    public LocatorViewModel(@NonNull Application application) {
        super(application);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplication());
    }


    @SuppressLint("MissingPermission")
    public void requestLocation(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLocations().get(0);
                Log.d(TAG,"lat: " + location.getLatitude() + " lon: " + location.getLongitude());
                mMyLocation.setValue(location);
            }
        };

        mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}
