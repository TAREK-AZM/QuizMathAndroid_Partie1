package com.example.firstapplication.models;

import java.io.Serializable;

public class Note implements Serializable {
    private String nom;
    private String description;
    private String date;
    private String priorite;
    private String photoPath; // Chemin de la photo (optionnel)

    // Constructeur complet
    public Note(String nom, String description, String date, String priorite) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.priorite = priorite;
        this.photoPath = null;
    }

    // Constructeur avec photo
    public Note(String nom, String description, String date, String priorite, String photoPath) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.priorite = priorite;
        this.photoPath = photoPath;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getPriorite() {
        return priorite;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    // Méthode pour obtenir la couleur selon la priorité
    public int getCouleurPriorite() {
        switch (priorite.toLowerCase()) {
            case "haute":
                return 0xFFFF5252; // Rouge
            case "moyenne":
                return 0xFFFFB74D; // Orange
            case "basse":
                return 0xFF81C784; // Vert
            default:
                return 0xFF9E9E9E; // Gris par défaut
        }
    }
}