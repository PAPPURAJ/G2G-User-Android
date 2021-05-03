package com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.R;
import com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system.item_recycler.ViewAssetAdapter;
import com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system.item_recycler.ViewAssetItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class Pager_Fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> arrayList;
    private String assetType,TAG="===ManageAsset_PagerFragment===";
    ArrayList<ViewAssetItem> recyArray=new ArrayList<>();

    public Pager_Fragment(ArrayList<String> arrayList, String assetType){
        this.arrayList=arrayList;
        this.assetType=assetType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { //Log.e("======","view");
        view= inflater.inflate(R.layout.fragment_first_, container, false);
        recyclerView=view.findViewById(R.id.assetFragment_Recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadAsset();
        return view;
    }


    void loadAsset(){

        recyArray.clear();
        CollectionReference cref= FirebaseFirestore.getInstance().collection("Asset");
        Query q1=cref.whereEqualTo("Type",assetType);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    downloadImage(ds.getString("Name"));
                    Log.e(TAG,"AssetPicName: "+ds.getString("Name"));
                }
            }
        });
    }




    void downloadImage(final String name){

        FirebaseStorage.getInstance().getReference().child("Asset/AssetCon/"+name+"_200x200.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Toast.makeText(getContext(),"Down "+uri.toString(),Toast.LENGTH_SHORT).show();
                recyArray.add(new ViewAssetItem(name,uri.toString()));
                recyclerView.setAdapter(new ViewAssetAdapter(getContext(),assetType,recyArray));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(),"Image downloading failed!",Toast.LENGTH_SHORT).show();
                Log.e(TAG,name);
            }
        });
    }




}