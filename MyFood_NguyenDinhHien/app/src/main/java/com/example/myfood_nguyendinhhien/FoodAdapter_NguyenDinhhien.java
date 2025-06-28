package com.example.myfood_nguyendinhhien;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FoodAdapter_NguyenDinhhien extends RecyclerView.Adapter<FoodAdapter_NguyenDinhhien.ViewHolder> {

    Context context;
    ArrayList<Food_NguyenDinhhien> list;

    public FoodAdapter_NguyenDinhhien(Context context, ArrayList<Food_NguyenDinhhien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_nguyendinhhien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food_NguyenDinhhien f = list.get(position);
        holder.txtName.setText(f.name);
        holder.txtDesc.setText(f.description);

        int imageResId = context.getResources().getIdentifier(f.image, "drawable", context.getPackageName());
        holder.img.setImageResource(imageResId != 0 ? imageResId : android.R.drawable.ic_menu_report_image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetailActivity_NguyenDinhhien.class);
            intent.putExtra("foodID", f.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtFoodName_NguyenDinhhien);
            txtDesc = itemView.findViewById(R.id.txtFoodDesc_NguyenDinhhien);
            img = itemView.findViewById(R.id.imgFood_NguyenDinhhien);
        }
    }
}
