/*
 * Terbilang2.java
 *
 * Created on May 19, 2008, 9:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Utilities;

/**
 *
 * @author The_Who
 */
public class Terbilang2 {
    
    /** Creates a new instance of Terbilang2 */
    public String Terbilang2(int intAngka) {
        String strTerbilang = "";
        int intJmlDigit = JmlDigit(intAngka);
        if (intJmlDigit == 1) {
            strTerbilang = Angka2Huruf(intAngka);
        } else if (intJmlDigit == 2) {
            strTerbilang = DuaDigit(intAngka);
        } else if (intJmlDigit == 3) {
            strTerbilang = TigaDigit(intAngka);
        } else if (intJmlDigit > 3) {
            strTerbilang = LebihDariTigaDigit(intAngka);
        }
        return strTerbilang.toUpperCase();
    }
    
    private String LebihDariTigaDigit(int intAngka) {
        String strLebihDariTigaDigit = "";
        
        String strAngka = String.valueOf(intAngka);
        int intJmlDigit = JmlDigit(intAngka);
        int a = 0;
        int b = 3;
        int intSatuan = 3;
        
        while (intJmlDigit > 3) {
            String str3AngkaTerakhir = strAngka.substring((strAngka.length()-b), (strAngka.length()-a));
            int int3AngkaTerakhir = Integer.parseInt(str3AngkaTerakhir);
            if (int3AngkaTerakhir != 0) {
                String str3Digit = TigaDigit(int3AngkaTerakhir);

                String strSatuan = "";
                if (intSatuan > 3) {
                    strSatuan = Satuan(intSatuan);
                }

                strLebihDariTigaDigit = str3Digit + " " + strSatuan + " " + strLebihDariTigaDigit;

                intJmlDigit = intJmlDigit - 3;
                a = a + 3;
                b = b + 3;
                intSatuan = intSatuan + 3;
            } else {
                strLebihDariTigaDigit = " ";
                intJmlDigit = 0;
            }
        }
        
        int intJmlDigitDepan = JmlDigit(intAngka) % 3;
        String strTerbilangAngkaDepan = "";
        if (intJmlDigitDepan > 0) {
            String strAngkaDepan = strAngka.substring(0, intJmlDigitDepan);
            int intAngkaDepan = Integer.parseInt(strAngkaDepan);
            if (intJmlDigitDepan == 1) {
                if ((intAngkaDepan == 1) & (intAngka < 1000000)) {
                    strTerbilangAngkaDepan = "SE";
                } else {
                    strTerbilangAngkaDepan = Angka2Huruf(intAngkaDepan) + " ";
                }
            } else if (intJmlDigitDepan == 2) {
                strTerbilangAngkaDepan = DuaDigit(intAngkaDepan) + " ";
            } else if (intJmlDigitDepan == 3) {
                strTerbilangAngkaDepan = TigaDigit(intAngkaDepan) + " ";
            }
        } else {
            String strAngkaDepan = strAngka.substring(0, 3);
            int intAngkaDepan = Integer.parseInt(strAngkaDepan);
            strTerbilangAngkaDepan = TigaDigit(intAngkaDepan) + " ";
        }
       
        String strSatuanAngkaDepan = Satuan(JmlDigit(intAngka));
        String strAngkaDepanSatuan = strTerbilangAngkaDepan + strSatuanAngkaDepan;
        
        return strAngkaDepanSatuan.toUpperCase() + " " + strLebihDariTigaDigit.toUpperCase();
    }
    
    private String TigaDigit(int intAngka) {
        String strTigaDigit = "";
        String strAngkaRatusan = String.valueOf(intAngka).substring(0,1);
        int intAngkaRatusan = Integer.parseInt(strAngkaRatusan);
        String strSatuan = Satuan(JmlDigit(intAngka));
        int intAngkaSisa = Integer.parseInt(String.valueOf(intAngka).substring(1,3));
        String strAngkaSisa = DuaDigit(intAngkaSisa);
        if (intAngkaRatusan == 1) {
            strTigaDigit = "SE" 
                         + strSatuan + " " 
                         + strAngkaSisa;
        } else {
            strTigaDigit = Angka2Huruf(Integer.parseInt(strAngkaRatusan)) + " " 
                         + strSatuan + " " 
                         + strAngkaSisa;
        }
        return strTigaDigit;
    }
    
    private String DuaDigit(int intAngka) {
        String strDuaDigit = "";
        if (intAngka <= 19) {
            strDuaDigit = Angka2Huruf(intAngka);
        } else {
            String strAngkaPuluhan = String.valueOf(intAngka).substring(0,1);
            String strAngkaSatuan = String.valueOf(intAngka).substring(1,2);
            String strSatuan = Satuan(JmlDigit(intAngka));
            strDuaDigit = Angka2Huruf(Integer.parseInt(strAngkaPuluhan)) + " " 
                        + strSatuan + " " 
                        + Angka2Huruf(Integer.parseInt(strAngkaSatuan));
        }
        return strDuaDigit;
    }
    
    private int JmlDigit(int intAngka) {
        String strAngka = String.valueOf(intAngka);
        int intJmlDigit = strAngka.length();
        return intJmlDigit;
    }
    
    private String Satuan(int intJmlDigit) {
        String strSatuan = "";
        if (intJmlDigit == 2) {
            strSatuan = "Puluh";
        } else if (intJmlDigit == 3) {
            strSatuan = "Ratus";
        } else if ((intJmlDigit > 3) & (intJmlDigit <= 6)) {
            strSatuan = "Ribu";
        } else if ((intJmlDigit > 6) & (intJmlDigit <= 9)) {
            strSatuan = "Juta";
        } else if ((intJmlDigit > 9) & (intJmlDigit <= 12)) {
            strSatuan = "Milyar";
        } else if ((intJmlDigit > 12) & (intJmlDigit <= 15)) {
            strSatuan = "Triliun";
        }
        return strSatuan;
    }
    
    private String Angka2Huruf(int intAngka) {
        String strAngka = "";
        if (intAngka == 1) {
            strAngka = "Satu";
        } else if (intAngka == 2) {
            strAngka = "Dua";
        } else if (intAngka == 3) {
            strAngka = "Tiga";
        } else if (intAngka == 4) {
            strAngka = "Empat";
        } else if (intAngka == 5) {
            strAngka = "Lima";
        } else if (intAngka == 6) {
            strAngka = "Enam";
        } else if (intAngka == 7) {
            strAngka = "Tujuh";
        } else if (intAngka == 8) {
            strAngka = "Delapan";
        } else if (intAngka == 9) {
            strAngka = "Sembilan";
        } else if (intAngka == 10) {
            strAngka = "Sepuluh";
        } else if (intAngka == 11) {
            strAngka = "Sebelas";
        } else if (intAngka == 12) {
            strAngka = "Dua Belas";
        } else if (intAngka == 13) {
            strAngka = "Tiga Belas";
        } else if (intAngka == 14) {
            strAngka = "Empat Belas";
        } else if (intAngka == 15) {
            strAngka = "Lima Belas";
        } else if (intAngka == 16) {
            strAngka = "Enam Belas";
        } else if (intAngka == 17) {
            strAngka = "Tujuh Belas";
        } else if (intAngka == 18) {
            strAngka = "Delapan Belas";
        } else if (intAngka == 19) {
            strAngka = "Sembilan Belas";
        } else {
            strAngka = " ";
        }
        return strAngka;
    }
}
