package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.depauw.repairshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddVehicle.setOnClickListener(this);
        binding.buttonAddRepair.setOnClickListener(this);
        binding.buttonSearchRepairs.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent newIntent;
        switch (view.getId())
        {
            case R.id.button_add_vehicle:
                newIntent = new Intent(this, AddVehicleActivity.class);
                startActivity(newIntent);
                break;
            case R.id.button_add_repair:
                newIntent = new Intent(this, AddRepairActivity.class);
                startActivity(newIntent);
                break;
            case R.id.button_search_repairs:
                newIntent = new Intent(this, SearchRepairsActivity.class);
                startActivity(newIntent);
                break;
        }
    }
}