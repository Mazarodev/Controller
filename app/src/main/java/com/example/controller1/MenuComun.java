package com.example.controller1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

    // Método para realizar logout com confirmação
    public void logout(View view) {
        // Criando a caixa de diálogo de confirmação
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Logout")
                .setMessage("Tem certeza de que deseja sair?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Limpar dados de sessão
                        getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply();

                        // Exibe uma mensagem de sucesso (opcional)
                        Toast.makeText(MenuComun.this, "Você foi desconectado.", Toast.LENGTH_SHORT).show();

                        // Redireciona para a tela de login (MainActivity)
                        Intent intent = new Intent(MenuComun.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();  // Finaliza a Activity atual
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();  // Apenas fecha a caixa de diálogo sem fazer nada
                    }
                })
                .show();  // Exibe a caixa de diálogo
    }
}