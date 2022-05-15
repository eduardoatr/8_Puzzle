package puzzle;

import java.util.Comparator;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class CustoEstimativaComparator implements Comparator<Puzzle> {

    // Compara utilizando o custo + estimativa
    @Override
    public int compare(Puzzle puzzle, Puzzle outroPuzzle) {

        int total = puzzle.getCusto() + puzzle.getEstimativa();
        int outroTotal = outroPuzzle.getCusto() + outroPuzzle.getEstimativa();

        if (total < outroTotal) {
            return -1;
        }

        if (total > outroTotal) {
            return 1;
        }

        if (total == outroTotal) {
            if (puzzle.getCusto() < outroPuzzle.getCusto()) {
                return -1;
            }
            if (puzzle.getCusto() > outroPuzzle.getCusto()) {
                return 1;
            }
        }

        return 0;
    }

}
