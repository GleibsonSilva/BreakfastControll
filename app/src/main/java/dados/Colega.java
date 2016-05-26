package dados;

import java.io.Serializable;

/**
 * Created by gleibson on 22/07/15.
 */
public class Colega implements Serializable{

    private Long id;
    private String nome;
    private String valor;

    @Override
    public String toString(){
        return nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
