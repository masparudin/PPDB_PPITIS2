package com.mascode.ppdbppitis2.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mascode.ppdbppitis2.Interface.ItemClickListener;
import com.mascode.ppdbppitis2.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener
{
    public TextView TextView_judul;
    public TextView TextView_pesan;
    public TextView TextView_waktu;
    private ItemClickListener itemClickListener;


    public NotificationViewHolder(View itemView) {
        super(itemView);

        TextView_judul = (TextView) itemView.findViewById(R.id.text_judul);
        TextView_pesan = (TextView) itemView.findViewById(R.id.text_pesan);
        TextView_waktu = (TextView) itemView.findViewById(R.id.text_waktu);

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
