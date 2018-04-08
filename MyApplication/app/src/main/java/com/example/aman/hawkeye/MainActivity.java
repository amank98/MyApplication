package com.example.aman.hawkeye;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;


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


public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;


    private MapView mMapView;
    PopupMenu popupMenu;
    boolean clicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.incidentBtn);
        popupMenu = new PopupMenu(this,findViewById(R.id.incidentBtn));


        mMapView = findViewById(R.id.mapView);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("button", "Permission there");
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d("button", "in here");
                                GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
                                mMapView.getGraphicsOverlays().add(graphicsOverlay);
                                SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
                                Point graphicPoint = new Point(38.994984, -76.940491, SpatialReferences.getWebMercator());
                                Graphic graphic = new Graphic(graphicPoint, symbol);
                                graphicsOverlay.getGraphics().add(graphic);
                            }
                            else {
                                Log.d("button", "location is null");
                            }
                        }
                    });

            mFusedLocationClient.getLastLocation().addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("button", "failed to get location");
                    GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
                    mMapView.getGraphicsOverlays().add(graphicsOverlay);
                    SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
                    Point graphicPoint = new Point(38.994984, -76.940491, SpatialReferences.getWgs84());




                    Graphic graphic = new Graphic(graphicPoint, symbol);
                    graphicsOverlay.getGraphics().add(graphic);
                }
            });
        }
        else {
            Log.d("Button", "No Permissions");
        }


        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 38.994984, -76.940491, 16);
        ArcGISMapImageLayer censusLayer = new ArcGISMapImageLayer("https://services2.arcgis.com/1UvBaQ5y1ubjUPmd/arcgis/rest/services/Crime_Data/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json");
        map.getOperationalLayers().add(censusLayer);


        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(graphicsOverlay);




        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12);
//        Point graphicPoint = new Point(38.994984, -76.940491, SpatialReferences.getWebMercator());
        Point wgsPoint  = new Point(-76.940491, 38.994984);
        Point myPoint = (Point) GeometryEngine.project(wgsPoint, SpatialReferences.getWgs84());


//        Graphic graphic = new Graphic(graphicPoint, symbol);
        Graphic graphic = new Graphic(myPoint, symbol);
        graphicsOverlay.getGraphics().add(graphic);

        mMapView.setMap(map);

        //Button action
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                showPopup(v);
                clicked = true;
//                Log.d("button", "Button clicked");
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    mFusedLocationClient.getLastLocation();
//
//                }
            }
        });

        //Popup menu item action
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.unarm_rob:
                        Log.d("ok","unarmed robbery reported");
                        return true;
                    case R.id.arm_rob:
                        Log.d("ok","armed robbery reported");
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

    //Popup menu
    public void showPopup(View v) {
        if (!clicked) {
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.incidentmenu, popupMenu.getMenu());
        }
        popupMenu.show();
    }

    //Add point to map once incident is selected
    public void addPoint() {
        Log.d("popupmenu","We clicked!");
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
}
