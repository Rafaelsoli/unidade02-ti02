package ti2cc;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DAO relogioDAO = new DAO();
        relogioDAO.conectar();
        

        Scanner scanner = new Scanner(System.in);

        int escolha;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Listar Relógios");
            System.out.println("2 - Inserir Relógio");
            System.out.println("3 - Excluir Relógio");
            System.out.println("4 - Atualizar Relógio");
            System.out.println("5 - Sair");
            System.out.print("Opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    listarRelogios(relogioDAO);
                    break;
                case 2:
                    inserirRelogio(relogioDAO, scanner);
                    break;
                case 3:
                    excluirRelogio(relogioDAO, scanner);
                    break;
                case 4:
                    atualizarRelogio(relogioDAO, scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (escolha != 5);

        scanner.close();
    }

    private static void listarRelogios(DAO relogioDAO) {
        System.out.println("\n==== Listar Relógios ====");
        Relogio[] relogios = relogioDAO.getRelogios();
        if (relogios != null) {
            for (Relogio relogio : relogios) {
                if (relogio != null) {
                    System.out.println(relogio.toString());
                }
            }
        } else {
            System.out.println("Nenhum relógio encontrado.");
        }
    }

    private static void inserirRelogio(DAO relogioDAO, Scanner scanner) {
        System.out.println("\n==== Inserir Relógio ====");
        System.out.print("Digite o código do relógio: ");
        int codigo = scanner.nextInt();
        System.out.print("Digite a marca do relógio: ");
        String marca = scanner.next();
        System.out.print("Digite o modelo do relógio: ");
        String modelo = scanner.next();
        System.out.print("Digite o ano de fabricação do relógio: ");
        int anoFabricacao = scanner.nextInt();

        Relogio relogio = new Relogio(codigo, modelo, marca, anoFabricacao);
        if (relogioDAO.inserirRelogio(relogio)) {
            System.out.println("Inserção com sucesso -> " + relogio.toString());
        } else {
            System.out.println("Erro ao inserir o relógio.");
        }
    }

    private static void excluirRelogio(DAO relogioDAO, Scanner scanner) {
        System.out.println("\n==== Excluir Relógio ====");
        System.out.print("Digite o código do relógio a ser excluído: ");
        int codigo = scanner.nextInt();

        if (relogioDAO.excluirRelogio(codigo)) {
            System.out.println("Exclusão realizada com sucesso.");
        } else {
            System.out.println("Erro ao excluir o relógio. Verifique o código informado.");
        }
    }

    private static void atualizarRelogio(DAO relogioDAO, Scanner scanner) {
        System.out.println("\n==== Atualizar Relógio ====");
        System.out.print("Digite o código do relógio a ser atualizado: ");
        int codigo = scanner.nextInt();
        Relogio relogioAtualizado = relogioDAO.getRelogioPorCodigo(codigo);

        if (relogioAtualizado != null) {
            System.out.print("Digite a nova marca do relógio (atual: " + relogioAtualizado.getMarca() + "): ");
            String novaMarca = scanner.next();
            System.out.print("Digite o novo modelo do relógio (atual: " + relogioAtualizado.getModelo() + "): ");
            String novoModelo = scanner.next();
            System.out.print("Digite o novo ano de fabricação do relógio (atual: " + relogioAtualizado.getAnoFabricacao() + "): ");
            int novoAnoFabricacao = scanner.nextInt();

            relogioAtualizado.setMarca(novaMarca);
            relogioAtualizado.setModelo(novoModelo);
            relogioAtualizado.setAnoFabricacao(novoAnoFabricacao);

            if (relogioDAO.atualizarRelogio(relogioAtualizado)) {
                System.out.println("Atualização realizada com sucesso -> " + relogioAtualizado.toString());
            } else {
                System.out.println("Erro ao atualizar o relógio.");
            }
        } else {
            System.out.println("Relógio não encontrado. Verifique o código informado.");
        }
    }
}
