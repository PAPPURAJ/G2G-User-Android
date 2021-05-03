package com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system.item_recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.R;


public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView assetName;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.singleAssetIv);
        assetName=itemView.findViewById(R.id.singleAssetNameTv);
    }
}
