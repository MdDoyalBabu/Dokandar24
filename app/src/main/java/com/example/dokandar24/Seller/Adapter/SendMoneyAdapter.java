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
import com.example.dokandar24.Common.Model.SendMoneyModel;
import com.example.dokandar24.R;

import java.util.ArrayList;
import java.util.List;

public class SendMoneyAdapter extends RecyclerView.Adapter<SendMoneyAdapter.MyViewHolder> {
   private Context context;
   private List<SendMoneyModel> sendMoneyList=new ArrayList<>();
    public SendMoneyAdapter(Context context,List<SendMoneyModel> sendMoneyList) {
        this.context = context;
        this.sendMoneyList = sendMoneyList;
     }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.send_money_item_layout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SendMoneyModel currentItem=sendMoneyList.get(position);
        holder.nameTv.setText("Name: "+currentItem.getReceiverName());
        holder.timeTv.setText("Time: "+currentItem.getTime());
        holder.amounTv.setText(currentItem.getAmount()+"tk");

    }

    @Override
    public int getItemCount() {
        return sendMoneyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTv,timeTv,amounTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.s_NameTv);
            timeTv=itemView.findViewById(R.id.s_TimeTv);
            amounTv=itemView.findViewById(R.id.s_amountTv);
        }
    }
}
