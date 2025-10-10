package org.example.view;

import org.example.shared.ConsoleUtil;

import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner sc;

    public MenuPrincipal(Scanner sc) {
        this.sc = sc;
    }

    public void exibirMenu() {

        System.out.println("-----Menu Principal-----");
        System.out.println("[1] Menu de Cadastro");
        System.out.println("[2] Criar Ordem de Manutenção");
        System.out.println("[3] Associar Peças a Ordem");
        System.out.println("[4] Executar Manutenção");
        System.out.println("[0] Sair do Sistema");

        int opcao = ConsoleUtil.lerOpcao("Digite uma Opção: ");

        switch (opcao) {
            case 1:
                new MenuCadastro().exibirMenu();
                break;

        }
    }
}
