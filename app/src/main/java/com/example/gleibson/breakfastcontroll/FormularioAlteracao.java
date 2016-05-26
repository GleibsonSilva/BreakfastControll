package com.example.gleibson.breakfastcontroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dados.Colega;
import dao.ColegaDAO;
import helper.AlteracaoHelper;
import helper.FormularioHelper;

/**
 * Created by gleibson on 02/09/15.
 */
public class FormularioAlteracao extends Activity {

    private Button botao;
    Colega colega = new Colega();
    private AlteracaoHelper helper;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_alteracao);

        helper = new AlteracaoHelper(this);
        botao = (Button) findViewById(R.id.sbSalvar);
        Colega colegaParaSerAlterado = (Colega) getIntent().getSerializableExtra(
                "COLEGA_SELECIONADO");
        if (colegaParaSerAlterado != null){
            helper.setColega(colegaParaSerAlterado);
        }
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Colega colega = helper.getColega();
                ColegaDAO dao = new ColegaDAO(FormularioAlteracao.this);
                if (colega.getNome()!=null){
                    dao.cadastrar(colega);
                }else{
                    dao.alterar(colega);
                }
                dao.close();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_colegas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listar_colegas:
                Toast.makeText(FormularioAlteracao.this, "Lista de colegas",
                        Toast.LENGTH_SHORT).show();
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
