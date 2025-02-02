package file_manage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InteracaoUsuario {
    static void InserirRespostasAlunos(Scanner input){
        String path = "demo/src/main/resources/arquivos_provas/respostas_alunos/";
        System.out.println("Digite a Disciplina:");
        path = path + input.nextLine();

        escreverArquivo(path, input);
    }

    static void GerarResultadoDisciplina(Scanner input){
        System.out.println("Digite a Disciplina:");
        String disciplina = input.nextLine();

        LendoArquivos(disciplina);
    }

    private static boolean LendoArquivos(String disciplina){
        String pathGabarito = "demo/src/main/resources/arquivos_provas/gabarito_provas/" + disciplina + ".txt";
        String pathRespostas = "demo/src/main/resources/arquivos_provas/respostas_alunos/" + disciplina + ".txt";
        
        try {
            FileReader fr = new FileReader(pathGabarito);
            BufferedReader br = new BufferedReader(fr);
            char[] itens = br.readLine().toCharArray();
            
            fr = new FileReader(pathRespostas);
            br = new BufferedReader(fr);

            System.out.println("Gabarito:");
            System.out.println(itens);

            String resposta = br.readLine();
            Map<String, Integer> ScoreAlunos = new TreeMap<>();
            while(resposta != null){
                int count = 0;
                String[] dados = resposta.split("\t");
                
                if(!dados[0].equalsIgnoreCase("VVVVVVVVVV") && !dados[0].equalsIgnoreCase("FFFFFFFFFF")){
                    for(int i = 0; i < itens.length; i++){
                        if(dados[0].toCharArray()[i] == itens[i]){
                            count++;
                        }
                    }
                }

                ScoreAlunos.put(dados[1], count);
                resposta = br.readLine();
            }

            EscrevendoResultado(disciplina, ScoreAlunos);
            br.close();
            fr.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        

        return true;
    }

    private static boolean EscrevendoResultado(String disciplina, Map<String, Integer> ScoreAlunos){
        String pathResultadoAlfabetico = "demo/src/main/resources/arquivos_provas/resultado/" + disciplina + " Alfabético.txt";
        String pathResultadoDecrescente = "demo/src/main/resources/arquivos_provas/resultado/" + disciplina + " Decrescente.txt";
        try {
            FileWriter fw = new FileWriter(pathResultadoAlfabetico, true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            System.out.println();
            System.out.println("Ordem Alfabética de Notas");

            int media = 0;
            for (Map.Entry<String, Integer> entry : ScoreAlunos.entrySet()) {
                String chave = entry.getKey();
                Integer valor = entry.getValue();

                media += valor;

                System.out.println(chave + "\t" + valor);
                
                bw.write(chave + "\t" + valor);
                bw.newLine();
            }
            
            System.out.println();
            media = media/ScoreAlunos.size();
            System.out.println("Media Final da Turma: " + media);
            System.out.println();

            bw.write("Media:\t" + media);
            
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            FileWriter fw = new FileWriter(pathResultadoDecrescente, true);
            BufferedWriter bw = new BufferedWriter(fw);

            Map<String, Integer> ScoreDecrescente = ScoreAlunos.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
                
                System.out.println();
                System.out.println("Ordem descrescente de Notas:");

                int media = 0;
            for (Map.Entry<String, Integer> entry : ScoreDecrescente.entrySet()) {
                String chave = entry.getKey();
                Integer valor = entry.getValue();

                media += valor;

                System.out.println(chave + "\t" + valor);
                
                bw.write(chave + "\t" + valor);
                bw.newLine();
            }
            
            System.out.println();
            media = media/ScoreDecrescente.size();
            System.out.println("Média Final da Turma: " + media);
            System.out.println();

            bw.write("Media:\t" + media);

            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static boolean escreverArquivo(String caminhoArquivo, Scanner input) {
        try (FileWriter fw = new FileWriter(caminhoArquivo + ".txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            while(true){
                System.out.println("Digite \"fim\" para acabar o processo");
                System.out.println("Digite as respostas: ");
                String resposta = input.nextLine();
                if(resposta.equalsIgnoreCase("fim")){
                    break;
                }
                
                System.out.println("Digite o nome do Aluno: ");
                String nomeAluno = input.nextLine();
                bw.write(resposta + "\t" + nomeAluno);
                bw.newLine();
            }

            bw.close();
            fw.close();

            return true;
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            return false;
        }
    }
}
