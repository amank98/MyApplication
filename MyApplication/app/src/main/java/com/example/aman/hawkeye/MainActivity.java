package com.example.aman.hawkeye;

//import android.Manifest;
import android.content.Context;
//import android.content.pm.PackageManager;

//import android.Manifest;
/*import android.content.pm.PackageManager;
>>>>>>> 5625cc19fdb0dc423abf9ee5d9844196f0103820
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;*/
//import android.graphics.Color;
//import android.location.Location;
//import android.support.v4.content.ContextCompat;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
//import android.view.GestureDetector;
//import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
//import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;


import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.ArcGISMap;
//import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
//import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.MapView;
//import com.esri.arcgisruntime.portal.Portal;
//import com.esri.arcgisruntime.portal.PortalUser;
import com.esri.arcgisruntime.security.AuthenticationManager;
import com.esri.arcgisruntime.security.DefaultAuthenticationChallengeHandler;
import com.esri.arcgisruntime.security.OAuthConfiguration;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;


import java.net.MalformedURLException;


public class MainActivity extends AppCompatActivity {
    //private FusedLocationProviderClient mFusedLocationClient;


    private MapView mMapView;
    private PopupMenu popupMenu;
    private Button button;
    private boolean clicked = false;
    private boolean crtPt = false;
    private int currColor;



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


        button = (Button) findViewById(R.id.incidentBtn);
        popupMenu = new PopupMenu(this,findViewById(R.id.incidentBtn));


        mMapView = findViewById(R.id.mapView);

        showWebMap();

        MapViewTouchListener mMapViewTouchListener = new MapViewTouchListener(this, mMapView);
        mMapView.setOnTouchListener(mMapViewTouchListener);

        //Button action
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                showPopup(v);
                clicked = true;
                crtPt = true;

//
            }
        });



        //Popup menu item action
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                button.setText("Select a location");
                switch(id) {
                    case R.id.t1:
                        currColor = Color.argb(255, 133, 231, 242 ); //(low level incident)
                        crtPt = true;
                        return true;
                    case R.id.t2:
                        currColor = Color.argb(255, 0, 158, 216 ); //(low level incident)
                        crtPt = true;
                        return true;
                    case R.id.t4:
                        currColor = Color.argb(255, 255, 173, 200 ); //(low level incident)
                        crtPt = true;
                        return true;
                    case R.id.t3:
                        currColor = Color.argb(255,255, 110, 38);
                        crtPt = true; //(mid level incident)
                        return true;
                    case R.id.t5:
                        currColor = Color.argb(255, 242,179, 33);
                        crtPt = true; //(high level incident)
                        return true;
                    default:
                        return false;
                }
            }
        });


    }


    class MapViewTouchListener extends DefaultMapViewOnTouchListener {

        /**
         * Constructs a DefaultMapViewOnTouchListener with the specified Context and MapView.
         *
         * @param context the context from which this is being created
         * @param mapView the MapView with which to interact
         */
        public MapViewTouchListener(Context context, MapView mapView){
            super(context, mapView);
        }


        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            // get the screen point where user tapped

            // identify graphics on the graphics overlay
            if (crtPt) {
                GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
                mMapView.getGraphicsOverlays().add(graphicsOverlay);
                SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.argb(0,0,0,0), 20);
                SimpleLineSymbol sim = new SimpleLineSymbol();
                sim.setColor(currColor);
                sim.setWidth(2);
                symbol.setOutline(sim);

                Point p = mMapView.screenToLocation(new android.graphics.Point((int) motionEvent.getX(), (int) motionEvent.getY()));
                Point graphicPoint = new Point(p.getX(), p.getY());

                Graphic graphic = new Graphic(graphicPoint, symbol);
                graphicsOverlay.getGraphics().add(graphic);
                Log.d("button", "added");
                button.setText("Report Incident");
            }
            crtPt = false;
            return super.onSingleTapConfirmed(motionEvent);
        }

    }




    private void showWebMap() {
        String itemId = "18c29aef83f145b2b95e548df04398c9";
        String url = "https://www.arcgis.com/sharing/rest/content/items/" + itemId + "/data";
        ArcGISMap map = new ArcGISMap(url);

        mMapView.setMap(map);
    }

    //Popup menu
    public void showPopup(View v) {
        if (!clicked) {
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.incidentmenu, popupMenu.getMenu());
        }
        popupMenu.show();
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
