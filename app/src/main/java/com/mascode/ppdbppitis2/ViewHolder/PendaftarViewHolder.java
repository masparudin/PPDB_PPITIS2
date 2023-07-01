package com.mascode.ppdbppitis2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mascode.ppdbppitis2.Interface.ItemClickListener;
import com.mascode.ppdbppitis2.R;

public class PendaftarViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener
{
    public TextView TextView_nama;
    public TextView TextView_unit;
    public TextView TextView_status;
    public ImageView imageView_delete;
    public TextView TextView_id;
    private ItemClickListener itemClickListener;


    public PendaftarViewHolder(View itemView) {
        super(itemView);

        TextView_id = (TextView) itemView.findViewById(R.id.text_id);
        TextView_nama = (TextView) itemView.findViewById(R.id.text_nama);
        TextView_unit = (TextView) itemView.findViewById(R.id.text_unit);
        TextView_status = (TextView) itemView.findViewById(R.id.text_status);
        imageView_delete = (ImageView) itemView.findViewById(R.id.img_delete);

        itemView.setOnClickListener(this);
        imageView_delete.setOnClickListener(this);

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
