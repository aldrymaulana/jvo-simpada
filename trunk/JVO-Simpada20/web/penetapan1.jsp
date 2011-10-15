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
    
    //int intJmlSI = 0;
    int intJmlSI = 1;
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
    String strKdNPWP = jvc.fnGetValue(ifcp.getStrKdNPWP());
    String strNPWPD = jvc.fnGetValue(ifcp.getStrNPWPD());
    String strWilNPWP = jvc.fnGetValue(ifcp.getStrWilNPWP());
    String strKdNPWR = jvc.fnGetValue(ifcp.getStrKdNPWR());
    String strNPWRD = jvc.fnGetValue(ifcp.getStrNPWRD());
    String strWilNPWR = jvc.fnGetValue(ifcp.getStrWilNPWR());
    String strKelurahanPemilik = jvc.fnGetValue(ifcp.getStrKelurahan());
    String strKecamatanPemilik = jvc.fnGetValue(ifcp.getStrKecamatan());
    String strKabupatenPemilik = jvc.fnGetValue(ifcp.getStrKabupaten());
    String strBidangUsaha = jvc.fnGetValue(ifcp.getStrBidUsaha());
    String strJnsPajak = jvc.fnGetValue(ifcp.getStrPajak());
    String strJnsRetribusi = jvc.fnGetValue(ifcp.getStrRetribusi());
    String strKdPemilik = jvc.fnGetValue(ifcp.getStrKodePemilik());
%>

<%@page import="java.text.DecimalFormat"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/simpada.css" rel="stylesheet">
        <script type="text/javascript" src="js/jsUtility.js"></script> 
        <script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/calendar-en.js"></script>
		<script type="text/javascript" src="js/calendar-setup.js"></script>
        <script type="text/javascript">
            function fnGetAllElement() {
                var vJmlElement = document.main_form.elements.length;
                for (var w=0; w<=vJmlElement-1; w++) {
                    var vElementName = '';
                    if (document.main_form.elements[w].name != null) {
                        vElementName = document.main_form.elements[w].name;
                    }
                    document.main_form.elements[w].title = vElementName;
                    document.main_form.elements[w].readOnly = true;
                }
            }
            
            function fnLastElement(lastElement) {
                document.main_form.hidLastElement.value = lastElement;
            }

            function fnGotoLastElement() {
                var vLastElement = document.main_form.hidLastElement.value;
                document.getElementById(vLastElement).focus();
            }
            
            function fnOpenSkpd(vKode) {
                var lVarWidth = 0.75 * screen.availWidth;
                var lVarHeight = 0.75 * screen.availHeight;
                var vXPos = (screen.availWidth - lVarWidth) / 2;
                var vYPos = (screen.availHeight - lVarHeight) / 2;
                var winprops = 'width='+lVarWidth+',height='+lVarHeight+',top='+vYPos+',left='+vXPos+',status=yes,resizable=yes,toolbar=no,menubar=no,scrollbars=yes,location=no,titlebar=no,hotkeys=no';
                window.open('skpd1.jsp?vWidth='+lVarWidth+'&vHeight='+lVarHeight+'&vKode='+vKode, 'wSkpd', winprops);
            }

            function fnOpenSkrd(vKode) {
                var lVarWidth = 0.75 * screen.availWidth;
                var lVarHeight = 0.75 * screen.availHeight;
                var vXPos = (screen.availWidth - lVarWidth) / 2;
                var vYPos = (screen.availHeight - lVarHeight) / 2;
                var winprops = 'width='+lVarWidth+',height='+lVarHeight+',top='+vYPos+',left='+vXPos+',status=yes,resizable=yes,toolbar=no,menubar=no,scrollbars=yes,location=no,titlebar=no,hotkeys=no';
                window.open('skrd.jsp?vWidth='+lVarWidth+'&vHeight='+lVarHeight+'&vKode='+vKode, 'wSkpd', winprops);
            }

            function fnOpenPAD(vKode) {
                var lVarWidth = 0.75 * screen.availWidth;
                var lVarHeight = 0.75 * screen.availHeight;
                var vXPos = (screen.availWidth - lVarWidth) / 2;
                var vYPos = (screen.availHeight - lVarHeight) / 2;
                var winprops = 'width='+lVarWidth+',height='+lVarHeight+',top='+vYPos+',left='+vXPos+',status=yes,resizable=yes,toolbar=no,menubar=no,scrollbars=yes,location=no,titlebar=no,hotkeys=no';
                window.open('pad.jsp?vWidth='+lVarWidth+'&vHeight='+lVarHeight+'&vKode='+vKode, 'wPad', winprops);
            }
        </script>
    </head>
    <body onload="fnGetAllElement();fnGotoLastElement();">
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
        <input type="hidden" name="hidKdPemilik" id="hidKdPemilik" value="<%= strKdPemilik %>">
        <input type="hidden" name="hidKdNPWP" id="hidKdNPWP" value="<%= strKdNPWP %>">
        <input type="hidden" name="hidNPWPD" id="hidNPWPD" value="<%= strNPWPD %>">
        <input type="hidden" name="hidWilNPWP" id="hidWilNPWP" value="<%= strWilNPWP %>">
        <input type="hidden" name="hidKdNPWR" id="hidKdNPWR" value="<%= strKdNPWP %>">
        <input type="hidden" name="hidNPWRD" id="hidNPWRD" value="<%= strNPWRD %>">
        <input type="hidden" name="hidWilNPWR" id="hidWilNPWR" value="<%= strWilNPWR %>">
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
                                    Nomor Formulir<br>
                                    <input type="text" readonly size="20" name="txtNoForm" id="txtNoForm" tabindex="1" onfocus="fnLastElement(this.name)" value="<%= strNoForm %>">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">
                                    FORMULIR PENETAPAN PAJAK / RETRIBUSI / PAD
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" valign="top" align="left">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <tr align="left">
                                            <td>1.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td>NPWPD</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td>
                                                <input type="text" readonly size="5" value="<%= strKdNPWP %>" name="txtKdNPWP" id="txtKdNPWP" onfocus="fnLastElement(this.name)">
                                                <input type="text" size="10" value="<%= strNPWPD %>" name="txtNPWPD" id="txtNPWPD" tabindex="27" onfocus="fnLastElement(this.name)">
                                                <input type="text" readonly size="5" value="<%= strWilNPWP %>" name="txtWilNPWP" id="txtWilNPWP" onfocus="fnLastElement(this.name)">
                                            </td>
                                        </tr>
                                        <tr align="left">
                                            <td>2.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td>NPWRD</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td>
                                                <input type="text" readonly size="5" value="<%= strKdNPWR %>" name="txtKdNPWR" id="txtKdNPWR" onfocus="fnLastElement(this.name)">
                                                <input type="text" size="10" value="<%= strNPWRD %>" name="txtNPWRD" id="txtNPWRD" tabindex="28" onfocus="fnLastElement(this.name)">
                                                <input type="text" readonly size="5" value="<%= strWilNPWR %>" name="txtWilNPWR" id="txtWilNPWR" onfocus="fnLastElement(this.name)">
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">KETERANGAN BADAN USAHA</td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" valign="top" align="left">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <tr valign="top">
                                            <td>3.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">
                                            	Nama Badan / Merk Usaha<br>
                                                <input type="text" value="<%= strNamaBU %>" name="txtNamaBU" id="txtNamaBU" tabindex="2" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>4.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">
                                            	Alamat (Photo Copy Surat Keterangan domisili dilampirkan)
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">
                                            	Jalan&nbsp;
                                            	<input type="text" value="<%= strJalanBU %>" name="txtJalanBU" id="txtJalanBU" tabindex="3" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">
                                                &nbsp;&nbsp;&nbsp;&nbsp;No&nbsp;<input type="text" value="<%= strNoBU %>" name="txtNoBU" id="txtNoBU" tabindex="4" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                                RT&nbsp;<input type="text" value="<%= strRTBU %>" name="txtRTBU" id="txtRTBU" tabindex="5" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                                RW&nbsp;<input type="text" value="<%= strRWBU %>" name="txtRWBU" id="txtRWBU" tabindex="6" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                                RK&nbsp;<input type="text" value="<%= strRKBU %>" name="txtRKBU" id="txtRKBU" tabindex="7" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kelurahan</td>
                                            <td colspan="2" align="left">
                                                <select style="width:150px" disabled name="slctKelurahanBU" id="slctKelurahanBU" onchange="fnChangeKelurahan(this.name,'slctKecamatanBU','slctKabupatenBU')" tabindex="8" onfocus="fnLastElement(this.name)">
                                                    <option>
                                                    <% 
                                                        Hashtable htKelurahan = new Hashtable();
                                                        if (request.getSession().getAttribute("htKelurahan") != null) {
                                                            htKelurahan = (Hashtable) request.getSession().getAttribute("htKelurahan");
                                                            int intKelurahan = htKelurahan.size();
                                                            for (int a=1; a<=intKelurahan; a++) {
                                                                String[] strKelurahan = (String[]) htKelurahan.get(String.valueOf(a));
                                                                %>
                                                                <option <%= (strKelurahanBU.equalsIgnoreCase(""+strKelurahan[0]+","+strKelurahan[1]+","+strKelurahan[2]+"")?"SELECTED":"")%> value="<%= strKelurahan[0] %>,<%= strKelurahan[1] %>,<%= strKelurahan[2] %>"><%= strKelurahan[3] %>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kecamatan</td>
                                            <td colspan="2" align="left">
                                                <!--
                                                <input type="text" name="txtKecamatanBU" id="txtKecamatanBU" tabindex="9" onfocus="fnLastElement(this.name)" size="100">
                                                -->
                                                <select style="width:150px" disabled name="slctKecamatanBU" id="slctKecamatanBU" onchange="fnChangeKecamatan(this.name,'slctKabupatenBU')" tabindex="9" onfocus="fnLastElement(this.name)">
                                                    <option>
                                                    <% 
                                                        Hashtable htKecamatan = new Hashtable();
                                                        if (request.getSession().getAttribute("htKecamatan") != null) {
                                                            htKecamatan = (Hashtable) request.getSession().getAttribute("htKecamatan");
                                                            int intKecamatan = htKecamatan.size();
                                                            for (int a=1; a<=intKecamatan; a++) {
                                                                String[] strKecamatan = (String[]) htKecamatan.get(String.valueOf(a));
                                                                %>
                                                    <option <%= (strKecamatanBU.equalsIgnoreCase(""+strKecamatan[0]+","+strKecamatan[1]+"")?"SELECTED":"")%> value="<%= strKecamatan[0] %>,<%= strKecamatan[1] %>"><%= strKecamatan[2] %>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kabupaten / Kotamadya</td>
                                            <td colspan="2" align="left">
                                                <select style="width:150px" disabled name="slctKabupatenBU" id="slctKabupatenBU" onchange="fnChangeKabupaten(this.name)" tabindex="10" onfocus="fnLastElement(this.name)">
                                                    <option>
                                                    <% 
                                                        Hashtable htKabupaten = new Hashtable();
                                                        if (request.getSession().getAttribute("htKabupaten") != null) {
                                                            htKabupaten = (Hashtable) request.getSession().getAttribute("htKabupaten");
                                                            int intKabupaten = htKabupaten.size();
                                                            for (int a=1; a<=intKabupaten; a++) {
                                                                String[] strKabupaten = (String[]) htKabupaten.get(String.valueOf(a));
                                                                %>
                                                    <option <%= (strKabupatenBU.equalsIgnoreCase(""+strKabupaten[0]+"")?"SELECTED":"")%> value="<%= strKabupaten[0] %>"><%= strKabupaten[1] %>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Nomor Telepon</td>
                                            <td colspan="2" align="left">
                                                <input style="width:150px" type="text" value="<%= strTelpBU %>" name="txtTelpBU" id="txtTelpBU" tabindex="11" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kode Pos</td>
                                            <td colspan="2" align="left">
                                                <input style="width:150px" type="text" value="<%= strKdPosBU %>" name="txtKdPosBU" id="txtKdPosBU" tabindex="12" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>5.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">Surat Ijin yang dimiliki (photo copy Surat Ijin harap dilampirkan)</td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">
                                                <table border="0" width="100%" cellpadding="2" cellspacing="2">
                                                	<tr>
                                                		<td align="center" bgcolor="black"><font style="color: white">Jenis Surat Ijin</font></td>
                                                		<td align="center" bgcolor="black"><font style="color: white">No Surat Ijin</font></td>
                                                		<td align="center" bgcolor="black"><font style="color: white">Tgl Berlaku</font></td>
                                                	</tr>
                                                    <%
                                                    String[] strArrSI = new String[3];
                                                    String strJnsIjin = "";
                                                    String strNoIjin = "";
                                                    String strTglIjin = "";
                                                    if (request.getSession().getAttribute("htSI") != null) {
                                                        Hashtable htSI = (Hashtable) request.getSession().getAttribute("htSI");
                                                        strArrSI = (String[]) htSI.get(String.valueOf(1));
                                                        strJnsIjin = jvc.fnGetValue(strArrSI[0]);
                                                        strNoIjin = jvc.fnGetValue(strArrSI[1]);
                                                        strTglIjin = jvc.fnGetValue(strArrSI[2]);
                                                    }
                                                    %>
                                                    <tr>
                                                        <td><input type="text" value="Tempat Usaha" name="txtJnsIjin_1" id="txtJnsIjin_1" readonly></td>
                                                        <td><input type="text" value="<%= strNoIjin %>" name="txtNoIjin_1" id="txtNoIjin_1"></td>
                                                        <td><input type="text" value="<%= strTglIjin %>" name="txtTglIjin_1" id="txtTglIjin_1" maxlength="10" size="10">
                                                        &nbsp;<button type="reset" disabled id="btTglIjin_1">...</button></td>
                                                    </tr>
                                                        <script type="text/javascript">
                                                            Calendar.setup({
                                                                inputField     :    "txtTglIjin_1",      // id of the input field
                                                                ifFormat       :    "%d/%m/%Y",       // format of the input field
                                                                showsTime      :    false,            // will display a time selector
                                                                button         :    "btTglIjin_1",   // trigger for the calendar (button ID)
                                                                singleClick    :    true,           // double-click mode
                                                                step           :    1                // show all years in drop-down boxes (instead of every other year as default)
                                                            });
                                                        </script>
                                                    
                                                    <%
                                                        //if (intJmlSI > 1) {
                                                            for (int a=1; a<=intJmlSI; a++) {
                                                                System.out.println("JSP, Cek 5a");
                                                                String[] strArrSI1 = new String[3];
                                                                String strJnsIjin1 = "";
                                                                String strNoIjin1 = "";
                                                                String strTglIjin1 = "";
                                                                if (request.getSession().getAttribute("htSI") != null) {
                                                                    System.out.println("JSP, Cek 5b");
                                                                    Hashtable htSI = (Hashtable) request.getSession().getAttribute("htSI");
                                                                    int intHtSI = htSI.size();
                                                                    if ((a+1) <= intHtSI) {
                                                                        strArrSI1 = (String[]) htSI.get(String.valueOf(a+1));
                                                                        System.out.println("JSP, Cek 5c");
                                                                        strJnsIjin1 = jvc.fnGetValue(strArrSI1[0]);
                                                                        strNoIjin1 = jvc.fnGetValue(strArrSI1[1]);
                                                                        strTglIjin1 = jvc.fnGetValue(strArrSI1[2]);
                                                                        System.out.println("JSP, Cek 5d");
                                                                    }
                                                                }
                                                    %>
                                                    <tr>
                                                        <td><input type="text" value="<%= strJnsIjin1 %>" name="txtJnsIjin_<%= a+1 %>" id="txtJnsIjin_<%= a+1 %>"></td>
                                                        <td><input type="text" value="<%= strNoIjin1 %>" name="txtNoIjin_<%= a+1 %>" id="txtNoIjin_<%= a+1 %>"></td>
                                                        <td><input type="text" value="<%= strTglIjin1 %>" name="txtTglIjin_<%= a+1 %>" id="txtTglIjin_<%= a+1 %>" maxlength="10" size="10">
                                                        &nbsp;<button type="reset" disabled id="btTglIjin_<%= a+1 %>">...</button></td>
                                                    </tr>
                                                        <script type="text/javascript">
                                                            Calendar.setup({
                                                                inputField     :    "txtTglIjin_<%= a+1 %>",      // id of the input field
                                                                ifFormat       :    "%d/%m/%Y",       // format of the input field
                                                                showsTime      :    false,            // will display a time selector
                                                                button         :    "btTglIjin_<%= a+1 %>",   // trigger for the calendar (button ID)
                                                                singleClick    :    true,           // double-click mode
                                                                step           :    1                // show all years in drop-down boxes (instead of every other year as default)
                                                            });
                                                        </script>
                                                    <%
                                                            }
                                                        //}
                                                    %>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>6.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">Bidang Usaha</td>
                                            <!--<td>&nbsp;&nbsp;&nbsp;</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>-->
                                        </tr>
                                        <%
                                            Hashtable htBidUsaha = new Hashtable();
                                            if (request.getSession().getAttribute("htBidUsaha") != null) {
                                                htBidUsaha = (Hashtable) request.getSession().getAttribute("htBidUsaha");
                                                int intBidUsaha = htBidUsaha.size();
                                                for (int a=1; a<=intBidUsaha; a++) {
                                                    String[] strArray = (String[]) htBidUsaha.get(String.valueOf(a));
                                                    %>
                                                    
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3"><input type="radio" onclick="return false;" <%= (strBidangUsaha.equals(strArray[0])?"CHECKED":"DISABLED") %> name="chkBidUsaha" id="chkBidUsaha" value="<%= strArray[0] %>">
                                                &nbsp;<%= strArray[1] %></td>
                                        </tr>
                                                    
                                                    <%
                                                }
                                            }
                                        %>
                                        
                                        <tr style="display: none;" valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3"><input type="radio" onclick="return false;" <%= (strBidangUsaha.equals(jvc.fnLRPad("RPAD", String.valueOf(intBidUsaha0), "0", 5))?"CHECKED":"DISABLED") %> name="chkBidUsaha" id="chkBidUsaha" value="<%= jvc.fnLRPad("RPAD", String.valueOf(intBidUsaha0), "0", 5) %>">
                                                &nbsp;Lainnya yang tidak termasuk bidang tersebut di atas, yaitu
                                                &nbsp;<input type="text" name="txtBidUsaha" id="txtBidUsaha"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">KETERANGAN PEMILIK / PENGELOLA</td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" valign="top" align="left">
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <tr valign="top">
                                            <td>7.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">
                                            	Nama Pemilik / Pengelola<br>
                                                <input type="text" value="<%= strNamaPemilik %>" name="txtNama" id="txtNama" tabindex="15" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>8.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">Jabatan<br>
                                            	<input type="text" value="<%= strJabatanPemilik %>" name="txtJabatan" id="Jabatan" tabindex="16" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>9.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">
                                            	Alamat Tempat Tinggal
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">
                                            	Jalan&nbsp;
                                            	<input type="text" value="<%= strJalan %>" name="txtJalan" id="txtJalan" tabindex="17" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">
                                                &nbsp;&nbsp;&nbsp;&nbsp;No&nbsp;<input type="text" value="<%= strNo %>" name="txtNo" id="txtNo" tabindex="18" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                                RT&nbsp;<input type="text" value="<%= strRT %>" name="txtRT" id="txtRT" tabindex="19" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                                RW&nbsp;<input type="text" value="<%= strRW %>" name="txtRW" id="txtRW" tabindex="20" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                                RK&nbsp;<input type="text" value="<%= strRK %>" name="txtRK" id="txtRK" tabindex="21" onfocus="fnLastElement(this.name)" size="10">&nbsp;
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kelurahan</td>
                                            <td colspan="2" align="left">
                                                <select style="width:150px" disabled name="slctKelurahan" id="slctKelurahan" onchange="fnChangeKelurahan(this.name,'slctKecamatan','slctKabupaten')" tabindex="22" onfocus="fnLastElement(this.name)">
                                                    <option>
                                                    <% 
                                                        //Hashtable htKelurahan = new Hashtable();
                                                        if (request.getSession().getAttribute("htKelurahan") != null) {
                                                            htKelurahan = (Hashtable) request.getSession().getAttribute("htKelurahan");
                                                            int intKelurahan = htKelurahan.size();
                                                            for (int a=1; a<=intKelurahan; a++) {
                                                                String[] strKelurahan = (String[]) htKelurahan.get(String.valueOf(a));
                                                                %>
                                                    <option <%= (strKelurahanPemilik.equalsIgnoreCase(""+strKelurahan[0]+","+strKelurahan[1]+","+strKelurahan[2]+"")?"SELECTED":"")%> value="<%= strKelurahan[0] %>,<%= strKelurahan[1] %>,<%= strKelurahan[2] %>"><%= strKelurahan[3] %>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kecamatan</td>
                                            <td colspan="2" align="left">
                                                <select style="width:150px" disabled name="slctKecamatan" id="slctKecamatan" onchange="fnChangeKecamatan(this.name,'slctKabupaten')" tabindex="23" onfocus="fnLastElement(this.name)">
                                                    <option>
                                                    <% 
                                                        //Hashtable htKecamatan = new Hashtable();
                                                        if (request.getSession().getAttribute("htKecamatan") != null) {
                                                            htKecamatan = (Hashtable) request.getSession().getAttribute("htKecamatan");
                                                            int intKecamatan = htKecamatan.size();
                                                            for (int a=1; a<=intKecamatan; a++) {
                                                                String[] strKecamatan = (String[]) htKecamatan.get(String.valueOf(a));
                                                                %>
                                                    <option <%= (strKecamatanPemilik.equalsIgnoreCase(""+strKecamatan[0]+","+strKecamatan[1]+"")?"SELECTED":"")%> value="<%= strKecamatan[0] %>,<%= strKecamatan[1] %>"><%= strKecamatan[2] %>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kabupaten / Kotamadya</td>
                                            <td colspan="2" align="left">
                                                <!--
                                                <input type="text" name="txtKabupaten" id="txtKabupaten" tabindex="24" onfocus="fnLastElement(this.name)" size="100">
                                                -->
                                                <select style="width:150px" disabled name="slctKabupaten" id="slctKabupaten" onchange="fnChangeKabupaten(this.name)" tabindex="24" onfocus="fnLastElement(this.name)">
                                                    <option>
                                                    <% 
                                                        //Hashtable htKabupaten = new Hashtable();
                                                        if (request.getSession().getAttribute("htKabupaten") != null) {
                                                            htKabupaten = (Hashtable) request.getSession().getAttribute("htKabupaten");
                                                            int intKabupaten = htKabupaten.size();
                                                            for (int a=1; a<=intKabupaten; a++) {
                                                                String[] strKabupaten = (String[]) htKabupaten.get(String.valueOf(a));
                                                                %>
                                                    <option <%= (strKabupatenPemilik.equalsIgnoreCase(""+strKabupaten[0]+"")?"SELECTED":"")%> value="<%= strKabupaten[0] %>"><%= strKabupaten[1] %>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Nomor Telepon</td>
                                            <td colspan="2" align="left">
                                                <input style="width:150px" type="text" value="<%= strTelp %>" name="txtTelp" id="txtTelp" tabindex="25" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>Kode Pos</td>
                                            <td colspan="2" align="left">
                                                <input style="width:150px" type="text" value="<%= strKdPos %>" name="txtKdPos" id="txtKdPos" tabindex="26" onfocus="fnLastElement(this.name)" size="100">
                                            </td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" align="center">KEWAJIBAN PAJAK / RETRIBUSI / PAD</td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td colspan="3" valign="top" align="left">
                                    <table border="0" cellpadding="0" cellspacing="0">

                                        <tr valign="top">
                                            <td>10.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">Kewajiban Pajak</td>
                                        </tr>
                                        <tr valign="top">
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">
                                                <table border="0" cellpadding="0" cellspacing="0" topmargin="0" leftmargin="0">
                                        
                                        <%
                                            Hashtable htPajak = new Hashtable();
                                            if (request.getSession().getAttribute("htPajak") != null) {
                                                htPajak = (Hashtable) request.getSession().getAttribute("htPajak");
                                                int intPajak = htPajak.size();
                                                int intLoop = intPajak;
                                                String[] strArrPajak = (String[]) htPajak.get(String.valueOf(1));
                                                for (int a=1; a<=intLoop; a++) {
                                                    String[] strPajak = (String[]) htPajak.get(String.valueOf(a));
                                                    String strCheckedPajak = "DISABLED";
                                                    String strButtonPajak = "DISABLED";
                                                    if (strJnsPajak.equals(strPajak[0])) {
                                                        strCheckedPajak = "CHECKED onclick=\"return false;\"";
                                                        strButtonPajak = "";
                                                    }
                                                    %>
                                                    <tr>
                                                    	<td><%= a %>.&nbsp;<button type="button" name="btBayar" id="btBayar" <%= strButtonPajak %> onclick="fnOpenSkpd('<%= strPajak[0] %>')">Bayar</button></td>
                                                        <td>&nbsp;</td>
                                                        <td><%= strPajak[1] %></td>
                                                    </tr>
                                                    <%
                                                }
                                            }
                                        %>
                                                </table>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td>11.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">Kewajiban Retribusi</td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">
                                                <table border="0" cellpadding="0" cellspacing="0" topmargin="0" leftmargin="0">
                                        
                                        <%
                                            Hashtable htRetribusi = new Hashtable();
                                            if (request.getSession().getAttribute("htRetribusi") != null) {
                                            	htRetribusi = (Hashtable) request.getSession().getAttribute("htRetribusi");
                                                int intRetribusi = htRetribusi.size();
                                                int intLoop = intRetribusi;
                                                String[] strArrRetribusi = (String[]) htRetribusi.get(String.valueOf(1));
                                                for (int a=1; a<=intLoop; a++) {
                                                    //String[] strRetribusi = new String[strArrRetribusi.length];
                                                    String[] strRetribusi = (String[]) htRetribusi.get(String.valueOf(a));
                                                    String strCheckedRetribusi = "DISABLED";
                                                    String strButtonRetribusi = "DISABLED";
                                                    if (strJnsRetribusi.equals(strRetribusi[0])) {
                                                        strCheckedRetribusi = "CHECKED onclick=\"return false;\"";
                                                        strButtonRetribusi = "";
                                                    }
                                                    %>
                                                    <tr>
                                                    	<td><%= a %>.&nbsp;<button type="button" name="btBayar" id="btBayar" onclick="fnOpenSkrd('<%= strRetribusi[0] %>')">Bayar</button></td>
                                                    	<td>&nbsp;</td>
                                                        <td><%= strRetribusi[1] %></td>
                                                    </tr>
                                                    <%
                                                }
                                            }
                                        %>
                                                </table>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td>12.</td>
                                            <td>&nbsp;&nbsp;&nbsp;</td>
                                            <td colspan="3">Denda Pajak</td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td colspan="3">
                                                <table border="0" cellpadding="0" cellspacing="0" topmargin="0" leftmargin="0">
                                        
                                        <%
                                            Hashtable htPADLain = new Hashtable();
                                            if (request.getSession().getAttribute("htPADLain") != null) {
                                            	htPADLain = (Hashtable) request.getSession().getAttribute("htPADLain");
                                                int intPADLain = htPADLain.size();
                                                int intLoop = intPADLain;
                                                String[] strArrPADLain = (String[]) htPADLain.get(String.valueOf(1));
                                                for (int a=1; a<=intLoop; a++) {
                                                    //String[] strPADLain = new String[strArrPADLain.length];
                                                    String[] strPADLain = (String[]) htPADLain.get(String.valueOf(a));
                                                    //String strCheckedPADLain = "DISABLED";
                                                    //String strButtonPADLain = "DISABLED";
                                                    //if (strJnsPADLain.equals(strPADLain[0])) {
                                                    //    strCheckedPADLain = "CHECKED onclick=\"return false;\"";
                                                    //    strButtonPADLain = "";
                                                    //}
                                                    %>
                                                    <tr>
                                                        <td><%= a %>.&nbsp;<button type="button" name="btBayar" id="btBayar" onclick="fnOpenPAD('<%= strPADLain[0] %>')">Bayar</button></td>
                                                        <td>&nbsp;</td>
                                                        <td><%= strPADLain[1] %></td>
                                                    </tr>
                                                    <%
                                                }
                                            }
                                        %>
                                                </table>
                                            </td>
                                        </tr>
                                        
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
