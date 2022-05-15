package puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class Main {

    public static void main(String[] args) {

        String in; // Caminho do arquivo de entrada
        String out; // Caminho do arquivo de saída

        int n; // Tamanho do tabuleiro
        boolean isTeste; // Flag para teste
        String modo; // Modo normal ou teste

        String linha; // Para leitura linha a linha do arquivo
        String[] splitStr; // Para separar os valores de cada linha

        // Aloca todos os algoritmos
        BFS bfs = new BFS();
        IDS ids = new IDS();
        UCS ucs = new UCS();
        AS as = new AS();
        GBFS gbfs = new GBFS();
        HC hc = new HC();

        // Variáveis para executar os algoritmos
        Solucao solucao;
        int mat[][];
        long tempoInicial, tempoFinal;
        int cont;
        ArrayList<Puzzle> matrizes = new ArrayList<Puzzle>();

        if (args.length != 2) {
            System.out.println("ERRO: Numero incorreto de parâmetros");
            System.exit(1);
        }

        in = args[0];
        out = args[1];

        try {

            // Inicia as estruturas para leitura e escrita dos arquivos
            FileReader entrada = new FileReader(in);
            BufferedReader lerArq = new BufferedReader(entrada);
            FileWriter saida = new FileWriter(out);
            PrintWriter gravarArq = new PrintWriter(saida);

            // Lê a linha inicial e separa os valores em strings utilizando o espaçamento
            // como separador
            linha = lerArq.readLine();
            splitStr = linha.trim().split("\\s+");

            // Testa se o número de parâmetros está correto
            if (splitStr.length != 2) {
                System.out.println("ERRO: Formato do arquivo incorreto");
                entrada.close();
                System.exit(2);
            }

            // Atribui os parâmetros da primeira linha
            n = Integer.parseInt(splitStr[0]);
            modo = splitStr[1];

            // Verifica se os parâmetros do arquivo estão corretos
            if (Math.sqrt(n + 1) != Math.floor(Math.sqrt(n + 1))) {
                System.out.println("ERRO: Valor de n incorreto");
                entrada.close();
                System.exit(3);
            } else {
                n = (int) Math.sqrt(n + 1);
            }

            isTeste = false;

            if (!(modo.equalsIgnoreCase("TESTE") || modo.equals("SIMPLES"))) {
                System.out.println("ERRO: Modo incorreto");
                entrada.close();
                System.exit(3);
            } else {
                if (modo.equalsIgnoreCase("TESTE")) {
                    isTeste = true;
                }
            }

            linha = lerArq.readLine();

            // Começa a leitura das matrizes
            while (linha != null) {

                splitStr = linha.trim().split("\\s+");

                if (splitStr.length != (n * n)) {
                    System.out.println("ERRO: Matriz incorreta");
                    entrada.close();
                    System.exit(4);
                }

                cont = 0;
                mat = new int[n][n];

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        mat[i][j] = Integer.parseInt(splitStr[cont]);
                        cont++;
                    }
                }

                Puzzle puzzle = new Puzzle(n, mat);
                matrizes.add(puzzle);

                linha = lerArq.readLine();
            }

            System.out.println("Arquivo .in lido com sucesso!");
            entrada.close();

            for (int i = 0; i < matrizes.size(); i++) {

                tempoInicial = System.currentTimeMillis();
                solucao = bfs.busca(matrizes.get(i));
                tempoFinal = System.currentTimeMillis();

                gravarArq.printf("Entrada(" + (i + 1) + ")%n%n");
                gravarArq.printf("BFS: " + solucao.getEstadoFinal().getCusto() + " movimentos%n");
                gravarArq.printf("Solução: ");
                for (int e = 1; e < solucao.getEstados().size(); e++) {
                    gravarArq.printf(solucao.getEstados().get(e).getAcao() + " ");
                }
                gravarArq.printf("%n");

                if (isTeste) {
                    gravarArq.printf("Tempo: " + (tempoFinal - tempoInicial) + "%n");
                    gravarArq.printf("Gerados: " + solucao.getNodosGerados() + "%n");
                    gravarArq.printf("Explorados: " + solucao.getNodosExplorados() + "%n");
                }
                gravarArq.printf("%n");

                tempoInicial = System.currentTimeMillis();
                solucao = ucs.busca(matrizes.get(i));
                tempoFinal = System.currentTimeMillis();

                gravarArq.printf("UCS: " + solucao.getEstadoFinal().getCusto() + " movimentos%n");
                gravarArq.printf("Solução: ");
                for (int e = 1; e < solucao.getEstados().size(); e++) {
                    gravarArq.printf(solucao.getEstados().get(e).getAcao() + " ");
                }
                gravarArq.printf("%n");

                if (isTeste) {
                    gravarArq.printf("Tempo: " + (tempoFinal - tempoInicial) + "%n");
                    gravarArq.printf("Gerados: " + solucao.getNodosGerados() + "%n");
                    gravarArq.printf("Explorados: " + solucao.getNodosExplorados() + "%n");
                }
                gravarArq.printf("%n");

                tempoInicial = System.currentTimeMillis();
                solucao = ids.busca(matrizes.get(i));
                tempoFinal = System.currentTimeMillis();

                gravarArq.printf("IDS: " + solucao.getEstadoFinal().getCusto() + " movimentos%n");
                gravarArq.printf("Solução: ");
                for (int e = 1; e < solucao.getEstados().size(); e++) {
                    gravarArq.printf(solucao.getEstados().get(e).getAcao() + " ");
                }
                gravarArq.printf("%n");

                if (isTeste) {
                    gravarArq.printf("Tempo: " + (tempoFinal - tempoInicial) + "%n");
                    gravarArq.printf("Gerados: " + solucao.getNodosGerados() + "%n");
                    gravarArq.printf("Explorados: " + solucao.getNodosExplorados() + "%n");
                }
                gravarArq.printf("%n");

                tempoInicial = System.currentTimeMillis();
                solucao = gbfs.busca(matrizes.get(i), 1);
                tempoFinal = System.currentTimeMillis();

                gravarArq.printf("GBFS(h1): " + solucao.getEstadoFinal().getCusto() + " movimentos%n");
                gravarArq.printf("Solução: ");
                for (int e = 1; e < solucao.getEstados().size(); e++) {
                    gravarArq.printf(solucao.getEstados().get(e).getAcao() + " ");
                }
                gravarArq.printf("%n");

                if (isTeste) {
                    gravarArq.printf("Tempo: " + (tempoFinal - tempoInicial) + "%n");
                    gravarArq.printf("Gerados: " + solucao.getNodosGerados() + "%n");
                    gravarArq.printf("Explorados: " + solucao.getNodosExplorados() + "%n");
                }
                gravarArq.printf("%n");

                tempoInicial = System.currentTimeMillis();
                solucao = as.busca(matrizes.get(i), 2);
                tempoFinal = System.currentTimeMillis();

                gravarArq.printf("AS(h2): " + solucao.getEstadoFinal().getCusto() + " movimentos%n");
                gravarArq.printf("Solução: ");
                for (int e = 1; e < solucao.getEstados().size(); e++) {
                    gravarArq.printf(solucao.getEstados().get(e).getAcao() + " ");
                }
                gravarArq.printf("%n");

                if (isTeste) {
                    gravarArq.printf("Tempo: " + (tempoFinal - tempoInicial) + "%n");
                    gravarArq.printf("Gerados: " + solucao.getNodosGerados() + "%n");
                    gravarArq.printf("Explorados: " + solucao.getNodosExplorados() + "%n");
                }
                gravarArq.printf("%n");

                tempoInicial = System.currentTimeMillis();
                solucao = hc.busca(matrizes.get(i), 50, 2);
                tempoFinal = System.currentTimeMillis();

                gravarArq.printf("HC(h2): " + solucao.getEstadoFinal().getCusto() + " movimentos%n");
                gravarArq.printf("Solução: ");
                for (int e = 1; e < solucao.getEstados().size(); e++) {
                    gravarArq.printf(solucao.getEstados().get(e).getAcao() + " ");
                }
                gravarArq.printf("%n");

                if (isTeste) {
                    gravarArq.printf("Tempo: " + (tempoFinal - tempoInicial) + "%n");
                    gravarArq.printf("Gerados: " + solucao.getNodosGerados() + "%n");
                    gravarArq.printf("Explorados: " + solucao.getNodosExplorados() + "%n");
                }
                gravarArq.printf("%n");

                System.out.println("Entrada(" + (i + 1) + "): Calculada");
            }

            saida.close();
            System.out.println("Arquivo .out escrito com sucesso!");

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
            System.out.println();
        }
    }
}
