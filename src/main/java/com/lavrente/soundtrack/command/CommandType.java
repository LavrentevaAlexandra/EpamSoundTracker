package com.lavrente.soundtrack.command;

/**
 * Created by 123 on 29.11.2016.
 */
public enum CommandType {
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
    SIGNUP {
        {
            this.command = new SingUpCommand();
        }
    },
    LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
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
    TRACK_INFO{
        {
            this.command=new TrackInfoCommand();
        }
    },
    COMMENT{
        {
            this.command=new CommentCommand();
        }
    },
    MAIN{
        {
            this.command=new MainCommand();
        }
    },
    INDEX{
        {
            this.command=new IndexCommand();
        }
    };
    AbstractCommand command;

    public AbstractCommand getCurrentCommand() {
        return command;
    }
}