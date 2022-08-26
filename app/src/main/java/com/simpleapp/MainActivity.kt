package com.simpleapp

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    lateinit var btn: Button
    lateinit var pickup:TextView
    lateinit var dropdown:TextView
    val pickupRequestMessage=1
    val dropdownRequestMessage=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abdo)
        pickup=findViewById(R.id.frg_tx1)
        dropdown=findViewById(R.id.frg_tx2)
        val mapFragment =supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)


        //check location permission
        val preciseLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val approximateLocationPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if(preciseLocationPermission==PackageManager.PERMISSION_GRANTED||approximateLocationPermission==PackageManager.PERMISSION_GRANTED)
            checkGps()
        else
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION))

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.US);
        }

    }

    private fun checkGps() {
        val locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            showDialog()
        Log.e(TAG, "checkGps: ${locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)}" )
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(this)

        builder.setMessage("to access your location")
                .setTitle("Open GPS")

        builder.setPositiveButton("open",DialogInterface.OnClickListener(){ dialog, which ->

            val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            enableGPS.launch(intent)

        })

        builder.show()
    }
    override fun onMapReady(p0: GoogleMap) {
        map = p0
        // Add a marker in Sydney and move the camera

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        map.isMyLocationEnabled=true
    }
    val enableGPS = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
        checkGps()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
            Toast.makeText(this,"You must let us access GPS",Toast.LENGTH_SHORT).show()
            // No location access granted.
        }
        }
    }
}