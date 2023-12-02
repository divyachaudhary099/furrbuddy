package com.example.petcare;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_images")
public class MyImages{

    @PrimaryKey(autoGenerate = true)
    public int image_id;
    public String image_title;
    public byte[] image;

    public MyImages(String image_title, byte[] image) {
        this.image_title = image_title;
        this.image = image;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getImage_title() {
        return image_title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
