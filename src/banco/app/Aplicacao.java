package banco.app;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import banco.logica.*;

public class Aplicacao {
	 private static List<Cliente> clientes;

	    public static void main(String[] args) {
	        // Carregar os dados salvos
	        clientes = Banco.carregarClientes();

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
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    consultarClientePorCpf(scanner);
                    break;
                case 4:
                    removerCliente(scanner);
                    break;
                case 5:
                    criarConta(scanner);
                    break;
                case 6:
                    listarContasDoCliente(scanner);
                    break;
                case 7:
                    removerConta(scanner);
                    break;
                case 8:
                    realizarDeposito(scanner);
                    break;
                case 9:
                    realizarSaque(scanner);
                    break;
                case 10:
                    efetuarTransferencia(scanner);
                    break;
                case 11:
                    imprimirExtrato(scanner);
                    break;
                case 12:
                    consultarSaldo(scanner);
                    break;
                case 13:
                    consultarBalanco(scanner);
                    break;
                case 0:
                    // Salvar os dados antes de sair
                    Banco.salvarClientes(clientes);
                    System.out.println("Dados salvos. Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }



	    private static void cadastrarCliente(Scanner scanner) {
	    	 System.out.print("Digite o CPF do cliente: ");
	    	    String cpf = scanner.nextLine();
	    	    
	    	    // Verificar se o cliente já existe
	    	    if (localizarCliente(cpf) != null) {
	    	        System.out.println("O cliente já existe.");
	    	    } else {
	    	        System.out.print("Digite o nome do cliente: ");
	    	        String nome = scanner.nextLine();
	    	        Cliente cliente = new Cliente(cpf, nome);
	    	        clientes.add(cliente);
	    	        System.out.println("Cliente cadastrado com sucesso.");
	    	    }
	    }

	    private static void listarClientes() {
	        System.out.println("Clientes cadastrados:");
	        for (Cliente cliente : clientes) {
	            System.out.println("CPF: " + cliente.getCpf() + ", Nome: " + cliente.getNome());
	        }
	    }

	    private static void consultarClientePorCpf(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            System.out.println("CPF: " + cliente.getCpf() + ", Nome: " + cliente.getNome());
	            System.out.println("Contas do cliente:");
	            for (Conta conta : cliente.getContas()) {
	                System.out.println("Número: " + conta.getNumero() + ", Saldo: " + conta.getSaldo());
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void removerCliente(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            clientes.remove(cliente);
	            System.out.println("Cliente removido com sucesso.");
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void criarConta(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);

	        if (cliente != null) {
	            System.out.print("Digite o número da conta: ");
	            String num = scanner.nextLine();

	            if (localizarContaGlobalmente(num) != null) {
	                System.out.println("A conta já existe.");
	            } else {
	                Conta conta = new Conta(num);
	                cliente.addConta(conta);
	                System.out.println("Conta criada com sucesso.");
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }


	    private static void listarContasDoCliente(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            System.out.println("Contas do cliente:");
	            for (Conta conta : cliente.getContas()) {
	                System.out.println("Número: " + conta.getNumero() + ", Saldo: " + conta.getSaldo());
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void removerConta(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            System.out.print("Digite o número da conta: ");
	            String num = scanner.nextLine();
	            Conta conta = cliente.localizarConta(num);
	            if (conta != null) {
	                cliente.remConta(conta);
	                System.out.println("Conta removida com sucesso.");
	            } else {
	                System.out.println("Conta não encontrada.");
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void realizarDeposito(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            System.out.print("Digite o número da conta: ");
	            String num = scanner.nextLine();
	            Conta conta = cliente.localizarConta(num);
	            if (conta != null) {
	                System.out.print("Digite o valor do depósito: ");
	                BigDecimal valor = scanner.nextBigDecimal();
	                conta.depositar(valor);
	                System.out.println("Depósito realizado com sucesso.");
	            } else {
	                System.out.println("Conta não encontrada.");
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void realizarSaque(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            System.out.print("Digite o número da conta: ");
	            String num = scanner.nextLine();
	            Conta conta = cliente.localizarConta(num);
	            if (conta != null) {
	                System.out.print("Digite o valor do saque: ");
	                BigDecimal valor = scanner.nextBigDecimal();
	                conta.sacar(valor);
	                System.out.println("Saque realizado com sucesso.");
	            } else {
	                System.out.println("Conta não encontrada.");
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void efetuarTransferencia(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente de origem: ");
	        String cpfOrigem = scanner.nextLine();
	        Cliente clienteOrigem = localizarCliente(cpfOrigem);
	        if (clienteOrigem != null) {
	            System.out.print("Digite o número da conta de origem: ");
	            String numOrigem = scanner.nextLine();
	            Conta contaOrigem = clienteOrigem.localizarConta(numOrigem);
	            if (contaOrigem != null) {
	                System.out.print("Digite o CPF do cliente de destino: ");
	                String cpfDestino = scanner.nextLine();
	                Cliente clienteDestino = localizarCliente(cpfDestino);
	                if (clienteDestino != null) {
	                    System.out.print("Digite o número da conta de destino: ");
	                    String numDestino = scanner.nextLine();
	                    Conta contaDestino = clienteDestino.localizarConta(numDestino);
	                    if (contaDestino != null) {
	                        System.out.print("Digite o valor da transferência: ");
	                        BigDecimal valor = scanner.nextBigDecimal();
	                        contaOrigem.transferir(valor, contaDestino);
	                        System.out.println("Transferência realizada com sucesso.");
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
	    }

	    private static void imprimirExtrato(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            System.out.print("Digite o número da conta: ");
	            String num = scanner.nextLine();
	            Conta conta = cliente.localizarConta(num);
	            if (conta != null) {
	                System.out.print("Digite o mês: ");
	                int mes = scanner.nextInt();
	                System.out.print("Digite o ano: ");
	                int ano = scanner.nextInt();
	                conta.imprimirExtrato(mes, ano);
	            } else {
	                System.out.println("Conta não encontrada.");
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void consultarSaldo(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            System.out.print("Digite o número da conta: ");
	            String num = scanner.nextLine();
	            Conta conta = cliente.localizarConta(num);
	            if (conta != null) {
	                System.out.println("Saldo da conta " + num + ": " + conta.getSaldo());
	            } else {
	                System.out.println("Conta não encontrada.");
	            }
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static void consultarBalanco(Scanner scanner) {
	        System.out.print("Digite o CPF do cliente: ");
	        String cpf = scanner.nextLine();
	        Cliente cliente = localizarCliente(cpf);
	        if (cliente != null) {
	            BigDecimal balanco = cliente.getBalanco();
	            System.out.println("Balanço das contas do cliente: " + balanco);
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }

	    private static Cliente localizarCliente(String cpf) {
	        for (Cliente cliente : clientes) {
	            if (cliente.getCpf().equals(cpf)) {
	                return cliente;
	            }
	        }
	        return null;
	    }
	   private static Conta localizarContaGlobalmente(String numero) {
		   for (Cliente cliente : clientes) {
			   Conta conta = cliente.localizarConta(numero);
			   if (conta != null) {
				   return conta;
			   }
		   }
		   return null;
	   }
	}

