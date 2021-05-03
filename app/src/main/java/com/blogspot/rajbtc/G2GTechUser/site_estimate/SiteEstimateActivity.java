package com.blogspot.rajbtc.G2GTechUser.site_estimate;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.rajbtc.G2GTechUser.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class SiteEstimateActivity extends AppCompatActivity {
    private TextView siteEstimateTv,SiteEstimateTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_estimate);
        siteEstimateTv=findViewById(R.id.siteEstimateTv);
        SiteEstimateTitleTv=findViewById(R.id.siteEstimateTitle);
        loadSiteEstimate();

    }

    void loadSiteEstimate(){
        String mySiteID=getSharedPreferences("login",MODE_PRIVATE).getString("mySiteID","null");
        if(mySiteID.equals("null")){
            Toast.makeText(getApplicationContext(),"Please login again!",Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore.getInstance().collection("Site").whereEqualTo("TenderID",mySiteID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot ds:queryDocumentSnapshots){
                            siteEstimateTv.setText(ds.getString("Estimate"));
                            SiteEstimateTitleTv.setText(ds.getString("WorkTitle"));
                        }
                    }
                });


    }



}