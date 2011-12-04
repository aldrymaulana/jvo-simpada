/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tech.master.servlet;

import com.tech.master.common.*;
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
import net.sf.jasperreports.view.JasperViewer;

public class srvPembayaran1 extends HttpServlet
{

    public srvPembayaran1()
    {
        jvg = new jvGeneralBusiness();
        jvc = new jvCommonClass();
        JRXML_LOCAL_PATH = jvCommonClass.fnGetProperty("JRXML_LOCAL_PATH").toString();
        REPORT_LOCAL_PATH = jvCommonClass.fnGetProperty("REPORT_LOCAL_PATH").toString();
        OUTPUT_LOCAL_PATH = jvCommonClass.fnGetProperty("OUTPUT_LOCAL_PATH").toString();
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
        if(request.getParameter("mode") != null) {
            strMode = request.getParameter("mode").toString();
        }
        String strIfFrame = "ifPembayaran1";
        request.getSession().setAttribute("strIfFrame", strIfFrame);
        String strWidth = request.getParameter("hidWidth").toString();
        request.getSession().setAttribute("strWidth", strWidth);
        String strHeight = request.getParameter("hidHeight").toString();
        request.getSession().setAttribute("strHeight", strHeight);
        String strLastElement = "";
        if(request.getParameter("hidLastElement") != null)
            strLastElement = request.getParameter("hidLastElement").toString();
        request.getSession().setAttribute("strLastElement", strLastElement);
        Hashtable htBidUsaha = jvg.fnGetBidUsaha();
        request.getSession().setAttribute("htBidUsaha", htBidUsaha);
        Hashtable htPajak = jvg.fnGetPajak();
        request.getSession().setAttribute("htPajak", htPajak);
        Hashtable htRetribusi = jvg.fnGetRetribusi();
        request.getSession().setAttribute("htRetribusi", htRetribusi);
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
        for(int a = 1; a <= intHtInfoPejabat; a++)
        {
            String strArray[] = (String[])htInfoPejabat1.get(String.valueOf(a));
            if(strArray[0].equals("31100"))
            {
                strNamaBendahara = strArray[1];
                strNIPBendahara = strArray[2];
            }
        }

        switch(Integer.parseInt(strMode))
        {
        case 2: // '\002'
            try
            {
                jvc.fnPrint("Case 2, Cetak Report");
                String strNoSKPD = jvc.fnGetValue(request.getParameter("hidNoSKPD"));
                String strKdPemilik = jvc.fnGetValue(request.getParameter("hidKdPemilik"));
                String strNoNPWPD = jvc.fnGetValue(request.getParameter("hidNoNPWPD"));
                String strNoNPWRD = jvc.fnGetValue(request.getParameter("hidNoNPWRD"));
                Hashtable htGetSKPD = jvg.fnGetSKPD(strNoSKPD, strKdPemilik, strNoNPWPD, strNoNPWRD);
                createReport2(request, response, htGetSKPD);
                return;
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp).toString());
                return;
            }
            

        case 3: // '\003'
            try
            {
                jvc.fnPrint("Case 3, Simpan");
                String strKdCetak = jvc.fnGetValue(request.getParameter("hidKdCetak"));
                String strDate1 = jvc.fnGetValue(request.getParameter("hidDate1"));
                String strDate2 = jvc.fnGetValue(request.getParameter("hidDate2"));
                String strTahun = jvc.fnGetValue(request.getParameter("hidTahun"));
                String strKdPemilik = jvc.fnGetValue(request.getParameter("hidKdPemilik"));
                String strNoRek = jvc.fnGetValue(request.getParameter("hidNoRek"));
                String strUraian = jvc.fnGetValue(request.getParameter("hidUraian"));
                String strJumlah = jvc.fnGetValue(request.getParameter("hidJml"));
                String strBunga = jvc.fnGetValue(request.getParameter("hidBunga"));
                String strKenaikan = jvc.fnGetValue(request.getParameter("hidKenaikan"));
                String strTotal = jvc.fnGetValue(request.getParameter("hidTotal"));
                String strTglSKPD = jvc.fnGetValue(request.getParameter("hidTglSKPD"));
                String strNoNPWPD = jvc.fnGetValue(request.getParameter("hidNoNPWPD"));
                String strJnsPekerjaan = jvc.fnGetValue(request.getParameter("hidJnsPekerjaan"));
                String strKodeSimpan = "0";
                if(strKdCetak.equalsIgnoreCase("L"))
                    strKodeSimpan = "1";
                String strCountSKPD = jvg.fnGetCounter("SKRD", strNoRek, strMonth, strYear, strKodeSimpan);
                String strNoSKPD = (new StringBuilder(String.valueOf(strNoRek))).append(".").append(strMonth).append(strYear.substring(2, 4)).append(".").append(jvc.fnLRPad("LPAD", strCountSKPD, "0", 4)).toString();
                ifcPenetapan ifcp = new ifcPenetapan();
                ifcp.setStrBunga(strBunga);
                ifcp.setStrDate1(strDate1);
                ifcp.setStrDate2(strDate2);
                ifcp.setStrJumlah(strJumlah);
                ifcp.setStrKdPemilik(strKdPemilik);
                ifcp.setStrKenaikan(strKenaikan);
                ifcp.setStrNoNPWPD(strNoNPWPD);
                ifcp.setStrNoRek(strNoRek);
                ifcp.setStrNoSKPD(strNoSKPD);
                ifcp.setStrTahun(strTahun);
                ifcp.setStrTglSKPD(strTglSKPD);
                ifcp.setStrTotal(strTotal);
                ifcp.setStrUraian(strUraian);
                ifcp.setStrJnsPekerjaan(strJnsPekerjaan);
                String strDetilUraian = "";
                String strRpUraian = "";
                String strJmlUraian = "";
                Hashtable htUraian = new Hashtable();
                if(!strNoRek.equalsIgnoreCase("4110606"))
                {
                    strJmlUraian = request.getParameter("hidJmlUraian").toString();
                } else
                {
                    String strPajakC = request.getParameter("hidPajakC").toString();
                    strJmlUraian = strPajakC;
                }
                int intJmlUraianAsli = Integer.parseInt(strJmlUraian);
                int b = 1;
                for(int a = 1; a <= intJmlUraianAsli; a++)
                {
                    String strArray[] = new String[2];
                    strDetilUraian = request.getParameter((new StringBuilder("txtUraian_")).append(a).toString()).toString();
                    strRpUraian = request.getParameter((new StringBuilder("rpUraian_")).append(a).toString()).toString();
                    if(!strRpUraian.equals("0"))
                    {
                        jvc.fnPrint((new StringBuilder("strDetilUraian: ")).append(strDetilUraian).toString());
                        jvc.fnPrint((new StringBuilder("strRpUraian: ")).append(strRpUraian).toString());
                        strArray[0] = strDetilUraian;
                        strArray[1] = strRpUraian;
                        htUraian.put(String.valueOf(b), strArray);
                        b++;
                    }
                }

                int intSimpan = 0;
                if(strKdCetak.equalsIgnoreCase("L"))
                    intSimpan = jvg.fnSaveSKPD(ifcp);
                else
                    intSimpan = jvg.fnSaveSKPD0(ifcp);
                if(!strNoRek.equalsIgnoreCase("4110606"))
                {
                    jvc.fnPrint("Bukan Bahan Galian");
                    if(intSimpan > 0 && strDetilUraian.trim().length() > 0)
                    {
                        int intDetil = 0;
                        if(strKdCetak.equalsIgnoreCase("L"))
                            jvg.fnSaveDetilSKPD(strNoSKPD, strNoNPWPD, htUraian);
                        else
                            jvg.fnSaveDetilSKPD0(strNoSKPD, strNoNPWPD, htUraian);
                    }
                } else
                {
                    jvc.fnPrint("Bahan Galian");
                    if(intSimpan > 0)
                    {
                        int intDetil = 0;
                        if(strKdCetak.equalsIgnoreCase("L"))
                            jvg.fnSaveDetilSKPD(strNoSKPD, strNoNPWPD, htUraian);
                        else
                            jvg.fnSaveDetilSKPD0(strNoSKPD, strNoNPWPD, htUraian);
                    }
                }
                if(strKdCetak.equalsIgnoreCase("L"))
                {
                    String strNoNPWRD = jvc.fnGetValue(request.getParameter("hidNoNPWRD"));
                    Hashtable htGetSKPD = jvg.fnGetSKPD(strNoSKPD, strKdPemilik, strNoNPWPD, strNoNPWRD);
                    createReport2(request, response, htGetSKPD);
                } else
                {
                    String strNoNPWRD = jvc.fnGetValue(request.getParameter("hidNoNPWRD"));
                    Hashtable htGetSKPD = jvg.fnGetSKPD0(strNoSKPD, strKdPemilik, strNoNPWPD, strNoNPWRD);
                    createReport20(request, response, htGetSKPD);
                }
                return;
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp).toString());
                return;
            }
            

        case 4: // '\004'
            try
            {
                jvc.fnPrint("Case 4, Tambah Uraian");
                String strJmlPajak = request.getParameter("jmlPajak").toString();
                request.getSession().setAttribute("strJmlPajak", strJmlPajak);
                Hashtable htUraian = new Hashtable();
                String strJmlUraian = request.getParameter("hidJmlUraian").toString();
                int intJmlUraianAsli = Integer.parseInt(strJmlUraian);
                for(int a = 1; a <= intJmlUraianAsli; a++)
                {
                    String strArray[] = new String[2];
                    String strUraian = request.getParameter((new StringBuilder("txtUraian_")).append(a).toString()).toString();
                    String strRpUraian = request.getParameter((new StringBuilder("rpUraian_")).append(a).toString()).toString();
                    strArray[0] = strUraian;
                    strArray[1] = strRpUraian;
                    htUraian.put(String.valueOf(a), strArray);
                }

                request.getSession().setAttribute("htUraian", htUraian);
                String strBunga = request.getParameter("txtBunga").toString();
                request.getSession().setAttribute("strBunga", strBunga);
                String strKenaikan = request.getParameter("txtKenaikan").toString();
                request.getSession().setAttribute("strKenaikan", strKenaikan);
                String strJmlTotal = request.getParameter("jmlTotal").toString();
                request.getSession().setAttribute("strJmlTotal", strJmlTotal);
                String strJmlSubTotal = request.getParameter("jmlSubTotal").toString();
                request.getSession().setAttribute("strJmlSubTotal", strJmlSubTotal);
                String strKodePajak = request.getParameter("hidKodePajak").toString();
                int intJmlUraian = Integer.parseInt(strJmlUraian) + 1;
                request.getSession().setAttribute("intJmlUraian", Integer.valueOf(intJmlUraian));
                response.sendRedirect((new StringBuilder("skrd.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).append("&vKode=").append(strKodePajak).toString());
                return;
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp).toString());
                return;
            }
            

        case 5: // '\005'
            try
            {
                jvc.fnPrint("Case 5, Hapus Uraian");
                String strJmlPajak = request.getParameter("jmlPajak").toString();
                request.getSession().setAttribute("strJmlPajak", strJmlPajak);
                Hashtable htUraian = new Hashtable();
                String strJmlUraian = request.getParameter("hidJmlUraian").toString();
                int intJmlUraianAsli = Integer.parseInt(strJmlUraian);
                for(int a = 1; a <= intJmlUraianAsli; a++)
                {
                    String strArray[] = new String[2];
                    String strUraian = request.getParameter((new StringBuilder("txtUraian_")).append(a).toString()).toString();
                    String strRpUraian = request.getParameter((new StringBuilder("rpUraian_")).append(a).toString()).toString();
                    strArray[0] = strUraian;
                    strArray[1] = strRpUraian;
                    htUraian.put(String.valueOf(a), strArray);
                }

                request.getSession().setAttribute("htUraian", htUraian);
                String strBunga = request.getParameter("txtBunga").toString();
                request.getSession().setAttribute("strBunga", strBunga);
                String strKenaikan = request.getParameter("txtKenaikan").toString();
                request.getSession().setAttribute("strKenaikan", strKenaikan);
                String strJmlTotal = request.getParameter("jmlTotal").toString();
                request.getSession().setAttribute("strJmlTotal", strJmlTotal);
                String strJmlSubTotal = request.getParameter("jmlSubTotal").toString();
                request.getSession().setAttribute("strJmlSubTotal", strJmlSubTotal);
                String strKodePajak = request.getParameter("hidKodePajak").toString();
                int intJmlUraian = Integer.parseInt(strJmlUraian) - 1;
                request.getSession().setAttribute("intJmlUraian", Integer.valueOf(intJmlUraian));
                response.sendRedirect((new StringBuilder("skrd.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).append("&vKode=").append(strKodePajak).toString());
                return;
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp).toString());
                return;
            }
            
        }
    }

    protected void createReport2(HttpServletRequest req, HttpServletResponse resp, Hashtable htGetSKPD)
        throws ServletException, IOException
    {
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String strDSN = jvCommonClass.fnGetProperty("DSN");
            java.sql.Connection con = DriverManager.getConnection("jdbc:odbc:" + strDSN);
            String strFileJRXML = "report3.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            String strNoSKPD = jvc.fnGetValue(req.getParameter("hidNoSKPD"));
            String strKdPemilik = jvc.fnGetValue(req.getParameter("hidKdPemilik"));
            String strNoNPWPD = jvc.fnGetValue(req.getParameter("hidNoNPWPD"));
            String strNoNPWRD = jvc.fnGetValue(req.getParameter("hidNoNPWRD"));
            String strNamaPejabat = jvc.fnGetValue(req.getParameter("hidNamaPejabat"));
            String strJabatanPejabat = jvc.fnGetValue(req.getParameter("hidJabatanPejabat"));
            String strNIPPejabat = jvc.fnGetValue(req.getParameter("hidNIPPejabat"));
            String strTglAwal = jvc.fnGetValue(req.getParameter("hidDate1"));
            String strTglAkhir = jvc.fnGetValue(req.getParameter("hidDate2"));
            String strTahun = jvc.fnGetValue(req.getParameter("hidTahun"));
            String strNama = ((String)htGetSKPD.get("Nama")).toString();
            String strJalan = ((String)htGetSKPD.get("Jalan")).toString();
            String strNo = ((String)htGetSKPD.get("No")).toString();
            String strRT = ((String)htGetSKPD.get("RT")).toString();
            String strRW = ((String)htGetSKPD.get("RW")).toString();
            String strRK = "";
            String strKodePos = ((String)htGetSKPD.get("KodePos")).toString();
            String strPemilik = ((String)htGetSKPD.get("Pemilik")).toString();
            String strKabupaten = ((String)htGetSKPD.get("Kabupaten")).toString();
            String strKecamatan = ((String)htGetSKPD.get("Kecamatan")).toString();
            String strKelurahan = ((String)htGetSKPD.get("Kelurahan")).toString();
            String strTotal = jvc.fnGetValue(req.getParameter("jmlTotal"));
            String strSubTotal = jvc.fnGetValue(req.getParameter("jmlSubTotal"));
            String strSubTerbilang = jvc.fnGetValue(req.getParameter("hidSubTerbilang"));
            jvc.fnPrint((new StringBuilder("strSubTerbilang: ")).append(strSubTerbilang).toString());
            String strTerbilang = jvc.fnGetValue(req.getParameter("hidTerbilang"));
            jvc.fnPrint((new StringBuilder("strTerbilang: ")).append(strTerbilang).toString());
            String strTglJthTempo = jvc.fnGetValue(req.getParameter("hidTglJthTempo"));
            SimpleDateFormat sdfTglJthTempo = new SimpleDateFormat("dd/MM/yyyy");
            Date dtTglJthTempo = new Date();
            try
            {
                dtTglJthTempo = sdfTglJthTempo.parse(strTglJthTempo);
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp.getMessage()).toString());
            }
            SimpleDateFormat sdfTglJthTempo1 = new SimpleDateFormat("dd MMMM yyyy");
            strTglJthTempo = sdfTglJthTempo1.format(dtTglJthTempo);
            String strNamaBU = jvc.fnGetValue(req.getParameter("hidNamaBU"));
            String strAlamatBU = jvc.fnGetValue(req.getParameter("hidAlamatBU"));
            String strBunga = jvc.fnGetValue(req.getParameter("txtBunga"));
            String strKenaikan = jvc.fnGetValue(req.getParameter("txtKenaikan"));
            String strPembayaran = jvc.fnGetValue(req.getParameter("hidUraian"));
            String strPekerjaan = jvc.fnGetValue(req.getParameter("hidJnsPekerjaan"));
            Map parameters = new HashMap();
            parameters.put("pNoUrut", strNoSKPD);
            parameters.put("pKdPemilik", strKdPemilik);
            parameters.put("pNPWPD", strNoNPWPD);
            parameters.put("pNPWRD", strNoNPWRD);
            parameters.put("pNamaPejabat", strNamaPejabat);
            parameters.put("pJabatanPejabat", strJabatanPejabat);
            parameters.put("pNIPPejabat", strNIPPejabat);
            parameters.put("pTglAwal", strTglAwal);
            parameters.put("pTglAkhir", strTglAkhir);
            parameters.put("pTahun", strTahun);
            parameters.put("pJmlUang", strTotal);
            parameters.put("pSubTerbilang", strSubTerbilang);
            parameters.put("pTerbilang", strTerbilang);
            parameters.put("pTglJthTempo", strTglJthTempo);
            parameters.put("pBunga", strBunga);
            parameters.put("pKenaikan", strKenaikan);
            parameters.put("pNamaPerusahaan", strNamaBU);
            parameters.put("pAlamatPerusahaan", strAlamatBU);
            parameters.put("pPembayaran", strPembayaran);
            parameters.put("pPekerjaan", strPekerjaan);
            parameters.put("pTitle1", "SURAT KETETAPAN RETRIBUSI DAERAH");
            parameters.put("pTitle2", "(SKR-DAERAH)");
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
            parameters.put("Nama", strNama);
            parameters.put("Jalan", strJalan);
            parameters.put("No", strNo);
            parameters.put("RT", strRT);
            parameters.put("RW", strRW);
            parameters.put("RK", strRK);
            parameters.put("KodePos", strKodePos);
            parameters.put("Pemilik", strPemilik);
            parameters.put("Kabupaten", strKabupaten);
            parameters.put("Kecamatan", strKecamatan);
            parameters.put("Kelurahan", strKelurahan);
            Hashtable htDataPemda = new Hashtable();
            String strNamaPemda = "";
            String strNamaBidang = "";
            String strAlamatPemda = "";
            String strKotamadyaPemda = "";
            String strKodePosPemda = "";
            String strTeleponPemda = "";
            String strFacsimilePemda = "";
            if(req.getSession().getAttribute("htDataPemda") != null)
            {
                htDataPemda = (Hashtable)req.getSession().getAttribute("htDataPemda");
                int intDataPemda = htDataPemda.size();
                for(int a = 1; a <= intDataPemda; a++)
                {
                    String strArray[] = (String[])htDataPemda.get(String.valueOf(a));
                    strNamaPemda = strArray[0].toUpperCase();
                    strNamaBidang = strArray[1].toUpperCase();
                    strAlamatPemda = (new StringBuilder(String.valueOf(strArray[2]))).append(" No ").append(strArray[3]).append(", RT/RW ").append(strArray[4]).append("/").append(strArray[5]).toString();
                    strKotamadyaPemda = strArray[9];
                    strKodePosPemda = strArray[10];
                    strTeleponPemda = strArray[11];
                    strFacsimilePemda = strArray[12];
                }

            }
            parameters.put("strNamaPemda", strNamaPemda);
            parameters.put("strNamaBidang", strNamaBidang);
            parameters.put("strAlamatPemda", strAlamatPemda);
            parameters.put("strKotamadyaPemda", strKotamadyaPemda);
            parameters.put("strKodePosPemda", strKodePosPemda);
            parameters.put("strTeleponPemda", strTeleponPemda);
            parameters.put("strFacsimilePemda", strFacsimilePemda);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            String strFilePDF = (new StringBuilder("SKRD_")).append(strNoNPWPD.replace('.', '_')).append("_").append(strToday).append(".pdf").toString();
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

    protected void createReport20(HttpServletRequest req, HttpServletResponse resp, Hashtable htGetSKPD)
        throws ServletException, IOException
    {
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String strDSN = jvCommonClass.fnGetProperty("DSN");
            java.sql.Connection con = DriverManager.getConnection("jdbc:odbc:" + strDSN);
            String strFileJRXML = "report30.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            String strNoSKPD = jvc.fnGetValue(req.getParameter("hidNoSKPD"));
            String strKdPemilik = jvc.fnGetValue(req.getParameter("hidKdPemilik"));
            String strNoNPWPD = jvc.fnGetValue(req.getParameter("hidNoNPWPD"));
            String strNoNPWRD = jvc.fnGetValue(req.getParameter("hidNoNPWRD"));
            String strNamaPejabat = jvc.fnGetValue(req.getParameter("hidNamaPejabat"));
            String strJabatanPejabat = jvc.fnGetValue(req.getParameter("hidJabatanPejabat"));
            String strNIPPejabat = jvc.fnGetValue(req.getParameter("hidNIPPejabat"));
            String strTglAwal = jvc.fnGetValue(req.getParameter("hidDate1"));
            String strTglAkhir = jvc.fnGetValue(req.getParameter("hidDate2"));
            String strTahun = jvc.fnGetValue(req.getParameter("hidTahun"));
            String strNama = ((String)htGetSKPD.get("Nama")).toString();
            String strJalan = ((String)htGetSKPD.get("Jalan")).toString();
            String strNo = ((String)htGetSKPD.get("No")).toString();
            String strRT = ((String)htGetSKPD.get("RT")).toString();
            String strRW = ((String)htGetSKPD.get("RW")).toString();
            String strRK = "";
            String strKodePos = ((String)htGetSKPD.get("KodePos")).toString();
            String strPemilik = ((String)htGetSKPD.get("Pemilik")).toString();
            String strKabupaten = ((String)htGetSKPD.get("Kabupaten")).toString();
            String strKecamatan = ((String)htGetSKPD.get("Kecamatan")).toString();
            String strKelurahan = ((String)htGetSKPD.get("Kelurahan")).toString();
            String strTotal = jvc.fnGetValue(req.getParameter("jmlTotal"));
            String strSubTotal = jvc.fnGetValue(req.getParameter("jmlSubTotal"));
            String strSubTerbilang = jvc.fnGetValue(req.getParameter("hidSubTerbilang"));
            jvc.fnPrint((new StringBuilder("strSubTerbilang: ")).append(strSubTerbilang).toString());
            String strTerbilang = jvc.fnGetValue(req.getParameter("hidTerbilang"));
            jvc.fnPrint((new StringBuilder("strTerbilang: ")).append(strTerbilang).toString());
            String strTglJthTempo = jvc.fnGetValue(req.getParameter("hidTglJthTempo"));
            SimpleDateFormat sdfTglJthTempo = new SimpleDateFormat("dd/MM/yyyy");
            Date dtTglJthTempo = new Date();
            try
            {
                dtTglJthTempo = sdfTglJthTempo.parse(strTglJthTempo);
            }
            catch(Exception exp)
            {
                jvc.fnPrint((new StringBuilder("Exception: ")).append(exp.getMessage()).toString());
            }
            SimpleDateFormat sdfTglJthTempo1 = new SimpleDateFormat("dd MMMM yyyy");
            strTglJthTempo = sdfTglJthTempo1.format(dtTglJthTempo);
            String strNamaBU = jvc.fnGetValue(req.getParameter("hidNamaBU"));
            String strAlamatBU = jvc.fnGetValue(req.getParameter("hidAlamatBU"));
            String strBunga = jvc.fnGetValue(req.getParameter("txtBunga"));
            String strKenaikan = jvc.fnGetValue(req.getParameter("txtKenaikan"));
            String strPembayaran = jvc.fnGetValue(req.getParameter("hidUraian"));
            String strPekerjaan = jvc.fnGetValue(req.getParameter("hidJnsPekerjaan"));
            Map parameters = new HashMap();
            parameters.put("pNoUrut", strNoSKPD);
            parameters.put("pKdPemilik", strKdPemilik);
            parameters.put("pNPWPD", strNoNPWPD);
            parameters.put("pNPWRD", strNoNPWRD);
            parameters.put("pNamaPejabat", strNamaPejabat);
            parameters.put("pJabatanPejabat", strJabatanPejabat);
            parameters.put("pNIPPejabat", strNIPPejabat);
            parameters.put("pTglAwal", strTglAwal);
            parameters.put("pTglAkhir", strTglAkhir);
            parameters.put("pTahun", strTahun);
            parameters.put("pJmlUang", strTotal);
            parameters.put("pSubTerbilang", strSubTerbilang);
            parameters.put("pTerbilang", strTerbilang);
            parameters.put("pTglJthTempo", strTglJthTempo);
            parameters.put("pBunga", strBunga);
            parameters.put("pKenaikan", strKenaikan);
            parameters.put("pNamaPerusahaan", strNamaBU);
            parameters.put("pAlamatPerusahaan", strAlamatBU);
            parameters.put("pPembayaran", strPembayaran);
            parameters.put("pPekerjaan", strPekerjaan);
            parameters.put("pTitle1", "SURAT KETETAPAN RETRIBUSI DAERAH");
            parameters.put("pTitle2", "(SKR-DAERAH)");
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
            parameters.put("Nama", strNama);
            parameters.put("Jalan", strJalan);
            parameters.put("No", strNo);
            parameters.put("RT", strRT);
            parameters.put("RW", strRW);
            parameters.put("RK", strRK);
            parameters.put("KodePos", strKodePos);
            parameters.put("Pemilik", strPemilik);
            parameters.put("Kabupaten", strKabupaten);
            parameters.put("Kecamatan", strKecamatan);
            parameters.put("Kelurahan", strKelurahan);
            Hashtable htDataPemda = new Hashtable();
            String strNamaPemda = "";
            String strNamaBidang = "";
            String strAlamatPemda = "";
            String strKotamadyaPemda = "";
            String strKodePosPemda = "";
            String strTeleponPemda = "";
            String strFacsimilePemda = "";
            if(req.getSession().getAttribute("htDataPemda") != null)
            {
                htDataPemda = (Hashtable)req.getSession().getAttribute("htDataPemda");
                int intDataPemda = htDataPemda.size();
                for(int a = 1; a <= intDataPemda; a++)
                {
                    String strArray[] = (String[])htDataPemda.get(String.valueOf(a));
                    strNamaPemda = strArray[0].toUpperCase();
                    strNamaBidang = strArray[1].toUpperCase();
                    strAlamatPemda = (new StringBuilder(String.valueOf(strArray[2]))).append(" No ").append(strArray[3]).append(", RT/RW ").append(strArray[4]).append("/").append(strArray[5]).toString();
                    strKotamadyaPemda = strArray[9];
                    strKodePosPemda = strArray[10];
                    strTeleponPemda = strArray[11];
                    strFacsimilePemda = strArray[12];
                }

            }
            parameters.put("strNamaPemda", strNamaPemda);
            parameters.put("strNamaBidang", strNamaBidang);
            parameters.put("strAlamatPemda", strAlamatPemda);
            parameters.put("strKotamadyaPemda", strKotamadyaPemda);
            parameters.put("strKodePosPemda", strKodePosPemda);
            parameters.put("strTeleponPemda", strTeleponPemda);
            parameters.put("strFacsimilePemda", strFacsimilePemda);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            JasperViewer.viewReport(print);
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
    jvGeneralBusiness jvg;
    jvCommonClass jvc;
    String JRXML_LOCAL_PATH;
    String REPORT_LOCAL_PATH;
    String OUTPUT_LOCAL_PATH;
}
