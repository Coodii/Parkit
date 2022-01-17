package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class App {
    /**
     * Initialise a logger named LOGGER.
     */
    private static final Logger LOGGER = LogManager.getLogger("App");
    /**
     * This is the main method.
     * @param arg
     */
    public static void main(String[] arg) {
        LOGGER.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
