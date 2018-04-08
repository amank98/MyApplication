package com.example.aman.hawkeye;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity implements LocationListener {
    private FusedLocationProviderClient mFusedLocationClient;


    private MapView mMapView;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.incidentBtn);
        mMapView = findViewById(R.id.mapView);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, (LocationListener) this);
//        }

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("button", "Permission there");
//            mFusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            // Got last known location. In some rare situations this can be null.
//                            if (location != null) {
//                                Log.d("button", "in here");
//                                GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
//                                mMapView.getGraphicsOverlays().add(graphicsOverlay);
//                                SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
//                                Point graphicPoint = new Point(38.994984, -76.940491, SpatialReferences.getWebMercator());
//                                Graphic graphic = new Graphic(graphicPoint, symbol);
//                                graphicsOverlay.getGraphics().add(graphic);
//                            }
//                            else {
//                                Log.d("button", "location is null");
//                            }
//                        }
//                    });
//
//            mFusedLocationClient.getLastLocation().addOnFailureListener(this, new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d("button", "failed to get location");
//                    GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
//                    mMapView.getGraphicsOverlays().add(graphicsOverlay);
//                    SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
//                    Point graphicPoint = new Point(38.994984, -76.940491, SpatialReferences.getWgs84());
//
//
//
//
//                    Graphic graphic = new Graphic(graphicPoint, symbol);
//                    graphicsOverlay.getGraphics().add(graphic);
//                }
//            });
//        }
//        else {
//            Log.d("Button", "No Permissions");
//        }


        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 38.994984, -76.940491, 16);
        ArcGISMapImageLayer censusLayer = new ArcGISMapImageLayer("https://services2.arcgis.com/1UvBaQ5y1ubjUPmd/arcgis/rest/services/Crime_Data/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json");
        map.getOperationalLayers().add(censusLayer);


        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getDownTime() >= 2000) {
                    GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
                    mMapView.getGraphicsOverlays().add(graphicsOverlay);
                    SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
                    Point p = mMapView.screenToLocation(new android.graphics.Point((int)motionEvent.getX(), (int)motionEvent.getY()));
                    Point graphicPoint = new Point(p.getX(), p.getY());

                    Graphic graphic = new Graphic(graphicPoint, symbol);
                    graphicsOverlay.getGraphics().add(graphic);
                    Log.d("button", "added");
                }
                return true;
            }
        });

//        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
//        mMapView.getGraphicsOverlays().add(graphicsOverlay);
//
//
//
//
//        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
//        Point graphicPoint = new Point(38.994984, -76.940491, SpatialReferences.getWebMercator());
//        Point wgsPoint  = new Point(-76.940491, 38.994984);
//        Point myPoint = (Point) GeometryEngine.project(wgsPoint, SpatialReferences.getWgs84());
//
//
//
////        Graphic graphic = new Graphic(graphicPoint, symbol);
//        Graphic graphic = new Graphic(myPoint, symbol);
//        graphicsOverlay.getGraphics().add(graphic);



        mMapView.setMap(map);

        //Button action
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("button", "Button clicked");
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    mFusedLocationClient.getLastLocation();
                    Location loc = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    if (loc != null) {
                        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
                        Point graphicPoint = new Point(38.994984, -76.940491, SpatialReferences.getWebMercator());
                        Point wgsPoint  = new Point(-76.940491, 38.994984);
                        Point myPoint = (Point) GeometryEngine.project(wgsPoint, SpatialReferences.getWgs84());
                        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
                        mMapView.getGraphicsOverlays().add(graphicsOverlay);
                        Graphic graphic = new Graphic(myPoint, symbol);
                        graphicsOverlay.getGraphics().add(graphic);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause(){
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("LOC", "DISABLED");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("LOC", "ENABLED");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LOC", "CHANGED");
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
