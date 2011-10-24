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
            String JRXML_LOCAL_PATH = jvCommon.fnGetProperty("JRXML_LOCAL_PATH").toString();
            String REPORT_LOCAL_PATH = jvCommon.fnGetProperty("REPORT_LOCAL_PATH").toString();
            String OUTPUT_LOCAL_PATH = jvCommon.fnGetProperty("OUTPUT_LOCAL_PATH").toString();

            //String NAMA_BANK = jvCommon.fnGetProperty("NAMA_BANK").toString();
            //String NO_REK = jvCommon.fnGetProperty("NO_REK").toString();

            String NAMA_BANK = request.getParameter("nmBank").toString();
            String NO_REK = request.getParameter("noRek").toString();
            System.out.println("Nama Bank: " + NAMA_BANK);
            System.out.println("No Rek Bank: " + NO_REK);

            jvCommon jvc = new jvCommon();

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
                for (int a = 1; a <= intDataPemda; a++) {
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
            String strLastElement = request.getSession().getAttribute("strLastElement").toString();

            Double intTotal = 0.0;
            if (request.getSession().getAttribute("intTotal") != null) {
                intTotal = Double.parseDouble(request.getSession().getAttribute("intTotal").toString());
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy");
            java.util.Date todayDate = new java.util.Date();
            String strToday = sdf.format(todayDate);

            String strTglTerimaUang = "";
            if (request.getSession().getAttribute("strTglSTS") != null) {
                strTglTerimaUang = request.getSession().getAttribute("strTglSTS").toString();
            }

            java.util.Date dtTglTerimaUang = jvc.fnStr2Date(strTglTerimaUang, "dd/MM/yyyy");
            String strTglTerimaUang1 = jvc.fnDate2Str(dtTglTerimaUang, "dd MMMM yyyy");

            String strNamaBendahara = request.getSession().getAttribute("strNamaBendahara").toString();
            String strNIPBendahara = request.getSession().getAttribute("strNIPBendahara").toString();

            String strNoSTS = request.getSession().getAttribute("strNoSTS").toString();
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
            function fnGetTerbilang() {
                var vTotal = document.main_form.hidTotal.value;
                var vTerbilang = terbilang(parseInt(vTotal));
                //alert("vTerbilang: " + vTerbilang);
                document.getElementById("lblTerbilang").innerHTML = vTerbilang;
            }

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

                    vTerbilang = document.getElementById("lblTerbilang").innerHTML;
                    document.main_form.hidTerbilang.value = vTerbilang;
                    document.main_form.mode.value = 2;
                    document.main_form.method = "POST";
                    document.main_form.action = "srvSetoran";
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
	
                    document.getElementById("lblJabatanPejabat").innerHTML = vJabatan;
                    document.getElementById("lblNIPPejabat").innerHTML = vNip;
                }
            }
        </script>
    </head>
    <body onload="fnGetTerbilang();fnChangePejabat();">
        <form name="main_form">
            <input type="hidden" name="mode" id="mode">
            <input type="hidden" name="hidTotal" id="hidTotal" value="<%= intTotal%>">
            <input type="hidden" name="hidTerbilang" id="hidTerbilang">
            <input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth%>">
            <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight%>">
            <input type="hidden" name="hidLastElement" id="hidLastElement" value="<%= strLastElement%>">

            <input type="hidden" name="hidNamaPejabat" id="hidNamaPejabat">
            <input type="hidden" name="hidJabatanPejabat" id="hidJabatanPejabat">
            <input type="hidden" name="hidNIPPejabat" id="hidNIPPejabat">

            <input type="hidden" name="hidNamaPemda" id="hidNamaPemda" value="<%= strNamaPemda%>">
            <input type="hidden" name="hidNamaBidang" id="hidNamaBidang" value="<%= strNamaBidang%>">
            <input type="hidden" name="hidAlamatPemda" id="hidAlamatPemda" value="<%= strAlamatPemda%>">
            <input type="hidden" name="hidKotamadyaPemda" id="hidKotamadyaPemda" value="<%= strKotamadyaPemda%>">
            <input type="hidden" name="hidKodePos" id="hidKodePos" value="<%= strKodePos%>">
            <input type="hidden" name="hidTelepon" id="hidTelepon" value="<%= strTelepon%>">
            <input type="hidden" name="hidFacsimile" id="hidFacsimile" value="<%= strFacsimile%>">

            <input type="hidden" name="hidNamaUser" id="hidNamaUser" value="<%= strNama%>">
            <input type="hidden" name="hidKdJabatanUser" id="hidKdJabatanUser" value="<%= strKdJabatan%>">
            <input type="hidden" name="hidNIPUser" id="hidNIPUser" value="<%= strNIP%>">
            <input type="hidden" name="hidJabatanUser" id="hidJabatanUser" value="<%= strJabatan%>">

            <input type="hidden" name="hidNoSTS" id="hidNoSTS" value="<%= strNoSTS%>">
            <input type="hidden" name="hidTglSTS" id="hidTglSTS" value="<%= strTglTerimaUang%>">

            <table border="0" width="98%" cellpadding="1" cellspacing="1">
                <tr valign="top">
                    <td colspan="3">&nbsp;</td>
                </tr>

                <tr valign="top">
                    <td colspan="3" align="center">
                        <fieldset>
                        <font class="NmDinas">PEMERINTAH <%= strNamaPemda.toUpperCase()%></font><br>
                        <font class="NmPemda">SURAT TANDA SETORAN</font>
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
                                <td>STS No:&nbsp;<%= strNoSTS%></td>
                                <td width="40%">&nbsp;</td>
                                <td>
                                    <table>
                                        <tr>
                                            <td>Bank</td>
                                            <td>&nbsp;:&nbsp;</td>
                                            <td><%= NAMA_BANK%></td>
                                        </tr>
                                        <tr>
                                            <td>No Rekening</td>
                                            <td>&nbsp;:&nbsp;</td>
                                            <td><%= NO_REK%></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>Harap diterima uang sebesar</td>
                                <td colspan="2">&nbsp;:&nbsp;Rp&nbsp;<%= jvc.fnFormatNumberInd(String.valueOf(intTotal))%></td>
                            </tr>
                            <tr>
                                <td>Dengan huruf</td>
                                <td colspan="2">&nbsp;:&nbsp;<label id="lblTerbilang"></label></td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3">Dengan rincian sebagai berikut:</td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td align="center" colspan="3">
                                    <table width="100%" border="0" cellpadding="1" cellspacing="1">
                                        <tr class="JUDUL2">
                                            <td>No</td>
                                            <td>Kode Rekening</td>
                                            <td>Uraian Rincian Obyek</td>
                                            <td>Jumlah (Rp)</td>
                                        </tr>

                                        <%
                                                    String strJumlah = "0";
                                                    Double dblTotal = 0.0;
                                                    String strFontWeight = "";
                                                    if (request.getSession().getAttribute("htRincian") != null) {
                                                        Hashtable htRincian = (Hashtable) request.getSession().getAttribute("htRincian");
                                                        int intHtRincian = htRincian.size();
                                                        int rowNum = 1;
                                                        for (int a = 1; a <= intHtRincian; a++) {
                                                            String[] strArray = (String[]) htRincian.get(String.valueOf(a));
                                                            if (request.getSession().getAttribute("htRincianBiaya") != null) {
                                                                Hashtable htRincianBiaya = (Hashtable) request.getSession().getAttribute("htRincianBiaya");
                                                                int intHtRincianBiaya = htRincianBiaya.size();
                                                                for (int b = 1; b <= intHtRincianBiaya; b++) {
                                                                    String[] strArrayBiaya = (String[]) htRincianBiaya.get(String.valueOf(b));
                                                                    if (strArray[0].equalsIgnoreCase(strArrayBiaya[1])) {
                                                                        strJumlah = strArrayBiaya[2];
                                                                    }
                                                                }
                                                            }
                                                            System.out.println("strArray[1]: " + strArray[1]);
                                                            if (strArray[0].trim().length() <= 5) {
                                                                strFontWeight = "style=\"font-weight: bold;\"";
                                                            } else {
                                                                strFontWeight = "style=\"font-weight: normal;\"";
                                                            }
                                                            int c = rowNum % 2;
                                                            String strNmClass;
                                                            if (c == 0) {
                                                                strNmClass = "TANYA";
                                                            } else {
                                                                strNmClass = "JAWAB";
                                                            }

                                        %>

                                        <tr class="<%= strNmClass%>">
                                            <td><%= a%></td>
                                            <td align="left" <%= strFontWeight%>><%= strArray[0]%></td>
                                            <td align="left" <%= strFontWeight%>><%= strArray[1]%></td>
                                            <td align="right"><%= jvc.fnFormatNumberInd(strJumlah) %></td>
                                        </tr>

                                        <%
                                                            Double dblJumlah = Double.parseDouble(strJumlah);
                                                            dblTotal = dblTotal + dblJumlah;
                                                            strJumlah = "0";
                                                            rowNum = rowNum + 1;
                                                        }
                                                    }
                                        %>
                                        <tr class="JUDUL2">
                                            <td colspan="3">J U M L A H</td>
                                            <td align="right"><%= jvc.fnFormatNumberInd(String.valueOf(dblTotal))%></td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3">Uang tersebut diterima tanggal:&nbsp;<%= strTglTerimaUang1%></td>
                            </tr>
                        </table>
                        </fieldset>
                    </td>
                </tr>

                <tr>
                    <td colspan="3">&nbsp;&nbsp;&nbsp;</td>
                </tr>
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
				NIP: <label id="lblNIPPejabat"></label>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <td align="center">
				Bendahara Penerimaan<br><br><br>
                        <%= strNamaBendahara%><br>
				NIP: <%= strNIPBendahara%>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;&nbsp;&nbsp;</td>
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