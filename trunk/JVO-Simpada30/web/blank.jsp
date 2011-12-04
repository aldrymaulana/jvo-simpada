<%-- 
    Document   : blank
    Created on : 16 Okt 11, 0:29:52
    Author     : Sharkie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.tech.master.common.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            Hashtable htDataPemda99 = new Hashtable();
            String strNamaPemda99 = "";
            String strNamaBidang99 = "";
            String strAlamatPemda99 = "";
            String strKotamadyaPemda99 = "";
            String strKodePos99 = "";
            String strTelepon99 = "";
            String strFacsimile99 = "";
            if (request.getSession().getAttribute("htDataPemda") != null) {
                htDataPemda99 = (Hashtable) request.getSession().getAttribute("htDataPemda");
                int intDataPemda99 = htDataPemda99.size();
                for (int a = 1; a <= intDataPemda99; a++) {
                    //String sqlQuery = "SELECT 0 mPemerintah.Daerah, 1 mPemerintah.Bidang, 2 mPemerintah.Alamat, " +
                    //              "3 mPemerintah.[No], 4 mPemerintah.RT, 5 mPemerintah.RW, 6 mPemerintah.RK, 7 mPemerintah.Kelurahan, " +
                    //              "8 mPemerintah.Kecamatan, 9 mPemerintah.Kabupaten, 10 mPemerintah.KodePos, " +
                    //              "11 mPemerintah.Telepon, 12 mPemerintah.Facs FROM mPemerintah";
                    String[] strArray = (String[]) htDataPemda99.get(String.valueOf(a));
                    strNamaPemda99 = strArray[0].toUpperCase();
                    strNamaBidang99 = strArray[1].toUpperCase();
                    strAlamatPemda99 = strArray[2] + " No " + strArray[3] + ", RT/RW " + strArray[4] + "/" + strArray[5];
                    strKotamadyaPemda99 = strArray[9];
                    strKodePos99 = strArray[10];
                    strTelepon99 = strArray[11];
                    strFacsimile99 = strArray[12];
                }
            }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/simpada.css" rel="stylesheet">
        <script type="text/javascript" src="js/jsUtility.js"></script>
    </head>
    <body>
        <fieldset>
            <table width="100%" height="100%">
                <tr valign="middle">
                    <td align="center">
                        <table width="100%" height="100%">
                            <tr valign="middle">
                                <td align="center">
                                    <font class="AlmDinas">JVO-SIMPADA v02</font><br>
                                    <font class="NmDinas">Sistem Informasi Pendapatan dan Aset Daerah</font><br>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <!--</fieldset>
            <br><br>
            <fieldset>
                <div align="center">-->
            <table width="100%" height="100%">
                <tr valign="middle">
                    <td align="center">
                        <table width="100%" height="100%">
                            <tr valign="middle">
                                <td align="center">
                                    <font class="NmPemda">PEMERINTAH&nbsp;<%= strNamaPemda99%></font><br>
                                    <font class="NmDinas"><%= strNamaBidang99%></font><br>
                                    <font class="AlmDinas"><%= strAlamatPemda99%>,&nbsp;<%= strKotamadyaPemda99%>&nbsp;<%= strKodePos99%></font><br>
                                    <font class="AlmDinas">Telp:&nbsp<%= strTelepon99%>, Facs:&nbsp;<%= strFacsimile99%></font>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <!--</div>-->
        </fieldset>
        <br><br>
    </body>
</html>
