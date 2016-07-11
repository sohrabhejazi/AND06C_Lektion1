package and06c.lektion1;


import java.util.List;

import com.google.android.gms.maps.MapView;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GatherActivity extends Activity
{
	private String newLine =System.getProperty("line.separator");
	private NoteLocation lastLocation;
	private TextView tvHello;
	private TextView tvOutput; // erst später benötigt
	private List<String> providers;
	private int providerIndex;
	private LocationManager locationManager;
	private final String TAG =GatherActivity.class.getSimpleName();
	private NoteLocationListener locationListener;
	// Felder für den GPS-Takt:
	private int minTime = 5000; // in Millisekunden
	private int minDistance = 5; // in Metern
		@Override
		protected void onCreate(Bundle savedInstanceState)
		        {
						super.onCreate(savedInstanceState);setContentView(R.layout.main);
						tvHello = (TextView) this.findViewById(R.id.tv_hello);
						tvOutput = (TextView) this.findViewById(R.id.tv_output);
						locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
						//providers = locationManager.getAllProviders();
						providers = locationManager.getProviders(true);
						providerIndex = providers.size()-1;
						this.listProviders(providers);
						tvOutput.append("Provider: " +
								providers.get(providerIndex) + newLine);
						locationListener = new NoteLocationListener();
						locationManager.requestLocationUpdates(
								providers.get(providerIndex), minTime,
								minDistance, locationListener);
						
						this.showProperties();
						
						Button btQuit = (Button)
								this.findViewById(R.id.bt_quit);
						Button btToggle = (Button) this
								.findViewById(R.id.bt_toggle);
						Button btLocation = (Button)
								this.findViewById(R.id.bt_location);
						btLocation.setOnClickListener(new OnClickListener()
								{
										@Override
										public void onClick(View v) {
										Intent intent = new Intent(GatherActivity.this,
										NoteMapActivity.class);
										if(lastLocation != null) {
											intent.putExtra("latitude",
											lastLocation.geoPoint.latitude);
											intent.putExtra("longitude",
											lastLocation.geoPoint.longitude);
										}
										try {
										startActivity(intent);
										} catch(Exception e) {
										Log.d("TAG", e.toString());
										}
										}
								});
								
						btQuit.setOnClickListener(new OnClickListener()
								{
								@Override
								public void onClick(View v) {
								endLocalization();
								}
								});
							
						btToggle.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
									if(providerIndex < providers.size()-1)
									providerIndex++;
									else
									providerIndex = 0;
									locationManager.removeUpdates(locationListener);
									locationManager.requestLocationUpdates(
									providers.get(providerIndex), minTime, minDistance, locationListener);
									tvOutput.setText("Provider: "
									+providers.get(providerIndex) + newLine);
									 showProperties();
									}
									});
				}
		
		@Override
		protected void onStart() {
				super.onStart();
				// provisorische Initialisierung für Tests:
				lastLocation = new NoteLocation(37.422006,-122.084895, 200);
		}
		
		protected void endLocalization() {
					locationManager.removeUpdates(locationListener);
					tvOutput.append("Geodatenerfassung beendet");
			}
		@Override
		protected void onDestroy() {
					super.onDestroy();
					this.endLocalization();
					
			}
private void listProviders(List<String> providers)
				{
						// Einbindung der String-Ressource "hello",
						// gefolgt von einem Leerzeichen
						StringBuilder sb = new StringBuilder(
						getResources().getString(R.string.hello));
						sb.append(" ");
						for(String provider : providers) {
						sb.append(provider);
						sb.append(", ");
						}
						String list = sb.toString();
						// letztes Komma entfernen:
						tvHello.setText(list.substring(0, list.length()-2));
				}
private void showProperties() {
			StringBuilder sb = new StringBuilder();
			String providerName =
			providers.get(providerIndex);
			sb.append("Provider: "
			+ providerName + newLine);
			LocationProvider provider =
			locationManager.getProvider(providerName);
			if (provider!= null) {
			sb.append("Horizontale Genauigkeit: ");
			sb.append((provider.getAccuracy() == 1)? "FINE" : "COARSE");
			sb.append(newLine);
			sb.append("Unterstützt Höhenermittlung: ");
			sb.append(provider.supportsAltitude());
			sb.append(newLine);
			sb.append("Erfordert Satellit: ");
			sb.append(provider.requiresSatellite());
			sb.append(newLine);
			tvOutput.setText(sb.toString());
			} else
			tvOutput.setText("Kein Provider verfügbar");
			}
class NoteLocationListener implements LocationListener
		{
				@Override
				public void onLocationChanged(Location location)
				{
					lastLocation = new NoteLocation(
					location.getLatitude(),
					location.getLongitude(),
					(int)location.getAltitude());
					tvOutput.append(lastLocation.toString());
					tvOutput.append(newLine);
				}
		
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					// TODO Auto-generated method stub
					
				}
		
				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}
		
				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
					
				}
		  }
		
}

