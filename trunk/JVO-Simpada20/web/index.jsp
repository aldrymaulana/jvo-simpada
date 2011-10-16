<%-- 
    Document   : index
    Created on : Nov 20, 2009, 9:41:52 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    String strWidth = request.getParameter("vWidth").toString();
    String strHeight = request.getParameter("vHeight").toString();
    int intWidth = Integer.parseInt(strWidth);
    int intHeight = Integer.parseInt(strHeight);
    
    String strSource = "blank.jsp";
    if (request.getSession().getAttribute("strSource") != null) {
        strSource = request.getSession().getAttribute("strSource").toString();
    }

    String strIfFrame = "";
    if (request.getSession().getAttribute("strIfFrame") != null) {
        strIfFrame = request.getSession().getAttribute("strIfFrame").toString();
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JVO-SIMPADA</title>
        <link href="css/simpada.css" rel="stylesheet">
        <script type="text/javascript" src="../js/jsUtility.js"></script>
        <script type="text/javascript">
            function fnPendaftaran() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvPendaftaran";
                document.main_form.submit();
            }
            
            function fnUbahData() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 3;
                document.main_form.action = "srvPendaftaran";
                document.main_form.submit();
            }
            
			/**
            function fnPembayaran() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvPembayaran";
                document.main_form.submit();
            }

            function fnPembayaran1() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvPembayaran1";
                document.main_form.submit();
            }
            */

            function fnPenetapan() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvPenetapan";
                document.main_form.submit();
            }

            function fnSetoran() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvSetoran";
                document.main_form.submit();
            }
            
            function fnLaporan() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvLaporan";
                document.main_form.submit();
            }
            
            function fnPengaturan() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvPengaturan";
                document.main_form.submit();
            }
            
            function fnKeluar() {
                document.main_form.target = "_top";
                document.main_form.method = "post";
                document.main_form.mode.value = 2;
                document.main_form.action = "srvLogin";
                document.main_form.submit();
            }

            function fnAnggaran() {
                //document.getElementById("ifrmBody").src = "pendaftaran.jsp";
                document.main_form.method = "post";
                document.main_form.mode.value = 0;
                document.main_form.action = "srvAnggaran";
                document.main_form.submit();
            }
        </script>
    </head>
    <body topmargin="0" leftmargin="0">
        <form name="main_form">
        <input type="hidden" name="mode" id="mode">
        <input type="hidden" name="hidWidth" id="hidWidth" value="<%= intWidth %>">
        <input type="hidden" name="hidHeight" id="hidHeight" value="<%= intHeight %>">    
        <table width="<%= intWidth*0.98 %>" height="<%= intHeight*0.92 %>" border="1" cellpadding="0" cellspacing="0">
            <tr valign="middle">
                <td align="center">
                    <table width="<%= intWidth*0.98 %>" height="<%= intHeight*0.92 %>" border="0" cellpadding="0" cellspacing="0">
                        <tr valign="top">
                            <td>
                                <%@ include file="header.jsp" %>
                            </td>
                        </tr>
                        <tr valign="top">
                            <td align="left">
                                <%@ include file="menu1.jsp" %>
                            </td>
                        </tr>
                        <tr height="90%">
                            <td>
                                <div id="<%= strIfFrame %>">
                                    <iframe src="<%= strSource %>"
                                            marginheight="0" marginwidth="0"
                                            name="ifrmBody" id="ifrmBody"
                                            height="100%" width="100%"
                                            frameborder="0" scrolling="no">
                                    </iframe>
                                </div>
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
