package com.example.aman.hawkeye;

//import android.Manifest;
/*import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;*/
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*import android.util.Log;
import android.view.View;*/ //maybe this??
import android.widget.Button;

//import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
//import com.esri.arcgisruntime.layers.Layer;
//import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
//import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
//import com.esri.arcgisruntime.portal.Portal;
//import com.esri.arcgisruntime.portal.PortalUser;
import com.esri.arcgisruntime.security.AuthenticationManager;
import com.esri.arcgisruntime.security.DefaultAuthenticationChallengeHandler;
import com.esri.arcgisruntime.security.OAuthConfiguration;
/*import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;*/
//import com.esri.arcgisruntime.geometry.SpatialReference;
/*import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;*/
//import com.esri.arcgisruntime.mapping.ArcGISMap;
/*import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;*/
//import com.esri.arcgisruntime.mapping.view.MapView;
//import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;


import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {
    //private FusedLocationProviderClient mFusedLocationClient;


    private MapView mMapView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String clientId = getResources().getString(R.string.client_id);
        String redirectUri = getResources().getString(R.string.redirect_uri);

        try {
            // configuration for OAuth access information against arcgis.com portal
            OAuthConfiguration oAuthConfiguration = new OAuthConfiguration("https://www.arcgis.com", clientId, redirectUri);
            // challenge handler using this activity to launch browser OAuth login
            DefaultAuthenticationChallengeHandler authenticationChallengeHandler = new DefaultAuthenticationChallengeHandler(this);
            // Authentication Manager manages authentication for OAuth configuration
            AuthenticationManager.setAuthenticationChallengeHandler(authenticationChallengeHandler);
            AuthenticationManager.addOAuthConfiguration(oAuthConfiguration);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /*
        // Set the DefaultAuthenticationChallegeHandler to allow authentication with the portal.
        DefaultAuthenticationChallengeHandler handler = new DefaultAuthenticationChallengeHandler(this);
        AuthenticationManager.setAuthenticationChallengeHandler(handler);
        // Create a Portal object, indicate authentication is required
        final Portal portal = new Portal("http://www.arcgis.com", true);
        portal.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (portal.getLoadStatus() == LoadStatus.LOADED) {
                    PortalUser user = portal.getUser();
                    String userDisplayName = user.getFullName(); // Returns display name of authenticated user
                }
            }
        });
        portal.loadAsync();
           */
        Button button = (Button) findViewById(R.id.incidentBtn);
        mMapView = findViewById(R.id.mapView);
        showWebMap();
        /*mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
*/

       // ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 38.994984, -76.940491, 16);
       // ArcGISMapImageLayer censusLayer = new ArcGISMapImageLayer("https://services2.arcgis.com/1UvBaQ5y1ubjUPmd/arcgis/rest/services/Crime_Data/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json");
       // map.getOperationalLayers().add(censusLayer);









       // ArcGISMap map = new ArcGISMap(Basemap.Type.DARK_GRAY_CANVAS_VECTOR, 38.994984, -76.940491, 16);

        //mMapView.setMap(map);

       // ArcGISMapImageLayer crimeLayer = new ArcGISMapImageLayer("https://megacity.esri.com/ArcGIS/rest/services/Demographics/USA_CrimeIndex/MapServer");

        //map.getOperationalLayers().add(crimeLayer);
    }

    private void showWebMap() {
        String itemId = "48aa410a6d7b497d9479f3a197d8a889";
        String url = "https://www.arcgis.com/sharing/rest/content/items/" + itemId + "/data";
        ArcGISMap map = new ArcGISMap(url);

        mMapView.setMap(map);
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
