package com.example.app.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ChatActivity;
import com.example.app.Kind_Product;
import com.example.app.MainActivity;
import com.example.app.ProductDetail;
import com.example.app.R;
import com.example.app.adapter.Category_RecyclerAdapter;
import com.example.app.adapter.Home_AllItem_RecyclerAdapter;
import com.example.app.adapter.ImageSliderAdapter;
import com.example.app.model.AllProductModel;
import com.example.app.model.Cart;
import com.example.app.model.Category;
import com.example.app.model.ImageSliderModel;
import com.example.app.util.CheckConnection;
import com.smarteist.autoimageslider.SliderView;

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


    //timkiem
    EditText editText;
    TextView cate, all;
    CardView cardView;
    ImageView btt_chat;

    ArrayList<AllProductModel> filteredlist;
    RecyclerView categoryRecyclerView,allItemRecyclerView;//recyclerView
    ArrayList<Category> mangloaisanpham;//arrayList
    ArrayList<AllProductModel> mangsanpham;
    Home_AllItem_RecyclerAdapter home_allItem_recyclerAdapter;
    Home_AllItem_RecyclerAdapter.RecyclerViewClickListener listener;
    private Category_RecyclerAdapter.RecyclerViewClickListener listenerCategory;
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

        editText = view.findViewById ( R.id.searchBar );
        cate = view.findViewById ( R.id.categoryTv );
        all = view.findViewById ( R.id.allItemsTv );
        cardView = view.findViewById ( R.id.cardView );
        btt_chat = view.findViewById ( R.id.btt_chat );

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

            JsonFetchCartList jsonFetchCartList = new JsonFetchCartList ();
            jsonFetchCartList.execute (  );

            search();
            setOnClickListener();
            setOnClickListenerCategory ();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Please check the internet connection");
        }

        btt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });

        return view;
    }

    private void search() {
        editText.addTextChangedListener(new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String key_search = editText.getText().toString();

                if (key_search.trim().equals("")) {
                    sliderView.setVisibility(View.VISIBLE);
                    categoryRecyclerView.setVisibility ( View.VISIBLE );
                    cate.setVisibility ( View.VISIBLE );
                    all.setVisibility ( View.VISIBLE );
                    cardView.setVisibility ( View.VISIBLE );
                    // JsonFetchAllProduct jsonFetchAllProduct = new JsonFetchAllProduct ();
                    //jsonFetchAllProduct.execute (  );
                    home_allItem_recyclerAdapter = new Home_AllItem_RecyclerAdapter( mangsanpham,getActivity ().getApplicationContext ( ),listener );

                    allItemRecyclerView.setAdapter ( home_allItem_recyclerAdapter );



                } else {
                    sliderView.setVisibility(View.GONE);
                    categoryRecyclerView.setVisibility ( View.GONE );
                    filter(s.toString());
                    cate.setVisibility ( View.GONE );
                    all.setVisibility ( View.GONE );
                    cardView.setVisibility ( View.GONE );
                    //notice.setVisibility ( View.GONE );


                }
            }
        });

    }

    ///tim kiem
    private void filter(String s) {
        // creating a new array list to filter our data.
        filteredlist = new ArrayList<> (  );

        // running a for loop to compare elements.
        for (AllProductModel item : mangsanpham) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName ().toLowerCase().contains(s.toLowerCase())) {

                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

            allItemRecyclerView.setVisibility ( View.GONE );

            //notice.setVisibility ( View.VISIBLE );
            // allItemRecyclerView.setVisibility ( View.INVISIBLE );
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            allItemRecyclerView.setVisibility ( View.VISIBLE );
            home_allItem_recyclerAdapter.filterList(filteredlist);
           // notice.setVisibility ( View.GONE );
        }


    }



    private void setOnClickListener() {



        listener = (v, position) -> {
            if(filteredlist == null  ) {
                //position = filteredlist.get ( position ).getIdGoods ();
                Intent intent = new Intent ( getActivity ( ).getApplicationContext ( ), ProductDetail.class );
                intent.putExtra ( "information", mangsanpham.get ( position ) );
                startActivity ( intent );
            }else {
                Intent intent = new Intent ( getActivity ( ).getApplicationContext ( ), ProductDetail.class );
                intent.putExtra ( "information", filteredlist.get ( position ) );
                startActivity ( intent );
            }

        };
    }


    public static int i;

    private void setOnClickListenerCategory() {

        listenerCategory = (v, position) -> {

            if (position == 0) {
                i = 0;
                Intent intent = new Intent ( getContext ( ), Kind_Product.class );
                startActivity ( intent );
            } else if (position == 1) {
                i = 1;
                Intent intent = new Intent ( getContext ( ), Kind_Product.class );
                startActivity ( intent );
            } else if (position == 2) {
                i = 2;
                Intent intent = new Intent ( getActivity ( ).getApplicationContext ( ), Kind_Product.class );
                startActivity ( intent );

            } else if (position == 3) {
                i = 3;
                Intent intent = new Intent ( getActivity ( ).getApplicationContext ( ), Kind_Product.class );
                startActivity ( intent );

            } else if (position == 4) {
                i = 4;
                Intent intent = new Intent ( getActivity ( ).getApplicationContext ( ), Kind_Product.class );
                startActivity ( intent );

            } else if (position == 5) {
                i = 5;
                Intent intent = new Intent ( getActivity ( ).getApplicationContext ( ), Kind_Product.class );
                startActivity ( intent );
            }

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

                    mangloaisanpham.add (new Category(image));

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

            Category_RecyclerAdapter categoryRecyclerAdapter = new Category_RecyclerAdapter( mangloaisanpham,getContext(),listenerCategory );

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

            home_allItem_recyclerAdapter = new Home_AllItem_RecyclerAdapter( mangsanpham,getContext(), listener );

            allItemRecyclerView.setAdapter ( home_allItem_recyclerAdapter );


        }
    }

    public class JsonFetchCartList extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getCart.php" );
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

                    JSONObject child = parent.getJSONObject (i);
                    int idGoods = child.getInt ( "idGood" );
                    String name = child.getString ( "name" );
                    int price = child.getInt ( "price" );
                    String weight = child.getString ( "weight" );
                    int quantity = child.getInt ( "quantity" );
                    String image = child.getString ( "image" );
                    String idUser = child.getString ( "idUser" );

                    if(idUser.equals(MainActivity.userId)) {
                        if (MainActivity.CartList.size() > 0) {
                            boolean exists = false;
                            for (int t = 0; t < MainActivity.CartList.size(); t++) {
                                if (MainActivity.CartList.get(t).getIdGoods() == idGoods) {
                                    exists = true;
                                }
                            }
                            if (exists == false) {
                                MainActivity.CartList.add(new Cart(idGoods, name, price, weight, quantity, image, idUser));
                            }
                        } else {
                            MainActivity.CartList.add(new Cart(idGoods, name, price, weight, quantity, image, idUser));
                        }
                    }
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

        protected void onPostExecute(String s) {
            super.onPostExecute ( s );
        }
    }

}





