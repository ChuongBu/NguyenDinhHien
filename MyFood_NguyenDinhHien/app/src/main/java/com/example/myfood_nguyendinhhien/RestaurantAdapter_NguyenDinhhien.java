package com.example.myfood_nguyendinhhien;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RestaurantAdapter_NguyenDinhhien extends RecyclerView.Adapter<RestaurantAdapter_NguyenDinhhien.ViewHolder> {

    Context context;
    ArrayList<Restaurant_NguyenDinhhien> list;

    public RestaurantAdapter_NguyenDinhhien(Context context, ArrayList<Restaurant_NguyenDinhhien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_restaurant_nguyendinhhien, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant_NguyenDinhhien r = list.get(position);
        holder.txtName.setText(r.name);
        holder.txtAddress.setText(r.address);

        int resId = context.getResources().getIdentifier(r.image, "drawable", context.getPackageName());
        holder.img.setImageResource(resId == 0 ? android.R.drawable.ic_menu_report_image : resId);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, FoodListActivity_NguyenDinhhien.class);
            i.putExtra("resID", r.id);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddress;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtResName_NguyenDinhhien);
            txtAddress = itemView.findViewById(R.id.txtResAddress_NguyenDinhhien);
            img = itemView.findViewById(R.id.imgRestaurant_NguyenDinhhien);
        }
    }
}
