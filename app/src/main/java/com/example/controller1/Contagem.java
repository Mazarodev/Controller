package com.example.controller1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Contagem extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1001;

    private LinearLayout layoutTelaInicial;
    private LinearLayout layoutTelaFinal;
    private TextView textoDescricao;
    private EditText editCodigoBarras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contagem);

        layoutTelaInicial = findViewById(R.id.layout_tela_inicial);
        layoutTelaFinal = findViewById(R.id.layout_tela_final);
        textoDescricao = findViewById(R.id.texto_descricao);
        editCodigoBarras = findViewById(R.id.edit_codigo_barras);
        Button botaoEscanear = findViewById(R.id.botao_escanear);

        // Configura listener para o botão de escanear
        botaoEscanear.setOnClickListener(v -> verificarPermissaoCamera());

        Button botaoSalvar = findViewById(R.id.botao_salvar);
        botaoSalvar.setOnClickListener(v -> salvarNovoProduto());

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
                editCodigoBarras.setText(codigoBarras);
                validarCodigoBarras(codigoBarras);
            }
        }
    }

    public void voltar(View view) {
        onBackPressed();
    }

    private void validarCodigoBarras(String codigo) {
        CadastroProdutos produto = consultaProdutoNoBanco(codigo);

        if (produto != null) {
            mostrarTelaFinal(produto);
        } else {
            exibirOpcaoNovoProduto();
        }
    }

    private CadastroProdutos consultaProdutoNoBanco(String codigo) {
        if (codigo.equals("0745888745411")) {
            return new CadastroProdutos();
        }
        return null;
    }

    private void mostrarTelaFinal(CadastroProdutos produto) {

        layoutTelaInicial.setVisibility(View.GONE);
        layoutTelaFinal.setVisibility(View.VISIBLE);
    }

    private void exibirOpcaoNovoProduto() {
        textoDescricao.setText("Produto não encontrado! Deseja cadastrar?");
        layoutTelaInicial.setVisibility(View.GONE);
        layoutTelaFinal.setVisibility(View.VISIBLE);
    }

    private void salvarNovoProduto() {
        EditText editQuantidade = findViewById(R.id.edit_quantidade); // Campo de quantidade
        String quantidadeStr = editQuantidade.getText().toString();
        String codigoBarras = editCodigoBarras.getText().toString();

        if (quantidadeStr.isEmpty()) {
            editQuantidade.setError("Quantidade obrigatória!");
            return;
        }

        int quantidade = Integer.parseInt(quantidadeStr);

        // Simulação de dados básicos do produto (pode adicionar mais campos)
        String descricaoProduto = "Produto Novo"; // Você pode solicitar uma descrição ao usuário
        double preco = 0.0; // Preço padrão ou fornecido pelo usuário

        // Salvando no banco de dados
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String fornecedor = "";
        Produto novoProduto = new Produto(descricaoProduto, codigoBarras, preco, fornecedor);

        boolean sucesso = dbHelper.addProduto(novoProduto);
        if (sucesso) {
            textoDescricao.setText("Produto salvo com sucesso!");
            editQuantidade.setText(""); // Limpa o campo
        } else {
            textoDescricao.setText("Erro ao salvar produto.");
        }

        // Opcional: voltar para a tela inicial ou encerrar a atividade
        layoutTelaFinal.setVisibility(View.GONE);
        layoutTelaInicial.setVisibility(View.VISIBLE);
    }

}

