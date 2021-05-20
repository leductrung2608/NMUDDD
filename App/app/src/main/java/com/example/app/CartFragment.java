package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapter.Cart_ListItem_RecyclerAdapter;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private static final String TAG = "ShoppingBag";
    private ArrayList<Integer> mImages = new ArrayList<Integer>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mOldPrice = new ArrayList<>();
    private ArrayList<String> mKind = new ArrayList<>();
    private ArrayList<String> mQuantity = new ArrayList<>();


    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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

    private void getBag(RecyclerView recyclerView){

        mImages.add(R.drawable.n0);
        mNames.add("Missha Cushion");
        mPrice.add("300 000 vnd");
        mOldPrice.add("350 000 vnd");
        mKind.add(null);
        mQuantity.add("1");


        mImages.add(R.drawable.n1);
        mNames.add("MAC lipstick");
        mPrice.add("600 000 vnd");
        mOldPrice.add("650 000 vnd");
        mKind.add("#414");
        mQuantity.add("2");

        mImages.add(R.drawable.n2);
        mNames.add("Mascara");
        mPrice.add("200 000 vnd");
        mOldPrice.add("230 000 vnd");
        mKind.add("Long");
        mQuantity.add("3");

        mImages.add(R.drawable.n3);
        mNames.add("Cushion");
        mPrice.add("270 000 vnd");
        mOldPrice.add("300 000 vnd");
        mKind.add("Natural");
        mQuantity.add("4");

        initRecyclerView(recyclerView);
    }

    private void initRecyclerView(RecyclerView recyclerView){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Cart_ListItem_RecyclerAdapter adapter = new Cart_ListItem_RecyclerAdapter(getActivity(), mImages, mNames, mPrice, mOldPrice, mQuantity, mKind);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        getBag(recyclerView);

        return view;
    }
}