package com.example.dokandar24.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.UserApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Responses.RetrofitResponses;
import com.example.dokandar24.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CashInFragments extends Fragment {
    public CashInFragments() {
     }
    private Button cashInButton;
    private EditText amountEt;


    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_cash_in_fragments, container, false);

        userApi=new UserApi(getActivity());
        userDb=new UserDb(getActivity());
        progressDialog=new ProgressDialog(getContext());

        cashInButton=view.findViewById(R.id.cashin_Button);
        amountEt=view.findViewById(R.id.amountEt);

        cashInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=amountEt.getText().toString();
                if(amount.isEmpty()){
                    amountEt.setError("Enter Some Amount..");
                    amountEt.requestFocus();
                    return;
                }else{
                    int am=Integer.parseInt(amount);
                    cashIn(am);
                }
            }
        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void cashIn(int am) {
        progressDialog.setMessage("Sending Cash In Request...");
        progressDialog.setCancelable(false);
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyy");
        String saveCurrentDate=currentDate.format(calForDate.getTime());

        Calendar callForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm");
        String saveCurrentTime=currentTime.format(callForTime.getTime());
        String totalTime=saveCurrentDate+" at "+saveCurrentTime;
        String token=userDb.getAccessToken();

        Map<String,Object> cashMap=new HashMap<>();
        cashMap.put("currentTime",totalTime);
        cashMap.put("amount",am);


        userApi.sendCashInRequest(cashMap, token, progressDialog, new RetrofitResponses() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                amountEt.setText("");
                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
            }
        });





    }
}