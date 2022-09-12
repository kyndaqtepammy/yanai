package com.pamsillah.yanai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pamsillah.yanai.R;
import com.pamsillah.yanai.models.ModelFlashcardCategories;

import java.util.List;

public class AdapterFlashCategories  extends RecyclerView.Adapter<AdapterFlashCategories.FlashCatsVH> {
    @NonNull

    private Context mContext;
    private OnFlashcatItemClickListener itemClickListener;
    private List<ModelFlashcardCategories> flashcardCategoriesList;

    public AdapterFlashCategories(@NonNull Context mContext, OnFlashcatItemClickListener itemClickListener, List<ModelFlashcardCategories> flashcardCategoriesList) {
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
        this.flashcardCategoriesList = flashcardCategoriesList;
    }

    public FlashCatsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_flashcats_layout, null);
        return new FlashCatsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashCatsVH holder, int position) {
        final ModelFlashcardCategories modelFlashcardCategories = flashcardCategoriesList.get(position);
        holder.flash_cat_name.setText(modelFlashcardCategories.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return flashcardCategoriesList.size();
    }

    public class FlashCatsVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView flash_cat_name;
        OnFlashcatItemClickListener onFlashcatItemClickListener;

        public FlashCatsVH(@NonNull View itemView) {
            super(itemView);
            flash_cat_name = itemView.findViewById(R.id.flashcatname);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onFlashcatItemClickListener.onFlashcatItemClicked(getAdapterPosition());
        }
    }

    public interface OnFlashcatItemClickListener {
        public void onFlashcatItemClicked(int position);
    }
}
