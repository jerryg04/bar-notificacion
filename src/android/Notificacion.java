package com.notificacionbar.cordova.plugins.notificacionbar;
import java.io.File;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class Notificacion extends CordovaPlugin{
	@SuppressWarnings("deprecation")
	private void makeNotification(String uriPath,String ticker,String contentTitle,String contentText,String mimeType,String summaryText){
		String path ="/storage/sdcard0/azabacheShare/flyer_personalizado.jpg";
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri data = Uri.fromFile( new File(path));
		intent.setDataAndType(data,"image/*");
		
		PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this.cordova.getActivity().getApplicationContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
		
		
		android.app.Notification mBuilder = new android.app.Notification(android.R.drawable.ic_menu_gallery,"Imagen",System.currentTimeMillis());
		mBuilder.tickerText=ticker;
		mBuilder.defaults = Notification.DEFAULT_VIBRATE;
		mBuilder.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.ledARGB=0xff0000;
		mBuilder.ledOnMS=1000;
		mBuilder.ledOffMS=2500;
		mBuilder.priority = Notification.PRIORITY_HIGH;
		
		mBuilder.setLatestEventInfo(cordova.getActivity().getApplicationContext(), contentTitle, contentText, resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) this.cordova.getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder);
	}
	@Override
	public boolean execute(String action,JSONArray args,CallbackContext callbackContext){
		try {
			JSONObject jsonObj = args.getJSONObject(0);
			makeNotification(jsonObj.getString("uri"),jsonObj.getString("ticker"),jsonObj.getString("contentTitle"),jsonObj.getString("contentText"),
					jsonObj.getString("mimeType"),jsonObj.getString("summaryText"));
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
			return true;
		} catch (JSONException e) {
			// TODO: handle exception
			Log.e("PhoneGapLog", "Notificacion Plugin: Error: " + PluginResult.Status.JSON_EXCEPTION);
			e.printStackTrace();
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
			return false;
		}
	}

}
