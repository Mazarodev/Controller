package com.example.controller1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroFornecedores extends AppCompatActivity {

    private EditText editRazaoSocial, editCnpj, editEndereco, editContato;

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
        // Validação do campo Razão Social
        if (TextUtils.isEmpty(editRazaoSocial.getText().toString().trim())) {
            editRazaoSocial.setError("Razão Social é obrigatória");
            return false;
        }

        // Validação do campo CNPJ
        if (TextUtils.isEmpty(editCnpj.getText().toString().trim())) {
            editCnpj.setError("CNPJ é obrigatório");
            return false;
        } else if (!validarCnpj(editCnpj.getText().toString().trim())) {
            editCnpj.setError("CNPJ inválido");
            return false;
        }

        // Validação do campo Endereço
        if (TextUtils.isEmpty(editEndereco.getText().toString().trim())) {
            editEndereco.setError("Endereço é obrigatório");
            return false;
        }

        // Validação do campo Contato
        if (TextUtils.isEmpty(editContato.getText().toString().trim())) {
            editContato.setError("Contato é obrigatório");
            return false;
        } else if (!Patterns.PHONE.matcher(editContato.getText().toString().trim()).matches()) {
            editContato.setError("Contato inválido");
            return false;
        }

        return true; // Se todos os campos forem válidos
    }

    // Método para validar o CNPJ (implementação simplificada)
    private boolean validarCnpj(String cnpj) {
        // Adicione a lógica para validação de CNPJ, caso necessário.
        // Esta é apenas uma validação básica de comprimento.
        return cnpj.length() == 14;
    }
}