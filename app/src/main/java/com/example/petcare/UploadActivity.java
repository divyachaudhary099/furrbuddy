package com.example.petcare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    private ImageView imageViewAddImage;
    private EditText editTextAddTitle;
    private FloatingActionButton buttonSave;
    private Bitmap selectedImage;
    private Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        buttonSave = findViewById(R.id.buttonSave);

        imageViewAddImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(UploadActivity.this
                    , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(UploadActivity.this
                        ,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);

                Log.i("terrorist", "success");
            }
            else {
                Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent,2);
            }

        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedImage == null)
                {
                    Toast.makeText(UploadActivity.this, "Please select an image!", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    String title = editTextAddTitle.getText().toString();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    scaledImage = makeSmall(selectedImage,300);
                    scaledImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
                    byte [] image = outputStream.toByteArray();

                    Intent intent = new Intent();
                    intent.putExtra("title",title);
                    intent.putExtra("image",image);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(imageIntent,2);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null)
        {
            try {

                if (Build.VERSION.SDK_INT >= 28)
                {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),data.getData());
                    selectedImage = ImageDecoder.decodeBitmap(source);
                }

                else
                {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                }

                imageViewAddImage.setImageBitmap(selectedImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap makeSmall(Bitmap image, int maxSize)
    {
        int width = image.getWidth();
        int height = image.getHeight();

        float ratio = (float) width / (float)height;

        if (ratio > 1)
        {
            width = maxSize;
            height = (int)(width / ratio);

        }
        else
        {
            height = maxSize;
            width = (int)(height * ratio);
        }

        return Bitmap.createScaledBitmap(image,width,height,true);
    }

}