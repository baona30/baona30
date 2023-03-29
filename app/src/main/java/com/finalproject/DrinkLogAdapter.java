package com.finalproject;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.databinding.FragmentLogItemBinding;

import java.util.List;

public class DrinkLogAdapter extends RecyclerView.Adapter<DrinkLogAdapter.ViewHolder> {

    private final List<DrinkLog> mValues;
    private final ClickItem clickItem;
    public DrinkLogAdapter(List<DrinkLog> items, ClickItem clickItem) {
        mValues = items;
        this.clickItem=clickItem;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentLogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DrinkLog drinkLog = mValues.get(position);
        // Set the image as the launcher icon of Android
        holder.binding.icon.setImageResource(drinkLog.imageId);
        holder.binding.name.setText(drinkLog.type);
        holder.binding.amount.setText(String.valueOf(drinkLog.amount));
        holder.binding.unit.setText(drinkLog.unit);
        holder.binding.time.setText(drinkLog.time);
        // Get the current data from the arraylist based on the position
        //       String current = mValues.get(position);
        //       holder.binding.content.setText(current);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected final FragmentLogItemBinding binding;
        public ViewHolder(@NonNull FragmentLogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener
                    (view -> clickItem.onClick(getBindingAdapterPosition()));
        }
    }
    public interface ClickItem{
        void onClick(int position);
    }

}