package com.example.googlebooks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mProgressBar = findViewById(R.id.pb_loading);
		try{
			URL bookURL = ApiUtils.buildUrl("cooking");
			new BookQueryTask().execute(bookURL);

		} catch (Exception e){
			Log.d("Error: ", e.getMessage());
		}
	}

	public class BookQueryTask extends AsyncTask<URL, Void, String>{

		@Override
		protected String doInBackground(URL... urls) {
			URL searchUrl = urls[0];
			String result = null;

			try{
				result = ApiUtils.getJSON(searchUrl);
			}catch (IOException e) {
				Log.d("Error: ", e.getMessage());
			}
			return result;
		}

		// return the result and update the UI
		@Override
		protected void onPostExecute(String result) {
			TextView responseTextView = findViewById(R.id.tvResponse);
			TextView errorTextView = findViewById(R.id.tvError);
			mProgressBar.setVisibility(View.INVISIBLE);

			if (result == null) {
				responseTextView.setVisibility(View.INVISIBLE);
				errorTextView.setVisibility(View.VISIBLE);
			}else {
				responseTextView.setVisibility(View.VISIBLE);
				errorTextView.setVisibility(View.INVISIBLE);
			}
			responseTextView.setText(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}
}
