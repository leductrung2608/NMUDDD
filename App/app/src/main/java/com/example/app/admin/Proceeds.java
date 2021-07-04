package com.example.app.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.app.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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

public class Proceeds extends AppCompatActivity {
    int[] a  = new int[20];
    BarChart barChart;
    ArrayList<BarEntry> barEntries = new ArrayList<> (  );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_proceeds );

        barChart = findViewById ( R.id.barChart );

        JsonProceed jsonProceed = new JsonProceed ();
        jsonProceed.execute (  );
    }

    public class JsonProceed extends AsyncTask<String, String, String> {
        HttpURLConnection httpURLConnection = null;
        String mainfile;

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL ( "https://ibeautycosmetic.000webhostapp.com/getProceeds.php" );
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
                while (i <= parent.length ( ) ) {


                    JSONObject child = parent.getJSONObject ( i );

                    int proceed = child.getInt ( "proceed" );

                    a[i] = proceed;
                    //Toast.makeText ( getApplicationContext (),a[i]+"",Toast.LENGTH_LONG ).show ();
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
            //Toast.makeText ( getApplicationContext (), a[1]+"", Toast.LENGTH_LONG ).show ();
            bar(a);

        }
    }


    private void bar(int[]a){



        barEntries.add ( new BarEntry (a[0],0  ) );
        barEntries.add ( new BarEntry (a[1],1  ) );
        barEntries.add ( new BarEntry (a[2],2  ) );
        barEntries.add ( new BarEntry (a[3],3  ) );
        barEntries.add ( new BarEntry (a[4],4  ) );
        barEntries.add ( new BarEntry (a[5],5  ) );
        barEntries.add ( new BarEntry (a[6],6  ) );
        barEntries.add ( new BarEntry (a[7],7  ) );
        barEntries.add ( new BarEntry (a[8],8  ) );
        barEntries.add ( new BarEntry (a[9],9  ) );
        barEntries.add ( new BarEntry (a[10],10  ) );
        barEntries.add ( new BarEntry (a[11],11  ) );





        BarDataSet barDataSet = new BarDataSet ( barEntries, "VNĐ" );
        barDataSet.setColor ( Color.rgb ( 245, 168, 154 ) );
        barDataSet.setValueTextSize ( 10 );
       // barDataSet.setBarSpacePercent ( 10 );

        YAxis yaLeft = barChart.getAxisLeft();
        yaLeft.setAxisMinValue ( 0 );

        ArrayList<String> theDates = new ArrayList<> (  );

        theDates.add ( "January" );
        theDates.add ( "February" );
        theDates.add ( "March" );
        theDates.add ( "April" );
        theDates.add ( "May" );
        theDates.add ( "June" );
        theDates.add ( "July" );
        theDates.add ( "August" );
        theDates.add ( "September" );
        theDates.add ( "October" );
        theDates.add ( "November" );
        theDates.add ( "December" );


        BarData data = new BarData ( theDates , barDataSet );

        barChart.setData ( data );
        barChart.setTouchEnabled ( true );
        barChart.setDragEnabled ( true );
        barChart.setScaleEnabled ( true );



    }

}