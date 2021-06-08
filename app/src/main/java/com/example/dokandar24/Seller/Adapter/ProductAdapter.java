package com.example.dokandar24.Seller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokandar24.Common.Model.CashInModel;
import com.example.dokandar24.Common.Model.ProductModel;
import com.example.dokandar24.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
   private Context context;
   private List<ProductModel> productModelList=new ArrayList<>();
    public ProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
     }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.products_item_layoute,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel productModel=productModelList.get(position);
        holder.productNameTv.setText(productModel.getTitle());
        holder.productPriceTv.setText("Price: "+productModel.getProductPrice()+"tk");

        String description=productModel.getDescription();
        if(description.length()>15){
            String subDes=description.substring(0,13)+"....";
            holder.productDescriptionTv.setText(subDes);

        }else{
            holder.productDescriptionTv.setText(description);
        }
        if(productModel.getImages()!=null){
            String currentImage=productModel.getImages().get(0).getImage();
            Picasso.get().load(currentImage).placeholder(R.drawable.select_image).into(holder.imageView);
        }



    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView productNameTv,productPriceTv,productDescriptionTv;
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productNameTv=itemView.findViewById(R.id.product_name);
            productPriceTv=itemView.findViewById(R.id.product_price);
            productDescriptionTv=itemView.findViewById(R.id.product_description);
            imageView=itemView.findViewById(R.id.product_image);


         }
    }
}
