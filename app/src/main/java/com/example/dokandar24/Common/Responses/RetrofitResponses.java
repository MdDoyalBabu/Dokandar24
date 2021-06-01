package com.example.dokandar24.Common.Responses;

import android.app.ProgressDialog;
import android.widget.ProgressBar;

public interface RetrofitResponses {
    void onSuccess(String message, ProgressDialog progressDialog);
    void onError(String message, ProgressDialog progressDialog);
}
