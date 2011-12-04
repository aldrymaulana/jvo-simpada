<%--
    Document   : menu2
    Created on : 18 Okt 11, 22:09:41
    Author     : Sharkie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.tech.master.common.*"%>
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
    <script type="text/javascript">
        function fnWP() {
            var vCheck = document.getElementById('hidWP').value;
            if (vCheck == '') {
                document.getElementById('imageWP').src = 'image/closedFolder.gif';
                document.getElementById('hidWP').value = 'none';
                document.getElementById('subMenuWP1').style.display = 'none';
                document.getElementById('subMenuWP2').style.display = 'none';
            } else {
                document.getElementById('imageWP').src = 'image/openFolder.gif';
                document.getElementById('hidWP').value = '';
                document.getElementById('subMenuWP1').style.display = '';
                document.getElementById('subMenuWP2').style.display = '';
            }
        }

        function fnMenuLaporan() {
            var vCheck = document.getElementById('hidLaporan').value;
            if (vCheck == '') {
                document.getElementById('imageLaporan').src = 'image/closedFolder.gif';
                document.getElementById('hidLaporan').value = 'none';
                document.getElementById('subMenuLaporan1').style.display = 'none';
                document.getElementById('subMenuLaporan2').style.display = 'none';
                document.getElementById('subMenuLaporan3').style.display = 'none';
                document.getElementById('subMenuLaporan4').style.display = 'none';
                document.getElementById('subMenuLaporan5').style.display = 'none';
                document.getElementById('subMenuLaporan6').style.display = 'none';
            } else {
                document.getElementById('imageLaporan').src = 'image/openFolder.gif';
                document.getElementById('hidLaporan').value = '';
                document.getElementById('subMenuLaporan1').style.display = '';
                document.getElementById('subMenuLaporan2').style.display = '';
                document.getElementById('subMenuLaporan3').style.display = '';
                document.getElementById('subMenuLaporan4').style.display = '';
                document.getElementById('subMenuLaporan5').style.display = '';
                document.getElementById('subMenuLaporan6').style.display = '';
            }
        }
    </script>
    <body>
<!--
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
-->
<table border="0">
    <tr valign="top" align="left">
        <td colspan="3"><b>M&nbsp;E&nbsp;N&nbsp;U</b></td>
    </tr>
    <tr align="left">
        <td><image src="image/closedFolder.gif" width="20" height="20" /></td>
        <td colspan="2"><a href="#" onclick="fnAnggaran()">Anggaran</a></td>
    </tr>
    <tr valign="top" align="left">
        <td><image id="imageWP" name="imageWP" src="image/openFolder.gif" width="20" height="20" /></td>
        <td colspan="2">
            <a href="#" onclick="fnWP()">Wajib Pajak</a>
            <input type="text" id="hidWP" name="hidWP" style="display: none">
        </td>
    </tr>
    <tr valign="top" align="left" id="subMenuWP1">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnPendaftaran()">Pendaftaran</a></td>
    </tr>
    <tr valign="top" align="left" id="subMenuWP2">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnUbahData()">Perubahan</a></td>
    </tr>
    <tr align="left">
        <td><image src="image/closedFolder.gif" width="20" height="20" /></td>
        <td colspan="2"><a href="#" onclick="fnPenetapan()">Penetapan</a></td>
    </tr>
    <tr align="left">
        <td><image src="image/closedFolder.gif" width="20" height="20" /></td>
        <td colspan="2"><a href="#" onclick="fnSetoran()">Penyetoran</a></td>
    </tr>
    <tr valign="top" align="left">
        <td><image id="imageLaporan" name="imageLaporan" src="image/openFolder.gif" width="20" height="20" /></td>
        <td colspan="2">
            <a href="#" onclick="fnMenuLaporan()">Pelaporan</a>
            <input type="text" id="hidLaporan" name="hidLaporan" style="display: none">
        </td>
    </tr>
    <tr valign="top" align="left" id="subMenuLaporan1">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnLaporan()">Rekapitulasi Penerimaan Harian</a></td>
    </tr>
    <tr valign="top" align="left" id="subMenuLaporan2">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnLaporan()">Buku Kas Pembantu</a></td>
    </tr>
    <tr valign="top" align="left" id="subMenuLaporan3">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnLaporan()">Buku Kas Umum</a></td>
    </tr>
    <tr valign="top" align="left" id="subMenuLaporan4">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnLaporan()">SPJ Pendapatan - Fungsional</a></td>
    </tr>
    <tr valign="top" align="left" id="subMenuLaporan5">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnLaporan()">Rekapitulasi Penetapan Pajak</a></td>
    </tr>
    <tr valign="top" align="left" id="subMenuLaporan6">
        <td>&nbsp;</td>
        <td><image src="image/file.gif" width="15" height="15" /></td>
        <td><a href="#" onclick="fnLaporan()">Daftar Perusahaan</a></td>
    </tr>
    <tr align="left">
        <td><image src="image/closedFolder.gif" width="20" height="20" /></td>
        <td colspan="2"><a href="#" onclick="fnPengaturan()">Pengaturan</a></td>
    </tr>
    <tr align="left">
        <td><image src="image/closedFolder.gif" width="20" height="20" /></td>
        <td colspan="2"><a href="#" onclick="fnKeluar()">Keluar</a></td>
    </tr>
</table>

    </body>
</html>
