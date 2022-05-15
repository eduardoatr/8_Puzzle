package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class UCS {

    private PriorityQueue<Puzzle> fila; // Fila de prioridade para guardar os nodos abertos
    private HashMap<Integer, Puzzle> fronteira; // Hash para guardar os nodos abertos para busca rápida
    private HashSet<Integer> explorados; // Hash para guardar as IDs os nodos explorados
    private ArrayList<Puzzle> filhos; // Lista para guardar os filhos gerados na expansão do nodo

    private Puzzle nodoAtual; // Ponteiro para o nodo sendo explorado
    private Puzzle filhoAtual; // Ponteiro para o filho sendo explorado
    private Solucao solucao; // Ponteiro para a solução
    private int nodosGerados; // Contador para o número de nodos gerados

    // Realiza a busca de custo uniforme
    public Solucao busca(Puzzle puzzle) {

        // Inicia o contador de nodos gerados
        nodosGerados = 0;

        // Comparador para o custo
        CustoComparator comparator = new CustoComparator();

        // Inicia as estruturas
        fila = new PriorityQueue<Puzzle>(1000, comparator);
        fronteira = new HashMap<Integer, Puzzle>();
        explorados = new HashSet<Integer>();

        // Adiciona o estado inicial
        fila.add(puzzle);
        fronteira.put(puzzle.getID(), puzzle);

        // Enquanto ainda existirem nodos na fronteira
        while (!fila.isEmpty()) {

            // Retira o primeiro nodo da fila com menor custo
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

                // Checa se não está em alguma das listas
                if (!(explorados.contains(filhoAtual.getID()) || (fronteira.containsKey(filhoAtual.getID())))) {

                    // Adiciona à fronteira
                    fila.add(filhoAtual);
                    fronteira.put(filhoAtual.getID(), filhoAtual);
                } else {

                    // Caso já esteja na fronteira
                    if (fronteira.containsKey(filhoAtual.getID())) {

                        // Substitui na fila o com menor custo
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
