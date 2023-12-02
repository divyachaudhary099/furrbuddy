package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.petcare.databinding.ActivityDetailedBinding;

public class DetailedActivity extends AppCompatActivity {

    ActivityDetailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if (intent != null){
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
            int pets = intent.getIntExtra("pets", R.string.dogs);
            int desc = intent.getIntExtra("desc", R.string.dogsDesc);
            int image = intent.getIntExtra("image", R.drawable.dogs);

            binding.detailName.setText(name);
            //binding.detailTime.setText(time);
            binding.detailDesc.setText(desc);
            binding.detailPets.setText(pets);
            binding.detailImage.setImageResource(image);
        }
    }
}