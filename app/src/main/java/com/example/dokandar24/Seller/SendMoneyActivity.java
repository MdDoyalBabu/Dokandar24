package com.example.dokandar24.Seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.dokandar24.R;
import com.example.dokandar24.Seller.Adapter.CashoutSectionPagerAdapter;
import com.example.dokandar24.Seller.Adapter.SendMoneySectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SendMoneyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mviewPager;
    private SendMoneySectionPagerAdapter sectionPagerAdapter;
    private TabLayout mTablayot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        toolbar=findViewById(R.id.appBarId);
        setSupportActionBar(toolbar);
        this.setTitle("Send Money");


        mviewPager=findViewById(R.id.adminTabpagerid);
        sectionPagerAdapter=new SendMoneySectionPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(sectionPagerAdapter);
        mTablayot=findViewById(R.id.admin_tabId);
        mTablayot.setupWithViewPager(mviewPager);
    }
}