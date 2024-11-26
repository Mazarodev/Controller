package com.example.controller1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BancoController.db";
    private static final int DATABASE_VERSION = 1;

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
                "id_fornecedor INTEGER," +
                "FOREIGN KEY(id_fornecedor) REFERENCES " + TABLE_FORNECEDOR + "(id_fornecedor))");

        // Tabela Estoque
        db.execSQL("CREATE TABLE " + TABLE_ESTOQUE + " (" +
                "id_estoque INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_produto INTEGER," +
                "qtd INTEGER," +
                "data_entrada DATETIME," +
                "FOREIGN KEY(id_produto) REFERENCES " + TABLE_PRODUTO + "(id_produto))");

        // Tabela Usuario
        db.execSQL("CREATE TABLE " + TABLE_USUARIO + " (" +
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "email TEXT UNIQUE," + // Adicionado UNIQUE para evitar duplicatas
                "senha TEXT," +
                "tipo_usuario TEXT NOT NULL CHECK(tipo_usuario IN ('comum', 'administrador')))");

        // Tabela Contagem
        db.execSQL("CREATE TABLE " + TABLE_CONTAGEM + " (" +
                "id_contagem INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_produto INTEGER," +
                "qtd_contada INTEGER," +
                "data_contagem DATETIME," +
                "id_usuario INTEGER," +
                "FOREIGN KEY(id_produto) REFERENCES " + TABLE_PRODUTO + "(id_produto)," +
                "FOREIGN KEY(id_usuario) REFERENCES " + TABLE_USUARIO + "(id_usuario))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORNECEDOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTOQUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAGEM);
        onCreate(db);
    }

    // Método para adicionar um produto
    public boolean addProduto(String descricao, String codigoBarra, double preco, int idFornecedor) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("descricao", descricao);
    values.put("codigo_barra", codigoBarra);
    values.put("preco", preco);
    values.put("id_fornecedor", idFornecedor);

    long result = db.insert(TABLE_PRODUTO, null, values);
    db.close(); // Fechar o banco após a operação
    return result != -1; // Retorna true se a inserção foi bem-sucedida
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

    public int getFornecedorIdByName(String razaoSocial) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_FORNECEDOR, new String[]{"id_fornecedor"},
            "razao_social=?", new String[]{razaoSocial},
            null, null, null);

    int id = -1;
    if (cursor.moveToFirst()) {
        id = cursor.getInt(cursor.getColumnIndexOrThrow("id_fornecedor"));
    }
    cursor.close();
    return id; // Retorna o ID ou -1 se não encontrado
    }

    // Método de verificação de código de barra
    public boolean codigoBarraExiste(String codigoBarra) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_PRODUTO, new String[]{"id_produto"},
            "codigo_barra=?", new String[]{codigoBarra},
            null, null, null);

    boolean existe = cursor.getCount() > 0;
    cursor.close();
    return existe; // Retorna true se o código de barras já existir
    }

    // Método para verificar o tipo do usuário através do email
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
