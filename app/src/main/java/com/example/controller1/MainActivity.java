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
        TextView esqueceuSenhaLink = findViewById(R.id.EsqueceuSenhaLink);
        TextView cadastroLink = findViewById(R.id.CadastroLink);

        // Inicializar o banco de dados
        dbHelper = new DatabaseHelper(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = CampoEmail.getText().toString().trim();
            String password = CampoSenha.getText().toString().trim();
    
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar se o usuário está registrado
                boolean loginValido = dbHelper.verificarLogin(email, password);
    
                if (loginValido) {
                    // Obter o tipo de usuário
                    String tipoUsuario = dbHelper.getTipoUsuario(email);
    
                    if (tipoUsuario != null) {
                        Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
    
                        // Redirecionar com base no tipo de usuário
                        if (tipoUsuario.equals("comum")) {
                            Intent intent = new Intent(MainActivity.this, MenuComun.class);
                            startActivity(intent);
                        } else if (tipoUsuario.equals("administrador")) {
                            Intent intent = new Intent(MainActivity.this, MenuAdministrador.class);
                            startActivity(intent);
                        }
                        finish(); // Fecha a tela de login
                    } else {
                        Toast.makeText(MainActivity.this, "Erro ao obter tipo de usuário!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }
            }
        }
         });


        // Configurar links de esquecimento de senha e registro
        esqueceuSenhaLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação para Esqueceu sua senha
                Toast.makeText(MainActivity.this, "Esqueceu a senha?", Toast.LENGTH_SHORT).show();
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
