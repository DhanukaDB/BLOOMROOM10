package com.example.bloomroom10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminPage extends AppCompatActivity {
    CardView create_category, create_follow, manage_category,order_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        order_check = findViewById(R.id.cd_id00);
        create_follow = findViewById(R.id.cd_id01);
        create_category = findViewById(R.id.cd_id02);
        manage_category = findViewById(R.id.cd_id03);


    }
}