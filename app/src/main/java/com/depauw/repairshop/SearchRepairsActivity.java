package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.RepairWithVehicle;
import com.depauw.repairshop.databinding.ActivitySearchRepairsBinding;

import java.util.ArrayList;

public class SearchRepairsActivity extends AppCompatActivity {

    private ActivitySearchRepairsBinding binding;

    private AdapterView.OnItemLongClickListener deleteOnClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            RepairWithVehicle rVeh =(RepairWithVehicle)adapterView.getItemAtPosition(i);

            DBHelper helper = DBHelper.getInstance(SearchRepairsActivity.this);

            int result = helper.removeRepair(rVeh.getRepair().getRid());


            if(result > 0)
            {
                Toast.makeText(SearchRepairsActivity.this, "Deletion successful", Toast.LENGTH_SHORT).show();
                binding.listviewResults.invalidateViews();
                binding.listviewResults.setAdapter(returnCustomAdapter(binding.edittextSearchPhrase.getText().toString()));
            }
            return false;


        }
    };

    private CustomAdapter returnCustomAdapter(String query)
    {
        DBHelper helper = DBHelper.getInstance(SearchRepairsActivity.this);
        return new CustomAdapter(SearchRepairsActivity.this, helper.returnRepairWithVehicles(query));
    }

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String query = binding.edittextSearchPhrase.getText().toString();

            CustomAdapter cusAdapter = returnCustomAdapter(query);
            binding.listviewResults.setAdapter(cusAdapter);
            binding.listviewResults.setOnItemLongClickListener(deleteOnClickListener);


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRepairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonFindRepairs.setOnClickListener(searchListener);


    }

}