package com.lavrente.soundtrack.command;

/**
 * Created by 123 on 29.11.2016.
 */
public enum CommandType {
    ADD_FUNDS{
        {
            this.command=new AddFundsCommand();
        }
    },
    ADD_TRACK{
        {
            this.command=new AddTrackCommand();
        }
    },
    CHANGE{
        {
            this.command=new ChangeCommand();
        }
    },
    CHANGE_PASS{
        {
            this.command=new ChangePasswordCommand();
        }
    },
    COMMENT{
        {
            this.command=new CommentCommand();
        }
    },
    DELETE{
        {
            this.command=new DeleteTrackCommand();
        }
    },
    INDEX{
        {
            this.command=new IndexCommand();
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
            this.command=new MainCommand();
        }
    },
    SIGNUP {
        {
            this.command = new SingUpCommand();
        }
    },
    TRACK_INFO{
        {
            this.command=new TrackInfoCommand();
        }
    };

    AbstractCommand command;
    public AbstractCommand getCurrentCommand() {
        return command;
    }
}