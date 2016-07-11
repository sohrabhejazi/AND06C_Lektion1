package and06c.lektion1;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class NoteMapActivity extends ActionBarActivity {
	private MapView mapView;
	private final String TAG = getClass().getSimpleName();
	private GoogleMap googleMap;
	private LatLng geoPoint;
	
	@Override
	protected void onStart()
	{
			super.onStart();
			
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notemap);
		mapView = (MapView) this.findViewById(R.id.map);
		// 1. vor allem erst mal forwording
		mapView.onCreate(savedInstanceState);
		// 2. Geodaten übernehmen (Code 3.2)
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null) {
		geoPoint =
		new LatLng(bundle.getDouble("latitude"),
		bundle.getDouble("longitude"));
		} else
		Log.d(TAG, "noch keine Geoposition verfügbar");
		// 3. Map initialisieren
		try{
		MapsInitializer.initialize(this);
		} catch (Exception e) {
		Log.e(TAG, e.toString());
		}
		// 4. Map verfügbar machen
		googleMap = mapView.getMap();
		if(googleMap != null)
		Log.d(TAG, "Map in onCreate verfügbar");
		else
		Log.d(TAG,
		"Map in onCreate n i c h t verfügbar");
		// 5. Marker adden
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.pin);
		googleMap.addMarker(new MarkerOptions()
		.position(geoPoint)
		.title("My Location")
		.snippet("Dies ist der Infotext")
		.icon(bitmap)
		.anchor(0.5F, 0.5F));
		// 5. Auf Marker zentrieren und Zoomen
		try {
		CameraUpdate update = CameraUpdateFactory
		.newCameraPosition(CameraPosition
		.fromLatLngZoom(geoPoint, 1));
		Log.d(TAG, "camera moved to the location");
		googleMap.moveCamera(update);
		} catch(Exception e) {
		Log.e(TAG, e.toString());
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onDestroy() {
	super.onDestroy();
	mapView.onDestroy();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	mapView.onSaveInstanceState(outState);
	}
	@Override
	public void onLowMemory() {
	mapView.onLowMemory();
	}
	@Override
	protected void onResume() {
	super.onResume();
	mapView.onResume();
	}
	@Override
	protected void onPause() {
	super.onPause();
	mapView.onPause();
	}
	
}
