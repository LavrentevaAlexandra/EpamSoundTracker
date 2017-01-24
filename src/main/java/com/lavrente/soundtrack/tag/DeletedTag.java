package com.lavrente.soundtrack.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by 123 on 25.01.2017.
 */
public class DeletedTag extends TagSupport {
    private final String IS_DELETED = "is_deleted";

    @Override
    public int doStartTag() throws JspException {
        String isDeleted =pageContext.getSession().getAttribute(IS_DELETED).toString();
        if (Boolean.valueOf(isDeleted)) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}

