package com.pamsillah.yanai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.models.ModelBooks;

import java.util.List;

import static com.pamsillah.yanai.config.Config.NODE_IMG_URL;

public class AdapterBooksLocal extends RecyclerView.Adapter<AdapterBooksLocal.BooksVH> {
    private Context mContext;
    private AdapterBooksLocal.OnBookLocalClickListener itemClickListener;
    private List<ModelBooks> modelBooksList;

    public AdapterBooksLocal(Context mContext, List<ModelBooks> modelBooksList, AdapterBooksLocal.OnBookLocalClickListener bookItemClickListener) {
        this.mContext = mContext;
        this.modelBooksList = modelBooksList;
        this.itemClickListener = bookItemClickListener;
    }

    @NonNull
    @Override
    public AdapterBooksLocal.BooksVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_book_details, null);
        return new BooksVH(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBooksLocal.BooksVH holder, int position) {
        final ModelBooks modelBooks = modelBooksList.get(position);
        //binding data with the views
        holder.mBookTitle.setText(modelBooks.getBookTitle());
        holder.mBookAuthor.setText(modelBooks.getBookAuthor());
        Glide.with(mContext)
                .load(NODE_IMG_URL+modelBooks.getBookCoverUrl())
                .into(holder.mBookImage);

        ViewCompat.setTransitionName(holder.mBookImage, modelBooks.getBookID()); //the view to be animated plus uniqid


    }

    @Override
    public int getItemCount() {
        return modelBooksList.size();
    }

    public interface OnBookLocalClickListener {
        public void onBookLocalItemClick(int position);
    }

    public class BooksVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mBookTitle, mBookAuthor;
        ImageView mBookImage;
        AdapterBooksLocal.OnBookLocalClickListener OnBookLocalClickListener;

        public BooksVH(@NonNull View itemView, OnBookLocalClickListener mBookItemClickListener) {
            super(itemView);
            mBookTitle = itemView.findViewById(R.id.txt_book_title);
            mBookAuthor = itemView.findViewById(R.id.txt_book_author);
            mBookImage = itemView.findViewById(R.id.book_cover_img);
            this.OnBookLocalClickListener = mBookItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnBookLocalClickListener.onBookLocalItemClick(getAdapterPosition());
        }

     
    }
}

