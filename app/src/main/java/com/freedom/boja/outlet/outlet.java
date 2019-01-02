package com.freedom.boja.outlet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.freedom.boja.BuildConfig;
import com.freedom.boja.R;
import com.freedom.boja.helper.DataHelper;
import com.freedom.boja.menu.menu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class outlet extends AppCompatActivity {
    private List<data_outlet> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private adapter_outlet mAdapter;
    private static String api_1_1 = BuildConfig.api_1_1;
    private DataHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        myDb = new DataHelper(outlet.this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            try {
              //  myDb.insert_session_login();
               // myDb.insert_awal_Session_Login_user();
                myDb.insert_session_order("1", "session_status", "0");
                myDb.insert_session_order("2", "session_outlet", "kosong");
                myDb.insert_session_order("3", "id_session_outlet", "0");
                Log.e("pertama", "jalan");

            } catch (Exception e) {
                Log.e("error insert", e + "");
            }


            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new adapter_outlet(movieList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        get_outlet();

    }


    void get_outlet() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("operasi", "outlet");
        client.post(api_1_1, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String menuJson = new String(responseBody);
               Log.e("nama",menuJson);
                try {

                    JSONObject jsonObj = new JSONObject(menuJson);
                    JSONArray contacts = jsonObj.getJSONArray("outlet");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String user_id = c.getString("user_id");
                        String nama = c.getString("nama");
                        String alamat = c.getString("alamat");
                        String gambar = c.getString("gambar");
                        data_outlet outlet = new data_outlet(user_id,nama, alamat, "17.00-21.00","gambar");
                        movieList.add(outlet);
                    }
                    mAdapter.notifyDataSetChanged();

                } catch (final JSONException e) {
                    Log.e("TAG", "get_outlet : " + e.getMessage());


                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String menuJson = new String(String.valueOf(error));
                Log.e("outlet",menuJson);
                Log.e("outlet",responseBody+"");
                Log.e("outlet",statusCode+"");
            }
        });


    }

}