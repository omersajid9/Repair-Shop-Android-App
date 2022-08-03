package com.depauw.repairshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddRepairBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class AddRepairActivity extends AppCompatActivity {

    private ActivityAddRepairBinding binding;

    AlertDialog.Builder myBuilder;


    private View.OnClickListener addReviewOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View view) {

            if(!lengthEqual(binding.edittextRepairCost, 0) &&
                    !lengthEqual(binding.edittextRepairDate, 0) &&
                    !lengthEqual(binding.edittextRepairDescription, 0))
            {
                Vehicle vehicle  = (Vehicle) binding.spinnerVehicles.getSelectedItem();
                String vName = vehicle.getModel();
                int Vid = vehicle.getVid();
                System.out.println(vehicle.getPrice());
                System.out.println(vehicle.getVid());

                String Date = binding.edittextRepairDate.getText().toString();
                float Cost = Float.valueOf(binding.edittextRepairCost.getText().toString());
                String Description = binding.edittextRepairDescription.getText().toString();

                Repair newRepair = new Repair(vName, Date ,Cost, Description, Vid);
                DBHelper helper = DBHelper.getInstance(AddRepairActivity.this);
                long result = helper.insertRepair(newRepair);

                if(result > 0)
                {
                    Toast.makeText(AddRepairActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
            else
            {
                createAlertBuilder("Incomplete fields", "Please enter input for all fields").show();
            }

        }
    };




    private DatePickerDialog.OnDateSetListener datepickerOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            binding.edittextRepairDate.setText(year+"-"+String.format("%02d", month+1)+"-"+String.format("%02d", date));
        }
    };

    private View.OnClickListener dateOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View view)
        {
            Calendar myCalender = Calendar.getInstance();
            DatePickerDialog myPicker = new DatePickerDialog(AddRepairActivity.this, datepickerOnDateSetListener,
                    myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH),
                    myCalender.get(Calendar.DATE));
            myPicker.show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper helper = DBHelper.getInstance(this);
        ArrayList<Vehicle> Vehicles = helper.returnVehicles();
//        ArrayList<String> vehicleString = vehicleToString(Vehicles, new String[]{"year", "model"});
        ArrayAdapter<Vehicle> newAdapter = new ArrayAdapter<Vehicle>(this, android.R.layout.simple_spinner_item, Vehicles);

        newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerVehicles.setAdapter(newAdapter);

        binding.edittextRepairDate.setOnClickListener(dateOnClickListener);
        binding.buttonAddRepair.setOnClickListener(addReviewOnClickListener);

    }

    private boolean lengthEqual(EditText text, int length)
    {
        return text.getText().toString().length() == length;
    }

    private AlertDialog createAlertBuilder(String title, String message)
    {
        myBuilder = new AlertDialog.Builder(AddRepairActivity.this);
        myBuilder.setTitle(title)
                .setMessage(message);
        return myBuilder.create();
    };

}