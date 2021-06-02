package com.example.dokandar24.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.CashInModel;
import com.example.dokandar24.Common.Model.SendMoneyModel;
import com.example.dokandar24.Common.Responses.RetrofitCashInListResponse;
import com.example.dokandar24.Common.Responses.RetrofitSendMoneyListResponse;
import com.example.dokandar24.R;
import com.example.dokandar24.Seller.Adapter.CashInAdapter;
import com.example.dokandar24.Seller.Adapter.SendMoneyAdapter;

import java.util.ArrayList;
import java.util.List;

public class SendMoneyHistoryFragment extends Fragment {
    public SendMoneyHistoryFragment() {
   }

    private RecyclerView recyclerView;
    private SendMoneyAdapter sendMoneyAdapter;

    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_send_money_history, container, false);
        userApi=new UserApi(getActivity());
        userDb=new UserDb(getActivity());
        progressDialog=new ProgressDialog(getContext());
        recyclerView=view.findViewById(R.id.sendMoneyRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return  view;
    }
    @Override
    public void onStart() {
        super.onStart();
        String token=userDb.getAccessToken();
        progressDialog.setMessage("Loading..");
        userApi.getSendMoneyHistory(token,  progressDialog, new RetrofitSendMoneyListResponse() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog, List<SendMoneyModel> sendMoneyList) {
                sendMoneyAdapter=new SendMoneyAdapter(getContext(),sendMoneyList);
                recyclerView.setAdapter(sendMoneyAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
            }
        });




    }
}