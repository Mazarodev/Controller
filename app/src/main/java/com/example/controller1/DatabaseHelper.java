package com.example.controller1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BancoController.db";
    private static final int DATABASE_VERSION = 2; // Versão atualizada

    // Tabelas
    private static final String TABLE_FORNECEDOR = "Fornecedor";
    private static final String TABLE_PRODUTO = "Produto";
    private static final String TABLE_ESTOQUE = "Estoque";
    private static final String TABLE_USUARIO = "Usuario";
    private static final String TABLE_CONTAGEM = "Contagem";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabela Fornecedor
        db.execSQL("CREATE TABLE " + TABLE_FORNECEDOR + " (" +
                "id_fornecedor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "razao_social TEXT," +
                "cnpj TEXT," +
                "endereco TEXT," +
                "contato TEXT)");

        // Tabela Produto
        db.execSQL("CREATE TABLE " + TABLE_PRODUTO + " (" +
                "id_produto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT," +
                "codigo_barra TEXT," +
                "preco REAL," +
                "quantidade INTEGER," +
                "id_fornecedor INTEGER," +
                "FOREIGN KEY(id_fornecedor) REFERENCES " + TABLE_FORNECEDOR + "(id_fornecedor))");

        // Tabela Estoque
        db.execSQL("CREATE TABLE " + TABLE_ESTOQUE + " (" +
                "id_estoque INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_produto INTEGER," +
                "data_entrada DATETIME," +
                "FOREIGN KEY(id_produto) REFERENCES " + TABLE_PRODUTO + "(id_produto))");

        // Tabela Usuario
        db.execSQL("CREATE TABLE " + TABLE_USUARIO + " (" +
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "email TEXT UNIQUE," +
                "senha TEXT," +
                "tipo_usuario TEXT NOT NULL CHECK(tipo_usuario IN ('comum', 'administrador')))");

        // Tabela Contagem (Caso exista)
        db.execSQL("CREATE TABLE " + TABLE_CONTAGEM + " (" +
                "id_contagem INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_produto INTEGER," +
                "quantidade INTEGER," +
                "data_contagem DATETIME," +
                "FOREIGN KEY(id_produto) REFERENCES " + TABLE_PRODUTO + "(id_produto))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Em vez de apagar todas as tabelas, apenas faça a atualização da estrutura necessária
        // Exemplo de alteração (adicionar uma coluna):
        if (oldVersion < 2) {
            // Supondo que você adicionou uma nova coluna
            db.execSQL("ALTER TABLE " + TABLE_USUARIO + " ADD COLUMN novoCampo TEXT");
        }

        // Adicione mais lógica para outros upgrades necessários
    }
        // O exemplo acima pode ser expandido com mais verificações de versões caso você adicione mais versões no futuro.

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);  // Caso você precise tratar downgrade (não é recomendado)
    }

    // Método para adicionar um produto
    public boolean addProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("descricao", produto.getDescricao());
        values.put("codigo_barras", produto.getCodigoBarras());
        values.put("preco", produto.getPreco());
        values.put("fornecedor", produto.getFornecedor());
        values.put("quantidade", produto.getQuantidade());

        long result = db.insert("produtos", null, values);
        return result != -1;
    }

    // Método para adicionar um fornecedor
    public boolean addFornecedor(String razaoSocial, String cnpj, String endereco, String contato) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("razao_social", razaoSocial);
        values.put("cnpj", cnpj);
        values.put("endereco", endereco);
        values.put("contato", contato);

        long result = db.insert(TABLE_FORNECEDOR, null, values);
        db.close(); // Fechar o banco após a operação
        return result != -1; // Retorna true se a inserção foi bem-sucedida
    }

    // Método para adicionar um usuário
    public boolean AddUsuario(String nome, String email, String senha, String tipoUsuario) {
        if (verificarEmailExiste(email)) {
            Log.e("DatabaseHelper", "E-mail já registrado: " + email);
            return false; // E-mail já está registrado
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("email", email);
        values.put("senha", senha);
        values.put("tipo_usuario", tipoUsuario); // Adicionando tipo_usuario

        long resultado = db.insert(TABLE_USUARIO, null, values);
        if (resultado == -1) {
            Log.e("DatabaseHelper", "Erro ao inserir usuário: nome=" + nome + ", email=" + email);
            db.close();
            return false; // Retorna false se a inserção falhar
        }
        db.close();
        return true; // Retorna true se a inserção foi bem-sucedida
    }

    public boolean verificarEmailExiste(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean existe = false;

        try {
            cursor = db.query(TABLE_USUARIO, new String[]{"id_usuario"},
                    "email=?", new String[]{email},
                    null, null, null);
            existe = cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return existe;
    }

    public boolean verificarLogin(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                ("SELECT * FROM Usuario WHERE email=? AND senha=?"),
                new String[]{email, senha});

        boolean usuarioExiste = cursor.getCount() > 0; // Verifica se encontrou um registro
        cursor.close();

        return usuarioExiste; // Retorna true se o login for válido
    }

    public String getTipoUsuario(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIO, new String[]{"tipo_usuario"},
                "email=?", new String[]{email},
                null, null, null);

        String tipoUsuario = null;
        if (cursor.moveToFirst()) {
            tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow("tipo_usuario"));
        }
        cursor.close();
        return tipoUsuario; // Retorna o tipo do usuário ou null se não encontrado
    }

    // Método para buscar todos os produtos
    public Cursor getAllProdutos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Produto", null);
    }

    // Método para buscar todos os fornecedores
    public Cursor getAllFornecedores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FORNECEDOR, null);
    }

    // Método para buscar todos os usuários
    public Cursor getAllUsuarios() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USUARIO, null);
    }
}
