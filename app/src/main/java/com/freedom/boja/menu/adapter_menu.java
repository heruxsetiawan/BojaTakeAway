package com.freedom.boja.menu;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freedom.boja.R;
import com.freedom.boja.aplication.AplicationsMrk;
import com.freedom.boja.helper.DataHelper;
import com.freedom.boja.outlet.data_outlet;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class adapter_menu extends RecyclerView.Adapter<adapter_menu.MyViewHolder> {

    private List<data_menu> menu_list;
    private DataHelper myDb;
    private Activity mContext;
    String sqliteoutlet;
    private Timer timer = new Timer();
    private final long DELAY = 900;
    menu menu1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, detail, tvharga, txtCount;
        public ImageView ker;
        public EditText note;
        public RelativeLayout plus, minus;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            detail = (TextView) view.findViewById(R.id.detail);
            tvharga = (TextView) view.findViewById(R.id.harga);
            txtCount = (TextView) view.findViewById(R.id.tv_qty_keranjang);
            plus = (RelativeLayout) view.findViewById(R.id.btn_plus_keranjang);
            minus = (RelativeLayout) view.findViewById(R.id.btn_minus_keranjang);
            note = (EditText) view.findViewById(R.id.add_note);
        }
    }


    public adapter_menu(List<data_menu> moviesList, Activity context, menu menu2) {
        this.menu_list = moviesList;
        this.mContext = context;
        this.menu1 = menu2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        myDb = new DataHelper(parent.getContext());

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final data_menu menu = menu_list.get(position);
        final String code_produk = menu.getCode_produk();
        final String harga = menu.getharga();
        final String getnote = menu.getNote();
        final String nama_order = menu.getTitle();
        final String spek = menu.getspek();
        final String qty = menu.getQty();
        if (spek.equalsIgnoreCase("")) {
            holder.detail.setVisibility(View.GONE);
        }
        if (!qty.equalsIgnoreCase("0")) {
            holder.note.setVisibility(View.VISIBLE);
        }
        sqliteoutlet = ((AplicationsMrk) mContext.getApplicationContext()).getOutlet();
        holder.title.setText(nama_order);
        holder.detail.setText(menu.getspek());
        holder.txtCount.setText(menu.getQty());
        holder.note.setText(getnote);


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String session = myDb.get_session_order("2");
                String session_status = myDb.get_session_order("1");

                double tempHarga = Double.parseDouble(harga);
                final String hargamenu = String.valueOf(Math.round(tempHarga));
                int hasil = 1 + Integer.parseInt(holder.txtCount.getText().toString());
                int total = hasil * Integer.parseInt(hargamenu);
                final String getnote = String.valueOf(holder.note.getText());
                final Integer jumlah = Integer.valueOf(holder.txtCount.getText().toString());
              /*  Log.e("session_status",session_status);
                Log.e("session",session);
                Log.e("sql",sqliteoutlet);*/
                if (session_status.equalsIgnoreCase("0")) {
                    if (jumlah == 0) {
                        holder.txtCount.setText(hasil + "");
                        boolean isInserted = myDb.insertData(code_produk, nama_order, hasil + "", getnote, hargamenu, total + "");


                    } else {
                        holder.txtCount.setText(hasil + "");
                        boolean updateData = myDb.updateData(code_produk, nama_order, hasil + "", hargamenu, total + "");


                    }
                    String idoutlet = ((AplicationsMrk) mContext.getApplicationContext()).getid();
                    myDb.update_session_order("1", "1");
                    myDb.update_session_order("2", sqliteoutlet);
                    myDb.update_session_order("3", idoutlet);
                    holder.note.setVisibility(View.VISIBLE);
                } else {
                    if (!session.equalsIgnoreCase(sqliteoutlet)) {
                        ((menu) mContext).dialog_hapus(session);


                    } else {
                        if (jumlah == 0) {
                            holder.txtCount.setText(hasil + "");
                            boolean isInserted = myDb.insertData(code_produk, nama_order, hasil + "", getnote, hargamenu, total + "");

                        } else {
                            holder.txtCount.setText(hasil + "");
                            boolean updateData = myDb.updateData(code_produk, nama_order, hasil + "", hargamenu, total + "");

                        }
                        holder.note.setVisibility(View.VISIBLE);
                    }

                }

                menu1.getTotal();
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                double tempHarga = Double.parseDouble(harga);
                final String hargamenu = String.valueOf(Math.round(tempHarga));
                final Integer aa2 = Integer.valueOf(holder.txtCount.getText().toString());
                int hasil = Integer.parseInt(holder.txtCount.getText().toString()) - 1;
                int total = hasil * Integer.parseInt(hargamenu);
                String jumlah = null;
                if (aa2 > 0) {
                    holder.txtCount.setText(hasil + "");
                    boolean updateData = myDb.updateData(code_produk, nama_order, hasil + "", hargamenu, total + "");
                    final Integer aa3 = Integer.valueOf(holder.txtCount.getText().toString());
                    if (aa3 == 0) {
                        Integer del_id = Integer.valueOf(code_produk);
                        myDb.deleteData(del_id);
                        holder.note.setVisibility(View.GONE);

                    }
                }


                menu1.getTotal();
            }

        });
        holder.note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                final String code_produk = menu.getCode_produk();
                final String test_note = s + "";

                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)


                                Boolean note = myDb.updateNote(code_produk, test_note);
                                if (note) {
                                    mContext.runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(mContext, "Berhasil Simpan Note", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    mContext.runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(mContext, "Gagal Simpan Note", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }


                            }


                        },
                        DELAY
                );


            }
        });


        try

        {
            double tempHarga = Double.parseDouble(menu.getharga());
            String value = String.valueOf(Math.round(tempHarga));
            value = value.replace(".", "").replace(",", "");
            double amount = Double.parseDouble(value);
            DecimalFormat formatter = null;

            if (value != null && !value.equals("")) {
                formatter = new DecimalFormat("#,###");
            }
            if (!value.equals(""))
                holder.tvharga.setText(formatter.format(amount));
            return;
        } catch (
                Exception ex)

        {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return menu_list.size();
    }
}