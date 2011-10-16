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
	
    jvCommon jvc = new jvCommon();
    
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy");
    java.util.Date todayDate = new java.util.Date();
    String strToday = sdf.format(todayDate);
    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd/MM/yyyy");
    String strToday1 = sdf1.format(todayDate);
    
    Calendar cal = new GregorianCalendar();
    int intDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
    int intDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    int intMonth = cal.get(Calendar.MONTH);
    int intYear = cal.get(Calendar.YEAR);
    
    Calendar cal2 = new GregorianCalendar(intYear, intMonth, 1);
    int intMaxDay2 = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
    
    String strDate1 = "01/" + jvc.fnLRPad("LPAD",String.valueOf(intMonth+1),"0",2) + "/" + intYear;
    String strDate2 = intMaxDay2 + "/" + jvc.fnLRPad("LPAD",String.valueOf(intMonth+1),"0",2) + "/" + intYear;

    String strIdxLaporan = "0";
    if (request.getSession().getAttribute("strIdxLaporan") != null) {
        strIdxLaporan = request.getSession().getAttribute("strIdxLaporan").toString();
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
		<style type="text/css">
			.clHidden {
				display: none;
			}
			.clShow {
				display: inline;
			}
		</style>
        <script type="text/javascript">
            function fnAutoPilihLaporan() {
                var vIdxLaporan = document.main_form.hidTypeLap.value;
                document.main_form.slctJnsLaporan.selectedIndex = vIdxLaporan;
                fnPilihLaporan();
            }

            function fnPilihLaporan() {
                var vIdxLap = document.main_form.slctJnsLaporan.selectedIndex;
                if (vIdxLap != 0) {
                    if (vIdxLap == 1) {
                            document.getElementById("lblTglAwal").innerHTML = "Tgl Laporan";
                            document.getElementById("txtTglAwal").value = '<%= strToday1 %>';
                            document.getElementById("rowTglAwal").className = "clShow";
                            document.getElementById("rowTglAkhir").className = "clHidden";
                            document.getElementById("rowJnsPajak").className = "clHidden";
                    } else if (vIdxLap == 5) {
                            document.getElementById("lblTglAwal").innerHTML = "Tgl Awal";
                            document.getElementById("txtTglAwal").value = '<%= strDate1 %>';
                            document.getElementById("rowTglAwal").className = "clShow";
                            document.getElementById("rowTglAkhir").className = "clShow";
                            document.getElementById("rowJnsPajak").className = "clShow";
                    } else if (vIdxLap == 6) {
                            document.getElementById("rowTglAwal").className = "clHidden";
                            document.getElementById("rowTglAkhir").className = "clHidden";
                            document.getElementById("rowJnsPajak").className = "clHidden";
                    } else {
                            document.getElementById("lblTglAwal").innerHTML = "Tgl Awal";
                            document.getElementById("txtTglAwal").value = '<%= strDate1 %>';
                            document.getElementById("rowTglAwal").className = "clShow";
                            document.getElementById("rowTglAkhir").className = "clShow";
                            document.getElementById("rowJnsPajak").className = "clHidden";
                    }
                } else {
                    document.getElementById("rowTglAwal").className = "clHidden";
                    document.getElementById("rowTglAkhir").className = "clHidden";
                    document.getElementById("rowJnsPajak").className = "clHidden";
                }
            }

            function fnCreateReport() {
                var vIdxLap = document.main_form.slctJnsLaporan.selectedIndex;
                if (vIdxLap != 0) {
                    var vIdxJnsPajak = document.main_form.slctJnsPajak.selectedIndex;
                    var vJnsPajak = document.main_form.slctJnsPajak[vIdxJnsPajak].value;
                    document.main_form.hidJnsPajak.value = vJnsPajak;
                    document.main_form.hidTypeLap.value = vIdxLap;
                    document.main_form.method = "post";
                    document.main_form.target = "_parent"
                    document.main_form.mode.value = 1;
                    document.main_form.action = "srvLaporan";
                    document.main_form.submit();
                }
            }
    	
            function fnBatal() {
                document.main_form.method = "post";
                document.main_form.target = "_parent"
                document.main_form.mode.value = 0;
                document.main_form.action = "srvLaporan";
                document.main_form.submit();
            }
        </script>
    </head>
    <body onload="fnGetAllElement();fnAutoPilihLaporan();">
    <!--body-->
        <form name="main_form">
            <input type="hidden" name="mode" id="mode">
            <input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth %>">
            <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight %>">
            <input type="hidden" name="hidLastElement" id="hidLastElement">
            <input type="hidden" name="hidTypeLap" id="hidTypeLap" value="<%= strIdxLaporan %>">
            <input type="hidden" name="hidJnsPajak" id="hidJnsPajak">
            <table border="0" width="100%" cellpadding="0" cellspacing="0">
                <tr valign="top">
                    <td align="center">
                        <table border="0" cellpadding="2" cellspacing="2" width="75%">
                            <tr>
                                <td valign="top" align="left" colspan="2">
                                    <table border="0" cellpadding="0" cellspacing="0" topmargin="0" leftmargin="0">
                                        <tr>
                                            <td align="left">
                                                <font class="NmPemda">PEMERINTAH&nbsp;<%= strNamaPemda%></font><br>
                                                <font class="NmDinas"><%= strNamaBidang %></font><br>
                                                <font class="AlmDinas"><%= strAlamatPemda %>,&nbsp;<%= strKotamadyaPemda %>&nbsp;<%= strKodePos %></font><br>
                                                <font class="AlmDinas">Telp:&nbsp<%= strTelepon %>, Facs:&nbsp;<%= strFacsimile %></font>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left">
                                                <hr>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td valign="top" align="left">
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    PELAPORAN
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" valign="top" align="center">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                    	<tr>
                                            <td align="right">Jenis Laporan</td>
                                            <td>&nbsp;:&nbsp;</td>
                                            <td align="left">
                                                <select name="slctJnsLaporan" id="slctJnsLaporan" onchange="fnPilihLaporan()">
                                                        <option value="0">
                                                        <option value="1" <%= strIdxLaporan.equalsIgnoreCase("1")?"SELECTED":"" %>>Buku Rekapitulasi Penerimaan Harian</option>
                                                        <option value="2" <%= strIdxLaporan.equalsIgnoreCase("2")?"SELECTED":"" %>>Buku Kas Pembantu</option>
                                                        <option value="3" <%= strIdxLaporan.equalsIgnoreCase("3")?"SELECTED":"" %>>Buku Kas Umum</option>
                                                        <option value="4" <%= strIdxLaporan.equalsIgnoreCase("4")?"SELECTED":"" %>>SPJ Pendapatan - Fungsional</option>
                                                        <option value="5" <%= strIdxLaporan.equalsIgnoreCase("5")?"SELECTED":"" %>>Daftar Rekapitulasi Penetapan Pajak</option>
                                                        <option value="6" <%= strIdxLaporan.equalsIgnoreCase("6")?"SELECTED":"" %>>Daftar Perusahaan</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr id="rowTglAwal" class="clHidden">
                                            <td align="right"><label id="lblTglAwal">Tgl Awal</label></td>
                                            <td><label id="lblTglAwal1">&nbsp;:&nbsp;</label></td>
                                            <td align="left">
                                                <input type="text" readonly value="<%= strDate1 %>" name="txtTglAwal" id="txtTglAwal" maxlength="10" size="10">
                                                &nbsp;<button type="reset" id="btTglAwal">...</button>
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
                                            </td>
                                        </tr>
                                        <tr id="rowTglAkhir" class="clHidden">
                                                <td align="right"><label id="lblTglAkhir">Tgl Akhir</label></td>
                                            <td><label id="lblTglAkhir1">&nbsp;:&nbsp;</label></td>
                                            <td align="left">
                                                <input type="text" readonly value="<%= strDate2 %>" name="txtTglAkhir" id="txtTglAkhir" maxlength="10" size="10">
                                                &nbsp;<button type="reset" id="btTglAkhir">...</button>
                                                <script type="text/javascript">
                                                    Calendar.setup({
                                                        inputField     :    "txtTglAkhir",      // id of the input field
                                                        ifFormat       :    "%d/%m/%Y",       // format of the input field
                                                        showsTime      :    false,            // will display a time selector
                                                        button         :    "btTglAkhir",   // trigger for the calendar (button ID)
                                                        singleClick    :    true,           // double-click mode
                                                        step           :    1                // show all years in drop-down boxes (instead of every other year as default)
                                                    });
                                                </script>
                                            </td>
                                        </tr>
                                        <tr id="rowJnsPajak" class="clHidden">
                                            <td align="right">Jenis Pajak</td>
                                            <td>&nbsp;:&nbsp;</td>
                                            <td align="left">
                                                <select name="slctJnsPajak" id="slctJnsPajak">
                                                        <%
                                                                if (request.getSession().getAttribute("htPajak") != null) {
                                                                        Hashtable htPajak = (Hashtable) request.getSession().getAttribute("htPajak");
                                                                        if (htPajak.size() > 0) {
                                                                                int intHtPajak = htPajak.size();
                                                                                for (int aa=1; aa<=intHtPajak; aa++) {
                                                                                        String[] strPajak = (String[]) htPajak.get(String.valueOf(aa));
                                                                                        %>
                                                                                                <option value="<%= strPajak[2] %>"><%= strPajak[1] %></option>
                                                                                        <%
                                                                                }
                                                                        }
                                                                }
                                                        %>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    <button type="button" name="btCari" id="btCari" onclick="fnCreateReport()" tabindex="29" onfocus="fnLastElement(this.name)">Susun</button>
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
