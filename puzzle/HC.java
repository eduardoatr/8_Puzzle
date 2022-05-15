package puzzle;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class HC {

    private int nrMovimentos; // Limite de movimentos laterais
    private Puzzle nodoAtual; // Ponteiro para o nodo sendo explorado
    private Puzzle vizinhoAtual; // Ponteiro para o vizinho sendo explorado
    private Solucao solucao; // Ponteiro para a solução
    private Heuristica heuristica; // Objeto com diferentes heurísticas
    private ArrayList<Puzzle> vizinhos; // Lista para guardar os vizinhos gerados na expansão do nodo
    private int nodosGerados; // Contador para o número de nodos gerados

    // Realiza a busca Hill Climbing utilizando o limite de movimentos laterais e a
    // heurística selecionada
    public Solucao busca(Puzzle puzzle, int limite, int h) {

        // Inicia as estruturas
        heuristica = new Heuristica();
        nodoAtual = puzzle;
        nrMovimentos = 0;

        // Comparador para a estimativa
        EstimativaComparator comparator = new EstimativaComparator();

        // Calcula a estimativa do estado inicial
        if (h == 1) {
            // Utilizando a quantidade de peças fora do lugar
            puzzle.setEstimativa(heuristica.calculaPecasFora(puzzle));
        } else {
            // Utilizando a Distância de Manhattan
            puzzle.setEstimativa(heuristica.calculaDistMan(puzzle));
        }

        // Inicia o contador de nodos gerados
        nodosGerados = 0;

        // Enquanto o número de movimentos laterais for menos do que o limite
        while (nrMovimentos <= limite) {

            // Expande o nodo (versão randomica da função para diminuir os loops em
            // shoulders)
            vizinhos = nodoAtual.expandirNodoRand();

            // Atualiza o contador de nodos gerados
            nodosGerados += vizinhos.size();

            // Calcula a estimativa para cada vizinho
            for (int i = 0; i < vizinhos.size(); i++) {
                vizinhoAtual = vizinhos.get(i);

                if (h == 1) {
                    // Utilizando a quantidade de peças fora do lugar
                    vizinhoAtual.setEstimativa(heuristica.calculaPecasFora(vizinhoAtual));
                } else {
                    // Utilizando a Distância de Manhattan
                    vizinhoAtual.setEstimativa(heuristica.calculaDistMan(vizinhoAtual));
                }
            }

            // Seleciona o vizinho com a menor estimativa
            vizinhoAtual = Collections.min(vizinhos, comparator);

            // Testa se a menor estimativa dos vizinhos é menor que a do nodo atual e
            // substitui (minimizando a estimativa)
            if (vizinhoAtual.getEstimativa() < nodoAtual.getEstimativa()) {
                nodoAtual = vizinhoAtual;
            } else {
                // Caso seja igual, fazemos um movimento lateral
                if (vizinhoAtual.getEstimativa() == nodoAtual.getEstimativa()) {
                    nodoAtual = vizinhoAtual;
                    nrMovimentos++;
                } else {
                    // Caso seja maior, encontramos um mínimo local
                    solucao = new Solucao(1, puzzle, nodoAtual, nodosGerados, nodoAtual.getCusto());
                    return solucao;
                }
            }
        }

        // Caso o número de movimentos tenha se esgotado, retorna o nodoAtual
        solucao = new Solucao(0, puzzle, nodoAtual, nodosGerados, nodoAtual.getCusto());
        return solucao;
    }

}
