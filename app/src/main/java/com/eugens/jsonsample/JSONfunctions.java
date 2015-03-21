package com.eugens.jsonsample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import android.os.Environment;
import android.util.Log;

public class JSONfunctions {

    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }

    public static JSONObject getJSONfromPath(String url)
    {
        JSONParser parser  = new JSONParser();
        JSONObject jsonObject = null;
        try {
            String PATH = Environment.getExternalStorageDirectory() + "/Download/list.json";
            Object object = parser.parse(new FileReader(PATH));
            jsonObject = (JSONObject) object;
            //Use JSONArray instead of JSONObject if json file contains array of JSONs
            //
            // Do anything with jsonObject
            //
        } catch(Exception e) {
            // TODO: handle exception
        }
        return jsonObject;
    }

}