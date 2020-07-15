package com.example.inforpet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    ArrayList<Reviewer> items = new ArrayList<Reviewer>();

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reviewer item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Reviewer item) {
        items.add(item);
    }

    public void setItems(ArrayList<Reviewer> items) {
        this.items = items;
    }

    public Reviewer getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Reviewer item) {
        items.set(position, item);
    }

    public void onItemClick(ReviewAdapter.ViewHolder holder, View view, int position) {
        Reviewer item;
    }
    public float getRatingAvg()
    {
        float ratingAvg = 0.0f;
        for(int i=0; i<items.size(); i++)
        {
            ratingAvg += items.get(i).getRating();
        }
        return ratingAvg/items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_user , textView_date, textView_context;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            textView_user = itemView.findViewById(R.id.recyc_name);
            textView_date = itemView.findViewById(R.id.recyc_date);
            textView_context = itemView.findViewById(R.id.recyc_context);
            ratingBar = itemView.findViewById(R.id.ratingBar);


        }

        public void setItem(Reviewer item) {
            textView_user.setText(item.getName());
            textView_date.setText(item.getDate());
            textView_context.setText(item.getContext());
            ratingBar.setRating(item.getRating());
        }

    }
}
