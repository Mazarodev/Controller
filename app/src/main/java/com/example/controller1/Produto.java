package com.example.controller1;

public class Produto {
    private String descricao;
    private String codigoBarras;
    private double preco;
    private String fornecedor;
    private String quantidade;

    public Produto(String descricao, String codigoBarras, double preco, String fornecedor, String quantidade) {
        this.descricao = descricao;
        this.codigoBarras = codigoBarras;
        this.preco = preco;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getQuantidade() {  return quantidade;  }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}
