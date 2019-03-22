package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.util.GregorianCalendar;

@SuppressWarnings("serial")
public class InfoTimeTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        GregorianCalendar gc = new GregorianCalendar();
        String time = "Date : <b> " + LocalDate.now() + " </b>";
        String contacts = "<footer><p>Created by: <a href=\"https://vk.com/id139357398\">Dmitry Yasiukevich</a></p></footer>";
        try {
            JspWriter out = pageContext.getOut();
            out.write(contacts + time);
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
