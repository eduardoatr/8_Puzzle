package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Eduardo Vieira e Sousa
 */
public class Puzzle {

    private int tamanho; // Tamanho do tabuleiro
    private int tabuleiro[][]; // Matriz tamanho x tamanho
    private int id; // Identificador gerado a partir do tabuleiro para chave da tabela hash
    private String acao; // String que identifica a ação adotada para geração do tabuleiro a partir do
                         // pai
    private int custo; // Custo real para se atingir o estado atual
    private int estimativa; // Custo estimado através de uma heurística para atingir uma solução

    private Puzzle pai; // Ponteiro para o nodo pai

    // Gera uma lista de filhos de acordo com os movimentos possíveis sobre o
    // tabuleiro
    public ArrayList<Puzzle> expandirNodo() {

        int zeroLinha = 0, zeroColuna = 0; // Guarda coordenadas do espaço vazio(zero)

        ArrayList<Puzzle> filhos = new ArrayList<Puzzle>(); // Guarda os filhos gerados
        int novoTabuleiro[][]; // Para instanciação dos filhos
        Puzzle novoPuzzle; // Para instanciação dos filhos

        // Localiza a posição do zero
        for (int i = 0; i < this.tamanho; i++) {
            for (int j = 0; j < this.tamanho; j++) {
                if (this.tabuleiro[i][j] == 0) {
                    zeroLinha = i;
                    zeroColuna = j;
                    break;
                }
            }
        }

        // Testa e gera as expansões de estados possíveis em cada direção movendo o zero
        // para:
        // Cima
        if (zeroLinha != 0) {
            novoTabuleiro = this.clonaTabuleiro();

            novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha - 1][zeroColuna];
            novoTabuleiro[zeroLinha - 1][zeroColuna] = 0;

            novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Cima", this.custo + 1);
            novoPuzzle.setPai(this);

            filhos.add(novoPuzzle);
        }

        // Direita
        if (zeroColuna != this.tamanho - 1) {
            novoTabuleiro = this.clonaTabuleiro();

            novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha][zeroColuna + 1];
            novoTabuleiro[zeroLinha][zeroColuna + 1] = 0;

            novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Direita", this.custo + 1);
            novoPuzzle.setPai(this);

            filhos.add(novoPuzzle);
        }

        // Baixo
        if (zeroLinha != this.tamanho - 1) {
            novoTabuleiro = this.clonaTabuleiro();

            novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha + 1][zeroColuna];
            novoTabuleiro[zeroLinha + 1][zeroColuna] = 0;

            novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Baixo", this.custo + 1);
            novoPuzzle.setPai(this);

            filhos.add(novoPuzzle);
        }

        // Esquerda
        if (zeroColuna != 0) {
            novoTabuleiro = this.clonaTabuleiro();

            novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha][zeroColuna - 1];
            novoTabuleiro[zeroLinha][zeroColuna - 1] = 0;

            novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Esquerda", this.custo + 1);
            novoPuzzle.setPai(this);

            filhos.add(novoPuzzle);
        }

        return filhos;
    }

    // Gera uma lista de filhos selecionando movimentos no tabuleiro em ordem
    // aleatória
    public ArrayList<Puzzle> expandirNodoRand() {

        int zeroLinha = 0, zeroColuna = 0; // Guarda coordenadas do espaço vazio(zero)

        ArrayList<Puzzle> filhos = new ArrayList<Puzzle>(); // Guarda os filhos gerados
        int novoTabuleiro[][]; // Para instanciação dos filhos
        Puzzle novoPuzzle; // Para instanciação dos filhos
        int numero; // Para seleção da ação
        Random rand = new Random(); // Para seleção da ação
        ArrayList<Integer> sorteio = new ArrayList<Integer>(); // Para seleção da ação

        // Adiciona o número de cada ação para o sorteio
        sorteio.add(1);
        sorteio.add(2);
        sorteio.add(3);
        sorteio.add(4);

        // Localiza a posição do zero
        for (int i = 0; i < this.tamanho; i++) {
            for (int j = 0; j < this.tamanho; j++) {
                if (this.tabuleiro[i][j] == 0) {
                    zeroLinha = i;
                    zeroColuna = j;
                    break;
                }
            }
        }

        // Para todas as ações possíveis, sorteia uma ação e expande o nodo
        while (!sorteio.isEmpty()) {

            // Sorteia um índice
            numero = rand.nextInt(sorteio.size());

            // Recupera o número daquele índice e remove
            numero = sorteio.remove(numero);

            switch (numero) {
                // Cima
                case 1:

                    if (zeroLinha != 0) {
                        novoTabuleiro = this.clonaTabuleiro();

                        novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha - 1][zeroColuna];
                        novoTabuleiro[zeroLinha - 1][zeroColuna] = 0;

                        novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Cima", this.custo + 1);
                        novoPuzzle.setPai(this);

                        filhos.add(novoPuzzle);
                    }
                    break;

                // Direita
                case 2:

                    if (zeroColuna != this.tamanho - 1) {
                        novoTabuleiro = this.clonaTabuleiro();

                        novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha][zeroColuna + 1];
                        novoTabuleiro[zeroLinha][zeroColuna + 1] = 0;

                        novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Direita", this.custo + 1);
                        novoPuzzle.setPai(this);

                        filhos.add(novoPuzzle);
                    }
                    break;

                // Baixo
                case 3:

                    if (zeroLinha != this.tamanho - 1) {
                        novoTabuleiro = this.clonaTabuleiro();

                        novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha + 1][zeroColuna];
                        novoTabuleiro[zeroLinha + 1][zeroColuna] = 0;

                        novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Baixo", this.custo + 1);
                        novoPuzzle.setPai(this);

                        filhos.add(novoPuzzle);
                    }
                    break;

                // Esquerda
                case 4:

                    if (zeroColuna != 0) {
                        novoTabuleiro = this.clonaTabuleiro();

                        novoTabuleiro[zeroLinha][zeroColuna] = novoTabuleiro[zeroLinha][zeroColuna - 1];
                        novoTabuleiro[zeroLinha][zeroColuna - 1] = 0;

                        novoPuzzle = new Puzzle(this.tamanho, novoTabuleiro, "Esquerda", this.custo + 1);
                        novoPuzzle.setPai(this);

                        filhos.add(novoPuzzle);
                    }
                    break;
            }
        }

        return filhos;
    }

    // Testa se é um estado final
    public boolean testaEstadoFinal() {

        int contador = 1;

        for (int i = 0; i < this.tamanho; i++) {
            for (int j = 0; j < this.tamanho; j++) {
                if ((this.tabuleiro[i][j] != contador) && (contador != (this.tamanho * this.tamanho))) {
                    return false;
                }
                contador++;
            }
        }

        return true;
    }

    // Gera o caminho do nodo até a raiz da árvore de estados
    public ArrayList<Puzzle> geraCaminho() {

        ArrayList<Puzzle> caminho = new ArrayList<Puzzle>();
        Puzzle nodo = this;

        while (nodo.pai != null) {
            caminho.add(nodo);
            nodo = nodo.pai;
        }

        Collections.reverse(caminho);
        return caminho;
    }

    // Copia o tabuleiro
    public int[][] clonaTabuleiro() {

        int copia[][] = new int[this.tamanho][this.tamanho];

        for (int i = 0; i < this.tamanho; i++) {
            for (int j = 0; j < this.tamanho; j++) {
                copia[i][j] = this.tabuleiro[i][j];
            }
        }

        return copia;
    }

    // Printa o tabuleiro
    public void printTabuleiro() {
        System.out.println("\n");
        for (int i = 0; i < this.tamanho; i++) {
            System.out.print("| ");
            for (int j = 0; j < this.tamanho; j++) {
                System.out.print(this.tabuleiro[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("\n");
    }

    // Gera um identificador único para cada Puzzle baseado na ordem do tabuleiro
    public void geraID() {
        int res = 0, m = 1;

        // Cada valor do tabuleiro representa um digito de um número de n^2 dígitos
        for (int i = this.tamanho - 1; i >= 0; i--) {
            for (int j = this.tamanho - 1; j >= 0; j--) {
                res = res + (this.tabuleiro[i][j] * m);
                m = m * 10;
            }
        }

        this.id = res;
    }

    // Construtores
    public Puzzle(int tamanho, int[][] tabuleiro) {
        this.tamanho = tamanho;
        this.tabuleiro = tabuleiro;
        this.custo = 0;
        this.estimativa = 0;
        this.geraID();
    }

    public Puzzle(int tamanho, int[][] tabuleiro, String acao, int custo) {
        this.tamanho = tamanho;
        this.tabuleiro = tabuleiro;
        this.acao = acao;
        this.custo = custo;
        this.estimativa = 0;
        this.geraID();
    }

    // Getters & Setters
    public int getTamanho() {
        return this.tamanho;
    }

    public int[][] getTabuleiro() {
        return this.tabuleiro;
    }

    public int getID() {
        return id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public int getCusto() {
        return this.custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getEstimativa() {
        return this.estimativa;
    }

    public void setEstimativa(int estimativa) {
        this.estimativa = estimativa;
    }

    public Puzzle getPai() {
        return this.pai;
    }

    public void setPai(Puzzle pai) {
        this.pai = pai;
    }

    // Para comparação e utilização da tabela hash e listas
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    // Para comparação e utilização da tabela hash e listas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Puzzle other = (Puzzle) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    // Para impressão
    @Override
    public String toString() {
        return "Puzzle{" + "tabuleiro=" + Arrays.toString(tabuleiro) + ", custo=" + custo + ", estimativa=" + estimativa
                + '}';
    }

}
