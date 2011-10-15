<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, jvo.simpada.common.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String strWidth = request.getSession().getAttribute("strWidth").toString();
	String strHeight = request.getSession().getAttribute("strHeight").toString();
	//String strLastElement = request.getSession().getAttribute("strLastElement").toString();

	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	java.util.Date todayDate = new java.util.Date();
	String strToday = sdf.format(todayDate);
	
	jvCommon jvc = new jvCommon();

	Hashtable htDataPemda = new Hashtable();
    String strNamaPemda = "";
    String strNamaBidang = "";
    String strAlamatPemda = "";
    String strNoPemda = "";
    String strRTPemda = "";
    String strRWPemda = "";
    String strRKPemda = "";
    String strKelurahanPemda = "";
    String strKecamatanPemda = "";
    String strKotamadyaPemda = "";
    String strKodePosPemda = "";
    String strTeleponPemda = "";
    String strFacsimilePemda = "";
    String strUkeyPemda = "";
    if (request.getSession().getAttribute("htDataPemda") != null) {
        htDataPemda = (Hashtable) request.getSession().getAttribute("htDataPemda");
        int intDataPemda = htDataPemda.size();
        for (int a=1; a<=intDataPemda; a++) {
            //String sqlQuery = "SELECT 0 mPemerintah.Daerah, 1 mPemerintah.Bidang, 2 mPemerintah.Alamat, " +
            //              "3 mPemerintah.[No], 4 mPemerintah.RT, 5 mPemerintah.RW, 6 mPemerintah.RK, 7 mPemerintah.Kelurahan, " +
            //              "8 mPemerintah.Kecamatan, 9 mPemerintah.Kabupaten, 10 mPemerintah.KodePos, " +
            //              "11 mPemerintah.Telepon, 12 mPemerintah.Facs, 13 mPemerintah.Ukey FROM mPemerintah";
            String[] strArray = (String[]) htDataPemda.get(String.valueOf(a));
            strNamaPemda = strArray[0].toUpperCase();
            strNamaBidang = strArray[1].toUpperCase();
            strAlamatPemda = strArray[2].toUpperCase();
            strNoPemda = strArray[3].toUpperCase();
            strRTPemda = strArray[4].toUpperCase();
            strRWPemda = strArray[5].toUpperCase();
            strRKPemda = strArray[6].toUpperCase();
            strKelurahanPemda = strArray[7].toUpperCase();
            strKecamatanPemda = strArray[8].toUpperCase();
            strKotamadyaPemda = strArray[9].toUpperCase();
            strKodePosPemda = strArray[10].toUpperCase();
            strTeleponPemda = strArray[11].toUpperCase();
            strFacsimilePemda = strArray[12].toUpperCase();
            strUkeyPemda = strArray[13].toUpperCase();
        }
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Setting Applikasi</title>
<link href="css/simpada.css" rel="stylesheet">
<script type="text/javascript" src="js/jsUtility.js"></script> 
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-en.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript">
	function fnPemdaSimpan(uKeyPemda) {
		document.main_form.uKeyPemda.value = uKeyPemda;
		document.main_form.mode.value = "1";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnSimpanKary(idxKary) {
		var vIdxJabatan = document.getElementById("slctJabatan_"+idxKary).selectedIndex;
		var vValue = document.getElementById("slctJabatan_"+idxKary)[vIdxJabatan].value;
		document.main_form.txtJabatanKary.value = vValue;
		document.main_form.idxKary.value = idxKary;
		document.main_form.mode.value = "2";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnHapusKary(idxKary) {
		document.main_form.idxKary.value = idxKary;
		document.main_form.mode.value = "3";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnTambahKary() {
		var vIdxJabatan = document.getElementById("slctJabatan0").selectedIndex;
		var vValue = document.getElementById("slctJabatan0")[vIdxJabatan].value;
		document.main_form.txtJabatanKary.value = vValue;
		document.main_form.mode.value = "4";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnSimpanLogin(idxLogin) {
		document.main_form.idxLogin.value = idxLogin;
		document.main_form.mode.value = "5";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnSimpanBank(idxBank) {
		document.main_form.idxLogin.value = idxBank;
		document.main_form.mode.value = "7";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnHapusLogin(idxLogin) {
		document.main_form.idxLogin.value = idxLogin;
		document.main_form.mode.value = "6";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnSimpanAkses(idxAkses) {
		document.main_form.idxAkses.value = idxAkses;
		document.main_form.mode.value = "8";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}

	function fnBatal() {
		document.main_form.mode.value = "0";
		document.main_form.target = "_parent";
		document.main_form.method = "POST";
		document.main_form.action = "srvPengaturan";
		document.main_form.submit();
	}
</script>
</head>
<body onload="fnGetAllElement()" style="font-size: 12px">
<form name="main_form">
	<input type="hidden" name="mode" id="mode"></input>
	<input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth %>">
    <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight %>">
    <input type="hidden" name="uKeyPemda" id="uKeyPemda"></input>
	<input type="hidden" name="idxKary" id="idxKary"></input>
	<input type="hidden" name="txtJabatanKary" id="txtJabatanKary"></input>
	<input type="hidden" name="idxLogin" id="idxLogin"></input>
	<input type="hidden" name="idxAkses" id="idxAkses"></input>
<a name="top"></a>	
<fieldset>
	<legend>
		<font style="font-weight: bold; font-size: 16px"></font>
	</legend>
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
		<tr valign="middle">
			<td align="center">
				<table border="0" cellpadding="2" cellspacing="2">
					<tr align="center">
						<td>
							P E R H A T I A N
						</td>
					</tr>
					<tr align="left">
						<td>
							Halaman ini dapat digunakan untuk melakukan pengaturan/perubahan pada beberapa informasi<br>
							umum, seperti tertulis berikut ini. Klik pada list untuk membuka form perubahan.
							<li><a href="#pemda">Informasi Pemerintah Daerah</a></li>
							<li><a href="#karyawan">Informasi Karyawan</a></li>
							<li><a href="#login">Informasi Login</a></li>
							<li><a href="#akses">Informasi Hak Akses</a></li>
							<li><a href="#bank">Informasi Bank</a></li>
							<br>
							Segala perubahan yang dilakukan akan berpengaruh setelah dilakukan restart aplikasi.
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</fieldset>	
<br>	
<a name="pemda"></a>
<fieldset>
	<legend>
		<font style="font-weight: bold; font-size: 16px">INFORMASI PEMERINTAH DAERAH</font>
		&nbsp;[ <a href="#top">kembali ke atas</a> ]
	</legend>
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
		<tr valign="middle">
			<td align="center">
			
	<table border="0" cellpadding="2" cellspacing="2">
		<tr align="left">
			<td>Nama Pemda</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtNamaPemda" id="txtNamaPemda" value="<%= strNamaPemda %>"></input></td>
		</tr>
		<tr align="left">
			<td>Nama Bidang</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtNamaBidang" id="txtNamaBidang" value="<%= strNamaBidang %>"></td>
		</tr>
		<tr align="left">
			<td>Alamat</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtAlamatPemda" id="txtAlamatPemda" value="<%= strAlamatPemda %>"></td>
		</tr>
		<tr align="left">
			<td>No</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtNoPemda" id="txtNoPemda" value="<%= strNoPemda %>"></td>
		</tr>
		<tr align="left">
			<td>RT</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtRTPemda" id="txtRTPemda" value="<%= strRTPemda %>"></td>
		</tr>
		<tr align="left">
			<td>RW</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtRWPemda" id="txtRWPemda" value="<%= strRWPemda %>"></td>
		</tr>
		<tr align="left">
			<td>RK</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtRKPemda" id="txtRKPemda" value="<%= strRKPemda %>"></td>
		</tr>
		<tr align="left">
			<td>Kelurahan</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtKelurahanPemda" id="txtKelurahanPemda" value="<%= strKelurahanPemda %>"></td>
		</tr>
		<tr align="left">
			<td>Kecamatan</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtKecamatanPemda" id="txtKecamatanPemda" value="<%= strKecamatanPemda %>"></td>
		</tr>
		<tr align="left">
			<td>Kabupaten / Kotamadya</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtKotamadyaPemda" id="txtKotamadyaPemda" value="<%= strKotamadyaPemda %>"></td>
		</tr>
		<tr align="left">
			<td>Kode Pos</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtKodePosPemda" id="txtKodePosPemda" value="<%= strKodePosPemda %>"></td>
		</tr>
		<tr align="left">
			<td>Telepon</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtTeleponPemda" id="txtTeleponPemda" value="<%= strTeleponPemda %>"></td>
		</tr>
		<tr align="left">
			<td>Facsimie</td><td>&nbsp;:&nbsp;</td>
			<td><input style="width: 80%" type="text" name="txtFacsimilePemda" id="txtFacsimilePemda" value="<%= strFacsimilePemda %>"></td>
		</tr>
		<tr align="left">
			<td colspan="3">
				<button type="button" onclick="fnPemdaSimpan(<%= strUkeyPemda %>)" name="btPemdaSimpan" id="btPemdaSimpan">Simpan</button>
				<button type="button" onclick="fnBatal()" name="btPemdaBatal" id="btPemdaBatal">Batal</button>
			</td>
		</tr>
	</table>
	
			</td>
		</tr>
	</table>
</fieldset>
<br></br>
<a name="karyawan"></a>
<fieldset>
	<legend>
		<font style="font-weight: bold; font-size: 16px">INFORMASI KARYAWAN</font>
		&nbsp;[ <a href="#top">kembali ke atas</a> ]
	</legend>
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
		<tr valign="middle">
			<td align="center">
			
	<table border="0" cellpadding="2" cellspacing="2">
		<tr class="JUDUL2" align="center">
			<td>&nbsp;</td>
			<td>No</td>
			<td>Nama</td>
			<td>Jabatan</td>
			<td>NIP</td>
			<td>Alamat</td>
		</tr>
		
		<tr class="TANYA">
			<td align="right" rowspan="7">
				<button type="button" onclick="fnTambahKary()" name="btSimpan" id="btSimpan">Simpan</button>
				<button type="button" onclick="fnBatal()" name="btBatal" id="btBatal">Batal</button>
			</td>			
			<td width="30" align="center" rowspan="7">BARU</td>
			<td><input type="text" name="txtNamaKary" id="txtNamaKary"></input></td>
			<td>
				<select name="slctJabatan0" id="slctJabatan0">
			<%
			Hashtable htListJabatan0 = new Hashtable();
			if (request.getSession().getAttribute("htListJabatan") != null) {
				htListJabatan0 = (Hashtable) request.getSession().getAttribute("htListJabatan");
				int intHtListJabatan = htListJabatan0.size();
				for (int c=1; c<=intHtListJabatan; c++) {
					String[] strListJabatan = (String[]) htListJabatan0.get(String.valueOf(c));
					%>
					<option value="<%= strListJabatan[1] %>"><%= strListJabatan[2] %>
					<%
				}
			}
			%>
				</select>
			</td>
			<td><input type="text" name="txtNipKary" id="txtNipKary"></input></td>
			<td><input type="text" name="txtAlamatKary" id="txtAlamatKary"></input></td>
		</tr>
		<tr class="JUDUL2" align="center">
			<td>No</td>
			<td>RT</td>
			<td>RW</td>
			<td>RK</td>
		</tr>
		<tr class="TANYA">
			<td><input type="text" name="txtNoKary" id="txtNoKary"></input></td>
			<td><input type="text" name="txtRTKary" id="txtRTKary"></input></td>
			<td><input type="text" name="txtRWKary" id="txtRWKary"></input></td>
			<td><input type="text" name="txtRKKary" id="txtRKKary"></input></td>
		</tr>
		<tr class="JUDUL2" align="center">
			<td>Kelurahan</td>
			<td>Kecamatan</td>
			<td>Kabupaten / Kotamadya</td>
			<td>Kode Pos</td>
		</tr>
		<tr class="TANYA">
			<td><input type="text" name="txtKelurahanKary" id="txtKelurahanKary"></input></td>
			<td><input type="text" name="txtKecamatanKary" id="txtKecamatanKary"></input></td>
			<td><input type="text" name="txtKotamadyaKary" id="txtKotamadyaKary"></input></td>
			<td><input type="text" name="txtKodePosKary" id="txtKodePosKary"></input></td>
		</tr>
		<tr class="JUDUL2" align="center">
			<td>Telepon</td>
			<td>Facsimie</td>
			<td></td>
			<td></td>		
		</tr>
		<tr class="TANYA">
			<td><input type="text" name="txtTeleponKary" id="txtTeleponKary"></input></td>
			<td><input type="text" name="txtFacsimileKary" id="txtFacsimileKary"></input></td>
			<td></td>
			<td></td>
		</tr>
		
		<%
		Hashtable htDaftarPegawai = new Hashtable();
		if (request.getSession().getAttribute("htDaftarPegawai") != null) {
			htDaftarPegawai = (Hashtable) request.getSession().getAttribute("htDaftarPegawai");
			int intDaftarPegawai = htDaftarPegawai.size();
			int rowNum = 1;
			for (int b=1; b<=intDaftarPegawai; b++) {
				//String sqlQuery = "SELECT mPegawai.Nama, mPegawai.Jabatan, mPegawai.NIP, " +
				//  "mPegawai.Alamat, mPegawai.[No], mPegawai.RT, mPegawai.RW, " +
				//  "mPegawai.RK, mPegawai.Kelurahan, mPegawai.Kecamatan, " + 
				//  "mPegawai.Kabupaten, mPegawai.KodePos, mPegawai.Telepon, " + 
				//  "mPegawai.Facs, mPegawai.Email, mPegawai.Agama, mPegawai.GolDarah, " + 
				//  "mPegawai.Ukey FROM mPegawai";
				String[] strArray = (String[]) htDaftarPegawai.get(String.valueOf(b));
				String strNama = jvc.fnGetValue(strArray[0]).toUpperCase();
				String strJabatan = jvc.fnGetValue(strArray[1]).toUpperCase();
				String strNip = jvc.fnGetValue(strArray[2]).toUpperCase();
				String strAlamat = jvc.fnGetValue(strArray[3]).toUpperCase();
				String strNo = jvc.fnGetValue(strArray[4]).toUpperCase();
				String strRT = jvc.fnGetValue(strArray[5]).toUpperCase();
				String strRW = jvc.fnGetValue(strArray[6]).toUpperCase();
				String strRK = jvc.fnGetValue(strArray[7]).toUpperCase();
				String strKelurahan = jvc.fnGetValue(strArray[8]).toUpperCase();
				String strKecamatan = jvc.fnGetValue(strArray[9]).toUpperCase();
				String strKotamadya = jvc.fnGetValue(strArray[10]).toUpperCase();
				String strKodePos = jvc.fnGetValue(strArray[11]).toUpperCase();
				String strTelepon = jvc.fnGetValue(strArray[12]).toUpperCase();
				String strFacsimile = jvc.fnGetValue(strArray[13]).toUpperCase();
				String strEmail = jvc.fnGetValue(strArray[14]).toUpperCase();
				String strAgama = jvc.fnGetValue(strArray[15]).toUpperCase();
				String strGolDarah = jvc.fnGetValue(strArray[16]).toUpperCase();
				String strUkey = jvc.fnGetValue(strArray[17]).toUpperCase();
				
				int cd = rowNum % 2;
				String strNmClass;
				if (cd == 0) {
					strNmClass = "TANYA";
				} else {
					strNmClass = "JAWAB";
				}
		%>
		<tr class="JUDUL2" align="center">
			<td>&nbsp;</td>
			<td>No</td>
			<td>Nama</td>
			<td>Jabatan</td>
			<td>NIP</td>
			<td>Alamat</td>
		</tr>
		<tr class="<%= strNmClass %>">
			<td align="right" rowspan="7">
				<input type="hidden" name="txtUkeyKary_<%= b %>" id="txtUkeyKary_<%= b %>" value="<%= strUkey %>"></input>
				<button type="button" onclick="fnSimpanKary(<%= b %>)" name="btSimpan" id="btSimpan">Simpan</button>
				<button type="button" onclick="fnHapusKary(<%= b %>)" name="btHapus" id="btHapus">Hapus</button>
				<button type="button" onclick="fnBatal()" name="btBatal" id="btBatal">Batal</button>
			</td>
			<td width="30" align="center" rowspan="7"><%= b %></td>
			<td><input type="text" name="txtNamaKary_<%= b %>" id="txtNamaKary_<%= b %>" value="<%= strNama %>"></input></td>
			<td>
				<select name="slctJabatan_<%= b %>" id="slctJabatan_<%= b %>">
			<%
			Hashtable htListJabatan = new Hashtable();
			if (request.getSession().getAttribute("htListJabatan") != null) {
				htListJabatan = (Hashtable) request.getSession().getAttribute("htListJabatan");
				int intHtListJabatan = htListJabatan.size();
				for (int c=1; c<=intHtListJabatan; c++) {
					String[] strListJabatan = (String[]) htListJabatan.get(String.valueOf(c));
					%>
					<option value="<%= strListJabatan[1] %>" <%= (strListJabatan[1].equalsIgnoreCase(strJabatan.trim()))?"SELECTED":"" %>><%= strListJabatan[2] %>
					<%
				}
			}
			%>
				</select>
			</td>
			<td><input type="text" name="txtNipKary_<%= b %>" id="txtNipKary_<%= b %>" value="<%= strNip %>"></input></td>
			<td><input type="text" name="txtAlamatKary_<%= b %>" id="txtAlamatKary_<%= b %>" value="<%= strAlamat %>"></input></td>
		</tr>
		<tr class="JUDUL2" align="center">
			<td>No</td>
			<td>RT</td>
			<td>RW</td>
			<td>RK</td>
		</tr>
		<tr class="<%= strNmClass %>">
			<td><input type="text" name="txtNoKary_<%= b %>" id="txtNoKary_<%= b %>" value="<%= strNo %>"></input></td>
			<td><input type="text" name="txtRTKary_<%= b %>" id="txtRTKary_<%= b %>" value="<%= strRT %>"></input></td>
			<td><input type="text" name="txtRWKary_<%= b %>" id="txtRWKary_<%= b %>" value="<%= strRW %>"></input></td>
			<td><input type="text" name="txtRKKary_<%= b %>" id="txtRKKary_<%= b %>" value="<%= strRK %>"></input></td>
		</tr>
		<tr class="JUDUL2" align="center">
			<td>Kelurahan</td>
			<td>Kecamatan</td>
			<td>Kabupaten / Kotamadya</td>
			<td>Kode Pos</td>
		</tr>
		<tr class="<%= strNmClass %>">
			<td><input type="text" name="txtKelurahanKary_<%= b %>" id="txtKelurahanKary_<%= b %>" value="<%= strKelurahan %>"></input></td>
			<td><input type="text" name="txtKecamatanKary_<%= b %>" id="txtKecamatanKary_<%= b %>" value="<%= strKecamatan %>"></input></td>
			<td><input type="text" name="txtKotamadyaKary_<%= b %>" id="txtKotamadyaKary_<%= b %>" value="<%= strKotamadya %>"></input></td>
			<td><input type="text" name="txtKodePosKary_<%= b %>" id="txtKodePosKary_<%= b %>" value="<%= strKodePos %>"></input></td>
		</tr>
		<tr class="JUDUL2" align="center">
			<td>Telepon</td>
			<td>Facsimie</td>
			<td></td>
			<td></td>		
		</tr>
		<tr class="<%= strNmClass %>">
			<td><input type="text" name="txtTeleponKary_<%= b %>" id="txtTeleponKary_<%= b %>" value="<%= strTelepon %>"></input></td>
			<td><input type="text" name="txtFacsimileKary_<%= b %>" id="txtFacsimileKary_<%= b %>" value="<%= strFacsimile %>"></input></td>
			<td></td>
			<td></td>
		</tr>
		<%
		rowNum = rowNum + 1;
			}
		}
		%>
	</table>
	
			</td>
		</tr>
	</table>
</fieldset>
<br></br>
<a name="login"></a>
<fieldset>
	<legend>
		<font style="font-weight: bold; font-size: 16px">INFORMASI LOGIN</font>
		&nbsp;[ <a href="#top">kembali ke atas</a> ]
	</legend>
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
		<tr valign="middle">
			<td align="center">
			
	<table border="0" cellpadding="2" cellspacing="2">
		<tr class="JUDUL2" align="center">
			<td>&nbsp;</td>
			<td width="30" align="center">No</td>
			<td>Nama</td>
			<td>Login</td>
			<td>Password</td>
		</tr>
		<%
		Hashtable htInfoLogin = new Hashtable();
		if (request.getSession().getAttribute("htInfoLogin") != null) {
			htInfoLogin = (Hashtable) request.getSession().getAttribute("htInfoLogin");
			int intInfoLogin = htInfoLogin.size();
			int rowNum = 1;
			for (int c=1; c<=intInfoLogin; c++) {
				//String sqlQuery = "SELECT mPegawai.Ukey, mPegawai.Nama, LoginId, Password " + 
				//  "FROM mPegawai INNER JOIN dataLogin " + 
				//  "ON mPegawai.Ukey = dataLogin.FUkey";
				String[] strArray = (String[]) htInfoLogin.get(String.valueOf(c));
				String strUkeyL = jvc.fnGetValue(strArray[0]);
				String strNamaL = jvc.fnGetValue(strArray[1]).toUpperCase();
				String strLoginL = jvc.fnGetValue(strArray[2]);
				String strPasswordL = jvc.fnGetValue(strArray[3]);
				
				int cc = rowNum % 2;
				String strNmClass;
				if (cc == 0) {
					strNmClass = "TANYA";
				} else {
					strNmClass = "JAWAB";
				}
		%>
				<tr class="<%= strNmClass %>">
					<td align="center">
						<input type="hidden" name="txtUkey_<%= c %>" id="txtUkey_<%= c %>" value="<%= strUkeyL %>"></input>
						<button type="button" onclick="fnSimpanLogin(<%= c %>)" name="btSimpan" id="btSimpan">Simpan</button>
						<button type="button" onclick="fnHapusLogin(<%= c %>)" name="btHapus" id="btHapus">Hapus</button>
						<button type="button" onclick="fnBatal()" name="btBatal" id="btBatal">Batal</button>
					</td>
					<td width="30" align="center"><%= c %></td>
					<td><%= strNamaL %></td>
					<td><input type="text" name="txtLogin_<%= c %>" id="txtLogin_<%= c %>" value="<%= strLoginL %>"></input></td>
					<td><input type="text" name="txtPassword_<%= c %>" id="txtPassword_<%= c %>" value="<%= strPasswordL %>"></input></td>
				</tr>
		<%
		rowNum = rowNum + 1;
			}
		}
		%>
	</table>
			</td>
		</tr>
	</table>
</fieldset>
<br>
<a name="akses"></a>
<fieldset>
	<legend>	
		<font style="font-weight: bold; font-size: 16px">INFORMASI HAK AKSES</font>
		&nbsp;[ <a href="#top">kembali ke atas</a> ]
	</legend>
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
		<tr valign="middle">
			<td align="center">
			
	<table border="0" cellpadding="2" cellspacing="2">
		<tr class="JUDUL2" align="center">
			<td>&nbsp;</td>
			<td width="30" align="center">No</td>
			<td>Kode Jabatan</td>
			<td>Jabatan</td>
			<%
			Hashtable htMenu = new Hashtable();
			if (request.getSession().getAttribute("htMenu") != null) {
				htMenu = (Hashtable) request.getSession().getAttribute("htMenu");
				int intMenu = htMenu.size();
				for (int c=1; c<=intMenu; c++) {
					String[] strArray = (String[]) htMenu.get(String.valueOf(c));
					String strMenu = jvc.fnGetValue(strArray[0]);
					%>
						<td><%= strMenu %></td>
					<%
				}
			}
			%>
		</tr>
		<%
		Hashtable htListAkses = new Hashtable();
		if (request.getSession().getAttribute("htListAkses") != null) {
			htListAkses = (Hashtable) request.getSession().getAttribute("htListAkses");
			int intListAkses = htListAkses.size();
			int rowNum = 1;
			for (int c=1; c<=intListAkses; c++) {
				// sqlQuery.append("SELECT Kode, Keterangan, m10000, m20000, m30000, m40000, m50000, m60000 ");
				String[] strArray = (String[]) htListAkses.get(String.valueOf(c));
				String strKodeJabatan = jvc.fnGetValue(strArray[0]);
				String strJabatan = jvc.fnGetValue(strArray[1]);
				String str10000 = jvc.fnGetValue(strArray[2]);
				String str20000 = jvc.fnGetValue(strArray[3]);
				String str30000 = jvc.fnGetValue(strArray[4]);
				String str40000 = jvc.fnGetValue(strArray[5]);
				String str50000 = jvc.fnGetValue(strArray[6]);
				String str60000 = jvc.fnGetValue(strArray[7]);
				
				int cc = rowNum % 2;
				String strNmClass;
				if (cc == 0) {
					strNmClass = "TANYA";
				} else {
					strNmClass = "JAWAB";
				}
		%>
				<tr class="<%= strNmClass %>">
					<td align="center">
						<input type="hidden" name="txtMenuKey_<%= c %>" id="txtMenuKey_<%= c %>" value="<%= c %>"></input>
						<input type="hidden" name="txtKdJabatan_<%= c %>" id="txtKdJabatan_<%= c %>" value="<%= strKodeJabatan %>"></input>
						<button type="button" onclick="fnSimpanAkses(<%= c %>)" name="btSimpan" id="btSimpan">Simpan</button>
						<button type="button" onclick="fnBatal()" name="btBatal" id="btBatal">Batal</button>
					</td>
					<td width="30" align="center"><%= c %></td>
					<td align="center"><%= strKodeJabatan %></td>
					<td><%= strJabatan %></td>
					<td align="center"><input type="checkbox" <%= (str10000.trim().equals("1"))?"CHECKED":"" %> name="chk10000_<%= c %>" id="chk10000_<%= c %>"></input></td>
					<td align="center"><input type="checkbox" <%= (str20000.trim().equals("1"))?"CHECKED":"" %> name="chk20000_<%= c %>" id="chk20000_<%= c %>"></input></td>
					<td align="center"><input type="checkbox" <%= (str30000.trim().equals("1"))?"CHECKED":"" %> name="chk30000_<%= c %>" id="chk30000_<%= c %>"></input></td>
					<td align="center"><input type="checkbox" <%= (str40000.trim().equals("1"))?"CHECKED":"" %> name="chk40000_<%= c %>" id="chk40000_<%= c %>"></input></td>
					<td align="center"><input type="checkbox" <%= (str50000.trim().equals("1"))?"CHECKED":"" %> name="chk50000_<%= c %>" id="chk50000_<%= c %>"></input></td>
					<td align="center"><input type="checkbox" <%= (str60000.trim().equals("1"))?"CHECKED":"" %> name="chk60000_<%= c %>" id="chk60000_<%= c %>"></input></td>
				</tr>
		<%
		rowNum = rowNum + 1;
			}
		}
		%>
	</table>
			</td>
		</tr>
	</table>
</fieldset>		
<br>
<a name="bank"></a>
<fieldset>
	<legend>
		<font style="font-weight: bold; font-size: 16px">INFORMASI BANK</font>
		&nbsp;[ <a href="#top">kembali ke atas</a> ]
	</legend>
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
		<tr valign="middle">
			<td align="center">
			
	<table border="0" cellpadding="2" cellspacing="2">
		<tr class="JUDUL2" align="center">
			<td>&nbsp;</td>
			<td width="30" align="center">No</td>
			<td>Nama</td>
			<td>No Rekening</td>
			<td>Kode</td>
		</tr>
		<%
		Hashtable htInfoBank = new Hashtable();
		if (request.getSession().getAttribute("htInfoBank") != null) {
			htInfoBank = (Hashtable) request.getSession().getAttribute("htInfoBank");
			int intInfoBank = htInfoBank.size();
			int rowNum = 1;
			for (int c=1; c<=intInfoBank; c++) {
				String[] strArray = (String[]) htInfoBank.get(String.valueOf(c));
				String strNamaB = jvc.fnGetValue(strArray[0]).toUpperCase();
				String strNoRekB = jvc.fnGetValue(strArray[1]);
				String strKodeB = jvc.fnGetValue(strArray[2]);
				
				int cc = rowNum % 2;
				String strNmClass;
				if (cc == 0) {
					strNmClass = "TANYA";
				} else {
					strNmClass = "JAWAB";
				}
		%>
				<tr class="<%= strNmClass %>">
					<td align="center">
						<input type="hidden" name="txtKodeB_<%= c %>" id="txtKodeB_<%= c %>" value="<%= strKodeB %>"></input>
						<button type="button" onclick="fnSimpanBank(<%= c %>)" name="btSimpan" id="btSimpan">Simpan</button>
						<button type="button" onclick="fnBatal()" name="btBatal" id="btBatal">Batal</button>
					</td>
					<td width="30" align="center"><%= c %></td>
					<td><input type="text" name="txtNamaB_<%= c %>" id="txtNamaB_<%= c %>" value="<%= strNamaB %>"></input></td>
					<td><input type="text" name="txtNoRekB_<%= c %>" id="txtNoRekB_<%= c %>" value="<%= strNoRekB %>"></input></td>
					<td><%= strKodeB %></td>
				</tr>
		<%
		rowNum = rowNum + 1;
			}
		}
		%>
	</table>	
			</td>
		</tr>
	</table>
</fieldset>	
</form>
</body>
</html>