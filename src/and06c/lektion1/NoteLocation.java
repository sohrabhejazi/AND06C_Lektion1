package and06c.lektion1;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.android.gms.maps.model.LatLng;
public class NoteLocation
{
public LatLng geoPoint;
public int altitude;
private Date timeStamp;
private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// latitude, longitude in degrees,
// altitude in Metern
public NoteLocation(double latitude,double longitude, int altitude)
{
	this.geoPoint = new LatLng(latitude, longitude);
	this.altitude = altitude;
	this.timeStamp = new Date();
}
@Override
public String toString() {
	return geoPoint.latitude + "/" + geoPoint.longitude
	+ ", Altitude=" + altitude + "m ("
	+ sdf.format(timeStamp) + ")";
}
}