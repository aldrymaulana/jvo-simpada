/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jvo.simpada.common;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jvCommon {

    public jvCommon() {
    }

    private static String fnCut(String fStrSource, char cutChar) {
        String fStrReturn = "";
        for (int i = 0; i < fStrSource.length(); i++) {
            if (fStrSource.charAt(i) != cutChar) {
                fStrReturn = fStrReturn + fStrSource.charAt(i);
            }
        }
        return fStrReturn;
    }

    public static String fnCutComma(String fStrSource) {
        return fnCut(fStrSource, ',');
    }

    /**
     * fnFormatNumber Indonesia
     */
    public static String fnFormatNumberInd(String fStrSource) {
        String sign = "";
        String passValue_value = "";

        if (fStrSource != null && fStrSource.length() != 0 && !fStrSource.equals("")) {
            fStrSource = fnCutComma(fStrSource);

            int lIntRecCount = fStrSource.indexOf(".");
            if (lIntRecCount != -1) {
                fStrSource = fStrSource.substring(0, lIntRecCount);
            }

            if (fStrSource.substring(0, 1).equals("-")) {
                sign = fStrSource.substring(0, 1);
                fStrSource = fStrSource.substring(1);
            }

            for (int i = 0; i < ((fStrSource.length() - (1 + i)) / 3); i++) {
                fStrSource = fStrSource.substring(0, fStrSource.length() - (4 * i + 3)) + "." + fStrSource.substring(fStrSource.length() - (4 * i + 3));
            }

            passValue_value = sign + fStrSource;
        } else {
            passValue_value = "0";
        }

        return passValue_value + ",00";
    }

    public String fnCurrencyFormat(String strInput) {
        System.out.println("strInput: " + strInput);
        String strOutput = "";
        Locale targetLocale = new Locale("in", "ID");
        try {
            if (strInput.length() > 0) {
                NumberFormat formatter = NumberFormat.getNumberInstance(targetLocale);
                strOutput = formatter.format(Double.parseDouble(strInput));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("strOutput: " + strOutput);
        return strOutput;
    }

//    public String fnDropCurrencySign(String strNumber) {
//        String strReturn = "0";
//        String strNumber2 = strNumber.replaceAll(",", "");
//        strReturn = strNumber2;
//        return strReturn;
//    }

    /**
     * fnDropCurrencySign
     * input: 1.200,00
     * output: 1200
     */
    public static String fnDropCurrencySign(String strNumber) {
            String strReturn = "0";
//            System.out.println("strNumber: " + strNumber);
            String strNumber1 = strNumber.replaceAll(",00","");
//            System.out.println("strNumber1: " + strNumber1);
            String strNumber2 = strNumber1.replaceAll("\\.","");
//            System.out.println("strNumber2: " + strNumber2);
            strReturn = strNumber2;
            return strReturn;
    }

    /**
     * Menghilangkan tanda titik dan koma pada rupiah
     * http://www.vogella.de/articles/JavaRegularExpressions/article.html
     */
    public static String fnDropCurrencySignWRegex(String strNumber) {
        String REGEX = "/.|,00/g";
        String REPLACE = "";
        Pattern replace = Pattern.compile(REGEX);
        Matcher matcher2 = replace.matcher(strNumber);
        return matcher2.replaceAll(REPLACE);
    }

    public Date fnStr2Date(String strDate, String strDateFormat) {
        Date rstDate = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            rstDate = sdf.parse(strDate);
        } catch (Exception exp) {
            System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
        }
        return rstDate;
    }

    public String fnDate2Str(Date dtDate, String strDateFormat) {
        String strDate = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            strDate = sdf.format(dtDate);
        } catch (Exception exp) {
            System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
        }
        return strDate;
    }

    public String fnGetValue(String strInput) {
        String strReturn = "";
        if (strInput != null) {
            strReturn = strInput;
        }
        return strReturn;
    }

    public String fnRemoveLPAD(String strSource) {
        String strReturn = strSource;
        System.out.println((new StringBuilder("strSource: ")).append(strSource).toString());
        int intLast = 0;
        int intSource = strSource.length();
        for (int a = 0; a <= intSource - 1; a++) {
            String strCek = strSource.substring(a, a + 1);
            if (!strCek.equals("0")) {
                break;
            }
            intLast = a + 1;
        }

        strReturn = strSource.substring(intLast, strSource.length());
        System.out.println((new StringBuilder("strReturn: ")).append(strReturn).toString());
        return strReturn;
    }

    public String fnLRPad(String strPos, String strSource, String strChar, int strLength) {
        int intLength = strSource.length();
        String strPad = "";
        String strReturn = "";
        if (intLength < strLength) {
            for (int a = 1; a <= strLength - intLength; a++) {
                strPad = (new StringBuilder(String.valueOf(strPad))).append(strChar).toString();
            }

            System.out.println((new StringBuilder("strPad: ")).append(strPad).toString());
            if (strPos.equalsIgnoreCase("LPAD")) {
                strReturn = (new StringBuilder(String.valueOf(strPad))).append(strSource).toString();
                System.out.println((new StringBuilder("LPAD: ")).append(strReturn).toString());
            } else if (strPos.equalsIgnoreCase("RPAD")) {
                strReturn = (new StringBuilder(String.valueOf(strSource))).append(strPad).toString();
                System.out.println((new StringBuilder("RPAD: ")).append(strReturn).toString());
            }
        } else {
            strReturn = strSource;
            System.out.println((new StringBuilder("strSource: ")).append(strReturn).toString());
        }
        return strReturn;
    }

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
            String str3AngkaTerakhir = strAngka.substring(strAngka.length() - b, strAngka.length() - a);
            int int3AngkaTerakhir = Integer.parseInt(str3AngkaTerakhir);
            if (int3AngkaTerakhir != 0) {
                String str3Digit = TigaDigit(int3AngkaTerakhir);
                String strSatuan = "";
                if (intSatuan > 3) {
                    strSatuan = Satuan(intSatuan);
                }
                strLebihDariTigaDigit = (new StringBuilder(String.valueOf(str3Digit))).append(" ").append(strSatuan).append(" ").append(strLebihDariTigaDigit).toString();
                intJmlDigit -= 3;
                a += 3;
                b += 3;
                intSatuan += 3;
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
                if ((intAngkaDepan == 1) & (intAngka < 0xf4240)) {
                    strTerbilangAngkaDepan = "SE";
                } else {
                    strTerbilangAngkaDepan = (new StringBuilder(String.valueOf(Angka2Huruf(intAngkaDepan)))).append(" ").toString();
                }
            } else if (intJmlDigitDepan == 2) {
                strTerbilangAngkaDepan = (new StringBuilder(String.valueOf(DuaDigit(intAngkaDepan)))).append(" ").toString();
            } else if (intJmlDigitDepan == 3) {
                strTerbilangAngkaDepan = (new StringBuilder(String.valueOf(TigaDigit(intAngkaDepan)))).append(" ").toString();
            }
        } else {
            String strAngkaDepan = strAngka.substring(0, 3);
            int intAngkaDepan = Integer.parseInt(strAngkaDepan);
            strTerbilangAngkaDepan = (new StringBuilder(String.valueOf(TigaDigit(intAngkaDepan)))).append(" ").toString();
        }
        String strSatuanAngkaDepan = Satuan(JmlDigit(intAngka));
        String strAngkaDepanSatuan = (new StringBuilder(String.valueOf(strTerbilangAngkaDepan))).append(strSatuanAngkaDepan).toString();
        return (new StringBuilder(String.valueOf(strAngkaDepanSatuan.toUpperCase()))).append(" ").append(strLebihDariTigaDigit.toUpperCase()).toString();
    }

    private String TigaDigit(int intAngka) {
        String strTigaDigit = "";
        String strAngkaRatusan = String.valueOf(intAngka).substring(0, 1);
        int intAngkaRatusan = Integer.parseInt(strAngkaRatusan);
        String strSatuan = Satuan(JmlDigit(intAngka));
        int intAngkaSisa = Integer.parseInt(String.valueOf(intAngka).substring(1, 3));
        String strAngkaSisa = DuaDigit(intAngkaSisa);
        if (intAngkaRatusan == 1) {
            strTigaDigit = (new StringBuilder("SE")).append(strSatuan).append(" ").append(strAngkaSisa).toString();
        } else {
            strTigaDigit = (new StringBuilder(String.valueOf(Angka2Huruf(Integer.parseInt(strAngkaRatusan))))).append(" ").append(strSatuan).append(" ").append(strAngkaSisa).toString();
        }
        return strTigaDigit;
    }

    private String DuaDigit(int intAngka) {
        String strDuaDigit = "";
        if (intAngka <= 19) {
            strDuaDigit = Angka2Huruf(intAngka);
        } else {
            String strAngkaPuluhan = String.valueOf(intAngka).substring(0, 1);
            String strAngkaSatuan = String.valueOf(intAngka).substring(1, 2);
            String strSatuan = Satuan(JmlDigit(intAngka));
            strDuaDigit = (new StringBuilder(String.valueOf(Angka2Huruf(Integer.parseInt(strAngkaPuluhan))))).append(" ").append(strSatuan).append(" ").append(Angka2Huruf(Integer.parseInt(strAngkaSatuan))).toString();
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

    public static String fnUpperLower(String strSource) {
        String strOutput = "";
        strSource = strSource.toLowerCase();
        int intCount = 0;
        int intSource = strSource.length();
        for (int a = 0; a <= intSource - 1; a++) {
            char ch = strSource.charAt(a);
            if (a != 0 && ch == ' ') {
                intCount++;
            }
        }

        String strSpcPos[] = (String[]) null;
        if (intCount > 0) {
            strSpcPos = new String[intCount];
            int intCounter = 0;
            for (int a = 0; a <= intSource - 1; a++) {
                char ch = strSource.charAt(a);
                if (a != 0 && ch == ' ') {
                    strSpcPos[intCounter] = String.valueOf(a);
                    intCounter++;
                }
            }

        }
        String strHurufAwal = (new StringBuilder()).append(strSource.charAt(0)).toString();
        strHurufAwal = strHurufAwal.toUpperCase();
        String strResult = (new StringBuilder(String.valueOf(strHurufAwal))).append(strSource.substring(1, strSource.length())).toString();
        String strResult1 = "";
        String strDepan = "";
        String strHurufAwal1 = "";
        String strBelakang = "";
        if (strSpcPos != null && strSpcPos.length > 0) {
            int intSpc = strSpcPos.length;
            for (int a = 0; a <= intSpc - 1; a++) {
                int intPos = Integer.parseInt(strSpcPos[a]);
                if (strResult1.trim().length() == 0) {
                    strDepan = strResult.substring(0, intPos + 1);
                    strHurufAwal1 = (new StringBuilder()).append(strSource.charAt(intPos + 1)).toString();
                    strHurufAwal1 = strHurufAwal1.toUpperCase();
                    strBelakang = strResult.substring(intPos + 2, strResult.length());
                    strResult1 = (new StringBuilder(String.valueOf(strDepan))).append(strHurufAwal1).append(strBelakang).toString();
                } else {
                    strDepan = strResult1.substring(0, intPos + 1);
                    strHurufAwal1 = (new StringBuilder()).append(strSource.charAt(intPos + 1)).toString();
                    strHurufAwal1 = strHurufAwal1.toUpperCase();
                    strBelakang = strResult1.substring(intPos + 2, strResult1.length());
                    strResult1 = (new StringBuilder(String.valueOf(strDepan))).append(strHurufAwal1).append(strBelakang).toString();
                }
            }

            strResult = strResult1;
        }
        strOutput = strResult;
        return strOutput;
    }

    public void fnPrint(String strMsg) {
        String strLogging = fnGetProperty("LOGGING").toString();
        if (strLogging.equalsIgnoreCase("true")) {
            System.out.println((new StringBuilder("[LOGGING] Message: ")).append(strMsg).toString());
        }
    }

    public static String fnGetProperty(String lStrProp) {
        FileInputStream is;
        Properties prop;
        is = null;
        prop = new Properties();
        try {
            is = new FileInputStream("/Simpada/simpada.properties");
            prop.load(is);
        } catch (FileNotFoundException e) {
            fnWriteLog("UTIL_LOGGER(fnGetProperty) : ", (new StringBuilder("Exception: ")).append(e).toString());
        } catch (IOException e) {
            fnWriteLog("UTIL_LOGGER(fnGetProperty) : ", (new StringBuilder("Exception: ")).append(e).toString());
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (Exception e) {
                fnWriteLog("UTIL_LOGGER(fnGetProperty) : ", (new StringBuilder("Close Exception: ")).append(e).toString());
            }
            return prop.getProperty(lStrProp);
        }
    }

    public static void fnWriteLog(String lStrClassName, String lStrMess) {
        FileWriter lMisLog = null;
        try {
            jvCommon lObjLog;
            lObjLog = new jvCommon();
            fnSetProperties(lObjLog);

            if (!lObjLog.lBlnLogging) {
                return;
            }

            String lStrFileName = (new StringBuilder("Simpada_")).append(fnGetDate()).toString();

            if (lStrFileName == null) {
                return;
            }

            lMisLog = new FileWriter((new StringBuilder(String.valueOf(lObjLog.lStrFilePath))).append("/").append(lStrFileName).toString(), true);
            String lStrTimeStamp = (new Date()).toString();
            String lStrMessage = (new StringBuilder("[")).append(lStrTimeStamp).append("]   ").append(lStrClassName).append("  : ").append(lStrMess).toString();
            char buffer[] = new char[lStrMessage.length()];
            lStrMessage.getChars(0, lStrMessage.length(), buffer, 0);
            lMisLog.write(buffer);
            lMisLog.write("\r\n");
        } catch (Exception e) {
            fnWriteLog("UTIL_LOGGER(fnWriteLog) : ", (new StringBuilder("Exception: ")).append(e).toString());
        } finally {
            try {
                if (lMisLog != null) {
                    lMisLog.close();
                    lMisLog = null;
                }
            } catch (Exception e) {
                fnWriteLog("UTIL_LOGGER(fnWriteLog) : ", (new StringBuilder("Exception: ")).append(e).toString());
            }
        }
    }

    protected static void fnSetProperties(jvCommon lObjLog) {
        FileInputStream is = null;
        try {
            is = new FileInputStream("/Simpada/simpada.properties");
            Properties prop = new Properties();
            prop.load(is);
            lObjLog.lStrFilePath = prop.getProperty("DIRECTORY");
            String lStrLog = prop.getProperty("LOGGING");
            if (lObjLog.lStrFilePath == null || lStrLog == null) {
                lObjLog.lBlnLogging = false;
            } else {
                lStrLog = lStrLog.trim();
                if (lStrLog.equalsIgnoreCase("TRUE")) {
                    lObjLog.lBlnLogging = true;
                } else {
                    lObjLog.lBlnLogging = false;
                }
            }
        } catch (Exception e) {
            fnWriteLog("UTIL_LOGGER(fnSetProperties) : ", (new StringBuilder("Exception: ")).append(e).toString());
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (Exception e) {
                fnWriteLog("UTIL_LOGGER(fnSetProperties) : ", (new StringBuilder("Exception: ")).append(e).toString());
            }
        }
    }

    private static String fnGetDate() {
        try {
            StringBuilder lSbrDate = new StringBuilder();
            int lIntDay = 0;
            int lIntMonth = 0;
            int lIntYear = 0;
            Calendar lMisCalc = Calendar.getInstance();
            lIntDay = lMisCalc.get(5);
            lIntYear = lMisCalc.get(1);
            lIntMonth = lMisCalc.get(2) + 1;
            lSbrDate.append(lIntYear);
            if (lIntMonth < 10) {
                lSbrDate.append("0").append(lIntMonth);
            } else {
                lSbrDate.append(lIntMonth);
            }
            if (lIntDay < 10) {
                lSbrDate.append("0").append(lIntDay);
            } else {
                lSbrDate.append(lIntDay);
            }
            lSbrDate.append(".log");
            return lSbrDate.toString();
        } catch (Exception e) {
            fnWriteLog("UTIL_LOGGER(fnGetDate) : ", (new StringBuilder("Exception: ")).append(e.getMessage()).toString());
        }
        return null;
    }

    public static int fnDbl2IntUP(double dblDouble) {
        int intReturn = 0;
        BigDecimal bdDouble = new BigDecimal(dblDouble);
        bdDouble = bdDouble.setScale(0, 0);
        String strBdDouble = bdDouble.toString();
        intReturn = Integer.parseInt(strBdDouble);
        return intReturn;
    }
    private String lStrFilePath;
    private boolean lBlnLogging;
}
