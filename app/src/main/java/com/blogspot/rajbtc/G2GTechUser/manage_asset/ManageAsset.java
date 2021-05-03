package com.blogspot.rajbtc.G2GTechUser.manage_asset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blogspot.rajbtc.G2GTechUser.R;
import com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system.AssetFragmentPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ManageAsset extends AppCompatActivity {
    ArrayList<AssetItem> arrayList=new ArrayList<>();
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_asset);
        tabLayout=findViewById(R.id.manageAsset_Tl);
        viewPager=findViewById(R.id.manageAsset_Vp);
        loadAssetDetails();


    }


    void loadAssetDetails(){


        FirebaseFirestore.getInstance().collection("Asset")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                arrayList.add(new AssetItem(document.getString("Name"),document.getString("Type")));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Loading failed!",Toast.LENGTH_SHORT).show();
                        }
                        if(arrayList.size()!=0){
                            viewPager.setAdapter(new AssetFragmentPagerAdapter(getSupportFragmentManager(),arrayList));
                            tabLayout.setupWithViewPager(viewPager);
                        }

                    }
                });

    }
}