
package Model;



public class Despesas {
    
    private float valor;
    private String categoria;
    private String descricao;
    private String data_insercao;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setData_insercao(String data_insercao) {
        this.data_insercao = data_insercao;
    }

    public String getData_insercao() {
        return data_insercao;
    }
    

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
