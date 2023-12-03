package com.example.bloomroom10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;

public class UserPage extends AppCompatActivity {
 CardView follower_ds,category_ds, card_ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        follower_ds = findViewById(R.id.cd_id);
        category_ds = findViewById(R.id.cd_id1);

        follower_ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the ViewFlower activity
                Intent intent = new Intent(UserPage.this, ViewFlower.class);
                startActivity(intent);
            }
        });

        category_ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the ViewFlower activity
                Intent intent = new Intent(UserPage.this, ViewCategory.class);
                startActivity(intent);
            }
        });

    }
}