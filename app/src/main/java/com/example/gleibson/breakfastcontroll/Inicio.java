package com.example.gleibson.breakfastcontroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Inicio extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //verificar item selecionado do menu
        switch (item.getItemId()){
          //Verifica o item selecionado
            case R.id.listar_colegas:
                Intent intent1 = new Intent(Inicio.this, Lista.class);
                startActivity(intent1);
                return false;

            case R.id.novo_colega:
                //mudança de tela
                Intent intent2 = new Intent(Inicio.this, FormularioCadastro.class);
                //Carregar nova tela
                startActivity(intent2);
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
