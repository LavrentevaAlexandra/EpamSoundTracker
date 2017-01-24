package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.command.admin.AddTrackCommand;
import com.lavrente.soundtrack.command.admin.DeleteTrackCommand;
import com.lavrente.soundtrack.command.admin.RecoverTrackCommand;
import com.lavrente.soundtrack.command.admin.ShowDeletedCommand;
import com.lavrente.soundtrack.command.user.*;
import com.lavrente.soundtrack.command.visitor.LogInCommand;
import com.lavrente.soundtrack.command.visitor.SingUpCommand;
import com.lavrente.soundtrack.command.visitor.TrackInfoCommand;

/**
 * Created by 123 on 29.11.2016.
 */
public enum CommandType {
    ADD_FUNDS{
        {
            this.command = new AddFundsCommand();
        }
    },
    ADD_TRACK{
        {
            this.command = new AddTrackCommand();
        }
    },
    CHANGE{
        {
            this.command = new ChangeCommand();
        }
    },
    CHANGE_PASS{
        {
            this.command = new ChangePasswordCommand();
        }
    },
    COMMENT{
        {
            this.command = new CommentCommand();
        }
    },
    DELETE{
        {
            this.command = new DeleteTrackCommand();
        }
    },
    DELETED{
        {
            this.command = new ShowDeletedCommand();
        }
    },
    INDEX{
        {
            this.command = new IndexCommand();
        }
    },
    LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    LOGIN {
        {
            this.command = new LogInCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogOutCommand();
        }
    },
    MAIN{
        {
            this.command = new MainCommand();
        }
    },
    RECOVER{
        {
            this.command = new RecoverTrackCommand();
        }
    },
    SIGNUP {
        {
            this.command = new SingUpCommand();
        }
    },
    TRACK_INFO{
        {
            this.command = new TrackInfoCommand();
        }
    };

    AbstractCommand command;
    public AbstractCommand getCurrentCommand() {
        return command;
    }
}