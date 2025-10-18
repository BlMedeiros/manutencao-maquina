package org.example;

import org.example.application.factory.AppFactory;
import org.example.application.service.MaquinaService;
import org.example.application.service.OrdemManutencaoService;
import org.example.application.service.TecnicoService;
import org.example.view.MenuPrincipal;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        MaquinaService maquinaService = AppFactory.getMaquinaService();
        TecnicoService tecnicoService = AppFactory.getTecnicoService();
        OrdemManutencaoService ordemManutencaoService = AppFactory.getOrdemManutencaoService();

        MenuPrincipal menuPrincipal = new MenuPrincipal(sc,maquinaService,tecnicoService,ordemManutencaoService);

        while (menuPrincipal.exibirMenu()) {

        }
    }
}