import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class locacao {

    private cadastroCliente cliente;
    private veiculo veiculo;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private double valorTotal;
    private boolean finalizado;

    public locacao(cadastroCliente cliente, veiculo veiculo, LocalDate dataRetirada, LocalDate dataDevolucao, double valorTotal, boolean finalizado){
        if (dataDevolucao.isBefore(dataRetirada)){
            throw new IllegalArgumentException("A data de devolução não pode ser anterior a data de retirada");
        }
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorTotal = valorTotal;
        this.finalizado = false;

        calculaValor();
    }


    private void calculaValor(){
        long dias = ChronoUnit.DAYS.between(dataRetirada,dataDevolucao);

        if(dias == 0){
            dias = 1;
        }

        this.valorTotal= dias * veiculo.getvalorLocacao();

    }

    public cadastroCliente getcliente() {
        return cliente;
    }

    public veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void finalizar(){
        finalizado=true;
        veiculo.devolver();
    }

}
