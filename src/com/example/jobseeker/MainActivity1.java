package com.example.jobseeker;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.array;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity1 extends Activity implements OnItemClickListener {

	ListView joblist;
	String JSON_STRING;
	Button btnFilterJob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		joblist = (ListView) findViewById(R.id.AllJobsList);
		joblist.setOnItemClickListener(this);
		btnFilterJob = (Button) findViewById(R.id.btnFilter);
		btnFilterJob.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent gofindjob=new Intent(MainActivity1.this,FindJobActivity.class);
				startActivity(gofindjob);
			}
			
		});
		getJSON();
	}

	private void getJSON() {

		class GetJSON extends AsyncTask<Void, Void, String> {
			ProgressDialog processloading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				processloading = ProgressDialog.show(MainActivity1.this,
						"Fetching data", "wait...", false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				processloading.dismiss();
				JSON_STRING = s;
				showAllJobs();

			}

			private void showAllJobs() {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

				try {
					JSONArray jobarray = new JSONArray(JSON_STRING);
					for (int i = 0; i < jobarray.length(); i++) {
						JSONObject jo = jobarray.getJSONObject(i);
						HashMap<String, String> joblists = new HashMap<String, String>();

						joblists.put("job_id", jo.getString("job_id"));
						joblists.put("job_title", jo.getString("job_title"));
						joblists.put("status", jo.getString("status"));
						joblists.put("job_company", jo.getString("job_company"));
						joblists.put("job_city", jo.getString("job_city"));
						joblists.put("job_type", jo.getString("job_type"));
						joblists.put("job_boost_budget",
								jo.getString("job_boost_budget"));
						joblists.put("job_created_at",
								jo.getString("job_created_at"));
						list.add(joblists);
					}

				} catch (JSONException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				ListAdapter joblistadapter = new SimpleAdapter(
						MainActivity1.this, list, R.layout.list_item,
						new String[] { "job_title", "job_company", "job_type",
								"job_city", "job_created_at" }, new int[] {
								R.id.title, R.id.company, R.id.type, R.id.city,
								R.id.create });
				joblist.setAdapter(joblistadapter);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config1.GET_ALLJOBS);
				return s;
			}

		}
		GetJSON gn = new GetJSON();
		gn.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent goDetailJob = new Intent(MainActivity1.this,
				JobDetailActivity.class);
		HashMap<String, String> map = (HashMap<String, String>) parent
				.getItemAtPosition(position);
		String job_id = map.get("job_id").toString();
		String companyname = map.get("job_company").toString();
		goDetailJob.putExtra("jobid", job_id);
		goDetailJob.putExtra("jobcompany", companyname);
		startActivity(goDetailJob);
	}

}
