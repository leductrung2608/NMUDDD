package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapter.Cart_ListItem_RecyclerAdapter;
import com.example.app.map.Address;
import com.example.app.map.CurrentLocation;
import com.example.app.map.Map;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


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

    TextView tvAddress, tvPhone, tvUsername;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Spinner spinner;

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

        ImageView ggMap = view.findViewById(R.id.ggMap);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        tvAddress = view.findViewById(R.id.tv_address);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvUsername = view.findViewById(R.id.tv_username);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();

        //gg Map
        ggMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CurrentLocation.class));
                startActivity(new Intent(getActivity(), Map.class));
            }
        });

        //Update info user
        DocumentReference df = fStore.collection("Users").document(userId);
        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


            }
        });

        getBag(recyclerView);

        return view;
    }


    //Get address
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);

    }

    public void onEventMainThread(Address event) {
        tvAddress.setText(event.getAddress());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }



}