package banco.logica;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cpf;
	private String nome;
	private ArrayList<Conta> contas = new ArrayList<>();
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}
	
	public void addConta(Conta c) {
		if(contas.contains(c))
			System.out.println("A conta já foi adicionada");
		else
			this.contas.add(c);

	}
	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
