package helper;

import android.widget.EditText;

import com.example.gleibson.breakfastcontroll.FormularioAlteracao;
import com.example.gleibson.breakfastcontroll.R;

import dados.Colega;

/**
 * Created by gleibson on 02/09/15.
 */
public class AlteracaoHelper {
    private EditText nome;
    private EditText valor;
    private Colega colega;

    public AlteracaoHelper(FormularioAlteracao activity){
        nome = (EditText) activity.findViewById(R.id.edNome);
        valor = (EditText) activity.findViewById(R.id.edValor);
        colega = new Colega();
    }

    public Colega getColega() {
        colega.setNome(nome.getText().toString());
        colega.setValor(valor.getText().toString());
        return colega;
    }

    public void setColega(Colega colega) {
        nome.setText(colega.getNome());
        valor.setText(colega.getValor());
        this.colega = colega;
    }
}
