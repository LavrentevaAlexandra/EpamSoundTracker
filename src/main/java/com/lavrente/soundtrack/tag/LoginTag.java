package com.lavrente.soundtrack.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by 123 on 05.01.2017.
 */
public class LoginTag extends TagSupport {

    /** The is login. */
    private final String IS_LOGIN = "is_login";

    /**
     * Do start tag.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
    @Override
    public int doStartTag() throws JspException {
        Object isLogin = pageContext.getSession().getAttribute(IS_LOGIN);
        if (isLogin!=null && Boolean.valueOf(isLogin.toString())) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
