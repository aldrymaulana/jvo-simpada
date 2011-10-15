<%-- 
    Document   : skpd
    Created on : Dec 1, 2009, 9:46:20 PM
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
    
    //java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    //java.util.Date todayDate = new java.util.Date();
    //String strToday = sdf.format(todayDate);
    
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
    
    String strJnsPekerjaan = "";
    if (request.getSession().getAttribute("strJnsPekerjaan") != null) {
    	strJnsPekerjaan = request.getSession().getAttribute("strJnsPekerjaan").toString();
	}
    
    String strUraianKelurahan = "";
    Hashtable htKelurahan = new Hashtable();
    if (request.getSession().getAttribute("htKelurahan") != null) {
        htKelurahan = (Hashtable) request.getSession().getAttribute("htKelurahan");
        int intHtKelurahan = htKelurahan.size();
        for (int a=1; a<=intHtKelurahan; a++) {
            String[] strKelurahan = (String[]) htKelurahan.get(String.valueOf(a));
            String strKodeKabupaten = strKelurahan[0];
            String strKodeKecamatan = strKelurahan[1];
            String strKodeKelurahan = strKelurahan[2];
            String strNamaKelurahan = strKelurahan[3];
            String strKodeGab = strKodeKabupaten + "," + strKodeKecamatan + "," + strKodeKelurahan;
            if (strKodeGab.equalsIgnoreCase(strKelurahanBU)) {
                strUraianKelurahan = strNamaKelurahan;
                break;
            }
        }
    }
    
    String strUraianKecamatan = "";
    Hashtable htKecamatan = new Hashtable();
    if (request.getSession().getAttribute("htKecamatan") != null) {
        htKecamatan = (Hashtable) request.getSession().getAttribute("htKecamatan");
        int intHtKecamatan = htKecamatan.size();
        for (int a=1; a<=intHtKecamatan; a++) {
            String[] strKecamatan = (String[]) htKecamatan.get(String.valueOf(a));
            String strKodeKabupaten = strKecamatan[0];
            String strKodeKecamatan = strKecamatan[1];
            String strNamaKecamatan = strKecamatan[2];
            String strKodeGab = strKodeKabupaten + "," + strKodeKecamatan;
            if (strKodeGab.equalsIgnoreCase(strKecamatanBU)) {
                strUraianKecamatan = strNamaKecamatan;
                break;
            }
        }
    }
    
    String strUraianKabupaten = "";
    Hashtable htKabupaten = new Hashtable();
    if (request.getSession().getAttribute("htKabupaten") != null) {
        htKabupaten = (Hashtable) request.getSession().getAttribute("htKabupaten");
        int intHtKabupaten = htKabupaten.size();
        for (int a=1; a<=intHtKabupaten; a++) {
            String[] strKabupaten = (String[]) htKabupaten.get(String.valueOf(a));
            String strKodeKabupaten = strKabupaten[0];
            String strNamaKabupaten = strKabupaten[1];
            if (strKodeKabupaten.equalsIgnoreCase(strKabupatenBU)) {
                strUraianKabupaten = strNamaKabupaten;
                break;
            }
        }
    }
    
    String strUraianRet = "";
    String vKodePADLain = request.getParameter("vKode").toString();
    Hashtable htPADLain = new Hashtable();
    if (request.getSession().getAttribute("htPADLain") != null) {
    	htPADLain = (Hashtable) request.getSession().getAttribute("htPADLain");
        int intHtPADLain = htPADLain.size();
        for (int a=1; a<=intHtPADLain; a++) {
            String[] strPADLain = (String[]) htPADLain.get(String.valueOf(a));
            String strKodePADLain = strPADLain[0];
            String strKetPADLain = strPADLain[1];
            String strKodeNPWR = strPADLain[2];
            if (strKodePADLain.equalsIgnoreCase(vKodePADLain)) {
            	strUraianRet = strKetPADLain;
                break;
            }
        }
    }
    
    //String strNoUrut = "";
    //if (request.getSession().getAttribute("strNoUrut") != null) {
    //    strNoUrut = request.getSession().getAttribute("strNoUrut").toString();
    //}
    
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    java.util.Date todayDate = new java.util.Date();
    String strToday = sdf.format(todayDate);
    String strDate = strToday.substring(0,2);
    String strMonth = strToday.substring(3,5);
    String strYear = strToday.substring(6,10);
        
    Calendar cal = new GregorianCalendar();
    int intDayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // jml hari dalam sebulan
    int intDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // jml hari dalam seminggu
    int intMonth = cal.get(Calendar.MONTH);
    int intYear = cal.get(Calendar.YEAR);
    
    Calendar cal2 = new GregorianCalendar(intYear, intMonth, 1);
    int intMaxDay2 = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
    
    String strDate1 = "01/" + jvc.fnLRPad("LPAD",String.valueOf(intMonth+1),"0",2) + "/" + intYear;
    String strDate2 = intMaxDay2 + "/" + jvc.fnLRPad("LPAD",String.valueOf(intMonth+1),"0",2) + "/" + intYear;
    
	Date currDate = cal.getTime();
	cal.setTime(currDate);
	cal.add(Calendar.DAY_OF_YEAR,30);
	Date nextDate = cal.getTime();
	
	//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    //java.util.Date todayDate = new java.util.Date();
    String strNextDate = sdf.format(nextDate);
    
%>

<%
    String strNoRekPADLain = "";
    Hashtable htNoRek = new Hashtable();
    if (request.getSession().getAttribute("htNoRek") != null) {
        htNoRek = (Hashtable) request.getSession().getAttribute("htNoRek");
        int intHtNoRek = htNoRek.size();
        for (int b=1; b<=intHtNoRek; b++) {
            String[] strNoRek = (String[]) htNoRek.get(String.valueOf(b));
            if ((strNoRek[3].equalsIgnoreCase("PADLain")) && (strNoRek[1].equalsIgnoreCase(vKodePADLain))) {
                strNoRekPADLain = strNoRek[0];
            }
        }
    }
    
    jvGeneral jvg = new jvGeneral();
    String strCountSKPD = jvg.fnGetCounter("PAD", strNoRekPADLain, strMonth, strYear, "0");
    //String strNoUrut = "2R." + strDate + "." + strMonth + "." + strYear + "." + jvc.fnLRPad("LPAD", strCountSKPD, "0", 5);
    String strNoUrut =  strNoRekPADLain + "." + strMonth + strYear.substring(2,4) + "." + jvc.fnLRPad("LPAD", strCountSKPD, "0", 4);
   	request.getSession().setAttribute("strNoUrut", strNoUrut);
%>

<%
	int intJmlUraian = 1;
	if (request.getSession().getAttribute("intJmlUraian") != null) {
		intJmlUraian = Integer.parseInt(request.getSession().getAttribute("intJmlUraian").toString());
	}
%>
    

<%@page import="java.text.DecimalFormat"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SKRD</title>
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
                }
            }
            
            function fnLastElement(lastElement) {
                document.main_form.hidLastElement.value = lastElement;
            }

            function fnGotoLastElement() {
                var vLastElement = document.main_form.hidLastElement.value;
                document.getElementById(vLastElement).focus();
            }

            function fnHitungTotal() {
                var vJmlUraian = document.main_form.hidJmlUraian.value;
                var vTotalUraian = 0;
                for (var a=1; a<=vJmlUraian; a++) {
                    var vUraian = parseInt(document.getElementById("rpUraian_"+a).value);
                    vTotalUraian = vTotalUraian + vUraian;
                }
                var vPajak = 0;
                if (document.getElementById("jmlPajak").value.length != 0) {
                    vPajak = parseInt(document.getElementById("jmlPajak").value);
                }
                var vSubTotal = vPajak + vTotalUraian;
                document.getElementById("jmlSubTotal").value = vSubTotal;	
                var vSubTerbilang = terbilang(parseInt(vSubTotal));
                document.getElementById("hidSubTerbilang").value = vSubTerbilang;

                var vKenaikan = 0;
                if (document.getElementById("txtKenaikan").value.length != 0) {
                	vKenaikan = parseInt(document.getElementById("txtKenaikan").value);
                }
                var vBunga = 0;
                if (document.getElementById("txtBunga").value.length != 0) {
                	vBunga = parseInt(document.getElementById("txtBunga").value);
                }
                var vTotal = vPajak + vTotalUraian + vKenaikan + vBunga;
                document.getElementById("jmlTotal").value = vTotal;

                var vTerbilang = terbilang(parseInt(vTotal));
                document.getElementById("hrfAngka").innerHTML = "# " + vTerbilang + " #";
                document.getElementById("hidTerbilang").value = vTerbilang;
            }

            function fnHitungPajakC(vNoUrut,vValue,vKonstanta,vFaktorKali) {
				var vNilai = 0;
				vNilai = vValue * vKonstanta * (vFaktorKali / 100);
				document.getElementById("rpUraian_"+vNoUrut).value = vNilai;
			}
            
            function fnHitungTotalPajakC(vJmlPajakC) {
                //alert('vJmlPajakC: ' + vJmlPajakC);
               	var vTotalUraian = 0;
                for (var a=1; a<=vJmlPajakC; a++) {
                    var vUraian = parseInt(document.getElementById("rpUraian_"+a).value);
                    vTotalUraian = vTotalUraian + vUraian;
                }
                
                var vPajak = 0;
                if (document.getElementById("jmlPajak").value.length != 0) {
                    vPajak = parseInt(document.getElementById("jmlPajak").value);
                }
                var vKenaikan = 0;
                if (document.getElementById("txtKenaikan").value.length != 0) {
                	vKenaikan = parseInt(document.getElementById("txtKenaikan").value);
                }
                var vBunga = 0;
                if (document.getElementById("txtBunga").value.length != 0) {
                	vBunga = parseInt(document.getElementById("txtBunga").value);
                }
                var vTotal = vPajak + vTotalUraian + vKenaikan + vBunga;
                document.getElementById("jmlTotal").value = vTotal;

                var vTerbilang = terbilang(parseInt(vTotal));
                document.getElementById("hrfAngka").innerHTML = "# " + vTerbilang + " #";
                document.getElementById("hidTerbilang").value = vTerbilang;
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
					document.main_form.hidJabatanPejabat.value = vNip;
					document.main_form.hidNIPPejabat.value = vJabatan;

					var vTglAwal = document.getElementById("txtTglAwal").value;
					document.main_form.hidDate1.value = vTglAwal;
					
					var vTglAkhir = document.getElementById("txtTglAkhir").value;
					document.main_form.hidDate2.value = vTglAkhir;
					
					var vIdxTahun = document.getElementById("slctTahun").selectedIndex;
					var vTahun = document.getElementById("slctTahun")[vIdxTahun].value;
					document.main_form.hidTahun.value = vTahun;
					
		            document.main_form.mode.value = 2;
		            document.main_form.method = "POST";
		            document.main_form.action = "srvPembayaran2";
		            document.main_form.submit();    
				} else {
					alert('Anda Belum Memilih Pejabat Penandatangan!');
				}
            }

            function fnSimpan(vKdCetak) {
                var vTglAwal = document.getElementById("txtTglAwal").value;
				document.main_form.hidDate1.value = vTglAwal;
				
				var vTglAkhir = document.getElementById("txtTglAkhir").value;
				document.main_form.hidDate2.value = vTglAkhir;
				
				var vIdxTahun = document.getElementById("slctTahun").selectedIndex;
				var vTahun = document.getElementById("slctTahun")[vIdxTahun].value;
				document.main_form.hidTahun.value = vTahun;
            
                var vJmlPajak = document.main_form.jmlPajak.value;
                document.main_form.hidJml.value = vJmlPajak;

                var vBunga = document.main_form.txtBunga.value;
                document.main_form.hidBunga.value = vBunga;

                var vKenaikan = document.main_form.txtKenaikan.value;
                document.main_form.hidKenaikan.value = vKenaikan;

                var vTotal = document.main_form.jmlTotal.value;
                document.main_form.hidTotal.value = vTotal;

                var vJnsPekerjaan = document.main_form.jnsPekerjaan.value;
                document.main_form.hidJnsPekerjaan.value = vJnsPekerjaan;

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

					document.main_form.hidKdCetak.value = vKdCetak;

					document.main_form.mode.value = 3;
	                document.main_form.method = "POST";
	                document.main_form.action = "srvPembayaran2";
	                document.main_form.submit();    
				} else {
					alert('Anda Belum Memilih Pejabat Penandatangan!');
				}
            }

            function fnAddUraian(vJmlUraian) {
            	document.main_form.method = "post";
                document.main_form.mode.value = 4;
                document.main_form.action = "srvPembayaran2";
                document.main_form.submit();
            }

            function fnDelUraian(vJmlUraian) {
            	if (vJmlUraian != '1') {
                    document.main_form.method = "post";
                    document.main_form.mode.value = 5;
                    document.main_form.action = "srvPembayaran2";
                    document.main_form.submit();
                }
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

			/**
            function fnChangeRetribusi(aa) {
				var vIdxRetribusi = document.getElementById("slctRetribusi_" + aa).selectedIndex;
				if (vIdxRetribusi != 0) {
					var vValue = document.getElementById("slctRetribusi_" + aa)[vIdxRetribusi].value;
					var vText = document.getElementById("slctRetribusi_" + aa)[vIdxRetribusi].text;
					document.getElementById("lblNoRek_" + aa).innerHTML = vValue;
					document.getElementById("hidLblNoRek_" + aa).value = vValue;
					document.getElementById("txtUraian_" + aa).value = vText;
				}
			}
			*/
        </script>
    </head>
    <body onload="fnChangePejabat();fnGetAllElement();fnGotoLastElement();">
    <form name="main_form">
        <input type="hidden" name="mode" id="mode">
        <input type="hidden" name="hidWidth" id="hidWidth" value="<%= strWidth %>">
        <input type="hidden" name="hidHeight" id="hidHeight" value="<%= strHeight %>">
        <input type="hidden" name="hidLastElement" id="hidLastElement" value="<%= strLastElement %>">
        <input type="hidden" name="hidJmlUraian" id="hidJmlUraian" value="<%= intJmlUraian %>">
        <input type="hidden" name="hidKodePajak" id="hidKodePajak" value="<%= vKodePADLain %>">
        
        <input type="hidden" name="hidNamaPejabat" id="hidNamaPejabat">
    	<input type="hidden" name="hidJabatanPejabat" id="hidJabatanPejabat">
    	<input type="hidden" name="hidNIPPejabat" id="hidNIPPejabat">
    	<input type="hidden" name="hidKdCetak" id="hidKdCetak">
    	
    	<input type="hidden" name="hidNoSKPD" id="hidNoSKPD" value="<%= strNoUrut %>">
        <input type="hidden" name="hidDate1" id="hidDate1" value="<%= strDate1 %>">
        <input type="hidden" name="hidDate2" id="hidDate2" value="<%= strDate2 %>">
        <input type="hidden" name="hidTahun" id="hidTahun">
        <input type="hidden" name="hidKdPemilik" id="hidKdPemilik" value="<%= strKdPemilik %>">
        <input type="hidden" name="hidNoRek" id="hidNoRek" value="<%= strNoRekPADLain %>">
        <input type="hidden" name="hidUraian" id="hidUraian" value="<%= strUraianRet %>">
        <input type="hidden" name="hidJml" id="hidJml">
        <input type="hidden" name="hidBunga" id="hidBunga">
        <input type="hidden" name="hidKenaikan" id="hidKenaikan">
        <input type="hidden" name="hidTotal" id="hidTotal">
        <input type="hidden" name="hidTglSKPD" id="hidTglSKPD" value="<%= strToday %>">
        <input type="hidden" name="hidTglJthTempo" id="hidTglJthTempo" value="<%= strNextDate %>">
        <input type="hidden" name="hidNoNPWPD" id="hidNoNPWPD" value="<%= strKdNPWP + strNPWPD + strWilNPWP%>">
        <input type="hidden" name="hidNoNPWRD" id="hidNoNPWRD" value="<%= strKdNPWR + strNPWRD + strWilNPWR%>">
        <input type="hidden" name="hidJnsPekerjaan" id="hidJnsPekerjaan" value="<%= strJnsPekerjaan %>">
        
        <input type="hidden" name="hidNamaBU" id="hidNamaBU" value="<%= strNamaBU %>">
		<input type="hidden" name="hidAlamatBU" id="hidAlamatBU" value="<%= strJalanBU + " " + strNoBU + " RT/RW " + strRTBU + "/" + strRWBU + " " + strUraianKelurahan + ", " + strUraianKecamatan + ", " + strUraianKabupaten + " " + strKdPosBU %>">
        
        <table border="1" cellpadding="2" cellspacing="0">
            <tr valign="top">
                <td align="center">PEMERINTAH KABUPATEN <%= strUraianKabupaten.toUpperCase() %></td>
                <td width="80%" align="center">
                    SURAT KETETAPAN DENDA PAJAK DAERAH<br>
                    (SKDP-DAERAH)
                </td>
                <td align="center">NOMOR&nbsp;URUT<br><%= strNoUrut %></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table>
                        <tr>
                            <td align="right">Masa</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td>
                                <input type="text" readonly value="<%= strDate1 %>" name="txtTglAwal" id="txtTglAwal" maxlength="10" size="10">
                                &nbsp;<button type="reset" id="btTglAwal">...</button>
                                &nbsp;s.d.&nbsp;
                                <input type="text" readonly value="<%= strDate2 %>" name="txtTglAkhir" id="txtTglAkhir" maxlength="10" size="10">
                                &nbsp;<button type="reset" id="btTglAkhir">...</button>
                            </td>
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
                            <script type="text/javascript">
                                Calendar.setup({
                                    inputField     :    "txtTglAkhir",      // id of the input field
                                    ifFormat       :    "%d/%m/%Y",       // format of the input field
                                    showsTime      :    false,            // will display a time selector
                                    button         :    "btTglAkhir",   // trigger for the calendar (button ID)
                                    singleClick    :    true,           // double-click mode
                                    step           :    1                // show all years in drop-down boxes (instead of every other year as default)
                                });
                            </script>
                        </tr>
                        <tr>
                            <td align="right">Tahun</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td>
                                <select name="slctTahun" id="slctTahun" onfocus="fnLastElement(this.name)">
                                <%
                                	for (int a=2000; a<=2030; a++) {
                                %>
                                    <option value="<%= a %>" <%= (String.valueOf(a).equals(String.valueOf(intYear)))?"SELECTED":"" %>><%= a %>
                                <%
                                	}
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr valign="top">
                            <td colspan="3">&nbsp;</td>
                        </tr>
                        <tr valign="top">
                            <td align="left">NAMA USAHA</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td><%= strNamaBU %></td>
                        </tr>
                        <tr valign="top">
                            <td align="left">NAMA PEMILIK</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td><%= strNamaPemilik %></td>
                        </tr>
                        <tr valign="top">
                            <td align="left">ALAMAT</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td><%= strJalanBU %>&nbsp;<%= strNoBU %>&nbsp;RT/RW&nbsp;<%= strRTBU %>/<%= strRWBU %><br>
                                <%= strUraianKelurahan %>,&nbsp;<%= strUraianKecamatan %><br>
                                <%= strUraianKabupaten %>&nbsp;<%= strKdPosBU %></td>
                        </tr>
                        <tr valign="top">
                            <td align="left">NOMOR POKOK WAJIB RETRIBUSI DAERAH (NPWRD)</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td><%= strKdNPWP %><%= strNPWPD %><%= strWilNPWP %></td>
                        </tr>
                        <tr valign="top">
                            <td align="left">TANGGAL JATUH TEMPO</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td><%= strNextDate %></td>
                        </tr>
                        <tr valign="top">
                            <td align="left">JENIS PEKERJAAN</td>
                            <td>&nbsp;:&nbsp;</td>
                            <td><input type="text" name="jnsPekerjaan" id="jnsPekerjaan"><%= strJnsPekerjaan %></input></td>
                        </tr>
                    </table>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td colspan="3">
                    <table border="1" width="100%" cellpadding="1" cellspacing="0" topmargin="0" leftmargin="0">
                        <tr align="center">
                            <td>NO</td>
                            <td>KODE REKENING</td>
                            <td width="60%">URAIAN RETRIBUSI DAERAH</td>
                            <td>JUMLAH (Rp)</td>
                        </tr>
    
                        <tr align="center">
                            <td>&nbsp;1&nbsp;</td>
                            <td>
                               <%= strNoRekPADLain %> 
                            </td>
                            <td style="text-align: center" width="60%">
                                <%= strUraianRet %>
                            </td>
                            <td>
                            	<%
                            		String strJmlPajak = "0";
                            		if (request.getSession().getAttribute("strJmlPajak") != null) {
                            			strJmlPajak = request.getSession().getAttribute("strJmlPajak").toString();
                            		}
                            	%>
                                <input style="text-align: right" value="<%= strJmlPajak %>" name="jmlPajak" id="jmlPajak" onfocus="fnLastElement(this.name)" onblur="fnHitungTotal()"></input>
                            </td>
                        </tr>
                        <%
                        	if (!strNoRekPADLain.equalsIgnoreCase("4110606")) {
	                        	for (int aa=1; aa<=intJmlUraian; aa++) {
	                        		String strUraian = "";
	                        		String strRpUraian = "0";
	                        		Hashtable htUraian = new Hashtable();
	                        		if (request.getSession().getAttribute("htUraian") != null) {
	                        			htUraian = (Hashtable) request.getSession().getAttribute("htUraian");
	                        			int intHtUraian = htUraian.size();
	                        			if (aa <= intHtUraian) {
	                        				String strArray[] = (String[]) htUraian.get(String.valueOf(aa));
	                        				strUraian = strArray[0];
	                        				strRpUraian = strArray[1];
	                        			}
	                        		}
                        %>
                        <tr align="center">
                            <td>&nbsp;</td>
                            <td>&nbsp;
                            	<!-- 
                            	<label id="lblNoRek_<--%= aa %>"></label>
                            	<input type="text" name="hidLblNoRek_<--%= aa %>" id="hidLblNoRek_<--%= aa %>"></input>
                            	 -->
                            </td>
                            <td>
                            	<input style="width: 80%; text-align: left;" value="<%= strUraian %>" type="text" name="txtUraian_<%= aa %>" id="txtUraian_<%= aa %>" onfocus="fnLastElement(this.name)">
                            	<!-- 
                            	<select onchange="fnChangeRetribusi(<--%= aa %>)" name="slctRetribusi_<--%= aa %>" id="slctRetribusi_<--%= aa %>" onfocus="fnLastElement(this.name)">
                            		<option></option>
                            		<%
                            			//if (request.getSession().getAttribute("htNoRekRetribusi") != null) {
                            			//	Hashtable<String, String[]> htNoRekRetribusi = (Hashtable<String, String[]>) request.getSession().getAttribute("htNoRekRetribusi");
                            			//	int intHtNoRekRetribusi = htNoRekRetribusi.size();
                            			//	for (int qq=1; qq<=intHtNoRekRetribusi; qq++) {
                            			//		String[] strArray = (String[]) htNoRekRetribusi.get(String.valueOf(qq));
                            			//		String strNoRek = strArray[0];
                            			//		String strKdRetribusi = strArray[1];
                            			//		String strKeterangan = strArray[2];
                            					%>
                            					
                            					<option value="<--%= strNoRek %>" <--%= (strUraian.equalsIgnoreCase(strKeterangan))?"SELECTED":"" %>><--%= strKeterangan %></option>
                            					
                            					<%
                            			//	}
                            			//}
                            		%>
                            	</select>
                            	 -->
                            </td>
                            <td>
                            	<input style="text-align: right" value="<%= strRpUraian %>" name="rpUraian_<%= aa %>" id="rpUraian_<%= aa %>" onfocus="fnLastElement(this.name)" onblur="fnHitungTotal()">
                            </td>
                        </tr>
                        <%
                        		}
                        %>
                        <tr align="left">
                            <td colspan="4">
                            	<button type="button" onclick="fnAddUraian(<%= intJmlUraian %>)" name="btAddUraian" id="btAddUraian">&nbsp;+&nbsp;</button>&nbsp;
                            	<button type="button" onclick="fnDelUraian(<%= intJmlUraian %>)" name="btMinUraian" id="btMinUraian">&nbsp;-&nbsp;</button>
                            </td>
                        </tr>
                        <%
                        	} else {
                        		if (request.getSession().getAttribute("htPajakC") != null) {
                        			Hashtable htPajakC = (Hashtable) request.getSession().getAttribute("htPajakC");
                        			int intPajakC = htPajakC.size();
                        			for (int qq=1; qq<=intPajakC; qq++) {
                        				String[] strArray = (String[]) htPajakC.get(String.valueOf(qq));
                        				
						%>
						
						<tr align="center">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td align="left">
                            	<table border="0" width="100%" cellpadding="0" cellspacing="0">
                            		<tr>
                            			<td align="left">
                            				<input style="text-align: left; width: 95%" value="<%= strArray[0] %>" type="text" name="txtUraian_<%= qq %>" id="txtUraian_<%= qq %>" onfocus="fnLastElement(this.name)"></input>
                            			</td>
                            			<td></td>
                            			<td align="right">
                            				<input type="text" style="width: 50px; text-align: right;" 
                            				onblur="fnHitungPajakC(<%= qq %>,this.value,<%= strArray[2] %>,<%= strArray[3] %>);fnHitungTotalPajakC(<%= intPajakC %>);" 
                            				name="PajakC_<%= qq %>" id="PajakC_<%= qq %>">&nbsp;
                            				<%= strArray[1] %>&nbsp;*&nbsp;<%= Double.parseDouble(strArray[2]) %>&nbsp;*&nbsp;<%= Double.parseDouble(strArray[3])/100 %>
                            			</td>
                            		</tr>
                            	</table>
                            	<input type="hidden" name="hidPajakC" name="hidPajakC" value="<%= intPajakC %>"></input>
                            </td>
                            <td>
                            	<input style="text-align: right" value="0" name="rpUraian_<%= qq %>" id="rpUraian_<%= qq %>" onfocus="fnLastElement(this.name)"></input>
                            </td>
                        </tr>
						
						<%     
                        			}
                        		}
                        	}
                        %>
                        
                        <%
	                        String strJmlSubTotal = "0";
	                		if (request.getSession().getAttribute("strJmlSubTotal") != null) {
	                			strJmlSubTotal = request.getSession().getAttribute("strJmlSubTotal").toString();
	                		}
	                	%>
                        <tr align="center">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td width="60%">
                                Jumlah Ketetapan Pokok Retribusi
                            </td>
                            <td>
                            	<input type="text" value="<%= strJmlSubTotal %>" style="text-align: right" name="jmlSubTotal" id="jmlSubTotal" readonly="readonly"></input>
                            	<input style="display: none" type="text" name="hidSubTerbilang" id="hidSubTerbilang">
                            </td>
                        </tr>
                        <tr align="center">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td width="60%" align="left">
                                <table>
                                    <tr>
                                        <td>Jumlah Sanksi</td>
                                        <td>&nbsp;:&nbsp;</td>
                                        <td>a. Bunga</td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>b. Kenaikan</td>
                                    </tr>
                                </table>
                            </td>
                            	<%
                            		String strBunga = "0";
                            		if (request.getSession().getAttribute("strBunga") != null) {
                            			strBunga = request.getSession().getAttribute("strBunga").toString();
                            		}
                            		
                            		String strKenaikan = "0";
                            		if (request.getSession().getAttribute("strKenaikan") != null) {
                            			strKenaikan = request.getSession().getAttribute("strKenaikan").toString();
                            		}
                            		
                            		String strJmlTotal = "0";
                            		if (request.getSession().getAttribute("strJmlTotal") != null) {
                            			strJmlTotal = request.getSession().getAttribute("strJmlTotal").toString();
                            		}
                            	%>
                                
                            <td>
                                <table>
                                    <tr>
                                        <td><input type="text" value="<%= strBunga %>" style="text-align: right" name="txtBunga" id="txtBunga" onfocus="fnLastElement(this.name)" onblur="fnHitungTotal()"></td>
                                    </tr>
                                    <tr>
                                       <td><input type="text" value="<%= strKenaikan %>" style="text-align: right" name="txtKenaikan" id="txtKenaikan" onfocus="fnLastElement(this.name)" onblur="fnHitungTotal()"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr align="center">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td width="60%">
                                Jumlah Keseluruhan
                            </td>
                            <td><input type="text" value="<%= strJmlTotal %>" style="text-align: right" name="jmlTotal" id="jmlTotal" readonly="readonly"></input></td>
                        </tr>
                        <tr align="left">
                            <td colspan="4">
                                Dengan huruf:&nbsp;<label id="hrfAngka"></label>
                                <input style="display: none" type="text" name="hidTerbilang" id="hidTerbilang">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
            	<td colspan="3" align="left">
            		Penandatangan:<br>
            		<select name="slctPejabat" id="slctPejabat" onchange="fnChangePejabat()">
					<option></option>
					<%
						Hashtable htPejabat = new Hashtable();
					    if (request.getSession().getAttribute("htInfoPejabat") != null) {
					    	htDataLogin = (Hashtable) request.getSession().getAttribute("htInfoPejabat");
					        int intDataLogin = htDataLogin.size();
					        for (int a=1; a<=intDataLogin; a++) {
					            String[] strArray = (String[]) htDataLogin.get(String.valueOf(a));
					            if (strArray[4].equals("1")) {
					            %>
					            	<option value="<%= strArray[1] %>,<%= strArray[2] %>,<%= strArray[3] %>" <%= (strArray[0].equals("31000"))?"SELECTED":"" %> ><%= strArray[1] %></option>
					            <%
					            }
					        }
					    }
					%>
					</select><br>
					<label id="lblJabatanPejabat"></label><br>
					NIP: <label id="lblNIPPejabat"></label>
            	</td>
            </tr>
            <tr>
            	<td colspan="3" align="center">
            		<button name="btCetak" id="btCetak" onclick="fnSimpan('C')">Cetak</button>
            		<button name="btSimpan" id="btSimpan" onclick="fnSimpan('L')">Pelunasan</button>
            		<button name="btClose" id="btClose" onclick="self.close()">Tutup</button>
            	</td>
            </tr>
        </table>
    </form>
    </body>
</html>
