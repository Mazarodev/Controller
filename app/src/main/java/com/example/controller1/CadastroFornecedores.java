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
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button btnSalvarFornecedor = findViewById(R.id.btn_Salvar2);

        databaseHelper = new DatabaseHelper(this);

        // Configurando o clique do botão Salvar
        btnSalvarFornecedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    // Se todos os campos forem válidos, exibe uma mensagem de sucesso
                    Toast.makeText(CadastroFornecedores.this, "Fornecedor cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Aqui você pode adicionar o código para salvar os dados no banco ou outra ação desejada
                }
            }
        });
    }

    // Método para validar os campos
    private boolean validarCampos() {
    if (TextUtils.isEmpty(editRazaoSocial.getText().toString().trim())) {
        editRazaoSocial.setError("Razão Social é obrigatória");
        return false;
    }

    if (TextUtils.isEmpty(editCnpj.getText().toString().trim())) {
        editCnpj.setError("CNPJ é obrigatório");
        return false;
    } else if (!validarCnpj(editCnpj.getText().toString().trim())) {
        editCnpj.setError("CNPJ inválido");
        return false;
    }

    if (TextUtils.isEmpty(editEndereco.getText().toString().trim())) {
        editEndereco.setError("Endereço é obrigatório");
        return false;
    }

    if (TextUtils.isEmpty(editContato.getText().toString().trim())) {
        editContato.setError("Contato é obrigatório");
        return false;
    } else if (!Patterns.PHONE.matcher(editContato.getText().toString().trim()).matches()) {
        editContato.setError("Contato inválido");
        return false;
    }

    private boolean validarCnpj(String cnpj) {
    if (cnpj.length() != 14) return false;

    try {
        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0, digito1, digito2;

        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
        }
        digito1 = soma % 11 < 2 ? 0 : 11 - soma % 11;

        soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
        }
        soma += digito1 * peso2[12];
        digito2 = soma % 11 < 2 ? 0 : 11 - soma % 11;

        return cnpj.endsWith(digito1 + "" + digito2);
    } catch (Exception e) {
        return false; // Se algo falhar, considera inválido
    }
}

    return true; // Validação passou
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
        Intent intent = new Intent(CadastroFornecedores.this, MenuComun.class);
        startActivity(intent);
        finish(); // Finaliza a atividade atual
    } else {
        Toast.makeText(this, "Erro ao salvar fornecedor.", Toast.LENGTH_SHORT).show();
        Log.e("CadastroFornecedores", "Erro ao salvar fornecedor: " + razaoSocial);
    }
}
