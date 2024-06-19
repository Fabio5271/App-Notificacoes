package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pi.dao.PessoaDAO;
import com.example.pi.model.PessoaVO;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etMatricula, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etMatricula = findViewById(R.id.editMatricula);
        etSenha = findViewById(R.id.editTextSenha);
        Button buttonLogin = findViewById(R.id.botaoconfirmarlogin);

        buttonLogin.setOnClickListener(v -> {
            String matricula = etMatricula.getText().toString();
            String senha = etSenha.getText().toString();

            if (TextUtils.isEmpty(matricula) || TextUtils.isEmpty(senha)) {
                Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            PessoaDAO pessoaDAO = new PessoaDAO(LoginActivity.this);
            List<PessoaVO> pessoas = pessoaDAO.getAllPessoas();

            boolean loginValido = false;

            for (PessoaVO pessoa : pessoas)
            {
                if (pessoa.getMatricula().equals(matricula) && pessoa.getSenha().equals(senha)) {
                    loginValido = true;
                    break;
                }
            }

            if (loginValido)
            {
                Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();

                // Verificar matrícula e direcionar para a atividade apropriada
                Intent intent;
                if (matricula.toLowerCase().startsWith("p"))
                {
                    intent = new Intent(LoginActivity.this, MainActivity2.class);
                } else
                {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            } else
            {
                Toast.makeText(LoginActivity.this, "Matrícula ou senha incorretos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
