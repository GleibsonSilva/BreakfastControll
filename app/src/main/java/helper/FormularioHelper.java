package helper;

import android.widget.EditText;

import com.example.gleibson.breakfastcontroll.FormularioCadastro;
import com.example.gleibson.breakfastcontroll.R;

import dados.Colega;

/**
 * Created by gleibson on 22/07/15.
 */
public class FormularioHelper {

    private EditText nome;
    private Colega colega;

    public FormularioHelper(FormularioCadastro activity) {
        //Associação de campos
        nome = (EditText) activity.findViewById(R.id.edNome);
        colega = new Colega();
    }

    public Colega getColega() {
        colega.setNome(nome.getText().toString());
        return colega;
    }

    public void setColega(Colega colega){
        //Atualização do campo da tela de formulário
        nome.setText(colega.getNome());
        this.colega = colega;
    }
}
