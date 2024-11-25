package com.example.controller1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroProdutos extends AppCompatActivity {

    private EditText editDescricao, editCodigoBarra, editPreco, editFornecedor;
    private Button btnSalvarUsuario;
    private DatabaseHelper dbHelper; // Declaração de DatabaseHelper

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);

        // Inicializando DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Inicializando os campos
        editDescricao = findViewById(R.id.editDescricao);
        editCodigoBarra = findViewById(R.id.editCodigoBarra);
        editPreco = findViewById(R.id.editPreco);
        editFornecedor = findViewById(R.id.editFornecedor);
        btnSalvarUsuario = findViewById(R.id.btnSalvarUsuario);

        // Configurando o clique do botão Salvar
        btnSalvarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    salvarProduto();
                }
            }
        });
    }

    // Método para validar os campos
    private boolean validarCampos() {
        if (TextUtils.isEmpty(editDescricao.getText().toString().trim())) {
            editDescricao.setError("Descrição é obrigatória");
            return false;
        }

        if (TextUtils.isEmpty(editCodigoBarra.getText().toString().trim())) {
            editCodigoBarra.setError("Código de barra é obrigatório");
            return false;
        }

        String precoStr = editPreco.getText().toString().trim();
        if (TextUtils.isEmpty(precoStr)) {
            editPreco.setError("Preço é obrigatório");
            return false;
        } else {
            try {
                double preco = Double.parseDouble(precoStr);
                if (preco <= 0) {
                    editPreco.setError("Preço deve ser um valor positivo");
                    return false;
                }
            } catch (NumberFormatException e) {
                editPreco.setError("Preço deve ser um valor numérico");
                return false;
            }
        }

        if (TextUtils.isEmpty(editFornecedor.getText().toString().trim())) {
            editFornecedor.setError("Fornecedor é obrigatório");
            return false;
        }

        return true;
    }

    // Método para salvar o produto
    private void salvarProduto() {
        String descricao = editDescricao.getText().toString().trim();
        String codigoBarra = editCodigoBarra.getText().toString().trim();
        double preco = Double.parseDouble(editPreco.getText().toString().trim());
        String fornecedor = editFornecedor.getText().toString().trim();

        // Você precisará obter o id do fornecedor baseado no nome ou adicioná-lo à tabela de fornecedores
        long fornecedorId = dbHelper.addFornecedor(fornecedor); // Ajuste conforme o método em DatabaseHelper

        if (fornecedorId != -1) {
            boolean sucesso = dbHelper.addProduto(descricao, codigoBarra, preco, (int) fornecedorId);
            if (sucesso) {
                Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao cadastrar produto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Erro ao cadastrar fornecedor", Toast.LENGTH_SHORT).show();
        }
    }
}
