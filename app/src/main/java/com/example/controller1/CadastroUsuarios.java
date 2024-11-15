package com.example.controller1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroUsuarios extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha, editConfirmarSenha;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuarios);

        // Vinculando os elementos de interface
        editNome = findViewById(R.id.edit_Nome);
        editEmail = findViewById(R.id.edit_Email);
        editSenha = findViewById(R.id.edit_Senha);
        editConfirmarSenha = findViewById(R.id.edit_ConfirmarSenha);
        Button btnSalvar = findViewById(R.id.btn_Salvar);

        // Configurando o botão de salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarUsuario();
            }
        });
    }

    // Método para salvar o usuário
    private void salvarUsuario() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String confirmarSenha = editConfirmarSenha.getText().toString().trim();

        // Validar campos vazios
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar se as senhas coincidem
        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aqui você pode salvar o usuário no banco de dados ou enviar os dados para um servidor
        // Por exemplo:
        // Usuario novoUsuario = new Usuario(nome, email, senha);
        // salvarUsuarioNoBanco(novoUsuario);

        Toast.makeText(this, "Usuário salvo com sucesso!", Toast.LENGTH_SHORT).show();
    }

}
