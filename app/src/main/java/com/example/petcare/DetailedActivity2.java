package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcare.databinding.ActivityDetailed2Binding;

public class DetailedActivity2 extends AppCompatActivity {

    ActivityDetailed2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailed2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if (intent != null){
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
            int commands = intent.getIntExtra("commands", R.string.command1);
            int image = intent.getIntExtra("image", R.drawable.maggi);

            binding.detailName.setText(name);
            binding.detailTime.setText(time);
            binding.detailIngredients.setText(commands);
            binding.detailImage.setImageResource(image);
        }
    }
}