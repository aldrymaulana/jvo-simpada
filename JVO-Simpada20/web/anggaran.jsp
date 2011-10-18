<%-- 
    Document   : pendaftaran
    Created on : Nov 20, 2009, 11:16:22 PM
    Author     : Administrator
--%>

<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jvo.simpada.common.*, java.text.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
            String JRXML_LOCAL_PATH = jvCommon.fnGetProperty("JRXML_LOCAL_PATH").toString();
            String REPORT_LOCAL_PATH = jvCommon.fnGetProperty("REPORT_LOCAL_PATH").toString();
            String OUTPUT_LOCAL_PATH = jvCommon.fnGetProperty("OUTPUT_LOCAL_PATH").toString();

            //String NAMA_BANK = jvCommon.fnGetProperty("NAMA_BANK").toString();
            //String NO_REK = jvCommon.fnGetProperty("NO_REK").toString();

            jvCommon jvc = new jvCommon();

            Hashtable htDataPemda = new Hashtable();
            String strNamaPemda = "";
            String strNamaBidang = "";
            String strAlamatPemda = "";
            String strKotamadyaPemda = "";
            String strKodePos = "";
            String strTelepon = "";
            String strFacsimile = "";
            String strCekBtSimpan = "0";
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

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date todayDate = new java.util.Date();
            String strToday = sdf.format(todayDate);
            String strDate = strToday.substring(0, 2);
            String strMonth = strToday.substring(3, 5);
            String strYear = strToday.substring(6, 10);

            Calendar cal = new GregorianCalendar();
            int intDayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // jml hari dalam sebulan
            int intDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // jml hari dalam seminggu
            int intMonth = cal.get(Calendar.MONTH);
            int intYear = cal.get(Calendar.YEAR);

            String strTglTerimaUang = "";
            if (request.getSession().getAttribute("htRincianAnggaran") != null) {
                //strTglTerimaUang = request.getSession().getAttribute("strTglAnggaran").toString();
                Hashtable htRincianAnggaran = (Hashtable) request.getSession().getAttribute("htRincianAnggaran");
                String[] strArrayBiaya = (String[]) htRincianAnggaran.get(String.valueOf(1));
                strTglTerimaUang = strArrayBiaya[3];
                java.util.Date dtTglTerimaUang = jvc.fnStr2Date(strTglTerimaUang, "yyyy-MM-dd");
                strTglTerimaUang = jvc.fnDate2Str(dtTglTerimaUang, "dd/MM/yyyy");
                strCekBtSimpan = "1";
            } else {
                strTglTerimaUang = strToday;
            }

            String strThnAnggaran = "";
            if (request.getSession().getAttribute("strThnAnggaran") != null) {
                strThnAnggaran = request.getSession().getAttribute("strThnAnggaran").toString();
            } else {
                strThnAnggaran = String.valueOf(intYear);
            }

            String strNamaBendahara = request.getSession().getAttribute("strNamaBendahara").toString();
            String strNIPBendahara = request.getSession().getAttribute("strNIPBendahara").toString();

            String strNamaPejabat = "";
            if (request.getSession().getAttribute("strNamaPejabat") != null) {
                strNamaPejabat = request.getSession().getAttribute("strNamaPejabat").toString();
            }
            String strNIPPejabat = "";
            if (request.getSession().getAttribute("strNIPPejabat") != null) {
                strNIPPejabat = request.getSession().getAttribute("strNIPPejabat").toString();
            }

            int intAwThn = Integer.parseInt(jvc.fnGetProperty("AWAL_THN_ANGGARAN"));
            int intAkThn = Integer.parseInt(jvc.fnGetProperty("AKHIR_THN_ANGGARAN"));
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

                    document.main_form.mode.value = 3;
                    document.main_form.method = "POST";
                    document.main_form.action = "srvAnggaran";
                    document.main_form.submit();
                } else {
                    alert('Anda Belum Memilih Pejabat Pengguna Anggaran!');
                }
            }

            function fnSimpan() {
                var vIdxThnAnggaran = document.main_form.slctTahun.selectedIndex;
                var vThnAnggaran = document.main_form.slctTahun[vIdxThnAnggaran].value;
                document.main_form.hidThnAnggaran.value = vThnAnggaran;

                document.main_form.mode.value = 1;
                document.main_form.method = "POST";
                document.main_form.action = "srvAnggaran";
                document.main_form.submit();
            }

            function fnUbah() {
                var vText = document.main_form.btEdit.value;
                if (vText == 'Ubah') {
                    document.main_form.txtUbah.value = "1";
                    document.main_form.btSimpan.disabled = false;
                    document.main_form.btEdit.value = "Batal";
                } else if (vText == 'Batal') {
                    document.main_form.txtUbah.value = "";
                    document.main_form.btSimpan.disabled = true;
                    document.main_form.btEdit.value = "Ubah";
                }
            }

            function fnChangeTahun() {
                var vIdxTahun = document.getElementById("slctTahun").selectedIndex;
                var vTahun = document.getElementById("slctTahun")[vIdxTahun].value;
                document.main_form.hidThnAnggaran.value = vTahun;
                document.main_form.mode.value = 2;
                document.main_form.target = "_parent"
                document.main_form.method = "POST";
                document.main_form.action = "srvAnggaran";
                document.main_form.submit();
            }
        </script>
    </head>
    <body onload="fnChangePejabat();" style="margin: 0">
        <form name="main_form">
            <input type="hidden" name="mode" id="mode">
            <input type="hidden" name="hidTotal" id="hidTotal" value="<%= intTotal%>">
            <input type="hidden" name="hidTerbilang" id="hidTerbilang">
            <input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth%>">
            <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight%>">
            <input type="hidden" name="hidLastElement" id="hidLastElement" value="<%= strLastElement%>">

            <input style="display: none;" type="text" name="hidNamaPejabat" id="hidNamaPejabat">
            <input style="display: none;" type="text" name="hidJabatanPejabat" id="hidJabatanPejabat">
            <input style="display: none;" type="text" name="hidNIPPejabat" id="hidNIPPejabat">
            <input type="hidden" name="hidNamaBendahara" id="hidNamaBendahara" value="<%= strNamaBendahara%>">
            <input type="hidden" name="hidNIPBendahara" id="hidNIPBendahara" value="<%= strNIPBendahara%>">
            <input type="hidden" name="hidThnAnggaran" id="hidThnAnggaran" value="<%= strThnAnggaran%>">

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

            <input type="hidden" name="hidTglTerima" id="hidTglTerima" value="<%= strTglTerimaUang%>">

            <table border="0" width="100%" cellpadding="1" cellspacing="1">
                <tr valign="top">
                    <td colspan="3">&nbsp;</td>
                </tr>

                <tr valign="middle">
                    <td colspan="3" align="center">
                        <fieldset>
                        <table border="0">
                            <tr valign="top">
                                <td colspan="3" align="center">
                                    <font class="NmDinas">PEMERINTAH <%= strNamaPemda.toUpperCase()%></font><br>
                                    <font class="NmPemda">URAIAN PENERIMAAN ANGGARAN</font>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="3" align="center">Tahun Anggaran&nbsp;
                                    <select onchange="fnChangeTahun()" name="slctTahun" id="slctTahun" onfocus="fnLastElement(this.name)">
                                        <%
                                                        for (int a = intAwThn; a <= intAkThn; a++) {
                                        %>
                                        <option value="<%= a%>" <%= (String.valueOf(a).equals(strThnAnggaran)) ? "SELECTED" : ""%>><%= a%>
                                            <%
                                                        }
                                            %>
                                    </select>
                                </td>
                            </tr>
                        </table>
                        </fieldset>
                    </td>
                </tr>

                <tr valign="top">
                    <td colspan="3">&nbsp;</td>
                </tr>

                <tr>
                    <td align="center" colspan="3">

                        <table width="100%" border="1" cellpadding="1" cellspacing="1">
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2">
                                        <tr class="JUDUL2">
                                            <td width="50">NO</td>
                                            <td>KODE REKENING</td>
                                            <td width="65%">URAIAN RINCIAN OBYEK</td>
                                            <td>JUMLAH (Rp)</td>
                                        </tr>

                                        <%
                                                    String strJumlah = "0";
                                                    Double dblTotal = 0.0;
                                                    String strInputStyle = "";
                                                    String strFontWeight = "";
                                                    if (request.getSession().getAttribute("htRincian") != null) {
                                                        Hashtable htRincian = (Hashtable) request.getSession().getAttribute("htRincian");
                                                        int intHtRincian = htRincian.size();
                                                        //System.out.println("intHtRincian: " + intHtRincian);
                                                        int rowNum = 1;
                                                        for (int a = 1; a <= intHtRincian; a++) {
                                                            String[] strArray = (String[]) htRincian.get(String.valueOf(a));
                                                            if (strArray[0].trim().length() <= 5) {
                                                                strInputStyle = "style=\"visibility: hidden;\"";
                                                                strFontWeight = "style=\"font-weight: bold;\"";
                                                            } else {
                                                                strInputStyle = "style=\"text-align: right;\"";
                                                                strFontWeight = "style=\"font-weight: normal;\"";
                                                            }
                                                            if (request.getSession().getAttribute("htRincianAnggaran") != null) {
                                                                Hashtable htRincianAnggaran = (Hashtable) request.getSession().getAttribute("htRincianAnggaran");
                                                                int inthtRincianAnggaran = htRincianAnggaran.size();
                                                                //System.out.println("inthtRincianAnggaran: " + inthtRincianAnggaran);
                                                                for (int b = 1; b <= inthtRincianAnggaran; b++) {
                                                                    String[] strArrayBiaya = (String[]) htRincianAnggaran.get(String.valueOf(b));
                                                                    if (strArray[0].equalsIgnoreCase(strArrayBiaya[1])) {
                                                                        strJumlah = strArrayBiaya[2];
                                                                    }
                                                                }
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
                                            <td align="center"><%= a%></td>
                                            <td align="left"><%= strArray[0]%></td>
                                            <td align="left" <%= strFontWeight%>><%= strArray[1]%></td>
                                            <td align="right">
                                                <input type="hidden" name="txtKodeRek_<%= a%>" id="txtKodeRek_<%= a%>" value="<%= strArray[0]%>"/>
                                                <input <%= strInputStyle %> type="text" name="txtAnggaran_<%= a%>" id="txtAnggaran_<%= a%>"
                                                                            onfocus="delContent('txtAnggaran_<%= a %>', this.value);"
                                                                            onkeypress="return filterInput(1, event)"
                                                                            onkeyup="this.value = formatAngka(this);"
                                                                            onBlur="this.value = fnFormatNum(this.value);"
                                                                            value="<%= jvc.fnFormatNumberInd(strJumlah) %>"/>
                                            </td>
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
                                            <td align="right">
                                                <input type="text" id="txtTotal" name="txtTotal"
                                                       style="text-align: right"
                                                       readonly value="<%= jvc.fnFormatNumberInd(BigDecimal.valueOf(dblTotal).toPlainString()) %>"/>
                                            </td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
				Uang tersebut diterima tanggal:&nbsp;
                        <input type="text" readonly value="<%= strTglTerimaUang%>" name="txtTglAwal" id="txtTglAwal" maxlength="10" size="10">
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
                        <button type="button" <%= (strCekBtSimpan.equalsIgnoreCase("1")?"DISABLED":"") %> name="btSimpan" id="btSimpan" onclick="fnSimpan()">Simpan</button>
                        <button type="button" name="btEdit" id="btEdit" onclick="fnUbah()">Ubah</button>
                        <!--
				<button type="button" name="btCetak" id="btCetak" onclick="fnCetak()">Cetak</button>
				 -->
                        <input style="display: none;" type="text" name="txtUbah" id="txtUbah"/>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>