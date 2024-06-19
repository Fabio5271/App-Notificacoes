package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pi.dao.NotasDAO;
import com.example.pi.model.NotasVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private TextView textViewDate, textViewNotification, textViewNotification2, textViewNotification3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textViewDate = findViewById(R.id.TextViewData2);
        textViewNotification = findViewById(R.id.exibirnotificacao1);
        textViewNotification2 = findViewById(R.id.exibirnotificacao2);
        textViewNotification3 = findViewById(R.id.exibirnotificacao3);

        String formattedDate = getFormattedDate();
        textViewDate.setText(formattedDate);

        Button buttonToCreate = findViewById(R.id.botaocriarnotificacao);
        ImageButton lixeiraButton = findViewById(R.id.lixeira_button);
        ImageButton lixeiraButton2 = findViewById(R.id.lixeira_button2);
        ImageButton lixeiraButton3 = findViewById(R.id.lixeira_button3);
        ImageButton btnLogoff = findViewById(R.id.logoff2);

        btnLogoff.setVisibility(View.VISIBLE);

        // Configurar listener para o botão de criar notificação
        buttonToCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, createActivity.class);
                startActivity(intent);
            }
        });

        // Configurar listener para o botão de logoff
        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoff();
            }
        });

        // Receber a notificação criada
        textViewNotification.setText(textoNotificacao(idUltimaNotificacao()));
        textViewNotification2.setText(textoNotificacao(idUltimaNotificacao()-1));
        textViewNotification3.setText(textoNotificacao(idUltimaNotificacao()-2));

        // Listeners para os botões de deletar
        lixeiraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarNotificacao(idUltimaNotificacao());
                Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        lixeiraButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarNotificacao(idUltimaNotificacao()-1);
                Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        lixeiraButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarNotificacao(idUltimaNotificacao()-2);
                Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public int idUltimaNotificacao() {
        NotasDAO notasDAO = new NotasDAO(this);
        List<NotasVO> listaNotas = notasDAO.getAllNotas();
        if (listaNotas.isEmpty()) {
            return 0;
        }
        return listaNotas.size() - 1;
    }

    public void deletarNotificacao(int id) {
        NotasDAO notasDAO = new NotasDAO(this);
        List<NotasVO> listaNotas = notasDAO.getAllNotas();
        if (listaNotas.isEmpty() || id >= listaNotas.size() || id < 0) {
            return;
        }
        notasDAO.deleteNota(listaNotas.get(id));
    }

    public String textoNotificacao(int id) {
        NotasDAO notasDAO = new NotasDAO(this);
        List<NotasVO> listaNotas = notasDAO.getAllNotas();
        if (listaNotas.isEmpty() || id >= listaNotas.size() || id < 0) {
            return null;
        }
        String title = listaNotas.get(id).getTitulo();
        String description = listaNotas.get(id).getDescricao();
        if (title != null && description != null) {
            String notificationText = title + " - " + getFormattedDate2() + "\n\n" + description;
            return notificationText;
        } else {
            return null;
        }
    }

    private String getFormattedDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        return dateFormat.format(calendar.getTime());
    }

    private String getFormattedDate2() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        return dateFormat.format(calendar.getTime());
    }

    private void logoff() {
        Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
