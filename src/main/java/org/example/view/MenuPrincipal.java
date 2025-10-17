package org.example.view;

import org.example.application.service.MaquinaService;
import org.example.application.service.OrdemManutencaoService;
import org.example.application.service.TecnicoService;
import org.example.domain.model.Maquina;
import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.Tecnico;
import org.example.shared.ConsoleUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner sc;
    private final MaquinaService maquinaService;
    private final TecnicoService tecnicoService;
    private final OrdemManutencaoService ordemManutencaoService;

    public MenuPrincipal(Scanner sc, MaquinaService maquinaService, TecnicoService tecnicoService, OrdemManutencaoService ordemManutencaoService) {
        this.sc = sc;
        this.maquinaService = maquinaService;
        this.tecnicoService = tecnicoService;
        this.ordemManutencaoService = ordemManutencaoService;
    }

    public void exibirMenu() throws SQLException {

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
            case 2:
                cadastrarOrdemManutencao();
                break;

        }
    }

    private String cadastrarOrdemManutencao() throws SQLException {

        List<Maquina> maquinas = maquinaService.listarMaquinaOperacional();

        if (maquinas.isEmpty()) {
            System.out.println("Nenhuma Maquina Encontrada!");
        }
        maquinas.forEach(maquina -> {
            System.out.printf("ID: %-5d | Nome: %-20s | Setor: %-15s | Status: %s%n",
                    maquina.getIdMaquina(),
                    maquina.getNome(),
                    maquina.getSetor(),
                    maquina.getMaquinaStatus());
        });
        int idMaquina = ConsoleUtil.lerOpcao("Digite o Identificador da Máquina: ");
        //inserir metodo para verificar o id

        List<Tecnico> tecnicos = tecnicoService.listarTodosTecnicos();

        if (tecnicos.isEmpty()) {
            return "Nenhum Técnico For Encontrado!";
        }
        tecnicos.forEach(tecnico -> {
            System.out.printf("ID: %-5d | Nome: %-20s | Especialidade: %-15s",
                    tecnico.getIdTecnico(),
                    tecnico.getNome(),
                    tecnico.getEspecialidade());
        });
        int idTecnico = ConsoleUtil.lerOpcao("\nDigite o Identificador do Técnico: ");

        OrdemManutencao ordemManutencao = new OrdemManutencao(idMaquina, idTecnico);

        return ordemManutencaoService.cadastrarOrdemManutencao(ordemManutencao);
    }
}
