package com.example.tron.andgestion.Stock;

/**
 * Created by BORICE on 28/03/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tron.andgestion.R;

import java.util.List;



/**
 * Created by BORICE on 28/03/2016.
 */

public class ListAdapter extends ArrayAdapter<Stock> {

    //private int ent;
    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Stock> items) {
        super(context, resource, items);
        //this.ent=entete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistrow, null);
        }

        Stock p = getItem(position);

        if (p != null) {
            //TextView tt1 = (TextView) v.findViewById(R.id.AR_Ref);
            TextView tt2 = (TextView) v.findViewById(R.id.AR_Design);
            TextView tt3 = (TextView) v.findViewById(R.id.quantite);

            //if (tt1 != null) {
              //  tt1.setText(p.getAR_Ref());
            //}

            if (tt2 != null) {
                tt2.setText(p.getAR_Design());
            }

            if(this.getPosition(p)==0){
                if (tt3 != null) {
                    tt3.setText("QUANTITE");
                }
            }else{

                if (tt3 != null) {
                    tt3.setText(p.getAS_QteSto()+"");
                }
            }
        }

        return v;
    }

}
