package com.example.controller1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CadastroProdutos extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1001;
    private EditText editDescricao, editCodigoBarra, editPreco, editFornecedor, editQuantidade;
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
        editQuantidade = findViewById(R.id.editQuantidade);

        Button btnSalvarProduto = findViewById(R.id.btnSalvarProduto);
        Button botaoEscanear = findViewById(R.id.botao_escanear); // Novo botão de escaneamento

        btnSalvarProduto.setOnClickListener(v -> {
            if (validarCampos()) {
                salvarProduto();
            }
        });

        botaoEscanear.setOnClickListener(v -> verificarPermissaoCamera());
    }

    public void voltar(View view) {
        onBackPressed();
    }

    private void verificarPermissaoCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            abrirCamera();
        }
    }

    private void abrirCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            String codigoBarras = data.getStringExtra("codigo_barras");
            if (codigoBarras != null) {
                editCodigoBarra.setText(codigoBarras);
            }
        }
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

        String quantidadeStr = editQuantidade.getText().toString().trim();
        if (TextUtils.isEmpty(quantidadeStr)) {
            editQuantidade.setError("Quantidade é obrigatória");
            return false;
        }

        try {
            double quantidade = Double.parseDouble(quantidadeStr);
            if (quantidade <= 0) {
                editQuantidade.setError("Quantidade deve ser positiva");
                return false;
            }
        } catch (NumberFormatException e) {
            editQuantidade.setError("Quantidade deve ser numérica");
            return false;
        }

        if (TextUtils.isEmpty(editFornecedor.getText().toString().trim())) {
            editFornecedor.setError("Fornecedor é obrigatório");
            return false;
        }

        return true;
    }



    private void salvarProduto() {
        Log.d("CadastroProduto", "Iniciando salvamento do produto");

        String descricao = editDescricao.getText().toString().trim();
        String codigoBarras = editCodigoBarra.getText().toString().trim();
        double preco;
        int quantidade;

        try {
            preco = Double.parseDouble(editPreco.getText().toString().trim());
            quantidade = Integer.parseInt(editQuantidade.getText().toString().trim()); // Alterado para int
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preço ou quantidade inválidos!", Toast.LENGTH_SHORT).show();
            Log.e("CadastroProduto", "Erro ao converter preço ou quantidade", e);
            return;
        }

        String fornecedor = editFornecedor.getText().toString().trim();
        if (fornecedor.isEmpty()) {
            Toast.makeText(this, "Razão social do fornecedor não pode ser vazia.", Toast.LENGTH_SHORT).show();
            return; // Não continua o processo de cadastro
        }

        Produto produto = new Produto(descricao, codigoBarras, preco, fornecedor, quantidade); // Passando quantidade como int
        boolean sucesso = dbHelper.addProduto(produto);

        Log.d("CadastroProduto", "Resultado do salvamento: " + sucesso);

        if (sucesso) {
            Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            Log.d("CadastroProduto", "Produto cadastrado com sucesso.");

            // Redireciona para a tela MenuComun
            Intent intent = new Intent(CadastroProdutos.this, MenuComun.class);
            startActivity(intent);
            finish(); // Fecha a tela atual (CadastroProdutos)
        } else {
            Toast.makeText(this, "Erro ao cadastrar produto!", Toast.LENGTH_SHORT).show();
            Log.e("CadastroProduto", "Erro ao salvar o produto no banco.");
        }

    }




}
