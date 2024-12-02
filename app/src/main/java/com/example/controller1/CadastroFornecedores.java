package com.example.controller1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroFornecedores extends AppCompatActivity {

    private EditText editRazaoSocial, editCnpj, editEndereco, editContato;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fornecedores);

        // Inicializando os campos
        editRazaoSocial = findViewById(R.id.edit_RazaoSocial);
        editCnpj = findViewById(R.id.edit_CNPJ);
        editEndereco = findViewById(R.id.edit_Endereco);
        editContato = findViewById(R.id.edit_Contato);
        Button btnSalvarFornecedor = findViewById(R.id.btn_Salvar2);

        databaseHelper = new DatabaseHelper(this);

        // Configurando o clique do botão Salvar
        btnSalvarFornecedor.setOnClickListener(v -> {
            if (validarCampos()) {
                salvarFornecedor();
            }
        });
    }

    public void voltar(View view) {
        onBackPressed();
    }


    // Método para validar os campos
    private boolean validarCampos() {
        if (isCampoVazio(editRazaoSocial, "Razão Social é obrigatória")) return false;
        if (isCampoVazio(editCnpj, "CNPJ é obrigatório")) return false;
        if (!validarCnpj(editCnpj.getText().toString().trim())) {
            editCnpj.setError("CNPJ inválido");
            return false;
        }
        if (isCampoVazio(editEndereco, "Endereço é obrigatório")) return false;
        if (isCampoVazio(editContato, "Contato é obrigatório")) return false;
        if (!Patterns.PHONE.matcher(editContato.getText().toString().trim()).matches()) {
            editContato.setError("Contato inválido");
            return false;
        }
        return true;
    }

    // Método auxiliar para verificar campos vazios
    private boolean isCampoVazio(EditText campo, String mensagemErro) {
        if (TextUtils.isEmpty(campo.getText().toString().trim())) {
            campo.setError(mensagemErro);
            return true;
        }
        return false;
    }

    // Método para validar o CNPJ
    private boolean validarCnpj(String cnpj) {
        if (!cnpj.matches("\\d{14}")) return false;

        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma1 = calcularSoma(cnpj, peso1, 12);
        int digito1 = calcularDigito(soma1);

        int soma2 = calcularSoma(cnpj, peso2, 12) + digito1 * peso2[12];
        int digito2 = calcularDigito(soma2);

        return cnpj.endsWith(digito1 + "" + digito2);
    }

    private int calcularSoma(String cnpj, int[] pesos, int length) {
        int soma = 0;
        for (int i = 0; i < length; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }
        return soma;
    }

    private int calcularDigito(int soma) {
        return soma % 11 < 2 ? 0 : 11 - soma % 11;
    }

    // Método para salvar fornecedor no banco
    private void salvarFornecedor() {
        String razaoSocial = editRazaoSocial.getText().toString().trim();
        String cnpj = editCnpj.getText().toString().trim();
        String endereco = editEndereco.getText().toString().trim();
        String contato = editContato.getText().toString().trim();

        boolean sucesso = databaseHelper.addFornecedor(razaoSocial, cnpj, endereco, contato);

        if (sucesso) {
            Toast.makeText(this, "Fornecedor salvo com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CadastroFornecedores.this, MenuComun.class));
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar fornecedor.", Toast.LENGTH_SHORT).show();
            Log.e("CadastroFornecedores", "Erro ao salvar fornecedor: " + razaoSocial);
        }
    }
}
