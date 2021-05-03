package com.blogspot.rajbtc.G2GTechUser.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.rajbtc.G2GTechUser.DownloadImageTask;
import com.blogspot.rajbtc.G2GTechUser.LoadingAlert;
import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {
    private EditText nameEt, fatherNameEt,motherNameEt, contactEt, nidET, emailEt,preAddEt,perAddEt,cvLinkEt,passEt;
    private String docID;
    private LoadingAlert progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameEt=findViewById(R.id.nameEt);
        fatherNameEt=findViewById(R.id.fatherNameEt);
        motherNameEt=findViewById(R.id.motherNameEt);
        contactEt=findViewById(R.id.contactEt);
        nidET=findViewById(R.id.nidET);
        emailEt=findViewById(R.id.emailEt);
        preAddEt=findViewById(R.id.preAddEt);
        perAddEt=findViewById(R.id.perAddEt);
        cvLinkEt=findViewById(R.id.cvLinkEt);
        passEt=findViewById(R.id.passEt);
        ((TextView)findViewById(R.id.alreadyRegSignup)).setText("");
        findViewById(R.id.alreadyRegSignup).setEnabled(false);
        progress=new LoadingAlert(MyProfile.this);
        loadData();
    }



    void loadData(){
        progress.start();
        FirebaseFirestore.getInstance().collection("Employee").whereEqualTo("NID",getSharedPreferences("login",MODE_PRIVATE).getString("nid","null"))
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot ds:queryDocumentSnapshots){
                    nameEt.setText(ds.getString("Name"));
                    nameEt.setEnabled(false);
                    fatherNameEt.setText(ds.getString("Father Name"));
                    motherNameEt.setText(ds.getString("Mother Name"));
                    contactEt.setText(ds.getString("Contacts"));
                    nidET.setText(ds.getString("NID"));
                    nidET.setEnabled(false);
                    emailEt.setText(ds.getString("Email"));
                    preAddEt.setText(ds.getString("Present Address"));
                    perAddEt.setText(ds.getString("Permanent Address"));
                    cvLinkEt.setText(ds.getString("CV Link"));
                    passEt.setText(ds.getString("Password"));
                    docID=ds.getId();

                FirebaseStorage.getInstance().getReference().child("Employee/AssetCon/"+ds.getString("NID")+"_200x200.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        new DownloadImageTask((CircleImageView)findViewById(R.id.signUp_circleIv)).execute(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(),"Image downloading failed!",Toast.LENGTH_SHORT).show();
                    }
                });
                    ((Button)findViewById(R.id.signUp_LoginBtn)).setText("Update");
                findViewById(R.id.signUp_LoginBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                                final String  fatherName,motherName, contact, email,preAdd,perAdd,cvLink,pass;
                                fatherName=fatherNameEt.getText().toString();
                                motherName=motherNameEt.getText().toString();
                                contact=contactEt.getText().toString();
                                email=emailEt.getText().toString();
                                preAdd=preAddEt.getText().toString();
                                perAdd=perAddEt.getText().toString();
                                cvLink=cvLinkEt.getText().toString();
                                pass=passEt.getText().toString();

                                if(fatherName.equals("") || motherName.equals("") || contact.equals("") || email.equals("") || preAdd.equals("") || perAdd.equals("") || cvLink.equals("") || pass.equals("")){
                                    Toast.makeText(getApplicationContext(),"Please input all data.",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                progress.start();
                                FirebaseFirestore.getInstance().collection("Employee").document(docID)
                                        .update("Father Name",fatherName,"Mother Name",motherName,"Contacts",contact,"Email",email,"Present Address",preAdd,"Permanent Address",perAdd,"CV Link",cvLink,"Password",pass)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(),"Profile successfully updated!",Toast.LENGTH_SHORT).show();
                                                progress.stop();
                                            }
                                        });

                    }
                });



                }progress.stop();
            }
        });


    }
}


