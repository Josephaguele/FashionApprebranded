package com.chupaj.android.fashionhome;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.CursorLoader;
import android.content.Loader;

import com.chupaj.android.fashionhome.data.ClientContract;
import com.chupaj.android.fashionhome.data.MySearchResultRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;


import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.ADVANCE;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.AMOUNT;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_ANKLE;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_BELLY;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_BLOUSE_LENGTH;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CAFTAN_LENGTH;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CALF;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CHEST;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_ADDRESS;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_BOSS;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_GENDER;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NAME;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NUMBER;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_NUMBER2;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_STYLE;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_CLIENT_WAIST;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_DATE;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_EMAIL;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_FEMALELONGSLEEVE;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_FEMALESHORTSLEEVE;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_FEMALE_SHOULDER;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_HIP;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_MALE_LS;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_MALE_SHOULDER;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_MALE_SS;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_NECK;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_SKIRT_LENGTH;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_THIGH;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_TOP;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_TOP_LENGTH;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_TROUSER_LENGTH;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry.COLUMN_WAIST;
import static com.chupaj.android.fashionhome.data.ClientContract.ClientEntry._ID;

/**
 * Created by AGUELE OSEKUEMEN JOE on 10/7/2017.
 */


public class SearchResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static String QUERY_EXTRA_KEY = "QUERY_EXTRA_KEY";
    private MySearchResultRecyclerViewAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_search);

        // create a new adapter and bind it to the List View
        mAdapter = new MySearchResultRecyclerViewAdapter(null, mListener);

        // Update the Recycler View
        RecyclerView resultsRecyclerView = findViewById(R.id.rlist);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsRecyclerView.setAdapter(mAdapter);

        //putting up divider lines with colour blue for recycler list
        DividerItemDecoration itemDecoration = new DividerItemDecoration(resultsRecyclerView.getContext(),
               LinearLayoutManager.HORIZONTAL);
        resultsRecyclerView.addItemDecoration(itemDecoration);
        //itemDecoration.setDrawable(new ColorDrawable(getResources().getColor(R.color.editorColorPrimary2)));

        // Initiate the Cursor Loader
        getLoaderManager().initLoader(0, null, this);
    }


    //Searches initiated from within the search results Activity will result in new Intents being
    //received — you can capture those Intents and extract the new queries from the onNewIntent handler
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // If the search Activity exists, and another search
        // is performed, set the launch Intent to the newly
        // received search Intent and perform a new search.
        setIntent(intent);
        getLoaderManager().restartLoader(0, null, this);

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        // Extract the search query from the Intent.
        String query = getIntent().getStringExtra(SearchManager.QUERY);

        // Construct the new query in the form of a Cursor Loader.
        String[] projection = {
                _ID,
                COLUMN_CLIENT_NAME,
                COLUMN_CLIENT_ADDRESS,
                COLUMN_CLIENT_STYLE,
                COLUMN_CLIENT_GENDER,
                COLUMN_CLIENT_NUMBER,
                COLUMN_CLIENT_NUMBER2,
                COLUMN_EMAIL,
                COLUMN_CLIENT_BOSS,
                COLUMN_CLIENT_WAIST,
                COLUMN_HIP,
                COLUMN_FEMALE_SHOULDER,
                COLUMN_FEMALESHORTSLEEVE,
                COLUMN_FEMALELONGSLEEVE,
                COLUMN_TOP,
                COLUMN_BLOUSE_LENGTH,
                COLUMN_SKIRT_LENGTH,
                COLUMN_CAFTAN_LENGTH,
                COLUMN_TOP_LENGTH,
                COLUMN_NECK,
                COLUMN_MALE_SHOULDER,
                COLUMN_MALE_SS,
                COLUMN_MALE_LS,
                COLUMN_CHEST,
                COLUMN_BELLY,
                COLUMN_TROUSER_LENGTH,
                COLUMN_THIGH,
                COLUMN_WAIST,
                COLUMN_CALF,
                COLUMN_ANKLE,
                COLUMN_DATE,
                AMOUNT,
                ADVANCE
        };


        String where = ClientContract.ClientEntry.COLUMN_CLIENT_NAME +
                        " LIKE ?";
        String[]whereArgs = {"%" + query + "%"};
        String sortOrder = COLUMN_CLIENT_NAME ;

        // Create new Cursor Loader
        return new CursorLoader(this, ClientContract.ClientEntry.CONTENT_URI,
                projection, where, whereArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    { // Replace the result Cursor displayed by the Cursor Adapter with
        // the new result set.
        mAdapter.setCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        // Remove the existing result Cursor from the List Adapter.
        mAdapter.setCursor(null);

    }

    private MySearchResultRecyclerViewAdapter.OnListItemInteractionListener mListener =
            new MySearchResultRecyclerViewAdapter.OnListItemInteractionListener() {
                @Override
                public void onListItemClick(Uri selectedContent)
                {
                    // TODO If an item is clicked, open an Activity
                    // to display further details.



                }
            };



}



// Providing actions for search results
    /*@Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        // Create a URI to the selected item.
        Uri selectedUri =
                ContentUris.withAppendedId(ClientContract.ClientEntry.CONTENT_URI, id);
        // Create an Intent to view the selected item.
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(selectedUri);
        // Start an Activity to view the selected item.
        startActivity(intent);
    }
*/

