package com.example.darshanparikh.recyclerviewdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.darshanparikh.recyclerviewdemo.adapter.FeedListItemsAdapter;
import com.example.darshanparikh.recyclerviewdemo.model.FeedListItems;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;


/**
 * Created by darshan.parikh on 25-Aug-15.
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<FeedListItems> feedsList = new ArrayList<FeedListItems>();
    private RecyclerView mRecyclerView;
    private FeedListItemsAdapter adapter;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView  = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnNext = (Button) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GyroScopeActivity.class);
                startActivity(intent);
            }
        });

        final String url = "http://javatechig.com/?json=get_recent_posts&count=45";
        new AsyncGetDetailsTask().execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected class AsyncGetDetailsTask extends AsyncTask<String, String, JSONObject> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            HttpEntity httpEntity = null;
            String responseString;
            JSONObject responceJson;
            try {
                URL url = new URL(strings[0]);
                HttpGet get = new HttpGet(url.toString());
                response = httpclient.execute(get);
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    httpEntity = response.getEntity();
                    responseString = EntityUtils.toString(httpEntity);
                    responceJson = new JSONObject(responseString);
                    return responceJson;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject responseJson) {
            super.onPostExecute(responseJson);
            if(pDialog.isShowing()) pDialog.dismiss();

            try {
                int length = responseJson.getInt("count");
                JSONArray jsonArray = responseJson.getJSONArray("posts");
                for(int i = 0; i < length; i++) {
                    JSONObject singleObject = jsonArray.getJSONObject(i);
                    String title = singleObject.getString("title");
                    feedsList.add(new FeedListItems(title));
                }
                adapter = new FeedListItemsAdapter(MainActivity.this, feedsList);
                mRecyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
