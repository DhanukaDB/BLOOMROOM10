package com.example.bloomroom10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloomroom10.CreateCategoryActivity;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        // Find the "Create Category" button by its ID
        Button createFlowerButton = findViewById(R.id.cd_id01);
        Button createCategoryButton = findViewById(R.id.cd_id02);
        Button manageFlowerButton = findViewById(R.id.cd_id03);
        Button manageCategoryButton = findViewById(R.id.cd_id04);

        // Set an OnClickListener for the button
        createCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the logic to start the new activity (ActivityCreateCategory)
                Intent intent = new Intent(AdminPage.this, CreateCategoryActivity.class);
                startActivity(intent);
            }
        });

        manageCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, ViewCategory.class);
                startActivity(intent);
            }
        });

        createFlowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, CreateFlowerActivity.class);
                startActivity(intent);
            }
        });

        manageFlowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, ViewFlower.class);
                startActivity(intent);
            }
        });
        // Add similar code for other buttons if needed
    }
}
