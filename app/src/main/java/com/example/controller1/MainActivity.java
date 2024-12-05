package com.example.controller1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText CampoEmail;
    private EditText CampoSenha;
    private DatabaseHelper dbHelper; // Variável para o banco de dados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar os componentes da interface
        CampoEmail = findViewById(R.id.campoEmail);
        CampoSenha = findViewById(R.id.campoSenha);
        Button btLogin = findViewById(R.id.btLogin);
        TextView cadastroLink = findViewById(R.id.CadastroLink);

        // Inicializar o banco de dados
        dbHelper = new DatabaseHelper(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = CampoEmail.getText().toString().trim();
                String password = CampoSenha.getText().toString().trim();

                // Verificar se os campos não estão vazios
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Verificar se o usuário está registrado
                        boolean loginValido = dbHelper.verificarLogin(email, password);

                        if (loginValido) {
                            // Obter o tipo de usuário
                            String tipoUsuario = dbHelper.getTipoUsuario(email);

                            if (tipoUsuario != null) {
                                Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                                // Redirecionar com base no tipo de usuário
                                Intent intent;
                                if (tipoUsuario.equals("comum")) {
                                    intent = new Intent(MainActivity.this, MenuComun.class);
                                } else if (tipoUsuario.equals("administrador")) {
                                    intent = new Intent(MainActivity.this, MenuAdministrador.class);
                                } else {
                                    // Caso tipo de usuário seja inválido ou desconhecido
                                    Toast.makeText(MainActivity.this, "Tipo de usuário desconhecido", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                startActivity(intent);
                                finish(); // Fecha a tela de login
                            } else {
                                Toast.makeText(MainActivity.this, "Erro ao obter tipo de usuário!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Captura qualquer exceção e exibe um erro
                        Toast.makeText(MainActivity.this, "Erro ao realizar login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

        cadastroLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação para Registrar
                Intent intent = new Intent(MainActivity.this, CadastroUsuarios.class);
                startActivity(intent);
            }
        });
    }
}
