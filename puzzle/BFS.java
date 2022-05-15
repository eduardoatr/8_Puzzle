package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class BFS {

    private LinkedList<Integer> fila; // Fila para as IDs dos nodos abertos
    private HashMap<Integer, Puzzle> fronteira; // Hash para guardar os nodos abertos
    private HashSet<Integer> explorados; // Hash para guardar as IDs os nodos explorados
    private ArrayList<Puzzle> filhos; // Lista para guardar os filhos gerados na expansão do nodo

    private Puzzle nodoAtual; // Ponteiro para o nodo sendo explorado
    private Puzzle filhoAtual; // Ponteiro para o filho sendo explorado
    private Solucao solucao; // Ponteiro para a solução
    private int nodosGerados; // Contador para o número de nodos gerados

    // Realiza a busca em largura
    public Solucao busca(Puzzle puzzle) {

        // Testa o estado inicial
        if (puzzle.testaEstadoFinal()) {
            solucao = new Solucao(1, puzzle, puzzle, 0, 0);
            return solucao;
        } else {

            // Inicia as estruturas
            fila = new LinkedList<Integer>();
            fronteira = new HashMap<Integer, Puzzle>();
            explorados = new HashSet<Integer>();

            // Adiciona o estado inicial
            fila.add(puzzle.getID());
            fronteira.put(puzzle.getID(), puzzle);

            // Inicia o contador de nodos gerados
            nodosGerados = 0;

            // Enquanto ainda existirem nodos na fronteira
            while (!fila.isEmpty()) {

                // Retira o primeiro nodo da fila (FIFO) e remove o nodo na tabela Hash
                nodoAtual = fronteira.remove(fila.poll());

                // Adiciona à lista de explorados
                explorados.add(nodoAtual.getID());

                // Expande o nodo
                filhos = nodoAtual.expandirNodo();

                // Atualiza o contador de nodos gerados
                nodosGerados += filhos.size();

                // Para cada um dos nodos filhos
                for (int i = 0; i < filhos.size(); i++) {

                    // Seleciona o filho
                    filhoAtual = filhos.get(i);

                    // Checa se não está em alguma das listas
                    if (!(explorados.contains(filhoAtual.getID()) || (fronteira.containsKey(filhoAtual.getID())))) {

                        // Testa se é estado final
                        if (filhoAtual.testaEstadoFinal()) {
                            solucao = new Solucao(1, puzzle, filhoAtual, nodosGerados, this.explorados.size());
                            return solucao;
                        }

                        // Adiciona à fronteira
                        fila.add(filhoAtual.getID());
                        fronteira.put(filhoAtual.getID(), filhoAtual);
                    }
                }
            }
        }

        // Caso toda a fronteira seja explorada, retorna fracasso
        solucao = new Solucao(-1, puzzle, nodosGerados, this.explorados.size());
        return solucao;
    }

}
