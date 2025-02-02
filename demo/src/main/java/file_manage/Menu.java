package file_manage;

import java.util.Scanner;

import static file_manage.InteracaoUsuario.GerarResultadoDisciplina;
import static file_manage.InteracaoUsuario.InserirRespostasAlunos;

public class Menu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Capturing the input
        String opcoes = null;
        
        do {
            System.out.println("1) Inserir Respostas dos Alunos para Disciplina");
            System.out.println("2) Gerar Resulstado de Disciplina");
            System.out.println("0) Sair");
            
            opcoes = input.nextLine();
            switch (opcoes) {
                case "1":
                    InserirRespostasAlunos(input);
                    break;
                case "2":
                    GerarResultadoDisciplina(input);
                    break;
                case "3":
                    
                    break;
                case "M":
                    break;
                    // Add the rest of your cases
            }
        } while (!opcoes.equals("0")); // quitting the program
    }
}
