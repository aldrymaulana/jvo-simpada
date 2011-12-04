<%-- 
    Document   : pembayaran
    Created on : Dec 1, 2009, 8:52:23 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.tech.master.common.*"%>
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
    
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
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
    
    ifcPendaftaran ifcp = new ifcPendaftaran();
    if (request.getSession().getAttribute("ifcp") != null) {
        ifcp = (ifcPendaftaran) request.getSession().getAttribute("ifcp");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/simpada.css" rel="stylesheet">
        <script type="text/javascript" src="js/jsUtility.js"></script> 
        <script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/calendar-en.js"></script>
		<script type="text/javascript" src="js/calendar-setup.js"></script>
        <script type="text/javascript">
//            function fnGetAllElement() {
//                var vJmlElement = document.main_form.elements.length;
//                for (var w=0; w<=vJmlElement-1; w++) {
//                    var vElementName = '';
//                    if (document.main_form.elements[w].name != null) {
//                        vElementName = document.main_form.elements[w].name;
//                    }
//                    document.main_form.elements[w].title = vElementName;
//                }
//            }
//
//            function fnLastElement(lastElement) {
//                document.main_form.hidLastElement.value = lastElement;
//            }
//
//            function fnGotoLastElement() {
//                var vLastElement = document.main_form.hidLastElement.value;
//                document.getElementById(vLastElement).focus();
//            }
            
            function fnBatal() {
                document.main_form.method = "post";
                document.main_form.target = "_parent"
                document.main_form.mode.value = 0;
                document.main_form.action = "srvSetoran";
                document.main_form.submit();
            }
            
            function fnCari() {
                document.main_form.method = "post";
                document.main_form.target = "_parent"
                document.main_form.mode.value = 1;
                document.main_form.action = "srvSetoran";
                document.main_form.submit();
            }
        </script>
    </head>
    <body onload="fnGetAllElement()">
        <form name="main_form">
            <input type="hidden" name="mode" id="mode">
            <input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth %>">
            <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight %>">
            <input type="hidden" name="hidLastElement" id="hidLastElement" value="<%= strLastElement %>">
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
                                	<font class="NmDinas">PEMBUATAN<br>
                                    SURAT TANDA SETORAN</font>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    <fieldset>
                                    <legend>Informasi Awal</legend>
                                    <br>
                                    <table border="0">
                                        <tr>
                                            <td colspan="3" valign="top" align="center">
                                                <table border="0" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td>Tanggal Setor</td>
                                                        <td>&nbsp;:&nbsp;</td>
                                                        <td>
                                                                            <input type="text" readonly value="<%= strToday %>" name="txtTglAwal" id="txtTglAwal" maxlength="10" size="10">
                                                                            &nbsp;<button type="reset" id="btTglAwal">...</button>
                                                                        </td>
                                                    </tr>
                                                    <script type="text/javascript">
                                                                    Calendar.setup({
                                                                        inputField     :    "txtTglAwal",      // id of the input field
                                                                        ifFormat       :    "%d/%m/%Y",       // format of the input field
                                                                        showsTime      :    false,            // will display a time selector
                                                                        button         :    "btTglAwal",   // trigger for the calendar (button ID)
                                                                        singleClick    :    true,           // double-click mode
                                                                        step           :    1                // show all years in drop-down boxes (instead of every other year as default)
                                                                    });
                                                                </script>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                                                            <br>
                                    </fieldset>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    <button type="button" name="btCari" id="btCari" onclick="fnCari()" tabindex="29" onfocus="fnLastElement(this.name)">Susun</button>
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
