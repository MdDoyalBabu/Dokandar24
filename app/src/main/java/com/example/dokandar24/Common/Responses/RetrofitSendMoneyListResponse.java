package com.example.dokandar24.Common.Responses;

import android.app.ProgressDialog;

import com.example.dokandar24.Common.Model.CashInModel;
import com.example.dokandar24.Common.Model.SendMoneyModel;

import java.util.List;

public interface RetrofitSendMoneyListResponse {
    void onSuccess(String message, ProgressDialog progressDialog, List<SendMoneyModel> sendMoneyList);
    void onError(String message, ProgressDialog progressDialog);
}
