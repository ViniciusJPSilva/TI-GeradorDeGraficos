package vjps.graficos.gui;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static vjps.graficos.arquivo.Arquivo.abrir;
import static vjps.graficos.arquivo.Arquivo.obterExtensao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import vjps.graficos.gui.paineis.GraficoColuna;
import vjps.graficos.gui.paineis.GraficoLinha;
import vjps.graficos.gui.paineis.ModeloGrafico;
import vjps.graficos.modelo.Grafico;
import vjps.graficos.modelo.GraficoList;
import vjps.graficos.modelo.Serie;
import vjps.graficos.utilitarios.Constantes;

/**
 * Classe responsável por gerar uma janela do tipo JFrame com todas as funcionalidades do programa.
 * 	- Criar e Gerar gráficos.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public class IgGeradorGrafico extends JFrame {
	
	private GraficoList graficos;
	
	private JTextField arquivoTextField;
	private JTextField graficoTextField;

	/**
	 * Cria e exibe a GUI.
	 */
	public IgGeradorGrafico(GraficoList graficos) {
		
		this.graficos = graficos;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Finaliza o programa e todas as suas suas sub-janelas.
				System.exit(0);
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				graficoTextField.requestFocus();
			}
		});
		
		// Cria um objeto para evitar a chamada ao método getContentPane().
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(0, 0)); 
		
		// Cria o painel principal.
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER); // Adiciona o painel principal no centro do contentPane.
		mainPanel.setLayout(null); // Define o layout do painel principal.
		
		// Cria o painel com as funções de criar um gráfico.
		JPanel criarGraficoPanel = new JPanel();
		criarGraficoPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), Constantes.CRIAR_GRAFICO, TitledBorder.LEFT, TitledBorder.TOP, null, new Color(51, 51, 51)));
		criarGraficoPanel.setBounds(12, 12, 560, 100);
		mainPanel.add(criarGraficoPanel);
		
		// Label Arquivo
		JLabel arquivoLabel = new JLabel(Constantes.TF_ARQUIVO);
		arquivoLabel.setDisplayedMnemonic(KeyEvent.VK_A);
		criarGraficoPanel.add(arquivoLabel);
		
		// TextField Arquivo
		arquivoTextField = new JTextField();
		arquivoTextField.setToolTipText(Constantes.TF_ARQUIVO_TOOLTIP);
		arquivoTextField.setColumns(30);
		arquivoLabel.setLabelFor(arquivoTextField); // Une a Label Arquivo com o TextField Arquivo.
		criarGraficoPanel.add(arquivoTextField);
		
		// Botão Selecionar arquivo
		JButton selecionarButton = new JButton(Constantes.BTN_SELECIONAR);
		selecionarButton.setToolTipText(Constantes.BTN_CRIARESALVAR_TOOLTIP);
		selecionarButton.setMnemonic(KeyEvent.VK_S);
		criarGraficoPanel.add(selecionarButton);
		
		// Botão Criar / Salvar gráfico
		JButton criarButton = new JButton(Constantes.BTN_CRIARESALVAR);
		criarButton.setToolTipText(Constantes.BTN_CRIARESALVAR_TOOLTIP);
		criarButton.setMnemonic(KeyEvent.VK_R);
		criarGraficoPanel.add(criarButton);
		
		// Cria um painel com as funções de construir (desenhar) um gráfico.
		JPanel construirGraficoPanel = new JPanel();
		construirGraficoPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), Constantes.CONSTRUIR_GRAFICO, TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		construirGraficoPanel.setBounds(12, 124, 560, 100);
		mainPanel.add(construirGraficoPanel);
		construirGraficoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Label Grafico
		JLabel graficoLabel = new JLabel(Constantes.TF_GRAFICO);
		graficoLabel.setDisplayedMnemonic(KeyEvent.VK_G);
		construirGraficoPanel.add(graficoLabel);
		
		// TextField Grafico
		graficoTextField = new JTextField();
		graficoTextField.setToolTipText(Constantes.TF_GRAFICO_TOOLTIP);
		construirGraficoPanel.add(graficoTextField);
		graficoTextField.setColumns(40);
		
		// Botão Gráfico Coluna
		JButton graficoColunaButton = new JButton(Constantes.BTN_GRAFICOCULUNA);
		graficoColunaButton.setToolTipText(Constantes.BTN_GRAFICOCOLUNA_TOOLTIP);
		graficoColunaButton.setMnemonic(KeyEvent.VK_C);
		construirGraficoPanel.add(graficoColunaButton);
		
		// Botão Gráfico Linha
		JButton graficoLinhaButton = new JButton(Constantes.BTN_GRAFICOLINHA);
		graficoLinhaButton.setToolTipText(Constantes.BTN_GRAFICOLINHA_TOOLTIP);
		graficoLinhaButton.setMnemonic(KeyEvent.VK_L);
		construirGraficoPanel.add(graficoLinhaButton);
		
		//Ações dos botões
		selecionarButton.addActionListener((event) -> abrirArquivo());
		criarButton.addActionListener((event) -> gerarGrafico());
		graficoColunaButton.addActionListener((event) -> construirGrafico(ModeloGrafico.COLUNAS));
		graficoLinhaButton.addActionListener((event) -> construirGrafico(ModeloGrafico.LINHAS));
		
		setTitle(Constantes.TITULO_GERADOR_GRAFICO);
		setBounds(100, 100, 600, 300);
		setResizable(false);
		setVisible(true);
	}// Construtor
	
	/**	
 	* Obtém o nome do gráfico da caixa de texto, busca na lista de gréficos, cria e exibe o mesmo para o usuário.
 	* @param Tipo de gráfico (LINHA ou COLUNA)
 	*/
	private void construirGrafico(int tipo) {
		String nomeGrafico = graficoTextField.getText();
		
		// Verifica se o campo do nome do gráfico está em branco.
		if(nomeGrafico.isBlank()) {
			showMessageDialog(this, Constantes.ERRO_FORNECA_NOME, Constantes.STR_GRAFICO, ERROR_MESSAGE);
			return;
		}
		
		// Cria um novo gráfico e seta um nome.
		Grafico grafico = new Grafico();
		grafico.setNomeGrafico(nomeGrafico);
		
		// Verifica se o gráfico existe.
		grafico = graficos.pesquisar(grafico);
		if(grafico == null) {
			showMessageDialog(this, Constantes.ERRO_GRAFICO_NAO_EXISTE, Constantes.STR_GRAFICO, ERROR_MESSAGE);
			return;
		}
		
		// Exibe o gráfico
		try {
			new IgGrafico(grafico, (tipo == ModeloGrafico.COLUNAS) ? new GraficoColuna(grafico) : new GraficoLinha(grafico));
			setVisible(true);
		} catch (Exception e) {
			showMessageDialog(this, Constantes.ERRO_EXIBIR_GRAFICO, Constantes.STR_GRAFICO, ERROR_MESSAGE);
			return;
		}
	}// construirGrafico()

	/**
	 * Exibe uma caixa de diálogo para o usuário selecionar o diretório e o nome do arquivo que ele deseja abrir.
	 * Em seguida, exibe o seu nome no campo de texto.
	 */
	private void abrirArquivo() {
		// Obtém o nome do arquivo com seu caminho absoluto.
		String nomeArquivo = IgArquivo.dialogoAbrirArquivo(this, Constantes.MSG_ABRIR_ARQUIVO);
		
		// Verifica se o usuário cancelou a operação.
		if(nomeArquivo != null) {
			String extensao = obterExtensao(nomeArquivo);
			nomeArquivo = String.format("%s%s", nomeArquivo, (extensao != null) ? "":".txt");
			
			// Insere o nome do arquivo no campo de texto.
			arquivoTextField.setText(nomeArquivo);
		}
	}

	/**
	 * Verifica se algum arquivo foi selecionado, realiza a leitura do mesmo, cria e armazena um gráfico na lista de gráficos.
	 */
	private void gerarGrafico() {
		String nomeArquivo = arquivoTextField.getText(),
				conteudoArquivo = null;
		
		// Verifica se algum arquivo foi selecionado.
		if(nomeArquivo.isBlank()) {
			showMessageDialog(this, Constantes.ERRO_SELECIONAR_ARQUIVO, Constantes.GERAR_GRAFICO, ERROR_MESSAGE);
			return;
		}
		
		// Tenta realizar a leitura do arquivo e salvar as informações do conteúdo.
		try {
			String texto = abrir(nomeArquivo);
			conteudoArquivo = texto;
		} catch (IOException e) {
			showMessageDialog(this, String.format("%s\n\n%s", Constantes.ERRO_ABRIR_ARQUIVO, nomeArquivo), Constantes.MSG_ABRIR_ARQUIVO, ERROR_MESSAGE);
			return;
		}
		
		// Validando os dados, criando e armazenando o gráfico.
		try {
			Grafico grafico = criarGrafico(conteudoArquivo);
				
			if(graficos.adicionar(grafico))
				showMessageDialog(this, Constantes.MSG_GRAFICO_CRIADO, Constantes.CRIAR_GRAFICO, INFORMATION_MESSAGE);
			else 
				showMessageDialog(this, Constantes.ERRO_GRAFICO_JA_EXISTE, Constantes.CRIAR_GRAFICO, ERROR_MESSAGE);
				
			limparTextFields();
			graficoTextField.setText(grafico.getNomeGrafico());
		}catch (NumberFormatException | StringIndexOutOfBoundsException e) {
			showMessageDialog(this, Constantes.ERRO_DADOS_INVALIDOS, Constantes.CRIAR_GRAFICO, ERROR_MESSAGE);
			return;
		}
	}// gerarGrafico()

	/**
	 * Cria um Gráfico com os dados obtidos de um arquivo TXT.
	 * Para que não ocorra erros durante a criação do gráfico, o arquivo TXT deve estar com os dados devidamente organizados.
	 * 
	 * @param Conteúdo do arquivo TXT.
	 * 
	 * @throws NumberFormatException - Disparada caso um dos valores float estejam inválidos.
	 * @throws StringIndexOutOfBoundsException - Disparada caso o conteúdo do arquivo possua algum dado inválido.
	 */
	private Grafico criarGrafico(String conteudoArquivo) throws NumberFormatException, StringIndexOutOfBoundsException {
		List<String> controle = new ArrayList<>();
		Grafico grafico = new Grafico();
		Serie serie = new Serie();
		int index = 0;
		final int MIN_LINHAS = 5;
		
		// Obtendo cada linha da String de conteúdo do arquivo.
		boolean fimArquivo = false;
		String linha = new String();
		while(linha != null) {
			try {
				linha = conteudoArquivo.substring(index, conteudoArquivo.indexOf(Constantes.CARACTER_NOVA_LINHA, index)); // Obtém uma linha do arquivo.
				
				// Verificando se ocorreram duas linhas em branco consecutivas (fim do arquivo)
				if(linha.isBlank() && !fimArquivo) 
					fimArquivo = true;
				else
					if(linha.isBlank() && fimArquivo) {
						controle.remove(controle.size() - 1); // Remove a última linha em branco.
						break;
					} else
						fimArquivo = false;
				
				controle.add(linha);
				index = conteudoArquivo.indexOf(Constantes.CARACTER_NOVA_LINHA, index) + 1;
			} catch (StringIndexOutOfBoundsException e) {
				linha = null;
			}
		}
		
		// Verifica se o arquivo possui o número minimo de linhas (4: titulo, serie, descrição e um par de dados)
		if(controle.size() < MIN_LINHAS)
			throw new StringIndexOutOfBoundsException();
		
		// Obtendo o título
		grafico.setNomeGrafico(controle.get(0));
		
		// Convertendo e obtendo os demais dados.
		for(index=1; index < controle.size(); index++) {
			linha = controle.get(index); //Armazenando a linha para evitar a chamada a função get.
			
			// Verifica se a linha está 'vazia', o que indica o início de uma nova série de dados.
			if(linha.isBlank()) {
				grafico.adicionarSerie(serie);
				serie = new Serie();
			} else
				// Verifica se a linha possui o caracter separador, o que indica um par de valores (String ; Float).
				if(!linha.contains(Constantes.STR_CARACTER_SEPARADOR)) { // Caso a linha não possua o caracter separador.
					serie.setNomeSerie(linha); // Define o nome da série

					String nomesDados = controle.get(index + 1); // Obtém os nomes dos dados.
					grafico.setNomeDadoString(nomesDados.substring(0, nomesDados.indexOf(Constantes.CARACTER_SEPARADOR))); // Define o nome do dado texto.
					grafico.setNomeDadoFloat(nomesDados.substring(nomesDados.indexOf(Constantes.CARACTER_SEPARADOR) + 1)); // Define o nome do dado de valor.
					
					index++; // Incremento do index.
				} else {
					String nomePar = linha.substring(0, linha.indexOf(Constantes.CARACTER_SEPARADOR)); // Define o nome do par de dados corrente.
					Float valorPar = Float.parseFloat(linha.substring(linha.indexOf(Constantes.CARACTER_SEPARADOR) + 1)); // Define o valor do par de dados corrente.
					
					serie.adicionarParDeDados(nomePar, valorPar); // Adiciona um novo par de dados na série corrente.
				}
			
			// Identifica o loop final e adiciona a última série.
			if(index == controle.size() - 1)
				grafico.adicionarSerie(serie);
		}
		
		return grafico;
	}// criarGrafico()

	/**
	 * Limpa o conteúdo dos TextFields arquivoTextField e graficoTextField.
	 */
	private void limparTextFields() {
		arquivoTextField.setText("");
		graficoTextField.setText("");
	}
	
} //class IgGeradorGrafico
