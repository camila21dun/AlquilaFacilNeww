package alquilafacil.controller;

import alquilafacil.model.AlquilaFacil;

public class ModelFactoryController {

    private AlquilaFacil alquilaFacil;


    private static class SingletonHolder {
        private final static ModelFactoryController eInstance = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eInstance;
    }
}
