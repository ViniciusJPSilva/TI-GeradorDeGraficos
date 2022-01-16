package vjps.graficos.modelo;

import java.util.ArrayList;
import java.util.List;

public class Serie {

		private String nomeSerie;
		private List<ParDados> paresDados;
		
		public Serie() {
			paresDados = new ArrayList<>();
		}

		public Serie(String nomeSerie, List<ParDados> paresDados) {
			this.nomeSerie = nomeSerie;
			this.paresDados = paresDados;
		}

		public String getNomeSerie() {
			return nomeSerie;
		}

		public void setNomeSerie(String nomeSerie) {
			this.nomeSerie = nomeSerie;
		}

		public List<ParDados> getParesDados() {
			return paresDados;
		}

		public void setParesDados(List<ParDados> paresDados) {
			this.paresDados = paresDados;
		}

		public void adicionarParDeDados(String nome, float valor) {
			paresDados.add(new ParDados(nome, valor));
		}

		@Override
		public String toString() {
			return String.format("Serie %s:\n\t%s\n", nomeSerie, paresDados);
		}
	
}// class Serie
