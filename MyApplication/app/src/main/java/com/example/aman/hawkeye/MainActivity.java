package com.example.aman.hawkeye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {


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

        mMapView = findViewById(R.id.mapView);
        showWebMap();
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
