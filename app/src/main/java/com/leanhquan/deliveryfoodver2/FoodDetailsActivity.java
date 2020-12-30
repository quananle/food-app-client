package com.leanhquan.deliveryfoodver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leanhquan.deliveryfoodver2.Database.Database;
import com.leanhquan.deliveryfoodver2.Model.Food;
import com.leanhquan.deliveryfoodver2.Model.Order;
import com.leanhquan.deliveryfoodver2.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

public class FoodDetailsActivity extends AppCompatActivity {
    private TextView                         foodName, foodPrice, foodDecription;
    private ImageView                        foodImage;
    private CollapsingToolbarLayout          collapsingToolbarLayout;
    private FloatingActionButton             btnCart;
    private ElegantNumberButton              numberButton;
    private FirebaseDatabase                 firebaseDatabase;
    private DatabaseReference                food;
    private  Food                            currenFood;

    private String foodId ="";
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapterFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        init();

        firebaseDatabase = FirebaseDatabase.getInstance();
        food = firebaseDatabase.getReference("foods");

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addTocart(new Order(
                        foodId,
                        currenFood.getName(),
                        numberButton.getNumber(),
                        currenFood.getPrice(),
                        currenFood.getDiscount()
                ));
                Toast.makeText(FoodDetailsActivity.this, "Add to cart" +currenFood.getName(), Toast.LENGTH_SHORT).show();

            }
        });

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapedAppbar);

        if (getIntent() != null){ foodId = getIntent().getStringExtra("FoodId");}
        assert foodId != null;
        if (!foodId.isEmpty() && foodId != null){
            loadDetailsFood();
        }

    }

    private void loadDetailsFood() {
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currenFood = dataSnapshot.getValue(Food.class);
                Picasso.with(FoodDetailsActivity.this).load(currenFood.getImage()).centerCrop().fit().into(foodImage);
                foodName.setText(currenFood.getName());
                foodPrice.setText(currenFood.getPrice());
                foodDecription.setText(currenFood.getDescription());
                collapsingToolbarLayout.setTitle(currenFood.getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        foodDecription = findViewById(R.id.food_description);
        foodName = findViewById(R.id.food_name);
        foodPrice = findViewById(R.id.food_price);
        foodImage = findViewById(R.id.img_food);
        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
    }
}
