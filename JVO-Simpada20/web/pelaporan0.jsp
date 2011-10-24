<%-- 
    Document   : pendaftaran
    Created on : Nov 20, 2009, 11:16:22 PM
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
            String strKelurahanPemda = "";
            String strKotamadyaPemda = "";
            String strKodePos = "";
            String strTelepon = "";
            String strFacsimile = "";
            if (request.getSession().getAttribute("htDataPemda") != null) {
                htDataPemda = (Hashtable) request.getSession().getAttribute("htDataPemda");
                int intDataPemda = htDataPemda.size();
                for (int a = 1; a <= intDataPemda; a++) {
                    //String sqlQuery = "SELECT 0 mPemerintah.Daerah, 1 mPemerintah.Bidang, 2 mPemerintah.Alamat, " +
                    //              "3 mPemerintah.[No], 4 mPemerintah.RT, 5 mPemerintah.RW, 6 mPemerintah.RK, 7 mPemerintah.Kelurahan, " +
                    //              "8 mPemerintah.Kecamatan, 9 mPemerintah.Kabupaten, 10 mPemerintah.KodePos, " +
                    //              "11 mPemerintah.Telepon, 12 mPemerintah.Facs FROM mPemerintah";
                    String[] strArray = (String[]) htDataPemda.get(String.valueOf(a));
                    strNamaPemda = strArray[0].toUpperCase();
                    strNamaBidang = strArray[1].toUpperCase();
                    strAlamatPemda = strArray[2] + " No " + strArray[3] + ", RT/RW " + strArray[4] + "/" + strArray[5];
                    strKelurahanPemda = strArray[7];
                    strKotamadyaPemda = strArray[9];
                    strKodePos = strArray[10];
                    strTelepon = strArray[11];
                    strFacsimile = strArray[12];
                }
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy");
            java.util.Date todayDate = new java.util.Date();
            String strToday = sdf.format(todayDate);

            java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("MMMM yyyy");
            String strToday1 = sdf1.format(todayDate);

            java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("dd/MM/yyyy");
            String strToday2 = sdf2.format(todayDate);

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
                for (int a = 1; a <= intDataLogin; a++) {
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
            String strLastElement = "";//request.getSession().getAttribute("strLastElement").toString();
            String strNamaBendahara = request.getSession().getAttribute("strNamaBendahara").toString();
            String strNIPBendahara = request.getSession().getAttribute("strNIPBendahara").toString();

            jvCommon jvc = new jvCommon();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Insert title here</title>
        <link href="css/simpada.css" rel="stylesheet">
        <script type="text/javascript" src="js/jsUtility.js"></script> 
        <script type="text/javascript" src="js/calendar.js"></script>
        <script type="text/javascript" src="js/calendar-en.js"></script>
        <script type="text/javascript" src="js/calendar-setup.js"></script>
        <script type="text/javascript">
        
            function fnCetak() {
                var vIdxPejabat = document.getElementById("slctPejabat").selectedIndex;
                if (vIdxPejabat != 0) {
                    var vValue = document.getElementById("slctPejabat")[vIdxPejabat].value;

                    var vArrValue = new Array;
                    var vSep = ",";
                    vArrValue = fnCSV2Array(vValue, vSep);
                    var vNama = vArrValue[0];
                    var vNip = vArrValue[1];
                    var vJabatan = vArrValue[2];

                    document.main_form.hidNamaPejabat.value = vNama;
                    document.main_form.hidJabatanPejabat.value = vJabatan;
                    document.main_form.hidNIPPejabat.value = vNip;

                    document.main_form.mode.value = 4;
                    document.main_form.method = "POST";
                    document.main_form.action = "srvLaporan";
                    document.main_form.submit();
                } else {
                    alert('Anda Belum Memilih Pejabat Pengguna Anggaran!');
                }
            }

            function fnChangePejabat() {
                var vIdxPejabat = document.getElementById("slctPejabat").selectedIndex;
                if (vIdxPejabat != 0) {
                    var vValue = document.getElementById("slctPejabat")[vIdxPejabat].value;

                    var vArrValue = new Array;
                    var vSep = ",";
                    vArrValue = fnCSV2Array(vValue, vSep);
                    var vNama = vArrValue[0];
                    var vNip = vArrValue[1];
                    var vJabatan = vArrValue[2];

                    document.main_form.hidNamaPejabat.value = vNama;
                    document.main_form.hidJabatanPejabat.value = vJabatan;
                    document.main_form.hidNIPPejabat.value = vNip;

                    document.getElementById("lblNamaPejabat").innerHTML = vNama;
                    document.getElementById("lblJabatanPejabat").innerHTML = vJabatan;
                    document.getElementById("lblNIPPejabat").innerHTML = vNip;

                    document.getElementById("hidNamaPejabat").value = vNama;
                    document.getElementById("hidJabatanPejabat").value = vJabatan;
                    document.getElementById("hidNIPPejabat").value = vNip;
                }
            }
        </script>
    </head>
    <body onload="fnChangePejabat();fnGetAllElement();">
        <form name="main_form">
            <input type="hidden" name="mode" id="mode">
            <input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth%>">
            <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight%>">
            <input type="hidden" name="hidLastElement" id="hidLastElement">

            <input style="display: none;" type="text" name="hidNamaPejabat" id="hidNamaPejabat">
            <input style="display: none;" type="text" name="hidJabatanPejabat" id="hidJabatanPejabat">
            <input style="display: none;" type="text" name="hidNIPPejabat" id="hidNIPPejabat">
            <input style="display: none;" type="text" name="hidBulanBuku" id="hidBulanBuku" value="<%= strToday1%>">

            <input type="hidden" name="hidNamaPemda" id="hidNamaPemda" value="<%= strNamaPemda%>">
            <input type="hidden" name="hidNamaBidang" id="hidNamaBidang" value="<%= strNamaBidang%>">
            <input type="hidden" name="hidAlamatPemda" id="hidAlamatPemda" value="<%= strAlamatPemda%>">
            <input type="hidden" name="hidKelurahanPemda" id="hidKelurahanPemda" value="<%= strKelurahanPemda%>">
            <input type="hidden" name="hidKotamadyaPemda" id="hidKotamadyaPemda" value="<%= strKotamadyaPemda%>">
            <input type="hidden" name="hidKodePos" id="hidKodePos" value="<%= strKodePos%>">
            <input type="hidden" name="hidTelepon" id="hidTelepon" value="<%= strTelepon%>">
            <input type="hidden" name="hidFacsimile" id="hidFacsimile" value="<%= strFacsimile%>">

            <input type="hidden" name="hidNamaUser" id="hidNamaUser" value="<%= strNama%>">
            <input type="hidden" name="hidKdJabatanUser" id="hidKdJabatanUser" value="<%= strKdJabatan%>">
            <input type="hidden" name="hidNIPUser" id="hidNIPUser" value="<%= strNIP%>">
            <input type="hidden" name="hidJabatanUser" id="hidJabatanUser" value="<%= strJabatan%>">

            <input type="hidden" name="hidNamaBendahara" id="hidNamaBendahara" value="<%= strNamaBendahara%>">
            <input type="hidden" name="hidNIPBendahara" id="hidNIPBendahara" value="<%= strNIPBendahara%>">
            <table border="0" width="98%" cellpadding="1" cellspacing="1">
                <tr valign="top">
                    <td colspan="3">&nbsp;</td>
                </tr>

                <tr valign="top">
                    <td colspan="3" align="center">
                        <fieldset>
                        <font class="NmDinas">PEMERINTAH <%= strNamaPemda.toUpperCase()%></font><br>
                        <font class="NmPemda">BUKU REKAPITULASI PENERIMAAN HARIAN</font>
                        </fieldset>
                    </td>
                </tr>

                <tr valign="top">
                    <td colspan="3">&nbsp;</td>
                </tr>

                <tr>
                    <td colspan="3">
                        <fieldset>
                            <table border="0" width="100%">
                                <tr>
                                    <td>Unit Kerja</td>
                                    <td>&nbsp;:&nbsp;</td>
                                    <td width="80%"><%= strNamaBidang%></td>
                                </tr>
                                <tr>
                                    <td>Pemimpin Unit Kerja</td>
                                    <td>&nbsp;:&nbsp;</td>
                                    <td><label id="lblNamaPejabat"></label></td>
                                </tr>
                                <tr>
                                    <td>Bendahara Penerimaan</td>
                                    <td>&nbsp;:&nbsp;</td>
                                    <td><%= strNamaBendahara%></td>
                                </tr>
                                <tr>
                                    <td colspan="3">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="3">
                                        <table width="100%" border="0" cellpadding="2" cellspacing="2">
                                            <tr class="JUDUL2" align="center" valign="middle">
                                                <td rowspan="2">No Urut</td>
                                                <td rowspan="2">Tanggal</td>
                                                <td rowspan="2">Referensi</td>
                                                <td colspan="2">Pajak Daerah (Rp)</td>
                                                <td colspan="2">Retribusi Daerah (Rp)</td>
                                                <td colspan="2">Lain-Lain PAD yang Sah (Rp)</td>
                                            </tr>
                                            <tr class="JUDUL2" align="center" valign="middle">
                                                <td>Kode Rekening</td>
                                                <td>Jumlah</td>
                                                <td>Kode Rekening</td>
                                                <td>Jumlah</td>
                                                <td>Kode Rekening</td>
                                                <td>Jumlah</td>
                                            </tr>
                                            <%
                                                        double dblTotalPajak = 0;
                                                        double dblTotalRetribusi = 0;
                                                        double dblTotalPADLain = 0;
                                                        if (request.getSession().getAttribute("htLapHarian") != null) {
                                                            String strTglSKDP = request.getSession().getAttribute("strTglAwal").toString();
                                                            Hashtable htLapHarian = (Hashtable) request.getSession().getAttribute("htLapHarian");
                                                            int intHtLapHarian = htLapHarian.size();
                                                            int rowNum = 1;
                                                            for (int a = 1; a <= intHtLapHarian; a++) {
                                                                String[] strHtLapHarian = (String[]) htLapHarian.get(String.valueOf(a));
                                                                int c = rowNum % 2;
                                                                String strNmClass;
                                                                if (c == 0) {
                                                                    strNmClass = "TANYA";
                                                                } else {
                                                                    strNmClass = "JAWAB";
                                                                }
                                            %>
                                            <tr class="<%= strNmClass%>" align="center">
                                                <td align="center"><%= a%></td>
                                                <td><%= (a == 1) ? strTglSKDP : "&nbsp;"%></td>
                                                <td>&nbsp;</td>
                                                <td align="center"><%= (strHtLapHarian[1].trim().length() == 0) ? "&nbsp;" : strHtLapHarian[1]%></td>
                                                <td align="right"><%= (strHtLapHarian[3].equalsIgnoreCase("0.0")) ? "Nihil" : new java.text.DecimalFormat("#,###,###,###,###.##").format(Double.parseDouble(strHtLapHarian[3]))%></td>
                                                <td align="center"><%= (strHtLapHarian[5].trim().length() == 0) ? "&nbsp;" : strHtLapHarian[5]%></td>
                                                <td align="right"><%= (strHtLapHarian[7].equalsIgnoreCase("0.0")) ? "Nihil" : new java.text.DecimalFormat("#,###,###,###,###.##").format(Double.parseDouble(strHtLapHarian[7]))%></td>
                                                <td align="center"><%= (strHtLapHarian[9].trim().length() == 0) ? "&nbsp;" : strHtLapHarian[9]%></td>
                                                <td align="right"><%= (strHtLapHarian[11].equalsIgnoreCase("0.0")) ? "Nihil" : new java.text.DecimalFormat("#,###,###,###,###.##").format(Double.parseDouble(strHtLapHarian[11]))%></td>
                                            </tr>
                                            <%
                                                                dblTotalPajak = dblTotalPajak + Double.parseDouble(strHtLapHarian[3]);
                                                                dblTotalRetribusi = dblTotalRetribusi + Double.parseDouble(strHtLapHarian[7]);
                                                                dblTotalPADLain = dblTotalPADLain + Double.parseDouble(strHtLapHarian[11]);
                                                                rowNum = rowNum + 1;
                                                            }
                                                        }
                                            %>
                                            <tr class="JUDUL2">
                                                <td colspan="4" align="center">TOTAL</td>
                                                <td align="right"><%= new java.text.DecimalFormat("#,###,###,###,###.##").format(dblTotalPajak)%></td>
                                                <td>&nbsp;</td>
                                                <td align="right"><%= new java.text.DecimalFormat("#,###,###,###,###.##").format(dblTotalRetribusi)%></td>
                                                <td>&nbsp;</td>
                                                <td align="right"><%= new java.text.DecimalFormat("#,###,###,###,###.##").format(dblTotalPADLain)%></td>
                                            </tr>
                                        </table>
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
                    <td align="left">
                        <table>
                            <tr>
                                <td align="center">
			Mengetahui<br>Pengguna Anggaran<br><br><br>
                        <select name="slctPejabat" id="slctPejabat" onchange="fnChangePejabat()">
                            <option></option>
                            <%
                                        Hashtable htPejabat = new Hashtable();
                                        if (request.getSession().getAttribute("htInfoPejabat") != null) {
                                            htDataLogin = (Hashtable) request.getSession().getAttribute("htInfoPejabat");
                                            int intDataLogin = htDataLogin.size();
                                            for (int a = 1; a <= intDataLogin; a++) {
                                                String[] strArray = (String[]) htDataLogin.get(String.valueOf(a));
                                                if (strArray[4].equals("1")) {
                            %>
                            <option value="<%= strArray[1]%>,<%= strArray[2]%>,<%= strArray[3]%>" <%= (strArray[0].equals("31000")) ? "SELECTED" : ""%> ><%= strArray[1]%></option>
                            <%
                                                }
                                            }
                                        }
                            %>
                        </select><br>
                        <label id="lblJabatanPejabat"></label><br>
                        <label id="lblNIPPejabat"></label>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <td align="right">
                        <table>
                            <tr>
                                <td align="center">
                                    <%= strKelurahanPemda%>,&nbsp;<%= strToday%><br>
					Bendahara Penerimaan<br><br><br>
                                    <%= strNamaBendahara%><br>
                                    <%= strNIPBendahara%>
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
                        <button type="button" name="btCetak" id="btCetak" onclick="fnCetak()">Cetak</button>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>