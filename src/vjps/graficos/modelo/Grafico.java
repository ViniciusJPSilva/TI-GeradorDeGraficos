package vjps.graficos.modelo;

import java.util.ArrayList;
import java.util.List;

public class Grafico {

	private String nomeGrafico, nomeDadoString, nomeDadoFloat;
	private List<Serie> seriesDados;
	
	public Grafico() {
		seriesDados = new ArrayList<>();
	}
	
	public Grafico(String nomeGrafico, String nomeDadoString, String nomeDadoFloat) {
		this();
		this.nomeGrafico = nomeGrafico;
		this.nomeDadoString = nomeDadoString;
		this.nomeDadoFloat = nomeDadoFloat;
	}

	public String getNomeGrafico() {
		return nomeGrafico;
	}

	public void setNomeGrafico(String nomeGrafico) {
		this.nomeGrafico = nomeGrafico;
	}

	public String getNomeDadoString() {
		return nomeDadoString;
	}

	public void setNomeDadoString(String nomeDadoString) {
		this.nomeDadoString = nomeDadoString;
	}

	public String getNomeDadoFloat() {
		return nomeDadoFloat;
	}

	public void setNomeDadoFloat(String nomeDadoFloat) {
		this.nomeDadoFloat = nomeDadoFloat;
	}

	public List<Serie> getSeriesDados() {
		return seriesDados;
	}

	public void adicionarSerie(Serie serie) {
		seriesDados.add(serie);
	}
	
	public void removerSerie(Serie serie) {
		seriesDados.add(serie);
	}

	@Override
	public String toString() {
		return String.format("Gráfico %s\nDados: %s ; %s\n%s", nomeGrafico, nomeDadoString, nomeDadoFloat, seriesDados);
	}
	
	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Grafico) 
			return nomeGrafico.equalsIgnoreCase(((Grafico) objeto).getNomeGrafico());
		else
			return false;
	}
} // class Grafico
