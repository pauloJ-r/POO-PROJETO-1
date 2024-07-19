package banco.logica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cpf;
	private String nome;
	private ArrayList<Conta> contas = new ArrayList<>();
	
	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}
	
    public void addConta(Conta conta) {
        contas.add(conta);
    }

    public void remConta(Conta conta) {
        contas.remove(conta);
    }
	public Conta localizarConta(String numero) {
		for(Conta c : contas) {
		   if(c.getNumero().equals(numero)) {
			  return c;
		   }
		}
		return null;
	}
	
	
	public ArrayList<Conta> getContas() {
		return contas;
	}

    public BigDecimal getBalanco() {
    	BigDecimal balanco = BigDecimal.ZERO;
    	for (Conta c : contas) {
    		balanco = balanco.add(c.getSaldo());
    	}
    	return balanco;
    }

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", contas=" + contas + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
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
