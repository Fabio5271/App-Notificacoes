package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class create2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) //configuração do botão que leva da segunda para a primeira tela de criação da notificações
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create2);

        Button buttonToNextCreate = findViewById(R.id.botaovoltarcreate);
        buttonToNextCreate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) //ir da segunda tela de criar notificações para a primeira após clicar no botão "Voltar"
            {
                Intent intent = new Intent(create2Activity.this, createActivity.class);
                startActivity(intent);
            }
        });
    }
}