package org.example.view;

import org.example.application.factory.AppFactory;
import org.example.application.service.MaquinaService;
import org.example.application.service.PecaService;
import org.example.application.service.TecnicoService;
import org.example.domain.model.Maquina;
import org.example.domain.model.PecasReposicao;
import org.example.domain.model.Tecnico;
import org.example.shared.ConsoleUtil;

import java.util.Scanner;

public class MenuCadastro {

    private final MaquinaService maquinaService = AppFactory.getMaquinaService();
    private final TecnicoService tecnicoService = AppFactory.getTecnicoService();
    private final PecaService pecaService = AppFactory.getPecaService();

    private final Scanner sc = new Scanner(System.in);



    public void exibirMenu() {

        System.out.println("-----Menu de Cadastro-----");
        System.out.println("[1] Cadastrar Máquina");
        System.out.println("[2] Cadastrar Técnico");
        System.out.println("[3] Cadastrar Peça");
        System.out.println("[0] Voltar ao Menu Principal");

        int opcao = ConsoleUtil.lerOpcao("Digite uma Opção: ");

        switch (opcao) {
            case 1:
                cadastrarMaquina();
                break;
            case 2:
                cadastrarTecnico();
                break;
            case 3:
                System.out.println();
                break;
            case 0:
                return;
            default:
                System.out.println("Opção Inválida! Tente Novamente.");
        }
    }

    private String cadastrarMaquina() {

        String nomeMaquina = ConsoleUtil.lerTexto("Digite o Nome da Maquina: ");
        String nomeSetor = ConsoleUtil.lerTexto("Digite o Setor da Maquina: ");

        Maquina maquina = new Maquina(nomeMaquina,nomeSetor);

        return maquinaService.cadastrarMaquina(maquina);
    }

    private String cadastrarTecnico() {

        String nomeTecnico = ConsoleUtil.lerTexto("Digite o Nome do Tecnico(a)");

        String especialidadeTecnico = ConsoleUtil.lerTexto("Digite a Especialidade do Tecnico(a)");

        Tecnico tecnico = new Tecnico(nomeTecnico,especialidadeTecnico);

        return tecnicoService.cadastrarTecnico(tecnico);
    }

    private String cadastrarPeca() {

        String nomePeca = ConsoleUtil.lerTexto("Digite o Nome da Peça: ");

        Double quantidadePeca = ConsoleUtil.lerDouble("Digite a Quantidade em Estoque Inicial da Peça: ");

        PecasReposicao pecasReposicao = new PecasReposicao(nomePeca,quantidadePeca);

        return pecaService.cadastrarPeca(pecasReposicao);
    }

}
