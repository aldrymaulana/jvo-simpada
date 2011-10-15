<%-- 
    Document   : menu1
    Created on : Dec 5, 2009, 12:55:23 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jvo.simpada.common.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
	Hashtable<String, String[]> htHakAkses = new Hashtable<String, String[]>();
	String clsMenuAnggaran = "style=\"display: none\"";
	String clsWajibPajak = "style=\"display: none\"";
	String clsPenetapan = "style=\"display: none\"";
	String clsPenyetoran = "style=\"display: none\"";
	String clsPelaporan = "style=\"display: none\"";
	String clsPengaturan = "style=\"display: none\"";
	int intHakAkses = 0;
	if (request.getSession().getAttribute("htHakAkses") != null) {
		htHakAkses = (Hashtable) request.getSession().getAttribute("htHakAkses");
		intHakAkses = htHakAkses.size();
		String[] strMenu = new String[2];
		for (int a=1; a<=intHakAkses; a++) {
			strMenu = (String[]) htHakAkses.get(String.valueOf(a));
			if (strMenu[0].equalsIgnoreCase("10000")) {
				clsMenuAnggaran = "";
			} else if (strMenu[0].equalsIgnoreCase("20000")) {
				clsWajibPajak = "";
			} else if (strMenu[0].equalsIgnoreCase("30000")) {
				clsPenetapan = "";
			} else if (strMenu[0].equalsIgnoreCase("40000")) {
				clsPenyetoran = "";
			} else if (strMenu[0].equalsIgnoreCase("50000")) {
				clsPelaporan = "";
			} else if (strMenu[0].equalsIgnoreCase("60000")) {
				clsPengaturan = "";
			}
		}
	}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
<style type="text/css" media="screen">
body{ behavior:url("csshover2.htc"); }
.pd_menu_01 {float:left; padding:0; margin:0; color: #000000; background: #b8bff8; width:100%; border:solid 0px #b8bff8; clear:both;} /*Color navigation bar normal mode*/
.pd_menu_01  a, .pd_menu_01 a:visited {
font-family:Courier New, Courier, monospace;
font-style:normal;
font-weight:normal;
font-size:12px;
color: #000000;
background-color: #b8bff8;
text-decoration: none;
}
.pd_menu_01 ul {list-style-type:none;padding:0; margin:0;}
.pd_menu_01 ul li {float:left; position:relative; z-index:auto !important ; z-index:1000 ; border-right:solid 1px #b8bff8; border-left:solid 1px #b8bff8;}
.pd_menu_01 ul li a {color: #000000;background: #b8bff8;float:none !important ; float:left ; display:block; height:30px; line-height:30px; padding:0 10px 0 10px; text-decoration:none; }
.pd_menu_01 ul li ul {display:none; border:none;color: #000000;background: #b8bff8; width:1px}
.pd_menu_01 ul li:hover a {background-color:#CA0000; text-decoration:none; color:#FFFF00;} /*Color main cells hovering mode*/
.pd_menu_01 ul li:hover ul {display:block;  position:absolute; z-index:999; top:29px; margin-top:1px; left:0;}
.pd_menu_01 ul li:hover ul li a {display:block; width:9em; height:auto; line-height:1.3em; margin-left:-1px; padding:5px 10px 5px 10px; border-left:solid 1px #b8bff8; border-bottom: solid 1px #b8bff8; background-color:#b8bff8;  color:#000000;} /*Color subcells normal mode*/
.pd_menu_01 ul li:hover ul li a:hover {background-color:#CA0000; text-decoration:none;color:#FFFF00;} /*Color subcells hovering mode*/
.pd_menu_01 ul li a:hover {background-color:#CA0000; text-decoration:none;color:#FFFF00;} /*Color main cells hovering mode*/
.pd_menu_01 ul li a:hover ul {display:block; width:9em; position:absolute; z-index:999; top:29px; left:0; }
.pd_menu_01 ul li ul li a:visited { background-color:#b8bff8;  color:#000000;} /*Color subcells normal mode*/
.pd_menu_01 ul li a:hover ul li a {display:block; width:9em; height:1px; line-height:1.3em; padding:4px 16px 4px 16px; border-left:solid 1px #b8bff8; border-bottom: solid 1px #b8bff8; background-color:#b8bff8;  color:#000000;}
.pd_menu_01 ul li a:hover ul li a:hover {background-color:#CA0000; text-decoration:none;color:#FFFF00;} /*Color subcells hovering mode*/
</style>

    </head>
    <body>
        <div class="pd_menu_01 "> 
        
            <ul <%= clsMenuAnggaran %>>
                <li><a href="#" onclick="javascript=fnAnggaran();">Anggaran</a></li>
            </ul>

            <ul <%= clsWajibPajak  %>>
                <li><a href="#" onclick="return false">Data Wajib Pajak</a>
                    <ul>
                    <li><a href="#" onclick="javascript=fnPendaftaran();">Daftar</a></li>
                    <li><a href="#" onclick="javascript=fnUbahData();">Ubah</a></li>
                    </ul>
                </li>
            </ul>

            <ul <%= clsPenetapan %>>
                <li><a href="#" onclick="javascript=fnPenetapan();">Penetapan</a></li>
            </ul>
            
            <ul <%= clsPenyetoran %>>
                <li><a href="#" onclick="javascript=fnSetoran();">Penyetoran</a></li>
            </ul>

            <ul <%= clsPelaporan %>>
                <li><a href="#" onclick="javascript=fnLaporan();">Pelaporan</a></li>
            </ul>

            <ul <%= clsPengaturan %>>
                <li><a href="#" onclick="javascript=fnPengaturan();">Pengaturan</a></li>
            </ul>

            <ul>
                <li><a href="#" onclick="javascript=fnKeluar();">Keluar</a></li>
            </ul>

        </div>

    </body>
</html>
