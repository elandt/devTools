package com.elandt.lil.spring_demo.adapter;

import java.util.logging.Logger;

public class MoroOrange implements Orange {

    Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public String getVariety() {
        return "Moro Blood Orange";
    }

    @Override
    public void eat() {
        logger.info("Moro gets enjoyed.");
    }

    @Override
    public void peel() {
        logger.info("Moro gets peeled.");
    }

    @Override
    public void juice() {
        logger.info("Moro gets juiced.");
    }
}
