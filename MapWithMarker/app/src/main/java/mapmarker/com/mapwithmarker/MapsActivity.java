package mapmarker.com.mapwithmarker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.TooManyListenersException;

import mapmarker.com.mapwithmarker.model.MLocation;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SearchView searchView;
    private static final String TAG = "mapsActivity";
    private MySqliteHelper mySqliteHelper;
    private SQLiteDatabase database;
    private ArrayList<MLocation> markersList;
    private ArrayList<Marker> mSavedMarkerList;
    private LatLngBounds.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mySqliteHelper = new MySqliteHelper(getBaseContext());

//         for focusing all markes
        builder = new LatLngBounds.Builder();


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                Log.i(TAG, "LatLng " + place.getLatLng());
//                inserting search location adderss and value in db
                createRow(place.getName().toString(), place.getLatLng().latitude,
                        place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        identfication user is comming from serach btn or view btn
        if (getIntent().getStringExtra(Config.IDENTITYKEY).equalsIgnoreCase(Config.VIEWVALUE)) {
//            reading saved data from db
            readData();
//            putting fatched markers on map
            drawSearchedMarkers();
        }
    }

    private void drawSearchedMarkers() {
        if (markersList != null && markersList.size() != 0){
            mSavedMarkerList = new ArrayList<>();

            for (int i = 0 ; i < markersList.size() ; i++){
                mSavedMarkerList.add(mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(markersList.get(i).getLat(),markersList.get(i).getLng()))
                        .title(markersList.get(i).getAddress())));
            }
        }else {
            Toast.makeText(this,Config.TOASTMSG,Toast.LENGTH_LONG).show();
        }

        for (Marker m : mSavedMarkerList){
            builder.include(m.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 50;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,padding);
        mMap.animateCamera(cu);
    }

    public void open() throws SQLException {
        database = mySqliteHelper.getWritableDatabase();
    }

    public void close() throws SQLException {
        mySqliteHelper.close();
    }

    public void createRow(String address, Double lat,Double lng) {
        open();
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.COLUMN_NAME, address);
        values.put(MySqliteHelper.COLUMN_LAT, lat);
        values.put(MySqliteHelper.COLUMN_LNG,lng);
        long insertid = database.insert(MySqliteHelper.TABLE_NAME, null, values);
        Toast.makeText(this,address ,Toast.LENGTH_SHORT).show();
        close();
    }

    public void readData() {
        markersList = new ArrayList<>();
        database = mySqliteHelper.getReadableDatabase();
        String columns[] = {mySqliteHelper.COLUMN_ID, MySqliteHelper.COLUMN_NAME,
                mySqliteHelper.COLUMN_LAT,mySqliteHelper.COLUMN_LNG};
        SQLiteDatabase db = mySqliteHelper.getReadableDatabase();
        Cursor cursor = db.query(MySqliteHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(MySqliteHelper.COLUMN_ID);
            int index2 = cursor.getColumnIndex(MySqliteHelper.COLUMN_NAME);
            int index3 = cursor.getColumnIndex(MySqliteHelper.COLUMN_LAT);
            int index4 = cursor.getColumnIndex(MySqliteHelper.COLUMN_LNG);
            int id = cursor.getInt(index1);
            String address = cursor.getString(index2);
            Double lat = cursor.getDouble(index3);
            Double lng = cursor.getDouble(index4);
            markersList.add(new MLocation(id,address,lat,lng));
        }
        close();
    }
}
