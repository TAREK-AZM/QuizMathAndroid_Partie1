package com.example.firstapplication.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firstapplication.R;
import com.example.firstapplication.models.Note;

public class DetailsNoteActivity extends AppCompatActivity {
    private TextView tvNom, tvDescription, tvDate, tvPriorite;
    private View viewPrioriteColor;
    private Button btnRetour;
    private ImageView ivPhoto;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);

        // Initialisation des vues
        tvNom = findViewById(R.id.tvDetailsNom);
        tvDescription = findViewById(R.id.tvDetailsDescription);
        tvDate = findViewById(R.id.tvDetailsDate);
        tvPriorite = findViewById(R.id.tvDetailsPriorite);
        viewPrioriteColor = findViewById(R.id.viewDetailsPrioriteColor);
        btnRetour = findViewById(R.id.btnRetour);
        ivPhoto = findViewById(R.id.ivDetailsPhoto);

        // Récupération de la note depuis l'Intent
        note = (Note) getIntent().getSerializableExtra("note");

        if (note != null) {
            displayNoteDetails();
        }

        // Bouton retour
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayNoteDetails() {
        tvNom.setText(note.getNom());
        tvDescription.setText(note.getDescription());
        tvDate.setText("Date: " + note.getDate());
        tvPriorite.setText("Priorité: " + note.getPriorite());
        viewPrioriteColor.setBackgroundColor(note.getCouleurPriorite());

        // Affichage de la photo si elle existe
        if (note.getPhotoPath() != null && !note.getPhotoPath().isEmpty()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(note.getPhotoPath());
                if (bitmap != null) {
                    ivPhoto.setImageBitmap(bitmap);
                    ivPhoto.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ivPhoto.setVisibility(View.GONE);
            }
        } else {
            ivPhoto.setVisibility(View.GONE);
        }
    }
}