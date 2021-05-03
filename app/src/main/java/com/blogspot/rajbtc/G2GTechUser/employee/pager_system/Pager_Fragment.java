package com.blogspot.rajbtc.G2GTechUser.employee.pager_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.R;
import com.blogspot.rajbtc.G2GTechUser.employee.EmployeeItem;
import com.blogspot.rajbtc.G2GTechUser.employee.pager_system.item_recycler.ViewAssetAdapter;
import com.blogspot.rajbtc.G2GTechUser.employee.pager_system.item_recycler.ViewAssetItem;

import java.util.ArrayList;

public class Pager_Fragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<EmployeeItem> arrayList;
    private String site;
    ArrayList<ViewAssetItem> recyArray=new ArrayList<>();

    public Pager_Fragment(String site,ArrayList<EmployeeItem> arrayList){
        this.arrayList=arrayList;
        this.site=site;
    //    Log.e("======","Constr");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {// Log.e("======","view");
        view= inflater.inflate(R.layout.fragment_first_, container, false);
        recyclerView=view.findViewById(R.id.assetFragment_Recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadAsset();
        return view;
    }


    void loadAsset(){
        ArrayList<EmployeeItem> recyArray=new ArrayList<>();
        recyArray.clear();
        if(!site.equals("All")){
            for(int i=0;i<arrayList.size();i++)
                if(arrayList.get(i).getSite().equals(site))
                    recyArray.add(arrayList.get(i));
        }
        else
            recyArray=arrayList;
        recyclerView.setAdapter(new ViewAssetAdapter(getContext(),recyArray));

    }






}