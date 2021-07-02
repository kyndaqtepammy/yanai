package com.pamsillah.yanai.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamsillah.yanai.R;
import com.pamsillah.yanai.adapters.AdapterBooks;
import com.pamsillah.yanai.models.ModelBooks;
import com.pamsillah.yanai.utils.DatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private List<ModelBooks> myLibraryList;
    private RecyclerView mLibraryRecycler;
    private ModelBooks mModelBooks;
    DatabaseHelper databaseHelper;
    TextView mCount;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        mLibraryRecycler = root.findViewById(R.id.rv_mylibrary);
        mLibraryRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myLibraryList = new ArrayList<>();
        mCount = root.findViewById(R.id.txt_library_count);
        databaseHelper = new DatabaseHelper(getActivity());
        fetchBooksFromLocal();

        return root;
    }

    private void fetchBooksFromLocal() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        //progressDialog.show();

        Cursor data = databaseHelper.booksFromLocal();
        ArrayList<ModelBooks> booksArrayList = new ArrayList<>();
        String filesPath = getActivity().getDir("books", Context.MODE_PRIVATE).getAbsolutePath();

        //Toast.makeText(getActivity(), filesPath, Toast.LENGTH_SHORT).show();
        while (data.moveToNext()) {
            final ModelBooks modelBooks = new ModelBooks(
                data.getString(0),
                data.getString(1),
                data.getString(2),
                data.getString(3),
                data.getString(4),
                data.getString(5),
                data.getString(6),
                data.getString(7)
            );
            booksArrayList.add(modelBooks);
            mCount.setText(booksArrayList.size()+ " books in this list.");
            AdapterBooks adapterBooks = new AdapterBooks(getActivity(), booksArrayList, new AdapterBooks.OnBookItemClickListener() {
                @Override
                public void onBookItemClick(int position) {
                    Toast.makeText(getContext(), ""+modelBooks.getBookPdfUrl(), Toast.LENGTH_SHORT).show();
                }
            });
            mLibraryRecycler.setAdapter(adapterBooks);
            adapterBooks.notifyDataSetChanged();
        }
    }

}