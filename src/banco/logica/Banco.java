package banco.logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Banco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static List<Cliente> cadastroClientes = new ArrayList<>();

	private static Banco instance;
	
	private Banco() {
		carregarDadosDeArquivo();
	}
	
	public static Banco getInstance() {
		if(instance!=null)
			return instance;
		else
			return new Banco();
	}

	public static void salvarCliente(Cliente c) {
		if (!cadastroClientes.contains(c)) {
			cadastroClientes.add(c);
			salvarDadosEmArquivo();
			System.out.println("Cliente cadastrados com sucesso!");
		} else
			System.err.println("Cliente ja cadastrado no sistema!");

	}
	
	public static Cliente localizarClientePorCPF(String cpf) {
		Cliente c = new Cliente(cpf, null);
		c.setCpf(cpf);
		if(cadastroClientes.contains(c)) {
			int index = cadastroClientes.indexOf(c);
			c = cadastroClientes.get(index);
			return c;
		}else
			return null;
	}
	
	public static void listarClientes() {
	    if (cadastroClientes.isEmpty()) {
	        System.out.println("Nenhum cliente cadastrado.");
	    } else {
	        System.out.println("Clientes cadastrados:");
	        for (Cliente cliente : cadastroClientes) {
	            System.out.println(cliente);
	        }
	    }
	}
	public static Cliente consultarClientePorCpf(String cpf) {
        for (Cliente cliente : cadastroClientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }
	public static Conta localizarContaGlobalmente(String numero) {
        for (Cliente cliente : cadastroClientes) {
            Conta conta = cliente.localizarConta(numero);
            if (conta != null) {
                return conta;
            }
        }
        return null;
    }

    public static void adicionarContaAoCliente(String cpf, Conta conta) {
        Cliente cliente = consultarClientePorCpf(cpf);
            cliente.addConta(conta);
        }
	public static void listarContasDoCliente(String cpf) {
	    Cliente cliente = consultarClientePorCpf(cpf);

	    if (cliente == null) {
	        System.out.println("Cliente não encontrado.");
	        return;
	    }

	    System.out.println("Contas do cliente " + cliente.getNome() + ":");
	    for (Conta conta : cliente.getContas()) {
	        System.out.println(conta);
	    }
	}

    public static void removerCliente(String cpf) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente != null) {
        	cadastroClientes.remove(cliente);
        	salvarDadosEmArquivo();
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    public static void removerContaDoCliente(String cpf, String numeroConta) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente != null) {
            Conta conta = cliente.localizarConta(numeroConta);
            if (conta != null) {
                cliente.remConta(conta);
                salvarDadosEmArquivo();
                System.out.println("Conta removida do cliente com sucesso.");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    public static void realizarDeposito(String cpf, String numeroConta, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Valor de depósito inválido.");
            return;
        }
        
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente != null) {
            Conta conta = cliente.localizarConta(numeroConta);
            if (conta != null) {
                conta.depositar(valor);
                salvarDadosEmArquivo();  
                System.out.println("Depósito realizado com sucesso.");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    public static void realizarSaque(String cpf, String numeroConta, BigDecimal valor) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente != null) {
            Conta conta = cliente.localizarConta(numeroConta);
            if (conta != null) {
                conta.sacar(valor);
                salvarDadosEmArquivo();
                System.out.println("Saque realizado com sucesso.");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    public static void imprimirExtrato(String cpf, String numeroConta, int mes, int ano) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente != null) {
            Conta conta = cliente.localizarConta(numeroConta);
            if (conta != null) {
                System.out.println("Extrato do mês " + mes + "/" + ano + " para a conta " + numeroConta + ":");
                for (Transacao transacao : conta.getTransacoes()) {
                    if (transacao.getData().getMonthValue() == mes && transacao.getData().getYear() == ano) {
                        System.out.println(transacao);
                    }
                }
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public static void consultarSaldo(String cpf, String numeroConta) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente != null) {
            Conta conta = cliente.localizarConta(numeroConta);
            if (conta != null) {
                System.out.println("Saldo da conta " + numeroConta + ": " + conta.getSaldo());
            } else {
                System.out.println("Conta não encontrada.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public static void consultarBalancoDasContas(String cpf) {
        Cliente cliente = consultarClientePorCpf(cpf);
        if (cliente != null) {
            System.out.println("Balanço total das contas do cliente " + cpf + ": " + cliente.getBalanco());
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }


	public static void salvarDadosEmArquivo() {
		try {
			FileOutputStream fos = new FileOutputStream("dados");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(cadastroClientes);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object carregarDadosDeArquivo() {

		try {
			FileInputStream fis = new FileInputStream("dados");
			ObjectInputStream ois = new ObjectInputStream(fis);
			cadastroClientes = (ArrayList<Cliente>)ois.readObject();
			ois.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cadastroClientes;
	}

}