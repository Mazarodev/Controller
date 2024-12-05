package com.example.controller1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Relatorio extends AppCompatActivity {

    private RecyclerView recyclerViewProdutos;
    private ProdutoAdapter produtoAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);

        List<Produto> produtoList = getProdutos();
        produtoAdapter = new ProdutoAdapter(this, produtoList);
        recyclerViewProdutos.setAdapter(produtoAdapter);
    }

    public void voltar(View view) {
        onBackPressed();
    }

    private List<Produto> getProdutos() {
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllProdutos();

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
                    String codigoBarras = cursor.getString(cursor.getColumnIndexOrThrow("codigo_barra"));  // Corrigido para codigo_barra
                    double preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"));
                    String fornecedor = getFornecedorById(cursor.getInt(cursor.getColumnIndexOrThrow("id_fornecedor"))); // Obter fornecedor pelo ID
                    String quantidade = cursor.getString(cursor.getColumnIndexOrThrow("quantidade"));

                    produtos.add(new Produto(descricao, codigoBarras, preco, fornecedor, quantidade));
                }
            } finally {
                cursor.close();
            }
        }
        return produtos;
    }

    // Método para obter o nome do fornecedor com base no ID
    private String getFornecedorById(int fornecedorId) {
        Cursor cursor = databaseHelper.getFornecedorById(fornecedorId);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow("razao_social"));
                }
            } finally {
                cursor.close();
            }
        }
        return "Fornecedor desconhecido"; // Caso o fornecedor não seja encontrado
    }
}
