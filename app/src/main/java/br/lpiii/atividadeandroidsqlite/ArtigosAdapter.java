package br.lpiii.atividadeandroidsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public abstract class ArtigosAdapter extends BaseAdapter {

    private List<Artigo> artigos;
    private LayoutInflater inflater;

    public ArtigosAdapter(Context context, List<Artigo> artigos) {
        this.inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.artigos = artigos;
    }

    public void novosDados(List<Artigo> artigos) {
        this.artigos = artigos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return artigos.size();
    }

    @Override
    public Object getItem(int position) {
        return artigos.get(position);
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_artigo, null);
        ((TextView) (v.findViewById(R.id.txtNome)))
                .setText(artigos.get(position).nome);

        ((ImageButton) (v.findViewById(R.id.btnEditar))).setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edita(artigos.get(position));
                    }
                });
        ((ImageButton) (v.findViewById(R.id.btnExcluir)))
                .setOnClickListener
                        (new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                deleta(artigos.get(position));
                            }
                        });

        return v;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public abstract void edita(Artigo artigo);

    public abstract void deleta(Artigo artigo);
}