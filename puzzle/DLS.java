package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class DLS {

    private Stack<Integer> pilha; // Pilha para guardar os nodos abertos
    private HashMap<Integer, Puzzle> fronteira; // Hash para guardar os nodos abertos
    private HashMap<Integer, Puzzle> explorados; // Hash para guardar os nodos explorados
    private ArrayList<Puzzle> filhos; // Lista para guardar os filhos gerados na expansão do nodo

    private Puzzle nodoAtual; // Ponteiro para o nodo sendo explorado
    private Puzzle filhoAtual; // Ponteiro para o filho sendo explorado
    private Solucao solucao; // Ponteiro para a solução
    private int nodosGerados; // Contador para o número de nodos gerados

    // Realiza a busca em profundidade limitada
    public Solucao busca(Puzzle puzzle, int limite) {

        // Inicia o contador de nodos gerados
        nodosGerados = 0;

        // Inicia as estruturas
        pilha = new Stack<Integer>();
        fronteira = new HashMap<Integer, Puzzle>();
        explorados = new HashMap<Integer, Puzzle>();

        // Adiciona o estado inicial
        pilha.push(puzzle.getID());
        fronteira.put(puzzle.getID(), puzzle);

        // Enquanto ainda existirem nodos na pilha
        while (!pilha.isEmpty()) {

            // Retira sempre do fim da fila (LIFO) e remove o nodo na tabela Hash
            nodoAtual = fronteira.remove(pilha.pop());

            // Testa se é estado final
            if (nodoAtual.testaEstadoFinal()) {
                solucao = new Solucao(1, puzzle, nodoAtual, nodosGerados, this.explorados.size());
                return solucao;
            }

            // Caso o nodo esteja na profundidade permitida
            if (nodoAtual.getCusto() < limite) {

                // Expande o nodo
                filhos = nodoAtual.expandirNodo();

                // Adiciona à lista de explorados
                explorados.put(nodoAtual.getID(), nodoAtual);

                // Atualiza o contador de nodos gerados
                nodosGerados += filhos.size();

                // Para cada um dos nodos filhos
                for (int i = 0; i < filhos.size(); i++) {

                    // Seleciona o filho
                    filhoAtual = filhos.get(i);

                    // Checa se não está em alguma das listas
                    if (!pilha.contains(filhoAtual.getID()) && !explorados.containsKey(filhoAtual.getID())) {

                        // Adiciona à pilha
                        fronteira.put(pilha.push(filhoAtual.getID()), filhoAtual);

                    } else {

                        // Se o nodoAtual já foi explorado
                        if (explorados.containsKey(filhoAtual.getID())) {
                            // Se seu custo é menor do que o que está nos explorados (ele pode fazer parte
                            // da solução que pode ser atingida dentro do limite)
                            if (filhoAtual.getCusto() <= explorados.get(filhoAtual.getID()).getCusto()) {

                                // Insere o nodo na fronteira
                                fronteira.put(pilha.push(filhoAtual.getID()), filhoAtual);

                                // Remove dos explorados
                                explorados.remove(filhoAtual.getID());

                            }
                        }
                    }
                }
            }
        }

        // Caso toda a pilha seja explorada, retorna fracasso
        solucao = new Solucao(0, puzzle, nodosGerados, this.explorados.size());

        return solucao;
    }

}
