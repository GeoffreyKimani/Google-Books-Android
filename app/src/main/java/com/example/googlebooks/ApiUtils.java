package com.example.googlebooks;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class ApiUtils {
	public ApiUtils() {}

	private static final String BASE_URL =
			"https://www.googleapis.com/books/v1/volumes";

	private static final String QUERY_PARAM_KEY = "q";
	private static final String KEY = "key";
	private static  final String API_KEY = "AIzaSyBEyWEEI3pojQyF9evLr42Z3HLrPP8F6xw";

	public static URL buildUrl (String title){

		URL url = null;
		Uri uri = Uri.parse(BASE_URL).buildUpon()
				.appendQueryParameter(QUERY_PARAM_KEY, title)
//				.appendQueryParameter(KEY, API_KEY)
				.build();

		try{
			url = new URL(uri.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}

	public static String getJSON(URL url) throws IOException{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {
			InputStream inputStream = connection.getInputStream();
			Scanner scanner = new Scanner(inputStream);

			scanner.useDelimiter("\\A"); // fetch all the data

			// check if there is data in the scanner
			boolean hasData = scanner.hasNext();

			if (hasData) {
				return scanner.next();
			}else {
				return null;
			}
		} catch (Exception e){
			Log.d("Error", e.toString());
			return null;
		}
		finally {
			connection.disconnect();
		}
	}
}
