/**
 ******************************************************************************
 * @file       HomePage.java
 * @author     AboveGroundLabs, http://abovegroundlabs.org, Copyright (C) 2012
 * @brief      Main launch page for the Android GCS activities
 * @see        The GNU Public License (GPL) Version 3
 *****************************************************************************/
/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.abovegroundlabs.androidgcs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This activity provides a selector to launch all the
 * main activities
 */
public class HomePage extends ObjectManagerActivity {
	private ImageAdapter adapt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gcs_home);

		adapt = new ImageAdapter(this);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapt);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
				startActivity(new Intent(HomePage.this, adapt.getActivity(position)));
			}
		});
	}

	//! Class that shows an icon with a label below
	public class LabeledImageView extends LinearLayout {
		final int WIDTH = 250;
		final int HEIGHT = 220;

		public LabeledImageView(Context context, int image, String text) {
			super(context);

			setOrientation(LinearLayout.VERTICAL);
			setLayoutParams(new AbsListView.LayoutParams(WIDTH, HEIGHT));

			// Show the icon for this activity
			ImageView imageView = new ImageView(context);
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setPadding(8, 8, 8, 8);
			imageView.setImageResource(image);
			addView(imageView);

			// Show a label for it
			TextView textView = new TextView(context);
			textView.setText(text);
			textView.setWidth(WIDTH);
			textView.setGravity(Gravity.CENTER);
			addView(textView);
		}
	}

	//! Map from a list of activities and icons to their views for display
	public class ImageAdapter extends BaseAdapter {
		private final Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LabeledImageView labeledImageView;

			if (convertView == null) {
				labeledImageView = new LabeledImageView(mContext, mThumbIds[position], names[position]);
			} else {
				labeledImageView = (LabeledImageView) convertView;
			}

			return labeledImageView;
		}

		@SuppressWarnings("rawtypes")
		public Class getActivity(int position) {
			return mActivities[position];
		}

		// references to our images
		private final Integer[] mThumbIds = {
				R.drawable.ic_browser, R.drawable.ic_pfd,
				R.drawable.ic_map, R.drawable.ic_controller,
				R.drawable.ic_logging, R.drawable.ic_alarms,
				R.drawable.ic_tabletcontrol, R.drawable.ic_tuning,
				R.drawable.ic_map, R.drawable.ic_3dview
		};

		@SuppressWarnings("rawtypes")
		private final Class[] mActivities = {
			ObjectBrowser.class, PfdActivity.class,
			UAVLocation.class, Controller.class,
			Logger.class, SystemAlarmActivity.class,
			Transmitter.class, TuningActivity.class,
			PathPlanner.class, OsgViewer.class
		};

		private final String[] names = {
				"Object Browser", "PFD",
				"Map", "Controller",
				"Logger", "Alarms",
				"Tablet Control", "Tuning",
				"Path Planning", "OSG"
		};
	}

}
