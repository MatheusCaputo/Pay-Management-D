
package Model;



public class Usuario {
    
    private Usuario(){}
    
    private static Usuario instance = null;
    
    private String nome; 
    private String cpf;
    private float salario;
    private String senha;
   
    public static Usuario getInstance(){
        if(instance == null){
            instance = new Usuario();
        }
        return instance;
    }
    public void Usuario(Usuario u){
        this.cpf = u.getCpf();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
     public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public float getSalario() {
        return salario;
    }

    public String getSenha() {
        return senha;
    }

   
}
