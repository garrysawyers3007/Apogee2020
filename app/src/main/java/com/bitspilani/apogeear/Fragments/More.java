package com.bitspilani.apogeear.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitspilani.apogeear.Adapters.MoreAdapter;
import com.bitspilani.apogeear.Adapters.MoreNestedAdapter;
import com.bitspilani.apogeear.Models.MoreModel;
import com.bitspilani.apogeear.Models.MoreNestedModel;
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

    List<MoreNestedModel> list;
    List<MoreModel> list1,list2,list3;
    RecyclerView recyclerView;

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

        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        list1.add(new MoreModel("Edit Character",R.drawable.next,"Change your Avatar"));
        list1.add(new MoreModel("Change Interests",R.drawable.next,"Select new Interests"));
        list2.add(new MoreModel("EPC Blog",R.drawable.next));
        list2.add(new MoreModel("HPC Blog",R.drawable.next));
        list3.add(new MoreModel("Developers",R.drawable.next,"Get to know our developers"));
        list3.add(new MoreModel("Instructions",R.drawable.next));
        list3.add(new MoreModel("About Us",R.drawable.next,"Get in touch with Coding Club !!!"));
        list3.add(new MoreModel("Privacy Policy",R.drawable.next));
        list3.add(new MoreModel("Terms And Conditions",R.drawable.next));
        list3.add(new MoreModel("Terms And Conditions",R.drawable.next));
        list3.add(new MoreModel("Terms And Conditions",R.drawable.next));

        list.add(new MoreNestedModel("My Account",list1));
        list.add(new MoreNestedModel("Notifications",list2));
        list.add(new MoreNestedModel("About",list3));

        recyclerView = view.findViewById(R.id.recycler_more2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MoreNestedAdapter moreNestedAdapter = new MoreNestedAdapter(getActivity(),list);

        recyclerView.setAdapter(moreNestedAdapter);
        // Inflate the layout for this fragment
        return view;
    }

}
