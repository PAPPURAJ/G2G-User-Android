package com.blogspot.rajbtc.G2GTechUser.employee.pager_system.item_recycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.DownloadImageTask;
import com.blogspot.rajbtc.G2GTechUser.R;
import com.blogspot.rajbtc.G2GTechUser.employee.EmployeeItem;
import com.blogspot.rajbtc.G2GTechUser.employee.pager_system.item_recycler.profile_costing_salary_credit_PagerSystem.ViewEmployeeDetails;
import com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system.item_recycler.ViewAssetItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ViewAssetAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private ArrayList<EmployeeItem> arrayList;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();


    public ViewAssetAdapter(Context context, ArrayList<EmployeeItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_employee,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            FirebaseStorage.getInstance().getReference().child("Employee/AssetCon/"+arrayList.get(position).getNid()+"_200x200.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                 new DownloadImageTask(holder.circleImageView).execute(uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(context,"Image downloading failed!",Toast.LENGTH_SHORT).show();
                }
            });


        holder.employeeName.setText(arrayList.get(position).getName());
        holder.employeeType.setText(arrayList.get(position).getType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] st={arrayList.get(position).getDocID(),arrayList.get(position).getName(),arrayList.get(position).getFatherName(),arrayList.get(position).getMotherName(),arrayList.get(position).getContacts(),arrayList.get(position).getNid(),arrayList.get(position).getEmail(),arrayList.get(position).getPreAddress(),arrayList.get(position).getPerAddress(),arrayList.get(position).getCvLink(),arrayList.get(position).getPass()};
                Intent intent=new Intent(context, ViewEmployeeDetails.class);
                intent.putExtra("data",st);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
