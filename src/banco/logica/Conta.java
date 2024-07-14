package banco.logica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numero;
	private BigDecimal saldo;
	private boolean status;
	private LocalDateTime dataAbertura;
	private List<Transacao> transacoes = new ArrayList<>();
	
	public Conta(String numero) {
		this.setNumero(numero);
		this.setSaldo(BigDecimal.ZERO);
		this.setStatus(true);
		this.setDataAbertura(LocalDateTime.now());
		this.setTransacoes(new ArrayList<>());
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

}
