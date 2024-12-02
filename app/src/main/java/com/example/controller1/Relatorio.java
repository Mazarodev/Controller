package com.example.controller1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Relatorio extends AppCompatActivity {

    private ImageView logoImageView;
    private TextView gerarTextView;
    private TextView dataTextView;
    private ImageView tabelaImageView;
    private ImageView searchIcon;
    private ImageView downloadIcon;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        // Inicialização dos componentes da interface
        logoImageView = findViewById(R.id.logoImageView);
        gerarTextView = findViewById(R.id.gerarTextView);
        dataTextView = findViewById(R.id.dataTextView);
        tabelaImageView = findViewById(R.id.tabelaImageView);
        searchIcon = findViewById(R.id.ic_search);
        downloadIcon = findViewById(R.id.ic_download_relatorio);
        backIcon = findViewById(R.id.ic_iconback);

        // Configuração dos listeners
        setupListeners();
    }

    private void setupListeners() {
        // Listener para o botão de pesquisa
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação para pesquisa
                // Exemplo: Toast.makeText(Relatorio.this, "Buscar relatório...", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para o botão de download
        downloadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação para download do relatório
                // Exemplo: Toast.makeText(Relatorio.this, "Baixando relatório...", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para o botão de voltar
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza a atividade e volta para a anterior
            }
        });
    }
}
