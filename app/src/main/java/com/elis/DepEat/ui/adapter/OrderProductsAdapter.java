package com.elis.DepEat.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.elis.DepEat.R;
import com.elis.DepEat.datamodels.Products;

import java.util.ArrayList;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.OrderProductViewHolder>{

    private ArrayList<Products> dataSet;
    private Context context;
    private LayoutInflater inflater;
    private float miniumOrder;




    public  OrderProductsAdapter(Context context, ArrayList<Products> dataSet,float miniumOrder){

        this.dataSet = dataSet;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.miniumOrder = miniumOrder;
    }

    public interface onItemRemovedListener{
        void onItemRemoved(float subtotal);

    }


    private onItemRemovedListener onItemRemovedListener;


    public OrderProductsAdapter.onItemRemovedListener getOnItemRemovedListener() {
        return onItemRemovedListener;
    }

    public void setOnItemRemovedListener(OrderProductsAdapter.onItemRemovedListener onItemRemovedListener) {
        this.onItemRemovedListener = onItemRemovedListener;
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new OrderProductViewHolder(inflater.inflate(R.layout.item_order_product,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder orderProductViewHolder, int i) {
        Products product = dataSet.get(i);
        orderProductViewHolder.productNameTv.setText(product.getNome());
        orderProductViewHolder.quantityTv.setText(String.valueOf(product.getQuantita()));
        orderProductViewHolder.subtotalTv.setText(String.valueOf(product.getSubtotale()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private void removeItem(int index){
        dataSet.remove(index);
        notifyItemRemoved(index);

    }


    public class OrderProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView quantityTv,productNameTv,subtotalTv;
        public ImageButton removeBtn;


        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            quantityTv = itemView.findViewById(R.id.quantity_tv);
            productNameTv = itemView.findViewById(R.id.product_name_tv);
            subtotalTv = itemView.findViewById(R.id.subtotal_tv);
            removeBtn = itemView.findViewById(R.id.remove_btn);
            removeBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder removeAlert = new AlertDialog.Builder(context);
            removeAlert.setTitle(R.string.be_careful)
                    .setMessage(R.string.remove_title)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onItemRemovedListener.onItemRemoved(dataSet.get(getAdapterPosition()).getSubtotale());
                            removeItem(getAdapterPosition());

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .create()
                    .show();

        }
    }
}