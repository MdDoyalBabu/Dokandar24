package com.example.dokandar24.Seller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokandar24.Common.Model.CashInModel;
import com.example.dokandar24.R;

import java.util.ArrayList;
import java.util.List;

public class CashInAdapter extends RecyclerView.Adapter<CashInAdapter.MyViewHolder> {
   private Context context;
   private List<CashInModel> cashInList=new ArrayList<>();
    public CashInAdapter(Context context, List<CashInModel> cashInList) {
        this.context = context;
        this.cashInList = cashInList;
     }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cash_in_item_layout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CashInModel currentItem=cashInList.get(position);
        holder.nameTv.setText(currentItem.getSellerName());
        holder.timeTv.setText(currentItem.getTime());
        holder.amounTv.setText(currentItem.getAmount()+"tk");
        holder.phoneTv.setText(currentItem.getSellerPhone());
        String state=currentItem.getState();
        if(state.equals("paid")){
            holder.imageView.setImageResource(R.drawable.paid);

        }else if(state.equals("pending")){
            holder.imageView.setImageResource(R.drawable.pending);
        }
    }

    @Override
    public int getItemCount() {
        return cashInList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTv,timeTv,phoneTv,amounTv;
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.c_NameTv);
            timeTv=itemView.findViewById(R.id.c_timeTv);
            phoneTv=itemView.findViewById(R.id.c_PhoneTv);
            imageView=itemView.findViewById(R.id.c_ImageView);
            amounTv=itemView.findViewById(R.id.c_amountTv);
        }
    }
}
