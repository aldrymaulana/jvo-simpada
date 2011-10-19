<%-- 
    Document   : pembayaran
    Created on : Dec 1, 2009, 8:52:23 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jvo.simpada.common.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    Hashtable htDataPemda = new Hashtable();
    String strNamaPemda = "";
    String strNamaBidang = "";
    String strAlamatPemda = "";
    String strKotamadyaPemda = "";
    String strKodePos = "";
    String strTelepon = "";
    String strFacsimile = "";
    if (request.getSession().getAttribute("htDataPemda") != null) {
        htDataPemda = (Hashtable) request.getSession().getAttribute("htDataPemda");
        int intDataPemda = htDataPemda.size();
        for (int a=1; a<=intDataPemda; a++) {
            //String sqlQuery = "SELECT 0 mPemerintah.Daerah, 1 mPemerintah.Bidang, 2 mPemerintah.Alamat, " +
            //              "3 mPemerintah.[No], 4 mPemerintah.RT, 5 mPemerintah.RW, 6 mPemerintah.RK, 7 mPemerintah.Kelurahan, " +
            //              "8 mPemerintah.Kecamatan, 9 mPemerintah.Kabupaten, 10 mPemerintah.KodePos, " +
            //              "11 mPemerintah.Telepon, 12 mPemerintah.Facs FROM mPemerintah";
            String[] strArray = (String[]) htDataPemda.get(String.valueOf(a));
            strNamaPemda = strArray[0].toUpperCase();
            strNamaBidang = strArray[1].toUpperCase();
            strAlamatPemda = strArray[2] + " No " + strArray[3] + ", RT/RW " + strArray[4] + "/" + strArray[5];
            strKotamadyaPemda = strArray[9];
            strKodePos = strArray[10];
            strTelepon = strArray[11];
            strFacsimile = strArray[12];
        }
    }
    
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy");
    java.util.Date todayDate = new java.util.Date();
    String strToday = sdf.format(todayDate);
    
    Hashtable htDataLogin = new Hashtable();
    String strNama = "";
    String strKdJabatan = "";
    String strLogin = "";
    String strPassword = "";
    String strNIP = "";
    String strJabatan = "";
    if (request.getSession().getAttribute("htInfoLogin") != null) {
        htDataLogin = (Hashtable) request.getSession().getAttribute("htInfoLogin");
        int intDataLogin = htDataLogin.size();
        for (int a=1; a<=intDataLogin; a++) {
            String[] strArray = (String[]) htDataLogin.get(String.valueOf(a));
            strNama = strArray[0];
            strKdJabatan = strArray[1];
            strLogin = strArray[2];
            strPassword = strArray[3];
            strNIP = strArray[4];
            strJabatan = strArray[5];
        }
    }
    
    String strWidth = request.getSession().getAttribute("strWidth").toString();
    String strHeight = request.getSession().getAttribute("strHeight").toString();
    String strLastElement = request.getSession().getAttribute("strLastElement").toString();
    
    int intJmlSI = 0;
    if (request.getSession().getAttribute("intJmlSI") != null) {
        intJmlSI = Integer.parseInt(request.getSession().getAttribute("intJmlSI").toString());
    }
    
    Hashtable htBidUsaha0 = new Hashtable();
    int intBidUsaha0 = 0;
    if (request.getSession().getAttribute("htBidUsaha") != null) {
        htBidUsaha0 = (Hashtable) request.getSession().getAttribute("htBidUsaha");
        intBidUsaha0 = htBidUsaha0.size();
    }
    
    Hashtable htPajak0 = new Hashtable();
    int intPajak0 = 0;
    if (request.getSession().getAttribute("htPajak") != null) {
        htPajak0 = (Hashtable) request.getSession().getAttribute("htPajak");
        intPajak0 = htPajak0.size();
    }
    
    Hashtable htRetribusi0 = new Hashtable();
    int intRetribusi0 = 0;
    if (request.getSession().getAttribute("htRetribusi") != null) {
        htRetribusi0 = (Hashtable) request.getSession().getAttribute("htRetribusi");
        intRetribusi0 = htRetribusi0.size();
    }
    
    if (intPajak0 > intRetribusi0) {
        intRetribusi0 = intPajak0;
    } else if (intPajak0 < intRetribusi0) {
        intPajak0 = intRetribusi0;
    } else {
        intPajak0 = intPajak0;
        intRetribusi0 = intRetribusi0;
    }
                                                        
    ifcPendaftaran ifcp = new ifcPendaftaran();
    if (request.getSession().getAttribute("ifcp") != null) {
        ifcp = (ifcPendaftaran) request.getSession().getAttribute("ifcp");
    }
    
    jvCommon jvc = new jvCommon();
    String strNoForm = jvc.fnGetValue(ifcp.getStrNoForm());
    String strNamaBU = jvc.fnGetValue(ifcp.getStrNamaBU());
    String strJalanBU = jvc.fnGetValue(ifcp.getStrJalanBU());
    String strNoBU = jvc.fnGetValue(ifcp.getStrNoBU());
    String strRTBU = jvc.fnGetValue(ifcp.getStrRTBU());
    String strRWBU = jvc.fnGetValue(ifcp.getStrRWBU());
    String strRKBU = jvc.fnGetValue(ifcp.getStrRKBU());
    String strTelpBU = jvc.fnGetValue(ifcp.getStrTelpBU());
    String strKdPosBU = jvc.fnGetValue(ifcp.getStrKdPosBU());
    String strKelurahanBU = jvc.fnGetValue(ifcp.getStrKelurahanBU());
    String strKecamatanBU = jvc.fnGetValue(ifcp.getStrKecamatanBU());
    String strKabupatenBU = jvc.fnGetValue(ifcp.getStrKabupatenBU());
    String strNamaPemilik = jvc.fnGetValue(ifcp.getStrNama());
    String strJabatanPemilik = jvc.fnGetValue(ifcp.getStrJabatan());
    String strJalan = jvc.fnGetValue(ifcp.getStrJalan());
    String strNo = jvc.fnGetValue(ifcp.getStrNo());
    String strRT = jvc.fnGetValue(ifcp.getStrRT());
    String strRW = jvc.fnGetValue(ifcp.getStrRW());
    String strRK = jvc.fnGetValue(ifcp.getStrRK());
    String strTelp = jvc.fnGetValue(ifcp.getStrTelp());
    String strKdPos = jvc.fnGetValue(ifcp.getStrKdPos());
    String strNPWPD = jvc.fnGetValue(ifcp.getStrNPWPD());
    String strNPWRD = jvc.fnGetValue(ifcp.getStrNPWRD());
    String strKelurahanPemilik = jvc.fnGetValue(ifcp.getStrKelurahan());
    String strKecamatanPemilik = jvc.fnGetValue(ifcp.getStrKecamatan());
    String strKabupatenPemilik = jvc.fnGetValue(ifcp.getStrKabupaten());
    String strBidangUsaha = jvc.fnGetValue(ifcp.getStrBidUsaha());
    String strJnsPajak = jvc.fnGetValue(ifcp.getStrPajak());
    String strJnsRetribusi = jvc.fnGetValue(ifcp.getStrRetribusi());
    
    String strMSG = new String();
    if (request.getSession().getAttribute("MSG") != null) {
        strMSG = request.getSession().getAttribute("MSG").toString();
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/simpada.css" rel="stylesheet">
        <script type="text/javascript" src="js/jsUtility.js"></script>
        <script type="text/javascript">
            function fnGetAllElement() {
                var vJmlElement = document.main_form.elements.length;
                for (var w=0; w<=vJmlElement-1; w++) {
                    var vElementName = '';
                    if (document.main_form.elements[w].name != null) {
                        vElementName = document.main_form.elements[w].name;
                    }
                    document.main_form.elements[w].title = vElementName;
                }
            }
            
            function fnLastElement(lastElement) {
                document.main_form.hidLastElement.value = lastElement;
            }

            function fnGotoLastElement() {
                var vLastElement = document.main_form.hidLastElement.value;
                document.getElementById(vLastElement).focus();
            }
            
            function fnBatal() {
                document.main_form.method = "post";
                document.main_form.target = "_parent"
                document.main_form.mode.value = 0;
                document.main_form.action = "srvPenetapan";
                document.main_form.submit();
            }
            
            function fnCari() {
                document.main_form.method = "post";
                document.main_form.target = "_parent"
                document.main_form.mode.value = 1;
                document.main_form.action = "srvPenetapan";
                document.main_form.submit();
            }
        </script>
    </head>
    <body onload="fnGetAllElement()">
        <form name="main_form">
            <input type="hidden" name="mode" id="mode">
            <input type="hidden" name="hidJmlSI" id="hidJmlSI" value="<%= intJmlSI %>">
            <input type="hidden" name="hidJmBidUsaha" id="hidJmBidUsaha" value="<%= intBidUsaha0 %>">
            <input type="hidden" name="hidBidUsaha" id="hidBidUsaha" value="<%= strBidangUsaha %>">
            <input type="hidden" name="hidJmPajak" id="hidJmPajak" value="<%= intPajak0 %>">
            <input type="hidden" name="hidPajak" id="hidPajak" value="<%= strJnsPajak %>">
            <input type="hidden" name="hidJmRetribusi" id="hidJmRetribusi" value="<%= intRetribusi0 %>">
            <input type="hidden" name="hidRetribusi" id="hidRetribusi" value="<%= strJnsRetribusi %>">
            <input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth %>">
            <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight %>">
            <input type="hidden" name="hidLastElement" id="hidLastElement" value="<%= strLastElement %>">
            <input type="hidden" name="hidKelurahanBU" id="hidKelurahanBU" value="<%= strKelurahanBU %>">
            <input type="hidden" name="hidKecamatanBU" id="hidKecamatanBU" value="<%= strKecamatanBU %>">
            <input type="hidden" name="hidKabupatenBU" id="hidKabupatenBU" value="<%= strKabupatenBU %>">
            <input type="hidden" name="hidKelurahan" id="hidKelurahan" value="<%= strKelurahanPemilik %>">
            <input type="hidden" name="hidKecamatan" id="hidKecamatan" value="<%= strKecamatanPemilik %>">
            <input type="hidden" name="hidKabupaten" id="hidKabupaten" value="<%= strKabupatenPemilik %>">    
            <input type="hidden" name="hidMSG" id="hidMSG" value="<%= strMSG %>">
            <table border="0" width="100%" cellpadding="0" cellspacing="0">
                <tr valign="top">
                    <td align="center">
                        <table border="0" cellpadding="2" cellspacing="2" width="75%">
                            <tr>
                                <td valign="top" align="left" colspan="3">
                                    <%@ include file="genHeader.jsp" %>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    <font class="NmDinas">PENETAPAN PAJAK / RETRIBUSI / PAD</font>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <fieldset>
                                    <legend>Informasi Awal</legend>
                                    <br>
                                    <table border="0">
                                        <tr>
                                            <td colspan="3" valign="top" align="left">
                                                <table border="0" cellpadding="0" cellspacing="0">
                                                    <tr valign="top">
                                                        <td>1.</td>
                                                        <td>&nbsp;&nbsp;&nbsp;</td>
                                                        <td>
                                                            Nomor NPWPD<br>
                                                            <input type="text" value="" name="txtNPWPD" id="txtNPWPD" tabindex="1" onfocus="fnLastElement(this.name)" size="100">
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                            <td colspan="3">
                                                                    &nbsp;
                                                            </td>
                                                    </tr>
                                                    <tr valign="top">
                                                        <td>2.</td>
                                                        <td>&nbsp;&nbsp;&nbsp;</td>
                                                        <td>
                                                            Nama Badan / Merk Usaha<br>
                                                            <input type="text" value="" name="txtNamaBU" id="txtNamaBU" tabindex="2" onfocus="fnLastElement(this.name)" size="100">
                                                        </td>
                                                    </tr>
                                                    <tr valign="top">
                                                        <td>&nbsp;</td>
                                                        <td>&nbsp;&nbsp;&nbsp;</td>
                                                        <td>
                                                            Nama Pemilik / Pengelola<br>
                                                            <input type="text" value="" name="txtNama" id="txtNama" tabindex="3" onfocus="fnLastElement(this.name)" size="100">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td colspan="3">
                                                Untuk melakukan pencarian data, masukkan<br>
                                                NPWPD / NPWRD<br>
                                                atau<br>
                                                Nama Badan / Merk Usaha dan Nama Pemilik / Pengelola<br>
                                            </td>
                                        </tr>
                                    </table>
                                    </fieldset>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                	<font class="error"><%= strMSG %></font><br>
                                    <button type="button" name="btCari" id="btCari" onclick="fnCari()" tabindex="29" onfocus="fnLastElement(this.name)">Cari</button>
                                    <button type="button" name="btBatal" id="btBatal" onclick="fnBatal()" tabindex="30" onfocus="fnLastElement(this.name)">Batal</button>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
