package com.laioffer.myapplication;



import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.laioffer.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {
    GridView gridView;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        gridView = (GridView) view.findViewById(R.id.comment_grid);
        // do logic manipulations to the xml
        gridView.setAdapter(new EventAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallbackInGrid.onItemSelectedInGrid(i);
            }
        });

        return view;
    }

    public void onItemSelected(int position){
        for (int i = 0; i < gridView.getChildCount(); i++){
            if (position == i) {
                gridView.getChildAt(i).setBackgroundColor(Color.BLUE);
            } else {
                gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#EEEEEE"));
            }
        }
    }

    OnItemSelectListenerInGrid mCallbackInGrid;

    // Container Activity must implement this interface
    public interface OnItemSelectListenerInGrid {
        public void onItemSelectedInGrid(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbackInGrid = (CommentFragment.OnItemSelectListenerInGrid) context;
        } catch (ClassCastException e) {
            //do something
        }
    }



}
