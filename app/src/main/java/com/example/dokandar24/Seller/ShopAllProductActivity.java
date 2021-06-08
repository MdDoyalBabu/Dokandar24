package com.example.dokandar24.Seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dokandar24.Common.ApiCall.ProductApi;
import com.example.dokandar24.Common.LocalDb.UserDb;
import com.example.dokandar24.Common.Model.ProductListModel;
import com.example.dokandar24.Common.Responses.ProductListResponse;
import com.example.dokandar24.R;
import com.example.dokandar24.Seller.Adapter.ProductAdapter;

public class ShopAllProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private UserDb userDb;
    private ProgressDialog progressDialog;
    private ProductApi productApi;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_all_product);
        init();
    }
    private void init(){
        userDb=new UserDb(this);
        productApi=new ProductApi(this);
        progressDialog=new ProgressDialog(this);

        toolbar=findViewById(R.id.appBarId);
        setSupportActionBar(toolbar);
        this.setTitle("Products.");

        recyclerView=findViewById(R.id.productRecyclerViewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        getAllProduct();


    }

    private void getAllProduct() {
        String token=userDb.getAccessToken();
        progressDialog.setMessage("Loading...");

        productApi.getMYProducts(token, progressDialog, new ProductListResponse() {
            @Override
            public void onSuccess(String message, ProgressDialog progressDialog, ProductListModel productListModel) {
                progressDialog.dismiss();
                productAdapter=new ProductAdapter(ShopAllProductActivity.this,productListModel.getProducts());
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onError(String message, ProgressDialog progressDialog) {
                progressDialog.dismiss();
                Toast.makeText(ShopAllProductActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }
        });



    }
}