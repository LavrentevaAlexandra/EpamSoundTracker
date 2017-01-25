package com.lavrente.soundtrack.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by 123 on 25.01.2017.
 */
public class NotDeletedTag extends TagSupport {
    private final String IS_DELETED = "is_deleted";

    @Override
    public int doStartTag() throws JspException {
        Object isDeleted =pageContext.getSession().getAttribute(IS_DELETED);
        if (isDeleted==null || !Boolean.valueOf(isDeleted.toString())) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
