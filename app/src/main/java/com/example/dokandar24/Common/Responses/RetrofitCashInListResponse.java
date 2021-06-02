package com.example.dokandar24.Common.Responses;

import android.app.ProgressDialog;

import com.example.dokandar24.Common.Model.CashInModel;
import com.example.dokandar24.Common.Model.SellerModel;

import java.util.List;

public interface RetrofitCashInListResponse {
    void onSuccess(String message, ProgressDialog progressDialog, List<CashInModel> cashInList);
    void onError(String message, ProgressDialog progressDialog);
}
