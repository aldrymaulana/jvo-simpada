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
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;

public class srvLaporan extends HttpServlet {

    public srvLaporan() {
        jvg = new jvGeneral();
        jvc = new jvCommon();
        JRXML_LOCAL_PATH = jvCommon.fnGetProperty("JRXML_LOCAL_PATH").toString();
        REPORT_LOCAL_PATH = jvCommon.fnGetProperty("REPORT_LOCAL_PATH").toString();
        OUTPUT_LOCAL_PATH = jvCommon.fnGetProperty("OUTPUT_LOCAL_PATH").toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
        String strMode = "0";
        if (request.getParameter("mode") != null) {
            strMode = request.getParameter("mode").toString();
        }
        String strWidth = request.getParameter("hidWidth").toString();
        request.getSession().setAttribute("strWidth", strWidth);
        String strHeight = request.getParameter("hidHeight").toString();
        request.getSession().setAttribute("strHeight", strHeight);

        String strIdxLaporan = "0";
        if (request.getParameter("hidTypeLap") != null) {
            strIdxLaporan = request.getParameter("hidTypeLap").toString();
        } else {
            if (request.getSession().getAttribute("strIdxLaporan") != null) {
                strIdxLaporan = request.getSession().getAttribute("strIdxLaporan").toString();
            } 
        }
        request.getSession().setAttribute("strIdxLaporan", strIdxLaporan);

        String strNamaBendahara = "";
        String strNIPBendahara = "";
        String strNamaKabid = "";
        String strNIPKabid = "";
        Hashtable htInfoPejabat1 = jvg.fnGetInfoPejabat();
        int intHtInfoPejabat = htInfoPejabat1.size();
        for (int a = 1; a <= intHtInfoPejabat; a++) {
            String strArray[] = (String[]) htInfoPejabat1.get(String.valueOf(a));
            if (strArray[0].equals("31100")) {
                strNamaBendahara = strArray[1];
                strNIPBendahara = strArray[2];
            } else if (strArray[0].equals("31000")) {
                strNamaKabid = strArray[1];
                strNIPKabid = strArray[2];
            }
        }
        request.getSession().setAttribute("strNamaBendahara", strNamaBendahara);
        request.getSession().setAttribute("strNIPBendahara", strNIPBendahara);
        request.getSession().setAttribute("strNamaKabid", strNamaKabid);
        request.getSession().setAttribute("strNIPKabid", strNIPKabid);

        Hashtable htPajak = jvg.fnGetPajak();
        request.getSession().setAttribute("htPajak", htPajak);

        switch (Integer.parseInt(strMode)) {
            case 0: // menu laporan
                try {
                    String strIfFrame = "ifPelaporan";
                    request.getSession().setAttribute("strIfFrame", strIfFrame);
                    String strSource = "pelaporan1.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 1: // create laporan
                try {
                    String strTypeLap = request.getParameter("hidTypeLap").toString();
                    String strTglAwal = "";
                    String strTglAkhir = "";
                    if (!strTypeLap.equals("6")) {
                        strTglAwal = request.getParameter("txtTglAwal").toString();
                        strTglAkhir = request.getParameter("txtTglAkhir").toString();
                    }
                    String strSource = "";
                    String strKetPajak = "";
                    String strJnsPajak = request.getParameter("hidJnsPajak").toString();
                    if (strJnsPajak.trim().length() > 0 && htPajak.size() > 0) {
                        for (int qq = 1; qq <= htPajak.size(); qq++) {
                            String strPajak[] = (String[]) htPajak.get(String.valueOf(qq));
                            if (strPajak[2].equalsIgnoreCase(strJnsPajak)) {
                                strKetPajak = strPajak[1];
                            }
                        }

                    }
                    if (strTypeLap.equals("1")) {
                        String strIfFrame = "ifPelaporan1";
                        request.getSession().setAttribute("strIfFrame", strIfFrame);
                        strSource = "pelaporan0.jsp";
                        Hashtable htLapHarian = new Hashtable();
                        htLapHarian = jvg.fnGetLapHarian(strTglAwal);
                        request.getSession().setAttribute("strTglAwal", strTglAwal);
                        request.getSession().setAttribute("htLapHarian", htLapHarian);
                    } else if (strTypeLap.equals("2")) {
                        String strIfFrame = "ifPelaporan2";
                        request.getSession().setAttribute("strIfFrame", strIfFrame);
                        strSource = "pelaporan2b.jsp";
                        String strThnAnggaran = strTglAwal.substring(6, strTglAwal.length());
                        Hashtable htBKP = new Hashtable();
                        htBKP = jvg.fnGetBukuKasPembantu(strTglAwal, strTglAkhir, strThnAnggaran);
                        request.getSession().setAttribute("strTglAwal", strTglAwal);
                        request.getSession().setAttribute("strTglAkhir", strTglAkhir);
                        request.getSession().setAttribute("strThnAnggaran", strThnAnggaran);
                        request.getSession().setAttribute("htBKP", htBKP);
                    } else if (strTypeLap.equals("3")) {
                        String strIfFrame = "ifPelaporan3";
                        request.getSession().setAttribute("strIfFrame", strIfFrame);
                        strSource = "pelaporan2.jsp";
                        Hashtable htGetBukuKasUmum = new Hashtable();
                        htGetBukuKasUmum = jvg.fnGetBukuKasUmum(strTglAwal, strTglAkhir);
                        request.getSession().setAttribute("strTglAwal", strTglAwal);
                        request.getSession().setAttribute("strTglAkhir", strTglAkhir);
                        request.getSession().setAttribute("htGetBukuKasUmum", htGetBukuKasUmum);
                    } else if (strTypeLap.equals("4")) {
                        String strIfFrame = "ifPelaporan4";
                        request.getSession().setAttribute("strIfFrame", strIfFrame);
                        strSource = "pelaporan3.jsp";
                        Hashtable htFungsional = new Hashtable();
                        htFungsional = jvg.fnGetSPJFungsional();
                        request.getSession().setAttribute("htFungsional", htFungsional);
                    } else if (strTypeLap.equals("5")) {
                        String strIfFrame = "ifPelaporan5";
                        request.getSession().setAttribute("strIfFrame", strIfFrame);
                        strSource = "pelaporan4.jsp";
                        Hashtable htRkpPajak = new Hashtable();
                        htRkpPajak = jvg.fnGetRekapPajak(strTglAwal, strTglAkhir, strJnsPajak);
                        request.getSession().setAttribute("htRkpPajak", htRkpPajak);
                        request.getSession().setAttribute("strKetPajak", strKetPajak);
                        request.getSession().setAttribute("strKodePajak", strJnsPajak);
                        request.getSession().setAttribute("strTglAwal", strTglAwal);
                        request.getSession().setAttribute("strTglAkhir", strTglAkhir);
                    } else if (strTypeLap.equals("6")) {
                        String strIfFrame = "ifPelaporan6";
                        request.getSession().setAttribute("strIfFrame", strIfFrame);
                        strSource = "pelaporan5.jsp";
                        Hashtable htDfPerusahaan = new Hashtable();
                        String strIdxKolom = (request.getParameter("hidIdxKolom")==null?"0":request.getParameter("hidIdxKolom").toString());
                        String strOrder = (request.getParameter("hidOrder")==null?"down":request.getParameter("hidOrder").toString());
                        htDfPerusahaan = jvg.fnGetDfPerusahaan(strIdxKolom,strOrder);
                        request.getSession().setAttribute("htDfPerusahaan", htDfPerusahaan);
                    }

                    request.getSession().setAttribute("strSource", strSource);
                    Hashtable htInfoPejabat = jvg.fnGetInfoPejabat();
                    request.getSession().setAttribute("htInfoPejabat", htInfoPejabat);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 2: // cetak laporan
                try {
                    System.out.println("Case 2, Cetak Laporan Buku Kas Umum");
                    createReport2(request, response);
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 3: // '\003'
                try {
                    System.out.println("Case 3, Cetak Laporan Buku Kas Pembantu");
                    createReport3(request, response);
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 4: // '\004'
                try {
                    System.out.println("Case 4, Cetak Rekapitulasi Penerimaan Harian");
                    createReport4(request, response);
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 5: // '\005'
                try {
                    System.out.println("Case 5, Cetak SPJ Pendapatan - Fungsional");
                    createReport5(request, response);
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 6: // '\006'
                try {
                    System.out.println("Case 6, Cetak Rekap Penetapan Pajak");
                    createReport6(request, response);
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 7: // '\007'
                try {
                    System.out.println("Case 7, Cetak Daftar Perusahaan");
                    createReport7(request, response);
                    return;
                } catch (Exception exp) {
                    System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;
        }
    }

    protected void createReport2(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            String strFileJRXML = "report2.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            String strTglAwal = req.getSession().getAttribute("strTglAwal").toString();
            String strDtDate3 = strTglAwal.substring(0, 2);
            String strDtMonth3 = strTglAwal.substring(3, 5);
            String strDtYear3 = strTglAwal.substring(6, strTglAwal.length());
            strTglAwal = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
            String strTglAkhir = req.getSession().getAttribute("strTglAkhir").toString();
            String strDtDate4 = strTglAkhir.substring(0, 2);
            String strDtMonth4 = strTglAkhir.substring(3, 5);
            String strDtYear4 = strTglAkhir.substring(6, strTglAkhir.length());
            strTglAkhir = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
            String strTxtRp = req.getParameter("txtRp").toString();
            String strTxtTerbilang = req.getParameter("hidLblRp").toString();
            String strTxtTunai = req.getParameter("txtTunai").toString();
            String strTxtSurat = req.getParameter("txtSurat").toString();
            String strTxtSaldo = req.getParameter("txtSaldo").toString();
            Map parameters = new HashMap();
            parameters.put("pTglAwal", strTglAwal);
            parameters.put("pTglAkhir", strTglAkhir);
            parameters.put("pTxtRp", strTxtRp);
            parameters.put("pTxtTerbilang", strTxtTerbilang);
            parameters.put("pTxtSurat", strTxtSurat);
            parameters.put("pTxtTunai", strTxtTunai);
            parameters.put("pTxtSaldo", strTxtSaldo);
            String strNmPemda = req.getParameter("hidNamaPemda").toString();
            parameters.put("NmDaerah", strNmPemda);
            String strNmBidang = req.getParameter("hidNamaBidang").toString();
            parameters.put("NmUnitKerja", strNmBidang);
            String strNmPejabat = req.getParameter("hidNamaPejabat").toString();
            parameters.put("NmPemimpin", strNmPejabat);
            String strJabatanPejabat = req.getParameter("hidJabatanPejabat").toString();
            parameters.put("JabatanPemimpin", strJabatanPejabat);
            String strNipPejabat = req.getParameter("hidNIPPejabat").toString();
            parameters.put("NipPemimpin", strNipPejabat);
            String strBulanBuku = req.getParameter("hidBulanBuku").toString();
            parameters.put("BulanBuku", strBulanBuku);
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

            parameters.put("NmBendahara", strNamaBendahara);
            parameters.put("NipBendahara", strNIPBendahara);
            String strKelurahanPemda = req.getParameter("hidKelurahanPemda").toString();
            parameters.put("NmKelurahan", strKelurahanPemda);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            String strFilePDF = (new StringBuilder("BKU_")).append(strToday).append(".pdf").toString();
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

    protected void createReport3(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            String strFileJRXML = "report7.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            String strTglAwal = req.getSession().getAttribute("strTglAwal").toString();
            String strDtDate3 = strTglAwal.substring(0, 2);
            String strDtMonth3 = strTglAwal.substring(3, 5);
            String strDtYear3 = strTglAwal.substring(6, strTglAwal.length());
            strTglAwal = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
            String strTglAkhir = req.getSession().getAttribute("strTglAkhir").toString();
            String strDtDate4 = strTglAkhir.substring(0, 2);
            String strDtMonth4 = strTglAkhir.substring(3, 5);
            String strDtYear4 = strTglAkhir.substring(6, strTglAkhir.length());
            strTglAkhir = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
            String strThnAnggaran = req.getSession().getAttribute("strThnAnggaran").toString();
            Map parameters = new HashMap();
            parameters.put("pTglAwal", strTglAwal);
            parameters.put("pTglAkhir", strTglAkhir);
            parameters.put("pThnAnggaran", strThnAnggaran);
            String strNmPemda = req.getParameter("hidNamaPemda").toString();
            parameters.put("pNmPemda", strNmPemda);
            String strNmBidang = req.getParameter("hidNamaBidang").toString();
            parameters.put("NmUnitKerja", strNmBidang);
            String strNmPejabat = req.getParameter("hidNamaPejabat").toString();
            parameters.put("NmPemimpin", strNmPejabat);
            String strJabatanPejabat = req.getParameter("hidJabatanPejabat").toString();
            parameters.put("JabatanPemimpin", strJabatanPejabat);
            String strNipPejabat = req.getParameter("hidNIPPejabat").toString();
            parameters.put("NipPemimpin", strNipPejabat);
            String strBulanBuku = req.getParameter("hidBulanBuku").toString();
            parameters.put("BulanBuku", strBulanBuku);
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

            parameters.put("NmBendahara", strNamaBendahara);
            parameters.put("NipBendahara", strNIPBendahara);
            String strKelurahanPemda = req.getParameter("hidKelurahanPemda").toString();
            parameters.put("NmKelurahan", strKelurahanPemda);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            String strFilePDF = (new StringBuilder("BKP_")).append(strToday).append(".pdf").toString();
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

    protected void createReport4(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            String strFileJRXML = "report6.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            String strSKPD = req.getSession().getAttribute("strTglAwal").toString();
            String strDtDate3 = strSKPD.substring(0, 2);
            String strDtMonth3 = strSKPD.substring(3, 5);
            String strDtYear3 = strSKPD.substring(6, strSKPD.length());
            strSKPD = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
            Map parameters = new HashMap();
            parameters.put("pTglSKPD", strSKPD);
            String strNmPemda = req.getParameter("hidNamaPemda").toString();
            parameters.put("pNmPemda", strNmPemda);
            String strNmBidang = req.getParameter("hidNamaBidang").toString();
            parameters.put("NmUnitKerja", strNmBidang);
            String strNmPejabat = req.getParameter("hidNamaPejabat").toString();
            parameters.put("NmPemimpin", strNmPejabat);
            String strJabatanPejabat = req.getParameter("hidJabatanPejabat").toString();
            parameters.put("JabatanPemimpin", strJabatanPejabat);
            String strNipPejabat = req.getParameter("hidNIPPejabat").toString();
            parameters.put("NipPemimpin", strNipPejabat);
            String strBulanBuku = req.getParameter("hidBulanBuku").toString();
            parameters.put("BulanBuku", strBulanBuku);
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

            parameters.put("NmBendahara", strNamaBendahara);
            parameters.put("NipBendahara", strNIPBendahara);
            String strKelurahanPemda = req.getParameter("hidKelurahanPemda").toString();
            parameters.put("NmKelurahan", strKelurahanPemda);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            String strFilePDF = (new StringBuilder("RKP_")).append(strToday).append(".pdf").toString();
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

    protected void createReport5(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            Calendar cal = new GregorianCalendar();
            int intDayOfMonth = cal.get(5);
            int intDayOfWeek = cal.get(7);
            int intMonth = cal.get(2);
            int intYear = cal.get(1);
            Calendar cal2 = new GregorianCalendar(intYear, intMonth, 1);
            int intMaxDay2 = cal2.getActualMaximum(5);
            String strDate1 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append("01").append("/").append(intYear).toString();
            String strDate2 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append(intMaxDay2).append("/").append(intYear).toString();
            int intBulanLalu = intMonth - 1;
            if (intBulanLalu < 0) {
                intBulanLalu = 11;
                intYear--;
            }
            String strDate1Y = (new StringBuilder("01/01/")).append(intYear).toString();
            Calendar cal3 = new GregorianCalendar(intYear, intBulanLalu, 1);
            int intDayOfLastMonth = cal3.getActualMaximum(5);
            String strDate2Y = (new StringBuilder(String.valueOf(intDayOfLastMonth))).append("/").append(jvc.fnLRPad("LPAD", String.valueOf(intBulanLalu + 1), "0", 2)).append("/").append(intYear).toString();
            String strFileJRXML = "report8.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            Map parameters = new HashMap();
            parameters.put("pTglAwalTahun", strDate1Y);
            parameters.put("pTglAkhirTahun", strDate2Y);
            parameters.put("pTglAwalBulan", strDate1);
            parameters.put("pTglAkhirBulan", strDate2);
            parameters.put("pThnAnggaran", String.valueOf(cal.get(1)));
            String strNmPemda = req.getParameter("hidNamaPemda").toString();
            parameters.put("pNmPemda", strNmPemda);
            String strNmBidang = req.getParameter("hidNamaBidang").toString();
            parameters.put("NmUnitKerja", strNmBidang);
            String strNmPejabat = req.getParameter("hidNamaPejabat").toString();
            parameters.put("NmPemimpin", strNmPejabat);
            String strJabatanPejabat = req.getParameter("hidJabatanPejabat").toString();
            parameters.put("JabatanPemimpin", strJabatanPejabat);
            String strNipPejabat = req.getParameter("hidNIPPejabat").toString();
            parameters.put("NipPemimpin", strNipPejabat);
            String strBulanBuku = req.getParameter("hidBulanBuku").toString();
            parameters.put("BulanBuku", strBulanBuku);
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

            parameters.put("NmBendahara", strNamaBendahara);
            parameters.put("NipBendahara", strNIPBendahara);
            String strKelurahanPemda = req.getParameter("hidKelurahanPemda").toString();
            parameters.put("NmKelurahan", strKelurahanPemda);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            String strFilePDF = (new StringBuilder("SPJF_")).append(strToday).append(".pdf").toString();
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

    protected void createReport6(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            Calendar cal = new GregorianCalendar();
            int intDayOfMonth = cal.get(5);
            int intDayOfWeek = cal.get(7);
            int intMonth = cal.get(2);
            int intYear = cal.get(1);
            Calendar cal2 = new GregorianCalendar(intYear, intMonth, 1);
            int intMaxDay2 = cal2.getActualMaximum(5);
            String strDate1 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append("01").append("/").append(intYear).toString();
            String strDate2 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append(intMaxDay2).append("/").append(intYear).toString();
            String strFileJRXML = "rpt_rkpLosmen.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            Map parameters = new HashMap();
            parameters.put("pTglAwal", strDate1);
            parameters.put("pTglAkhir", strDate2);
            String strNmPemda = req.getParameter("hidNamaPemda").toString();
            parameters.put("pNmPemda", strNmPemda);
            String strNmBidang = req.getParameter("hidNamaBidang").toString();
            parameters.put("pNmDinas", strNmBidang);
            String strNmPejabat = req.getParameter("hidNamaPejabat").toString();
            parameters.put("NmPemimpin", strNmPejabat);
            String strJabatanPejabat = req.getParameter("hidJabatanPejabat").toString();
            parameters.put("JabatanPemimpin", strJabatanPejabat);
            String strNipPejabat = req.getParameter("hidNIPPejabat").toString();
            parameters.put("NipPemimpin", strNipPejabat);
            String strBulanBuku = req.getParameter("hidBulanBuku").toString();
            parameters.put("pBulan", strBulanBuku);
            String strNamaBendahara = "";
            String strNIPBendahara = "";
            String strNamaKabid = "";
            String strNIPKabid = "";
            Hashtable htInfoPejabat1 = jvg.fnGetInfoPejabat();
            int intHtInfoPejabat = htInfoPejabat1.size();
            for (int a = 1; a <= intHtInfoPejabat; a++) {
                String strArray[] = (String[]) htInfoPejabat1.get(String.valueOf(a));
                if (strArray[0].equals("31100")) {
                    strNamaBendahara = strArray[1];
                    strNIPBendahara = strArray[2];
                } else if (strArray[0].equals("31000")) {
                    strNamaKabid = strArray[1];
                    strNIPKabid = strArray[2];
                }
            }

            parameters.put("NmBendahara", strNamaBendahara);
            parameters.put("NipBendahara", strNIPBendahara);
            parameters.put("NmKabidPendapatan", strNamaKabid);
            parameters.put("NipKabidPendapatan", strNIPKabid);
            String strKelurahanPemda = req.getParameter("hidKelurahanPemda").toString();
            parameters.put("NmKelurahan", strKelurahanPemda);
            String strAlamatPemda = req.getParameter("hidAlamatPemda").toString();
            parameters.put("pAlamatPemda", strAlamatPemda);
            String strKodePajak = req.getParameter("hidKodePajak").toString();
            parameters.put("pKodePajak", strKodePajak);
            String strJenisPajak = req.getParameter("hidKetPajak").toString();
            parameters.put("pJnsPajak", strJenisPajak);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            String strFilePDF = (new StringBuilder("RPT_PENETAPAN_")).append(strJenisPajak).append("_").append(strToday).append(".pdf").toString();
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

    protected void createReport7(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            Calendar cal = new GregorianCalendar();
            int intDayOfMonth = cal.get(5);
            int intDayOfWeek = cal.get(7);
            int intMonth = cal.get(2);
            int intYear = cal.get(1);
            Calendar cal2 = new GregorianCalendar(intYear, intMonth, 1);
            int intMaxDay2 = cal2.getActualMaximum(5);
            String strDate1 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append("01").append("/").append(intYear).toString();
            String strDate2 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append(intMaxDay2).append("/").append(intYear).toString();
            String strFileJRXML = "rpt_dtPersh.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            Map parameters = new HashMap();
            parameters.put("pTglAwal", strDate1);
            parameters.put("pTglAkhir", strDate2);
            String strNmPemda = req.getParameter("hidNamaPemda").toString();
            parameters.put("pNmPemda", strNmPemda);
            String strNmBidang = req.getParameter("hidNamaBidang").toString();
            parameters.put("pNmDinas", strNmBidang);
            String strNmPejabat = req.getParameter("hidNamaPejabat").toString();
            parameters.put("NmPemimpin", strNmPejabat);
            String strJabatanPejabat = req.getParameter("hidJabatanPejabat").toString();
            parameters.put("JabatanPemimpin", strJabatanPejabat);
            String strNipPejabat = req.getParameter("hidNIPPejabat").toString();
            parameters.put("NipPemimpin", strNipPejabat);
            String strBulanBuku = req.getParameter("hidBulanBuku").toString();
            parameters.put("pBulan", strBulanBuku);
            String strNamaBendahara = "";
            String strNIPBendahara = "";
            String strNamaKabid = "";
            String strNIPKabid = "";
            Hashtable htInfoPejabat1 = jvg.fnGetInfoPejabat();
            int intHtInfoPejabat = htInfoPejabat1.size();
            for (int a = 1; a <= intHtInfoPejabat; a++) {
                String strArray[] = (String[]) htInfoPejabat1.get(String.valueOf(a));
                if (strArray[0].equals("31100")) {
                    strNamaBendahara = strArray[1];
                    strNIPBendahara = strArray[2];
                } else if (strArray[0].equals("31000")) {
                    strNamaKabid = strArray[1];
                    strNIPKabid = strArray[2];
                }
            }

            jvc.fnPrint("cek 1");
            parameters.put("NmBendahara", strNamaBendahara);
            parameters.put("NipBendahara", strNIPBendahara);
            parameters.put("NmKabidPendapatan", strNamaKabid);
            parameters.put("NipKabidPendapatan", strNIPKabid);
            jvc.fnPrint("cek 2");
            String strKelurahanPemda = req.getParameter("hidKelurahanPemda").toString();
            parameters.put("NmKelurahan", strKelurahanPemda);
            String strAlamatPemda = req.getParameter("hidAlamatPemda").toString();
            parameters.put("pAlamatPemda", strAlamatPemda);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            String strFilePDF = (new StringBuilder("DAFTAR_PERUSAHAAN_")).append(strToday).append(".pdf").toString();
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
