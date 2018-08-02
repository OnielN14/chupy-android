package com.chopchop.chupy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chopchop.chupy.feature.petservice.DetailTokoActivity;

public class Coba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba);

        Button button = (Button) findViewById(R.id.budug);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Coba.this, DetailTokoActivity.class);
                startActivity(intent);
            }
        });
    }
}
