package com.lavrente.soundtrack.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by 123 on 05.01.2017.
 */
public class NotLoginTag extends TagSupport{
    private final String IS_LOGIN = "loginSuccess";


    @Override
    public int doStartTag() throws JspException {
        String isLogin =(String) pageContext.getSession().getAttribute(IS_LOGIN);
        if (isLogin==null || "false".equals(isLogin)) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
