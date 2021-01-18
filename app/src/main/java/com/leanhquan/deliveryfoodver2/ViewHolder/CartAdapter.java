package com.leanhquan.deliveryfoodver2.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.leanhquan.deliveryfoodver2.Common.Common;
import com.leanhquan.deliveryfoodver2.Inteface.ItemClickListener;
import com.leanhquan.deliveryfoodver2.Model.Order;
import com.leanhquan.deliveryfoodver2.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class cartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView                   txtCartname,txtprice;
    public ElegantNumberButton        btnCount;
    private ItemClickListener         itemClickListener;
    public ImageView                  cartImage;
    public RelativeLayout             view_background;
    public LinearLayout               view_foreground;


    public cartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCartname=(TextView)itemView.findViewById(R.id.cart_item_name);
        txtprice=(TextView)itemView.findViewById(R.id.cart_item_price);
        btnCount=(ElegantNumberButton) itemView.findViewById(R.id.btn_quantity);
        cartImage = (ImageView)itemView.findViewById(R.id.card_image);
        view_background = (RelativeLayout)itemView.findViewById(R.id.view_background);
        view_foreground = (LinearLayout)itemView.findViewById(R.id.view_foreground);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
        menu.add(0,0, getAdapterPosition(), Common.DELETE);
    }
}

public class CartAdapter extends RecyclerView.Adapter<cartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_list_item_cart, parent, false);
        return new cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartViewHolder holder, int position) {

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(""+listData.get(position).getProductCount(), Color.RED);

        holder.cartImage.setImageDrawable(drawable);


        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        int price = (Integer.parseInt(listData.get(position).getProductPrice()))*(Integer.parseInt(listData.get(position).getProductCount())) ;
        holder.txtprice.setText(fmt.format(price));

        holder.txtCartname.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
