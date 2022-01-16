package vjps.graficos.gui.paineis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

import vjps.graficos.modelo.Grafico;
import vjps.graficos.modelo.ParDados;
import vjps.graficos.modelo.Serie;
import vjps.graficos.utilitarios.Constantes;

/**
 * Classe responsável por desenhar os elementos padrões de um gráfico, como sua legenda, eixos X e Y e valores de marcação.
 * 		O conteúdo do gráfico (linhas, colunas, etc) deve ser desenhados pelas subclasses.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public abstract class ModeloGrafico extends JPanel {

	// Constantes estáticas
	public final static int COLUNAS = 1, LINHAS = 2;
	
	// Constantes de posicionamento
	public final int xEsquerda = 150, xDireita = 1000, 
			yTopo = 50, yBase = 510, 
			espacamentoEntreValoresEixoY = 45, distanciaPadrao = 10,
			xMarcacao = 35, numeroDivisoesEixoY = 10, tamanhoFonteTexto = 12;
	
	// Atributos protegidos, somente a própria classe e suas subclasses possuem acesso.
	protected Grafico grafico;
	protected List<Color> coresSeries;
	
	protected int quantidadeSeries, quantidadeParesDeDados, minMarcacao, divisaoEixoX;
	
	protected float valorPorPixel;

	/**
	 * Construtor
	 */
	protected ModeloGrafico(Grafico grafico) {
		int valorMaximoRGB = 255;
		
		this.grafico = grafico;
		this.coresSeries = new ArrayList<>();
		
		//Gera cores aleatórias para as colunas.
		Random random = new Random();
		for(int i=0; i < grafico.getSeriesDados().size(); i++)
			coresSeries.add(new Color(random.nextInt(valorMaximoRGB), random.nextInt(valorMaximoRGB), random.nextInt(valorMaximoRGB)));
		
		// Registra o tratador de eventos do mouse para o objeto ModeloGrafico (painel).
		addMouseListener(
			new MouseAdapter() { 
				/**
				 * Redesenha o conteúdo do gráfico (painel) usando a(s) cor(es) escolhida(s) pelo usuário.
				 */
				@Override
				public void mouseClicked(MouseEvent e) {
					// Percorre toda a lista de cores.
					for(int index = 0; index < coresSeries.size(); index++) {
						// Salva a cor atual.
						Color tempColor = coresSeries.get(index);
						
						// Exibe um diálogo com opções de cores para o usuário escolher uma.
						Color cor = JColorChooser.showDialog(ModeloGrafico.this, String.format("%s - %s", Constantes.MSG_SELECIONAR_COR, grafico.getSeriesDados().get(index).getNomeSerie()), tempColor);
						
						// Verifica se o usuário clicou no botão "Cancelar" ou "Fechar", o que significa que ele não selecionou uma cor.
						// Não selecionar uma cor termina o loop de seleções.
						if (cor == null) {
							coresSeries.set(index, tempColor); // Recupera a cor anterior.
							break; // Finaliza o loop
						}else
							coresSeries.set(index, cor); // Define a nova cor.
					}
					
					// Faz uma chamada ao método paintComponent do painel.
					repaint();	
					
				} // mouseClicked()
			}
		); // addMouseListener()
	}
	
	/**
	 * Desenha o gráfico com os dados. 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		// Inicializando e criando variáveis de posicionamento.
		quantidadeParesDeDados = grafico.getSeriesDados().get(0).getParesDados().size();
		
		int yMarcacao = yTopo + distanciaPadrao,
			maxMarcacao = calcularMaximoMarcacao(), // Valor do topo do eixo Y (valor máximo).
			divisaoEixoY = (maxMarcacao - minMarcacao) / numeroDivisoesEixoY,
			distanciaValoresEixoY = String.valueOf(maxMarcacao).length() * 9; // Valor em pixels do intervalo entre as marcações do eixo Y.
			
		divisaoEixoX = (xDireita - xEsquerda) / quantidadeParesDeDados; // Valor em pixels do intervalo entre as marcações do eixo X.
		minMarcacao = calcularMinimoMarcacao((maxMarcacao > 100) ? 100 : 10); // Valor da base do eixo Y (valor minimo).
		quantidadeSeries = grafico.getSeriesDados().size(); // Quantidade de séries do gráfico.
		
		valorPorPixel = (float) divisaoEixoY / espacamentoEntreValoresEixoY; // Valor atribuido a cada pixel do eixo Y.
	
		super.paintComponent(g);
	
		g.setColor(Color.BLACK);
		
		// Desenha o eixo Y.
		g.drawLine(xEsquerda, yTopo, xEsquerda, yBase);
		
		// Desenha o eixo X.
		g.drawLine(xEsquerda, yBase, xDireita, yBase);

		// Define a fonte usada para desenhar as marcações e os números do eixo Y e os nomes das colunas no eixo X. 
		g.setFont(new Font(Font.DIALOG, Font.BOLD, tamanhoFonteTexto));

		// Desenha as marcações dos números do eixo Y.
		g.setColor(Color.LIGHT_GRAY);
		for (int linhaMarcacao = yTopo + distanciaPadrao, numero = numeroDivisoesEixoY; numero >= 1; numero--, linhaMarcacao += espacamentoEntreValoresEixoY)		
			g.drawLine(xEsquerda  - 3, linhaMarcacao, xDireita, linhaMarcacao);
		
		// Desenha as marcações e escreve os nomes das colunas do eixo x.
		g.setColor(Color.BLACK);
		for (int linhaMarcacao = xEsquerda, numero = 0; numero <= quantidadeParesDeDados; numero++, linhaMarcacao += divisaoEixoX) {	
			g.drawLine(linhaMarcacao, yBase + 3, linhaMarcacao, yBase - 3);
			
			if(numero != quantidadeParesDeDados)
				escreverNaVertical(grafico.getSeriesDados().get(0).getParesDados().get(numero).getNome(), 12, linhaMarcacao + divisaoEixoX / 2 - tamanhoFonteTexto / 2, yBase + 15, g);
		}

		// Escreve os números do eixo Y.
		g.setColor(Color.BLACK);
		g.drawString(String.format("%d", maxMarcacao), xEsquerda  - distanciaValoresEixoY, yMarcacao);
		for (int linhaRotulo = yMarcacao + espacamentoEntreValoresEixoY, numero = maxMarcacao - divisaoEixoY; numero >= minMarcacao; numero-=divisaoEixoY, linhaRotulo += espacamentoEntreValoresEixoY) 
			g.drawString(String.format("%d", numero), xEsquerda  - distanciaValoresEixoY, linhaRotulo);
		
		// Escreve o nome do eixo Y.
		escreverNaVertical(grafico.getNomeDadoFloat(), 15, (xEsquerda  - distanciaValoresEixoY) / 2, yTopo + 50, g);
		
		//Desenha as colunas e as legendas do gráfico.
		for(int serie = 0; serie < quantidadeSeries; serie++) {
			// Posição base para o desenho da coluna. Tem como inspiração o passo do parafuso, que é a distância entre as ranhuras do mesmo.
			int distanciaLegenda = 20,
				ladoQuadrado = distanciaLegenda - 5,
				xLegenda = xDireita + distanciaPadrao * 2,
				yLegenda = yTopo + 50 + (serie * distanciaLegenda);
			
			g.setColor(Color.BLACK);
			g.drawString(grafico.getSeriesDados().get(serie).getNomeSerie(), xLegenda + ladoQuadrado + distanciaPadrao, yLegenda + tamanhoFonteTexto);
			
			// Define a cor da coluna, baseado na série corrente.
			g.setColor(coresSeries.get(serie));
			
			// Desenha o quadrado com a cor da legenda
			g.fillRect(xLegenda, yLegenda, ladoQuadrado, ladoQuadrado);
		}
		
	} // paintComponent()

	/**
	 * Escreve um texto na posição vertical, um caracter sobre o outro.
	 * @param texto Texto a ser escrito
	 * @param tamanhoDaFonte Tamanho da fonte do texto
	 * @param x Posição X do texto
	 * @param y Posição Y do texto
	 * @param g Graphics
	 */
	private void escreverNaVertical(String texto, int tamanhoDaFonte, int x, int y, Graphics g) {
		// Salva as valores de cor e fonte.
		Color corAntiga = g.getColor();
		Font fonteAntiga = g.getFont();
		
		// Define novos valores de cor e fonte.
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, tamanhoDaFonte));
		
		// Escreve o texto.
		for(int c=0; c < texto.length(); c++, y += tamanhoDaFonte)
			g.drawString(Character.toString(texto.charAt(c)), x, y);
		
		// Restaura os valores de cor e fonte iniciais.
		g.setColor(corAntiga);
		g.setFont(fonteAntiga);
	}

	/**
	 * Obtém o valor mais alto entre os pares de dados e retorna o valor arredondado para um inteiro maior.
	 * @return Valor mais alto.
	 */
	private int calcularMaximoMarcacao() {
		int maximo = 0; 
		
		for(Serie serie : grafico.getSeriesDados())
			for(ParDados par : serie.getParesDados()) {
				// Obtém o valor arredondado.
				int valorArredondado = (int) Math.ceil(par.getValor());
				
				// Verifica se o valor atual é maior que o máximo.
				maximo = (valorArredondado > maximo) ? valorArredondado : maximo;
			}

		int alcance = (maximo > 100) ? 100 : 10;
		
		// Tornando o valor máximo em um número múltimo de 10.
		while(maximo % alcance != 0)
			maximo++;
		
		return maximo;
	}
	
	/**
	 * Obtém o valor mais baixo entre os pares de dados e retorna o valor arredondado para um inteiro menor.
	 * @return Valor mais baixo.
	 */
	private int calcularMinimoMarcacao(int alcance) {
		int minimo = 0; 
		boolean primeiroValor = true;
		
		for(Serie serie : grafico.getSeriesDados())
			for(ParDados par : serie.getParesDados()) {
				// Obtém o valor arredondado.
				int valorArredondado = (int) Math.ceil(par.getValor());
				
				if(primeiroValor) {
					minimo = valorArredondado;
					primeiroValor = false;
				}
				
				// Verifica se o valor atual é maior que o máximo.
				minimo = (valorArredondado < minimo) ? valorArredondado : minimo;
			}

		// Tornando o valor máximo em um número múltimo de 10.
		while(minimo % alcance != 0)
			minimo--;
		
		return minimo;
	}

	/*
	 * Retorna a lista de cores das séries.
	 */
	public List<Color> getCoresSeries() {
		return coresSeries;
	}
	
}// class ModeloGrafico