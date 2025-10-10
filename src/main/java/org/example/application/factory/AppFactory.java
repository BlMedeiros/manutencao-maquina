package org.example.application.factory;

import org.example.application.service.MaquinaService;
import org.example.application.service.PecaService;
import org.example.application.service.TecnicoService;
import org.example.domain.repository.*;

public class AppFactory {

    // ======= REPOSITORIOS ========
    private static final MaquinaRepository maquinaRepository = new MaquinaRepositoryImpl();
    private static final TecnicoRepository tecnicoRepository = new TecnicoRepositoryImpl();
    private static final PecaRepository pecaRepository = new PecaRepositoryImpl();


    // ======= SERVICES ========
    private static final MaquinaService maquinaService = new MaquinaService(maquinaRepository);
    private static final TecnicoService tecnicoService = new TecnicoService(tecnicoRepository);
    private static final PecaService pecaService = new PecaService(pecaRepository);
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
}
