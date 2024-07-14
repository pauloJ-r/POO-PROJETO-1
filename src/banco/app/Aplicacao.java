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
	            opcao = scanner.nextInt();
	            scanner.nextLine(); 
	            
	           switch (opcao) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    listarClientes();
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
		private static Cliente localizarCliente(String cpf) {
		    for (Cliente cliente : clientes) {
		        if (cliente.getCpf().equals(cpf)) {
		            return cliente;
		        }
		    }
		    return null;
		}
}