package com.lavrente.soundtrack.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by 123 on 05.01.2017.
 */
public class LoginTag extends TagSupport {
    private final String IS_LOGIN = "loginSuccess";

    @Override
    public int doStartTag() throws JspException {
        if ("true".equals(pageContext.getSession().getAttribute(IS_LOGIN))) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
