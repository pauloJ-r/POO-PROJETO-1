package banco.app;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import banco.logica.*;

public class Aplicacao {
	 private static List<Cliente> clientes;
	 private static Object cadastroClientes;

	    public static void main(String[] args) {
	        // Carregar os dados salvos
	    	 cadastroClientes = Banco.carregarDadosDeArquivo();

	        Scanner scanner = new Scanner(System.in);
	        int opcao;
	        do {
	            System.out.println("\nMenu:");
	            System.out.println("1. Cadastrar cliente");
	            System.out.println("2. Listar clientes");
	            System.out.println("3. Consultar cliente por CPF");
	            System.out.println("4. Remover cliente");
	            System.out.println("5. Criar conta");
	            System.out.println("6. Listar contas de um cliente");
	            System.out.println("7. Remover conta");
	            System.out.println("8. Realizar depósito");
	            System.out.println("9. Realizar saque");
	            System.out.println("10. Efetuar transferência");
	            System.out.println("11. Imprimir extrato");
	            System.out.println("12. Consultar saldo");
	            System.out.println("13. Consultar balanço das contas");
	            System.out.println("0. Para sair do sistema");
	            opcao = scanner.nextInt();
	            scanner.nextLine(); 
	            
	           switch (opcao) {
                case 1:
                	 System.out.print("Digite o nome do cliente: ");
                     String nome = scanner.nextLine();

                     System.out.print("Digite o CPF do cliente: ");
                     String cpf = scanner.nextLine();

                     Cliente novoCliente = new Cliente(nome, cpf);
                     Banco.salvarCliente(novoCliente);
                     break;
                     
                case 2:
                	Banco.listarClientes();
                	break;
                	
                case 3: 
                	 System.out.print("Digite o CPF do cliente: ");
                     String cpfBusca = scanner.nextLine();
                     Cliente clienteEncontrado = Banco.consultarClientePorCpf(cpfBusca);
                     if (clienteEncontrado != null) {
                         System.out.println("Cliente encontrado: " + clienteEncontrado);
                     } else {
                         System.out.println("Cliente não encontrado!");
                     }
                     break;
                case 4:
                    System.out.print("Digite o CPF do cliente a ser removido: ");
                    cpf = scanner.nextLine();
                    Banco.removerCliente(cpf);
                    break;
                case 5:
                	System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine();
                    Cliente cliente = Banco.consultarClientePorCpf(cpf);

                    if (cliente != null) {
                        System.out.print("Digite o número da conta: ");
                        String num = scanner.nextLine();

                        if (Banco.localizarContaGlobalmente(num) != null) {
                            System.out.println("A conta já existe.");
                        } else {
                            Conta conta = new Conta(num);
                            Banco.adicionarContaAoCliente(cpf, conta);
                            System.out.println("Conta criada com sucesso.");
                        }
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                   break;
                case 6: 
                	System.out.print("Digite o CPF do cliente para listar suas contas: ");
                    String cpfParaListarContas = scanner.nextLine();
                    Cliente clienteParaListarContas = Banco.consultarClientePorCpf(cpfParaListarContas);
                    if (clienteParaListarContas != null) {
                        for (Conta conta : clienteParaListarContas.getContas()) {
                            System.out.println(conta);
                        }
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 7:
                	 System.out.print("Digite o CPF do cliente: ");
                     cpf = scanner.nextLine();
                     System.out.print("Digite o número da conta a ser removida: ");
                     String numeroConta = scanner.nextLine();
                     Banco.removerContaDoCliente(cpf, numeroConta);
                     break;
                case 8:
                    System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine();
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextLine();
                    System.out.print("Digite o valor do depósito: ");
                    BigDecimal valorDeposito = scanner.nextBigDecimal();
                    Banco.realizarDeposito(cpf, numeroConta, valorDeposito);
                    break;
                case 9:
                    System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine();
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextLine();
                    System.out.print("Digite o valor do saque: ");
                    BigDecimal valorSaque = scanner.nextBigDecimal();
                    Banco.realizarSaque(cpf, numeroConta, valorSaque);
                    break;
                
                case 10:
                	 System.out.print("Digite o CPF do cliente da conta de origem: ");
                	    String cpfOrigem = scanner.nextLine();
                	    Cliente clienteOrigem = Banco.consultarClientePorCpf(cpfOrigem);

                	    if (clienteOrigem != null) {
                	        System.out.print("Digite o número da conta de origem: ");
                	        String numeroContaOrigem = scanner.nextLine();
                	        Conta contaOrigem = clienteOrigem.localizarConta(numeroContaOrigem);

                	        if (contaOrigem != null) {
                	            System.out.print("Digite o CPF do cliente da conta de destino: ");
                	            String cpfDestino = scanner.nextLine();
                	            Cliente clienteDestino = Banco.consultarClientePorCpf(cpfDestino);

                	            if (clienteDestino != null) {
                	                System.out.print("Digite o número da conta de destino: ");
                	                String numeroContaDestino = scanner.nextLine();
                	                Conta contaDestino = clienteDestino.localizarConta(numeroContaDestino);

                	                if (contaDestino != null) {
                	                    System.out.print("Digite o valor da transferência: ");
                	                    BigDecimal valorTransferencia = scanner.nextBigDecimal();
                	                    scanner.nextLine();

                	                    try {
                	                        contaOrigem.transferir(valorTransferencia, contaDestino);
                	                    } catch (IllegalArgumentException e) {
                	                        System.out.println(e.getMessage());
                	                    }
                	                } else {
                	                    System.out.println("Conta de destino não encontrada.");
                	                }
                	            } else {
                	                System.out.println("Cliente de destino não encontrado.");
                	            }
                	        } else {
                	            System.out.println("Conta de origem não encontrada.");
                	        }
                	    } else {
                	        System.out.println("Cliente de origem não encontrado.");
                	    }
                	    break;
                case 11:
                	System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine();
                    cliente = Banco.consultarClientePorCpf(cpf);

                    if (cliente != null) {
                        System.out.print("Digite o número da conta: ");
                        numeroConta = scanner.nextLine();
                        Conta conta = cliente.localizarConta(numeroConta);

                        if (conta != null) {
                            System.out.print("Digite o mês (1-12): ");
                            int mes = scanner.nextInt();
                            System.out.print("Digite o ano: ");
                            int ano = scanner.nextInt();
                            scanner.nextLine();  

                           
                            Banco.imprimirExtrato(cpf, numeroConta, mes, ano);
                        } else {
                            System.out.println("Conta não encontrada.");
                        }
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;

                case 12:
                    System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine(); 
                    System.out.print("Digite o número da conta: ");
                    numeroConta = scanner.nextLine(); 
                    Banco.consultarSaldo(cpf, numeroConta); 
                    break;

                case 13:
                    System.out.print("Digite o CPF do cliente: ");
                    cpf = scanner.nextLine(); 
                    Banco.consultarBalancoDasContas(cpf); 
                    break;
                case 0:
                    // Salvar os dados antes de sair
                    Banco.salvarDadosEmArquivo();
                    System.out.println("Dados salvos. Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }


}
