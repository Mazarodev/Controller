package com.example.controller1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Contagem extends AppCompatActivity {

    private LinearLayout layoutTelaInicial;
    private LinearLayout layoutTelaFinal;
    private TextView textoDescricao; // Para exibir a descrição do produto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contagem);

        // Referências para os layouts e componentes
        layoutTelaInicial = findViewById(R.id.layout_tela_inicial);
        layoutTelaFinal = findViewById(R.id.layout_tela_final);
        textoDescricao = findViewById(R.id.texto_descricao);
        EditText editCodigoBarras = findViewById(R.id.edit_codigo_barras);

        // Configura listener para leitura do código de barras
        editCodigoBarras.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String codigo = editCodigoBarras.getText().toString();
                    validarCodigoBarras(codigo);
                    return true;
                }
                return false;
            }
        });
    }

    public void voltar(View view) {
        onBackPressed();
    }


    // Função para validar o código de barras no banco de dados
    private void validarCodigoBarras(String codigo) {
        // Simula uma consulta no banco de dados
        CadastroProdutos produto = consultaProdutoNoBanco(codigo);

        if (produto != null) {
            // Produto encontrado: exibe a tela final com os detalhes
            mostrarTelaFinal(produto);
        } else {
            // Produto não encontrado: exibe mensagem ou ação
            exibirOpcaoNovoProduto();
        }
    }

    // Exemplo de consulta no banco de dados (dummy)
    private CadastroProdutos consultaProdutoNoBanco(String codigo) {
        // Simule a consulta aqui
        // Exemplo de retorno de produto fictício
        if (codigo.equals("0745888745411")) {
            return new CadastroProdutos();
        }
        return null; // Retorna null se não encontrar
    }

    @SuppressLint("SetTextI18n")
    private void mostrarTelaFinal(CadastroProdutos produto) {
        // Atualiza as informações do produto na tela final
        textoDescricao.setText(
                "DESCRIÇÃO: " + produto.getDescricao() + "\n" +
                        "CÓDIGO DE BARRAS: " + produto.getCodigoBarras() + "\n" +
                        "PREÇO: R$" + produto.getPreco() + "\n" +
                        "FORNECEDOR: " + produto.getFornecedor()
        );

        // Troca as telas
        layoutTelaInicial.setVisibility(View.GONE); // Esconde a tela inicial
        layoutTelaFinal.setVisibility(View.VISIBLE); // Exibe a tela final
    }

    private void exibirOpcaoNovoProduto() {
        // Implementa lógica para produtos não encontrados (exibir mensagem ou ação)
        textoDescricao.setText("Produto não encontrado! Deseja cadastrar?");
        layoutTelaInicial.setVisibility(View.GONE);
        layoutTelaFinal.setVisibility(View.VISIBLE);
    }


}

