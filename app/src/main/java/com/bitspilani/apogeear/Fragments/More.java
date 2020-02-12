package com.bitspilani.apogeear.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitspilani.apogeear.Adapters.MoreAdapter;
import com.bitspilani.apogeear.Models.MoreModel;
import com.bitspilani.apogeear.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.List;

public class More extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<MoreModel> list,list1,list2;
    RecyclerView recyclerView,recyclerView1,recyclerView2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public More() {
        // Required empty public constructor
    }

    public static More newInstance(String param1, String param2) {
        More fragment = new More();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more,container,false);

        recyclerView = view.findViewById(R.id.recycler_more);
        recyclerView1 = view.findViewById(R.id.recycler_more1);
        recyclerView2 = view.findViewById(R.id.recycler_more2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

      //  recyclerView.setLayoutManager(new CardSliderLayoutManager(getActivity()));
      //    new CardSnapHelper().attachToRecyclerView(recyclerView);

        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list.add(new MoreModel("Edit Character",R.drawable.next,"Change your Avatar"));
        list.add(new MoreModel("Change Interests",R.drawable.next,"Select new Interests"));
        list1.add(new MoreModel("EPC Blog",R.drawable.next));
        list1.add(new MoreModel("HPC Blog",R.drawable.next));
        list2.add(new MoreModel("Developers",R.drawable.next,"Get to know our developers"));
        list2.add(new MoreModel("Instructions",R.drawable.next));
        list2.add(new MoreModel("About Us",R.drawable.next,"Get in touch with Coding Club !!!"));
        list2.add(new MoreModel("Privacy Policy",R.drawable.next));
        list2.add(new MoreModel("Terms And Conditions",R.drawable.next));

        MoreAdapter moreAdapter = new MoreAdapter(getActivity(),list);
        MoreAdapter moreAdapter1 = new MoreAdapter(getActivity(),list1);
        MoreAdapter moreAdapter2 = new MoreAdapter(getActivity(),list2);
        recyclerView.setAdapter(moreAdapter);
        recyclerView1.setAdapter(moreAdapter1);
        recyclerView2.setAdapter(moreAdapter2);

        // Inflate the layout for this fragment
        return view;
    }

}
