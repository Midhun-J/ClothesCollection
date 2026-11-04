
package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName, etCategory, etColour;
    Button btnSave, btnView, btnUpdate, btnDelete;
    TextView tvDisplay;

    Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML components
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etCategory = findViewById(R.id.etCategory);
        etColour = findViewById(R.id.etColour);

        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Create database object
        db = new Db(this);

        // Save button
        btnSave.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String colour = etColour.getText().toString().trim();

            if (name.isEmpty() || category.isEmpty()
                    || colour.isEmpty()) {

                Toast.makeText(this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            long result = db.insertClothes(name, category, colour);

            if (result != -1) {

                Toast.makeText(this,
                        "Clothes saved successfully",
                        Toast.LENGTH_SHORT).show();

                clearFields();
                displayClothes();

            } else {

                Toast.makeText(this,
                        "Failed to save clothes",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // View button
        btnView.setOnClickListener(v -> {
            displayClothes();
        });

        // Update button
        btnUpdate.setOnClickListener(v -> {

            String idText = etId.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String colour = etColour.getText().toString().trim();

            if (idText.isEmpty() || name.isEmpty()
                    || category.isEmpty() || colour.isEmpty()) {

                Toast.makeText(this,
                        "Enter ID and all clothes details",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int id = Integer.parseInt(idText);

                int result = db.updateClothes(
                        id, name, category, colour);

                if (result > 0) {

                    Toast.makeText(this,
                            "Clothes updated successfully",
                            Toast.LENGTH_SHORT).show();

                    clearFields();
                    displayClothes();

                } else {

                    Toast.makeText(this,
                            "Clothes ID not found",
                            Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "Invalid ID",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Delete button
        btnDelete.setOnClickListener(v -> {

            String idText = etId.getText().toString().trim();

            if (idText.isEmpty()) {

                Toast.makeText(this,
                        "Please enter Clothes ID",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int id = Integer.parseInt(idText);

                int result = db.deleteClothes(id);

                if (result > 0) {

                    Toast.makeText(this,
                            "Clothes deleted successfully",
                            Toast.LENGTH_SHORT).show();

                    clearFields();
                    displayClothes();

                } else {

                    Toast.makeText(this,
                            "Clothes ID not found",
                            Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "Invalid ID",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Display all saved clothes
    private void displayClothes() {
        String data = db.getAllClothes();
        tvDisplay.setText(data);
    }

    // Clear input fields
    private void clearFields() {
        etId.setText("");
        etName.setText("");
        etCategory.setText("");
        etColour.setText("");
    }
}
