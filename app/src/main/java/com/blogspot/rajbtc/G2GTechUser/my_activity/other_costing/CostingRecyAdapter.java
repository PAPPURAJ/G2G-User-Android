package com.blogspot.rajbtc.G2GTechUser.my_activity.other_costing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CostingRecyAdapter extends RecyclerView.Adapter<CostingRecyAdapter.CostingViewHolder> {
    private ArrayList<CostingItem> arrayList;
    private Context context;
    private String tabType,nid;

    public CostingRecyAdapter(Context context,ArrayList<CostingItem> arrayList, String nid,String tabType) {
        this.arrayList = arrayList;
        this.context = context;
        this.tabType=tabType;
        this.nid=nid;
    }

    @NonNull
    @Override
    public CostingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CostingViewHolder(LayoutInflater.from(context).inflate(R.layout.single_employee_costing,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CostingViewHolder holder, final int position) {
        holder.costName.setText("Cost name: "+arrayList.get(position).getCostName());
        holder.quantity.setText("Quantity: "+arrayList.get(position).getQuantity());
        holder.payment.setText("Payment: "+arrayList.get(position).getPayment());
        holder.type.setText("Status: "+arrayList.get(position).getType());
        holder.time.setText("Time: "+arrayList.get(position).getTime());
        holder.date.setText("Date: "+arrayList.get(position).getDate());
        if(tabType.toLowerCase().equals("costing"))
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Do you want to delete?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("======","NID: "+nid+" TabType:"+tabType+" docID:"+arrayList.get(position).getDocID());
                        FirebaseFirestore.getInstance().collection("EmpCosting").document(nid).collection(tabType).document(arrayList.get(position).getDocID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context,"Deleting success",Toast.LENGTH_SHORT).show();
                                arrayList.remove(position);
                                notifyDataSetChanged();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,"Deleting failed",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Cancel",null).show();

                return true;
            }
        });
        else{
            holder.costName.setTextSize(0);
            holder.costName.setEnabled(false);
            holder.quantity.setTextSize(0);
            holder.quantity.setEnabled(false);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            acceptCosting(arrayList.get(position).getDocID(),holder);
                        }
                    }).setNegativeButton("Cancel",null).setTitle("Do you want to accept?").show();
                    return true;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    private void acceptCosting(String docID, final CostingViewHolder holder) {

        final SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat timeFormat= new SimpleDateFormat("HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());

        FirebaseFirestore.getInstance().collection("EmpCosting").document(nid).collection(tabType).document(docID)
                .update(
                        "Type", "Accepted",
                        "Date",dateFormat.format(date),
                        "Time",timeFormat.format(date)
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Product accepted", Toast.LENGTH_LONG).show();
                        holder.type.setText("Status: Accepted!");
                        holder.date.setText("Date: "+dateFormat.format(date));
                        holder.time.setText("Time: "+timeFormat.format(date));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Product not accepted", Toast.LENGTH_LONG).show();
                    }
                });


    }









    public class CostingViewHolder extends RecyclerView.ViewHolder{

        TextView costName,quantity,payment,type,date,time;

        public CostingViewHolder(@NonNull View itemView) {
            super(itemView);

            costName=itemView.findViewById(R.id.singleEmployeeCosting_costNameTv);
            quantity=itemView.findViewById(R.id.singleEmployeeCosting_quantityTv);
            payment=itemView.findViewById(R.id.singleEmployeeCosting_paymentTv);
            type=itemView.findViewById(R.id.singleEmployeeCosting_typeTv);
            date=itemView.findViewById(R.id.singleEmployeeCosting_dateTv);
            time=itemView.findViewById(R.id.singleEmployeeCosting_timeTv);
        }
    }
}
