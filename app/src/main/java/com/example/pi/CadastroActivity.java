package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pi.dao.PessoaDAO;
import com.example.pi.model.PessoaVO;

import java.util.Arrays;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, etConfirmarSenha, etMatricula, etDisciplinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.editTextNome);
        etEmail = findViewById(R.id.editTextEmail);
        etSenha = findViewById(R.id.editTextCriarSenha);
        etConfirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        etMatricula = findViewById(R.id.editTextMatricula);
        etDisciplinas = findViewById(R.id.editTextDisciplinasMatriculadas);
        Button botaoCadastrar = findViewById(R.id.botaocadastrar);

        botaoCadastrar.setOnClickListener(v -> {
            String nome = etNome.getText().toString();
            String email = etEmail.getText().toString();
            String senha = etSenha.getText().toString();
            String confirmarSenha = etConfirmarSenha.getText().toString();
            String matricula = etMatricula.getText().toString();
            String disciplinasStr = etDisciplinas.getText().toString();

            if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(senha) || TextUtils.isEmpty(confirmarSenha) || TextUtils.isEmpty(matricula) || TextUtils.isEmpty(disciplinasStr)) {
                Toast.makeText(CadastroActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(CadastroActivity.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            PessoaVO pessoa = new PessoaVO();
            pessoa.setNome(nome);
            pessoa.setEmail(email);
            pessoa.setSenha(senha);
            pessoa.setMatricula(matricula);
            pessoa.setDisciplinas(Arrays.asList(disciplinasStr.split(",")));

            PessoaDAO pessoaDAO = new PessoaDAO(CadastroActivity.this);
            pessoaDAO.addPessoa(pessoa);

            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();

            // Verificar matrícula e direcionar para a atividade apropriada
            Intent intent;
            if (matricula.toLowerCase().startsWith("p")) {
                intent = new Intent(CadastroActivity.this, MainActivity2.class);
            } else {
                intent = new Intent(CadastroActivity.this, MainActivity.class);
            }
            startActivity(intent);
        });

        Button buttonToLogin = findViewById(R.id.botaologin);
        buttonToLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
