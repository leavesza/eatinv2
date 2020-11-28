package com.eatin.eatinv2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eatin.eatinv2.Callback.IRecyclerClickListener;
import com.eatin.eatinv2.EventBus.PopularCategoryClick;
import com.eatin.eatinv2.Model.PopularCategoryModel;
import com.eatin.eatinv2.R;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyPopularCategoriesAdapter extends RecyclerView.Adapter<MyPopularCategoriesAdapter.MyViewHolder > {

    Context context;
    List<PopularCategoryModel> popularCategoryModelList;

    public MyPopularCategoriesAdapter(Context context, List<PopularCategoryModel> popularCategoryModelList) {
        this.context = context;
        this.popularCategoryModelList = popularCategoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_popular_categories_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(popularCategoryModelList.get(position))
                .into(holder.category_image);
        holder.txt_category_name.setText(popularCategoryModelList.get(position).getName());

        holder.setListener((view, pos) ->
                EventBus.getDefault().postSticky(new PopularCategoryClick(popularCategoryModelList.get(pos))));
    }

    @Override
    public int getItemCount() {
        return popularCategoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Unbinder unbinder;

        @BindView(R.id.txt_category_name)
        TextView txt_category_name;
        @BindView(R.id.category_image)
        CircleImageView category_image;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            listener.onItemClickListener(view,getAdapterPosition());
        }
    }
}
