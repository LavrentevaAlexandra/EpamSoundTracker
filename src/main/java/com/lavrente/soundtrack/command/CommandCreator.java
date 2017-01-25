package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.servlet.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by 123 on 29.11.2016.
 */
public class CommandCreator {
    private static final Logger LOG = LogManager.getLogger();
    private static final String COMMAND = "command";

    public AbstractCommand defineCommand(SessionRequestContent sessionRequestContent) {
        AbstractCommand current = new EmptyCommand();

        String command = sessionRequestContent.getRequestParameter(COMMAND);
        if (command != null && !command.isEmpty()) {
            try {
                CommandType currentCommand = CommandType.valueOf(command.toUpperCase());
                current = currentCommand.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                LOG.error("Exception during command creator",e);
            }
        }
        return current;
    }
}
