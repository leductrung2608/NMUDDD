package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapter.Category_RecyclerAdapter;
import com.example.app.adapter.ImageSliderAdapter;
import com.example.app.adapter.Home_AllItem_RecyclerAdapter;
import com.example.app.model.Category;
import com.example.app.model.ImageSliderModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    SliderView sliderView;
    List<ImageSliderModel> imageSliderModelList;

    Category_RecyclerAdapter categoryAdapter;
    List<Category> categoryList;

    RecyclerView categoryRecyclerView;

    //var discountitem
    private ArrayList<String> nSaleName = new ArrayList<>();
    private ArrayList<Integer> nSaleImage = new ArrayList<>();
    private ArrayList<String> nOldPrice = new ArrayList<>();
    private ArrayList<String> nSalePrice = new ArrayList<>();

    public HomeFragment() { }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setSliderView()
    {
        imageSliderModelList = new ArrayList<>();
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n0));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n1));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n2));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n3));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n4));
        sliderView.setSliderAdapter(new ImageSliderAdapter(getActivity(),imageSliderModelList));
        sliderView.startAutoCycle();
    }

    private void setCategoryRecycler(List<Category> categoryDataList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new Category_RecyclerAdapter(getActivity(),categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void getImagesDiscount(RecyclerView recyclerView){
        //Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
        nSaleName.add("Son MAC #314 Mull It Over");
        nSaleImage.add(R.drawable.p1);
        nOldPrice.add("$80.00");
        nSalePrice.add("$60.00");


        nSaleName.add("NARS Eyeshadow Palette");
        nSaleImage.add(R.drawable.n2);
        nOldPrice.add("$33.00");
        nSalePrice.add("$20.00");

        nSaleName.add("Warrior Princess Mascara");
        nSaleImage.add(R.drawable.n3);
        nOldPrice.add("$10.00");
        nSalePrice.add("$6.00");

        initRecyclerViewDiscount(recyclerView);
    }

    private void initRecyclerViewDiscount(RecyclerView recyclerView)
    {
        //  Log.d(TAG, "initRecyclerView: init recyclerview");

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        // ArrayList<Integer> mImageUrls;
        Home_AllItem_RecyclerAdapter adapter = new Home_AllItem_RecyclerAdapter(getActivity(), nSaleName, nSaleImage,nOldPrice,nSalePrice);
        recyclerView.setAdapter(adapter);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = view.findViewById(R.id.imageSlider);
        categoryRecyclerView = view.findViewById(R.id.categoryRecycler);
        RecyclerView recyclerViewDiscount = view.findViewById(R.id.recycleViewDiscount);

        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.ic_son ));
        categoryList.add(new Category(2,  R.drawable.ic_mahong));
        categoryList.add(new Category(2,  R.drawable.ic_mas));
        categoryList.add(new Category(2,  R.drawable.ic_mat));
        categoryList.add(new Category(2,  R.drawable.ic_phan));
        categoryList.add(new Category(2,  R.drawable.ic_kemnen));

        setSliderView();
        setCategoryRecycler(categoryList);
        getImagesDiscount(recyclerViewDiscount);

        return view;
    }



}