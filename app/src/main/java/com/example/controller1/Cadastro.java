package com.example.controller1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {

    private EditText CampoNome, CampoEmail, CampoSenha, CampoConfirmarSenha;
    private Button btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializar os campos
        CampoNome = findViewById(R.id.campoNome);
        CampoEmail = findViewById(R.id.campoEmail);
        CampoSenha = findViewById(R.id.campoSenha);
        CampoConfirmarSenha = findViewById(R.id.campoConfirmarSenha);
        btSalvar = findViewById(R.id.botaoSalvar);

        // Ação do botão Salvar
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = CampoNome.getText().toString().trim();
                String email = CampoEmail.getText().toString().trim();
                String password = CampoSenha.getText().toString().trim();
                String confirmPassword = CampoConfirmarSenha.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(Cadastro.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                } else {
                    // Lógica para salvar o novo usuário
                    Toast.makeText(Cadastro.this, "Usuário registrado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}