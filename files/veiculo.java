public class veiculo {

    private int idcarro;
    private String marca;
    private String modelo;
    private String placa;
    private int ano;
    private double valorLocacao;
    boolean disponibilidade;

    public veiculo(String marca, String modelo, String placa, int ano, double valorLocacao){
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
        this.valorLocacao = valorLocacao;
        this.disponibilidade = true;
    }

    public int getIdcarro() {
        return idcarro;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public double getvalorLocacao() {
        return valorLocacao;
    }

    public int getAno() {
        return ano;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void alugar(){
        disponibilidade= false;
    }

    public void devolver(){
        disponibilidade = true;
    }

}
