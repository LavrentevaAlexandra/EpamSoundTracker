package com.lavrente.soundtrack.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by 123 on 05.01.2017.
 */
public class LoginTag extends TagSupport {
    private final String IS_LOGIN = "is_login";

    @Override
    public int doStartTag() throws JspException {
        String isLogin = pageContext.getSession().getAttribute(IS_LOGIN).toString();
        if (Boolean.valueOf(isLogin)) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
