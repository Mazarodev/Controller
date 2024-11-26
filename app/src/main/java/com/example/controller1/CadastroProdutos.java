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
    private DatabaseHelper dbHelper; // Declaração de DatabaseHelper

    public CadastroProdutos(String descricao, String codigoBarras, double preco, String fornecedor) {
        this.editDescricao = new EditText(this);
        this.editDescricao.setText(descricao);
        this.editCodigoBarra = new EditText(this);
        this.editCodigoBarra.setText(codigoBarras);
        this.editPreco = new EditText(this);
        this.editPreco.setText(String.valueOf(preco));
        this.editFornecedor = new EditText(this);
        this.editFornecedor.setText(fornecedor);
    }

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
        Button btnSalvarUsuario = findViewById(R.id.btnSalvarUsuario);

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

        if (dbHelper.codigoBarraExiste(editCodigoBarra.getText().toString().trim())) {
            editCodigoBarra.setError("Código de barras já existe");
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
        int fornecedorId = dbHelper.getFornecedorIdByName(fornecedor);
        if (fornecedorId == -1) {
            Toast.makeText(this, "Fornecedor não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sucesso = dbHelper.addProduto(descricao, codigoBarra, preco, fornecedorId);
        if (sucesso) {
            Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            editDescricao.setText("");
            editCodigoBarra.setText("");
            editPreco.setText("");
            editFornecedor.setText("");
        } else {
            Toast.makeText(this, "Erro ao cadastrar produto", Toast.LENGTH_SHORT).show();
        }
    }

    // Getters para obter os valores dos campos
    public String getDescricao() {
        return editDescricao != null ? editDescricao.getText().toString().trim() : null;
    }

    public String getCodigoBarras() {
        return editCodigoBarra != null ? editCodigoBarra.getText().toString().trim() : null;
    }

    public String getPreco() {
        return editPreco != null ? editPreco.getText().toString().trim() : null;
    }

    public String getFornecedor() {
        return editFornecedor != null ? editFornecedor.getText().toString().trim() : null;
    }
}
