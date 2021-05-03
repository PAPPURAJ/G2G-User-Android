package com.blogspot.rajbtc.G2GTechUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    private CircleImageView circleImageView;
    private FirebaseFirestore db;
    private SharedPreferences sp;
    private static final int PICK_IMAGE = 100;
    Uri imageUri=null;
    private LoadingAlert loadingAlert;
    private String TAG= "===Login===";
    private Button loginBtn;

    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadingAlert=new LoadingAlert(LoginActivity.this);

        db = FirebaseFirestore.getInstance();
        sp=this.getSharedPreferences("login",MODE_PRIVATE);
        ((EditText)findViewById(R.id.login_nidEt)).setText(sp.getString("nid",""));
        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void signUpClick(View view) {
        setContentView(R.layout.activity_signup);
        circleImageView=findViewById(R.id.signUp_circleIv);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilePicClick(null);
            }
        });

        ((TextView)findViewById(R.id.alreadyRegSignup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_alreadyUserClick(null);
            }
        });

        loginBtn=findViewById(R.id.signUp_LoginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClick();
            }
        });
    }

    public void register_alreadyUserClick(View view) {
        setContentView(R.layout.activity_login);
        ((EditText)findViewById(R.id.login_nidEt)).setText(sp.getString("nid",""));
    }



    public void loginButtonClick(View view) {

        final String nid=((EditText)findViewById(R.id.login_nidEt)).getText().toString(),pass=((EditText)findViewById(R.id.login_passEt)).getText().toString();
        if(nid.equals("") || pass.equals("")){
            Toast.makeText(getApplicationContext(),"Please input all data.",Toast.LENGTH_SHORT).show();
            return;
        } loadingAlert.start();
        CollectionReference cref=db.collection("Employee");
        Query q1=cref.whereEqualTo("NID",nid);
        Log.e(TAG,"ID: "+nid+"  Pass:"+pass);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String ID,password;
                    ID = ds.getString("NID");
                    password= ds.getString("Password");
                    if (ID.equals(nid) && password.equals(pass)) {
                        sp.edit().putString("nid",nid).putString("pass",pass).apply();

                        if(ds.getString("SiteName").equals("WareHouse")){
                            Toast.makeText(getApplicationContext(), "Please wait until admin accept you", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            sp.edit().putString("mySiteID",ds.getString("SiteID")).putString("myNID",nid).putString("myName",ds.getString("Name")).putString("mySiteName",ds.getString("SiteName")).apply();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                            loadingAlert.stop();
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                loadingAlert.stop();
            }
        });}

    public void registerClick() {

        final String name, fatherName,motherName, contact, nid, email,preAdd,perAdd,cvLink,pass;
        name=((EditText)findViewById(R.id.nameEt)).getText().toString();
        fatherName=((EditText)findViewById(R.id.fatherNameEt)).getText().toString();
        motherName=((EditText)findViewById(R.id.motherNameEt)).getText().toString();
        contact=((EditText)findViewById(R.id.contactEt)).getText().toString();
        nid=((EditText)findViewById(R.id.nidET)).getText().toString();
        email=((EditText)findViewById(R.id.emailEt)).getText().toString();
        preAdd=((EditText)findViewById(R.id.preAddEt)).getText().toString();
        perAdd=((EditText)findViewById(R.id.perAddEt)).getText().toString();
        cvLink=((EditText)findViewById(R.id.cvLinkEt)).getText().toString();
        pass=((EditText)findViewById(R.id.passEt)).getText().toString();
        if(imageUri==null){
            Toast.makeText(getApplicationContext(),"Please set a image",Toast.LENGTH_LONG).show();
            return;
        }
        if(name.equals("") || fatherName.equals("") || motherName.equals("") || contact.equals("") || nid.equals("") || email.equals("") || preAdd.equals("") || perAdd.equals("") || cvLink.equals("") || pass.equals("")){
            Toast.makeText(getApplicationContext(),"Please input all data.",Toast.LENGTH_SHORT).show();
            return;
        }

        loginBtn.setOnClickListener(null);

        loadingAlert.start();

        CollectionReference cref=db.collection("Employee");
        Query q1=cref.whereEqualTo("NID",nid);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String ID;
                    ID = ds.getString("NID");
                    if (ID.replace(" ","").equals(nid.replace(" ",""))) {
                        Toast.makeText(getApplicationContext(),"User already exist!",Toast.LENGTH_SHORT).show();
                        loadingAlert.stop();
                        signUpClick(null);
                        return;
                    }
                }

            //Profile pic upload section
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("Employee/"+nid+".jpg");
                riversRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
           //Profile upload section
                                                    Toast.makeText(getApplicationContext(),"Upload done",Toast.LENGTH_SHORT).show();
                                                    Map<String, Object> user = new HashMap<>();
                                                    user.put("Name", name);
                                                    user.put("Father Name", fatherName);
                                                    user.put("Mother Name", motherName);
                                                    user.put("Contacts", contact);
                                                    user.put("NID", nid);
                                                    user.put("Email", email);
                                                    user.put("Present Address", preAdd);
                                                    user.put("Permanent Address", perAdd);
                                                    user.put("CV Link", cvLink);
                                                    user.put("Password", pass);
                                                    user.put("Type","NA");
                                                    user.put("SiteName","WareHouse");
                                                    user.put("SiteID","NA");


                                                    db.collection("Employee")
                                                            .add(user)
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Toast.makeText(getApplicationContext(),"Successfully Registered!",Toast.LENGTH_SHORT).show();
                                                                    sendPushNotificationToAdmin(name);
                                                                    register_alreadyUserClick(null);
                                                                    loadingAlert.stop();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getApplicationContext(),"Registration Failed!",Toast.LENGTH_SHORT).show();
                                                                    loadingAlert.stop();
                                                                }
                                                            });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(),"Upload failed!",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }

    public void profilePicClick(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            circleImageView.setImageURI(imageUri);
        }
    }


    void sendPushNotificationToAdmin(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("NewLogin");
        myRef.setValue(name);
    }


}