/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Mengubah Comma Separated Value menjadi Array
 * Input  : 1,2,3
 * Output : [1,2,3]
 */
function fnCSV2Array(vString, vSep) {
	//var vString = document.test.txtTest.value;
	//var vSep = document.test.txtTest1.value;
	//alert('vString: ' + vString + '\nvSep: ' + vSep);
		
	var vStrLength = vString.length
	var vArrLength = 1;
	for (var a=0; a<=vStrLength; a++) {
		if (vString.charAt(a) == vSep) {
			vArrLength = vArrLength + 1; 
		}
	}
	//alert('vArrLength: ' + vArrLength);
	
	var vWord = new Array;
	var vIdxWord = 0;
	var vString2 = vString;
	var vOldIdx = 0;
	for (var a=0; a<=vString.length; a++) {
		if (vString.indexOf(vSep) > 0) {
			if (vString.charAt(a) == vSep) {
				vIdxSep = a;
				
				var vNullCheck = vString.substring(vOldIdx,vIdxSep);
				if (vNullCheck == 'null') {
					vNullCheck = '';
				}
				//vWord[vIdxWord] = vString.substring(vOldIdx,vIdxSep);
				vWord[vIdxWord] = vNullCheck;
			
				vString2 = vString2.substring(vIdxSep+1,vString2.length);
				vIdxWord = vIdxWord + 1;
				vOldIdx = vIdxSep + 1; 
				
				if (vString.lastIndexOf(vSep) == a) {
					var vNullCheck1 = vString.substring(a+1,vString.length);
					if (vNullCheck1 == 'null') {
						vNullCheck1 = '';
					}
					//vWord[vArrLength-1] = vString.substring(a+1,vString.length);
					vWord[vArrLength-1] = vNullCheck1;
				}
			}
		} else {
			var vNullCheck = vString;
			if (vNullCheck == 'null') {
				vNullCheck = '';
			}
			//vWord[vIdxWord] = vString;
			vWord[vIdxWord] = vNullCheck;
		}
	}
	
	//for (var b=0; b<=vArrLength-1; b++) {
	//	alert('vWord['+b+']: ' + vWord[b]);
	//}
	return vWord;
}

/*
Fungsi terbilang dalam JavaScript
dibuat oleh Budi Adiono (iKode.net)
*/

function terbilang(bilangan) {

  bilangan    = String(bilangan);
  var angka   = new Array('0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');
  var kata    = new Array('','Satu','Dua','Tiga','Empat','Lima','Enam','Tujuh','Delapan','Sembilan');
  var tingkat = new Array('','Ribu','Juta','Milyar','Triliun');

  var panjang_bilangan = bilangan.length;

  /* pengujian panjang bilangan */
  if (panjang_bilangan > 15) {
    kaLimat = "Diluar Batas";
    return kaLimat;
  }

  /* mengambil angka-angka yang ada dalam bilangan, dimasukkan ke dalam array */
  for (i = 1; i <= panjang_bilangan; i++) {
    angka[i] = bilangan.substr((panjang_bilangan-i),1);
  }

  i = 1;
  j = 0;
  kaLimat = "";


  /* mulai proses iterasi terhadap array angka */
  while (i <= panjang_bilangan) {

    subkaLimat = "";
    kata1 = "";
    kata2 = "";
    kata3 = "";

    /* untuk Ratusan */
    if (angka[i+2] != "0") {
      if (angka[i+2] == "1") {
        kata1 = "Seratus";
      } else {
        kata1 = kata[angka[i+2]] + " Ratus";
      }
    }

    /* untuk Puluhan atau Belasan */
    if (angka[i+1] != "0") {
      if (angka[i+1] == "1") {
        if (angka[i] == "0") {
          kata2 = "Sepuluh";
        } else if (angka[i] == "1") {
          kata2 = "Sebelas";
        } else {
          kata2 = kata[angka[i]] + " Belas";
        }
      } else {
        kata2 = kata[angka[i+1]] + " Puluh";
      }
    }

    /* untuk Satuan */
    if (angka[i] != "0") {
      if (angka[i+1] != "1") {
        kata3 = kata[angka[i]];
      }
    }

    /* pengujian angka apakah tidak nol semua, lalu ditambahkan tingkat */
    if ((angka[i] != "0") || (angka[i+1] != "0") || (angka[i+2] != "0")) {
      subkaLimat = kata1+" "+kata2+" "+kata3+" "+tingkat[j]+" ";
    }

    /* gabungkan variabe sub kaLimat (untuk Satu blok 3 angka) ke variabel kaLimat */
    kaLimat = subkaLimat + kaLimat;
    i = i + 3;
    j = j + 1;

  }

  /* mengganti Satu Ribu jadi Seribu jika diperlukan */
  if ((angka[5] == "0") && (angka[6] == "0")) {
    kaLimat = kaLimat.replace("Satu Ribu","Seribu");
  }

  return kaLimat + "Rupiah";
}

/**
 * Memberi nama setiap element
 */
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
				
/**
 * Menyimpan data element terakhir yang digunakan
 */
function fnLastElement(vElement) {
        document.main_form.hidLastElement.value = vElement;
}
		
/**
 * Menuju element terakhir yang digunakan
 */		
function fnGotoLastElement() {
        var vLastElement = document.main_form.hidLastElement.value;
        document.getElementById(vLastElement).focus();
}

/**
 * Mencegah klik kanan
 */
function right(e) {
    if (navigator.appName == 'Netscape' && (e.which == 3 || e.which == 2)) {
        return false;
    } else if (navigator.appName == 'Microsoft Internet Explorer' && (event.button == 2 || event.button == 3)) {
        alert("Maaf, anda tidak bisa menggunakan menu Tombol Kanan Mouse!");
        return false;
    }
    return true;
}

document.onmousedown=right;
document.onmouseup=right;
if (document.layers) window.captureEvents(Event.MOUSEDOWN);
if (document.layers) window.captureEvents(Event.MOUSEUP);
window.onmousedown=right;
window.onmouseup=right;

top.window.moveTo(0,0);
if (document.all) {
    top.window.resizeTo(screen.availWidth,screen.availHeight);
} else if (document.layers||document.getElementById) {
    if (top.window.outerHeight<screen.availHeight||top.window.outerWidth<screen.availWidth){
        top.window.outerHeight = screen.availHeight;
        top.window.outerWidth = screen.availWidth;
    }
}
