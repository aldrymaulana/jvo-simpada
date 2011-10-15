/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jvo.simpada.serv;

import jvo.simpada.common.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import net.sf.jasperreports.engine.*;

public class srvPendaftaran extends HttpServlet {

    public srvPendaftaran() {
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
        ifcPendaftaran ifcp;
        String txtKdNPWP;
        String txtNPWPD;
        String txtWilNPWP;
        String strKdPemilik;
        Hashtable htSI;
        String strToday;
        response.setContentType("text/html;charset=UTF-8");
        request.getSession().removeAttribute("htSI");
        request.getSession().removeAttribute("ifcp");
        request.getSession().removeAttribute("MSG");
        strMode = "0";
        if (request.getParameter("mode") != null) {
            strMode = request.getParameter("mode").toString();
        }
        String strIfFrame = "ifPendaftaran";
        request.getSession().setAttribute("strIfFrame", strIfFrame);
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
        Hashtable htKabupaten = jvg.fnGetKabupaten();
        request.getSession().setAttribute("htKabupaten", htKabupaten);
        Hashtable htKecamatan = jvg.fnGetKecamatan();
        request.getSession().setAttribute("htKecamatan", htKecamatan);
        Hashtable htKelurahan = jvg.fnGetKelurahan();
        request.getSession().setAttribute("htKelurahan", htKelurahan);
        ifcp = new ifcPendaftaran();
        String txtNoForm = jvc.fnGetValue(request.getParameter("txtNoForm"));
        String txtNamaBU = jvc.fnGetValue(request.getParameter("txtNamaBU"));
        String txtJalanBU = jvc.fnGetValue(request.getParameter("txtJalanBU"));
        String txtNoBU = jvc.fnGetValue(request.getParameter("txtNoBU"));
        String txtRTBU = jvc.fnGetValue(request.getParameter("txtRTBU"));
        String txtRWBU = jvc.fnGetValue(request.getParameter("txtRWBU"));
        String txtRKBU = jvc.fnGetValue(request.getParameter("txtRKBU"));
        String txtTelpBU = jvc.fnGetValue(request.getParameter("txtTelpBU"));
        String txtKdPosBU = jvc.fnGetValue(request.getParameter("txtKdPosBU"));
        String hidKelurahanBU = jvc.fnGetValue(request.getParameter("hidKelurahanBU"));
        String hidKecamatanBU = jvc.fnGetValue(request.getParameter("hidKecamatanBU"));
        String hidKabupatenBU = jvc.fnGetValue(request.getParameter("hidKabupatenBU"));
        String txtModal = jvc.fnGetValue(request.getParameter("txtModal"));
        ifcp.setStrNoForm(txtNoForm);
        ifcp.setStrNamaBU(txtNamaBU);
        ifcp.setStrJalanBU(txtJalanBU);
        ifcp.setStrNoBU(txtNoBU);
        ifcp.setStrRTBU(txtRTBU);
        ifcp.setStrRWBU(txtRWBU);
        ifcp.setStrRKBU(txtRKBU);
        ifcp.setStrTelpBU(txtTelpBU);
        ifcp.setStrKdPosBU(txtKdPosBU);
        ifcp.setStrKelurahanBU(hidKelurahanBU);
        ifcp.setStrKecamatanBU(hidKecamatanBU);
        ifcp.setStrKabupatenBU(hidKabupatenBU);
        ifcp.setStrModal(txtModal);
        String strCekAlamat = jvc.fnGetValue(request.getParameter("chkAlamat"));
        jvc.fnPrint((new StringBuilder("strCekAlamat: ")).append(strCekAlamat).toString());
        String txtNama = jvc.fnGetValue(request.getParameter("txtNama"));
        String txtJabatan = jvc.fnGetValue(request.getParameter("txtJabatan"));
        String txtJalan = jvc.fnGetValue(request.getParameter("txtJalan"));
        String txtNo = jvc.fnGetValue(request.getParameter("txtNo"));
        String txtRT = jvc.fnGetValue(request.getParameter("txtRT"));
        String txtRW = jvc.fnGetValue(request.getParameter("txtRW"));
        String txtRK = jvc.fnGetValue(request.getParameter("txtRK"));
        String txtTelp = jvc.fnGetValue(request.getParameter("txtTelp"));
        String txtKdPos = jvc.fnGetValue(request.getParameter("txtKdPos"));
        txtKdNPWP = jvc.fnGetValue(request.getParameter("txtKdNPWP"));
        txtNPWPD = jvc.fnGetValue(request.getParameter("txtNPWPD"));
        txtWilNPWP = jvc.fnGetValue(request.getParameter("txtWilNPWP"));
        String txtKdNPWR = jvc.fnGetValue(request.getParameter("txtKdNPWR"));
        String txtNPWRD = jvc.fnGetValue(request.getParameter("txtNPWRD"));
        String txtWilNPWR = jvc.fnGetValue(request.getParameter("txtWilNPWR"));
        String hidKelurahan = jvc.fnGetValue(request.getParameter("hidKelurahan"));
        String hidKecamatan = jvc.fnGetValue(request.getParameter("hidKecamatan"));
        String hidKabupaten = jvc.fnGetValue(request.getParameter("hidKabupaten"));
        ifcp.setStrNama(txtNama);
        ifcp.setStrJabatan(txtJabatan);
        ifcp.setStrJalan(txtJalan);
        ifcp.setStrNo(txtNo);
        ifcp.setStrRT(txtRT);
        ifcp.setStrRW(txtRW);
        ifcp.setStrRK(txtRK);
        ifcp.setStrTelp(txtTelp);
        ifcp.setStrKdPos(txtKdPos);
        ifcp.setStrKdNPWP(txtKdNPWP);
        ifcp.setStrNPWPD(txtNPWPD);
        ifcp.setStrWilNPWP(txtWilNPWP);
        ifcp.setStrKdNPWR(txtKdNPWR);
        ifcp.setStrNPWRD(txtNPWRD);
        ifcp.setStrWilNPWR(txtWilNPWR);
        ifcp.setStrKelurahan(hidKelurahan);
        ifcp.setStrKecamatan(hidKecamatan);
        ifcp.setStrKabupaten(hidKabupaten);
        String hidBidUsaha = jvc.fnGetValue(request.getParameter("hidBidUsaha"));
        ifcp.setStrBidUsaha(hidBidUsaha);
        String hidPajak = jvc.fnGetValue(request.getParameter("hidPajak"));
        ifcp.setStrPajak(hidPajak);
        String hidRetribusi = jvc.fnGetValue(request.getParameter("hidRetribusi"));
        ifcp.setStrRetribusi(hidRetribusi);
        strKdPemilik = jvc.fnGetValue(request.getParameter("hidKdPemilik"));
        ifcp.setStrKodePemilik(strKdPemilik);
        request.getSession().setAttribute("ifcp", ifcp);
        htSI = new Hashtable();
        String strJmlSI0 = jvc.fnGetValue(request.getParameter("hidJmlSI"));
        int b = 1;
        if (strJmlSI0.trim().length() > 0) {
            int intJmlSI0 = Integer.parseInt(strJmlSI0) + 1;
            for (int a = 1; a <= intJmlSI0; a++) {
                String strArrSI[] = new String[3];
                strArrSI[0] = jvc.fnGetValue(request.getParameter((new StringBuilder("txtJnsIjin_")).append(a).toString()));
                strArrSI[1] = jvc.fnGetValue(request.getParameter((new StringBuilder("txtNoIjin_")).append(a).toString()));
                strArrSI[2] = jvc.fnGetValue(request.getParameter((new StringBuilder("txtTglIjin_")).append(a).toString()));
                if (strArrSI[0].trim().length() > 0) {
                    htSI.put(String.valueOf(b), strArrSI);
                    b++;
                }
            }

            request.getSession().setAttribute("htSI", htSI);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        Date todayDate = new Date();
        strToday = sdf.format(todayDate);
//        JVM INSTR tableswitch 0 6: default 2720
        //                   0 1532
        //                   1 1764
        //                   2 1843
        //                   3 2089
        //                   4 2173
        //                   5 2516
        //                   6 2595;
//           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
        switch (Integer.parseInt(strMode)) {
            case 0:
                try {
                    ifcp = new ifcPendaftaran();
                    request.getSession().removeAttribute("ifcp");
                    request.getSession().removeAttribute("htSI");
                    request.getSession().removeAttribute("MSG");
                    String strSource = "pendaftaran1.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    String strNoForm = jvg.fnAmbilNoForm();
                    if (!strNoForm.equals("0000")) {
                        strNoForm = jvc.fnLRPad("LPAD", String.valueOf(Integer.parseInt(jvc.fnRemoveLPAD(strNoForm)) + 1), "0", 4);
                    } else {
                        strNoForm = "0001";
                    }
                    ifcp.setStrNoForm((new StringBuilder(String.valueOf(strToday))).append(strNoForm).toString());
                    request.getSession().setAttribute("ifcp", ifcp);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    jvCommon.fnWriteLog("srvPendaftaran, case 0", (new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 1:
                try {
                    String strJmlSI = request.getParameter("hidJmlSI").toString();
                    int intJmlSI = Integer.parseInt(strJmlSI) + 1;
                    request.getSession().setAttribute("intJmlSI", Integer.valueOf(intJmlSI));
                    response.sendRedirect("pendaftaran1.jsp");
                    return;
                } catch (Exception exp) {
                    jvCommon.fnWriteLog("srvPendaftaran, case 1", (new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 2:
                try {
                    String strMSG = "Data Gagal Disimpan!";
                    String strSimpan = "1";
                    String strModeSimpan = request.getParameter("hidMSG");
                    if (strModeSimpan.equalsIgnoreCase("Perubahan Data")) {
                        strSimpan = jvg.fnHapusDetail(strKdPemilik);
                    }
                    String strKodePemilik = "0";
                    String strSimpanBU = "0";
                    String strSimpanSI = "0";
                    if (strSimpan.equals("1")) {
                        strKodePemilik = jvg.fnSimpanPemilik(ifcp);
                        if (!strKodePemilik.equals("0")) {
                            strSimpanBU = jvg.fnSimpanBU(ifcp, strKodePemilik);
                            if (!strSimpanBU.equals("0")) {
                                strSimpanSI = jvg.fnSimpanSI(htSI, strKodePemilik);
                                if (!strSimpanSI.equals("0")) {
                                    strMSG = "Data Tersimpan!";
                                }
                            } else {
                                String strHapusPemilik = jvg.fnHapusDetail(strKodePemilik);
                                strMSG = "Data NPWP yang dimasukkan telah terdaftar. Harap masukkan nomor lain!";
                            }
                        }
                    }
                    ifcp.setStrKodePemilik(strKodePemilik);
                    request.getSession().setAttribute("htSI", htSI);
                    request.getSession().setAttribute("ifcp", ifcp);
                    request.getSession().setAttribute("MSG", strMSG);
                    response.sendRedirect("pendaftaran1.jsp");
                    return;
                } catch (Exception exp) {
                    jvCommon.fnWriteLog("srvPendaftaran, case 2", (new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 3:
                try {
                    String strSource = "pendaftaran2.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    jvCommon.fnWriteLog("srvPendaftaran, case 3", (new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 4:
                String strNPWPD = request.getParameter("txtNPWPD").toString();
                String strNamaBU = request.getParameter("txtNamaBU").toString();
                String strNama = request.getParameter("txtNama").toString();
                Hashtable htDetail = new Hashtable();
                htDetail = jvg.fnGetDetail(strNPWPD, strNamaBU, strNama);
                ifcPendaftaran ifcpResult = new ifcPendaftaran();
                ifcpResult = (ifcPendaftaran) htDetail.get("ifcp");
                if (ifcpResult.getStrKodePemilik() != null) {
                    String strSource = "pendaftaran1.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    Hashtable htSIResult = new Hashtable();
                    htSIResult = (Hashtable) htDetail.get("htSI");
                    int intJmlSI = htSIResult.size();
                    request.getSession().setAttribute("intJmlSI", Integer.valueOf(intJmlSI));
                    request.getSession().setAttribute("htSI", htSIResult);
                    request.getSession().setAttribute("ifcp", ifcpResult);
                    request.getSession().setAttribute("MSG", "Perubahan Data");
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                }
                try {
                    String strSource = "pendaftaran2.jsp";
                    request.getSession().setAttribute("strSource", strSource);
                    request.getSession().setAttribute("MSG", "Data Tidak Ditemukan!");
                    response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                    return;
                } catch (Exception exp) {
                    jvCommon.fnWriteLog("srvPendaftaran, case 4", (new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 5:
                try {
                    String strJmlSI = request.getParameter("hidJmlSI").toString();
                    int intJmlSI = Integer.parseInt(strJmlSI) - 1;
                    request.getSession().setAttribute("intJmlSI", Integer.valueOf(intJmlSI));
                    response.sendRedirect("pendaftaran1.jsp");
                    return;
                } catch (Exception exp) {
                    jvCommon.fnWriteLog("srvPendaftaran, case 5", (new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;

            case 6:
                try {
                    jvc.fnPrint("Case 6, Cetak Form");
                    String strNoNPWPD = (new StringBuilder(String.valueOf(txtKdNPWP))).append(txtNPWPD).append(txtWilNPWP).toString();
                    jvc.fnPrint((new StringBuilder("strKdPemilik: ")).append(strKdPemilik).toString());
                    jvc.fnPrint((new StringBuilder("strNoNPWPD: ")).append(strNoNPWPD).toString());
                    createReport2(request, response, strKdPemilik, strNoNPWPD);
                    return;
                } catch (Exception exp) {
                    jvCommon.fnWriteLog("srvPendaftaran, case 6", (new StringBuilder("Exception: ")).append(exp).toString());
                }
                return;
        }
    }

    protected void createReport2(HttpServletRequest req, HttpServletResponse resp, String strKdPemilik, String strNoNPWPD)
            throws ServletException, IOException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:odbc:Simpada_v01");
            String strFileJRXML = "report1.jrxml";
            String strFileJasper = (new StringBuilder(String.valueOf(JRXML_LOCAL_PATH))).append(strFileJRXML).toString();
            String NmPemda = req.getParameter("hidNmPemda").toString();
            String NmDinas = req.getParameter("hidNmDinas").toString();
            String AlamatPemda = req.getParameter("hidAlamatPemda").toString();
            String KelurahanPemda = req.getParameter("hidKelurahanPemda").toString();
            String KecamatanPemda = req.getParameter("hidKecamatanPemda").toString();
            String KotamadyaPemda = req.getParameter("hidKotamadyaPemda").toString();
            String KodePosPemda = req.getParameter("hidKodePosPemda").toString();
            Map parameters = new HashMap();
            parameters.put("KdPemilik", strKdPemilik);
            parameters.put("NmPemda", NmPemda);
            parameters.put("NmDinas", NmDinas);
            parameters.put("AlamatPemda", AlamatPemda);
            parameters.put("KelurahanPemda", KelurahanPemda);
            parameters.put("KecamatanPemda", KecamatanPemda);
            parameters.put("KotamadyaPemda", KotamadyaPemda);
            parameters.put("KodePosPemda", KodePosPemda);
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date todayDate = new Date();
            String strToday = sdf.format(todayDate);
            net.sf.jasperreports.engine.JasperReport jasperReport = JasperCompileManager.compileReport(strFileJasper);
            net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, con);
            String strFilePDF = (new StringBuilder("DAFTAR_")).append(strNoNPWPD.replace('.', '_')).append("_").append(strToday).append(".pdf").toString();
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
