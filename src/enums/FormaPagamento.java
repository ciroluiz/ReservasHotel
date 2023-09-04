package enums;

public enum FormaPagamento {
	DEBITO("Cartão de Débito"),
	CREDITO("Cartão de Crédito"),
	BOLETO("Boleto bancário");
	
	private final String formaPagamento;

	FormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}	
}
