package com.lavrente.soundtrack.tag;

import com.lavrente.soundtrack.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by 123 on 05.01.2017.
 */
public class AdminTag extends TagSupport {
    private static final String USER_ATTR="user";

    @Override
    public int doStartTag() throws JspException {
        User user=(User)pageContext.getSession().getAttribute(USER_ATTR);
        if (user!=null) {
            if(user.getRole()==1)
                return EVAL_BODY_INCLUDE;
            else {
                return SKIP_BODY;
            }
        } else {
            return SKIP_BODY;
        }
    }
}
