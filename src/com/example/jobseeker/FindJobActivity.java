package com.example.jobseeker;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FindJobActivity extends Activity {
	EditText edtJobtitle;
	Spinner spnLocation, spnCategory;
	Button btnfindjob;

	String String_Json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findjob);
		edtJobtitle = (EditText) findViewById(R.id.edtJobtitle);
		spnLocation = (Spinner) findViewById(R.id.spnLocation);
		spnCategory = (Spinner) findViewById(R.id.spnCategory);
		btnfindjob = (Button) findViewById(R.id.btnfindjob);

		getJSONCity();
		getJSONCategory();


	}

	private void getJSONCategory() {
		// TODO Auto-generated method stub
		class getallCategory extends AsyncTask<Void, Void, String> {

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				String_Json = s;
				showAllCategory();
			}

			private void showAllCategory() {
				// TODO Auto-generated method stub
				ArrayList<String> CategoryId_list = new ArrayList<String>();
				ArrayList<String> CategoryName_list = new ArrayList<String>();
				CategoryName_list.add("Choose Category");
				try {
					JSONArray arrayCategory = new JSONArray(String_Json);
					for (int i = 0; i < arrayCategory.length(); i++) {
						JSONObject jo = arrayCategory.getJSONObject(i);
						CategoryId_list.add(jo.getString("id"));
						CategoryName_list.add(jo.getString("title"));

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						FindJobActivity.this,
						android.R.layout.simple_spinner_item,
						android.R.id.text1, CategoryName_list);
				spnCategory.setAdapter(adapter);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config1.URL_GET_CATEGORIES);
				return s;
			}

		}
		getallCategory gn = new getallCategory();
		gn.execute();
	}

	private void getJSONCity() {
		// TODO Auto-generated method stub
		class getallCities extends AsyncTask<Void, Void, String> {

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				String_Json = s;
				showAllCities();

			}

			private void showAllCities() {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, String>> cityLists = new ArrayList<HashMap<String, String>>();
				ArrayList<String> CityName_lists = new ArrayList<String>();
				ArrayList<String> CityID_lists = new ArrayList<String>();
				CityName_lists.add("Choose Location");
				try {
					JSONArray arrayCity = new JSONArray(String_Json);

					for (int i = 0; i < arrayCity.length(); i++) {
						JSONObject jo = arrayCity.getJSONObject(i);

						// HashMap<String, String> list = new HashMap<String,
						// String>();
						// list.put("id", jo.getString("id"));
						// list.put("title", jo.getString("title"));
						// list.put("created_at", jo.getString("created_at"));
						// list.put("updated_at", jo.getString("updated_at"));
						//
						// cityLists.add(list);

						CityID_lists.add(jo.getString("id"));

						CityName_lists.add(jo.getString("title"));

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						FindJobActivity.this,
						android.R.layout.simple_spinner_item,
						android.R.id.text1, CityName_lists);
				spnLocation.setAdapter(adapter);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stubim
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config1.URL_GET_CITIES);
				return s;
			}

		}
		getallCities gn = new getallCities();
		gn.execute();
	}

}
