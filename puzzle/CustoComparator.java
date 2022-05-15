package puzzle;

import java.util.Comparator;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class CustoComparator implements Comparator<Puzzle> {

    // Compara utilizando o custo
    @Override
    public int compare(Puzzle puzzle, Puzzle outroPuzzle) {
        if (puzzle.getCusto() < outroPuzzle.getCusto()) {
            return -1;
        }

        if (puzzle.getCusto() > outroPuzzle.getCusto()) {
            return 1;
        }

        return 0;
    }

}
