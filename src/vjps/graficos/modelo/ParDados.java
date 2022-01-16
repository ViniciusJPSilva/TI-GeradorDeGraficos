package vjps.graficos.modelo;

public class ParDados {

	private String nome;
	private float valor;
	
	public ParDados() {
	}

	public ParDados(String nome, float valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return String.format("Par de Dados: %s - %f", nome, valor);
	}
	
}// class ParDados
