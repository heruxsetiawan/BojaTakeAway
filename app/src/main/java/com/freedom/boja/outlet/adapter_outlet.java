package com.freedom.boja.outlet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freedom.boja.MainActivity;
import com.freedom.boja.aplication.AplicationsMrk;
import com.freedom.boja.menu.menu;
import com.freedom.boja.R;

import java.util.List;

public class adapter_outlet extends RecyclerView.Adapter<adapter_outlet.MyViewHolder> {

    private List<data_outlet> outlet_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, jam, alamat;
        public ImageView gambar;
        public RelativeLayout outlet;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            alamat = (TextView) view.findViewById(R.id.alamat);
            jam = (TextView) view.findViewById(R.id.jam);
            gambar = (ImageView) view.findViewById(R.id.gambar);
            outlet = (RelativeLayout) view.findViewById(R.id.rv_outlet);
        }
    }


    public adapter_outlet(List<data_outlet> moviesList) {
        this.outlet_list = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.outlet_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final data_outlet outlet = outlet_list.get(position);
        holder.title.setText(outlet.getTitle());
        holder.alamat.setText(outlet.getAlamat());
        holder.jam.setText(outlet.getJam());
        holder.outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //((AplicationsMrk) context.getApplicationContext()).setAlamat(alamat);
                ((AplicationsMrk) context.getApplicationContext()).setid(outlet.getTitleid_outlet());
                ((AplicationsMrk) context.getApplicationContext()).setOutlet(outlet.getTitle());
                Intent intent = new Intent(context, menu.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return outlet_list.size();
    }
}