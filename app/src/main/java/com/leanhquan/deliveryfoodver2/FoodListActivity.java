package com.leanhquan.deliveryfoodver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.leanhquan.deliveryfoodver2.Inteface.ItemClickListener;
import com.leanhquan.deliveryfoodver2.Model.Food;
import com.leanhquan.deliveryfoodver2.ViewHolder.FoodViewHolder;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    private String                                          menuId = "";
    private RecyclerView                                    recyclerViewListFood;
    private RecyclerView.LayoutManager                      layoutManagerListFood;
    private FirebaseDatabase                                database;
    private DatabaseReference                               foodList;

    //list food
    private FirebaseRecyclerAdapter<Food, FoodViewHolder>   adapterFoodList;

    //srearch
    FirebaseRecyclerAdapter<Food, FoodViewHolder>           searchAdapter;
    MaterialSearchBar                                       materialSearchBar;
    List<String> suggestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("foods");

        recyclerViewListFood = findViewById(R.id.recycler_food);
        layoutManagerListFood = new LinearLayoutManager(FoodListActivity.this);
        recyclerViewListFood.setLayoutManager(layoutManagerListFood);
        materialSearchBar = findViewById(R.id.searchBar);

        if (getIntent() != null){ menuId = getIntent().getStringExtra("IdCategory");}
        assert menuId != null;
        if (!menuId.isEmpty() && menuId != null){
            loadListFood(menuId);
        }

        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // chage food list when user enter
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList)
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //khi search bar đóng,trở về adapter ban đầu
                if (!enabled)
                    recyclerViewListFood.setAdapter(adapterFoodList);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //khi search hoàn thành,show kết quả của search adapter
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(String text) {
        String query = text.toUpperCase();
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>().
                setQuery(foodList.orderByChild("name").startAt(query).endAt(query+"\uf8ff"), //so sánh tên
                        Food.class).build();
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FoodViewHolder foodViewHolder, final int i, @NonNull final Food model) {
                /*
                TextView textViewName = foodViewHolder.itemView.findViewById(R.id.food_name_detail);
                ImageView imageView = foodViewHolder.itemView.findViewById(R.id.img_food_detail);
                TextView textViewPrice = foodViewHolder.itemView.findViewById(R.id.food_price_detail);
                textViewPrice.setText(String.format("$ %s", model.getPrice()));
                textViewName.setText(model.getName());
                Picasso.with(FoodListActivity.this).load(model.getImage()).resize(600, 600).into(imageView);
                 */
                foodViewHolder.nameFood.setText(model.getName());
                Picasso.with(FoodListActivity.this).load(model.getImage()).into(foodViewHolder.imgFood);

                final Food local = model;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(FoodListActivity.this, FoodDetailsActivity.class);
                        foodDetail.putExtra("FoodId", searchAdapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listfood, parent, false);
                return new FoodViewHolder(view);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerViewListFood.setLayoutManager(gridLayoutManager);
        searchAdapter.startListening();
        recyclerViewListFood.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        foodList.orderByChild("menuId").equalTo(menuId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Food item = postSnapshot.getValue(Food.class);
                    suggestList.add(item.getName());  //add food into suggest list
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
