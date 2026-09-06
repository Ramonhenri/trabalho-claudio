import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class principal {

    static Scanner scanner = new Scanner(System.in);
    static lojaLocadora loja = new lojaLocadora();
    static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        boolean continuarPrograma = true;

        while(continuarPrograma){

            cadastroCliente clienteLogado = autenticarOuCadastrar();

            System.out.println("Bem-vindo, " + clienteLogado.getNome() + "!");

            boolean logout = false;
            int opcao;

            do {
                System.out.print("---MENU PRINCIPAL---\n");
                System.out.print("(1)CONSULTAR DISPONIBILIDADE DE VEÍCULOS\n");
                System.out.print("(2)FAZER LOCAÇÃO\n");
                System.out.print("(3)CADASTRAR NOVO VEÍCULO\n");
                System.out.print("(4)LOGOUT\n");
                System.out.print("(5)ENCERRAR CÓDIGO\n");
                System.out.print("Escolha uma opção: ");

                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        loja.consultarDisponibilidade();
                        break;
                    case 2:
                        fazerLocacao(clienteLogado);
                        break;
                    case 3:
                        System.out.println("Digite a marca do carro:");
                        String marca = scanner.nextLine();

                        System.out.println("Digite o modelo do veículo:");
                        String modelo = scanner.nextLine();

                        System.out.println("Digite a placa do veículo:");
                        String placa = scanner.nextLine();

                        System.out.println("Digite o ano de fabricação do veículo:");
                        int ano = scanner.nextInt();

                        System.out.println("Digite o valor da diária do veículo:");
                        double valorLocacao = scanner.nextDouble();

                        veiculo veiculo = new veiculo(marca, modelo, placa, ano,valorLocacao);
                        break;
                    case 4:
                        System.out.println("Voltando ao menu de login...");
                        logout = true;
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }

            } while (opcao != 4);
        }

    }

    public static cadastroCliente autenticarOuCadastrar() {
        cadastroCliente clienteLogado = null;

        while (clienteLogado == null) {
            System.out.println("Você já possui cadastro? (S/N)");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (resposta.equalsIgnoreCase("Sim")) {
                clienteLogado = fazerLogin();

                if (clienteLogado == null) {
                    System.out.println("Cadastro não encontrado.");
                }

            } else if (resposta.equalsIgnoreCase("Não")) {
                clienteLogado = criarCadastro();

            } else {
                System.out.println("Resposta inválida. Digite Sim ou Não.");
            }
        }

        return clienteLogado;
    }

    public static cadastroCliente fazerLogin() {
        System.out.println("Digite seu email:");
        String email = scanner.nextLine();

        System.out.println("Digite sua senha:");
        String senha = scanner.nextLine();

        return loja.autenticarCliente(email, senha);
    }

    public static cadastroCliente criarCadastro() {
        System.out.println("Digite seu nome:");
        String Nome = scanner.nextLine();

        System.out.println("Digite seu email:");
        String email = scanner.nextLine();

        System.out.println("Digite sua senha:");
        String Senha = scanner.nextLine();

        System.out.println("Digite seu CPF:");
        String CPF = scanner.nextLine();

        System.out.println("Digite seu endereço:");
        String Endereco = scanner.nextLine();

        System.out.println("Digite seu telefone:");
        String Telefone = scanner.nextLine();

        cadastroCliente novoCliente = new cadastroCliente(Nome, CPF, Endereco, email, Telefone, Senha);
        loja.CadastrarCliente(novoCliente);

        System.out.println("Cadastro realizado com sucesso!");
        return novoCliente;
    }

    public static void fazerLocacao(cadastroCliente clienteLogado) {
        System.out.println();
        loja.consultarDisponibilidade();
        System.out.println();

        System.out.println("Digite a placa do veículo que deseja alugar:");
        String placa = scanner.nextLine();

        veiculo veiculoEscolhido = loja.buscarVeiculoPorPlaca(placa);

        if (veiculoEscolhido == null) {
            System.out.println("Veículo não encontrado. Verifique a placa digitada.");
            return;
        }

        if (!veiculoEscolhido.isDisponibilidade()) {
            System.out.println("Esse veículo já está alugado no momento. Escolha outro veículo.");
            return;
        }

        LocalDate dataRetirada = lerData("Digite a data de retirada (dd/MM/yyyy):");
        LocalDate dataDevolucao = lerData("Digite a data de devolução (dd/MM/yyyy):");

        try {
            loja.realizarAluguel(clienteLogado, veiculoEscolhido, dataRetirada, dataDevolucao);

            locacao locacaoCriada = loja.buscarLocacaoAtiva(veiculoEscolhido);

            System.out.println("Locação realizada com sucesso!");
            if (locacaoCriada != null) {
                System.out.println("Veículo: " + veiculoEscolhido.getMarca() + " " + veiculoEscolhido.getModelo()
                        + " (" + veiculoEscolhido.getPlaca() + ")");
                System.out.println("Retirada: " + dataRetirada.format(FORMATO_DATA)
                        + " | Devolução: " + dataDevolucao.format(FORMATO_DATA));
                System.out.println("Total a pagar: R$ " + String.format("%.2f", locacaoCriada.getValorTotal()));
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Não foi possível concluir a locação: " + e.getMessage());
        }
    }

    private static LocalDate lerData(String mensagem) {
        LocalDate data = null;
        while (data == null) {
            System.out.println(mensagem);
            String texto = scanner.nextLine();
            try {
                data = LocalDate.parse(texto, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/yyyy (ex: 25/12/2026).");
            }
        }
        return data;
    }
}
