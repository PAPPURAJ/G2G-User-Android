package com.blogspot.rajbtc.G2GTechUser.my_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blogspot.rajbtc.G2GTechUser.my_activity.other_costing.Costing_Fragment;

public class EmployeeProfileCostingPagerAdapter extends FragmentPagerAdapter {
    String[] profileData,tabData={"Costing","Salary","Credit"};
    public EmployeeProfileCostingPagerAdapter(@NonNull FragmentManager fm,String[] profileData) {
        super(fm);
        this.profileData=profileData;//docID,name,fatherName,motherName,contact,nid, mail,preAdd,perAdd,cvLink,pass;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
           // case 0:return new ProfileFragment(profileData);
            case 0:return new Costing_Fragment(tabData[position],profileData[5]);
            case 1:return new Costing_Fragment(tabData[position],profileData[5]);
            default:return new Costing_Fragment(tabData[position],profileData[5]);

        }
    }

    @Override
    public int getCount() {
        return tabData.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabData[position];
    }
}
