package br.lpiii.atividadeandroidsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class BancoDeDados {
    static String KEY_ID = "_id";
    static String KEY_NOME = "nome";
    static String KEY_REVISTA = "revista";
    static String KEY_EDICAO = "edicao";
    static String KEY_STATUS = "status";
    static String KEY_PAGO = "pago";

    String NOME_BANCO = "db_Revistas";
    String NOME_TABELA = "artigo";
    int VERSAO_BANCO = 1;
    String SQL_CREATE_TABLE = "create table contacts " +
            "(" + KEY_ID + " integer primary key autoincrement, "
            + KEY_NOME + " text not null, " +
            KEY_REVISTA + " text, " +
            KEY_EDICAO + " text, " +
            KEY_STATUS + " integer, " +
            KEY_PAGO + " integer);";


    final Context context;
    MeuOpenHelper openHelper;
    SQLiteDatabase db;


    public BancoDeDados(Context ctx) {
        this.context = ctx;
        openHelper = new MeuOpenHelper(context);
    }

    private class MeuOpenHelper extends SQLiteOpenHelper {
        MeuOpenHelper(Context context) {
            super(context, NOME_BANCO, null, VERSAO_BANCO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    }

    public BancoDeDados abrir() throws SQLException {
        db = openHelper.getWritableDatabase();
        return this;
    }

    public void fechar() {
        openHelper.close();
    }

    public long insereArtigo(String nome, String revista, String edicao,
                             int status, int pago) {
        ContentValues campos = new ContentValues();
        campos.put(KEY_NOME, nome);
        campos.put(KEY_REVISTA, revista);
        campos.put(KEY_EDICAO, edicao);
        campos.put(KEY_STATUS, status);
        campos.put(KEY_PAGO, pago);
        return db.insert(NOME_TABELA, null, campos);
    }

    public boolean apagaArtigo(long id) {
        return db.delete(NOME_TABELA, KEY_ID + "=" + id, null) > 0;
    }

    public Cursor retornaTodosArtigos() {
        return db.query(NOME_TABELA, new String[]{KEY_ID, KEY_NOME,
                        KEY_REVISTA,
                        KEY_EDICAO, KEY_STATUS, KEY_PAGO}, null, null, null, null,
                null);
    }

    public boolean atualizaArtigo(long id, String nome, String revista,
                                  String edicao,
                                  int status, int pago) {
        ContentValues args = new ContentValues();
        args.put(KEY_NOME, nome);
        args.put(KEY_REVISTA, revista);
        args.put(KEY_EDICAO, edicao);
        args.put(KEY_STATUS, status);
        args.put(KEY_PAGO, pago);
        return db.update(NOME_TABELA, args, KEY_ID + "=" + id, null)
                > 0;
    }
}