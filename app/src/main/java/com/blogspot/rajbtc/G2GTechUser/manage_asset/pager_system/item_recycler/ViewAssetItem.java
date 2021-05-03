package com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system.item_recycler;

public class ViewAssetItem {
    String assetName, assetUrl;

    public ViewAssetItem(String assetName, String assetUrl) {
        this.assetName = assetName;
        this.assetUrl = assetUrl;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getAssetUrl() {
        return assetUrl;
    }
}
