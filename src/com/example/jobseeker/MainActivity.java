package com.example.jobseeker;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements OnItemClickListener {

	Button btnfiler;
	ListView alljobs;
	private String JSON_STRING;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnfiler = (Button) findViewById(R.id.btnFilter);
		btnfiler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goFindJob = new Intent(MainActivity.this,
						FindJobActivity.class);
				startActivity(goFindJob);
			}

		});
		getJSON();
		alljobs = (ListView) findViewById(R.id.AllJobsList);
		alljobs.setOnItemClickListener(this);

	}

	private void showAllJobs() {
		// JSONObject jsonObject = null;
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			// jsonObject = new JSONObject(JSON_STRING);
			// JSONArray result = jsonObject.getJSONArray(JSON_STRING);
			JSONArray result = new JSONArray(JSON_STRING);
			for (int i = 0; i < result.length(); i++) {
				JSONObject jo = result.getJSONObject(i);
				String job_id = jo.getString(Config.TAG_ID);
				String job_title = jo.getString(Config.TAG_TITLE);
				String status = jo.getString(Config.TAG_STATUS);
				String job_company = jo.getString(Config.TAG_COMPANY);
				String job_city = jo.getString(Config.TAG_CITY);
				String job_type = jo.getString(Config.TAG_TYPE);
				String job_boost_budget = jo.getString(Config.TAG_BOOSTBUDGET);
				String job_created_at = jo.getString(Config.TAG_CREATED);

				HashMap<String, String> joblists = new HashMap<>();
				joblists.put(Config.TAG_ID, job_id);
				joblists.put(Config.TAG_TITLE, job_title);
				joblists.put(Config.TAG_STATUS, status);
				joblists.put(Config.TAG_COMPANY, job_company);
				joblists.put(Config.TAG_CITY, job_city);
				joblists.put(Config.TAG_TYPE, job_type);
				joblists.put(Config.TAG_BOOSTBUDGET, job_boost_budget);
				joblists.put(Config.TAG_CREATED, job_created_at);
				list.add(joblists);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(MainActivity.this, list,
				R.layout.list_item, new String[] { Config.TAG_TITLE,
						Config.TAG_COMPANY, Config.TAG_TYPE, Config.TAG_CITY,
						Config.TAG_CREATED }, new int[] { R.id.title,
						R.id.company, R.id.type, R.id.city, R.id.create });

		alljobs.setAdapter(adapter);
	}

	private void getJSON() {
		class GetJSON extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(MainActivity.this,
						"Fetching Data", "Wait...", false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				showAllJobs();
			}

			@Override
			protected String doInBackground(Void... params) {
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.URL_GET_ALLJOBS);
				System.out.println(s);
				return s;
			}
		}
		GetJSON gj = new GetJSON();
		gj.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent viewDetail = new Intent(MainActivity.this, ViewJobDetail.class);
		HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
		String jobId = map.get(Config.TAG_ID).toString();
		String companyname = map.get(Config.TAG_COMPANY).toString();
		System.out.println(jobId);
		viewDetail.putExtra(Config.JOB_ID, jobId);
		viewDetail.putExtra(Config.TAG_DETAIL_COMPANY, companyname);
		startActivity(viewDetail);
	}
}
