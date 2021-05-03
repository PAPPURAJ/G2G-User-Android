package com.blogspot.rajbtc.G2GTechUser.employee.pager_system.item_recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.rajbtc.G2GTechUser.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView employeeName, employeeType;
    CircleImageView circleImageView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        employeeName=itemView.findViewById(R.id.singleEmployee_Name_Tv);
        employeeType=itemView.findViewById(R.id.singleEmployee_type_Tv);
        circleImageView=itemView.findViewById(R.id.singleEmployee_proCircleIv);
    }
}
