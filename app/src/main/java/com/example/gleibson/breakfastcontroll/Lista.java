package com.example.gleibson.breakfastcontroll;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dados.Colega;
import dao.ColegaDAO;


public class Lista extends Activity {

    //Constantes
    private final String TAG = "CADASTRO_COLEGA";
    private final String COLEGAS_KEY = "LISTA";

    //Variáveis de tela
    private ListView listagem;

    //Lista de nomes de colegas a serem exibidos na tela
    private List<Colega> listaColegas;

    //Adaptar a lista para ser exibida em tela
    private SimpleAdapter adapter;

    //Definição do layout de exibição da listagem
    //private int adapterLayout = android.R.layout.simple_list_item_2;

    //Colega selecionado no click longo da ListView
    private Colega colegaSelecionado = null;

    //Variáveis para manipulação dos itens da ListVeiw
    private Colega colega = new Colega();
    private List<Map<String, String>> lista = getLista();
    private String[] from = {colega.getNome(), colega.getValor()};
    private int[] to = {android.R.id.text1, android.R.id.text2};

    //Método para carregar a lista de colegas cadastrados no BD
    public void carregarLista() {
        //Criação do objeto DAO e inicio da conexão com o BD
        ColegaDAO dao = new ColegaDAO(this);
        //Chamada do método listar
        this.listaColegas = dao.listar();
        //Fexando conexão do o BD
        dao.close();
        //ArrayAdapter converte listas em View
        this.adapter = new SimpleAdapter(this, lista, android.R.layout.simple_list_item_2, from, to);
        //Associação do Adapter ao ListView
        this.listagem.setAdapter(adapter);
    }

    //Método usado para manipulação de hash para itens da ListView
    private List<Map<String, String>> getLista() {
        List<Map<String, String>> lista = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 10; i++) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("", colega.getNome());
            m.put("", colega.getValor());
            lista.add(m);
        }
        return lista;
    }

    //Método para carregamento da Lista
    @Override
    protected void onResume(){
        super.onResume();
        //Carregar a coleção
        this.carregarLista();
    }

    //Método de confirmação de exclusão de um colega
    private void excluirColega(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quer mesmo excluir: " + colegaSelecionado.getNome());
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ColegaDAO dao = new ColegaDAO(Lista.this);
                dao.apagar(colegaSelecionado);
                dao.close();
                carregarLista();
                colegaSelecionado = null;
            }
        });
        builder.setNegativeButton("Não", null);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirmação");
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        //Ligação dos componentes da tela aos atributos da Activity
        listagem = (ListView) findViewById(R.id.lvListagem);
        //Informa que a ListView tem Menu de contexto
        registerForContextMenu(listagem);

        //Sente um click simples
        listagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {

                //Intenção de navegar entre Lista e Formulario
                Intent form = new Intent(Lista.this, FormularioAlteracao.class);
                //recupera o colega selecionado
                colegaSelecionado = (Colega)listagem.getItemAtPosition(posicao);
                //Compartilha o objeto Colega com a nova tela
                form.putExtra("COLEGA_SELECIONADO", colegaSelecionado);
                //Inicia a tela de Formulario
                startActivity(form);
            }
        });

        //Sente um click longo
        listagem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                                           int posicao, long id) {
                //Marca o colega selecionado na ListView
                colegaSelecionado = (Colega) adapter.getItemAtPosition(posicao);
                Log.i(TAG, "Colega selecionado ListView.longClick()"
                        + colegaSelecionado.getNome());
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo_colega:
                Intent intent1 = new Intent(Lista.this, FormularioCadastro.class);
                startActivity(intent1);
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contexto, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuApagar:
                excluirColega();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


}
