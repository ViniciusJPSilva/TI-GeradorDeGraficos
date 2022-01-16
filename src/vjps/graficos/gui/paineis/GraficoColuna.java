package vjps.graficos.gui.paineis;

import java.awt.Graphics;
import java.util.List;

import vjps.graficos.modelo.Grafico;
import vjps.graficos.modelo.ParDados;

/**
 * Classe responsável por desenhar um gráfico de colunas.
 * 	Subclasse ModeloGrafico.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public class GraficoColuna extends ModeloGrafico {

	public GraficoColuna(Grafico grafico) {
		super(grafico);
	} // construtor

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(int serie = 0; serie < quantidadeSeries; serie++) {
			int espacamentoEntreColunas = (xDireita - xEsquerda) / quantidadeParesDeDados, // Valor em pixels da distância entre as colunas dos pares de dados.
				larguraColunas = (espacamentoEntreColunas - distanciaPadrao * 2) / quantidadeSeries, // Valor em pixels da largura das colunas.
				passo = xEsquerda + distanciaPadrao + larguraColunas * serie; // 
			
			// Armazena os pares de dados em uma lista.
			List<ParDados> paresDeDados = grafico.getSeriesDados().get(serie).getParesDados();
			
			// Define a cor da coluna, baseado na série corrente.
			g.setColor(coresSeries.get(serie));
			
			for(int par = 0; par < paresDeDados.size(); par++) {
				// Calcula a altura da coluna conforme o valor do par de dados.
				Float valor =  paresDeDados.get(par).getValor();
				int altura =  (int) (((valor != null) ? valor - minMarcacao : 0) / valorPorPixel);
				
				g.fillRect(passo + divisaoEixoX * par, yBase - altura, larguraColunas, (altura != 0) ? altura : 1);// Desenha a coluna.
			}
		}
	}// paintComponent()
}// class GraficoColuna
