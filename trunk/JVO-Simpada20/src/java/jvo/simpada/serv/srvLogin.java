/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jvo.simpada.serv;

import jvo.simpada.common.jvGeneral;
import jvo.simpada.common.jvCommon;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class srvLogin extends HttpServlet {

    public srvLogin() {
        jvg = new jvGeneral();
        jvc = new jvCommon();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strMode = "0";
        if (request.getParameter("mode") != null) {
            strMode = request.getParameter("mode").toString();
        }
        String strWidth = jvc.fnGetProperty("SCR_WIDTH");
        request.getSession().setAttribute("strWidth", strWidth);
        String strHeight = jvc.fnGetProperty("SCR_HEIGHT");
        request.getSession().setAttribute("strHeight", strHeight);
        switch (Integer.parseInt(strMode)) {
            case 0: // '\0'
                try {
                    request.getSession().invalidate();
                    Hashtable htDataPemda = jvg.fnGetInfoPemda();
                    request.getSession().setAttribute("htDataPemda", htDataPemda);
                    out.println("<script type=\"text/javascript\">");
                    out.println("window.opener = self;");
                    out.println("window.close();");
                    out.println("var lVarWidth = "+strWidth+";");
                    out.println("var lVarHeight = "+strHeight+";");
                    out.println("var vXPos = (screen.availWidth - lVarWidth) / 2;");
                    out.println("var vYPos = (screen.availHeight - lVarHeight) / 2;");
                    out.println("var winprops = 'width='+lVarWidth+',height='+lVarHeight+',top='+vYPos+',left='+vXPos+',status=yes,resizable=yes,toolbar=no,menubar=no,scrollbars=no,location=no,titlebar=no,hotkeys=no';");
                    out.println("window.open('login.jsp?vWidth='+lVarWidth+'&vHeight='+lVarHeight, 'wLogin', winprops);");
                    out.println("</script>");
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 1: // '\001'
                try {
                    String strLogin = request.getParameter("hidLogin").toString();
                    String strPassword = request.getParameter("hidPassword").toString();
                    Hashtable htInfoLogin = jvg.fnGetInfoLogin(strLogin, strPassword);
                    int intInfoLogin = htInfoLogin.size();
                    if (intInfoLogin > 1) {
                        String strErrMsg = htInfoLogin.get("2").toString();
                        request.getSession().setAttribute("ERROR", strErrMsg);
                        out.println("<script type=\"text/javascript\">");
                        out.println("var lVarWidth = "+strWidth+";");
                        out.println("var lVarHeight = "+strHeight+";");
                        out.println("var vXPos = (screen.availWidth - lVarWidth) / 2;");
                        out.println("var vYPos = (screen.availHeight - lVarHeight) / 2;");
                        out.println("var winprops = 'width='+lVarWidth+',height='+lVarHeight+',top='+vYPos+',left='+vXPos+',status=yes,resizable=yes,toolbar=no,menubar=no,scrollbars=no,location=no,titlebar=no,hotkeys=no';");
                        out.println("window.open('login.jsp?vWidth='+lVarWidth+'&vHeight='+lVarHeight, 'wLogin', winprops);");
                        out.println("</script>");
                    } else {
                        request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                        String strArrLogin[] = (String[]) htInfoLogin.get(String.valueOf(1));
                        String strKdJabatan = strArrLogin[1];
                        Hashtable htHakAkses = jvg.fnGetHakAkses(strKdJabatan);
                        request.getSession().setAttribute("htHakAkses", htHakAkses);
                        out.println("<script type=\"text/javascript\">");
                        out.println("var lVarWidth = "+strWidth+";");
                        out.println("var lVarHeight = "+strHeight+";");
                        out.println("var vXPos = (screen.availWidth - lVarWidth) / 2;");
                        out.println("var vYPos = (screen.availHeight - lVarHeight) / 2;");
                        out.println("var winprops = 'width='+lVarWidth+',height='+lVarHeight+',top='+vYPos+',left='+vXPos+',status=yes,resizable=yes,toolbar=no,menubar=no,scrollbars=no,location=no,titlebar=no,hotkeys=no';");
                        out.println("window.open('index.jsp?vWidth='+lVarWidth+'&vHeight='+lVarHeight, 'wLogin', winprops);");
                        out.println("</script>");
                    }
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 2: // '\002'
                try {
                    request.getSession().invalidate();
                    Hashtable htDataPemda = jvg.fnGetInfoPemda();
                    request.getSession().setAttribute("htDataPemda", htDataPemda);
//                    String strWidth = "1019";
//                    request.getSession().setAttribute("strWidth", strWidth);
//                    String strHeight = "739";
//                    request.getSession().setAttribute("strHeight", strHeight);
                    response.sendRedirect((new StringBuilder("login.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;
        }
    }
    private static final long serialVersionUID = 1L;
    jvGeneral jvg;
    jvCommon jvc;
}
