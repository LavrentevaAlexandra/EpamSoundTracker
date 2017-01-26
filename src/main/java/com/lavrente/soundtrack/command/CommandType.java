package com.lavrente.soundtrack.command;

import com.lavrente.soundtrack.command.admin.*;
import com.lavrente.soundtrack.command.user.*;
import com.lavrente.soundtrack.command.visitor.*;

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
    ALL{
        {
            this.command = new AllTracksCommand();
        }
    },
    BUY{
        {
            this.command= new BuyTrackCommand();
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
    DOWNLOAD{
        {
            this.command= new DownloadCommand();
        }
    },
    EDIT{
        {
          this.command = new EditTrackCommand();
        }
    },
    GENRE{
        {
            this.command = new ShowGenreCommand();
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
    MY_ORDERS{
        {
            this.command= new MyOrdersCommand();
        }
    },
    RECOVER{
        {
            this.command = new RecoverTrackCommand();
        }
    },
    SEARCH{
        {
            this.command = new SearchCommand();
        }
    },
    SEARCH_USERS{
        {
            this.command = new SearchUsersCommand();
        }
    },
    SET_BONUS{
        {
            this.command = new SetBonusCommand();
        }
    },
    SHOW_USERS{
        {
            this.command = new ShowUsersCommand();
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