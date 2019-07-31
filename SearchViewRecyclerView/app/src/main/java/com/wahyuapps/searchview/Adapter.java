package com.wahyuapps.searchview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter
{

	private Context mContext;
	private LayoutInflater inflater;
	List<Data> data;
	private List<Data> mData;
	private List<Data> mDataAll;
	Data current;
	int currentposition = 0;

	public Adapter(Context mContext, List<Data> data)
	{
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
		this.data = data;
		mData = data;
		mDataAll = new ArrayList<>();
		mDataAll.addAll(mData);
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = inflater.inflate(R.layout.main_item_list_card, parent, false);
		Holder holder = new Holder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		Holder myHolder = (Holder)holder;
		Data item = data.get(position);

		myHolder.title.setText(item.mTitle);
		myHolder.subtitle.setText(item.mDescription);
		Glide.with(mContext).load(item.mImage).into(myHolder.image);
	}

	@Override
	public int getItemCount()
	{
		return data.size();
	}

	class Holder extends RecyclerView.ViewHolder
	{
		TextView title, subtitle;
		ImageView image;

		public Holder(View itemView)
		{
			super(itemView);
			title = itemView.findViewById(R.id.title);
			subtitle = itemView.findViewById(R.id.subtitle);
			image = itemView.findViewById(R.id.image);

			itemView.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v)
					{
						Data item = data.get(getAdapterPosition());
						Context mContext = v.getContext();
						Toast.makeText(mContext, item.mTitle + " Has Clicked", Toast.LENGTH_LONG).show();
					}
				});
		}
	}

	// Search Function
	public void search(String string)
	{
		String query = string.toLowerCase(Locale.getDefault()).trim();
		mData.clear();
		if (query.length() == 0)
		{
			mData.addAll(mDataAll);
		}
		else
		{
			for (int i = 0; i < mDataAll.size(); i++)
			{
				Data data = mDataAll.get(i);
				String title = data.mTitle.toLowerCase(Locale.getDefault());
				if (title.contains(query))
				{
					mData.add(data);
				}
			}
		}
		notifyDataSetChanged();
	}
	// end
}
