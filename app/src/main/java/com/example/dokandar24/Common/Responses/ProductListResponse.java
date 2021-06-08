package com.example.dokandar24.Common.Responses;

import android.app.ProgressDialog;

import com.example.dokandar24.Common.Model.CashInModel;
import com.example.dokandar24.Common.Model.ProductListModel;

import java.util.List;

public interface ProductListResponse {
    void onSuccess(String message, ProgressDialog progressDialog, ProductListModel productListModel);
    void onError(String message, ProgressDialog progressDialog);
}
