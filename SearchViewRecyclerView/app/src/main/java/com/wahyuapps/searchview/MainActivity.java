package com.wahyuapps.searchview;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import android.view.inputmethod.EditorInfo;

public class MainActivity extends AppCompatActivity
{

	private RecyclerView mRecyclerView;
	private Adapter mAdapter;
	private SearchView mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mRecyclerView = findViewById(R.id.recycler_view);
		setupRecyclerView();
    }

	public void setupRecyclerView()
	{
		List<Data> data = new ArrayList<>();
		String[] titles;
		int[] images;

		titles = getResources().getStringArray(R.array.item_title);
		TypedArray a = getResources().obtainTypedArray(R.array.item_image);
		images = new int[a.length()];

		for (int i = 0; i < titles.length; i++)
		{
			Data item_data = new Data();
			images[i] = a.getResourceId(i, 0);

			item_data.mTitle = titles[i];
			item_data.mDescription = getResources().getString(R.string.dummy_text);
			item_data.mImage = images[i];
			data.add(item_data);
		}
		mAdapter = new Adapter(this, data);

		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_search, menu);
		MenuItem search = menu.findItem(R.id.menu_search);
		mSearch = (SearchView)MenuItemCompat.getActionView(search);
		mSearch.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		mSearch.setQueryHint("Search");
		mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
				@Override
				public boolean onQueryTextSubmit(String string)
				{
					return true;
				}
				@Override
				public boolean onQueryTextChange(String string)
				{
					filterSearch(string);
					return true;
				}
			});
		return super.onCreateOptionsMenu(menu);
	}

	private void filterSearch(String query)
	{
		try
		{
			mAdapter.search(query);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
