package com.izhar.halalboss.ui.gallery;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.izhar.halalboss.R;
import com.izhar.halalboss.models.Order;
import com.izhar.halalboss.ui.home.FoodListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.Holder> {
    Context context;
    List<Order> orders;

    public OrderListAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_order, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Order order = orders.get(position);
        holder.id.setText(order.getId());
        holder.price.setText(order.getTotal_price() + " ETB");
        holder.status.setText(order.getStatus());
        holder.amount.setText(order.getFoods().size() + " Foods");
        Picasso.with(context).load(order.getUser().getUser_image()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView id, amount, status, price;
        LinearLayout linear;
        public Holder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.order_id);
            amount = itemView.findViewById(R.id.order_amount);
            status = itemView.findViewById(R.id.order_status);
            price = itemView.findViewById(R.id.order_total_price);
            image = itemView.findViewById(R.id.user_image);
            linear = itemView.findViewById(R.id.linear);
            linear.setOnClickListener(this);
            image.setOnClickListener(this);
            id.setOnClickListener(this);
            amount.setOnClickListener(this);
            status.setOnClickListener(this);
            price.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Order order = orders.get(getAdapterPosition());

            Dialog dialog = new Dialog(context);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.order_detail);
            dialog.show();

            ImageView image = dialog.findViewById(R.id.user_image);
            Picasso.with(context).load(order.getUser().getUser_image()).placeholder(R.drawable.person).into(image);

            RecyclerView recyclerView = dialog.findViewById(R.id.recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(true);
            FoodListAdapter fla = new FoodListAdapter(context, order.getFoods());
            recyclerView.setAdapter(fla);

            TextView id, name, address, amount, status, price;

            id = dialog.findViewById(R.id.order_id);
            id.setText(order.getId());

            amount = dialog.findViewById(R.id.order_amount);
            amount.setText(order.getFoods().size() + " Foods");

            status = dialog.findViewById(R.id.order_status);
            status.setText(order.getStatus());

            price = dialog.findViewById(R.id.order_total_price);
            price.setText(order.getTotal_price() + " ETB");

            name = dialog.findViewById(R.id.user_name);
            name.setText(order.getUser().getName());

            address = dialog.findViewById(R.id.order_address);
            address.setText(order.getAddress());

            Button accept = dialog.findViewById(R.id.accept);
            Button reject = dialog.findViewById(R.id.reject);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
