package com.androidlearning.androidarchitecturecomponents.ui.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.androidlearning.androidarchitecturecomponents.R;
import com.androidlearning.androidarchitecturecomponents.entity.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<Shop> data;

    public ShopAdapter(List<Shop> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.tvName.setText(data.get(position).getName());
        holder.tvAddress.setText(data.get(position).getAddress());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(Shop shop);
        void onDeleteClick(Shop shop, int position);

        void onEditClick(Shop shop, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress;
        ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvAddress = itemView.findViewById(R.id.address);
            background = itemView.findViewById(R.id.image);

        }


        public void click(final Shop shops, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClick(shops));

            itemView.findViewById(R.id.imageview_delete).setOnClickListener((v)->listener.onDeleteClick(shops, getAdapterPosition()));
            itemView.findViewById(R.id.imageview_edit).setOnClickListener((v)->listener.onEditClick(shops, getAdapterPosition()));
        }
    }


}
