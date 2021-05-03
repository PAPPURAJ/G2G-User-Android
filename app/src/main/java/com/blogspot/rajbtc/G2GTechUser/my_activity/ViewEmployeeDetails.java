package com.blogspot.rajbtc.G2GTechUser.my_activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewEmployeeDetails extends AppCompatActivity {
    private  String[] data;  //docID,name,fatherName,motherName,contact,nid, mail,preAdd,perAdd,cvLink,pass;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_details);
        viewPager=findViewById(R.id.viewEmployeeDetails_Vp);
        tabLayout=findViewById(R.id.viewEmployeeDetails_Tl);

        String nid=getSharedPreferences("login",MODE_PRIVATE).getString("nid","null");
        if(nid.equals("null")){
            Toast.makeText(getApplicationContext(),"Please login again",Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference cref= FirebaseFirestore.getInstance().collection("Employee");
        Query q1=cref.whereEqualTo("NID",nid);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                // this.profileData=profileData;//docID,name,fatherName,motherName,contact,nid, mail,preAdd,perAdd,cvLink,pass;
                    data=new String[]{ds.getId(),ds.getString("Name"),ds.getString("Father Name"),ds.getString("Mother Name"),ds.getString("Contacts"),ds.getString("NID"),ds.getString("Email"),ds.getString("Permanent Address"),ds.getString("CV Link"),ds.getString("Password")};
                    viewPager.setAdapter(new EmployeeProfileCostingPagerAdapter(getSupportFragmentManager(),data));
                    tabLayout.setupWithViewPager(viewPager);
                }
            }
        });


    }
}