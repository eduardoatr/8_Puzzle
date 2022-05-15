package puzzle;

import java.util.Comparator;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class EstimativaComparator implements Comparator<Puzzle> {

    // Compara utilizando a estimativa
    @Override
    public int compare(Puzzle puzzle, Puzzle outroPuzzle) {
        if (puzzle.getEstimativa() < outroPuzzle.getEstimativa()) {
            return -1;
        }

        if (puzzle.getEstimativa() > outroPuzzle.getEstimativa()) {
            return 1;
        }

        if (puzzle.getEstimativa() == outroPuzzle.getEstimativa()) {
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
