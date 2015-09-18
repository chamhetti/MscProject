package com.directfn.exchange_simulator.common_util.exceptions;


public class SimulatorException extends Exception {
    /**
     * @param message is the error message
     */
    public SimulatorException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param e
     */
    public SimulatorException(String message, Exception e) {
        super(message, e);
    }
}
