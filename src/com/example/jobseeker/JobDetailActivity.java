package com.example.jobseeker;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class JobDetailActivity extends Activity {

	TextView txttitle, txtcompany, txtcreated, txtcity, txttype, txtsalary,
			txtdescription, txtreq, txthowto;
	String job_id;
	String companyname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobdetail);
		txttitle = (TextView) findViewById(R.id.txtTitle);
		txtcompany = (TextView) findViewById(R.id.txtCompanyName);
		txtcreated = (TextView) findViewById(R.id.txtCreatedAt);
		txtcity = (TextView) findViewById(R.id.txtCity);
		txttype = (TextView) findViewById(R.id.txtType);
		txtsalary = (TextView) findViewById(R.id.txtSalary);
		txtdescription = (TextView) findViewById(R.id.txtdes);
		txtreq = (TextView) findViewById(R.id.txtRequirement);
		txthowto = (TextView) findViewById(R.id.txtHowto);

		job_id = getIntent().getStringExtra("jobid");
		companyname = getIntent().getStringExtra("jobcompany");

		getJob();
	}

	private void getJob() {
		// TODO Auto-generated method stub
		class GETJobDetail extends AsyncTask<Void, Void, String> {
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(JobDetailActivity.this,
						"Fetching data", "Wait...", false, false);
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				loading.dismiss();
				showDetailJob(result);
			}

			private void showDetailJob(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject job = new JSONObject(result);

					String title = job.getString("title");
					String createdate = job.getString("created_at");
					String city = job.getString("city");
					String type = job.getString("job_type");
					String salary = job.getString("salary_range");
					String description = job.getString("description");
					String requirement = job.getString("requirement");
					String howto = job.getString("how_to");

					txttitle.setText(title);
					txtcompany.setText(companyname);
					txtcreated.setText(createdate);
					txtcity.setText(city);
					txttype.setText(type);
					txtsalary.setText(salary);
					txtdescription.setText(description);
					txtreq.setText(requirement);
					txthowto.setText(howto);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config1.GET_JOB,job_id);
				return s;
			}

		}
		GETJobDetail gn = new GETJobDetail();
		gn.execute();

	}
}
