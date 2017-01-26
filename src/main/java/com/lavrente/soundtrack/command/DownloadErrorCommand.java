package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.manager.ConfigurationManager;
import com.lavrente.soundtrack.manager.MessageManager;
import com.lavrente.soundtrack.servlet.SessionRequestContent;

/**
 * Created by 123 on 26.01.2017.
 */
public class DownloadErrorCommand extends AbstractCommand {

    /* (non-Javadoc)
     * @see src.main.java.com.lavrente.soundtrack.command.AbstractCommand#execute(SessionRequestContent)
     */
    @Override
    public String execute(SessionRequestContent sessionRequestContent) {
        sessionRequestContent.setRequestAttribute(ERROR, messageManager.getProperty(MessageManager.DOWNLOAD_ERROR));
        return ConfigurationManager.getProperty(ConfigurationManager.ERROR_PATH);
    }
}
