package com.blogspot.rajbtc.G2GTechUser.manage_asset.pager_system.item_recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.DownloadImageTask;
import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewAssetAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private ArrayList<ViewAssetItem> arrayList;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private String itemtype;


    public ViewAssetAdapter(Context context,String itemtype, ArrayList<ViewAssetItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.itemtype=itemtype;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_asset_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.assetName.setText(arrayList.get(position).assetName);
        new DownloadImageTask(holder.imageView).execute(arrayList.get(position).assetUrl);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(context,"You don't access to change",Toast.LENGTH_SHORT).show();

                return true;
            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    void viewDialog(final String name, String imgUrl, final String selectedItem, ArrayList<String> arrayList){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_edit_asset,null));
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

        ImageView itemIv=alertDialog.findViewById(R.id.dialogEditAsset_Iv);
        EditText itemName=alertDialog.findViewById(R.id.dialogEditDialogName_Et);
        final Spinner spinner=alertDialog.findViewById(R.id.dialogEditDialogType_Et);


        new DownloadImageTask(itemIv).execute(imgUrl);
        itemName.setText(name);
        spinner.setAdapter(new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,arrayList));

        ((Button)alertDialog.findViewById(R.id.dialogEditDialogUpdate_Btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(name,selectedItem,spinner.getSelectedItem().toString());

            }
        });




    }

    private void updateData(String itemName, String previousType, final String updateType) {
        CollectionReference cref=db.collection("Asset");

        Query q1=cref.whereEqualTo("Name",itemName).whereEqualTo("Type",previousType);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String id=ds.getId();
                    updateData2(id,updateType);
                    return;
                }

            }
        });

    }

    private void updateData2(String editDocPath,String typeStr){
        db.collection("Asset").document(editDocPath)
                .update(
                        "Type", typeStr
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Product Updated", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Product not Updated", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
