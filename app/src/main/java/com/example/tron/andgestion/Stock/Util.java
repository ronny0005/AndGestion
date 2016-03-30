package com.example.tron.andgestion.Stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Util {

	public Util() {

	}

	@SuppressLint("NewApi")
	public static String getJsonFromServer(String lien) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		final StringBuilder sb = new StringBuilder();
		InputStream is = (InputStream) new URL(lien).getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String result, line = reader.readLine();
		result = line;
		while((line=reader.readLine())!=null){
			result+=line;
		}
		return result;
		// read the JSON results into a string
	}


}