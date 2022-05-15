package puzzle;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class Heuristica {

    // Peças fora do lugar
    public int calculaPecasFora(Puzzle puzzle) {

        // Utilizando a relação entre o índice e o número da peça
        int h = 0, contador = 1, tamanho = puzzle.getTamanho();

        // Para cada posição do tabuleiro, começando pela primeira
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {

                // Se o elemento na posição n não for o n, a peça dessa posição está fora do
                // lugar
                if ((puzzle.getTabuleiro()[i][j] != contador) && (contador != (tamanho * tamanho))) {
                    h++;
                }

                contador++;
            }
        }

        return h;
    }

    // Distância de Manhattan
    public int calculaDistMan(Puzzle puzzle) {

        // Utilizando a relação entre o número da peça e suas coordenadas no tabuleiro
        int h = 0, tamanho = puzzle.getTamanho();
        int n, div, resto, distancia = 0;

        // Para cada peça do tabuleiro
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {

                // Calcula-se a divisão e o resto de n pelo tamanho do tabuleiro
                n = puzzle.getTabuleiro()[i][j];
                div = n / puzzle.getTamanho();
                resto = n % puzzle.getTamanho();

                // Se o resto for 0, o lugar original de n é: [div-1 , tamanho-1]. A divisão
                // indica a linha e a coluna só pode ser a última, já que temos resto 0
                // Se o reto for um valor r, a linha é indicada por quantas vezes o número cabe
                // dentro do tamanho e o resto indica a coluna.
                if (n != 0) {
                    // Faz o cálculo da distância usando as coordenadas obtidas
                    if ((resto == 0)) {
                        distancia = Math.abs(i - (div - 1)) + Math.abs(j - (tamanho - 1));
                    } else {
                        distancia = Math.abs(i - div) + Math.abs(j - (resto - 1));
                    }

                    h = h + distancia;
                }
            }
        }

        return h;
    }

}
