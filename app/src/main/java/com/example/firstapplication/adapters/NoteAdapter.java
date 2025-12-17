package com.example.firstapplication.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.firstapplication.R;
import com.example.firstapplication.models.Note;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notesList;
    private LayoutInflater inflater;

    public NoteAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public Object getItem(int position) {
        return notesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_note, parent, false);
            holder = new ViewHolder();
            holder.tvNom = convertView.findViewById(R.id.tvNoteNom);
            holder.tvDate = convertView.findViewById(R.id.tvNoteDate);
            holder.viewPriorite = convertView.findViewById(R.id.viewPriorite);
            holder.tvPriorite = convertView.findViewById(R.id.tvNotePriorite);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Note note = notesList.get(position);
        holder.tvNom.setText(note.getNom());
        holder.tvDate.setText(note.getDate());
        holder.tvPriorite.setText(note.getPriorite());
        holder.viewPriorite.setBackgroundColor(note.getCouleurPriorite());

        return convertView;
    }

    // ViewHolder pattern pour optimiser les performances
    static class ViewHolder {
        TextView tvNom;
        TextView tvDate;
        TextView tvPriorite;
        View viewPriorite;
    }

    // Méthode pour mettre à jour la liste
    public void updateNotes(List<Note> newNotes) {
        this.notesList = newNotes;
        notifyDataSetChanged();
    }
}