/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * created by novan
 */
package jvo.simpada.serv;

import jvo.simpada.common.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;

public class srvAnggaran extends HttpServlet {

    public srvAnggaran() {
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strMode = "0";
        if (request.getParameter("mode") != null) {
            strMode = request.getParameter("mode").toString();
        }
        String strIfFrame = "ifAnggaran";
        request.getSession().setAttribute("strIfFrame", strIfFrame);
        String strWidth = request.getParameter("hidWidth").toString();
        request.getSession().setAttribute("strWidth", strWidth);
        String strHeight = request.getParameter("hidHeight").toString();
        request.getSession().setAttribute("strHeight", strHeight);
        String strLastElement = "";
        if (request.getParameter("hidLastElement") != null) {
            strLastElement = request.getParameter("hidLastElement").toString();
        }
        request.getSession().setAttribute("strLastElement", strLastElement);
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

        request.getSession().setAttribute("strNamaBendahara", strNamaBendahara);
        request.getSession().setAttribute("strNIPBendahara", strNIPBendahara);
        switch (Integer.parseInt(strMode)) {
            case 0: // '\0'
                try {
                    String strSource = "anggaran.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    Hashtable htRincian = jvg.fnGetRincian();
                    request.getSession().setAttribute("htRincian", htRincian);
                    Hashtable htRincianAnggaran = jvg.fnGetRincianAnggaran(strYear);
                    if (htRincianAnggaran.size() > 0) {
                        request.getSession().setAttribute("htRincianAnggaran", htRincianAnggaran);
                    } else {
                        request.getSession().removeAttribute("htRincianAnggaran");
                    }
                    Hashtable htInfoPejabat = jvg.fnGetInfoPejabat();
                    request.getSession().setAttribute("htInfoPejabat", htInfoPejabat);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 1: // '\001'
                try {
                    String strSource = "anggaran.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    String strThnAnggaran = request.getParameter("hidThnAnggaran");
                    request.getSession().setAttribute("strThnAnggaran", strThnAnggaran);
                    String strNamaPejabat = request.getParameter("hidNamaPejabat");
                    request.getSession().setAttribute("strNamaPejabat", strNamaPejabat);
                    String strNIPPejabat = request.getParameter("hidNIPPejabat");
                    request.getSession().setAttribute("strNIPPejabat", strNIPPejabat);
                    String strTglTerima = request.getParameter("txtTglAwal");
                    request.getSession().setAttribute("strTglTerima", strTglTerima);
                    Hashtable htData = new Hashtable();
                    Hashtable htRincian = new Hashtable();
                    if (request.getSession().getAttribute("htRincian") != null) {
                        htRincian = (Hashtable) request.getSession().getAttribute("htRincian");
                        int intHtRincian = htRincian.size();
                        for (int a = 1; a <= intHtRincian; a++) {
                            String strKdRekening = request.getParameter((new StringBuilder("txtKodeRek_")).append(a).toString());
                            String strJumlah0 = request.getParameter((new StringBuilder("txtAnggaran_")).append(a).toString());
                            String strJumlah = jvc.fnDropCurrencySign(strJumlah0);
                            String strData[] = new String[6];
                            strData[0] = strThnAnggaran;
                            strData[1] = strKdRekening;
                            strData[2] = strJumlah;
                            strData[3] = strNIPBendahara;
                            strData[4] = strNIPPejabat;
                            strData[5] = strTglTerima;
                            htData.put(String.valueOf(a), strData);
                        }
                    }
                    String strCekUbah = request.getParameter("txtUbah");
                    int intSave = 0;
                    int intDelete = 0;
                    if (strCekUbah.equalsIgnoreCase("1")) {
                        intSave = jvg.fnHapusAnggaran(strThnAnggaran);
                    }
                    intSave = jvg.fnSimpanAnggaran(htData);
                    Hashtable htRincianAnggaran = jvg.fnGetRincianAnggaran(strThnAnggaran);
                    if (htRincianAnggaran.size() > 0) {
                        request.getSession().setAttribute("htRincianAnggaran", htRincianAnggaran);
                    } else {
                        request.getSession().removeAttribute("htRincianAnggaran");
                    }
                    response.sendRedirect((new StringBuilder("anggaran.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 2: // '\002'
                try {
                    String strThnAnggaran = request.getParameter("hidThnAnggaran");
                    request.getSession().setAttribute("strThnAnggaran", strThnAnggaran);
                    String strSource = "anggaran.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    Hashtable htRincian = jvg.fnGetRincian();
                    request.getSession().setAttribute("htRincian", htRincian);
                    Hashtable htRincianAnggaran = jvg.fnGetRincianAnggaran(strThnAnggaran);
                    if (htRincianAnggaran.size() > 0) {
                        request.getSession().setAttribute("htRincianAnggaran", htRincianAnggaran);
                    } else {
                        request.getSession().removeAttribute("htRincianAnggaran");
                    }
                    Hashtable htInfoPejabat = jvg.fnGetInfoPejabat();
                    request.getSession().setAttribute("htInfoPejabat", htInfoPejabat);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 3: // '\003'
                try {
                    String strSource = "anggaran.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;
        }
    }

    protected void createReport2(HttpServletRequest req, HttpServletResponse resp, String strNoSTS)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            String strFileJRXML = "report5.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            String strTglAnggaran = req.getSession().getAttribute("strTglAnggaran").toString();
            Date dtTglAnggaran = null;
            try {
                dtTglAnggaran = (new SimpleDateFormat("dd/MM/yyyy")).parse(strTglAnggaran);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String strNamaPemda = req.getParameter("hidNamaPemda").toString();
            String strNamaBidang = req.getParameter("hidNamaBidang").toString();
            String strAlamatPemda = req.getParameter("hidAlamatPemda").toString();
            String strKotamadyaPemda = req.getParameter("hidKotamadyaPemda").toString();
            String strKodePos = req.getParameter("hidKodePos").toString();
            String strTelepon = req.getParameter("hidTelepon").toString();
            String strFacsimile = req.getParameter("hidFacsimile").toString();
            String strNamaUser = req.getParameter("hidNamaUser").toString();
            String strKdJabatanUser = req.getParameter("hidKdJabatanUser").toString();
            String strNIPUser = req.getParameter("hidNIPUser").toString();
            String strJabatanUser = req.getParameter("hidJabatanUser").toString();
            String strAngka = req.getParameter("hidTotal").toString();
            String strTerbilang = req.getParameter("hidTerbilang").toString();
            String strNamaPejabat = req.getParameter("hidNamaPejabat").toString();
            String strJabatanPejabat = req.getParameter("hidJabatanPejabat").toString();
            String strNIPPejabat = req.getParameter("hidNIPPejabat").toString();
            Map parameters = new HashMap();
            parameters.put("pNamaPemda", strNamaPemda);
            parameters.put("pNamaDinas", strNamaBidang);
            parameters.put("pNoSTS", strNoSTS);
            parameters.put("pAngka", strAngka);
            parameters.put("pTerbilang", strTerbilang);
            parameters.put("pNamaAtasan", strNamaPejabat);
            parameters.put("pJabatanAtasan", strJabatanPejabat);
            parameters.put("pNIP", strNIPPejabat);
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

            parameters.put("pNamaBendahara", strNamaBendahara);
            parameters.put("pNIPBendahara", strNIPBendahara);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            String strFilePDF = (new StringBuilder("STS_")).append(strToday).append(".pdf").toString();
            String strOutput = (new StringBuilder(String.valueOf(REPORT_LOCAL_PATH))).append(strFilePDF).toString();
            JasperExportManager.exportReportToPdfFile(print, strOutput);
            String strPDFOutput = (new StringBuilder(String.valueOf(OUTPUT_LOCAL_PATH))).append(strFilePDF).toString();
            resp.sendRedirect(strPDFOutput);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (JRException jre) {
            jre.printStackTrace();
        }
    }
    private static final long serialVersionUID = 1L;
    jvGeneral jvg;
    jvCommon jvc;
    String JRXML_LOCAL_PATH;
    String REPORT_LOCAL_PATH;
    String OUTPUT_LOCAL_PATH;
}
