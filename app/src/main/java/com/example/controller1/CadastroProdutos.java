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

        return true; // Se todos os campos forem válidos
    }
}