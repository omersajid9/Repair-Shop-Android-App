package com.depauw.repairshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddVehicleBinding;

public class AddVehicleActivity extends AppCompatActivity {

    private ActivityAddVehicleBinding binding;

    private AlertDialog.Builder myBuilder;

    private View.OnClickListener button_OnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View view) {

            if(!lengthEqual(binding.edittextMakeModel, 0) &&
                    !lengthEqual(binding.edittextPrice, 0) &&
                    lengthEqual(binding.edittextYear, 4))
            {
                String Year = binding.edittextYear.getText().toString();
                String Model = binding.edittextMakeModel.getText().toString();
                float Price = Float.valueOf(binding.edittextPrice.getText().toString());
                boolean New = binding.checkboxIsNew.isChecked();

                Vehicle newVehicle = new Vehicle(Year, Model, Price, New);

                DBHelper helper = DBHelper.getInstance(AddVehicleActivity.this);
                long result = helper.insertVehicle(newVehicle);

                if(result > 0)
                {
                    Toast.makeText(AddVehicleActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
            else
            {
                createAlertBuilder("Required fields incomplete", "Kindly complete the input fields").show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddVehicle.setOnClickListener(button_OnClickListener);


    }

    private boolean intToBool(int b)
    {
        return b == 1 ? true : false;
    }

    private boolean lengthEqual(EditText text, int length)
    {
        return text.getText().toString().length() == length;
    }

    private AlertDialog createAlertBuilder(String title, String message)
    {
        myBuilder = new AlertDialog.Builder(AddVehicleActivity.this);
        myBuilder.setTitle(title)
                .setMessage(message);
        return myBuilder.create();
    };
}