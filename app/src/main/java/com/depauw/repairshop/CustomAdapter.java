package com.depauw.repairshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.depauw.repairshop.database.RepairWithVehicle;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RepairWithVehicle> rvehs;

    public CustomAdapter(Context context, ArrayList<RepairWithVehicle> rvehs) {
        this.context = context;
        this.rvehs = rvehs;

    }

    @Override
    public int getCount() {
        return rvehs.size();
    }

    @Override
    public Object getItem(int i) {
        return rvehs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.listview_results_row, viewGroup, false);
        }

        RepairWithVehicle repWVeh = rvehs.get(i);

        TextView makeModel = view.findViewById(R.id.text_year_make_model);
        TextView repairDate = view.findViewById(R.id.text_repair_date);
        TextView repairCost = view.findViewById(R.id.text_repair_cost);
        TextView repairDescription= view.findViewById(R.id.text_repair_description);

        makeModel.setText(repWVeh.getVehicle().toString());
        repairDate.setText(repWVeh.getRepair().getDate());
        repairCost.setText("$" + String.valueOf(repWVeh.getRepair().getCost()));
        repairDescription.setText(repWVeh.getRepair().getDescription());


        return view;
    }








}
