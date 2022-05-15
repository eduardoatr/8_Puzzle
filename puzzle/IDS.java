package puzzle;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class IDS {

    private DLS dls; // DLS para realizar a busca em profundidade limitada
    private Solucao solucao; // Ponteiro para a solucao após cada iteração
    private int profundidade; // Profundidade da busca limitada
    private int gerados; // Guarda o número de nodos gerados em cada iteração
    private int explorados; // Guarda o número de nodos explorados em cada iteração

    // Realiza a busca com aprofundamento iterativo
    public Solucao busca(Puzzle puzzle) {

        // Inicia as estruturas
        dls = new DLS();
        profundidade = 0;
        gerados = 0;
        explorados = 0;

        // Executa a busca limitada incrementando a profundidade a cada iteração
        do {
            solucao = dls.busca(puzzle, profundidade);
            gerados += solucao.getNodosGerados();
            explorados += solucao.getNodosExplorados();
            profundidade++;
            // Até que uma solução seja encontrada
        } while (solucao.getResultado() != 1);

        // Corrige o número de nodos gerados e explorados
        solucao.setNodosGerados(gerados);
        solucao.setNodosExplorados(explorados);

        return solucao;
    }

}
