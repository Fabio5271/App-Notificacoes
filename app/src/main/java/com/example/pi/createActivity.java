package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pi.dao.NotasDAO;
import com.example.pi.model.NotasVO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class createActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription;
    private String visibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        editTextTitle = findViewById(R.id.editTextTituloNotificacao);
        editTextDescription = findViewById(R.id.editTextDescricaoNotificacao);
        Spinner spinner = findViewById(R.id.spinner_visibility);
        Button buttonCreateNotification = findViewById(R.id.confirmarcriacao);

        // Configurar o Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.visibility_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visibility = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Não é necessário fazer nada aqui
            }
        });

        buttonCreateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(createActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Supondo pessoaId = 1
                NotasVO nota = new NotasVO();
                nota.setTitulo(title);
                nota.setDescricao(description);
                nota.setDataCriacao(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                NotasDAO notasDAO = new NotasDAO(createActivity.this);
                notasDAO.addNota(nota);

                Intent intentToMain1 = new Intent(createActivity.this, MainActivity.class);
                intentToMain1.putExtra("title", title);
                intentToMain1.putExtra("description", description);
                startActivity(intentToMain1);

                Intent intentToMain2 = new Intent(createActivity.this, MainActivity2.class);
                intentToMain2.putExtra("title", title);
                intentToMain2.putExtra("description", description);
                startActivity(intentToMain2);

                finish();
            }
        });
    }
}
