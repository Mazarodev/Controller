package com.example.controller1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MenuAdministradorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);
    }

    // Método para abrir a tela de Cadastro de Produtos
    public void openCadastroProdutos(View view) {
        Intent intent = new Intent(MenuAdministradorActivity.this, CadastroProdutos.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Cadastro de Fornecedores
    public void openCadastroFornecedores(View view) {
        Intent intent = new Intent(MenuAdministradorActivity.this, CadastroFornecedoresActivity.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Cadastro de Usuários
    public void openCadastroUsuarios(View view) {
        Intent intent = new Intent(MenuAdministradorActivity.this, CadastroUsuariosActivity.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Contagem
    public void openContagem(View view) {
        Intent intent = new Intent(MenuAdministradorActivity.this, ContagemActivity.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Relatório
    public void openRelatorio(View view) {
        Intent intent = new Intent(MenuAdministradorActivity.this, RelatorioActivity.class);
        startActivity(intent);
    }

    // Método para abrir a tela de Estoque
    public void openEstoque(View view) {
        Intent intent = new Intent(MenuAdministradorActivity.this, EstoqueActivity.class);
        startActivity(intent);
    }

    // Método para realizar logout
    public void logout(View view) {
        Intent intent = new Intent(MenuAdministradorActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}