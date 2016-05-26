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
import helper.FormularioHelper;


public class FormularioCadastro extends Activity {

    private Button botao;
    Colega colega = new Colega();
    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        //Criação do objeto helper
        helper = new FormularioHelper(this);
        //Ligação dos atributos aos componentes de tela
        botao = (Button) findViewById(R.id.sbSalvar);
        //Busca o colega a ser alterado
        Colega colegaParaSerAlterado = (Colega) getIntent().getSerializableExtra(
                "COLEGA_SELECIONADO");
        if (colegaParaSerAlterado!=null) {
            //Atualiza a tela com os dados do Colega
            helper.setColega(colegaParaSerAlterado);
        }
        //Disponibilização do clique no botão
        botao.setOnClickListener(new View.OnClickListener() {
              @Override
            public void onClick(View v) {
                  //Utilização do Helper para recuperar dados do colega
                  Colega colega = helper.getColega();
                  //Criação do objeto DAO e inicio da conexão com o BD
                  ColegaDAO dao = new ColegaDAO(FormularioCadastro.this);
                  //Verificando para salvar ou cadastrar o aluno
                  if (colega.getNome()!=null) {
                      //Chamada do metodo de cadastro do colega
                      dao.cadastrar(colega);
                  }else{
                      dao.alterar(colega);
                  }
                  //Encerrar a conexão com o BD
                  dao.close();
                  //Encerra a Activity
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
                Toast.makeText(FormularioCadastro.this, "Lista de colegas",
                        Toast.LENGTH_SHORT).show();
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
