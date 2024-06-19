package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pi.dao.NotasDAO;
import com.example.pi.model.NotasVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) //calendario
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnLogoff = findViewById(R.id.logoff1);
        TextView textViewDate = findViewById(R.id.TextViewData);
        TextView textViewNotification = findViewById(R.id.exibirnotificacao);
        TextView textViewNotification2 = findViewById(R.id.exibirnotificacao2);
        TextView textViewNotification3 = findViewById(R.id.exibirnotificacao3);

        String formattedDate = getFormattedDate();
        textViewDate.setText(formattedDate);

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
    }

     public int idUltimaNotificacao() {
        NotasDAO notasDAO = new NotasDAO(this);
        List<NotasVO> listaNotas = notasDAO.getAllNotas();
        if (listaNotas.isEmpty()) {
            return 0;
        }
        return listaNotas.size() - 1;
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

    private String getFormattedDate() //calendario
    {
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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}