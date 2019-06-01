package com.epam.whatwherewhen.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * Date: 21.02.2019
 *
 * Helps to display only the first two sentences of the text.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SectionTag extends TagSupport {
    private static final String SENTENCE_SPLIT = "\\r?\\n";
    private static final int SENTENCES_COUNT = 2;
    private String articleBody;

    public void setArticleBody(String articleBody) {
        this.articleBody = articleBody;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String[] sentences = articleBody.trim().split(SENTENCE_SPLIT);
            out.print(sentences.length >= SENTENCES_COUNT ? sentences[0] + sentences[1] : articleBody);

        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
