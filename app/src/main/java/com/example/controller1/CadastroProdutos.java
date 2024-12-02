package com.example.controller1;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroProdutos extends AppCompatActivity {

    private EditText editDescricao, editCodigoBarra, editPreco, editFornecedor;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);

        dbHelper = new DatabaseHelper(this);

        editDescricao = findViewById(R.id.editDescricao);
        editCodigoBarra = findViewById(R.id.editCodigoBarra);
        editPreco = findViewById(R.id.editPreco);
        editFornecedor = findViewById(R.id.editFornecedor);
        Button btnSalvarProduto = findViewById(R.id.btnSalvarProduto);


        btnSalvarProduto.setOnClickListener(v -> {
            if (validarCampos()) {
                salvarProduto();
            }
        });
    }

    private boolean validarCampos() {
        if (TextUtils.isEmpty(editDescricao.getText().toString().trim())) {
            editDescricao.setError("Descrição é obrigatória");
            return false;
        }

        if (TextUtils.isEmpty(editCodigoBarra.getText().toString().trim())) {
            editCodigoBarra.setError("Código de barras é obrigatório");
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
        }

        try {
            double preco = Double.parseDouble(precoStr);
            if (preco <= 0) {
                editPreco.setError("Preço deve ser positivo");
                return false;
            }
        } catch (NumberFormatException e) {
            editPreco.setError("Preço deve ser numérico");
            return false;
        }

        if (TextUtils.isEmpty(editFornecedor.getText().toString().trim())) {
            editFornecedor.setError("Fornecedor é obrigatório");
            return false;
        }

        return true;
    }

    public EditText getDescricao() {
        return editDescricao;
    }

    public EditText getCodigoBarras() {
        return editCodigoBarra;
    }

    public EditText getPreco() {
        return editPreco;
    }

    public EditText getFornecedor() {
        return editFornecedor;
    }


    private void salvarProduto() {
        String descricao = editDescricao.getText().toString().trim();
        String codigoBarra = editCodigoBarra.getText().toString().trim();
        double preco = Double.parseDouble(editPreco.getText().toString().trim());
        String fornecedor = editFornecedor.getText().toString().trim();

        int fornecedorId = dbHelper.getFornecedorIdByName(fornecedor);

        if (fornecedorId == -1) {
            Toast.makeText(this, "Fornecedor não encontrado!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sucesso = dbHelper.addProduto(descricao, codigoBarra, preco, fornecedorId);
        Toast.makeText(this, sucesso ? "Produto cadastrado com sucesso!" : "Erro ao cadastrar produto", Toast.LENGTH_SHORT).show();
    }
}
