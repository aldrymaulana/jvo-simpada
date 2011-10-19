<%-- 
    Document   : genHeader
    Created on : 19 Okt 11, 14:43:15
    Author     : Sharkie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jvo.simpada.common.*"%>
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
        for (int a=1; a<=intDataPemda99; a++) {
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
    </head>
    <body>
        <fieldset>
        <table border="0" cellpadding="0" cellspacing="0" topmargin="0" leftmargin="0">
            <tr>
                <td align="left">
                    <font class="NmPemda">PEMERINTAH&nbsp;<%= strNamaPemda99%></font><br>
                    <font class="NmDinas"><%= strNamaBidang99 %></font><br>
                    <font class="AlmDinas"><%= strAlamatPemda99 %>,&nbsp;<%= strKotamadyaPemda99 %>&nbsp;<%= strKodePos99 %></font><br>
                    <font class="AlmDinas">Telp:&nbsp<%= strTelepon99 %>, Facs:&nbsp;<%= strFacsimile99 %></font>
                </td>
            </tr>
        </table>
        </fieldset>
    </body>
</html>
