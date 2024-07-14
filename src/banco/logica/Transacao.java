package banco.logica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private LocalDateTime data;
    private Conta destino;

    public Transacao(BigDecimal valor, TipoTransacao tipo, LocalDateTime data, Conta destino) {
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
        this.destino = destino;
    }

    public LocalDateTime getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Transacao [valor=" + valor + ", tipo=" + tipo + ", data=" + data + ", destino=" + destino.getNumero() + "]";
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
