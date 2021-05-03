package com.blogspot.rajbtc.G2GTechUser.manage_asset;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.rajbtc.G2GTechUser.DownloadImageTask;
import com.blogspot.rajbtc.G2GTechUser.LoadingAlert;
import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddAsset extends AppCompatActivity {
    String TAG="====Add Assset====";
    private StorageReference mStorageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LoadingAlert progress;

    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);
        imageView=findViewById(R.id.addAssetIv);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        progress=new LoadingAlert(AddAsset.this);
    }

    public void uploadClick(View view) {
        final String assetName=((EditText)findViewById(R.id.addAssetNameEt)).getText().toString();
        if(imageUri==null || assetName.equals("")){
            Toast.makeText(getApplicationContext(),"Please choose image",Toast.LENGTH_SHORT).show();
            return;
        }

        progress.start();

        FirebaseFirestore.getInstance().collection("Asset").whereEqualTo("Name",assetName).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                           progress.stop();
                           Toast.makeText(getApplicationContext(),"This asset already added",Toast.LENGTH_SHORT).show();return;
                       }
                        uploadFile(imageUri,assetName);
                    }
                });




    }

    public void chooseFileClick(View view) {
            openGallery();
    }



    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    void uploadFile(Uri imageUri, final String name){
        StorageReference riversRef = mStorageRef.child("Asset/"+name+".jpg");

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Toast.makeText(getApplicationContext(),"Upload done",Toast.LENGTH_SHORT).show();
                        uploadImageDetailsToFirestore(name);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(),"Upload failed!",Toast.LENGTH_SHORT).show();
                        progress.stop();
                    }
                });
    }



    void uploadImageDetailsToFirestore(String name){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Type", "WareHouse");

// Add a new document with a generated ID
        db.collection("Asset")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        progress.stop();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


}