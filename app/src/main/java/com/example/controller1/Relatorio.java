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

        // Buscar dados do banco
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
            while (cursor.moveToNext()) {
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
                String codigoBarras = cursor.getString(cursor.getColumnIndexOrThrow("codigo_barras"));
                double preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"));
                String fornecedor = cursor.getString(cursor.getColumnIndexOrThrow("fornecedor"));
                String quantidade = cursor.getString(cursor.getColumnIndexOrThrow("quantidade"));

                produtos.add(new Produto(descricao, codigoBarras, preco, fornecedor, quantidade));
            }
            cursor.close();
        }
        return produtos;
    }
}
