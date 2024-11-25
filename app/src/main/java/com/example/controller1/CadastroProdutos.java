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
                    // Se todos os campos forem válidos, exibe uma mensagem de sucesso
                    Toast.makeText(CadastroProdutos.this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    salvarProduto();

                    // Aqui você pode adicionar o código para salvar os dados no banco ou outra ação desejada
                }
            }
        });
    }

    // Método para validar os campos
    private boolean validarCampos() {
        // Validação do campo Descrição
        if (TextUtils.isEmpty(editDescricao.getText().toString().trim())) {
            editDescricao.setError("Descrição é obrigatória");
            return false;
        }

        // Validação do campo Código de Barra
        if (TextUtils.isEmpty(editCodigoBarra.getText().toString().trim())) {
            editCodigoBarra.setError("Código de barra é obrigatório");
            return false;
        }

        // Validação do campo Preço
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

        // Validação do campo Fornecedor
        if (TextUtils.isEmpty(editFornecedor.getText().toString().trim())) {
            editFornecedor.setError("Fornecedor é obrigatório");
            return false;
        }

        private void salvarProduto() {
        String descricao = editDescricao.getText().toString().trim();
        String codigoBarra = editCodigoBarra.getText().toString().trim();
        double preco = Double.parseDouble(editPreco.getText().toString().trim());
        String fornecedor = editFornecedor.getText().toString().trim();
    
        // Você precisará obter o id do fornecedor baseado no nome ou adicioná-lo à tabela de fornecedores
        long fornecedorId = dbHelper.addFornecedor(fornecedor, preco, codigoBarra, descricao); // Modifique conforme necessário
    
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


        return true; // Se todos os campos forem válidos
    }
}
