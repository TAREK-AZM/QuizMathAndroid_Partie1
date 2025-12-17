package com.example.firstapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapplication.adapters.NoteAdapter;
import com.example.firstapplication.R;
import com.example.firstapplication.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private ListView listView;
    private NoteAdapter adapter;
    private List<Note> notesList;
    private FloatingActionButton fabAdd;
    private static final int REQUEST_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        // Initialisation des vues
        listView = findViewById(R.id.listViewNotes);
        fabAdd = findViewById(R.id.fabAddNote);

        // Initialisation de la liste avec des notes d'exemple
        initializeNotes();

        // Configuration de l'adapteur
        adapter = new NoteAdapter(this, notesList);
        listView.setAdapter(adapter);

        // Gestion du clic sur une note
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = notesList.get(position);
                Intent intent = new Intent(NoteListActivity.this, DetailsNoteActivity.class);
                intent.putExtra("note", note);
                startActivity(intent);
            }
        });

        // Gestion du bouton d'ajout
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, REQUEST_ADD_NOTE);
            }
        });
    }

    // Initialisation avec des notes d'exemple
    private void initializeNotes() {
        notesList = new ArrayList<>();
        notesList.add(new Note("Réunion projet", "Préparer la présentation pour le client", "17/12/2025", "Haute"));
        notesList.add(new Note("Courses", "Acheter des fruits et légumes", "18/12/2025", "Moyenne"));
        notesList.add(new Note("Anniversaire", "Acheter un cadeau pour Marie", "20/12/2025", "Basse"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_NOTE && resultCode == RESULT_OK && data != null) {
            Note newNote = (Note) data.getSerializableExtra("newNote");
            if (newNote != null) {
                notesList.add(newNote);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste si nécessaire
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}