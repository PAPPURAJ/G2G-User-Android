package com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blogspot.rajbtc.G2GTechUser.manage_asset.AssetItem;

import java.util.ArrayList;

public class AssetFragmentPagerAdapter extends FragmentPagerAdapter {
    private  ArrayList<AssetItem> arrayList;
    private ArrayList<String> tab=new ArrayList<>();
    public AssetFragmentPagerAdapter(@NonNull FragmentManager fm, ArrayList<AssetItem> arrayList) {
        super(fm);  this.arrayList=arrayList;
                    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ArrayList<String> assetName=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++){
            if(tab.get(position).equals(arrayList.get(i).getAssetType()))
                assetName.add(arrayList.get(i).getAssetName());
        }
        return new Pager_Fragment(assetName,tab.get(position));
    }

    @Override
    public int getCount() {
        for(int i=0;i<arrayList.size();i++)
            if(!tab.contains(arrayList.get(i).getAssetType()))
                tab.add(arrayList.get(i).getAssetType());
        return tab.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab.get(position);
    }
}
