package com.blogspot.rajbtc.G2GTechUser.employee;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blogspot.rajbtc.G2GTechUser.R;
import com.blogspot.rajbtc.G2GTechUser.employee.pager_system.EmployeeFragmentPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {

    private ArrayList<EmployeeItem> arrayList=new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        tabLayout=findViewById(R.id.manageEmployee_Tl);
        viewPager=findViewById(R.id.manageEmployee_Vp);
        loadAssetDetails();
    }





    void loadAssetDetails(){

        FirebaseFirestore.getInstance().collection("Employee")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                arrayList.add(new EmployeeItem(document.getId(),document.getString("Name"),document.getString("Father Name"),document.getString("Mother Name"),document.getString("Contacts"),document.getString("Email"),document.getString("NID"),document.getString("Present Address"),document.getString("Present Address"),document.getString("Password"),document.getString("CV Link"),document.getString("Type"),document.getString("SiteName")));
                            }
                            if(!arrayList.isEmpty()){
                                viewPager.setAdapter(new EmployeeFragmentPagerAdapter(getSupportFragmentManager(),arrayList));
                                tabLayout.setupWithViewPager(viewPager);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Loading failed!",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }
}

