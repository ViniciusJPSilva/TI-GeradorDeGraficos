package vjps.graficos.gui.paineis;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import vjps.graficos.modelo.Grafico;
import vjps.graficos.modelo.ParDados;

/**
 * Classe responsável por desenhar um gráfico de linhas.
 * 	Subclasse ModeloGrafico.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public class GraficoLinha extends ModeloGrafico {
	
	public GraficoLinha(Grafico grafico) {
		super(grafico);
	} // construtor

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Desenha as marcações verticais.
		g.setColor(Color.LIGHT_GRAY);
		for (int linhaMarcacao = xEsquerda, numero = 0; numero < quantidadeParesDeDados; numero++, linhaMarcacao += divisaoEixoX) 
			g.drawLine(linhaMarcacao + divisaoEixoX / 2, yBase, linhaMarcacao + divisaoEixoX / 2, yTopo);

		for(int serie = 0; serie < quantidadeSeries; serie++) {
			// Armazena os pares de dados em uma lista.
			List<ParDados> paresDeDados = grafico.getSeriesDados().get(serie).getParesDados();
			
			// Define a cor da coluna, baseado na série corrente.
			g.setColor(coresSeries.get(serie));
			
			for(int par = 0; par < paresDeDados.size() - 1; par++) {
				Float valor =  paresDeDados.get(par).getValor(), // Valor do inicio da linha.
						valorProximo = paresDeDados.get(par + 1).getValor(); // Valor do fim da linha.
				
				int altura =  (int) (((valor != null) ? valor - minMarcacao : 0) / valorPorPixel), // Altura (ponto Y) do inicio da linha no eixo Y.
						alturaProximo =  (int) (((valorProximo != null) ? valorProximo - minMarcacao : 0) / valorPorPixel), // Altura (ponto Y) do fim da linha no eixo Y.
						xValor = xEsquerda + (divisaoEixoX * (par + 1)) - divisaoEixoX / 2, // Ponto X do inicio da linha.
						xProximo = xValor + divisaoEixoX; // Ponto X do fim da linha.
				
				g.drawLine(xValor, yBase - altura, xProximo, yBase - alturaProximo); // Desenha a primeira linha.
				g.drawLine(xValor, yBase - altura - 1, xProximo, yBase - alturaProximo - 1); // Desenha uma segunda linha, tangente à primeiroa, para melhorar a visualização. 
			}
		}
	}// paintComponent()
	
}// class GraficoLinha
