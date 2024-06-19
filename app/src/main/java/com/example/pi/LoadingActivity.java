package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() // iniciar CadastroActivity após loading
            {
                // Iniciar a CadastroActivity
                Intent intent = new Intent(LoadingActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish(); // Finalizar a LoadingActivity
            }
        }, 3000); // 3000 ms na tela de loading
    }
}
