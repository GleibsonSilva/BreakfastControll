package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.Colega;

/**
 * Created by gleibson on 23/07/15.
 */
public class ColegaDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "Colega";
    private static final String DATABASE = "MPColegas";

    private ContentValues values = new ContentValues();

    //constante para logcat
    private static final String TAG = "CADASTRO_COLEGA";

    public ColegaDAO(Context context){
        //Construtor que sabe acessar o BD
        super(context, DATABASE, null, VERSAO);
    }

    public ColegaDAO(Context context, String name, SQLiteDatabase.CursorFactory factory,
                     int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Definição do comando DDL a ser executado
        String ddl = "CREATE TABLE " + TABELA + "( "
                + "id INTEGER PRIMARY KEY"
                + "nome TEXT, valor TEXT)";
        //Execução do comando no SQLite
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Definição do comando para destruir a tabela Colega
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        //Execução do comando
        db.execSQL(sql);
        //Chamada ao metodo de construção da base de dados
        onCreate(db);
    }

    public void cadastrar(Colega colega){
        //Objeto para armazenamento dos valores
        //ContentValues values = new ContentValues();
        //Definição de valores dos campos da tabela
        values.put("nome", colega.getNome());
        //Inserir dados do colega no BD
        getWritableDatabase().insert(TABELA, null, values);
        Log.i(TAG, "Colega cadastrado: " + colega.getNome());
    }

    public void pagar(Colega colega){
        //ContentValues values = new ContentValues();
        values.put("valor", colega.getValor());
        getWritableDatabase().insert(TABELA, null, values);
        Log.i(TAG, "Pagamento efetuado para: " + colega.getNome());
    }

    public List<Colega> listar() {
        //Definição da coleção de colegas
        List<Colega> lista = new ArrayList<Colega>();
        //Definição da isntrução SQL
        String sql = "Select * from Colega order by nome";
        //Objeto que recebe os registros do BD
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                //Criação de nova referencia para colega
                Colega colega = new Colega();
                //Carregar os atributos de Colega com dados do BD
                colega.setId(cursor.getLong(0));
                colega.setNome(cursor.getString(1));
                colega.setValor(cursor.getString(2));
                //Adicionar novo Colega a lista
                lista.add(colega);
            }
        } finally {
            cursor.close();
        }
        return lista;
    }

    public void apagar(Colega colega){
        //Definição do array de parametros
        String[] args = {colega.getId().toString()};
        //Exclusão do Colega
        getWritableDatabase().delete(TABELA, "id=?", args);
        //Aviso
        Log.i(TAG, "Colega " + colega.getNome() + " apagado!");
    }

    public void alterar(Colega colega){
        ContentValues values = new ContentValues();
        values.put("nome", colega.getNome());
        values.put("valor", colega.getValor());
        //Coleção de valores de parametros do SQL
        String[] args = {colega.getId().toString()};
        //Alterar dados do Colega no BD
        getWritableDatabase().update(TABELA, values, "id=?", args);
        Log.i(TAG, "Colega " + colega.getNome() + ", alterado!");
    }
}
