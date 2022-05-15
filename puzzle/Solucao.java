package puzzle;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class Solucao {

    private int resultado; // Retorna: -1 para "failure", 0 para "cutoff" e 1 para "success"
    private Puzzle estadoInicial; // Aponta para o estado inicial do tabuleiro
    private Puzzle estadoFinal; // Aponta para o estado final do tabuleiro
    private ArrayList<Puzzle> estados; // Sequência de estados ordenados correspondentes à solução
    private int nodosGerados; // Guarda a quantidade de nodos que foram gerados
    private int nodosExplorados; // Guarda a quantidade de nodos que foram explorados

    public Solucao(int resultado, Puzzle estadoInicial, int nodosGerados, int nodosExplorados) {
        this.estadoInicial = estadoInicial;
        this.resultado = resultado;
        this.nodosGerados = nodosGerados;
        this.nodosExplorados = nodosExplorados;
        estados = new ArrayList<Puzzle>();
    }

    public Solucao(int resultado, Puzzle estadoInicial, Puzzle estadoFinal, int nodosGerados, int nodosExplorados) {
        this.resultado = resultado;
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
        this.nodosGerados = nodosGerados;
        this.nodosExplorados = nodosExplorados;

        estados = new ArrayList<Puzzle>();
        Puzzle estadoAtual = estadoFinal;

        // Gera a lista de estados na sequência correta
        while (estadoAtual != null) {
            estados.add(estadoAtual);
            estadoAtual = estadoAtual.getPai();
        }

        Collections.reverse(estados);
    }

    // Imprime a solução na forma de matrizes
    public void printMatriz() {
        for (int i = 0; i < estados.size(); i++) {
            System.out.print(i + ": ");
            estados.get(i).printTabuleiro();
        }
    }

    // Imprime a solução na forma de direções
    public void printDirecao() {
        for (int i = 1; i < estados.size(); i++) {
            System.out.println(i + ": " + estados.get(i).getAcao());
        }
        System.out.println();
    }

    public int getResultado() {
        return resultado;
    }

    public Puzzle getEstadoInicial() {
        return estadoInicial;
    }

    public Puzzle getEstadoFinal() {
        return estadoFinal;
    }

    public int getNodosGerados() {
        return nodosGerados;
    }

    public void setNodosGerados(int nodosGerados) {
        this.nodosGerados = nodosGerados;
    }

    public int getNodosExplorados() {
        return nodosExplorados;
    }

    public void setNodosExplorados(int nodosExplorados) {
        this.nodosExplorados = nodosExplorados;
    }

    public ArrayList<Puzzle> getEstados() {
        return estados;
    }

    @Override
    public String toString() {
        return "Solucao{" + "resultado=" + resultado + ", estadoInicial=" + estadoInicial + ", estadoFinal="
                + estadoFinal + '}';
    }

}
