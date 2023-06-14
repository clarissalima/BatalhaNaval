
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Jogo {


    //variaveis do tabuleiro
    static final int VAZIO = 0;
    static final int NAVIO = 1;
    static final int ERROU_TIRO = 2;
    static final int ACERTOU_TIRO = 3;

    static final int POSICAO_X = 0;
    static final int POSICAO_Y = 1;


    static String nomeJogador1, nomeJogador2;
    static int tamanhoX, tamanhoY, quantidadeDeNavios, limiteMaxNavios;
    static int tabuleiroJogador1[][], tabuleiroJogador2[][];
    static Scanner input = new Scanner(System.in);
    static int naviosJogador1, naviosJogador2;

    public static void obterTamanhoDosTabuleiros() {

        try {

            input = new Scanner(System.in);
            System.out.println("Digite a altura do tabuleiro: ");
            tamanhoX = input.nextInt();

            System.out.println("Digite o comprimento do tabuleiro: ");
            tamanhoY = input.nextInt();

        } catch (InputMismatchException erro) {
            System.out.println("Digite um valor numerico");

        }

    }


    public static void obterNomesDosJogadores() {
        System.out.println("Digite o nome do jogador 1: ");
        nomeJogador1 = input.next();

        System.out.println("Digite o nome do jogador 2: ");
        nomeJogador2 = input.next();
    }


    public static void calcularQtdMaxNavios() {
        limiteMaxNavios = (tamanhoX * tamanhoY) / 3;
    }

    public static void iniciarTamanhos() {
        tabuleiroJogador1 = retornarNovoTabuleiroVazio();
        tabuleiroJogador2 = retornarNovoTabuleiroVazio();

    }

    public static int[][] retornarNovoTabuleiroVazio() {
        return new int[tamanhoX][tamanhoY];
    }

    public static void obterQtdNavios() {

        Scanner input = new Scanner(System.in);
        System.out.println("Digite a quantidade de navios do jogo: ");
        System.out.println("Max: " + limiteMaxNavios + " navios");
        quantidadeDeNavios = input.nextInt();

        if (quantidadeDeNavios < 1 && quantidadeDeNavios > 8) {
            quantidadeDeNavios = limiteMaxNavios;
        }

    }

    public static void instanciarContadoresDeNaviosDosJogadores() {
        naviosJogador1 = quantidadeDeNavios;
        naviosJogador2 = quantidadeDeNavios;
    }


    public static int[][] retornarNovoTabuleiroComOsNavios() {

        int novoTabuleiro[][] = retornarNovoTabuleiroVazio();
        int quantidadeRestanteDeNavios = quantidadeDeNavios;
        int x = 0, y = 0;
        Random numeroAleatorio = new Random();
        do {
            x = 0;
            y = 0;
            for (int[] linha : novoTabuleiro) {
                for (int coluna : linha) {
                    if (numeroAleatorio.nextInt(100) <= 10) {
                        if (coluna == VAZIO) {
                            novoTabuleiro[x][y] = NAVIO;
                            quantidadeRestanteDeNavios--;
                            break;
                        }

                        if (quantidadeRestanteDeNavios <= 0) {
                            break;

                        }

                    }
                    y++;
                }
                y = 0;
                x++;

                if (quantidadeRestanteDeNavios <= 0) {
                    break;
                }

            }
        }
        while (quantidadeRestanteDeNavios > 0);
        return novoTabuleiro;

    }

    public static void inserirOsNaviosNoTabuleiroDosJogadores() {
        tabuleiroJogador1 = retornarNovoTabuleiroComOsNavios();
        tabuleiroJogador2 = retornarNovoTabuleiroComOsNavios();

    }


    public static void exibirNumerosDasColunasDosTabuleiros() {
        int numeroDaColuna = 1;
        String numerosDoTabuleiro = "   ";

        for (int i = 0; i < tamanhoY; i++) {
            numerosDoTabuleiro += (numeroDaColuna++) + " ";
        }
        System.out.println(numerosDoTabuleiro);

    }

    public static void exibirTabuleiro(String nomeDoJogador, int[][] tabuleiro, boolean seuTabuleiro) {
        System.out.println("|----- " + nomeDoJogador + " -----| ");
        exibirNumerosDasColunasDosTabuleiros();
        String linhaDoTabuleiro = "";
        char letraDaLinha = 65;
        for (int[] linha : tabuleiro) {
            linhaDoTabuleiro = (letraDaLinha++) + " |";


            for (int coluna : linha) {
                switch (coluna) {
                    case VAZIO:
                        linhaDoTabuleiro += " |";
                        break;
                    case NAVIO:
                        if (seuTabuleiro) {
                            linhaDoTabuleiro += "N|";
                            break;
                        } else {
                            linhaDoTabuleiro += " |";
                            break;
                        }
                    case ERROU_TIRO:
                        linhaDoTabuleiro += "X|";
                        break;
                    case ACERTOU_TIRO:
                        linhaDoTabuleiro += "D|";
                        break;
                }
            }
            System.out.println(linhaDoTabuleiro);
        }

    }


    public static void exibirTabuleirosDosJogadores() {
        exibirTabuleiro(nomeJogador1, tabuleiroJogador1, true);
        exibirTabuleiro(nomeJogador2, tabuleiroJogador2, false);
    }


    //aulas 29, 30,31

    public static boolean validarPosicoesInseridasPeloJogador(int[] posicoes) {

        boolean retorno = true;
        if (posicoes[0] > tamanhoX - 1) {

            retorno = false;

            System.out.println("A posição das letras não pode ser maior que " + (char) tamanhoX + 64);
        }

        if (posicoes[1] > tamanhoY) {
        	
        	retorno = false;
            System.out.println("A posição numérica não pode ser maior que " + tamanhoY);
        }
        return retorno;
    }

    public static String receberValorDigitadoPeloJogador() {
        System.out.println("Digite a posição do seu tiro: ");

        return input.next();
    }

    public static boolean validarTiroJogador(String tiroDoJogador) {
        int quantidadeDeNumeros = (tamanhoY > 10) ? 2 : 1;
        String expressaoDeVerificacao = "^[A-Za-z] {1} [0-9]{" + quantidadeDeNumeros + "}$";

        return tiroDoJogador.matches(expressaoDeVerificacao);

    }

    public static int[] retornarPosicoesDigitadasPeloJogador(String tiroDoJogador) {
        String tiro = tiroDoJogador.toLowerCase();
        int[] retorno = new int[2];
        retorno[POSICAO_X] = tiro.charAt(0) - 97;
        retorno[POSICAO_Y] = Integer.parseInt(tiro.substring(1)) - 1;
        return retorno;
    }


    public static void inserirValoresDaAcaoNoTabuleiro(int[] posicoes, int numeroDoJogador) {

        if (numeroDoJogador == 1) {

            if (tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] == NAVIO) {
                tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ACERTOU_TIRO;
                naviosJogador2--;
                System.out.println("Você acertou o navio!");
            } else {
                tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ERROU_TIRO;
                System.out.println("Você errou o tiro!");
            }
        } else {
            if (tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] == NAVIO) {
                tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ACERTOU_TIRO;
                naviosJogador1--;
                System.out.println("Você acertou o navio!");
            } else {
                tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ERROU_TIRO;
                System.out.println("Você errou o tiro!");
            }
        }
    }


    public static boolean acaoDoJogador() {
        boolean acaoValida = true;
        String tiroDoJogador = receberValorDigitadoPeloJogador();

        if (validarTiroJogador(tiroDoJogador)) {
            int[] posicoes = retornarPosicoesDigitadasPeloJogador(tiroDoJogador);

            if (validarPosicoesInseridasPeloJogador(posicoes)) {
                inserirValoresDaAcaoNoTabuleiro(posicoes, 1);
            } else {
                acaoValida = false;
            }
        } else {
            System.out.println("posicao invalida");
            acaoValida = false;
        }
        return acaoValida;
    }


    public static void acaoDoComputador() {

        int[] posicoes = retornarJogadaDoComputador();
        inserirValoresDaAcaoNoTabuleiro(posicoes, 2);
    }

    public static int[] retornarJogadaDoComputador() {

        int[] posicoes = new int[2];
        posicoes[POSICAO_X] = retornarJogadaAleatoriaDoComputador(tamanhoX);
        posicoes[POSICAO_Y] = retornarJogadaAleatoriaDoComputador(tamanhoY);
        return posicoes;
    }

    public static int retornarJogadaAleatoriaDoComputador(int limite) {
        Random jogadaDoComputador = new Random();
        int numeroGerado = jogadaDoComputador.nextInt(limite);

        return (numeroGerado == limite) ? --numeroGerado : numeroGerado;

    }

    public static void main(String[] args) {

        obterNomesDosJogadores();
        obterTamanhoDosTabuleiros();
        calcularQtdMaxNavios();
        iniciarTamanhos();
        obterQtdNavios();
        instanciarContadoresDeNaviosDosJogadores();
        inserirOsNaviosNoTabuleiroDosJogadores();

        boolean jogoAtivo = true;
        do {
            exibirTabuleirosDosJogadores();
            if (acaoDoJogador()) {
                if (naviosJogador2 <= 0) {
                    System.out.println(nomeJogador1 + " venceu o jogo!!!!");
                    break;
                }
                //verifica fim do jogo
                acaoDoComputador();
                if (naviosJogador1 <= 0) {
                    System.out.println(nomeJogador2 + " venceu o jogo!!!!");
                    break;
                }
            }

        } while (jogoAtivo);
        exibirTabuleirosDosJogadores();
        input.close();
    }

}
