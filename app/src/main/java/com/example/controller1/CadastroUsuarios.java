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

        criarUsuarioTeste();

        // Configurando o clique do botão de salvar
        btnSalvar.setOnClickListener(v -> salvarUsuario());
    }

    public void voltar(View view) {
        onBackPressed();
    }

    private void criarUsuarioTeste() {
        boolean usuarioTesteCriado = getSharedPreferences("app_preferences", MODE_PRIVATE)
                .getBoolean("usuario_teste_criado", false);
        Log.d("CadastroUsuarios", "usuarioTesteCriado: " + usuarioTesteCriado);

        // Se o usuário de teste já foi criado, não fazer nada
        if (usuarioTesteCriado) {
            Log.d("CadastroUsuarios", "Usuário de teste já criado anteriormente.");
            return;
        }

        // Criando um usuário de teste com um e-mail único
        String nomeTeste = "IsraelAdmin";
        String emailTeste = "israeladmin" + System.currentTimeMillis() + "@teste.com"; // E-mail único
        String senhaTeste = "1234567";
        String tipoUsuarioTeste = "administrador";

        // Verificando se o e-mail já está registrado
        if (!databaseHelper.verificarEmailExiste(emailTeste)) {
            // Adicionando o usuário de teste no banco de dados
            boolean sucesso = databaseHelper.AddUsuario(nomeTeste, emailTeste, senhaTeste, tipoUsuarioTeste);
            Log.d("CadastroUsuarios", "Tentativa de adicionar usuário de teste: " + sucesso);

            if (sucesso) {
                Log.d("CadastroUsuarios", "Usuário de teste criado com sucesso.");

                // Marcando que o usuário de teste foi criado
                getSharedPreferences("app_preferences", MODE_PRIVATE).edit()
                        .putBoolean("usuario_teste_criado", true).apply();
            } else {
                Log.e("CadastroUsuarios", "Erro ao criar usuário de teste.");
            }
        } else {
            Log.d("CadastroUsuarios", "E-mail de teste já está registrado.");
        }
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
        String tipoUsuario = "administrador"; // Você pode alterar isso conforme necessário

        // Chamando o método para adicionar o usuário no banco de dados
        boolean sucesso = databaseHelper.AddUsuario(nome, email, senha, tipoUsuario);

        Log.d("CadastroUsuarios", "Tentativa de salvar usuário: nome=" + nome + ", email=" + email);

        if (sucesso) {
            Toast.makeText(this, "Usuário salvo com sucesso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CadastroUsuarios.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finaliza a atividade atual
        } else {
            Toast.makeText(this, "Erro ao salvar usuário.", Toast.LENGTH_SHORT).show();
            Log.e("CadastroUsuarios", "Falha ao salvar usuário: nome=" + nome);
        }
    }
}