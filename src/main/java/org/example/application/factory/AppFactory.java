package org.example.application.factory;

import org.example.application.service.MaquinaService;
import org.example.application.service.OrdemManutencaoService;
import org.example.application.service.PecaService;
import org.example.application.service.TecnicoService;
import org.example.domain.repository.*;
import org.example.view.MenuPrincipal;

import java.util.Scanner;

public class AppFactory {

    private static final Scanner sc = new Scanner(System.in);

    // ======= REPOSITORIOS ========
    private static final MaquinaRepository maquinaRepository = new MaquinaRepositoryImpl();
    private static final TecnicoRepository tecnicoRepository = new TecnicoRepositoryImpl();
    private static final PecaRepository pecaRepository = new PecaRepositoryImpl();
    private static final OrdemManutencaoRepository ordemManutencaoRepository = new OrdemManutencaoRepositoryImpl();

    // ======= SERVICES ========
    private static final MaquinaService maquinaService = new MaquinaService(maquinaRepository);
    private static final TecnicoService tecnicoService = new TecnicoService(tecnicoRepository);
    private static final PecaService pecaService = new PecaService(pecaRepository);
    private static final OrdemManutencaoService ordemManutencaoService = new OrdemManutencaoService(ordemManutencaoRepository);
    // ======= GETTERS =========


    public static MaquinaService getMaquinaService() {
        return maquinaService;
    }

    public static TecnicoService getTecnicoService() {
        return tecnicoService;
    }

    public static PecaService getPecaService() {
        return pecaService;
    }

    public static OrdemManutencaoService getOrdemManutencaoService() { return ordemManutencaoService; }

}
