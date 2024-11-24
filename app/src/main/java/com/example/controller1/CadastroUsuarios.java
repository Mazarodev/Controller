package com.example.controller1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroUsuarios extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha, editConfirmarSenha;
    private Spinner spinnerTipoUsuario; // Spinner para selecionar o tipo de usuário
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
        spinnerTipoUsuario = findViewById(R.id.spinnerTipoUsuario); // Inicializando o Spinner

        Button btnSalvar = findViewById(R.id.btn_Salvar);
        databaseHelper = new DatabaseHelper(this);

        // Configurando o Spinner com os tipos de usuário
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_usuario_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoUsuario.setAdapter(adapter);

        // Configurando o clique do botão de salvar
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

        // Obtendo o tipo de usuário selecionado
        String tipoUsuario = spinnerTipoUsuario.getSelectedItem().toString();

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




