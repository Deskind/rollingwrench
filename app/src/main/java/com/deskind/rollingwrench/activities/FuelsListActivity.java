package com.deskind.rollingwrench.activities;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.deskind.rollingwrench.database.DBUtility;
import com.rollingwrench.deskind.rollingwrench.R;

public class FuelsListActivity extends Activity {

    Cursor fuelUps;
    TableLayout tl;
    TableRow tr;
    String [] cNames;
    TextView fuelId, carBrand, fuelDate, liters, cost;

    TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT);



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuels_list);

        fuelUps = DBUtility.getFuelUpsList(getApplicationContext());
        cNames = fuelUps.getColumnNames();
        Log.i("FUELUPS", "THE NUMBER OF FUELUPS IN THE TABLE   " + fuelUps.getCount());

        tl = (TableLayout) findViewById(R.id.mainTable);
        addHeaders();
        addData();
    }

    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(rowParams);

        /** Id header **/
        fuelId = new TextView(this);
        fuelId.setText("Id");
        fuelId.setTextColor(Color.RED);
        fuelId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        fuelId.setPadding(15, 5, 5, 0);
        tr.addView(fuelId);  // Adding textView to tablerow.

        /** Car header **/
        carBrand = new TextView(this);
        carBrand.setText("Car");
        carBrand.setTextColor(Color.RED);
        carBrand.setPadding(15, 5, 5, 0);
        carBrand.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(carBrand); // Adding textView to tablerow.

        /**Date header **/
        fuelDate = new TextView(this);
        fuelDate.setText("Date");
        fuelDate.setTextColor(Color.RED);
        fuelDate.setPadding(15, 5, 5, 0);
        fuelDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(fuelDate); // Adding textView to tablerow.

        /**Liters header**/
        liters = new TextView(this);
        liters.setText("Liters");
        liters.setTextColor(Color.RED);
        liters.setPadding(15, 5, 5, 0);
        liters.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(liters); // Adding textView to tablerow.

        /**Cost header**/
        cost = new TextView(this);
        cost.setText("Cost");
        cost.setTextColor(Color.RED);
        cost.setPadding(15, 5, 5, 0);
        cost.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(cost); // Adding textView to tablerow.


        // Add the row to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

//        // we are adding two textviews for the divider because we have two columns
//        tr = new TableRow(this);
//        tr.setLayoutParams(new TableRow.LayoutParams(
//                TableRow.LayoutParams.FILL_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//
//        /** Creating another textview **/
//        TextView divider = new TextView(this);
//        divider.setText("-----------------");
//        divider.setTextColor(Color.GREEN);
//        divider.setPadding(5, 0, 0, 0);
//        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(divider); // Adding textView to tablerow.
//
//        TextView divider2 = new TextView(this);
//        divider2.setText("-------------------------");
//        divider2.setTextColor(Color.GREEN);
//        divider2.setPadding(5, 0, 0, 0);
//        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(divider2); // Adding textView to tablerow.
//
//        // Add the TableRow to the TableLayout
//        tl.addView(tr, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.FILL_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
    }
//
    /** This function add the data to the table **/
    public void addData(){

        int index;
        int i;
        String s;
        float f;

        while (fuelUps.moveToNext())
        {


            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            /** Creating TextView for fuel ID **/
            index = fuelUps.getColumnIndexOrThrow(cNames[0]);
            i = fuelUps.getInt(index);

            fuelId = new TextView(this);
            fuelId.setText(String.valueOf(i));
            fuelId.setTextColor(Color.BLUE);
//            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            fuelId.setPadding(15, 5, 5, 5);
            tr.addView(fuelId);  // Adding textView to tablerow.

            /** Creating TextView for CarBrand **/
            index = fuelUps.getColumnIndexOrThrow(cNames[1]);
            s = fuelUps.getString(index);

            carBrand = new TextView(this);
            carBrand.setText(s);
            carBrand.setTextColor(Color.BLUE);
            carBrand.setPadding(15, 5, 5, 5);
//            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(carBrand); // Adding textView to tablerow.

            /** Creating TextView for Date **/
            index = fuelUps.getColumnIndexOrThrow(cNames[2]);
            s = fuelUps.getString(index);

            fuelDate = new TextView(this);
            fuelDate.setText(s);
            fuelDate.setTextColor(Color.BLUE);
            fuelDate.setPadding(15, 5, 5, 5);
//            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(fuelDate); // Adding textView to tablerow.

            /** Creating TextView for fueled liters **/
            index = fuelUps.getColumnIndexOrThrow(cNames[3]);
            i = fuelUps.getInt(index);

            liters = new TextView(this);
            liters.setText(String.valueOf(i));
            liters.setTextColor(Color.BLUE);
            liters.setPadding(15, 5, 5, 5);
//            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(liters); // Adding textView to tablerow.

            /** Creating TextView for total cost **/
            index = fuelUps.getColumnIndexOrThrow(cNames[4]);
            f = fuelUps.getFloat(index);

            cost = new TextView(this);
            cost.setText(String.valueOf(f));
            cost.setTextColor(Color.BLUE);
            cost.setPadding(15, 5, 5, 5);
//            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(cost); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
