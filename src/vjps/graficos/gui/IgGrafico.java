package vjps.graficos.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import vjps.graficos.gui.paineis.ModeloGrafico;
import vjps.graficos.modelo.Grafico;
import vjps.graficos.utilitarios.Constantes;

/**
 * Classe responsável por gerar uma janela do tipo JDialog e exibir um gráfico.
 * 
 * @author Vinícius José Pires Silva
 *
 */
public class IgGrafico extends JDialog {

	ModeloGrafico graficoPanel;
	
	/**
	 * Cria e exibe a GUI.
	 */
	public IgGrafico(Grafico grafico, ModeloGrafico graficoPanel) {
		
		this.graficoPanel = graficoPanel;
		String titulo = grafico.getNomeGrafico();
		
		// Dá dispose na janela ao fechar.
		addWindowListener(
			new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			}
		);
		
		//Adiciona o painel do gráfico.
		getContentPane().setLayout(new BorderLayout());
		graficoPanel.setToolTipText(Constantes.PANEL_GRAFICO_TOOLTIP);
		getContentPane().add(graficoPanel, BorderLayout.CENTER);
		
		//Cria e adiciona o título do gráfico.
		JLabel tituloLabel = new JLabel(titulo);
		tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
		tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(tituloLabel, BorderLayout.NORTH);
		
		setTitle(String.format("%s - %s", Constantes.STR_GRAFICO, titulo)); // Define o título da janela
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Define o que ocorre com a janela ao fecha-la, no caso o dispose define que as referêncidas dessa janela serão removidas da memória.
		setBounds(100, 100, 1280, 720); // Define as dimensões da janela.
		setResizable(false); // Define que a janela não deve ser redimesionável.
		setVisible(true); // Revela a janela e seus componentes.
	}// construtor

}