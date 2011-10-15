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

/**
 * Menghilangkan tanda titik dan koma pada rupiah
 */
function dropCurrencySign(number) {
        var vNumber = number.replace(/\.|\,00/g, "");
        return vNumber
}

/**
 * Menuliskan tanda ribuan pada saat mengetik
 */
function formatAngka(b) {
    var vValue0 = b.value;
    vValue = dropCurrencySign(vValue0).replace(/[^\d]/g,"");
    var vValLength = vValue.length;
    var vCek = "";
    if (vValLength > 3) {
            var vCount = 0;
            for (var a=vValLength; a>0; a--) {
                    vCount = vCount + 1;
                    if ((vCount%3 == 1) && (a != vValue.length)) {
                            vCek = vValue.substring(0,a) + "." + vValue.substring(a,vValue.length);
                    } else if ((vCount%3 == 1) && (a == vValue.length)) {
                            vCek = vValue.substring(0,a) + vValue.substring(a,vValue.length);
                    }
                    vValue = vCek;
            }
            //alert('vCount: ' + vCount + '\nvValue: ' + vValue);
    }
    return vValue;
}

/**
 * Menghapus isi kolom pada saat mulai mengetik
 */
function delContent(compName, compValue) {
    if ((compValue == "0,00") || (compValue == "0")) {
            document.getElementById(compName).value = '';
    }
}

/**
 * filter untuk text box
 * tipe 0 : huruf
 * tipe 1 : angka
 * tipe 2 : huruf dan angka
 * sample:
 * onkeypress="return filterInput(1, event)"
 * onkeypress="return filterInput(1, event, false, '.')"
 */
function filterInput(filterType, evt, allowDecimal, allowCustom){
    var keyCode, Char, inputField, filter = '';
    var alpha = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var num   = '0123456789';

    // Get the Key Code of the Key pressed if possible else - allow
    if(window.event){
        keyCode = window.event.keyCode;
		evt = window.event;
    } else if (evt)keyCode = evt.which;
    else return true;

    // Setup the allowed Character Set
    if(filterType == 0) filter = alpha;
    else if(filterType == 1) filter = num;
    else if(filterType == 2) filter = alpha + num;
    if(allowCustom)filter += allowCustom;
    if(filter == '')return true;

    // Get the Element that triggered the Event
    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;

    // If the Key Pressed is a CTRL key like Esc, Enter etc - allow
    if((keyCode==null) || (keyCode==0) || (keyCode==8) || (keyCode==9) || (keyCode==13) || (keyCode==27) )return true;

    // Get the Pressed Character
    Char = String.fromCharCode(keyCode);

    // If the Character is a number - allow
    if((filter.indexOf(Char) > -1)) return true;

    // Else if Decimal Point is allowed and the Character is '.' - allow
    else if(filterType == 1 && allowDecimal && (Char == '.') && inputField.value.indexOf('.') == -1)return true;
    else return false;
}

/**
 * Format Mata Uang
 */
function fnFormatNum(num)
{

	num = num.toString();
	num = dropCurrencySign(num).replace(/[^\d]/g,"");
	var suf="";
	var pre="";
	pre=NUM_POB_DEL
	suf=NUM_POF_DEL
	var num_dec = "";
	for(var i=0;i<NUM_DEC;i++)
	{
		num_dec=num_dec+"0";
	}
	if(num == "")
	{
		num = "0"+NUM_DEC_DEL+num_dec;
	}
	else
	{
		if(num.substring(0,1)=="-")
		{
			num=NUM_NEF_DEL+num.substring(1,num.length);
			if(num.substring(num.substring.length-1,num.substring.length)!=NUM_NEB_DEL)
			{
				if(num.substring(num.substring.length-1,num.substring.length)==NUM_POB_DEL)
				{
					num=num.substring(0,num.substring.length-1)+NUM_NEB_DEL;
				}
				else
				{
					num=num+NUM_NEB_DEL;
				}
			}
		}
		if(NUM_NEF_DEL!="" && num.indexOf(NUM_NEF_DEL)==0)
		{
			suf=NUM_NEF_DEL;
			num=num.substring(1,num.length);
		}
		if(NUM_NEB_DEL!="" && num.indexOf(NUM_NEB_DEL)==num.length-1)
		{
			pre=NUM_NEB_DEL;
			num=num.substring(0,num.length-1);
		}
		if(NUM_POF_DEL!="" && num.indexOf(NUM_POF_DEL)==0)
		{
			suf=NUM_POF_DEL;
			num=num.substring(1,num.length)

		}
		if(NUM_POB_DEL!="" && num.indexOf(NUM_POB_DEL)==num.length-1)
		{
			pre=NUM_POB_DEL;
			num=num.substring(0,num.length-1);
		}
		if(suf==NUM_POF_DEL)
		{
			pre=NUM_POB_DEL;
		}
		if(suf==NUM_NEF_DEL)
		{
			pre=NUM_NEB_DEL;
		}
		if(num.indexOf(NUM_DEC_DEL)==-1)
		{
			num=num.toString().replace(".",NUM_DEC_DEL);
		}
		obj=num.split(NUM_THD_DEL);
		for(i=0;i<obj.length;i++)
		{
			num=num.toString().replace(NUM_THD_DEL,"");
		}
		num=num.toString().replace(NUM_DEC_DEL,".");
		if(isNaN(num))
			num = "0";

		num_dec="1"+num_dec;
		num_dec=parseInt(num_dec);
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*num_dec+0.50000000001);

		cents = num%num_dec;
		num = Math.floor(num/num_dec).toString();

		var newCents = NUM_DEC - ((cents).toString()).length;
		 for(var j=0;j < newCents;j++){
		  cents = "0" + cents;
		 }

		 for(var i=0; i<Math.floor((num.length-(1+i))/3); i++)
  			num = num.substring(0, num.length-(4*i+3))+ NUM_THD_DEL +num.substring(num.length-(4*i+3));

  		num = ((sign)?'':'-') + num + NUM_DEC_DEL + cents
	}
	num=suf+num+pre;
	return num;
}