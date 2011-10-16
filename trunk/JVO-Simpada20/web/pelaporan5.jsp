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

            String strNamaKabid = request.getSession().getAttribute("strNamaKabid").toString();
            String strNIPKabid = request.getSession().getAttribute("strNIPKabid").toString();

            jvCommon jvc = new jvCommon();

            String strIdxLaporan = "0";
            if (request.getSession().getAttribute("strIdxLaporan") != null) {
                strIdxLaporan = request.getSession().getAttribute("strIdxLaporan").toString();
            }
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

                    document.main_form.mode.value = 7;
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
				
                    //document.getElementById("lblNamaPejabat").innerHTML = vNama;
                    document.getElementById("lblJabatanPejabat").innerHTML = vJabatan;
                    document.getElementById("lblNIPPejabat").innerHTML = vNip;
				
                    document.getElementById("hidNamaPejabat").value = vNama;
                    document.getElementById("hidJabatanPejabat").value = vJabatan;
                    document.getElementById("hidNIPPejabat").value = vNip;
                }
            }

            function fnOrderUp(a) {
                document.main_form.hidIdxKolom.value = a;
                document.main_form.hidOrder.value = "up";
                fnCreateReport();
            }

            function fnOrderDown(a) {
                document.main_form.hidIdxKolom.value = a;
                document.main_form.hidOrder.value = "down";
                fnCreateReport();
            }

            function fnCreateReport() {
                var vIdxLap = document.main_form.hidTypeLap.value;
                if (vIdxLap != 0) {
                    document.main_form.hidJnsPajak.value = '';
                    document.main_form.hidTypeLap.value = vIdxLap;
                    document.main_form.method = "post";
                    document.main_form.target = "_parent"
                    document.main_form.mode.value = 1;
                    document.main_form.action = "srvLaporan";
                    document.main_form.submit();
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

            <input type="hidden" name="hidIdxKolom" id="hidIdxKolom">
            <input type="hidden" name="hidOrder" id="hidOrder">
            <input type="hidden" name="hidTypeLap" id="hidTypeLap" value="<%= strIdxLaporan%>">
            <input type="hidden" name="hidJnsPajak" id="hidJnsPajak">

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
            <table border="0" width="100%" cellpadding="1" cellspacing="1">
                <tr valign="top">
                    <td colspan="3">&nbsp;</td>
                </tr>

                <tr valign="top">
                    <td colspan="3" align="center">
                        <font class="NmDinas">PEMERINTAH <%= strNamaPemda.toUpperCase()%></font><br>
                        <font class="NmPemda">DAFTAR PERUSAHAAN</font><br>
                    </td>
                </tr>

                <tr valign="top">
                    <td colspan="3">&nbsp;</td>
                </tr>

                <tr>
                    <td colspan="3">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2">
                            <tr class="JUDUL2" align="center" valign="middle">
                                <td valign="middle" rowspan="2">No</td>
                                <td valign="middle" colspan="2">Nama</td>
                                <td valign="middle" rowspan="2">
                                    <table border="0">
                                        <tr>
                                            <td><img onclick="fnOrderUp(2)" alt="arrow up" src="../image/arrowbigup.gif" align="middle"/></td>
                                            <td>&nbsp;Jenis Usaha&nbsp;</td>
                                            <td><img onclick="fnOrderDown(2)" alt="arrow down" src="../image/arrowbigdown.gif" align="middle" /></td>
                                        </tr>
                                    </table>
                                </td>
                                <td valign="middle" rowspan="2">
                                    <table border="0">
                                        <tr>
                                            <td><img onclick="fnOrderUp(1)" alt="arrow up" src="../image/arrowbigup.gif" align="middle"/></td>
                                            <td>&nbsp;Alamat&nbsp;</td>
                                            <td><img onclick="fnOrderDown(1)" alt="arrow down" src="../image/arrowbigdown.gif" align="middle" /></td>
                                        </tr>
                                    </table>
                                </td>
                                <td valign="middle" rowspan="2">
                                    <table border="0">
                                        <tr>
                                            <td><img onclick="fnOrderUp(5)" alt="arrow up" src="../image/arrowbigup.gif" align="middle"/></td>
                                            <td>&nbsp;Modal&nbsp;</td>
                                            <td><img onclick="fnOrderDown(5)" alt="arrow down" src="../image/arrowbigdown.gif" align="middle" /></td>
                                        </tr>
                                    </table>
                                </td>
                                <td valign="middle" rowspan="2">
                                    <table border="0">
                                        <tr>
                                            <td><img onclick="fnOrderUp(6)" alt="arrow up" src="../image/arrowbigup.gif" align="middle"/></td>
                                            <td>&nbsp;NPWP&nbsp;</td>
                                            <td><img onclick="fnOrderDown(6)" alt="arrow down" src="../image/arrowbigdown.gif" align="middle" /></td>
                                        </tr>
                                    </table>
                                </td>
                                <td valign="middle" rowspan="2">
                                    <table border="0">
                                        <tr>
                                            <td><img onclick="fnOrderUp(7)" alt="arrow up" src="../image/arrowbigup.gif" align="middle"/></td>
                                            <td>&nbsp;Masa Pajak<br>s.d.&nbsp;</td>
                                            <td><img onclick="fnOrderDown(7)" alt="arrow down" src="../image/arrowbigdown.gif" align="middle" /></td>
                                        </tr>
                                    </table>
                                </td>
                                <td valign="middle" rowspan="2">Keterangan</td>
                            </tr>
                            <tr class="JUDUL2" align="center" valign="middle">
                                <td valign="middle">
                                    <table border="0">
                                        <tr>
                                            <td><img onclick="fnOrderUp(0)" alt="arrow up" src="../image/arrowbigup.gif" align="middle"/></td>
                                            <td>&nbsp;Perusahaan&nbsp;</td>
                                            <td><img onclick="fnOrderDown(0)" alt="arrow down" src="../image/arrowbigdown.gif" align="middle" /></td>
                                        </tr>
                                    </table>
                                </td>
                                <td valign="middle">
                                    <table border="0">
                                        <tr>
                                            <td><img onclick="fnOrderUp(3)" alt="arrow up" src="../image/arrowbigup.gif" align="middle"/></td>
                                            <td>&nbsp;Pemilik / Pengelola&nbsp;</td>
                                            <td><img onclick="fnOrderDown(3)" alt="arrow down" src="../image/arrowbigdown.gif" align="middle" /></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <%
                                        if (request.getSession().getAttribute("htDfPerusahaan") != null) {
                                            Hashtable htDfPerusahaan = (Hashtable) request.getSession().getAttribute("htDfPerusahaan");
                                            int intHtDfPerusahaan = htDfPerusahaan.size();
                                            int rowNum = 1;
                                            for (int a = 1; a <= intHtDfPerusahaan; a++) {
                                                String[] strHtDfPerusahaan = (String[]) htDfPerusahaan.get(String.valueOf(a));
                                                String strNmPerusahaan = (strHtDfPerusahaan[0].length() == 0) ? "" : strHtDfPerusahaan[0];
                                                String strNmPemilik = (strHtDfPerusahaan[3].length() == 0) ? "" : strHtDfPerusahaan[3];
                                                String strJnsUsaha = (strHtDfPerusahaan[2].length() == 0) ? "" : strHtDfPerusahaan[2];
                                                String strAlamat = (strHtDfPerusahaan[1].length() == 0) ? "" : strHtDfPerusahaan[1];
                                                String strModal = (strHtDfPerusahaan[5].length() == 0) ? "0" : jvc.fnDropCurrencySign(strHtDfPerusahaan[5]);
                                                String strNPWP = (strHtDfPerusahaan[6].length() == 0) ? "" : strHtDfPerusahaan[6];
                                                String strMasaPajak = (strHtDfPerusahaan[7].length() == 0) ? "" : new java.text.SimpleDateFormat("dd MMM yyyy").format(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(strHtDfPerusahaan[7]));
                                                int c = rowNum % 2;
                                                String strNmClass;
                                                if (c == 0) {
                                                    strNmClass = "TANYA";
                                                } else {
                                                    strNmClass = "JAWAB";
                                                }
                            %>

                            <tr class="<%= strNmClass%>">
                                <td align="left"><%= a%></td>
                                <td align="left"><%= strNmPerusahaan%></td>

                                <td align="left"><%= strNmPemilik%></td>
                                <td align="left"><%= strJnsUsaha%></td>
                                <td align="left"><%= strAlamat%></td>

                                <td align="right"><%= new java.text.DecimalFormat("#,###,###,###,###.##").format(Double.parseDouble(strModal))%></td>
                                <td align="center"><%= strNPWP%></td>
                                <td align="center"><%= strMasaPajak%></td>
                                <td align="center"></td>
                            </tr>
                            <%
                                                rowNum = rowNum + 1;
                                            }
                                        }
                            %>
                        </table>
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
								Mengetahui<br>
                                    <label id="lblJabatanPejabat"></label><br>
                                    <%= jvc.fnUpperLower(strNamaPemda)%><br><br><br>
                                    <select name="slctPejabat" id="slctPejabat" onchange="fnChangePejabat()">
                                        <option></option>
                                        <%
                                                    Hashtable htPejabat = new Hashtable();
                                                    if (request.getSession().getAttribute("htInfoPejabat") != null) {
                                                        htPejabat = (Hashtable) request.getSession().getAttribute("htInfoPejabat");
                                                        int intDataPejabat = htPejabat.size();
                                                        for (int a = 1; a <= intDataPejabat; a++) {
                                                            String[] strArray = (String[]) htPejabat.get(String.valueOf(a));
                                                            if (strArray[4].equals("1")) {
                                        %>
                                        <option value="<%= strArray[1]%>,<%= strArray[2]%>,<%= strArray[3]%>" <%= (strArray[0].equals("10000")) ? "SELECTED" : ""%> ><%= strArray[1]%></option>
                                        <%
                                                            }
                                                        }
                                                    }
                                        %>
                                    </select><br>
                                    <label id="lblNIPPejabat"></label>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td align="center">
                        <table>
                            <tr>
                                <td align="center"><br>
								Kabid Pendapatan<br><br><br>
                                    <%= strNamaKabid%><br>
                                    <%= strNIPKabid%>
                                </td>
                            </tr>
                        </table>
                    </td>
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