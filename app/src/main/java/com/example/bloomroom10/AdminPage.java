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
        Button createCategoryButton = findViewById(R.id.cd_id02);

        // Set an OnClickListener for the button
        createCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the logic to start the new activity (ActivityCreateCategory)
                Intent intent = new Intent(AdminPage.this, CreateCategoryActivity.class);
                startActivity(intent);
            }
        });

        // Add similar code for other buttons if needed
    }
}
