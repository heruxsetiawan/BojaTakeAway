package com.freedom.boja.keranjang;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.freedom.boja.BuildConfig;
import com.freedom.boja.MainActivity;
import com.freedom.boja.R;
import com.freedom.boja.aplication.AplicationsMrk;
import com.freedom.boja.helper.DataHelper;
import com.freedom.boja.menu.adapter_menu;
import com.freedom.boja.menu.data_menu;
import com.freedom.boja.menu.menu;

import com.freedom.boja.w;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class keranjang extends AppCompatActivity {
    private DataHelper myDb;
    private ArrayList<Data_keranjang> dataList = new ArrayList<Data_keranjang>();
    private RecyclerView recyclerView;
    private adapter_keranjang mAdapter;
    private TextView tvtotal, tvitem_qty;
    private RelativeLayout order;
    String totalrp;
    private GoogleMap mMap;
    private TextView peta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_keranjang);
        //init();
        myDb = new DataHelper(keranjang.this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_submenu_keranjang);
        order = (RelativeLayout) findViewById(R.id.rl_total);
        tvtotal = (TextView) findViewById(R.id.textharga_total);
        tvitem_qty = (TextView) findViewById(R.id.item_qty);
        peta = (TextView) findViewById(R.id.tittle_alamat_ubah);
      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        mAdapter = new adapter_keranjang(dataList, keranjang.this, keranjang.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        peta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(keranjang.this, Peta.class);
                startActivity(intent);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_json_format();

            }
        });
        Getfromdata();
        getTotal();

    }

    private String post_sold_out = "";
    private String post_isi = "";
    private String isi_json_header = "";
    private String post_depan = "";
    private String json_format_detail = "";
    String status = "", localTime = "", idoutlet="outlet";
    String Id_user = "user 123";

    void post_json_format() {
        //  ((AplicationsMrk) getActivity().getApplicationContext()).setSold_out("0");
        String note, qty, code_produk, fcombo_attribute, id_paket, detail;
        Cursor res = myDb.getjumlah2();
        Integer jml = Integer.valueOf(getjumlahdata());
        Log.e("tes jml", jml + "");
        String split = "";
        String split2 = "";
        post_sold_out = "";
        Integer i = 1;
        String nama;
        String line1;
        while (res.moveToNext()) {
            code_produk = res.getString(1);
            nama = res.getString(2);
            qty = res.getString(3);
            note = res.getString(4);
            line1 = nama.replace("\"", "");
            /*fcombo_attribute*/
            fcombo_attribute = "";
            id_paket = "";

            detail = "null";

            if (note == null) {
                note = "null";
            }

            if (i == 1) {
                split2 = "" + post_isi;

            } else {

                split2 = "," + post_isi;

            }
        /*    if (fcombo_attribute.equalsIgnoreCase("1")) {
                detail = "[" + Get_Data_paket(code_produk, id_paket) + "]";

            }*/

            if (jml > 1) {
                isi_json_header = "{\"fpkey\":\"" + code_produk + "\",\"id_paket\":\"" + id_paket + "\",\"fcombo_attribute\":\"" + fcombo_attribute + "\",\"nama\":\"" + line1 + "\",\"qty\":\"" + qty + "\",\"note\":\"" + note + "\",\"detail\":" + detail + "}";
                post_isi = isi_json_header + "" + split2;


            } else {
                isi_json_header = "{\"fpkey\":\"" + code_produk + "\",\"id_paket\":\"" + id_paket + "\",\"fcombo_attribute\":\"" + fcombo_attribute + "\",\"nama\":\"" + line1 + "\",\"qty\":\"" + qty + "\",\"note\":\"" + note + "\",\"detail\":" + detail + "}";
                post_isi = isi_json_header;

            }

            i++;


        }
        String status_soldout;
        status_soldout = "0";

        //    Log.e("sold out",post_sold_out);
        post_depan = "{\"meja\":\"" + "android" + "\",\"totalrp\":\"" + totalrp + "\",\"localTime\":\"" + localTime + "\",\"iduser\":\"" + Id_user + "\",\"cabang\":\"" + idoutlet + "\",\"pesanan\":[" + post_isi + "]}";
        Log.e("post_isi header", post_depan);

        if (status_soldout.equalsIgnoreCase("1")) {
            //  showDialog_soldout();
        } else {
            get_invoice(post_depan);
        }
        res.close();

    }

    private static String posturl = BuildConfig.post;
    void get_invoice(String post) {
        Log.e("get_menu", "jalan");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("pass", post);
        params.put("firebase_token", "11");
        client.post(posturl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                myDb.update_session_order("1", "0");
                myDb.update_session_order("2", "kosong");
                myDb.update_session_order("3", "0");
                finish();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error+"");

            }
        });


    }

    public void getTotal() {


        if (myDb.hitungjumlahdata() == null) {
            tvtotal.setText("0");
        } else if (myDb.hitungjumlahdata().equalsIgnoreCase("0")) {
            tvtotal.setText("0");

        } else {
            try {

                String value = myDb.getTotal();
                value = value.replace(".", "").replace(",", "");
                double amount = Double.parseDouble(value);
                DecimalFormat formatter = null;

                if (value != null && !value.equals("")) {
                    formatter = new DecimalFormat("#,###");
                }
                if (!value.equals(""))
                    totalrp = (formatter.format(amount));
                tvtotal.setText(formatter.format(amount));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try {
            String jumlah = null;
            Cursor res2 = myDb.jumlah_qty();
            while (res2.moveToNext()) {
                jumlah = res2.getString(0);

            }
            // Log.e("jumlah data", jumlah);
            tvitem_qty.setText(jumlah + " Item Order");
            res2.close();

        } catch (Exception e) {
            tvitem_qty.setText("0 Item Order");

        }

    }

    void Getfromdata() {
        dataList.clear();
        String note, harga, nama_produk, qty, code_produk, total, fkitchen_id, fcombo_attribute, id_paket;
        Cursor res = myDb.getjumlah2();
        Integer jml = Integer.valueOf(getjumlahdata());

        String split = "";
        Integer i = 1;
        while (res.moveToNext()) {
            code_produk = res.getString(1);
            nama_produk = res.getString(2);
            qty = res.getString(3);
            note = res.getString(4);
            harga = res.getString(5);
            total = res.getString(6);


            Data_keranjang dk = new Data_keranjang();
            dk.setcode_produk(code_produk);
            dk.setnama_order(nama_produk);
            dk.setjumlah_order(qty);
            dk.setnote(note);
            dk.setharga(harga);
            dk.settotal(total);
            i++;

            try {
                dataList.add(dk);
            } catch (Exception e) {

            }

        }

        Collections.sort(dataList, Data_keranjang.StuNameComparator);
        mAdapter.notifyDataSetChanged();
        // mAdapter = new Adapter_keranjang(getActivity(), dataList, getContext(), KeranjangFragment.this);
       /* rvView.setAdapter(mAdapter);
        rvView.setHasFixedSize(true);
        rvView.setItemViewCacheSize(dataList.size());
*/

        res.close();

    }

    public String getjumlahdata() {
        String jumlah = null;
        jumlah = myDb.hitungjumlahdata();
      /*  Cursor res = myDb.hitungjumlahdata();
        while (res.moveToNext()) {
            jumlah = res.getString(0);

        }
        res.close();*/

        return jumlah;
    }

    protected String address;

/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng markerPoint = ((AplicationsMrk) getApplicationContext()).getCoordinat();
        if (markerPoint != null) {
            mMap.addMarker(new MarkerOptions().position(markerPoint)).setTitle("new marker");
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPoint, zoomLevel));
        }


    }*/

    @Override
    protected void onResume() {
        super.onResume();
      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
*/
       /* String a=  ((AplicationsMrk) getApplicationContext()).getSet_marker();
        if (a.equalsIgnoreCase("1")){
            LatLng markerPoint = ((AplicationsMrk) getApplicationContext()).getCoordinat();
            if (markerPoint != null) {
                mMap.addMarker(new MarkerOptions().position(markerPoint)).setTitle("new marker");
                float zoomLevel = 16.0f; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPoint, zoomLevel));
            }
        }*/
    }
}
