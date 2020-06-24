package br.lpiii.atividadeandroidsqlite;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtigosActivity extends ListActivity {

    private BancoDeDados db;
    private List<Artigo> artigos = new ArrayList<Artigo>();
    private ArtigosAdapter artigosAdapter;
    public static final int REQUEST_EDICAO = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artigos);

        db = new BancoDeDados(this);
        try {
            lerDados();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void lerDados() throws SQLException {
        db.abrir();
        artigos.clear();
        Cursor cursor = db.retornaTodosArtigos();
        if (cursor.moveToFirst()) {
            do {
                Artigo a = new Artigo();
                a.id = cursor.getInt
                        (cursor.getColumnIndex(BancoDeDados.KEY_ID));
                a.nome = cursor.getString
                        (cursor.getColumnIndex(BancoDeDados.KEY_NOME));
                a.revista = cursor.getString
                        (cursor.getColumnIndex(BancoDeDados.KEY_REVISTA));
                a.edicao = cursor.getString
                        (cursor.getColumnIndex(BancoDeDados.KEY_EDICAO));
                a.status = cursor.getInt
                        (cursor.getColumnIndex(BancoDeDados.KEY_STATUS));
                a.pago = cursor.getInt
                        (cursor.getColumnIndex(BancoDeDados.KEY_PAGO));
                artigos.add(a);
            } while (cursor.moveToNext());
        }

        if (artigos.size() > 0) {
            if (artigosAdapter == null) {
                artigosAdapter = new ArtigosAdapter(this, artigos) {
                    @Override
                    public void edita(Artigo artigo) {
                        Intent intent = new Intent(getApplicationContext(),
                                NovoEdicaoActivity.class);
                        intent.putExtra("artigo", artigo);
                        startActivityForResult(intent, REQUEST_EDICAO);
                    }

                    @Override
                    public void deleta(Artigo artigo) {
                        try {
                            db.abrir();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        db.apagaArtigo(artigo.id);
                        db.fechar();

                    }
                };
                setListAdapter(artigosAdapter);
            } else {
                artigosAdapter.novosDados(artigos);
            }
        }
        db.fechar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_artigos, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent(this, NovoEdicaoActivity.class);
            startActivityForResult(intent, REQUEST_EDICAO);
            return true;
        } else {
            return super.onMenuItemSelected(featureId, item);
        }
    }

    public static final int REQUEST_SALVOU = 1;

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_EDICAO) {
            if (resultCode == REQUEST_SALVOU) {
                try {
                    lerDados();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
