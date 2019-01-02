package com.freedom.boja.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freedom.boja.BuildConfig;
import com.freedom.boja.R;
import com.freedom.boja.aplication.AplicationsMrk;
import com.freedom.boja.helper.DataHelper;
import com.freedom.boja.keranjang.keranjang;
import com.freedom.boja.outlet.adapter_outlet;
import com.freedom.boja.outlet.data_outlet;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class menu extends AppCompatActivity {
    private List<data_menu> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private adapter_menu mAdapter;
    private static String api_1_1 = BuildConfig.api_1_1;
    private String idoutlet;
    private DataHelper myDb;
    String sqliteoutlet;
    ViewGroup container2;
    LayoutInflater layoutInflater2;
    private RelativeLayout get_total;
    View addView2;
    public TextView Rp, total;
    TextView tv_jumlah,tv_outlet;
    String session_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet);
        myDb = new DataHelper(menu.this);
        sqliteoutlet = ((AplicationsMrk) getApplicationContext()).getOutlet();
        idoutlet = ((AplicationsMrk) getApplicationContext()).id_outlet();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        container2 = (RelativeLayout) findViewById(R.id.container2);

//        Log.e("session",session_order);
        getinflate();
        getTotal();
        mAdapter = new adapter_menu(movieList, menu.this, menu.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        get_menu();

    }

    public void getinflate() {
        layoutInflater2 = (LayoutInflater) menu.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView2 = layoutInflater2.inflate(R.layout.inflate_total, null);
        Rp = (TextView) addView2.findViewById(R.id.rp);
        tv_outlet = (TextView) addView2.findViewById(R.id.tv_outlet);
        //total = (TextView) addView2.findViewById(R.id.total);
        tv_jumlah = (TextView) addView2.findViewById(R.id.tv_jumlah);
        container2.addView(addView2);
        get_total = (RelativeLayout) addView2.findViewById(R.id.rl_total);



        get_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, keranjang.class);
                  startActivity(intent);
                // getActivity().finish();
            }
        });

    }

    public void getTotal() {
        session_order = myDb.get_session_order("2");
        tv_outlet.setText(session_order);
        try {
            String jumlah = null;
            Cursor res = myDb.jumlah_qty();
            while (res.moveToNext()) {
                jumlah = res.getString(0);

            }
            // Log.e("jumlah data", jumlah);
            tv_jumlah.setText(jumlah);
            res.close();

        } catch (Exception e) {
            tv_jumlah.setText("0");

        }


        // if (myDb.getTotal() == null) {
        if (myDb.hitungjumlahdata() == null) {
            Rp.setText("0");
            hideinflate();
            //  } else if (myDb.getTotal().equalsIgnoreCase("0")) {
        } else if (myDb.hitungjumlahdata().equalsIgnoreCase("0")) {
            Rp.setText("0");
            hideinflate();
        } else {
            try {
                showinflate();
                String value = myDb.getTotal();
                value = value.replace(".", "").replace(",", "");
                double amount = Double.parseDouble(value);
                DecimalFormat formatter = null;

                if (value != null && !value.equals("")) {
                    formatter = new DecimalFormat("#,###");
                }
                if (!value.equals(""))
                    Rp.setText(formatter.format(amount));

                return;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    public void hideinflate() {
        container2.setVisibility(View.GONE);
    }

    public void showinflate() {
        container2.setVisibility(View.VISIBLE);
    }

    void get_menu() {
        Log.e("get_menu", "jalan");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("operasi", "menu");
        params.put("id_outlet", idoutlet);
        client.post(api_1_1, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String menuJson = new String(responseBody);
                Log.e("nama", menuJson);
                try {

                    JSONObject jsonObj = new JSONObject(menuJson);
                    JSONArray contacts = jsonObj.getJSONArray("menu");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String code_produk = c.getString("code_produk");
                        String nama_menu = c.getString("nama_menu");
                        String spesifikasi = c.getString("spesifikasi");
                        String harga = c.getString("harga");
                        String gambar = c.getString("gambar");
                        String qty = myDb.get_qty_menu_paket(code_produk);
                        String note = myDb.getnote(code_produk);
                        // Log.e("nama",nama);
                        data_menu movie = new data_menu(code_produk, nama_menu, spesifikasi, harga, gambar, qty, note);
                        movieList.add(movie);
                    }
                    mAdapter.notifyDataSetChanged();

                } catch (final JSONException e) {
                    Log.e("TAG", "get_transaksi : " + e.getMessage());


                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String menuJson = new String(String.valueOf(error));
                Toast.makeText(menu.this, "Error " + menuJson + "", Toast.LENGTH_LONG).show();

            }
        });


    }

    public void dialog_hapus(final String outlet) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(menu.this);
        builder1.setTitle("Orderan di " + outlet + " akan di ganti ?");
        builder1.setMessage("Orderan hanya untuk 1 outlet");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDb.deleteAll();
                        String idoutlet = ((AplicationsMrk) getApplicationContext()).getid();
                        myDb.update_session_order("2", sqliteoutlet);
                        myDb.update_session_order("3", idoutlet);
                        getTotal();
                        dialog.cancel();


                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


}