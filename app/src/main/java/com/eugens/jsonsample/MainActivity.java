package com.eugens.jsonsample;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListView listView;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arrayList;
    ListViewAdapter adapter;
    static String RANK = "rank";
    static String COUNTRY = "country";
    static String POPULATION = "population";
    static String FLAG = "flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadJSON().execute();
    }

    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //Creating array
            arrayList = new ArrayList<HashMap<String, String>>();
            jsonObject = JSONfunctions
                    .getJSONfromURL("http://www.androidbegin.com/tutorial/jsonparsetutorial.txt");

            try {
                jsonArray = jsonObject.getJSONArray("worldpopulation");
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonObject = jsonArray.getJSONObject(i);
                    // Retrive JSON Objects
                    map.put("rank", jsonObject.getString("rank"));
                    map.put("country", jsonObject.getString("country"));
                    map.put("population", jsonObject.getString("population"));
                    map.put("flag", jsonObject.getString("flag"));
                    // Set the JSON Objects into the array
                    arrayList.add(map);
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Android JSON Parse Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Locate the listview in listview_main.xml
            listView = (ListView) findViewById(R.id.listView);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this, arrayList);
            // Set the adapter to the ListView
            listView.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
