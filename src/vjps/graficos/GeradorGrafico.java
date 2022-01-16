package vjps.graficos;

import vjps.graficos.gui.IgGeradorGrafico;
import vjps.graficos.modelo.GraficoList;

/**
 * Classe responsável por inicializar o programa Gerador de Gráficos.
 * @author Vinícius José Pires Silva
 *
 */
public class GeradorGrafico {

	public static void main(String[] args) {
		new IgGeradorGrafico(new GraficoList());
	}
	
}// class GeradorGrafico
