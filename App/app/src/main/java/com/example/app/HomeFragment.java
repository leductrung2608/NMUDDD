package com.example.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapter.Category_RecyclerAdapter;
import com.example.app.adapter.Home_AllItem_RecyclerAdapter;
import com.example.app.adapter.ImageSliderAdapter;
import com.example.app.model.AllProductModel;
import com.example.app.model.Category;
import com.example.app.model.ImageSliderModel;
import com.example.app.util.CheckConnection;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    SliderView sliderView;
    List<ImageSliderModel> imageSliderModelList;

    RecyclerView categoryRecyclerView,allItemRecyclerView;//recyclerView
    ArrayList<Category> mangloaisanpham;//arrayList
    ArrayList<AllProductModel> mangsanpham;
    Home_AllItem_RecyclerAdapter home_allItem_recyclerAdapter;
    Home_AllItem_RecyclerAdapter.RecyclerViewClickListener listener;

    public HomeFragment() { }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setSliderView() {
        imageSliderModelList = new ArrayList<>();
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n0));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n1));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n2));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n3));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.n4));
        sliderView.setSliderAdapter(new ImageSliderAdapter(getActivity(),imageSliderModelList));
        sliderView.startAutoCycle();
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

        View view = inflater.inflate ( R.layout.fragment_home, container, false );

        sliderView = view.findViewById ( R.id.imageSlider );

        categoryRecyclerView = view.findViewById ( R.id.categoryRecycler );
        categoryRecyclerView.setHasFixedSize ( true );
        categoryRecyclerView.setLayoutManager ( new LinearLayoutManager ( getApplicationContext (),LinearLayoutManager.HORIZONTAL,false ) );

        mangloaisanpham = new ArrayList<Category> ( );

        allItemRecyclerView = view.findViewById ( R.id.recycleViewDiscount );
        allItemRecyclerView.setHasFixedSize ( true );
        allItemRecyclerView.setLayoutManager ( new GridLayoutManager ( getApplicationContext (),2 ) );

        mangsanpham = new ArrayList<AllProductModel> (  );
        home_allItem_recyclerAdapter = new Home_AllItem_RecyclerAdapter(mangsanpham, getApplicationContext(), listener);
        allItemRecyclerView.setAdapter(home_allItem_recyclerAdapter);

        setSliderView();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            JsonFetch jsonFetch = new JsonFetch ( );
            jsonFetch.execute ( );

            JsonFetchAllProduct jsonFetchAllProduct = new JsonFetchAllProduct ();
            jsonFetchAllProduct.execute (  );

            setOnClickListener();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Please check the internet connection");
        }


        return view;
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
            intent.putExtra("id", mangsanpham.get(position).getIdGoods() );
            intent.putExtra("name", mangsanpham.get(position).getName());
            intent.putExtra("price", mangsanpham.get(position).getCurrentPrice());
            intent.putExtra("status", mangsanpham.get(position).getStatus());
            intent.putExtra("weight", mangsanpham.get(position).getWeight());
            intent.putExtra("description", mangsanpham.get(position).getDescription());
            intent.putExtra("image", mangsanpham.get(position).getImage());
            intent.putExtra("kind", mangsanpham.get(position).getKind());
            startActivity(intent);
        };
    }

    ////////////////////////////////////////////////////////////////////////////////////////////JSON/////////
    public class JsonFetch extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getKind.php" );
                httpURLConnection = (HttpURLConnection) url.openConnection ( );
                httpURLConnection.connect ( );

                InputStream inputStream = httpURLConnection.getInputStream ( );
                BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader ( inputStream ) );

                StringBuffer stringBuffer = new StringBuffer ( );
                String line = "";
                while ((line = bufferedReader.readLine ( )) != null) {

                    stringBuffer.append ( line );

                }

                mainfile = stringBuffer.toString ( );

                JSONArray parent = new JSONArray ( mainfile );
                int i = 0;
                while (i <= parent.length ( )) {

                    JSONObject child = parent.getJSONObject ( i );
                    int id = child.getInt ( "id" );
                    String image = child.getString ( "image" );

                    mangloaisanpham.add ( new Category ( image ) );

                    i++;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            } catch (JSONException e) {
                e.printStackTrace ( );
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );

            Category_RecyclerAdapter categoryRecyclerAdapter = new Category_RecyclerAdapter( mangloaisanpham,getApplicationContext ( ) );

            categoryRecyclerView.setAdapter ( categoryRecyclerAdapter );


        }
    }


    public class JsonFetchAllProduct extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getGoods.php" );
                httpURLConnection = (HttpURLConnection) url.openConnection ( );
                httpURLConnection.connect ( );

                InputStream inputStream = httpURLConnection.getInputStream ( );
                BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader ( inputStream ) );

                StringBuffer stringBuffer = new StringBuffer ( );
                String line = "";
                while ((line = bufferedReader.readLine ( )) != null) {

                    stringBuffer.append ( line );

                }

                mainfile = stringBuffer.toString ( );

                JSONArray parent = new JSONArray ( mainfile );
                int i = 0;
                while (i <= parent.length ( )) {

                    JSONObject child = parent.getJSONObject ( i );
                    int id = child.getInt ( "id" );
                    String name = child.getString ( "name" );
                    int price = child.getInt ( "currentPrice" );
                    int status = child.getInt ( "status" );
                    String weight = child.getString ( "weight" );
                    String description = child.getString ( "description" );
                    String image = child.getString ( "image" );
                    int kind = child.getInt ( "idKind" );

                    mangsanpham.add ( new AllProductModel ( id,name,price,status,weight,description,image,kind ) );

                    i++;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            } catch (JSONException e) {
                e.printStackTrace ( );
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );

            Home_AllItem_RecyclerAdapter homeAllItemRecyclerAdapter = new Home_AllItem_RecyclerAdapter( mangsanpham,getApplicationContext(), listener );

            allItemRecyclerView.setAdapter ( homeAllItemRecyclerAdapter );


        }
    }

}





