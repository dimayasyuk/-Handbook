package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class InfoTimeTag extends TagSupport {
	private String link;
	private String author;

	public void setLink(String link) { this.link = link;      }
    public void setAuthor(String author) { this.author = author;      }

    @Override
    public int doStartTag() throws JspException {
        String footerContent = "<footer><p>Created by: <a href=" + link + ">" + author + "</a>.</p></footer>";
        try {
            JspWriter out = pageContext.getOut();
            out.write(footerContent);
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
