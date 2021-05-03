package com.blogspot.rajbtc.G2GTechUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.rajbtc.G2GTechUser.contact_us.ContactUs;
import com.blogspot.rajbtc.G2GTechUser.employee.EmployeeActivity;
import com.blogspot.rajbtc.G2GTechUser.manage_asset.AddAsset;
import com.blogspot.rajbtc.G2GTechUser.my_activity.ViewEmployeeDetails;
import com.blogspot.rajbtc.G2GTechUser.manage_asset.ManageAsset;
import com.blogspot.rajbtc.G2GTechUser.profile.MyProfile;
import com.blogspot.rajbtc.G2GTechUser.site_estimate.SiteEstimateActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.about){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setTitle("Developer").setIcon(R.drawable.star).setMessage("Developed by\nPAPPURAJ BHOTTACHARJEE\nPowered by Roboment")
                    .setPositiveButton("Developer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https:www.facebook.com/PAPPURAJ.DUET"));
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Roboment", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://www.facebook.com/RobomentLab"));
                            startActivity(intent);
                        }
                    }).show();

        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public void assetManClick(View view) {startActivity(new Intent(getApplicationContext(), ManageAsset.class));
    }

    public void manageEmployeeClick(View view) {startActivity(new Intent(this, EmployeeActivity.class));
    }

    public void myActivityClick(View view) {startActivity(new Intent(this, ViewEmployeeDetails.class));
    }

    public void siteEstimateClick(View view) {startActivity(new Intent(this, SiteEstimateActivity.class));
    }

    public void addAssetClick(View view) {startActivity(new Intent(this, AddAsset.class));
    }

    public void myProfileClick(View view) {startActivity(new Intent(this, MyProfile.class));
    }

    public void contactUsClick(View view) {startActivity(new Intent(this, ContactUs.class));
    }
}
