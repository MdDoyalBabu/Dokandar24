package com.example.dokandar24.Seller.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dokandar24.Fragments.CashInFragments;
import com.example.dokandar24.Fragments.PendingCashInFragments;
import com.example.dokandar24.Fragments.SendMoneyFragment;
import com.example.dokandar24.Fragments.SendMoneyHistoryFragment;
import com.example.dokandar24.Fragments.SuccessCashInFragments;

public class SendMoneySectionPagerAdapter extends FragmentPagerAdapter {
    public SendMoneySectionPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:{
                return new SendMoneyFragment();
            }
            case 1: {
               return new SendMoneyHistoryFragment();
            }
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return  "Send Money";
            case 1:
                return  "History";
           default:
                 return null;

        }
    }

}
