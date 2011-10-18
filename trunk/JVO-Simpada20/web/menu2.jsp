<%-- 
    Document   : menu2
    Created on : 18 Okt 11, 22:09:41
    Author     : Sharkie
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
        <link href="menu/menu_style.css" rel="stylesheet" media="all" >
    </head>
    <body>

<div>
<ul class="menu">
<li class="top" <%= clsMenuAnggaran %>><a href="#" onclick="fnAnggaran()" target="_self" class="top_link"><span>Anggaran</span></a>
</li>
<li class="top" <%= clsWajibPajak  %>><a href="" target="_self" class="top_link"><span>Wajib Pajak</span></a>
<ul class="sub" <%= clsWajibPajak  %>>
<li><a href="#" onclick="fnPendaftaran()" target="_self">Daftar</a></li>
<li><a href="#" onclick="fnUbahData()" target="_self">Ubah</a></li>
</ul>
</li>
<li class="top" <%= clsPenetapan %>><a href="#" onclick="fnPenetapan()" target="_self" class="top_link"><span>Penetapan</span></a>
</li>
<li class="top" <%= clsPenyetoran %>><a href="#" onclick="fnSetoran()" target="_self" class="top_link"><span>Penyetoran</span></a>
</li>
<li class="top" <%= clsPelaporan %>><a href="#" onclick="fnLaporan()" target="_self" class="top_link"><span>Pelaporan</span></a>
</li>
<li class="top" <%= clsPengaturan %>><a href="#" onclick="fnPengaturan()" target="_self" class="top_link"><span>Pengaturan</span></a>
</li>
<li class="top"><a href="#" onclick="fnKeluar()" target="_self" class="top_link"><span>Keluar</span></a>
</li>
</ul>
</div>
        
    </body>
</html>
