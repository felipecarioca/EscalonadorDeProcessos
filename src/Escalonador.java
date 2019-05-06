import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Escalonador {

	public static void main(String[] args) {
	 
		 Scanner scanner = new Scanner(System.in);
	
		 int N, entrada, tempoAtual, execucao, idProcessoAtual, qteprocessos;
		 
		 ArrayList entradas, duracoes, prioridades, processos, cpentradas;
		 
		 int[] temposFinais, temposIniciais;
		 String ordemExecucao;
		 int contTeste = 0;
		 String formato, saida;
		 DecimalFormat nf = new DecimalFormat("0.00");
	
		 System.out.println("N° de processos para armazenar:");
		 N = scanner.nextInt();
		 
		 System.out.println();

		 while (N != 0) {
			 
			 contTeste++;
			 
			 processos = new ArrayList();
			 entradas = new ArrayList();
			 duracoes = new ArrayList();
			 prioridades = new ArrayList();

		 	// lendo os processos
			 for (int i = 0; i < N; i++) {
				 
				 // le e adiciona dados dos processos em suas respectivas listas
				 System.out.println("Entrada do processo P" + (i+1) + ":");
				 entrada = scanner.nextInt();
				 entradas.add(entrada);
				 
				 System.out.println("Duração do P" + (i+1) + ":");
				 
				 entrada = scanner.nextInt();
				 duracoes.add(entrada);
				
				 System.out.println("Prioridade do P" + (i+1) + ":");
				 
			 	 entrada = scanner.nextInt();
			 	 prioridades.add(entrada);
		 	 }
			 
			 temposIniciais = new int[N];
			 temposFinais = new int[N];
			 
		 	 // cria copia da lista de tempos de ingressos devido a modificacoes
			 cpentradas = (ArrayList) entradas.clone();

			 ordemExecucao = "";

			 // tempo comeca do primeiro processo a ingressar
			 tempoAtual = (int) entradas.get(0);

			 qteprocessos = N;
			 
			 while (qteprocessos > 0) {
				 // percorrendo ingressos para descobrir processos que entram no tempo atual
				 
				 processos = new ArrayList();
				 
				 for (int i = 0; i < N; i++) {
					 if ((int) entradas.get(i) != -1 && (int) entradas.get(i) <= tempoAtual) {
						 // adicionar na lista de processos
						 processos.add(i);
					 }
				 }

				 if (processos.isEmpty()) {
					 tempoAtual++;
				 } else {
					 // assumindo que o primeiro da lista eh o de menor prioridade
					 execucao = (int) processos.get(0);
					 
					 for (int i = 0; i < processos.size(); i++) {
						 
						 idProcessoAtual = (int) processos.get(i);
						 // se a prioridade do processo atual for menor do que a menor prioridade ja encontrada
						 
						 if ((int) prioridades.get(idProcessoAtual) < (int) prioridades.get(execucao)) {
							 // entao alteramos o processo que vai executar
							 execucao = (int) processos.get(i);
						 }
					 }

					 temposIniciais[execucao] = tempoAtual;

					 tempoAtual += (int) duracoes.get(execucao);

					 // tempo que o processo termina de executar
					 temposFinais[execucao] = tempoAtual;
					 entradas.set(execucao, -1);

					 // define ordem de execucao
					 ordemExecucao += "P" + (execucao + 1) + " ";

					 qteprocessos--;
				 }
			 }

			 // calculo tempo de execucao e tempo de espera
			 double tempoExecucao = 0, tempoEspera = 0;
			 
			 for (int i = 0; i < N; i++) {
				 tempoExecucao += temposFinais[i] - (int) cpentradas.get(i);
				 tempoEspera += temposIniciais[i] - (int) cpentradas.get(i);
			 }
			 
			 tempoExecucao = tempoExecucao / N;
			 tempoEspera = tempoEspera / N;
			 
			 System.out.println("Processamento n° " + contTeste);

			 formato = nf.format(tempoExecucao);
			 saida = "Tempo médio de execução: " + formato + "s";
			 saida = saida.replace(".", ",");
			 
			 System.out.println(saida);

			 formato = nf.format(tempoEspera);
			 saida = "Tempo médio de espera: " + formato + "s";
			 saida = saida.replace(".", ",");
			 
			 System.out.println(saida);

			 System.out.println("Ordem de execução: " + ordemExecucao);
			 System.out.println();
			 
			 N = scanner.nextInt();
		 }
	}
}