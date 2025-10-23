package org.example.view;

import org.example.application.service.MaquinaService;
import org.example.application.service.OrdemManutencaoService;
import org.example.application.service.PecaService;
import org.example.application.service.TecnicoService;
import org.example.domain.model.Maquina;
import org.example.domain.model.OrdemManutencao;
import org.example.domain.model.PecasReposicao;
import org.example.domain.model.Tecnico;
import org.example.domain.model.enums.StatusOrdem;
import org.example.shared.ConsoleUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            case 4:
                executarManutencao();
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

        List<PecasReposicao> pecaList = new ArrayList<>();
        int idPeca;

        List<Integer> idPecaList = reposicaoList.stream().map(PecasReposicao::getIdPeca).toList();

        System.out.println("\n----- Seleção de Peças -----");
        System.out.println("Digite o Identificador das Peças a Serem Vinculadas (Ou 0 Para Finalizar):");

        while (true) {
            System.out.print("ID: ");

            if (sc.hasNextInt()) {
                idPeca = sc.nextInt();
                sc.nextLine();

                if (idPeca == 0) {
                    break;
                } else if (idPecaList.contains(idPeca)) {

                    int finalIdPeca = idPeca;
                    boolean jaSelecionada = pecaList.stream()
                            .anyMatch(p -> p.getIdPeca() == finalIdPeca);

                    if (jaSelecionada) {
                        System.out.println("ERRO: A Peça ID " + idPeca + " já foi adicionada a esta Ordem. Digite outra peça ou 0 para finalizar.");
                        continue;
                    }

                    Double quantidadePeca = ConsoleUtil.lerDouble("Digite a Quantidade de Peça a Ser Associada: ");

                    if (quantidadePeca > reposicaoList.stream()
                            .filter(pecasReposicao -> pecasReposicao.getIdPeca() == finalIdPeca)
                            .map(PecasReposicao::getEstoque)
                            .findFirst()
                            .orElse(0.0)) {

                        System.out.println("A Quantidade não pode ser Maior que o Estoque!");
                    } else {
                        PecasReposicao pecasReposicao = new PecasReposicao(idPeca, quantidadePeca);

                        pecaList.add(pecasReposicao);

                        System.out.println("ID " + idPeca + " adicionado. Digite o próximo ou 0 para finalizar.");
                    }
                } else {
                    System.out.println("ID inválido. Digite um Identificador que Exista ou 0 para finalizar.");
                }
            } else {
                System.out.println("Entrada inválida. Digite apenas números.");
                sc.nextLine();
            }
        }

        if (!pecaList.isEmpty()) {

            List<Integer> pecaId = pecaList.stream().map(PecasReposicao::getIdPeca).toList();

            System.out.println("\nResumo: Vinculando a Ordem de ID " + opcao +
                    " as Peças IDs: "+ pecaId);

            ordemManutencaoService.cadastrarOrdemItem(opcao,pecaList);
        } else {
            System.out.println("Nenhuma Peça Selecionada. Operação Cancelada.");
        }
    }

    private void executarManutencao() throws SQLException {
        List<OrdemManutencao> ordemManutencoes = ordemManutencaoService.listarOrdemManutencaoPendente();

        if (ordemManutencoes.isEmpty()) {
            System.out.println("Nenhuma Ordem de Manutenção PENDENTE para ser executada.");
            return;
        }

        System.out.println("\n----- Ordens de Manutenção PENDENTES -----");
        ordemManutencoes.forEach(ordemManutencao -> {
            System.out.printf("ID: %-5d | Id Máquina: %-5d | Id Técnico: %-5d | Data da Solicitação: %-15s | Status: %-15s%n",
                    ordemManutencao.getIdOrdem(),
                    ordemManutencao.getIdMaquina(),
                    ordemManutencao.getIdTecnico(),
                    ordemManutencao.getDataSolicitacao().toString().replace("-", "/"),
                    ordemManutencao.getStatusOrdem().toString());
        });

        int idOrdem = ConsoleUtil.lerOpcao("\nDigite o Identificador da Ordem de Manutenção a ser Executada: ");

        OrdemManutencao ordemParaExecutar = ordemManutencaoService.buscarOrdemComItens(idOrdem);

        if (ordemParaExecutar == null || ordemParaExecutar.getStatusOrdem() != StatusOrdem.PENDENTE) {
            System.out.println("Ordem de Manutenção não encontrada, ou não está no status PENDENTE.");
            return;
        }

        if (ordemParaExecutar.getPecasUtilizadas() == null || ordemParaExecutar.getPecasUtilizadas().isEmpty()) {
            System.out.println("A Ordem de Manutenção ID " + idOrdem + " não possui peças associadas.");
            System.out.println("Não é possível executar sem peças.");
            return;
        }

        System.out.println("\n----- Peças Necessárias para a Ordem " + idOrdem + " -----");
        ordemParaExecutar.getPecasUtilizadas().forEach(peca -> {
            System.out.printf("ID Peça: %-5d | Quantidade Necessária: %.2f%n",
                    peca.getIdPeca(),
                    peca.getEstoque());
        });

        List<PecasReposicao> pecasNecessarias = ordemParaExecutar.getPecasUtilizadas();
        List<PecasReposicao> pecasEmEstoque = pecaService.listarPeca();

        Map<Integer, Double> estoqueMap = pecasEmEstoque.stream()
                .collect(Collectors.toMap(PecasReposicao::getIdPeca, PecasReposicao::getEstoque));

        boolean estoqueSuficiente = true;
        for (PecasReposicao pecaNecessaria : pecasNecessarias) {
            double estoqueAtual = estoqueMap.getOrDefault(pecaNecessaria.getIdPeca(), 0.0);
            if (pecaNecessaria.getEstoque() > estoqueAtual) {
                System.out.printf("5. Estoque Insuficiente! Peça ID %d (Nome: %s). Necessário: %.2f, Disponível: %.2f%n",
                        pecaNecessaria.getIdPeca(),
                        pecaNecessaria.getNome(),
                        pecaNecessaria.getEstoque(),
                        estoqueAtual);
                estoqueSuficiente = false;
                break;
            }
        }

        if (!estoqueSuficiente) {
            System.out.println("Execução da Manutenção Cancelada devido a falta de estoque de peças.");
            return;
        }

        try {
            ordemManutencaoService.executarManutencao(
                    idOrdem,
                    ordemParaExecutar.getIdMaquina(),
                    pecasNecessarias
            );

            System.out.println("\nManutenção da Ordem ID " + idOrdem + " executada com sucesso!");
            System.out.println("Status da Ordem atualizado para EXECUTADA.");
            System.out.println("Status da Máquina ID " + ordemParaExecutar.getIdMaquina() + " atualizado para OPERACIONAL.");

        } catch (SQLException e) {
            System.out.println("Erro ao executar manutenção: " + e.getMessage());
        }
    }
}
