package br.com.zup.estrelas.jogodaforca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.Normalizer;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {

	public static final int CONTROLHE_LETRA_REPETIDA = -1;
	public static final String QUEBRA_LINHA = "\n";

	public static int qtdPalavrasArquivo() throws IOException {

		FileReader estrutura = new FileReader("palavras.txt");
		LineNumberReader leitor = new LineNumberReader(estrutura);

		leitor.skip(Long.MAX_VALUE);

		int totalPalavras = leitor.getLineNumber() + 1;

		leitor.close();

		return totalPalavras;

	}

	public static String[] palavrasArquivo(int qtdDePalavrasNoArquivo) throws IOException {
		String[] palavrasArquivo = new String[qtdDePalavrasNoArquivo];

		FileReader estrutura = new FileReader("palavras.txt");
		BufferedReader leitor = new BufferedReader(estrutura);

		String linha;
		int nLinha = 0;
		while ((linha = leitor.readLine()) != null) {
			palavrasArquivo[nLinha] = linha;
			nLinha++;
		}

		leitor.close();

		return palavrasArquivo;

	}

	public static String sorteiaPalavra() throws IOException {

		Random sorteia = new Random();

		int qtdDePalavrasNoArquivo = qtdPalavrasArquivo();

		String palavraSorteada;
		palavraSorteada = palavrasArquivo(qtdDePalavrasNoArquivo)[sorteia.nextInt(qtdDePalavrasNoArquivo)]
				.toUpperCase();

		return palavraSorteada;

	}

	public static String premioGanhador(Scanner teclado) throws IOException {
		FileWriter writer = new FileWriter("palavras.txt", true);

		System.out.println("Como prêmio, digite uma palavra para ser adicionada na lista");
		String palavraLida = teclado.nextLine().toUpperCase();
		writer.append(QUEBRA_LINHA);
		writer.append(String.format("%s", palavraLida));

		writer.close();

		return "palavra adicionada com sucesso =)";
	}

	public static String removerAcentos(String palavraSorteada) throws IOException {

		String PalavraSorteadaSemAcentos = Normalizer.normalize(palavraSorteada, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");

		return PalavraSorteadaSemAcentos;

	}

	public static void mascaraPalavra(String palavraSorteada, char[] letrasPalavraSorteada) {

		for (int i = 0; i < letrasPalavraSorteada.length; i++) {
			letrasPalavraSorteada[i] = 0;
			if (palavraSorteada.charAt(i) == ' ') {
				letrasPalavraSorteada[i] = 1;
			}
		}
	}

	public static void palavraExposta(String palavraSorteada, char[] letrasPalavraSorteada) {
		for (int i = 0; i < palavraSorteada.length(); i++) {
			if (letrasPalavraSorteada[i] == 0) {
				System.out.print("_ ");
			} else {
				System.out.print(palavraSorteada.charAt(i) + " ");
			}
		}
	}

	public static boolean palavraDescoberta(String palavraSorteada, char[] letrasPalavraSorteada) {
		for (int i = 0; i < palavraSorteada.length(); i++) {
			if (letrasPalavraSorteada[i] == 0) {
				return false;
			}
		}
		return true;
	}

	public static void headJogoDaForca() {
		System.out.println("--------------------------------------------------------");
		System.out.println("|                   JOGO  DA  FORCA                    |");
		System.out.println("--------------------------------------------------------");
	}

	public static void numeroDeTentativas(int chances) {
		System.out.printf("\nVocê tem %d tentativas \ndigite uma letra:", chances);

	}

	public static boolean verificaPerdeChance(String palavraSorteada, String palavraSorteadaSemAcentos,
			char[] letrasPalavraSorteada, char letraLida) {
		boolean verificaPerdeChance = true;
		for (int i = 0; i < palavraSorteada.length(); i++) {
			if (letraLida == palavraSorteadaSemAcentos.charAt(i)) {
				letrasPalavraSorteada[i] = 1;
				verificaPerdeChance = false;
			}
		}
		return verificaPerdeChance;
	}

	public static void acertouPalavra(String palavraSorteada, Scanner teclado) throws IOException {
		System.out.printf("\n%s\n", palavraSorteada);
		System.out.println("Parabéns, você ganhou!!!");
		System.out.println(premioGanhador(teclado));
	}

	public static void letraRepetida(String letrasDigitadas) {
		System.out.printf("Letras digitadas:%s\n", letrasDigitadas);
		System.out.println("Você já digitou essa letra, tente outra vez!");
	}

	public static String letraNaoRepetida(String letrasDigitadas, char letraLida) {
		System.out.printf("Letras digitadas:%s\n", letrasDigitadas);
		return letrasDigitadas += " " + letraLida;
	}

	public static int diminuiChances(int chances) {
		System.out.println("Ops, você errou!");
		return chances - 1;
	}

	public static void errouPalavra(String palavraSorteada) {
		System.out.printf("\nA palavra era %s\n", palavraSorteada);
		System.out.println("Não foi dessa vez, quem sabe na próxima... :(");
	}

	public static void main(String[] args) throws IOException {

		Scanner teclado = new Scanner(System.in);

		String palavraSorteada = sorteiaPalavra();
		String palavraSorteadaSemAcentos = removerAcentos(palavraSorteada);
		char letraLida;
		int chances = 6;
		String letrasDigitadas = "";
		int verificaLetraRepetida;

		char[] letrasPalavraSorteada = new char[palavraSorteadaSemAcentos.length()];

		mascaraPalavra(palavraSorteadaSemAcentos, letrasPalavraSorteada);

		headJogoDaForca();

		do {
			do {

				System.out.println(QUEBRA_LINHA);

				palavraExposta(palavraSorteadaSemAcentos, letrasPalavraSorteada);
				numeroDeTentativas(chances);

				letraLida = teclado.next().toUpperCase().charAt(0);
				teclado.nextLine();
				verificaLetraRepetida = letrasDigitadas.indexOf(letraLida);

				if (verificaLetraRepetida != CONTROLHE_LETRA_REPETIDA) {
					letraRepetida(letrasDigitadas);
				} else {
					letrasDigitadas = letraNaoRepetida(letrasDigitadas, letraLida);
				}

			} while (verificaLetraRepetida != CONTROLHE_LETRA_REPETIDA);

			if (verificaPerdeChance(palavraSorteada, palavraSorteadaSemAcentos, letrasPalavraSorteada,
					letraLida)) {
				chances = diminuiChances(chances);
			}

		} while (!palavraDescoberta(palavraSorteadaSemAcentos, letrasPalavraSorteada) && chances > 0);

		if (chances > 0) {
			acertouPalavra(palavraSorteadaSemAcentos, teclado);
		} else {
			errouPalavra(palavraSorteadaSemAcentos);
		}
		teclado.close();
	}
}