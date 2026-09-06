public class cadastroCliente {

    private int IDCliente;
    public String login;
    private String Nome;
    private String CPF;
    private String Endereco;
    private String email;
    private String Telefone;
    private String Senha;

    public cadastroCliente(String Nome, String CPF, String Endereco, String email, String Telefone, String Senha){
        this.Nome = Nome;
        this.CPF = CPF;
        this.Endereco = Endereco;
        this.email = email;
        this.Telefone = Telefone;
        this.Senha = Senha;

    }

    public String getNome() {
        return Nome;
    }

    public String getEndereco() {
        return Endereco;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public String getCPF() {
        return CPF;
    }

    public String getSenha() {
        return Senha;
    }



}
