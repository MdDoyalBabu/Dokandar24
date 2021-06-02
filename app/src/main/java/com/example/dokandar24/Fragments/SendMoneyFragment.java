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

public class SendMoneyFragment extends Fragment {
    public SendMoneyFragment() {
   }

    private Button sendMoneyButton;
    private EditText phoneEt,amountEt;


    UserApi userApi;
    UserDb userDb;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_send_money, container, false);
        userApi=new UserApi(getActivity());
        userDb=new UserDb(getActivity());
        progressDialog=new ProgressDialog(getContext());

        sendMoneyButton=view.findViewById(R.id.send_MoneyButton);
        phoneEt=view.findViewById(R.id.phoneEt);
        amountEt=view.findViewById(R.id.amountEt);


        sendMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=phoneEt.getText().toString();
                String amount=amountEt.getText().toString();
                if(phone.isEmpty()){
                    phoneEt.setError("Enter Phone Number");
                    phoneEt.requestFocus();
                    return;
                }else if(amount.isEmpty()){
                    amountEt.setError("Enter Some Amount");
                    amountEt.requestFocus();
                    return;
                }else{
                    sendMoney(phone,Integer.parseInt(amount));
                }
            }
        });



        return view;
    }

    private void sendMoney(String phone,int amount) {
        progressDialog.setMessage("Loading...");
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
        cashMap.put("amount",amount);
        cashMap.put("phone",phone);

        userApi.sendMoney(cashMap, token, progressDialog, new RetrofitResponses() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                phoneEt.setText("");
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