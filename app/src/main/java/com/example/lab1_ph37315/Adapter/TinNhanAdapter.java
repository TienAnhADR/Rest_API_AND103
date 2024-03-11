package com.example.lab1_ph37315.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1_ph37315.R;
import com.example.lab1_ph37315.TinNhan;

import java.util.ArrayList;

public class TinNhanAdapter extends RecyclerView.Adapter<TinNhanAdapter.ViewHolder>{
    private Context context;
    private ArrayList<TinNhan> list;

    public TinNhanAdapter(Context context, ArrayList<TinNhan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tin_nhan,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TinNhan tn = list.get(position);
        holder.txtUserName.setText("User name: "+tn.getUser());
        holder.txtContent.setText("Content: "+tn.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName,txtContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.txtTinNhan);
            txtUserName = itemView.findViewById(R.id.txtNameUser);
        }
    }
}
