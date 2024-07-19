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
	public void depositar(BigDecimal valor) {
        saldo = saldo.add(valor);
        transacoes.add(new Transacao(valor, TipoTransacao.CREDITO, LocalDateTime.now(), this));
    }
	 public void sacar(BigDecimal valor) {
	        if (saldo.compareTo(valor) >= 0) {
	            saldo = saldo.subtract(valor);
	            transacoes.add(new Transacao(valor, TipoTransacao.DEBITO, LocalDateTime.now(), this));
	        } else {
	            System.out.println("Saldo insuficiente.");
	        }
	}
	 public void transferir(BigDecimal valor, Conta contaDestino) {
		    if (valor.compareTo(BigDecimal.ZERO) <= 0) {
		        System.out.println("O valor da transferência deve ser maior que zero.");
		        return;
		    }

		    if (saldo.compareTo(valor) >= 0) {
		        saldo = saldo.subtract(valor);
		        transacoes.add(new Transacao(valor, TipoTransacao.TRANSFERENCIA_DEBITO, LocalDateTime.now(), this));

		        
		        contaDestino.receberTransferencia(valor, this);

		        System.out.println("Transferência realizada com sucesso.");
		        Banco.salvarDadosEmArquivo();
		    } else {
		        System.out.println("Saldo insuficiente.");
		    }
		}

		public void receberTransferencia(BigDecimal valor, Conta contaOrigem) {
		    saldo = saldo.add(valor);
		    transacoes.add(new Transacao(valor, TipoTransacao.TRANSFERENCIA_CREDITO, LocalDateTime.now(), contaOrigem));
		}
	    public void imprimirExtrato(int mes, int ano) {
	        System.out.println("Extrato do mês " + mes + "/" + ano + " para a conta " + numero);
	        for (Transacao transacao : transacoes) {
	            if (transacao.getData().getMonthValue() == mes && transacao.getData().getYear() == ano) {
	                System.out.println(transacao);
	            }
	        }
	    }
	    public String toString() {
	        return "Número: " + numero + ", Saldo: " + saldo;
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
	public char[] getExtrato() {
		// TODO Auto-generated method stub
		return null;
	}

}
