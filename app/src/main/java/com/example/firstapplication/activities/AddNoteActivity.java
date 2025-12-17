package com.example.firstapplication.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firstapplication.R;
import com.example.firstapplication.models.Note;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    private EditText etNom, etDescription, etDate;
    private Spinner spinnerPriorite;
    private Button btnSave, btnCancel;
    private ImageButton btnCamera;
    private Calendar calendar;
    private static final int REQUEST_CAMERA = 2;
    private String photoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Initialisation des vues
        etNom = findViewById(R.id.etNom);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        spinnerPriorite = findViewById(R.id.spinnerPriorite);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnCamera = findViewById(R.id.btnCamera);

        calendar = Calendar.getInstance();

        // Configuration du Spinner de priorité
        setupPrioritySpinner();

        // Configuration du sélecteur de date
        setupDatePicker();

        // Bouton Enregistrer
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        // Bouton Annuler
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Bouton Caméra
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteActivity.this, CameraActivity.class);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
    }

    private void setupPrioritySpinner() {
        String[] priorites = {"Basse", "Moyenne", "Haute"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, priorites);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriorite.setAdapter(adapter);
    }

    private void setupDatePicker() {
        etDate.setFocusable(false);
        etDate.setClickable(true);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateField();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDateField() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        etDate.setText(sdf.format(calendar.getTime()));
    }

    private void saveNote() {
        String nom = etNom.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String priorite = spinnerPriorite.getSelectedItem().toString();

        // Validation
        if (nom.isEmpty()) {
            etNom.setError("Le nom est obligatoire");
            etNom.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("La description est obligatoire");
            etDescription.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            etDate.setError("La date est obligatoire");
            etDate.requestFocus();
            return;
        }

        // Création de la note
        Note newNote = new Note(nom, description, date, priorite, photoPath);

        // Retour à l'activité précédente avec la note
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newNote", newNote);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Note enregistrée avec succès", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {
            photoPath = data.getStringExtra("photoPath");
            Toast.makeText(this, "Photo ajoutée", Toast.LENGTH_SHORT).show();
        }
    }
}