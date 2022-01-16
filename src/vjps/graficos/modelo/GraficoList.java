package vjps.graficos.modelo;

import java.util.ArrayList;
import java.util.List;

public class GraficoList {
	private List<Grafico> graficosList;
	
	public GraficoList() {
		graficosList = new ArrayList<>();
	}

	/**
	 * Adiciona um gráfico na lista.
	 */
	public boolean adicionar(Grafico grafico) {
		return (pesquisar(grafico) == null) ? graficosList.add(grafico):false;
	}

	/**
	 * Pesquisa pelo nome do gráfico na lista.
	 * Retorna o grafico se estiver cadastrado na lista e null caso contrário.
	 */
	public Grafico pesquisar(Grafico grafico) {
		//Utiliza o método equals da classe Grafico para comparar os objetos da lista.
		return graficosList.contains(grafico) ? graficosList.get(graficosList.lastIndexOf(grafico)) : null;
	}
	
}// class GraficoList
