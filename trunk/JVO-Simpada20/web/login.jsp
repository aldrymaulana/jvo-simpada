<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, jvo.simpada.common.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	//System.out.println("test 1");

    String strWidth = request.getParameter("vWidth").toString();
    String strHeight = request.getParameter("vHeight").toString();
    int intWidth = Integer.parseInt(strWidth);
    int intHeight = Integer.parseInt(strHeight);
    
    //System.out.println("test 2");
    
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
    
    System.out.println("test 3");
    
    String strErrMsg = "";
    if (request.getSession().getAttribute("ERROR") != null) {
        strErrMsg = request.getSession().getAttribute("ERROR").toString();
    }
    
    System.out.println("test 4");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JVO-SIMPADA</title>
        <link href="css/simpada.css" rel="stylesheet">
        <script type="text/javascript" src="js/jsUtility.js"></script>
        <script type="text/javascript">
            function fnLogin() {
                var vLogin = document.main_form.txtLogin.value;
                var vPassword = document.main_form.txtPassword.value;
                
                document.main_form.hidLogin.value = vLogin;
                document.main_form.hidPassword.value = vPassword;
                                                    
                document.main_form.method = "post";
                document.main_form.mode.value = 1;
                document.main_form.action = "srvLogin";
                document.main_form.submit();
            }
        </script>
    </head>
    <body topmargin="0" leftmargin="0"
          style="background-image: url('image/countryRoad1.jpg');
                 background-repeat: no-repeat;
                 background-attachment: fixed;">
        <form name="main_form">
        <input type="hidden" name="mode" id="mode">
        <input type="hidden" name="hidLogin" id="hidLogin">   
        <input type="hidden" name="hidPassword" id="hidPassword">
                
        <table width="<%= intWidth*0.98 %>" height="<%= intHeight*0.92 %>" border="1" cellpadding="0" cellspacing="0">
            <tr valign="middle">
                <td align="center">
                    <table width="<%= intWidth*0.98 %>" height="<%= intHeight*0.92 %>" border="0" cellpadding="0" cellspacing="0">
                        <tr valign="top">
                            <td>
                                <%@ include file="header.jsp" %>
                            </td>
                        </tr>
                        <tr height="90%" valign="middle">
                            <td align="center">
                                <table border="0">
                                    <tr>
                                        <td align="center" colspan="4">
                                            <fieldset>
                                            <font class="NmPemda">PEMERINTAH&nbsp;<%= strNamaPemda%></font><br>
                                            <font class="NmDinas"><%= strNamaBidang %></font><br>
                                            <font class="AlmDinas"><%= strAlamatPemda %>,&nbsp;<%= strKotamadyaPemda %>&nbsp;<%= strKodePos %></font><br>
                                            <font class="AlmDinas">Telp:&nbsp;<%= strTelepon %>, Facs:&nbsp;<%= strFacsimile %></font>
                                            </fieldset>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="4">
                                            <fieldset>
                                            <table border="0" width="100%">
                                                <tr>
                                                    <td rowspan="4">
                                                        <img src="image/logo1.gif" height="150" width="119">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="left">Login Id</td>
                                                    <td>&nbsp;:&nbsp;</td>
                                                    <td align="left"><input type="text" name="txtLogin" id="txtLogin"></td>
                                                </tr>
                                                <tr>
                                                    <td align="left">Password</td>
                                                    <td>&nbsp;:&nbsp;</td>
                                                    <td align="left"><input type="password" name="txtPassword" id="txtPassword"></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="3">
                                                        <button type="button" onclick="javascript=fnLogin();">Login</button>
                                                    </td>
                                                </tr>
                                            </table>
                                            </fieldset>
                                        </td>
                                    </tr>
                                    <!--
                                    <tr>
                                        <td colspan="4">
                                            <font class="error"><--%= strErrMsg%></font>
                                        </td>
                                    </tr>
                                    -->
                                </table>
                            </td>
                        </tr>
                        <tr valign="top">
                            <td>
                                <%@ include file="footer.jsp" %>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        </form>
    </body>
</html>