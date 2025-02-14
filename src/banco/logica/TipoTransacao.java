package banco.logica;

public enum TipoTransacao {
	
	DEBITO(1),
	CREDITO(2),
	TRANSFERENCIA_DEBITO(3),
	TRANSFERENCIA_CREDITO(4);
	
	int valor;
		
	private TipoTransacao(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static TipoTransacao getEnumFromValor(int valor) {
		for(TipoTransacao t : values()) {
			if(t.getValor() == valor) {
				return t; 
			}
		}
		return null;
	}
}
