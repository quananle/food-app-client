package com.leanhquan.deliveryfoodver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.leanhquan.deliveryfoodver2.Inteface.ItemClickListener;
import com.leanhquan.deliveryfoodver2.Model.Food;
import com.leanhquan.deliveryfoodver2.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

public class FoodListActivity extends AppCompatActivity {

    private String                                          menuId = "";
    private RecyclerView                                    recyclerViewListFood;
    private RecyclerView.LayoutManager                      layoutManagerListFood;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder>   adapterFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recyclerViewListFood = findViewById(R.id.recycler_food);
        layoutManagerListFood = new LinearLayoutManager(FoodListActivity.this);
        recyclerViewListFood.setLayoutManager(layoutManagerListFood);

        if (getIntent() != null){ menuId = getIntent().getStringExtra("IdCategory");}
        assert menuId != null;
        if (!menuId.isEmpty() && menuId != null){
            loadListFood(menuId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapterFoodList != null){
            adapterFoodList.startListening();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapterFoodList != null) {
            adapterFoodList.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapterFoodList != null) {
            adapterFoodList.stopListening();
        }
    }

    private void loadListFood(String menuId){
        Query query = FirebaseDatabase.getInstance().getReference().child("foods").orderByChild("menuId").equalTo(menuId);
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();
        adapterFoodList = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(FoodListActivity.this).inflate(R.layout.layout_listfood,parent, false);
                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                Picasso.with(FoodListActivity.this).load(model.getImage()).centerCrop().fit().into(holder.imgFood);
                holder.nameFood.setText(model.getName());
                final Food food = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean longClick) {
                        String idFood = adapterFoodList.getRef(position).getKey();
                        Intent foodDetails = new Intent(FoodListActivity.this, FoodDetailsActivity.class);
                        foodDetails.putExtra("FoodId", idFood);
                        startActivity(foodDetails);
                    }
                });
            }


        };
        recyclerViewListFood.setAdapter(adapterFoodList);
    }
}
