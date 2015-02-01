package ca.ualberta.cs.travelapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class TSManager {

	static final String prefFile = "ItemList";
	static final String clKey = "TSList";
	Context context;

	static private TSManager tsManager = null;

	public static void initManager(Context context) {
		if (tsManager == null) {
			if (context==null) {
				throw new RuntimeException("Missing context for TSManager");
			}
			tsManager = new TSManager(context);
		}
	}

	public static TSManager getManager()
	{
		if (tsManager==null) {
			throw new RuntimeException("Did not initialize Manager");
		}
		return tsManager;
	}

	public TSManager(Context context) {
		this.context = context;
	}

	public TotalSum loadTS() throws ClassNotFoundException, IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		String tsData = settings.getString(clKey, "");	
		if (tsData.equals("")) {
			return new TotalSum();
		} else {	
			return tsFromString(tsData);
		}
	}

	static public TotalSum tsFromString(String itemListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(itemListData, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (TotalSum)oi.readObject();
	}

	static public String tsToString(TotalSum cl) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo;
		oo = new ObjectOutputStream(bo);
		oo.writeObject(cl);
		oo.close();
		byte bytes[] = bo.toByteArray();
		return Base64.encodeToString(bytes,Base64.DEFAULT);
	}

	public void saveTS(TotalSum cl) throws IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(clKey, tsToString(cl));
		editor.commit();	
	}

}
