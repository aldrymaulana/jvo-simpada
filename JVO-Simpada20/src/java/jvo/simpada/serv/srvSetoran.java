/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jvo.simpada.serv;

import jvo.simpada.common.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;

public class srvSetoran extends HttpServlet
{

    public srvSetoran()
    {
        jvg = new jvGeneral();
        jvc = new jvCommon();
        JRXML_LOCAL_PATH = jvCommon.fnGetProperty("JRXML_LOCAL_PATH").toString();
        REPORT_LOCAL_PATH = jvCommon.fnGetProperty("REPORT_LOCAL_PATH").toString();
        OUTPUT_LOCAL_PATH = jvCommon.fnGetProperty("OUTPUT_LOCAL_PATH").toString();
        NAMA_BANK = "";
        NO_REK = "";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strMode = "0";
        if(request.getParameter("mode") != null)
            strMode = request.getParameter("mode").toString();
        String strWidth = request.getParameter("hidWidth").toString();
        request.getSession().setAttribute("strWidth", strWidth);
        String strHeight = request.getParameter("hidHeight").toString();
        request.getSession().setAttribute("strHeight", strHeight);
        String strLastElement = "";
        if(request.getParameter("hidLastElement") != null)
            strLastElement = request.getParameter("hidLastElement").toString();
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
        for(int a = 1; a <= intHtInfoPejabat; a++)
        {
            String strArray[] = (String[])htInfoPejabat1.get(String.valueOf(a));
            if(strArray[0].equals("31100"))
            {
                strNamaBendahara = strArray[1];
                strNIPBendahara = strArray[2];
            }
        }

        request.getSession().setAttribute("strNamaBendahara", strNamaBendahara);
        request.getSession().setAttribute("strNIPBendahara", strNIPBendahara);
        String strKode = "STS";
        Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
        if(htInfoBank.size() > 0)
        {
            String strInfoBank[] = (String[])htInfoBank.get(String.valueOf(1));
            NAMA_BANK = strInfoBank[0];
            NO_REK = strInfoBank[1];
        }
        jvc.fnPrint((new StringBuilder("Nama Bank: ")).append(NAMA_BANK).toString());
        jvc.fnPrint((new StringBuilder("No Rek Bank: ")).append(NO_REK).toString());
        switch(Integer.parseInt(strMode))
        {
        case 0: // '\0'
            try
            {
                String strSource = "setoran1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 1: // '\001'
            try
            {
                String strSource = (new StringBuilder("setoran2.jsp?nmBank=")).append(NAMA_BANK).append("&noRek=").append(NO_REK).toString();
                request.getSession().setAttribute("strSource", strSource);
                String strTglSTS = request.getParameter("txtTglAwal").toString();
                request.getSession().setAttribute("strTglSTS", strTglSTS);
                String strDateSTS = strTglSTS.substring(0, 2);
                String strMonthSTS = strTglSTS.substring(3, 5);
                String strYearSTS = strTglSTS.substring(6, 10);
                String strCountSTS = jvg.fnGetCounter("STS", "STS", strMonthSTS, strYearSTS, "0");
                String strNoSTS = (new StringBuilder("03.")).append(strDateSTS).append(".").append(strMonthSTS).append(".").append(strYearSTS).append(".").append(jvc.fnLRPad("LPAD", strCountSTS, "0", 5)).toString();
                request.getSession().setAttribute("strNoSTS", strNoSTS);
                Hashtable htRincian = jvg.fnGetRincian();
                request.getSession().setAttribute("htRincian", htRincian);
                Hashtable htRincianBiaya = jvg.fnGetRincianBiaya(strTglSTS);
                request.getSession().setAttribute("htRincianBiaya", htRincianBiaya);
                Hashtable htInfoPejabat = jvg.fnGetInfoPejabat();
                request.getSession().setAttribute("htInfoPejabat", htInfoPejabat);
                Hashtable htPajakC = jvg.fnGetPajakC();
                request.getSession().setAttribute("htPajakC", htPajakC);
                int itnReincian = htRincianBiaya.size();
                Double dblTotal = Double.valueOf(0.0D);
                for(int a = 1; a <= itnReincian; a++)
                {
                    String strArray[] = (String[])htRincianBiaya.get(String.valueOf(a));
                    String strJumlah = strArray[2];
                    Double intJumlah = Double.valueOf(Double.parseDouble(strJumlah));
                    dblTotal = Double.valueOf(dblTotal.doubleValue() + intJumlah.doubleValue());
                }

                request.getSession().setAttribute("intTotal", dblTotal);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 2: // '\002'
            try
            {
                jvc.fnPrint("Case 2, Simpan dan Cetak STS");
                String strTglSTS = request.getParameter("hidTglSTS").toString();
                String strDateSTS = strTglSTS.substring(0, 2);
                String strMonthSTS = strTglSTS.substring(3, 5);
                String strYearSTS = strTglSTS.substring(6, 10);
                String strCountSTS = jvg.fnGetCounter("STS", "STS", strMonthSTS, strYearSTS, "1");
                String strNoSTS = (new StringBuilder("03.")).append(strDateSTS).append(".").append(strMonthSTS).append(".").append(strYearSTS).append(".").append(jvc.fnLRPad("LPAD", strCountSTS, "0", 5)).toString();
                Hashtable htSTS = jvg.fnGetSTS(strTglSTS);
                Hashtable htValidSTS = new Hashtable();
                int intHtSTS = htSTS.size();
                jvc.fnPrint((new StringBuilder("intHtSTS: ")).append(intHtSTS).toString());
                int i = 1;
                for(int a = 1; a <= intHtSTS; a++)
                {
                    String strArray[] = (String[])htSTS.get(String.valueOf(a));
                    if(strArray[2].trim().length() != 0 && !strArray[2].equals("0"))
                    {
                        String strValidArray[] = new String[6];
                        String strKdRekening = strArray[0];
                        jvc.fnPrint((new StringBuilder("strKdRekening: ")).append(strKdRekening).toString());
                        String strJumlah = strArray[2];
                        jvc.fnPrint((new StringBuilder("strJumlah: ")).append(strJumlah).toString());
                        strValidArray[0] = strTglSTS;
                        strValidArray[1] = strKdRekening;
                        strValidArray[2] = strJumlah;
                        strValidArray[3] = NAMA_BANK;
                        strValidArray[4] = NO_REK;
                        strValidArray[5] = strNoSTS;
                        htValidSTS.put(String.valueOf(i), strValidArray);
                        i++;
                    }
                }

                int intSaveSTS = jvg.fnSaveSTS(htValidSTS);
                createReport2(request, response, strNoSTS);
                return;
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;
        }
    }

    protected void createReport2(HttpServletRequest req, HttpServletResponse resp, String strNoSTS)
        throws ServletException, IOException
    {
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            String strFileJRXML = "report5.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            String strTglSTS = req.getSession().getAttribute("strTglSTS").toString();
            String strDtDate3 = strTglSTS.substring(0, 2);
            String strDtMonth3 = strTglSTS.substring(3, 5);
            String strDtYear3 = strTglSTS.substring(6, strTglSTS.length());
            strTglSTS = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
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
            parameters.put("pTglSTS", strTglSTS);
            parameters.put("pNamaPemda", strNamaPemda);
            parameters.put("pNamaDinas", strNamaBidang);
            parameters.put("pNoSTS", strNoSTS);
            parameters.put("pAngka", strAngka);
            parameters.put("pTerbilang", strTerbilang);
            parameters.put("pNamaBank", NAMA_BANK);
            parameters.put("pNoRek", NO_REK);
            parameters.put("pNamaAtasan", strNamaPejabat);
            parameters.put("pJabatanAtasan", strJabatanPejabat);
            parameters.put("pNIP", strNIPPejabat);
            String strNamaBendahara = "";
            String strNIPBendahara = "";
            Hashtable htInfoPejabat1 = jvg.fnGetInfoPejabat();
            int intHtInfoPejabat = htInfoPejabat1.size();
            for(int a = 1; a <= intHtInfoPejabat; a++)
            {
                String strArray[] = (String[])htInfoPejabat1.get(String.valueOf(a));
                if(strArray[0].equals("31100"))
                {
                    strNamaBendahara = strArray[1];
                    strNIPBendahara = strArray[2];
                }
            }

            parameters.put("pNamaBendahara", strNamaBendahara);
            parameters.put("pNIPBendahara", strNIPBendahara);
            String strToday = (new StringBuilder(String.valueOf(strDtDate3))).append(strDtMonth3).append(strDtYear3).toString();
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            String strFilePDF = (new StringBuilder("STS_")).append(strToday).append(".pdf").toString();
            String strOutput = (new StringBuilder(String.valueOf(REPORT_LOCAL_PATH))).append(strFilePDF).toString();
            JasperExportManager.exportReportToPdfFile(print, strOutput);
            String strPDFOutput = (new StringBuilder(String.valueOf(OUTPUT_LOCAL_PATH))).append(strFilePDF).toString();
            resp.sendRedirect(strPDFOutput);
        }
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        catch(JRException jre)
        {
            jre.printStackTrace();
        }
    }

    private static final long serialVersionUID = 1L;
    jvGeneral jvg;
    jvCommon jvc;
    String JRXML_LOCAL_PATH;
    String REPORT_LOCAL_PATH;
    String OUTPUT_LOCAL_PATH;
    String NAMA_BANK;
    String NO_REK;
}
