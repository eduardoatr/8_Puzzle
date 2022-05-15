package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class GBFS {

    private PriorityQueue<Puzzle> fila; // Fila de prioridade para guardar os nodos abertos
    private HashMap<Integer, Puzzle> fronteira; // Hash para guardar os nodos abertos para busca rápida
    private HashSet<Integer> explorados; // Hash para guardar as IDs os nodos explorados
    private ArrayList<Puzzle> filhos; // Lista para guardar os filhos gerados na expansão do nodo

    private Puzzle nodoAtual; // Ponteiro para o nodo sendo explorado
    private Puzzle filhoAtual; // Ponteiro para o filho sendo explorado
    private Solucao solucao; // Ponteiro para a solução
    private Heuristica heuristica; // Objeto com diferentes heurísticas a serem aplicadas
    private int nodosGerados; // Contador para o número de nodos gerados

    // Realiza a busca gulosa utilizando a heurística selecionada
    public Solucao busca(Puzzle puzzle, int h) {

        // Inicia o contador de nodos gerados
        nodosGerados = 0;

        // Comparador para a estimativa
        EstimativaComparator comparator = new EstimativaComparator();

        // Inicia as estruturas
        fila = new PriorityQueue<Puzzle>(1000, comparator);
        fronteira = new HashMap<Integer, Puzzle>();
        explorados = new HashSet<Integer>();
        heuristica = new Heuristica();

        // Adiciona o estado inicial
        fila.add(puzzle);
        fronteira.put(puzzle.getID(), puzzle);

        // Calcula a estimativa do estado inicial
        if (h == 1) {
            // Utilizando a quantidade de peças fora do lugar
            puzzle.setEstimativa(heuristica.calculaPecasFora(puzzle));
        } else {
            // Utilizando a Distância de Manhattan
            puzzle.setEstimativa(heuristica.calculaDistMan(puzzle));
        }

        // Enquanto ainda existirem nodos na fronteira
        while (!fila.isEmpty()) {

            // Retira o primeiro nodo da fila com menor estimativa
            nodoAtual = fila.poll();
            fronteira.remove(nodoAtual.getID());

            // Testa se é estado final
            if (nodoAtual.testaEstadoFinal()) {
                solucao = new Solucao(1, puzzle, nodoAtual, nodosGerados, this.explorados.size());
                return solucao;
            }

            // Expande o nodo
            filhos = nodoAtual.expandirNodo();

            // Adiciona à lista de explorados
            explorados.add(nodoAtual.getID());

            // Atualiza o contador de nodos gerados
            nodosGerados += filhos.size();

            // Para cada um dos nodos filhos
            for (int i = 0; i < filhos.size(); i++) {

                // Seleciona o filho
                filhoAtual = filhos.get(i);

                // Calcula a estimativa do filho
                if (h == 1) {
                    // Utilizando a quantidade de peças fora do lugar
                    filhoAtual.setEstimativa(heuristica.calculaPecasFora(filhoAtual));
                } else {
                    // Utilizando a Distância de Manhattan
                    filhoAtual.setEstimativa(heuristica.calculaDistMan(filhoAtual));
                }

                // Checa se não está em alguma das listas
                if (!(explorados.contains(filhoAtual.getID()) || (fronteira.containsKey(filhoAtual.getID())))) {

                    // Adiciona à fronteira
                    fila.add(filhoAtual);
                    fronteira.put(filhoAtual.getID(), filhoAtual);
                } else {

                    // Caso já esteja na fronteira
                    if (fronteira.containsKey(filhoAtual.getID())) {

                        // Substitui na lista o com menor estimativa
                        if (comparator.compare(filhoAtual, fronteira.get(filhoAtual.getID())) == -1) {

                            // Remove o antigo
                            fila.remove(filhoAtual);
                            fronteira.remove(filhoAtual.getID());

                            // Adiciona o novo
                            fila.add(filhoAtual);
                            fronteira.put(filhoAtual.getID(), filhoAtual);
                        }
                    }
                }
            }
        }

        // Caso toda a fronteira seja explorada, retorna fracasso
        solucao = new Solucao(-1, puzzle, nodosGerados, this.explorados.size());
        return solucao;
    }

}
