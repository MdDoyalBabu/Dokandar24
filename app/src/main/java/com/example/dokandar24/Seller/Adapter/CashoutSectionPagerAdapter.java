package com.example.dokandar24.Seller.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dokandar24.Fragments.CashInFragments;
import com.example.dokandar24.Fragments.PendingCashInFragments;
import com.example.dokandar24.Fragments.SuccessCashInFragments;

public class CashoutSectionPagerAdapter extends FragmentPagerAdapter {
    public CashoutSectionPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:{
                return new CashInFragments();
            }
            case 1: {
               return new PendingCashInFragments();
            }
            case 2: {
                return new SuccessCashInFragments();
            }
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return  "Home";
            case 1:
                return  "Pending";
            case 2:
                return  "Success";
             default:
                 return null;

        }
    }

}
