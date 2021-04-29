package com.example.swipabledialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StringRequest request = new StringRequest(Request.Method.POST, "https://backend-test-zypher.herokuapp.com/testData", response -> {
            Log.d("Response from site is ",response);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String title = jsonObject.getString("title");
                String imageURL = jsonObject.getString("imageURL");
                String success_url = jsonObject.getString("success_url");

                MyDialog.getInstance().showDialogBox(MainActivity.this,title,imageURL,success_url);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }
}