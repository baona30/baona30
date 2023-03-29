package com.finalproject;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.finalproject.databinding.FragmentDrinkItemBinding;
import java.util.List;

public class DrinkItemAdapter extends RecyclerView.Adapter<DrinkItemAdapter.ViewHolder> {

    private final List<DrinkItem> mValues;
    private final ClickItem clickItem;
    public DrinkItemAdapter(List<DrinkItem> items, ClickItem clickItem) {
        mValues = items;
        this.clickItem=clickItem;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentDrinkItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DrinkItem drinkItem = mValues.get(position);
        // Set the image as the launcher icon of Android
        holder.binding.icon.setImageResource(drinkItem.imageId);
        holder.binding.name.setText(drinkItem.type);
        holder.binding.amount.setText(String.valueOf(drinkItem.amount));
        holder.binding.unit.setText(drinkItem.unit);
        holder.binding.time.setText(drinkItem.time);
        // Get the current data from the arraylist based on the position
 //       String current = mValues.get(position);
 //       holder.binding.content.setText(current);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected final FragmentDrinkItemBinding binding;
        public ViewHolder(FragmentDrinkItemBinding binding) {
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