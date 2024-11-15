package com.example.controller1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MenuComun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_comun);
    }

    // Método para abrir a tela de Cadastro de Produtos
    public void openCadastroProdutos(View view) {
        Intent intent = new Intent(MenuComun.this, CadastroProdutos.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Cadastro de Fornecedores
    public void openCadastroFornecedores(View view) {
        Intent intent = new Intent(MenuComun.this, CadastroFornecedores.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Contagem
    public void openContagem(View view) {
        Intent intent = new Intent(MenuComun.this, Contagem.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Relatório
    public void openRelatorio(View view) {
        Intent intent = new Intent(MenuComun.this, Relatorio.class);
        startActivity(intent);
    }

    // Método para realizar logout
    public void logout(View view) {
        Intent intent = new Intent(MenuComun.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}