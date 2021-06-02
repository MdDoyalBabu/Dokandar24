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
import com.example.dokandar24.Common.Responses.RetrofitCashInListResponse;
import com.example.dokandar24.R;
import com.example.dokandar24.Seller.Adapter.CashInAdapter;

import java.util.ArrayList;
import java.util.List;

public class PendingCashInFragments extends Fragment {
    private RecyclerView recyclerView;
    private CashInAdapter cashInAdapter;
    private List<CashInModel> cashInList=new ArrayList<>();

    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;


    public PendingCashInFragments() {
     }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pending_cash_in_fragments, container, false);
        userApi=new UserApi(getActivity());
        userDb=new UserDb(getActivity());
        progressDialog=new ProgressDialog(getContext());
        recyclerView=view.findViewById(R.id.cashInRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return  view;
    }
    @Override
    public void onStart() {
        super.onStart();
        String token=userDb.getAccessToken();
        progressDialog.setMessage("Loading..");
        userApi.getCashIn(token, "pending", progressDialog, new RetrofitCashInListResponse() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog, List<CashInModel> dcashInList) {
                cashInAdapter=new CashInAdapter(getContext(),dcashInList);
                recyclerView.setAdapter(cashInAdapter);
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