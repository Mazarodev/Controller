package com.example.controller1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroUsuarios extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha, editConfirmarSenha;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuarios);

        editNome = findViewById(R.id.edit_Nome);
        editEmail = findViewById(R.id.edit_Email);
        editSenha = findViewById(R.id.edit_Senha);
        editConfirmarSenha = findViewById(R.id.edit_ConfirmarSenha);

        Button btnSalvar = findViewById(R.id.btn_Salvar);

        databaseHelper = new DatabaseHelper(this);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarUsuario();
            }
        });
    }

    private void salvarUsuario() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String confirmarSenha = editConfirmarSenha.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.verificarEmailExiste(email)) {
            Toast.makeText(this, "E-mail já está registrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sucesso = databaseHelper.AddUsuario(nome, email, senha);

        Log.d("CadastroUsuarios",
                ("Tentativa de salvar usuário: nome=" + nome + ", email=" + email));

        if (sucesso) {
            Toast.makeText(this,
                    ("Usuário salvo com sucesso!"), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CadastroUsuarios.this, MenuComun.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,
                    ("Erro ao salvar usuário."), Toast.LENGTH_SHORT).show();
            Log.e("CadastroUsuarios",
                    ("Falha ao salvar usuário: nome=" + nome));
        }
    }
}




