package com.example.controller1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        // Inicializando os componentes da interface
        editNome = findViewById(R.id.edit_Nome);
        editEmail = findViewById(R.id.edit_Email);
        editSenha = findViewById(R.id.edit_Senha);
        editConfirmarSenha = findViewById(R.id.edit_ConfirmarSenha);

        Button btnSalvar = findViewById(R.id.btn_Salvar);
        databaseHelper = new DatabaseHelper(this);

        // Configurando o clique do botão de salvar
        btnSalvar.setOnClickListener(v -> salvarUsuario());
    }

    private void salvarUsuario() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String confirmarSenha = editConfirmarSenha.getText().toString().trim();

        // Verificando se os campos estão preenchidos
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

        // Definindo um tipo de usuário padrão (por exemplo, "comum")
        String tipoUsuario = "comum"; // Você pode alterar isso conforme necessário

        // Chamando o método para adicionar o usuário no banco de dados
        boolean sucesso = databaseHelper.AddUsuario(nome, email, senha, tipoUsuario);

        Log.d("CadastroUsuarios", "Tentativa de salvar usuário: nome=" + nome + ", email=" + email);

        if (sucesso) {
            Toast.makeText(this, "Usuário salvo com sucesso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CadastroUsuarios.this, MenuComun.class);
            startActivity(intent);
            finish(); // Finaliza a atividade atual
        } else {
            Toast.makeText(this, "Erro ao salvar usuário.", Toast.LENGTH_SHORT).show();
            Log.e("CadastroUsuarios", "Falha ao salvar usuário: nome=" + nome);
        }
    }
}