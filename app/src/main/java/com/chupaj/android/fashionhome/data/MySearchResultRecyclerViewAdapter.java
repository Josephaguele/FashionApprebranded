package com.chupaj.android.fashionhome.data;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chupaj.android.fashionhome.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_ADDRESS;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NAME;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.CONTENT_URI;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry._ID;

public class MySearchResultRecyclerViewAdapter
        extends RecyclerView.Adapter<MySearchResultRecyclerViewAdapter.ViewHolder> {

    private Cursor mValues;
    private OnListItemInteractionListener mClickListener;

    private int mHoardIdIndex = -1;
    private int mHoardNameIndex = -1;
    private int mHoardAmountIndex = -1;

    public MySearchResultRecyclerViewAdapter(Cursor items,
                                             OnListItemInteractionListener clickListener) {
        mValues = items;
        mClickListener = clickListener;
    }

    public void setCursor(Cursor items) {
        mValues = items;
        if (items != null) {
            mHoardIdIndex =
                    items.getColumnIndex(_ID);

            mHoardNameIndex =
                    items.getColumnIndex(
                            COLUMN_CLIENT_NAME);

            mHoardAmountIndex =
                    items.getColumnIndex(
                            COLUMN_CLIENT_ADDRESS);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchresult_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (mValues != null) {
            // Move the Cursor to the correct position, extract the
            // search result values, and assign them to the UI for
            // each search result.
            mValues.moveToPosition(position);

            holder.mNameView.setText(mValues.getString(mHoardNameIndex));
            holder.mAmountView.setText(mValues.getString(mHoardAmountIndex));

            // Create a Uri that points to this search result item.
            int rowId = mValues.getInt(mHoardIdIndex);
            final Uri rowAddress =
                    ContentUris.withAppendedId(CONTENT_URI, rowId);

            // Return the Uri to this search result item if clicked.
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onListItemClick(rowAddress);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.getCount();
        else
            return 0;
    }

    // View Holder is used as a template to encapsulate the UI
    // for each search result item.
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mAmountView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.id);
            mAmountView = view.findViewById(R.id.content);
        }
    }

    // Interface used to encapsulate the behavior when a user
// clicks on a search result item.
    public interface OnListItemInteractionListener {
        void onListItemClick(Uri selectedContent);
    }
}
