package com.mascode.ppdbppitis2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mascode.ppdbppitis2.Interface.ItemClickListener;
import com.mascode.ppdbppitis2.R;

public class PendaftarUjianViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener
{
    public TextView TextView_nama;
    public TextView TextView_unit;
    public TextView TextView_status;
    public TextView TextView_id;
    private ItemClickListener itemClickListener;


    public PendaftarUjianViewHolder(View itemView) {
        super(itemView);

        TextView_id = (TextView) itemView.findViewById(R.id.text_id);
        TextView_nama = (TextView) itemView.findViewById(R.id.text_nama);
        TextView_unit = (TextView) itemView.findViewById(R.id.text_unit);
        TextView_status = (TextView) itemView.findViewById(R.id.text_status);

        itemView.setOnClickListener(this);

    }
    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view, getAdapterPosition(),false);
    }

}
