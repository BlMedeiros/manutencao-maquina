package org.example.view;

import org.example.application.service.MaquinaService;
import org.example.application.service.OrdemManutencaoService;
import org.example.application.service.PecaService;
import org.example.application.service.TecnicoService;
import org.example.domain.model.Maquina;
import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.PecasReposicao;
import org.example.domain.model.Tecnico;
import org.example.shared.ConsoleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner sc;
    private final MaquinaService maquinaService;
    private final TecnicoService tecnicoService;
    private final OrdemManutencaoService ordemManutencaoService;
    private final PecaService pecaService;

    public MenuPrincipal(Scanner sc, MaquinaService maquinaService, TecnicoService tecnicoService, OrdemManutencaoService ordemManutencaoService, PecaService pecaService) {
        this.sc = sc;
        this.maquinaService = maquinaService;
        this.tecnicoService = tecnicoService;
        this.ordemManutencaoService = ordemManutencaoService;
        this.pecaService = pecaService;
    }


    public boolean exibirMenu() throws SQLException {

        System.out.println("\n-----Menu Principal-----");
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
                String resultadoOrdem = cadastrarOrdemManutencao();
                System.out.println(resultadoOrdem);
                break;
            case 3:
                associarPecaOrdem();
            break;
            case 0:
                System.out.println("Encerrando o Sistema....");
                return false;
            default:
                System.out.println("Opção Inválida! Tente Novamente.");
        }

        return true;
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


        List<Tecnico> tecnicos = tecnicoService.listarTodosTecnicos();

        if (tecnicos.isEmpty()) {
            return "Nenhum Técnico For Encontrado!";
        }
        tecnicos.forEach(tecnico -> {
            System.out.printf("ID: %-5d | Nome: %-20s | Especialidade: %-15s%n",
                    tecnico.getIdTecnico(),
                    tecnico.getNome(),
                    tecnico.getEspecialidade());
        });
        int idTecnico = ConsoleUtil.lerOpcao("\nDigite o Identificador do Técnico: ");

        OrdemManutencao ordemManutencao = new OrdemManutencao(idMaquina, idTecnico);

        return ordemManutencaoService.cadastrarOrdemManutencao(ordemManutencao);
    }

    private void associarPecaOrdem() throws SQLException {
        List<OrdemManutencao> ordemManutencoes = ordemManutencaoService.listarOrdemManutencaoPendente();

        ordemManutencoes.forEach(ordemManutencao -> {
            System.out.printf("ID: %-5d | Id Máquina: %-5d | Id Técnico: %-5d | Data da Solicitação: %-15s | Status: %-15s%n",
                    ordemManutencao.getIdOrdem(),
                    ordemManutencao.getIdMaquina(),
                    ordemManutencao.getIdTecnico(),
                    ordemManutencao.getDataSolicitacao().toString().replace("-","/"),
                    ordemManutencao.getStatusOrdem().toString());
        });

        int opcao = ConsoleUtil.lerOpcao("\nDigite o Identificador da Ordem de Manutenção: ");

        List<PecasReposicao> reposicaoList = pecaService.listarPeca();

        reposicaoList.forEach(pecasReposicao -> {
            System.out.printf("ID: %-5d | Nome da Peça: %-15s | Estoque: %.2f%n",
                    pecasReposicao.getIdPeca(),
                    pecasReposicao.getNome(),
                    pecasReposicao.getEstoque());
        });

        List<Integer> idPecaList = new ArrayList<>();
        int idPeca;

        System.out.println("\n----- Seleção de Peças -----");
        System.out.println("Digite o Identtificador das Peças a Serem Vinculadas (Ou 0 Para Finalizar):");

        while (true) {
            System.out.print("ID: ");

            if (sc.hasNextInt()) {
                idPeca = sc.nextInt();
                sc.nextLine();

                if (idPeca == 0) {
                    break;
                } else if (idPeca > 0) {
                    idPecaList.add(idPeca);
                    System.out.println("ID " + idPeca + " adicionado. Digite o próximo ou 0 para finalizar.");
                } else {
                    System.out.println("ID inválido. Digite um número positivo ou 0 para finalizar.");
                }
            } else {
                System.out.println("Entrada inválida. Digite apenas números.");
                sc.nextLine();
            }
        }

        if (!idPecaList.isEmpty()) {

            System.out.println("\nResumo: Vinculando a Ordem de ID " + opcao +
                    " as Peças IDs: " + idPecaList);
        } else {
            System.out.println("Nenhuma Peça Selecionada. Operação Cancelada.");
        }
    }
}
