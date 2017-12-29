package com.example.jobseeker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.util.HashMap;

public class ViewJobDetail extends Activity implements View.OnClickListener {

	TextView txtTitle,txtcompanyname,txtcreated,txtcity,txttype,txtsalary,txtdes,txtreq,txthowto;

	private String id;
	String companyName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobdetail);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtcompanyname=(TextView)findViewById(R.id.txtCompanyName);
		txtcreated=(TextView)findViewById(R.id.txtCreatedAt);
		txtcity=(TextView)findViewById(R.id.txtCity);
		txttype=(TextView)findViewById(R.id.txtType);
		txtsalary=(TextView)findViewById(R.id.txtSalary);
		txtdes=(TextView)findViewById(R.id.txtdes);
		txtreq=(TextView)findViewById(R.id.txtRequirement);
		txthowto=(TextView)findViewById(R.id.txtHowto);
		Intent intent = getIntent();

		id = intent.getStringExtra(Config.JOB_ID);
	 companyName=intent.getStringExtra(Config.TAG_DETAIL_COMPANY);
				  
		getJob();
	}

	private void getJob() {
		class GetJob extends AsyncTask<Void, Void, String> {
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				loading = ProgressDialog.show(ViewJobDetail.this,
						"Fetching...", "Wait...", false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);
				loading.dismiss();
				showJob(s);
			}

			@Override
			protected String doInBackground(Void... params) {
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_GET_VIEWJOB, id);
				// System.out.println(s);
				return s;
			}
		}
		GetJob job = new GetJob();
		job.execute();
	}

	private void showJob(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			//JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			//JSONObject c = jsonObject.getJSONObject(0);
			// JSONObject jsonObject = new JSONObject(json);
			// JSONArray result = new JSONArray(json);
			// JSONObject c = result.getJSONObject(0);
			String title = jsonObject.getString(Config.TAG_DETAIL_TITLE );
			//String companyname = jsonObject.getString(Config.TAG_DETAIL_COMPANY );
			String createdate = jsonObject.getString(Config.TAG_DETAIL_CREATED );
			String city = jsonObject.getString(Config.TAG_DETAIL_CITY );
			String type = jsonObject.getString(Config.TAG_DETAIL_TYPE );
			String salary = jsonObject.getString(Config.TAG_DETAIL_SALARY);
			String description = jsonObject.getString(Config.TAG_DETAIL_DESCRITION );
			String requirement = jsonObject.getString(Config.TAG_DETAIL_REQUIREMENT );
			String howto = jsonObject.getString(Config.TAG_DETAIL_HOWTO );
			
			txtTitle.setText(title);
			txtcompanyname.setText(companyName);
			txtcreated.setText(createdate);
			txtcity.setText(city);
			txttype.setText(type);
			txtsalary.setText(salary);
			txtdes.setText(description);
			txtreq.setText(requirement);
			txthowto.setText(howto);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
