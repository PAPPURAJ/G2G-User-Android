package com.blogspot.rajbtc.G2GTechUser.employee.pager_system.item_recycler.profile_costing_salary_credit_PagerSystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blogspot.rajbtc.G2GTechUser.DownloadImageTask;
import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewEmployeeDetails extends AppCompatActivity {
    private  String[] data;  //docID,name,fatherName,motherName,contact,nid, mail,preAdd,perAdd,cvLink,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_employee_profile);
        data=getIntent().getStringArrayExtra("data");
        ((TextView)findViewById(R.id.viewEmployeeDetails_nameTv)).setText("Name: "+data[1]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_fatherNameTv)).setText("Father's name: "+data[2]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_motherNameTv)).setText("Mother's name: "+data[3]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_contactTv)).setText("Contact: "+data[4]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_nidTv)).setText("NID: "+data[5]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_mailTv)).setText("Mail: "+data[6]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_preAddTv)).setText("Present address: "+data[7]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_perAddTv)).setText("Permanent address: "+data[8]);
        ((TextView)findViewById(R.id.viewEmployeeDetails_cvLinkTv)).setText("CV link: "+data[9]);

        FirebaseStorage.getInstance().getReference().child("Employee/AssetCon/"+data[5]+"_200x200.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                new DownloadImageTask((CircleImageView)findViewById(R.id.fragmentEmployee_ProfileIv)).execute(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(),"Image downloading failed!",Toast.LENGTH_SHORT).show();
            }
        });

        ((Button)findViewById(R.id.profileFragDriveBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://"+(data[9]).replace("http://","").replace("https://","")));
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.profileFragMailBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+data[6]));
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);

                }

        }
        });


        ((Button)findViewById(R.id.profileFragCallBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+data[4]));
                    startActivity(intent);
                }
                catch (Exception e){

                    checkPermission(Manifest.permission.CALL_PHONE,102);
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
                            ViewEmployeeDetails.this,
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