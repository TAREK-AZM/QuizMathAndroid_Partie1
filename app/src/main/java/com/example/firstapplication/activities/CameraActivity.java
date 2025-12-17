package com.example.firstapplication.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.firstapplication.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_PICK_IMAGE = 102;

    private ImageView ivPreview;
    private Button btnTakePhoto, btnSelectGallery, btnSave, btnCancel;
    private Bitmap capturedBitmap;
    private String currentPhotoPath;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Initialisation des vues
        ivPreview = findViewById(R.id.ivPhotoPreview);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnSelectGallery = findViewById(R.id.btnSelectGallery);
        btnSave = findViewById(R.id.btnSavePhoto);
        btnCancel = findViewById(R.id.btnCancelPhoto);

        // Bouton Prendre une photo
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    openCamera();
                } else {
                    requestCameraPermission();
                }
            }
        });

        // Bouton Sélectionner depuis la galerie
        btnSelectGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Bouton Enregistrer
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });

        // Bouton Annuler
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA_PERMISSION);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }

    private void savePhoto() {
        if (capturedBitmap == null) {
            Toast.makeText(this, "Aucune photo à enregistrer", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Créer un fichier pour stocker la photo
            File photoFile = createImageFile();

            // Enregistrer le bitmap dans le fichier
            FileOutputStream fos = new FileOutputStream(photoFile);
            capturedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();

            // Retourner le chemin de la photo
            Intent resultIntent = new Intent();
            resultIntent.putExtra("photoPath", currentPhotoPath);
            setResult(RESULT_OK, resultIntent);

            Toast.makeText(this, "Photo enregistrée", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
        String imageFileName = "NOTE_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                capturedBitmap = (Bitmap) extras.get("data");
                ivPreview.setImageBitmap(capturedBitmap);
                ivPreview.setVisibility(View.VISIBLE);
                btnSave.setEnabled(true);
            } else if (requestCode == REQUEST_PICK_IMAGE && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    capturedBitmap = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), selectedImageUri);
                    ivPreview.setImageBitmap(capturedBitmap);
                    ivPreview.setVisibility(View.VISIBLE);
                    btnSave.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permission caméra refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }
}