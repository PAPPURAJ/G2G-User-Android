package com.blogspot.rajbtc.G2GTechUser.contact_us;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.rajbtc.G2GTechUser.R;
import com.blogspot.rajbtc.G2GTechUser.employee.pager_system.item_recycler.profile_costing_salary_credit_PagerSystem.ViewEmployeeDetails;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((TextView)findViewById(R.id.services)).setText("\n\n"+
                "Emergency Services\n" +
                "Pre-construction Design and Estimating\n" +
                "Design/Build\n" +
                "Construction Management\n" +
                "Construction Services\n" +
                "Office Build-outs\n" +
                "Renovation and Expansion\n" +
                "Remodeling\n" +
                "Building Sustainability\n" +
                "Upgrades\n" +
                "Bank Equipment Sales and Installation\n");

        ((TextView)findViewById(R.id.contactTv)).setText("Chairman\nMd. Haris Uddin\n\nManaging Director\nEngr. Mohammad Nasir Uddin\n\nContact us\nHead Office\nG2G Tech Limited\n\nEntity Address: House-272/Kha/3-D, West Agargaon, Sher-E Bangla Nagar, Dhaka-1207\nMobile: 01713510548\n\ng2gtech.info@gmail.com\n\n");


        ((Button)findViewById(R.id.contactUsCallBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:+8801713510548"));
                    startActivity(intent);
                }
                catch (Exception e){

                    checkPermission(Manifest.permission.CALL_PHONE,102);
                }
            }
        });

        ((Button)findViewById(R.id.contactUsCallMailBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:g2g.info@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);

                }
            }
        });


    }










    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            ContactUs.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(getApplicationContext(),
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

}