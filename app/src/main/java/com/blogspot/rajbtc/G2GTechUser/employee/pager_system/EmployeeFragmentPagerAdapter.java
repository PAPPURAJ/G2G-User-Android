package com.blogspot.rajbtc.G2GTechUser.employee.pager_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blogspot.rajbtc.G2GTechUser.employee.EmployeeItem;

import java.util.ArrayList;

public class EmployeeFragmentPagerAdapter extends FragmentPagerAdapter {
    private  ArrayList<EmployeeItem> arrayList;
    private ArrayList<String> tabName=new ArrayList<>();
    public EmployeeFragmentPagerAdapter(@NonNull FragmentManager fm, ArrayList<EmployeeItem> arrayList) {
        super(fm);  this.arrayList=arrayList;
                    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new Pager_Fragment(tabName.get(position),arrayList);
    }

    @Override
    public int getCount() {
        tabName.clear();
        tabName.add("All");
        for(int i=0;i<arrayList.size();i++)
            if(!tabName.contains(arrayList.get(i).getSite()))
                tabName.add(arrayList.get(i).getSite());

        return tabName.size();

    }

    @Override
    public void notifyDataSetChanged() {
        arrayList.clear();tabName.clear();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabName.get(position);
    }
}
