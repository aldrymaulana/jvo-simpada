/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jvo.simpada.serv;

import jvo.simpada.common.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class srvPenetapan extends HttpServlet {

    public srvPenetapan() {
        jvg = new jvGeneral();
        jvc = new jvCommon();
        JRXML_LOCAL_PATH = jvCommon.fnGetProperty("JRXML_LOCAL_PATH").toString();
        REPORT_LOCAL_PATH = jvCommon.fnGetProperty("REPORT_LOCAL_PATH").toString();
        OUTPUT_LOCAL_PATH = jvCommon.fnGetProperty("OUTPUT_LOCAL_PATH").toString();
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
        String strMode;
        String strWidth;
        String strHeight;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        strMode = "0";
        if (request.getParameter("mode") != null) {
            strMode = request.getParameter("mode").toString();
        }
        strWidth = request.getParameter("hidWidth").toString();
        request.getSession().setAttribute("strWidth", strWidth);
        strHeight = request.getParameter("hidHeight").toString();
        request.getSession().setAttribute("strHeight", strHeight);
        String strLastElement = "";
        if (request.getParameter("hidLastElement") != null) {
            strLastElement = request.getParameter("hidLastElement").toString();
        }
        request.getSession().setAttribute("strLastElement", strLastElement);
        Hashtable htBidUsaha = jvg.fnGetBidUsaha();
        request.getSession().setAttribute("htBidUsaha", htBidUsaha);
        Hashtable htPajak = jvg.fnGetPajak();
        request.getSession().setAttribute("htPajak", htPajak);
        Hashtable htRetribusi = jvg.fnGetRetribusi();
        request.getSession().setAttribute("htRetribusi", htRetribusi);
        Hashtable htPADLain = jvg.fnGetPADLain();
        request.getSession().setAttribute("htPADLain", htPADLain);
        Hashtable htKabupaten = jvg.fnGetKabupaten();
        request.getSession().setAttribute("htKabupaten", htKabupaten);
        Hashtable htKecamatan = jvg.fnGetKecamatan();
        request.getSession().setAttribute("htKecamatan", htKecamatan);
        Hashtable htKelurahan = jvg.fnGetKelurahan();
        request.getSession().setAttribute("htKelurahan", htKelurahan);
        Hashtable htNoRekRetribusi = jvg.fnGetNoRekRetribusi();
        request.getSession().setAttribute("htNoRekRetribusi", htNoRekRetribusi);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String strToday = sdf.format(todayDate);
        String strDate = strToday.substring(0, 2);
        String strMonth = strToday.substring(3, 5);
        String strYear = strToday.substring(6, 10);
        String strNamaBendahara = "";
        String strNIPBendahara = "";
        Hashtable htInfoPejabat1 = jvg.fnGetInfoPejabat();
        int intHtInfoPejabat = htInfoPejabat1.size();
        for (int a = 1; a <= intHtInfoPejabat; a++) {
            String strArray[] = (String[]) htInfoPejabat1.get(String.valueOf(a));
            if (strArray[0].equals("31100")) {
                strNamaBendahara = strArray[1];
                strNIPBendahara = strArray[2];
            }
        }

//        JVM INSTR tableswitch 0 1: default 984
        //                   0 500
        //                   1 579;
//           goto _L1 _L2 _L3
        switch (Integer.parseInt(strMode)) {
            case 0:
                try {
                    String strSource = "penetapan.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;
                
            case 1:
                String strNPWPD = request.getParameter("txtNPWPD").toString();
                String strNamaBU = request.getParameter("txtNamaBU").toString();
                String strNama = request.getParameter("txtNama").toString();
                Hashtable htDetail = new Hashtable();
                htDetail = jvg.fnGetDetail(strNPWPD, strNamaBU, strNama);
                ifcPendaftaran ifcpResult = new ifcPendaftaran();
                ifcpResult = (ifcPendaftaran) htDetail.get("ifcp");
                if (ifcpResult.getStrKodePemilik() != null) {
                    String strSource = "penetapan1.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    Hashtable htNoRek = new Hashtable();
                    htNoRek = jvg.fnGetNoRek();
                    Hashtable htSIResult = new Hashtable();
                    htSIResult = (Hashtable) htDetail.get("htSI");
                    int intJmlSI = htSIResult.size();
                    request.getSession().setAttribute("intJmlSI", Integer.valueOf(intJmlSI));
                    Hashtable htInfoPejabat = jvg.fnGetInfoPejabat();
                    request.getSession().setAttribute("htInfoPejabat", htInfoPejabat);
                    Hashtable htPajakC = jvg.fnGetPajakC();
                    request.getSession().setAttribute("htPajakC", htPajakC);
                    request.getSession().setAttribute("htSI", htSIResult);
                    request.getSession().setAttribute("ifcp", ifcpResult);
                    request.getSession().setAttribute("htNoRek", htNoRek);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                }
                try {
                    String strSource = "penetapan.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    request.getSession().setAttribute("MSG", "Data Tidak Ditemukan!");
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
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
    String JRXML_LOCAL_PATH;
    String REPORT_LOCAL_PATH;
    String OUTPUT_LOCAL_PATH;
}
