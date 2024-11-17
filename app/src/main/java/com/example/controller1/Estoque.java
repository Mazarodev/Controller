package com.example.controller1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Estoque extends AppCompatActivity {

        private ImageView logoImageView;
        private TextView estoqueTextView;
        private TextView dataTextView;
        private ImageView tabelaImageView;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_estoque); //

            // Inicialização dos componentes da interface
            logoImageView = findViewById(R.id.logoImageView);
            estoqueTextView = findViewById(R.id.estoqueTextView);
            dataTextView = findViewById(R.id.dataTextView);
            tabelaImageView = findViewById(R.id.tabelaImageView);

            // Defina ações nos botões ou outros componentes, se necessário
            setupListeners();
        }

        private void setupListeners() {
            // Exemplo de listener para um dos ImageViews
            findViewById(R.id.ic_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ação para o botão de pesquisa
                }
            });

            findViewById(R.id.ic_download_relatorio).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ação para o botão de download do relatório
                }
            });

            findViewById(R.id.ic_iconback).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ação para o botão de voltar
                }
            });
        }

    public ImageView getLogoImageView() {
        return logoImageView;
    }

    public void setLogoImageView(ImageView logoImageView) {
        this.logoImageView = logoImageView;
    }

    public TextView getEstoqueTextView() {
        return estoqueTextView;
    }

    public void setEstoqueTextView(TextView estoqueTextView) {
        this.estoqueTextView = estoqueTextView;
    }

    public TextView getDataTextView() {
        return dataTextView;
    }

    public void setDataTextView(TextView dataTextView) {
        this.dataTextView = dataTextView;
    }

    public ImageView getTabelaImageView() {
        return tabelaImageView;
    }

    public void setTabelaImageView(ImageView tabelaImageView) {
        this.tabelaImageView = tabelaImageView;
    }
}
