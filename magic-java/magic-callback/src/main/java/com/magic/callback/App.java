package com.magic.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    /**
     * Program entry point
     */
    public static void main(String[] args) {
        Task task = new SimpleTask();
        Callback callback = () -> LOGGER.info("I'm done now.");
        task.executeWith(callback);
    }

}
