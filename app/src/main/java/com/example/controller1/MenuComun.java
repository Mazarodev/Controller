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

    // Método genérico para navegar para outra tela
    private void navigateTo(Class<?> targetClass) {
        Intent intent = new Intent(MenuComun.this, targetClass);
        startActivity(intent);
    }

    // Método para abrir a tela de Cadastro de Produtos
    public void openCadastroProdutos(View view) {
        navigateTo(CadastroProdutos.class);
    }

    // Método para abrir a tela de Cadastro de Fornecedores
    public void openCadastroFornecedores(View view) {
        navigateTo(CadastroFornecedores.class);
    }


    // Método para abrir a tela de Relatório
    public void openRelatorio(View view) {
        navigateTo(Relatorio.class);
    }

    // Método para realizar logout com limpeza de sessão
    public void logout(View view) {
        // Limpar dados de sessão
        getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply();

        Intent intent = new Intent(MenuComun.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}