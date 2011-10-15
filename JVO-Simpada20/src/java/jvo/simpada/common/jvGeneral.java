/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jvo.simpada.common;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package common:
//            jvCommon, ifcSetting, ifcPenetapan, ifcPendaftaran

public class jvGeneral
{

    public jvGeneral()
    {
        DBurl = "jdbc:odbc:Simpada_v01";
        con = null;
        stmt = null;
        rs = null;
        rsmd = null;
        jvc = new jvCommon();
    }

    public Hashtable fnGetDfPerusahaan()
    {
        Hashtable htResult = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("select NamaBU, AlamatBU, iif(len(a.jnspekerjaan1) = 5,(select mJnsPekerjaan.JnsPekerjaan from mJnsPekerjaan where mJnsPekerjaan.KdNPWP = mid(a.NPWP,1,5)),a.JnsPekerjaan1) as JnsPekerjaan ");
        sqlQuery.append(", NamaPemilik, AlamatPemilik, modal, NPWP, TglAkhir, TglDaftar ");
        sqlQuery.append("from ( ");
        sqlQuery.append("SELECT distinct dataBU.Nama as NamaBU, dataBU.Jalan&dataBU.No AS AlamatBU ");
        sqlQuery.append(", IIf(isnull(mSKPD.JnsPekerjaan),mid(mSKPD.NPWPD,1,5),mSKPD.JnsPekerjaan) as JnsPekerjaan1 ");
        sqlQuery.append(", dataPemilik.Nama as NamaPemilik, dataPemilik.Jalan&dataPemilik.No AS AlamatPemilik, dataBU.modal ");
        sqlQuery.append(", dataBU.KdNPWP&dataBU.NPWPD&dataBU.WilNPWP AS NPWP, mSKPD.TglAkhir, dataBU.TglDaftar ");
        sqlQuery.append("FROM (dataPemilik INNER JOIN dataBU ON dataPemilik.KdPemilik = dataBU.KdPemilik) ");
        sqlQuery.append("INNER JOIN mSKPD ON dataBU.KdPemilik = mSKPD.KdPemilik ORDER BY dataBU.Nama ");
        sqlQuery.append(") a ");
        jvc.fnPrint((new StringBuilder("fnGetDfPerusahaan: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetRekapPajak(String tglAwal, String tglAkhir, String jnsPajak)
    {
        Hashtable htResult = new Hashtable();
        String strDtDate3 = tglAwal.substring(0, 2);
        String strDtMonth3 = tglAwal.substring(3, 5);
        String strDtYear3 = tglAwal.substring(6, tglAwal.length());
        tglAwal = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
        String strDtDate4 = tglAkhir.substring(0, 2);
        String strDtMonth4 = tglAkhir.substring(3, 5);
        String strDtYear4 = tglAkhir.substring(6, tglAkhir.length());
        tglAkhir = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT a.nama, a.nmPemilik, a.alamat, a.npwpd, iif(len(a.jnspekerjaan) = 5 ");
        sqlQuery.append(",(select mJnsPekerjaan.JnsPekerjaan from mJnsPekerjaan where mJnsPekerjaan.KdNPWP = mid(a.npwpd,1,5)),a.jnspekerjaan) AS JenisPekerjaan ");
        sqlQuery.append(", a.total1, b.total AS denda, a.tglawal, a.tglakhir, a.tglskpd, a.tgllunas ");
        sqlQuery.append(", mid(a.npwpd,1,5) as kdPajak ");
        sqlQuery.append("FROM ( ");
        sqlQuery.append("\t\tselect nama, nmPemilik, alamat, npwpd, jnspekerjaan, sum(total) as total1 ");
        sqlQuery.append("\t\t, tglawal, tglakhir, tglskpd, tgllunas ");
        sqlQuery.append("\t\tfrom qrekap_pajak_persh ");
        sqlQuery.append("\t\tgroup by nama, nmPemilik, alamat, npwpd, jnspekerjaan, tglawal, tglakhir, tglskpd, tgllunas, mid(npwpd,1,5) ");
        sqlQuery.append(") AS a LEFT JOIN qrekap_lain_persh AS b ON (a.tglskpd = b.tglskpd) AND (a.tglakhir = b.tglakhir) AND (a.tglawal = b.tglawal) AND (a.npwpd = b.npwpd) ");
        sqlQuery.append((new StringBuilder("WHERE a.tglskpd between DateValue('")).append(tglAwal).append("') and DateValue('").append(tglAkhir).append("') ").toString());
        sqlQuery.append((new StringBuilder("and mid(a.npwpd,1,5) = '")).append(jnsPajak).append("' ").toString());
        jvc.fnPrint((new StringBuilder("fnGetRekapPajak: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public int fnSimpanAkses(ifcSetting ifcs)
    {
        int intResult = 0;
        String txtKdJabatan = jvc.fnGetValue(ifcs.getTxtKdJabatan());
        String txt10000 = jvc.fnGetValue(ifcs.getTxt10000());
        String txt20000 = jvc.fnGetValue(ifcs.getTxt20000());
        String txt30000 = jvc.fnGetValue(ifcs.getTxt30000());
        String txt40000 = jvc.fnGetValue(ifcs.getTxt40000());
        String txt50000 = jvc.fnGetValue(ifcs.getTxt50000());
        String txt60000 = jvc.fnGetValue(ifcs.getTxt60000());
        StringBuffer sqlQuery = new StringBuffer();
        try
        {
            OpenConnection();
            sqlQuery.delete(0, sqlQuery.length());
            sqlQuery.append("DELETE FROM mAkses ");
            sqlQuery.append((new StringBuilder("WHERE mAkses.KdJabatan = '")).append(txtKdJabatan).append("' ").toString());
            jvc.fnPrint((new StringBuilder("fnSimpanAkses 1: ")).append(sqlQuery).toString());
            stmt = con.createStatement();
            stmt.executeUpdate(sqlQuery.toString());
            if(stmt != null)
            {
                stmt.close();
                stmt = null;
            }
            String strSql10000 = (new StringBuilder("INSERT INTO mAkses(KdJabatan, KdMenu) VALUES ('")).append(txtKdJabatan).append("', '10000')").toString();
            String strSql20000 = (new StringBuilder("INSERT INTO mAkses(KdJabatan, KdMenu) VALUES ('")).append(txtKdJabatan).append("', '20000')").toString();
            String strSql30000 = (new StringBuilder("INSERT INTO mAkses(KdJabatan, KdMenu) VALUES ('")).append(txtKdJabatan).append("', '30000')").toString();
            String strSql40000 = (new StringBuilder("INSERT INTO mAkses(KdJabatan, KdMenu) VALUES ('")).append(txtKdJabatan).append("', '40000')").toString();
            String strSql50000 = (new StringBuilder("INSERT INTO mAkses(KdJabatan, KdMenu) VALUES ('")).append(txtKdJabatan).append("', '50000')").toString();
            String strSql60000 = (new StringBuilder("INSERT INTO mAkses(KdJabatan, KdMenu) VALUES ('")).append(txtKdJabatan).append("', '60000')").toString();
            String strSql70000 = (new StringBuilder("INSERT INTO mAkses(KdJabatan, KdMenu) VALUES ('")).append(txtKdJabatan).append("', '70000')").toString();
            stmt = con.createStatement();
            if(txt10000.equalsIgnoreCase("on"))
            {
                stmt.addBatch(strSql10000);
                jvc.fnPrint((new StringBuilder("strSql10000: ")).append(strSql10000).toString());
            }
            if(txt20000.equalsIgnoreCase("on"))
            {
                stmt.addBatch(strSql20000);
                jvc.fnPrint((new StringBuilder("strSql20000: ")).append(strSql20000).toString());
            }
            if(txt30000.equalsIgnoreCase("on"))
            {
                stmt.addBatch(strSql30000);
                jvc.fnPrint((new StringBuilder("strSql30000: ")).append(strSql30000).toString());
            }
            if(txt40000.equalsIgnoreCase("on"))
            {
                stmt.addBatch(strSql40000);
                jvc.fnPrint((new StringBuilder("strSql40000: ")).append(strSql40000).toString());
            }
            if(txt50000.equalsIgnoreCase("on"))
            {
                stmt.addBatch(strSql50000);
                jvc.fnPrint((new StringBuilder("strSql50000: ")).append(strSql50000).toString());
            }
            if(txt60000.equalsIgnoreCase("on"))
            {
                stmt.addBatch(strSql60000);
                jvc.fnPrint((new StringBuilder("strSql60000: ")).append(strSql60000).toString());
            }
            stmt.addBatch(strSql70000);
            jvc.fnPrint((new StringBuilder("strSql70000: ")).append(strSql70000).toString());
            int intResultBatch[] = stmt.executeBatch();
            int intBatch = intResultBatch.length;
            for(int b = 0; b <= intBatch - 1; b++)
            {
                int intRslt = intResultBatch[b];
                if(intRslt >= 0)
                    continue;
                intResult = 0;
                break;
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public Hashtable fnGetMenu()
    {
        Hashtable htMenu = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.delete(0, sqlQuery.length());
        sqlQuery.append("SELECT mMenu.Menu ");
        sqlQuery.append("FROM mMenu ");
        sqlQuery.append("WHERE mMenu.Kode not in ('70000') ");
        jvc.fnPrint((new StringBuilder("fnGetMenu: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htMenu.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htMenu;
    }

    public Hashtable fnGetListAkses()
    {
        Hashtable htListAkses = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.delete(0, sqlQuery.length());
        sqlQuery.append("SELECT Kode, Keterangan, m10000, m20000, m30000, m40000, m50000, m60000 ");
        sqlQuery.append("FROM qHakAkses ");
        jvc.fnPrint((new StringBuilder("fnGetHakAkses: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htListAkses.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htListAkses;
    }

    public Hashtable fnGetSewaReklame(String strData[])
    {
        Hashtable htData = new Hashtable();
        String strKdWilayah = strData[0];
        String strJnsReklame = strData[1];
        String strUraian = strData[2];
        String strSatuan = strData[3];
        String strTempat = strData[4];
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.delete(0, sqlQuery.length());
        if(strJnsReklame.equalsIgnoreCase("0"))
        {
            if(strTempat.equalsIgnoreCase("1"))
            {
                sqlQuery.append("SELECT SewaJalan, FaktorKali ");
                sqlQuery.append("FROM mPajakReklame ");
                sqlQuery.append((new StringBuilder("WHERE Wilayah = '")).append(strKdWilayah).append("' ").toString());
                sqlQuery.append((new StringBuilder("AND KdSatuan = '")).append(strSatuan).append("' ").toString());
                sqlQuery.append((new StringBuilder("AND ")).append(strUraian).append(" BETWEEN LuasBawah AND LuasAtas ").toString());
            } else
            {
                sqlQuery.append("SELECT SewaToko, FaktorKali ");
                sqlQuery.append("FROM mPajakReklame ");
                sqlQuery.append((new StringBuilder("WHERE Wilayah = '")).append(strKdWilayah).append("' ").toString());
                sqlQuery.append((new StringBuilder("AND KdSatuan = '")).append(strSatuan).append("' ").toString());
                sqlQuery.append((new StringBuilder("AND ")).append(strUraian).append(" BETWEEN LuasBawah AND LuasAtas ").toString());
            }
        } else
        if(strTempat.equalsIgnoreCase("1"))
        {
            sqlQuery.append("SELECT SewaJalan, FaktorKali ");
            sqlQuery.append("FROM mPajakReklame1 ");
            sqlQuery.append((new StringBuilder("WHERE Wilayah = '")).append(strKdWilayah).append("' ").toString());
            sqlQuery.append((new StringBuilder("AND KdSatuan = '")).append(strSatuan).append("' ").toString());
            sqlQuery.append((new StringBuilder("AND KdJenis = '")).append(strJnsReklame).append("' ").toString());
        } else
        {
            sqlQuery.append("SELECT SewaToko, FaktorKali ");
            sqlQuery.append("FROM mPajakReklame1 ");
            sqlQuery.append((new StringBuilder("WHERE Wilayah = '")).append(strKdWilayah).append("' ").toString());
            sqlQuery.append((new StringBuilder("AND KdSatuan = '")).append(strSatuan).append("' ").toString());
            sqlQuery.append((new StringBuilder("AND KdJenis = '")).append(strJnsReklame).append("' ").toString());
        }
        jvc.fnPrint((new StringBuilder("fnGetSewaReklame: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            String strFaktorKali;
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next(); htData.put("FaktorKali", strFaktorKali))
            {
                String strSewa = rs.getString(1);
                strFaktorKali = rs.getString(2);
                htData.put("Sewa", strSewa);
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htData;
    }

    public int fnSimpanBank(ifcSetting ifcs)
    {
        int intResult = 0;
        String txtKodeBank = jvc.fnGetValue(ifcs.getTxtUkeyKary());
        String txtNamaBank = jvc.fnGetValue(ifcs.getTxtLogin());
        String txtNoRekBank = jvc.fnGetValue(ifcs.getTxtPassword());
        StringBuffer sqlQuery = new StringBuffer();
        try
        {
            OpenConnection();
            sqlQuery.delete(0, sqlQuery.length());
            sqlQuery.append("UPDATE mBank SET ");
            sqlQuery.append((new StringBuilder("NamaBank = '")).append(txtNamaBank).append("', ").toString());
            sqlQuery.append((new StringBuilder("NoRekening = '")).append(txtNoRekBank).append("' ").toString());
            sqlQuery.append((new StringBuilder("WHERE Kode = '")).append(txtKodeBank).append("' ").toString());
            jvc.fnPrint((new StringBuilder("fnSimpanBank: ")).append(sqlQuery).toString());
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public Hashtable fnGetInfoBank(String strKode)
    {
        Hashtable htBidUsaha = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.delete(0, sqlQuery.length());
        sqlQuery.append("SELECT mBank.NamaBank, mBank.NoRekening, mBank.Kode, mBank.Status ");
        sqlQuery.append("FROM mBank ");
        sqlQuery.append("WHERE Status = '1' ");
        if(strKode.trim().length() > 0)
            sqlQuery.append((new StringBuilder("AND Kode = '")).append(strKode).append("' ").toString());
        jvc.fnPrint((new StringBuilder("fnGetInfoBank: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htBidUsaha.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htBidUsaha;
    }

    public int fnHapusLogin(ifcSetting ifcs)
    {
        int intResult = 0;
        String uKeyKary = jvc.fnGetValue(ifcs.getTxtUkeyKary());
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.delete(0, sqlQuery.length());
        sqlQuery.append("DELETE * FROM dataLogin ");
        sqlQuery.append((new StringBuilder("WHERE FUkey = ")).append(uKeyKary).append(" ").toString());
        jvc.fnPrint((new StringBuilder("fnHapusLogin: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnSimpanLogin(ifcSetting ifcs)
    {
        int intResult = 0;
        String uKeyKary = jvc.fnGetValue(ifcs.getTxtUkeyKary());
        String txtLogin = jvc.fnGetValue(ifcs.getTxtLogin());
        String txtPassword = jvc.fnGetValue(ifcs.getTxtPassword());
        StringBuffer sqlQuery = new StringBuffer();
        try
        {
            OpenConnection();
            sqlQuery.delete(0, sqlQuery.length());
            sqlQuery.append("SELECT count(*) as jumlah ");
            sqlQuery.append("FROM dataLogin ");
            sqlQuery.append((new StringBuilder("WHERE FUkey = ")).append(uKeyKary).append(" ").toString());
            stmt = con.createStatement();
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next();)
                intResult = rs.getInt(1);

            if(intResult == 0)
            {
                sqlQuery.delete(0, sqlQuery.length());
                sqlQuery.append("INSERT INTO dataLogin(LoginId, Password, FUkey) ");
                sqlQuery.append((new StringBuilder("VALUES ('")).append(txtLogin).append("', '").append(txtPassword).append("', ").append(uKeyKary).append(") ").toString());
            } else
            {
                sqlQuery.delete(0, sqlQuery.length());
                sqlQuery.append("UPDATE dataLogin SET ");
                sqlQuery.append((new StringBuilder("LoginId = '")).append(txtLogin).append("', ").toString());
                sqlQuery.append((new StringBuilder("Password = '")).append(txtPassword).append("' ").toString());
                sqlQuery.append((new StringBuilder("WHERE FUkey = ")).append(uKeyKary).append(") ").toString());
            }
            jvc.fnPrint((new StringBuilder("fnSimpanLogin: ")).append(sqlQuery).toString());
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnTambahKaryawan(ifcSetting ifcs)
    {
        int intResult = 0;
        String txtNamaKary = jvc.fnGetValue(ifcs.getTxtNamaKary());
        String txtJabatanKary = jvc.fnGetValue(ifcs.getTxtJabatanKary());
        String txtNipKary = jvc.fnGetValue(ifcs.getTxtNipKary());
        String txtAlamatKary = jvc.fnGetValue(ifcs.getTxtAlamatKary());
        String txtNoKary = jvc.fnGetValue(ifcs.getTxtNoKary());
        String txtRTKary = jvc.fnGetValue(ifcs.getTxtRTKary());
        String txtRWKary = jvc.fnGetValue(ifcs.getTxtRWKary());
        String txtRKKary = jvc.fnGetValue(ifcs.getTxtRKKary());
        String txtKelurahanKary = jvc.fnGetValue(ifcs.getTxtKelurahanKary());
        String txtKecamatanKary = jvc.fnGetValue(ifcs.getTxtKecamatanKary());
        String txtKotamadyaKary = jvc.fnGetValue(ifcs.getTxtKotamadyaKary());
        String txtKodePosKary = jvc.fnGetValue(ifcs.getTxtKodePosKary());
        String txtTeleponKary = jvc.fnGetValue(ifcs.getTxtTeleponKary());
        String txtFacsimileKary = jvc.fnGetValue(ifcs.getTxtFacsimileKary());
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("INSERT INTO mPegawai (Nama, Jabatan, NIP, Alamat, [No], RT, RW, ");
        sqlQuery.append("RK, Kelurahan, Kecamatan, Kabupaten, KodePos, Telepon, ");
        sqlQuery.append("Facs) ");
        sqlQuery.append((new StringBuilder("VALUES( '")).append(txtNamaKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtJabatanKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtNipKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtAlamatKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtNoKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtRTKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtRWKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtRKKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtKelurahanKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtKecamatanKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtKotamadyaKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtKodePosKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtTeleponKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtFacsimileKary).append("') ").toString());
        jvc.fnPrint((new StringBuilder("fnTambahKaryawan: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnDeleteKaryawan(String strUkeyKary)
    {
        int intResult = 0;
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("DELETE * ");
        sqlQuery.append("FROM mPegawai ");
        sqlQuery.append((new StringBuilder("WHERE Ukey = ")).append(strUkeyKary).append(" ").toString());
        jvc.fnPrint((new StringBuilder("fnDeleteKaryawan: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnUpdateKaryawan(ifcSetting ifcs)
    {
        int intResult = 0;
        String uKeyKary = jvc.fnGetValue(ifcs.getTxtUkeyKary());
        String txtNamaKary = jvc.fnGetValue(ifcs.getTxtNamaKary());
        String txtJabatanKary = jvc.fnGetValue(ifcs.getTxtJabatanKary());
        String txtNipKary = jvc.fnGetValue(ifcs.getTxtNipKary());
        String txtAlamatKary = jvc.fnGetValue(ifcs.getTxtAlamatKary());
        String txtNoKary = jvc.fnGetValue(ifcs.getTxtNoKary());
        String txtRTKary = jvc.fnGetValue(ifcs.getTxtRTKary());
        String txtRWKary = jvc.fnGetValue(ifcs.getTxtRWKary());
        String txtRKKary = jvc.fnGetValue(ifcs.getTxtRKKary());
        String txtKelurahanKary = jvc.fnGetValue(ifcs.getTxtKelurahanKary());
        String txtKecamatanKary = jvc.fnGetValue(ifcs.getTxtKecamatanKary());
        String txtKotamadyaKary = jvc.fnGetValue(ifcs.getTxtKotamadyaKary());
        String txtKodePosKary = jvc.fnGetValue(ifcs.getTxtKodePosKary());
        String txtTeleponKary = jvc.fnGetValue(ifcs.getTxtTeleponKary());
        String txtFacsimileKary = jvc.fnGetValue(ifcs.getTxtFacsimileKary());
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("UPDATE mPegawai SET ");
        sqlQuery.append((new StringBuilder("Nama = '")).append(txtNamaKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Jabatan = '")).append(txtJabatanKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Nip = '")).append(txtNipKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Alamat = '")).append(txtAlamatKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("[No] = '")).append(txtNoKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("RT = '")).append(txtRTKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("RW = '")).append(txtRWKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("RK = '")).append(txtRKKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Kelurahan = '")).append(txtKelurahanKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Kecamatan = '")).append(txtKecamatanKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Kabupaten = '")).append(txtKotamadyaKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("KodePos = '")).append(txtKodePosKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Telepon = '")).append(txtTeleponKary).append("', ").toString());
        sqlQuery.append((new StringBuilder("Facs = '")).append(txtFacsimileKary).append("' ").toString());
        sqlQuery.append((new StringBuilder("WHERE Ukey = ")).append(uKeyKary).append(" ").toString());
        jvc.fnPrint((new StringBuilder("fnUpdateKary: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnUpdatePemda(ifcSetting ifcs)
    {
        int intResult = 0;
        String uKeyPemda = jvc.fnGetValue(ifcs.getTxtUkeyPemda());
        String txtNamaPemda = jvc.fnGetValue(ifcs.getTxtNamaPemda());
        String txtNamaBidang = jvc.fnGetValue(ifcs.getTxtNamaBidang());
        String txtAlamatPemda = jvc.fnGetValue(ifcs.getTxtAlamatPemda());
        String txtNoPemda = jvc.fnGetValue(ifcs.getTxtNoPemda());
        String txtRTPemda = jvc.fnGetValue(ifcs.getTxtRTPemda());
        String txtRWPemda = jvc.fnGetValue(ifcs.getTxtRWPemda());
        String txtRKPemda = jvc.fnGetValue(ifcs.getTxtRKPemda());
        String txtKelurahanPemda = jvc.fnGetValue(ifcs.getTxtKelurahanPemda());
        String txtKecamatanPemda = jvc.fnGetValue(ifcs.getTxtKecamatanPemda());
        String txtKotamadyaPemda = jvc.fnGetValue(ifcs.getTxtKotamadyaPemda());
        String txtKodePosPemda = jvc.fnGetValue(ifcs.getTxtKodePosPemda());
        String txtTeleponPemda = jvc.fnGetValue(ifcs.getTxtTeleponPemda());
        String txtFacsimilePemda = jvc.fnGetValue(ifcs.getTxtFacsimilePemda());
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("UPDATE mPemerintah SET ");
        sqlQuery.append((new StringBuilder("Daerah = '")).append(txtNamaPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("Bidang = '")).append(txtNamaBidang).append("', ").toString());
        sqlQuery.append((new StringBuilder("Alamat = '")).append(txtAlamatPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("[No] = '")).append(txtNoPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("RT = '")).append(txtRTPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("RW = '")).append(txtRWPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("RK = '")).append(txtRKPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("Kelurahan = '")).append(txtKelurahanPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("Kecamatan = '")).append(txtKecamatanPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("Kabupaten = '")).append(txtKotamadyaPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("KodePos = '")).append(txtKodePosPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("Telepon = '")).append(txtTeleponPemda).append("', ").toString());
        sqlQuery.append((new StringBuilder("Facs = '")).append(txtFacsimilePemda).append("' ").toString());
        sqlQuery.append((new StringBuilder("WHERE Ukey = ")).append(uKeyPemda).append(" ").toString());
        jvc.fnPrint((new StringBuilder("fnUpdatePemda: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public Hashtable fnGetSPJFungsional()
    {
        Hashtable htResult = new Hashtable();
        Calendar cal = new GregorianCalendar();
        int intDayOfMonth = cal.get(5);
        int intDayOfWeek = cal.get(7);
        int intMonth = cal.get(2);
        int intYear = cal.get(1);
        Calendar cal2 = new GregorianCalendar(intYear, intMonth, 1);
        int intMaxDay2 = cal2.getActualMaximum(5);
        String strDate1 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append("01").append("/").append(intYear).toString();
        String strDate2 = (new StringBuilder(String.valueOf(jvc.fnLRPad("LPAD", String.valueOf(intMonth + 1), "0", 2)))).append("/").append(intMaxDay2).append("/").append(intYear).toString();
        int intBulanLalu = intMonth - 1;
        if(intBulanLalu < 0)
        {
            intBulanLalu = 11;
            intYear--;
        }
        String strDate1Y = (new StringBuilder("01/01/")).append(intYear).toString();
        Calendar cal3 = new GregorianCalendar(intYear, intBulanLalu, 1);
        int intDayOfLastMonth = cal3.getActualMaximum(5);
        String strDate2Y = (new StringBuilder(String.valueOf(intDayOfLastMonth))).append("/").append(jvc.fnLRPad("LPAD", String.valueOf(intBulanLalu + 1), "0", 2)).append("/").append(intYear).toString();
        jvc.fnPrint((new StringBuilder("strDate1: ")).append(strDate1).toString());
        jvc.fnPrint((new StringBuilder("strDate2: ")).append(strDate2).toString());
        jvc.fnPrint((new StringBuilder("strDate1Y: ")).append(strDate1Y).toString());
        jvc.fnPrint((new StringBuilder("strDate2Y: ")).append(strDate2Y).toString());
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT spjLengkap.*, spjBulanan.Penerimaan as Penerimaan1, spjBulanan.Penyetoran as Penyetoran1 ");
        sqlQuery.append(", spjBulanan.Sisa as Sisa1 ");
        sqlQuery.append(", (spjLengkap.Penerimaan + spjBulanan.Penerimaan) as Realisasi ");
        sqlQuery.append(", (spjLengkap.Penyetoran + spjBulanan.Penyetoran) as Disetor ");
        sqlQuery.append(", (Realisasi - Penyetoran) as [Sisa Blm Setor] ");
        sqlQuery.append(", (spjLengkap.[Jumlah Anggaran] - [Realisasi]) as [Pelampauan Anggaran] ");
        sqlQuery.append(", iif((spjLengkap.[Jumlah Anggaran]>0),(([Realisasi]/spjLengkap.[Jumlah Anggaran])*100),0) AS Persentase ");
        sqlQuery.append("FROM ");
        sqlQuery.append("( ");
        sqlQuery.append("SELECT spjAnggaran.[Kode Rekening], spjAnggaran.[Uraian], spjAnggaran.[Jumlah Anggaran] ");
        sqlQuery.append(", spjSampaiBulan.Penerimaan, spjSampaiBulan.Penyetoran, spjSampaiBulan.Sisa ");
        sqlQuery.append("FROM ");
        sqlQuery.append("( ");
        sqlQuery.append("SELECT mRekening.Kode1 & mRekening.Kode2 & mRekening.Kode3 & mRekening.Kode4 & mRekening.Kode5 AS [Kode Rekening] ");
        sqlQuery.append(", mRekening.Keterangan AS Uraian, mAnggaran.Jumlah AS [Jumlah Anggaran] ");
        sqlQuery.append("FROM mAnggaran, mRekening ");
        sqlQuery.append("WHERE mAnggaran.KdRekening=mRekening.Kode1 & mRekening.Kode2 & mRekening.Kode3 & mRekening.Kode4 & mRekening.Kode5 ");
        sqlQuery.append((new StringBuilder("AND mAnggaran.Tahun = '")).append(String.valueOf(cal.get(1))).append("' ").toString());
        sqlQuery.append(") as spjAnggaran ");
        sqlQuery.append("LEFT JOIN ");
        sqlQuery.append("( ");
        sqlQuery.append("SELECT spjTerima.KdRekening, iif(isnull(spjTerima.Penerimaan),0,spjTerima.Penerimaan) AS Penerimaan ");
        sqlQuery.append(", iif(isnull(spjSetor.JmlSetor),0,spjSetor.JmlSetor) AS Penyetoran, (Penerimaan - Penyetoran) AS Sisa ");
        sqlQuery.append("FROM [SELECT mSKPD.KdRekening, sum(mSKPD.Total) as Penerimaan ");
        sqlQuery.append("FROM mSKPD ");
        sqlQuery.append((new StringBuilder("WHERE mSKPD.TglSKPD BETWEEN DateValue('")).append(strDate1Y).append("') AND DateValue('").append(strDate2Y).append("') ").toString());
        sqlQuery.append("GROUP BY mSKPD.KdRekening ");
        sqlQuery.append("]. AS spjTerima LEFT JOIN [SELECT mSTS.KdRekening, sum(mSTS.Jumlah) as JmlSetor ");
        sqlQuery.append("FROM mSTS ");
        sqlQuery.append((new StringBuilder("WHERE mSTS.TanggalSetor BETWEEN DateValue('")).append(strDate1Y).append("') AND DateValue('").append(strDate2Y).append("') ").toString());
        sqlQuery.append("GROUP BY mSTS.KdRekening ");
        sqlQuery.append("]. AS spjSetor ON spjTerima.KdRekening = spjSetor.KdRekening ");
        sqlQuery.append(") as spjSampaiBulan ON spjAnggaran.[Kode Rekening] = spjSampaiBulan.KdRekening ");
        sqlQuery.append(") as spjLengkap ");
        sqlQuery.append("LEFT JOIN ");
        sqlQuery.append("( ");
        sqlQuery.append("SELECT spjTerima.TglAwal, spjTerima.TglAkhir, spjTerima.KdRekening ");
        sqlQuery.append(", iif(isnull(spjTerima.Penerimaan),0,spjTerima.Penerimaan) as Penerimaan ");
        sqlQuery.append(", iif(isnull(spjSetor.JmlSetor),0,spjSetor.JmlSetor) as Penyetoran ");
        sqlQuery.append(", (Penerimaan - Penyetoran) as Sisa ");
        sqlQuery.append("FROM [SELECT mSKPD.TglAwal, mSKPD.TglAkhir, mSKPD.KdRekening, sum(mSKPD.Total) as Penerimaan ");
        sqlQuery.append("FROM mSKPD ");
        sqlQuery.append((new StringBuilder("WHERE mSKPD.TglAwal = DateValue('")).append(strDate1).append("') ").toString());
        sqlQuery.append((new StringBuilder("AND mSKPD.TglAkhir = DateValue('")).append(strDate2).append("') ").toString());
        sqlQuery.append("GROUP BY mSKPD.TglAwal, mSKPD.TglAkhir, mSKPD.KdRekening ");
        sqlQuery.append("]. AS spjTerima LEFT JOIN [SELECT mSTS.KdRekening, sum(mSTS.Jumlah) as JmlSetor ");
        sqlQuery.append("FROM mSTS ");
        sqlQuery.append((new StringBuilder("WHERE mSTS.TanggalSetor BETWEEN DateValue('")).append(strDate1).append("') AND DateValue('").append(strDate2).append("') ").toString());
        sqlQuery.append("GROUP BY mSTS.KdRekening ");
        sqlQuery.append("]. AS spjSetor ON spjTerima.KdRekening = spjSetor.KdRekening ");
        sqlQuery.append(") as spjBulanan ");
        sqlQuery.append("ON spjLengkap.[Kode Rekening] = spjBulanan.KdRekening ");
        jvc.fnPrint((new StringBuilder("fnGetSPJFungsional: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetBukuKasPembantu(String tglAwal, String tglAkhir, String thnAnggaran)
    {
        Hashtable htResult = new Hashtable();
        String strDtDate3 = tglAwal.substring(0, 2);
        String strDtMonth3 = tglAwal.substring(3, 5);
        String strDtYear3 = tglAwal.substring(6, tglAwal.length());
        tglAwal = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
        String strDtDate4 = tglAkhir.substring(0, 2);
        String strDtMonth4 = tglAkhir.substring(3, 5);
        String strDtYear4 = tglAkhir.substring(6, tglAkhir.length());
        tglAkhir = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT mSKPD.KdRekening, mRekening.Keterangan, mSKPD.TglSKPD, sum(Total) as nJumlah, mAnggaran.Jumlah ");
        sqlQuery.append("FROM mSKPD, mRekening, mAnggaran ");
        sqlQuery.append("WHERE mSKPD.KdRekening = mRekening.[Kode1]&mRekening.[Kode2]&mRekening.[Kode3]&mRekening.[Kode4]&mRekening.[Kode5] AND mSKPD.KdRekening = mAnggaran.KdRekening ");
        sqlQuery.append((new StringBuilder("AND mSKPD.TglSKPD BETWEEN DateValue('")).append(tglAwal).append("') AND DateValue('").append(tglAkhir).append("') ").toString());
        sqlQuery.append((new StringBuilder("AND mAnggaran.Tahun = '")).append(thnAnggaran).append("' ").toString());
        sqlQuery.append("GROUP BY mSKPD.KdRekening, mRekening.Keterangan, mSKPD.TglSKPD, mAnggaran.Jumlah ");
        jvc.fnPrint((new StringBuilder("fnGetBukuKasPembantu: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetLapHarian(String tglAwal)
    {
        Hashtable htResult = new Hashtable();
        String strDtDate3 = tglAwal.substring(0, 2);
        String strDtMonth3 = tglAwal.substring(3, 5);
        String strDtYear3 = tglAwal.substring(6, tglAwal.length());
        tglAwal = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT PAJAK.TglSKPD, iif(isnull(PAJAK.NoRekPajak),'',PAJAK.NoRekPajak) as NoRekPajak ");
        sqlQuery.append(", PAJAK.Keterangan as KetPajak, iif(isnull(PAJAK.nTotal),0,PAJAK.nTotal) as TotPajak ");
        sqlQuery.append(", RETRIBUSI.TglSKPD, iif(isnull(RETRIBUSI.NoRekRetribusi),'',RETRIBUSI.NoRekRetribusi) as NoRekRetribusi ");
        sqlQuery.append(", RETRIBUSI.Keterangan as KetRetribusi, iif(isnull(RETRIBUSI.nTotal),0,RETRIBUSI.nTotal) as TotRetribusi ");
        sqlQuery.append(", PADLAIN.TglSKPD, iif(isnull(PADLAIN.NoRekPADLain),'',PADLAIN.NoRekPADLain) as NoRekPADLain ");
        sqlQuery.append(", PADLAIN.Keterangan as KetPADLain, iif(isnull(PADLAIN.nTotal),0,PADLAIN.nTotal) as TotPADLain ");
        sqlQuery.append("FROM ( ");
        sqlQuery.append("( ");
        sqlQuery.append("SELECT qTerbayar.TglSKPD, qNoRekPajak.KdPajak, qNoRekPajak.NoRekPajak, qNoRekPajak.Keterangan, qTerbayar.nTotal ");
        sqlQuery.append("FROM qNoRekPajak LEFT JOIN (SELECT TglSKPD, KdRekening, Uraian, sum(Total) as nTotal ");
        sqlQuery.append((new StringBuilder("FROM mSKPD WHERE TglSKPD = DateValue('")).append(tglAwal).append("') ").toString());
        sqlQuery.append("GROUP BY TglSKPD, KdRekening, Uraian) AS qTerbayar ON ");
        sqlQuery.append("qNoRekPajak.NoRekPajak=qTerbayar.KdRekening ");
        sqlQuery.append(") AS PAJAK ");
        sqlQuery.append("LEFT JOIN ");
        sqlQuery.append("( ");
        sqlQuery.append("SELECT qTerbayar.TglSKPD, qNoRekRetribusi.KdRetribusi, qNoRekRetribusi.NoRekRetribusi, qNoRekRetribusi.Keterangan, qTerbayar.nTotal ");
        sqlQuery.append("FROM qNoRekRetribusi LEFT JOIN (SELECT TglSKPD, KdRekening, Uraian, sum(Total) as nTotal ");
        sqlQuery.append((new StringBuilder("FROM mSKPD WHERE TglSKPD = DateValue('")).append(tglAwal).append("') ").toString());
        sqlQuery.append("GROUP BY TglSKPD, KdRekening, Uraian) AS qTerbayar ON ");
        sqlQuery.append("qNoRekRetribusi.NoRekRetribusi = qTerbayar.KdRekening ");
        sqlQuery.append(") AS RETRIBUSI ");
        sqlQuery.append("ON PAJAK.KdPajak = RETRIBUSI.KdRetribusi ");
        sqlQuery.append(") ");
        sqlQuery.append("LEFT JOIN ");
        sqlQuery.append("( ");
        sqlQuery.append("SELECT qTerbayar.TglSKPD, qNoRekPADLain.KdLain, qNoRekPADLain.NoRekPADLain, qNoRekPADLain.Keterangan, qTerbayar.nTotal ");
        sqlQuery.append("FROM qNoRekPADLain LEFT JOIN (SELECT TglSKPD, KdRekening, Uraian, sum(Total) as nTotal ");
        sqlQuery.append((new StringBuilder("FROM mSKPD WHERE TglSKPD = DateValue('")).append(tglAwal).append("') ").toString());
        sqlQuery.append("GROUP BY TglSKPD, KdRekening, Uraian) AS qTerbayar ON ");
        sqlQuery.append("qNoRekPADLain.NoRekPADLain = qTerbayar.KdRekening ");
        sqlQuery.append(") AS PADLAIN ");
        sqlQuery.append("ON  PAJAK.KdPajak = PADLAIN.KdLain ");
        jvc.fnPrint((new StringBuilder("fnGetLapHarian: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public int fnHapusAnggaran(String strTahun)
    {
        int intResult = 0;
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("DELETE * ");
        sqlQuery.append("FROM mAnggaran ");
        sqlQuery.append((new StringBuilder("WHERE Tahun = '")).append(strTahun).append("' ").toString());
        jvc.fnPrint((new StringBuilder("fnHapusAnggaran: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnSimpanAnggaran(Hashtable htData)
    {
        int intResult = 1;
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            int intHtData = htData.size();
            for(int a = 1; a <= intHtData; a++)
            {
                String strData[] = (String[])htData.get(String.valueOf(a));
                String strThnAnggaran = strData[0];
                String strKdRekening = strData[1];
                String strJumlah = strData[2];
                String strNIPBendahara = strData[3];
                String strNIPPejabat = strData[4];
                String strTglTerima = strData[5];
                String strDtDate4 = strTglTerima.substring(0, 2);
                String strDtMonth4 = strTglTerima.substring(3, 5);
                String strDtYear4 = strTglTerima.substring(6, strTglTerima.length());
                strTglTerima = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
                StringBuffer sqlQuery = new StringBuffer();
                sqlQuery.delete(0, sqlQuery.length());
                sqlQuery.append("INSERT INTO mAnggaran (Tahun, KdRekening, Jumlah, NIPPenerima, NIPPejabat, TglTerima) ");
                sqlQuery.append((new StringBuilder("VALUES ('")).append(strThnAnggaran).append("', '").append(strKdRekening).append("' ").toString());
                sqlQuery.append((new StringBuilder(", ")).append(strJumlah).append(", '").append(strNIPBendahara).append("' ").toString());
                sqlQuery.append((new StringBuilder(", '")).append(strNIPPejabat).append("', '").append(strTglTerima).append("') ").toString());
                jvc.fnPrint((new StringBuilder("sqlQuery: ")).append(sqlQuery.toString()).toString());
                stmt.addBatch(sqlQuery.toString());
            }

            int intResultBatch[] = stmt.executeBatch();
            int intBatch = intResultBatch.length;
            for(int b = 0; b <= intBatch - 1; b++)
            {
                int intRslt = intResultBatch[b];
                if(intRslt >= 0)
                    continue;
                intResult = 0;
                break;
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public Hashtable fnGetRincianAnggaran(String strTahun)
    {
        Hashtable htResult = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT Tahun, KdRekening, Jumlah, TglTerima ");
        sqlQuery.append("FROM mAnggaran ");
        sqlQuery.append((new StringBuilder("WHERE Tahun = '")).append(strTahun).append("' ").toString());
        sqlQuery.append("ORDER BY KdRekening ");
        jvc.fnPrint((new StringBuilder("fnGetRincianAnggaran: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

                jvc.fnPrint((new StringBuilder("strArray[1]: ")).append(strArray[2]).toString());
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        jvc.fnPrint((new StringBuilder("fnGetRincianAnggaran: ")).append(htResult.size()).toString());
        return htResult;
    }

    public Hashtable fnGetNoRekRetribusi()
    {
        Hashtable htResult = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT [Kode1] & [Kode2] & [Kode3] & [Kode4] & [Kode5] AS NoRekRetribusi, KdRetribusi, mRetribusi.Keterangan ");
        sqlQuery.append("FROM mRekening, mRetribusi ");
        sqlQuery.append("WHERE mRekening.KdRetribusi=mRetribusi.Kode ");
        sqlQuery.append("ORDER BY [Kode1] & [Kode2] & [Kode3] & [Kode4] & [Kode5] ");
        jvc.fnPrint((new StringBuilder("fnGetNoRekRetribusi: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = jvc.fnGetValue(rs.getString(a + 1));

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public int fnSaveSTS(Hashtable htSTS)
    {
        int intResult = 1;
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            int intHtSTS = htSTS.size();
            for(int a = 1; a <= intHtSTS; a++)
            {
                String strValidArray[] = (String[])htSTS.get(String.valueOf(a));
                String strTglSTS = strValidArray[0];
                String strDtDate4 = strTglSTS.substring(0, 2);
                String strDtMonth4 = strTglSTS.substring(3, 5);
                String strDtYear4 = strTglSTS.substring(6, strTglSTS.length());
                strTglSTS = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
                String strKdRekening = strValidArray[1];
                String strJumlah = strValidArray[2];
                String NAMA_BANK = strValidArray[3];
                String NO_REK = strValidArray[4];
                String strNoSTS = strValidArray[5];
                StringBuffer sqlQuery = new StringBuffer();
                sqlQuery.delete(0, sqlQuery.length());
                sqlQuery.append("INSERT INTO mSTS (TanggalSetor, KdRekening, Jumlah, NamaBank, NoRekening, NoSTS) ");
                sqlQuery.append((new StringBuilder("VALUES ('")).append(strTglSTS).append("', '").append(strKdRekening).append("', ").append(strJumlah).append(" ").toString());
                sqlQuery.append((new StringBuilder(", '")).append(NAMA_BANK).append("', '").append(NO_REK).append("', '").append(strNoSTS).append("') ").toString());
                jvc.fnPrint((new StringBuilder("fnSaveSTS: ")).append(sqlQuery.toString()).toString());
                stmt.addBatch(sqlQuery.toString());
            }

            int intResultBatch[] = stmt.executeBatch();
            int intBatch = intResultBatch.length;
            for(int b = 0; b <= intBatch - 1; b++)
            {
                int intRslt = intResultBatch[b];
                if(intRslt >= 0)
                    continue;
                intResult = 0;
                break;
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public Hashtable fnGetSTS(String strTglSTS)
    {
        Hashtable htResult = new Hashtable();
        String strDtDate4 = strTglSTS.substring(0, 2);
        String strDtMonth4 = strTglSTS.substring(3, 5);
        String strDtYear4 = strTglSTS.substring(6, strTglSTS.length());
        strTglSTS = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("select KodeRekening, UraianRincianObyek, ListTerbayar.Jumlah from (SELECT mRekening.[Kode1]& mRekening.[Kode2]& mRekening.[Kode3]& mRekening.[Kode4]& mRekening.[Kode5] as [KodeRekening] ");
        sqlQuery.append(",  mRekening.[Keterangan] as [UraianRincianObyek] ");
        sqlQuery.append("FROM mRekening) as ListRekening ");
        sqlQuery.append("LEFT JOIN ");
        sqlQuery.append("(select mKodeRek.KodeRekening, mKodeRek.UraianRincianObyek, sum(mSKPD.Total) as Jumlah ");
        sqlQuery.append("from mSKPD, ");
        sqlQuery.append("(SELECT mRekening.[Kode1]& mRekening.[Kode2]& mRekening.[Kode3]& mRekening.[Kode4]& mRekening.[Kode5] as [KodeRekening] ");
        sqlQuery.append(",  mRekening.[Keterangan] as [UraianRincianObyek] ");
        sqlQuery.append("FROM mRekening) as mKodeRek ");
        sqlQuery.append("where mSKPD.KdRekening = mKodeRek.KodeRekening ");
        sqlQuery.append((new StringBuilder("and mSKPD.TglSKPD = DateValue('")).append(strTglSTS).append("') ").toString());
        sqlQuery.append("group by mKodeRek.KodeRekening, mKodeRek.UraianRincianObyek) as ListTerbayar ");
        sqlQuery.append("ON ListRekening.KodeRekening = ListTerbayar.KodeRekening ");
        jvc.fnPrint((new StringBuilder("fnGetSTS: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = jvc.fnGetValue(rs.getString(a + 1));

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetPajakC()
    {
        Hashtable htPajakC = new Hashtable();
        String sqlQuery = "SELECT JnsBahan, SatuanUkuran, HargaDasar, FaktorKali FROM mPajakC";
        jvc.fnPrint((new StringBuilder("fnGetPajakC: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htPajakC.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htPajakC;
    }

    public Hashtable fnGetInfoPejabat()
    {
        Hashtable htInfoPejabat = new Hashtable();
        String sqlQuery = "SELECT Kode, Nama, NIP, Keterangan, HakTTD FROM mJabatan INNER JOIN mPegawai ON mJabatan.Kode=mPegawai.Jabatan";
        jvc.fnPrint((new StringBuilder("fnGetInfoPejabat: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htInfoPejabat.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htInfoPejabat;
    }

    public Hashtable fnGetBukuKasUmum(String tglAwal, String strTglAkhir)
    {
        Hashtable htResult = new Hashtable();
        String strDtDate3 = tglAwal.substring(0, 2);
        String strDtMonth3 = tglAwal.substring(3, 5);
        String strDtYear3 = tglAwal.substring(6, tglAwal.length());
        tglAwal = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
        String strDtDate4 = strTglAkhir.substring(0, 2);
        String strDtMonth4 = strTglAkhir.substring(3, 5);
        String strDtYear4 = strTglAkhir.substring(6, strTglAkhir.length());
        strTglAkhir = (new StringBuilder(String.valueOf(strDtMonth4))).append("/").append(strDtDate4).append("/").append(strDtYear4).toString();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("select mSKPD.TglSKPD, mSKPD.KdRekening, Uraian, sum(Penerimaan), sum(Pengeluaran) from ( ");
        sqlQuery.append("SELECT mSKPD.TglSKPD, mSKPD.KdRekening, dataBU.Nama as Uraian, mSKPD.Total as Penerimaan, mSKPD.Total as Pengeluaran ");
        sqlQuery.append("FROM (dataBU INNER JOIN mSKPD ON dataBU.KdPemilik = mSKPD.KdPemilik) INNER JOIN mPajak ON dataBU.KdNPWP = mPajak.KodeNPWP&'.' ");
        sqlQuery.append("order by mSKPD.TglSKPD, mSKPD.KdRekening ");
        sqlQuery.append("UNION ALL SELECT mSKPD.TglSKPD, mSKPD.KdRekening, mSKPD.Uraian,'0' as Penerimaan,'0' as Pengeluaran ");
        sqlQuery.append("FROM mSKPD ");
        sqlQuery.append("ORDER BY mSKPD.TglSKPD, mSKPD.KdRekening ");
        sqlQuery.append((new StringBuilder(") WHERE mSKPD.TglSKPD BETWEEN DateValue('")).append(tglAwal).append("') AND DateValue('").append(strTglAkhir).append("') ").toString());
        sqlQuery.append("group by mSKPD.TglSKPD, mSKPD.KdRekening, Uraian ");
        sqlQuery.append("order by mSKPD.TglSKPD, mSKPD.KdRekening, sum(Penerimaan), sum(Pengeluaran) ");
        jvc.fnPrint((new StringBuilder("fnGetBukuKasUmum: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetRincianBiaya(String strTglSTS)
    {
        Hashtable htResult = new Hashtable();
        String strDtDate3 = strTglSTS.substring(0, 2);
        String strDtMonth3 = strTglSTS.substring(3, 5);
        String strDtYear3 = strTglSTS.substring(6, strTglSTS.length());
        strTglSTS = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT TglSKPD,  KdRekening, SUM(Total) as Jumlah ");
        sqlQuery.append("FROM mSKPD ");
        sqlQuery.append((new StringBuilder("WHERE TglSKPD = DateValue('")).append(strTglSTS).append("') ").toString());
        sqlQuery.append("GROUP BY TglSKPD, KdRekening ");
        jvc.fnPrint((new StringBuilder("fnGetRincianBiaya: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetRincian()
    {
        Hashtable htResult = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT [Kode1]&[Kode2]&[Kode3]&[Kode4]&[Kode5] as [Kode Rekening] ");
        sqlQuery.append(", [Keterangan] as [Uraian Rincian Obyek] ");
        sqlQuery.append("FROM mRekening ");
        jvc.fnPrint((new StringBuilder("fnGetRincian: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htResult.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetSKPD(String strNoSKPD, String strKdPemilik, String strNoNPWPD, String strNoNPWRD)
    {
        Hashtable htResult = new Hashtable();
        String Nama = "";
        String Jalan = "";
        String No = "";
        String RT = "";
        String RW = "";
        String RK = "";
        String KodePos = "";
        String Pemilik = "";
        String Kabupaten = "";
        String Kecamatan = "";
        String Kelurahan = "";
        String NoNPWP = "";
        String NoNPWR = "";
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT dataBU.Nama, dataBU.Jalan, dataBU.[No], dataBU.RT, dataBU.RW, dataBU.RK, dataBU.KodePos ");
        sqlQuery.append(", dataPemilik.Nama as Pemilik, mKabupaten.Keterangan as Kabupaten ");
        sqlQuery.append(", (select mKecamatan.Keterangan from mKecamatan where mKecamatan.KdKabupaten = dataBU.Kabupaten and mKecamatan.Kode = mid(dataBU.Kecamatan,4,2)) as Kecamatan ");
        sqlQuery.append(", (select mKelurahan.Keterangan from mKelurahan where mKelurahan.KdKabupaten = dataBU.Kabupaten and mKelurahan.KdKecamatan = mid(dataBU.Kecamatan,4,2) ");
        sqlQuery.append("and mKelurahan.Kode = mid(dataBU.Kelurahan,7,2)) as Kelurahan, dataBU.KdNPWP&dataBU.NPWPD&dataBU.WilNPWP as NoNPWP, dataBU.KdNPWR&dataBU.NPWRD&dataBU.WilNPWR as NoNPWR ");
        sqlQuery.append("FROM dataPemilik, dataBU, mKabupaten ");
        sqlQuery.append("WHERE 1 = 1 ");
        sqlQuery.append("and  dataPemilik.KdPemilik = dataBU.KdPemilik ");
        sqlQuery.append("and  dataBU.Kabupaten = mKabupaten.Kode ");
        sqlQuery.append((new StringBuilder("and  dataPemilik.KdPemilik = '")).append(strKdPemilik).append("' ").toString());
        sqlQuery.append((new StringBuilder("and  (([dataBU.KdNPWP]&[dataBU.NPWPD]&[dataBU.WilNPWP] = '")).append(strNoNPWPD).append("') ").toString());
        sqlQuery.append((new StringBuilder("      or ([dataBU.KdNPWR]&[dataBU.NPWRD]&[dataBU.WilNPWR] = '")).append(strNoNPWRD).append("')) ").toString());
        jvc.fnPrint((new StringBuilder("fnGetCounter 1: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next(); htResult.put("Kelurahan", Kelurahan))
            {
                Nama = jvc.fnGetValue(rs.getString(1));
                htResult.put("Nama", Nama);
                Jalan = jvc.fnGetValue(rs.getString(2));
                htResult.put("Jalan", Jalan);
                No = jvc.fnGetValue(rs.getString(3));
                htResult.put("No", No);
                RT = jvc.fnGetValue(rs.getString(4));
                htResult.put("RT", RT);
                RW = jvc.fnGetValue(rs.getString(5));
                htResult.put("RW", RW);
                KodePos = jvc.fnGetValue(rs.getString(7));
                htResult.put("KodePos", KodePos);
                Pemilik = jvc.fnGetValue(rs.getString(8));
                htResult.put("Pemilik", Pemilik);
                Kabupaten = jvc.fnGetValue(rs.getString(9));
                htResult.put("Kabupaten", Kabupaten);
                Kecamatan = jvc.fnGetValue(rs.getString(10));
                htResult.put("Kecamatan", Kecamatan);
                Kelurahan = jvc.fnGetValue(rs.getString(11));
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public Hashtable fnGetSKPD0(String strNoSKPD, String strKdPemilik, String strNoNPWPD, String strNoNPWRD)
    {
        Hashtable htResult = new Hashtable();
        String Nama = "";
        String Jalan = "";
        String No = "";
        String RT = "";
        String RW = "";
        String RK = "";
        String KodePos = "";
        String Pemilik = "";
        String Kabupaten = "";
        String Kecamatan = "";
        String Kelurahan = "";
        String NoNPWP = "";
        String NoNPWR = "";
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT dataBU.Nama, dataBU.Jalan, dataBU.[No], dataBU.RT, dataBU.RW, dataBU.RK, dataBU.KodePos ");
        sqlQuery.append(", dataPemilik.Nama as Pemilik, mKabupaten.Keterangan as Kabupaten ");
        sqlQuery.append(", (select mKecamatan.Keterangan from mKecamatan where mKecamatan.KdKabupaten = dataBU.Kabupaten and mKecamatan.Kode = mid(dataBU.Kecamatan,4,2)) as Kecamatan ");
        sqlQuery.append(", (select mKelurahan.Keterangan from mKelurahan where mKelurahan.KdKabupaten = dataBU.Kabupaten and mKelurahan.KdKecamatan = mid(dataBU.Kecamatan,4,2) ");
        sqlQuery.append("and mKelurahan.Kode = mid(dataBU.Kelurahan,7,2)) as Kelurahan, dataBU.KdNPWP&dataBU.NPWPD&dataBU.WilNPWP as NoNPWP, dataBU.KdNPWR&dataBU.NPWRD&dataBU.WilNPWR as NoNPWR ");
        sqlQuery.append("FROM dataPemilik, dataBU, mKabupaten ");
        sqlQuery.append("WHERE 1 = 1 ");
        sqlQuery.append("and  dataPemilik.KdPemilik = dataBU.KdPemilik ");
        sqlQuery.append("and  dataBU.Kabupaten = mKabupaten.Kode ");
        sqlQuery.append((new StringBuilder("and  dataPemilik.KdPemilik = '")).append(strKdPemilik).append("' ").toString());
        sqlQuery.append((new StringBuilder("and  (([dataBU.KdNPWP]&[dataBU.NPWPD]&[dataBU.WilNPWP] = '")).append(strNoNPWPD).append("') ").toString());
        sqlQuery.append((new StringBuilder("      or ([dataBU.KdNPWR]&[dataBU.NPWRD]&[dataBU.WilNPWR] = '")).append(strNoNPWRD).append("')) ").toString());
        jvc.fnPrint((new StringBuilder("fnGetSKPD0 1: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next(); htResult.put("Kelurahan", Kelurahan))
            {
                Nama = jvc.fnGetValue(rs.getString(1));
                htResult.put("Nama", Nama);
                Jalan = jvc.fnGetValue(rs.getString(2));
                htResult.put("Jalan", Jalan);
                No = jvc.fnGetValue(rs.getString(3));
                htResult.put("No", No);
                RT = jvc.fnGetValue(rs.getString(4));
                htResult.put("RT", RT);
                RW = jvc.fnGetValue(rs.getString(5));
                htResult.put("RW", RW);
                KodePos = jvc.fnGetValue(rs.getString(7));
                htResult.put("KodePos", KodePos);
                Pemilik = jvc.fnGetValue(rs.getString(8));
                htResult.put("Pemilik", Pemilik);
                Kabupaten = jvc.fnGetValue(rs.getString(9));
                htResult.put("Kabupaten", Kabupaten);
                Kecamatan = jvc.fnGetValue(rs.getString(10));
                htResult.put("Kecamatan", Kecamatan);
                Kelurahan = jvc.fnGetValue(rs.getString(11));
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htResult;
    }

    public int fnSaveDetilSKPD(String strNoSKPD, String strNoNPWPD, Hashtable htUraian)
    {
        int intResult = 1;
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            int intHtUraian = htUraian.size();
            for(int a = 1; a <= intHtUraian; a++)
            {
                String strArray[] = (String[])htUraian.get(String.valueOf(a));
                String strDetailUraian = strArray[0];
                String strRpUraian = strArray[1];
                StringBuffer sqlQuery = new StringBuffer();
                sqlQuery.delete(0, sqlQuery.length());
                sqlQuery.append("INSERT INTO mDetilSKPD (NoSKPD, NPWPD, Uraian, Rp) ");
                sqlQuery.append((new StringBuilder("VALUES ('")).append(strNoSKPD).append("', '").append(strNoNPWPD).append("' ").toString());
                sqlQuery.append((new StringBuilder(", '")).append(strDetailUraian).append("', '").append(strRpUraian).append("') ").toString());
                stmt.addBatch(sqlQuery.toString());
            }

            int intResultBatch[] = stmt.executeBatch();
            int intBatch = intResultBatch.length;
            for(int b = 0; b <= intBatch - 1; b++)
            {
                int intRslt = intResultBatch[b];
                if(intRslt >= 0)
                    continue;
                intResult = 0;
                break;
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnSaveDetilSKPD0(String strNoSKPD, String strNoNPWPD, Hashtable htUraian)
    {
        int intResult = 1;
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            int intHtUraian = htUraian.size();
            for(int a = 1; a <= intHtUraian; a++)
            {
                String strArray[] = (String[])htUraian.get(String.valueOf(a));
                String strDetailUraian = strArray[0];
                String strRpUraian = strArray[1];
                StringBuffer sqlQuery = new StringBuffer();
                sqlQuery.delete(0, sqlQuery.length());
                sqlQuery.append("INSERT INTO mDetilSKPD0 (NoSKPD, NPWPD, Uraian, Rp) ");
                sqlQuery.append((new StringBuilder("VALUES ('")).append(strNoSKPD).append("', '").append(strNoNPWPD).append("' ").toString());
                sqlQuery.append((new StringBuilder(", '")).append(strDetailUraian).append("', '").append(strRpUraian).append("') ").toString());
                stmt.addBatch(sqlQuery.toString());
            }

            int intResultBatch[] = stmt.executeBatch();
            int intBatch = intResultBatch.length;
            for(int b = 0; b <= intBatch - 1; b++)
            {
                int intRslt = intResultBatch[b];
                if(intRslt >= 0)
                    continue;
                intResult = 0;
                break;
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnSaveSKPD(ifcPenetapan ifcp)
    {
        int intResult = 0;
        String strBunga = jvc.fnGetValue(ifcp.getStrBunga());
        String strDate1 = jvc.fnGetValue(ifcp.getStrDate1());
        String strDtDate1 = strDate1.substring(0, 2);
        String strDtMonth1 = strDate1.substring(3, 5);
        String strDtYear1 = strDate1.substring(6, strDate1.length());
        strDate1 = (new StringBuilder(String.valueOf(strDtMonth1))).append("/").append(strDtDate1).append("/").append(strDtYear1).toString();
        String strDate2 = jvc.fnGetValue(ifcp.getStrDate2());
        String strDtDate2 = strDate2.substring(0, 2);
        String strDtMonth2 = strDate2.substring(3, 5);
        String strDtYear2 = strDate2.substring(6, strDate2.length());
        strDate2 = (new StringBuilder(String.valueOf(strDtMonth2))).append("/").append(strDtDate2).append("/").append(strDtYear2).toString();
        String strJumlah = jvc.fnGetValue(ifcp.getStrJumlah());
        String strKdPemilik = jvc.fnGetValue(ifcp.getStrKdPemilik());
        String strKenaikan = jvc.fnGetValue(ifcp.getStrKenaikan());
        String strNoNPWPD = jvc.fnGetValue(ifcp.getStrNoNPWPD());
        String strNoRek = jvc.fnGetValue(ifcp.getStrNoRek());
        String strNoSKPD = jvc.fnGetValue(ifcp.getStrNoSKPD());
        String strTahun = jvc.fnGetValue(ifcp.getStrTahun());
        String strJnsPekerjaan = jvc.fnGetValue(ifcp.getStrJnsPekerjaan());
        String strTglSKPD = jvc.fnGetValue(ifcp.getStrTglSKPD());
        String strDtDate3 = strTglSKPD.substring(0, 2);
        String strDtMonth3 = strTglSKPD.substring(3, 5);
        String strDtYear3 = strTglSKPD.substring(6, strTglSKPD.length());
        strTglSKPD = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
        String strTotal = jvc.fnGetValue(ifcp.getStrTotal());
        String strUraian = jvc.fnGetValue(ifcp.getStrUraian());
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.delete(0, sqlQuery.length());
        sqlQuery.append("INSERT INTO mSKPD (NoSKPD, TglAwal, TglAkhir, Tahun, KdPemilik ");
        sqlQuery.append(", KdRekening, Uraian, Jumlah, Bunga, Kenaikan ");
        sqlQuery.append(", Total, TglSKPD, NPWPD, JnsPekerjaan) ");
        sqlQuery.append((new StringBuilder("VALUES ('")).append(strNoSKPD).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strDate1).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strDate2).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strTahun).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strKdPemilik).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strNoRek).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strUraian).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strJumlah).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strBunga).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strKenaikan).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strTotal).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strTglSKPD).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strNoNPWPD).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strJnsPekerjaan).append("') ").toString());
        jvc.fnPrint((new StringBuilder("fnSaveSKPD : ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public int fnSaveSKPD0(ifcPenetapan ifcp)
    {
        int intResult = 0;
        String strBunga = jvc.fnGetValue(ifcp.getStrBunga());
        String strDate1 = jvc.fnGetValue(ifcp.getStrDate1());
        String strDtDate1 = strDate1.substring(0, 2);
        String strDtMonth1 = strDate1.substring(3, 5);
        String strDtYear1 = strDate1.substring(6, strDate1.length());
        strDate1 = (new StringBuilder(String.valueOf(strDtMonth1))).append("/").append(strDtDate1).append("/").append(strDtYear1).toString();
        String strDate2 = jvc.fnGetValue(ifcp.getStrDate2());
        String strDtDate2 = strDate2.substring(0, 2);
        String strDtMonth2 = strDate2.substring(3, 5);
        String strDtYear2 = strDate2.substring(6, strDate2.length());
        strDate2 = (new StringBuilder(String.valueOf(strDtMonth2))).append("/").append(strDtDate2).append("/").append(strDtYear2).toString();
        String strJumlah = jvc.fnGetValue(ifcp.getStrJumlah());
        String strKdPemilik = jvc.fnGetValue(ifcp.getStrKdPemilik());
        String strKenaikan = jvc.fnGetValue(ifcp.getStrKenaikan());
        String strNoNPWPD = jvc.fnGetValue(ifcp.getStrNoNPWPD());
        String strNoRek = jvc.fnGetValue(ifcp.getStrNoRek());
        String strNoSKPD = jvc.fnGetValue(ifcp.getStrNoSKPD());
        String strTahun = jvc.fnGetValue(ifcp.getStrTahun());
        String strJnsPekerjaan = jvc.fnGetValue(ifcp.getStrJnsPekerjaan());
        String strTglSKPD = jvc.fnGetValue(ifcp.getStrTglSKPD());
        String strDtDate3 = strTglSKPD.substring(0, 2);
        String strDtMonth3 = strTglSKPD.substring(3, 5);
        String strDtYear3 = strTglSKPD.substring(6, strTglSKPD.length());
        strTglSKPD = (new StringBuilder(String.valueOf(strDtMonth3))).append("/").append(strDtDate3).append("/").append(strDtYear3).toString();
        String strTotal = jvc.fnGetValue(ifcp.getStrTotal());
        String strUraian = jvc.fnGetValue(ifcp.getStrUraian());
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.delete(0, sqlQuery.length());
        sqlQuery.append("INSERT INTO mSKPD0 (NoSKPD, TglAwal, TglAkhir, Tahun, KdPemilik ");
        sqlQuery.append(", KdRekening, Uraian, Jumlah, Bunga, Kenaikan ");
        sqlQuery.append(", Total, TglSKPD, NPWPD, JnsPekerjaan) ");
        sqlQuery.append((new StringBuilder("VALUES ('")).append(strNoSKPD).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strDate1).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strDate2).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strTahun).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strKdPemilik).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strNoRek).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strUraian).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strJumlah).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strBunga).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strKenaikan).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strTotal).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strTglSKPD).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strNoNPWPD).append("' ").toString());
        sqlQuery.append((new StringBuilder(", '")).append(strJnsPekerjaan).append("') ").toString());
        jvc.fnPrint((new StringBuilder("fnSaveSKPD0 : ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            intResult = stmt.executeUpdate(sqlQuery.toString());
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return intResult;
    }

    public String fnGetCounter(String strKode, String strNoRek, String strBulan, String strTahun, String strSimpan)
    {
        String strResult = "";
        int intNoUrut = 0;
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT iif(isnull(max(mCounter.NoUrut)),0,max(mCounter.NoUrut)) as no_urut ");
        sqlQuery.append("FROM mCounter ");
        sqlQuery.append("WHERE 1 = 1 ");
        sqlQuery.append((new StringBuilder("AND Kode = '")).append(strKode).append("' ").toString());
        sqlQuery.append((new StringBuilder("AND Bulan = '")).append(strBulan).append("' ").toString());
        sqlQuery.append((new StringBuilder("AND Tahun = '")).append(strTahun).append("' ").toString());
        sqlQuery.append((new StringBuilder("AND KdRekening = '")).append(strNoRek).append("' ").toString());
        jvc.fnPrint((new StringBuilder("fnGetCounter 1: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next();)
                strResult = rs.getString(1);

            CloseConnection();
            if(strResult.equalsIgnoreCase("0"))
            {
                intNoUrut = 1;
                if(strSimpan.equals("1"))
                {
                    sqlQuery.delete(0, sqlQuery.length());
                    sqlQuery.append("INSERT INTO mCounter (NoUrut, Bulan, Tahun, Kode, KdRekening) ");
                    sqlQuery.append((new StringBuilder("VALUES ('")).append(String.valueOf(intNoUrut)).append("' ").toString());
                    sqlQuery.append((new StringBuilder(", '")).append(strBulan).append("' ").toString());
                    sqlQuery.append((new StringBuilder(", '")).append(strTahun).append("' ").toString());
                    sqlQuery.append((new StringBuilder(", '")).append(strKode).append("' ").toString());
                    sqlQuery.append((new StringBuilder(", '")).append(strNoRek).append("') ").toString());
                    jvc.fnPrint((new StringBuilder("fnGetCounter 2: ")).append(sqlQuery.toString()).toString());
                    OpenConnection();
                    stmt = con.createStatement();
                    int intResult = stmt.executeUpdate(sqlQuery.toString());
                    CloseConnection();
                }
            } else
            {
                intNoUrut = Integer.parseInt(strResult) + 1;
                if(strSimpan.equals("1"))
                {
                    sqlQuery.delete(0, sqlQuery.length());
                    sqlQuery.append("UPDATE mCounter ");
                    sqlQuery.append((new StringBuilder("SET NoUrut = '")).append(String.valueOf(intNoUrut)).append("' ").toString());
                    sqlQuery.append((new StringBuilder("WHERE Bulan = '")).append(strBulan).append("' ").toString());
                    sqlQuery.append((new StringBuilder("and Tahun = '")).append(strTahun).append("' ").toString());
                    sqlQuery.append((new StringBuilder("and Kode = '")).append(strKode).append("' ").toString());
                    sqlQuery.append((new StringBuilder("and KdRekening = '")).append(strNoRek).append("' ").toString());
                    jvc.fnPrint((new StringBuilder("fnGetCounter 3: ")).append(sqlQuery.toString()).toString());
                    OpenConnection();
                    stmt = con.createStatement();
                    stmt.executeUpdate(sqlQuery.toString());
                    CloseConnection();
                }
            }
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return String.valueOf(intNoUrut);
    }

    public Hashtable fnGetNoRek()
    {
        Hashtable htNoRek = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT [Kode1]&[Kode2]&[Kode3]&[Kode4]&[Kode5] as noRek, KdPajak, Keterangan, 'PAJAK' as  kode ");
        sqlQuery.append("FROM mRekening where KdPajak is not null and KdRetribusi is null and KdLain is null ");
        sqlQuery.append("union all ");
        sqlQuery.append("SELECT [Kode1]&[Kode2]&[Kode3]&[Kode4]&[Kode5] as noRek, KdRetribusi, Keterangan, 'RETRIBUSI' as kode ");
        sqlQuery.append("FROM mRekening where KdRetribusi is not null and KdPajak is null and KdLain is null ");
        sqlQuery.append("union all ");
        sqlQuery.append("SELECT [Kode1]&[Kode2]&[Kode3]&[Kode4]&[Kode5] as noRek, KdLain, Keterangan, 'PADLain' as kode ");
        sqlQuery.append("FROM mRekening where KdLain is not null and KdPajak is null and KdRetribusi is null ");
        jvc.fnPrint((new StringBuilder("fnGetNoRek: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htNoRek.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htNoRek;
    }

    public String fnHapusDetail(String strKdPemilik)
    {
        String strResult = "0";
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("DELETE FROM dataPemilik ");
        sqlQuery.append((new StringBuilder("WHERE kdPemilik = '")).append(strKdPemilik).append("' ").toString());
        jvc.fnPrint((new StringBuilder("fnHapusDetail: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            int intResult = stmt.executeUpdate(sqlQuery.toString());
            strResult = String.valueOf(intResult);
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return strResult;
    }

    private ifcPendaftaran fnGetIFCP(String strNPWP, String strNamaBU, String strNamaPemilik)
    {
        ifcPendaftaran ifcp = new ifcPendaftaran();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("select a.NoForm, a.Nama, a.Jalan, a.[No], a.RT, ");
        sqlQuery.append("a.RW, a.RK, a.Kelurahan, a.Kecamatan, a.Kabupaten, ");
        sqlQuery.append("a.Kotamadya, a.KodePos, a.Telepon, a.NPWPD, a.NPWRD, ");
        sqlQuery.append("a.KdPemilik, a.KdBidUsaha, a.KdNPWP, a.WilNPWP, a.KdNPWR, a.WilNPWR, a.Modal ");
        sqlQuery.append(", b.Nama as NamaP, b.Jalan as JalanP, b.[No] as NoP, ");
        sqlQuery.append("b.RT as RTP, b.RW as RWP, b.RK as RKP, b.Kelurahan as KelurahanP, ");
        sqlQuery.append("b.Kecamatan as KecamatanP, b.Kabupaten as KabupatenP, b.Kotamadya as KotamadyaP, ");
        sqlQuery.append("b.KodePos as KodePosP, b.Telepon as TeleponP, b.Jabatan, b.KdPajak, b.KdRetribusi ");
        sqlQuery.append("from dataBU a, dataPemilik b ");
        sqlQuery.append("where a.kdPemilik = b.kdPemilik ");
        sqlQuery.append((new StringBuilder("and (([a.KdNPWP]&[a.NPWPD]&[a.WilNPWP] = '")).append(strNPWP).append("') ").toString());
        sqlQuery.append((new StringBuilder("or (a.Nama = '")).append(strNamaBU).append("' ").toString());
        sqlQuery.append((new StringBuilder("and b.Nama = '")).append(strNamaPemilik).append("')) ").toString());
        jvc.fnPrint((new StringBuilder("fnGetIFCP: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            String hidRetribusi;
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next(); ifcp.setStrRetribusi(hidRetribusi))
            {
                String txtNoForm = rs.getString("NoForm");
                String txtNamaBU = rs.getString("Nama");
                String txtJalanBU = rs.getString("Jalan");
                String txtNoBU = rs.getString("No");
                String txtRTBU = rs.getString("RT");
                String txtRWBU = rs.getString("RW");
                String txtRKBU = rs.getString("RK");
                String txtTelpBU = rs.getString("Telepon");
                String txtKdPosBU = rs.getString("KodePos");
                String hidKelurahanBU = rs.getString("Kelurahan");
                String hidKecamatanBU = rs.getString("Kecamatan");
                String hidKabupatenBU = rs.getString("Kabupaten");
                String txtNPWPD = rs.getString("NPWPD");
                String txtNPWRD = rs.getString("NPWRD");
                String hidBidUsaha = rs.getString("KdBidUsaha");
                String kdPemilik = rs.getString("KdPemilik");
                String txtKdNPWP = rs.getString("KdNPWP");
                String txtWilNPWP = rs.getString("WilNPWP");
                String txtKdNPWR = rs.getString("KdNPWR");
                String txtWilNPWR = rs.getString("WilNPWR");
                String txtModal = rs.getString("Modal");
                ifcp.setStrNoForm(txtNoForm);
                ifcp.setStrNamaBU(txtNamaBU);
                ifcp.setStrJalanBU(txtJalanBU);
                ifcp.setStrNoBU(txtNoBU);
                ifcp.setStrRTBU(txtRTBU);
                ifcp.setStrRWBU(txtRWBU);
                ifcp.setStrRKBU(txtRKBU);
                ifcp.setStrTelpBU(txtTelpBU);
                ifcp.setStrKdPosBU(txtKdPosBU);
                ifcp.setStrKelurahanBU(hidKelurahanBU);
                ifcp.setStrKecamatanBU(hidKecamatanBU);
                ifcp.setStrKabupatenBU(hidKabupatenBU);
                ifcp.setStrNPWPD(txtNPWPD);
                ifcp.setStrNPWRD(txtNPWRD);
                ifcp.setStrBidUsaha(hidBidUsaha);
                ifcp.setStrKodePemilik(kdPemilik);
                ifcp.setStrKdNPWP(txtKdNPWP);
                ifcp.setStrKdNPWR(txtKdNPWR);
                ifcp.setStrWilNPWP(txtWilNPWP);
                ifcp.setStrWilNPWR(txtWilNPWR);
                ifcp.setStrModal(txtModal);
                String txtNama = rs.getString("NamaP");
                String txtJalan = rs.getString("JalanP");
                String txtNo = rs.getString("NoP");
                String txtRT = rs.getString("RTP");
                String txtRW = rs.getString("RWP");
                String txtRK = rs.getString("RKP");
                String txtTelp = rs.getString("TeleponP");
                String txtKdPos = rs.getString("KodePosP");
                String hidKelurahan = rs.getString("KelurahanP");
                String hidKecamatan = rs.getString("KecamatanP");
                String hidKabupaten = rs.getString("KabupatenP");
                String txtJabatan = rs.getString("Jabatan");
                String hidPajak = rs.getString("KdPajak");
                hidRetribusi = rs.getString("KdRetribusi");
                ifcp.setStrNama(txtNama);
                ifcp.setStrJalan(txtJalan);
                ifcp.setStrNo(txtNo);
                ifcp.setStrRT(txtRT);
                ifcp.setStrRW(txtRW);
                ifcp.setStrRK(txtRK);
                ifcp.setStrTelp(txtTelp);
                ifcp.setStrKdPos(txtKdPos);
                ifcp.setStrKelurahan(hidKelurahan);
                ifcp.setStrKecamatan(hidKecamatan);
                ifcp.setStrKabupaten(hidKabupaten);
                ifcp.setStrJabatan(txtJabatan);
                ifcp.setStrPajak(hidPajak);
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return ifcp;
    }

    private Hashtable fnGetSI(String strKdPemilik)
    {
        Hashtable htSI = new Hashtable();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("select JenisSI, NoSI, TglSI ");
        sqlQuery.append("from dataSI ");
        sqlQuery.append((new StringBuilder("where KdPemilik = '")).append(strKdPemilik).append("' ").toString());
        jvc.fnPrint((new StringBuilder("fnGetSI: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery.toString());
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htSI.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htSI;
    }

    public Hashtable fnGetDetail(String strNPWP, String strNamaBU, String strNamaPemilik)
    {
        Hashtable htResult = new Hashtable();
        ifcPendaftaran ifcp = new ifcPendaftaran();
        ifcp = fnGetIFCP(strNPWP, strNamaBU, strNamaPemilik);
        String strKdPemilik = "";
        if(ifcp.getStrKodePemilik() != null)
            strKdPemilik = ifcp.getStrKodePemilik();
        Hashtable htSI = new Hashtable();
        if(strKdPemilik.trim().length() > 0)
            htSI = fnGetSI(strKdPemilik);
        htResult.put("ifcp", ifcp);
        htResult.put("htSI", htSI);
        return htResult;
    }

    private String fnCreateCode()
    {
        String strResult = "";
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyh24mmss");
        java.util.Date todayDate = new java.util.Date();
        String strToday = sdf.format(todayDate);
        strResult = strToday;
        return strResult;
    }

    public String fnSimpanPemilik(ifcPendaftaran ifcp)
    {
        String strResult = "0";
        String txtNama = jvc.fnGetValue(ifcp.getStrNama());
        String txtJabatan = jvc.fnGetValue(ifcp.getStrJabatan());
        String txtJalan = jvc.fnGetValue(ifcp.getStrJalan());
        String txtNo = jvc.fnGetValue(ifcp.getStrNo());
        String txtRT = jvc.fnGetValue(ifcp.getStrRT());
        String txtRW = jvc.fnGetValue(ifcp.getStrRW());
        String txtRK = jvc.fnGetValue(ifcp.getStrRK());
        String txtTelp = jvc.fnGetValue(ifcp.getStrTelp());
        String txtKdPos = jvc.fnGetValue(ifcp.getStrKdPos());
        String hidKelurahan = jvc.fnGetValue(ifcp.getStrKelurahan());
        String hidKecamatan = jvc.fnGetValue(ifcp.getStrKecamatan());
        String hidKabupaten = jvc.fnGetValue(ifcp.getStrKabupaten());
        String hidPajak = jvc.fnGetValue(ifcp.getStrPajak());
        String hidRetribusi = jvc.fnGetValue(ifcp.getStrRetribusi());
        String strKdPemilik = fnCreateCode();
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("insert into dataPemilik (Nama, Jalan, [No], ");
        sqlQuery.append("RT, RW, RK, Kelurahan, ");
        sqlQuery.append("Kecamatan, Kabupaten, Kotamadya, KodePos, ");
        sqlQuery.append("Telepon, KdPemilik, Jabatan, KdPajak, KdRetribusi) ");
        sqlQuery.append((new StringBuilder("values ('")).append(txtNama).append("', '").append(txtJalan).append("', '").append(txtNo).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtRT).append("', '").append(txtRW).append("', '").append(txtRK).append("', '").append(hidKelurahan).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(hidKecamatan).append("', '").append(hidKabupaten).append("', '").append(hidKabupaten).append("', '").append(txtKdPos).append("', ").toString());
        sqlQuery.append((new StringBuilder("'")).append(txtTelp).append("', '").append(strKdPemilik).append("', '").append(txtJabatan).append("', '").append(hidPajak).append("', '").append(hidRetribusi).append("') ").toString());
        jvc.fnPrint((new StringBuilder("fnSimpanPemilik: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            int intResult = stmt.executeUpdate(sqlQuery.toString());
            if(intResult > 0)
                strResult = strKdPemilik;
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return strResult;
    }

    public String fnAmbilNoForm()
    {
        String strResult = "0000";
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("Select NoForm from dataBU ");
        sqlQuery.append("where Ukey = (select max(ukey) from dataBU) ");
        jvc.fnPrint((new StringBuilder("fnAmbilNoForm: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next();)
            {
                String strNoForm = rs.getString(1);
                strResult = strNoForm.substring(strNoForm.length() - 4, strNoForm.length());
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return strResult;
    }

    public String fnSimpanBU(ifcPendaftaran ifcp, String strKodePemilik)
    {
        String strResult = "0";
        String txtNoForm = jvc.fnGetValue(ifcp.getStrNoForm());
        String txtNamaBU = jvc.fnGetValue(ifcp.getStrNamaBU());
        String txtJalanBU = jvc.fnGetValue(ifcp.getStrJalanBU());
        String txtNoBU = jvc.fnGetValue(ifcp.getStrNoBU());
        String txtRTBU = jvc.fnGetValue(ifcp.getStrRTBU());
        String txtRWBU = jvc.fnGetValue(ifcp.getStrRWBU());
        String txtRKBU = jvc.fnGetValue(ifcp.getStrRKBU());
        String txtTelpBU = jvc.fnGetValue(ifcp.getStrTelpBU());
        String txtKdPosBU = jvc.fnGetValue(ifcp.getStrKdPosBU());
        String hidKelurahanBU = jvc.fnGetValue(ifcp.getStrKelurahanBU());
        String hidKecamatanBU = jvc.fnGetValue(ifcp.getStrKecamatanBU());
        String hidKabupatenBU = jvc.fnGetValue(ifcp.getStrKabupatenBU());
        String txtKdNPWP = jvc.fnGetValue(ifcp.getStrKdNPWP());
        String txtNPWPD = jvc.fnGetValue(ifcp.getStrNPWPD());
        String txtWilNPWP = jvc.fnGetValue(ifcp.getStrWilNPWP());
        String txtKdNPWR = jvc.fnGetValue(ifcp.getStrKdNPWR());
        String txtNPWRD = jvc.fnGetValue(ifcp.getStrNPWRD());
        String txtWilNPWR = jvc.fnGetValue(ifcp.getStrWilNPWR());
        String hidBidUsaha = jvc.fnGetValue(ifcp.getStrBidUsaha());
        String txtModal = jvc.fnGetValue(ifcp.getStrModal());
        StringBuffer sqlQuery = new StringBuffer();
        String strCekNPWP = (new StringBuilder(String.valueOf(txtKdNPWP))).append(txtNPWPD).append(txtWilNPWP).toString();
        sqlQuery.delete(0, sqlQuery.length());
        sqlQuery.append("SELECT count(*) as cek ");
        sqlQuery.append("FROM dataBU ");
        sqlQuery.append((new StringBuilder("WHERE dataBU.KdNPWP&dataBU.NPWPD&dataBU.WilNPWP = '")).append(strCekNPWP).append("' ").toString());
        jvc.fnPrint((new StringBuilder("fnSimpanBU 1: ")).append(sqlQuery.toString()).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            for(rs = stmt.executeQuery(sqlQuery.toString()); rs.next();)
            {
                int intCek = rs.getInt(1);
                if(intCek == 0)
                {
                    if(stmt != null)
                    {
                        stmt.close();
                        stmt = null;
                    }
                    sqlQuery.delete(0, sqlQuery.length());
                    sqlQuery.append("insert into dataBU (Nama, Jalan, [No], ");
                    sqlQuery.append("RT, RW, RK, Kelurahan, ");
                    sqlQuery.append("Kecamatan, Kabupaten, Kotamadya, KodePos, ");
                    sqlQuery.append("Telepon, NPWPD, NPWRD, KdPemilik, NoForm, KdBidUsaha, ");
                    sqlQuery.append("KdNPWP, KdNPWR, WilNPWP, WilNPWR, Modal) ");
                    sqlQuery.append((new StringBuilder("values ('")).append(txtNamaBU).append("', '").append(txtJalanBU).append("', '").append(txtNoBU).append("', ").toString());
                    sqlQuery.append((new StringBuilder("'")).append(txtRTBU).append("', '").append(txtRWBU).append("', '").append(txtRKBU).append("', '").append(hidKelurahanBU).append("', ").toString());
                    sqlQuery.append((new StringBuilder("'")).append(hidKecamatanBU).append("', '").append(hidKabupatenBU).append("', '").append(hidKabupatenBU).append("', '").append(txtKdPosBU).append("', ").toString());
                    sqlQuery.append((new StringBuilder("'")).append(txtTelpBU).append("', '").append(txtNPWPD).append("', '").append(txtNPWRD).append("', '").append(strKodePemilik).append("', ").toString());
                    sqlQuery.append((new StringBuilder("'")).append(txtNoForm).append("', '").append(hidBidUsaha).append("', '").append(txtKdNPWP).append("', '").append(txtKdNPWR).append("', ").toString());
                    sqlQuery.append((new StringBuilder("'")).append(txtWilNPWP).append("', '").append(txtWilNPWR).append("', '").append(txtModal).append("') ").toString());
                    jvc.fnPrint((new StringBuilder("fnSimpanBU 2: ")).append(sqlQuery.toString()).toString());
                    stmt = con.createStatement();
                    int intResult = stmt.executeUpdate(sqlQuery.toString());
                    if(intResult > 0)
                        strResult = String.valueOf(intResult);
                } else
                {
                    strResult = "0";
                }
            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return strResult;
    }

    public String fnSimpanSI(Hashtable htSI, String strKodePemilik)
    {
        String strResult = "0";
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            int intHtSI = htSI.size();
            int intTotalResult = 0;
            for(int a = 1; a <= intHtSI; a++)
            {
                String strSI[] = (String[])htSI.get(String.valueOf(a));
                String strJnsSI = strSI[0];
                String strNoSI = strSI[1];
                String strTglSI = strSI[2];
                StringBuffer sqlQuery1 = new StringBuffer();
                sqlQuery1.append("insert into dataSI (KdPemilik, NoSI, TglSI, JenisSI) ");
                sqlQuery1.append((new StringBuilder("values ('")).append(strKodePemilik).append("', '").append(strNoSI).append("', '").append(strTglSI).append("', '").append(strJnsSI).append("') ").toString());
                jvc.fnPrint((new StringBuilder("fnSimpanBU ")).append(a).append(": ").append(sqlQuery1.toString()).toString());
                int intResult = stmt.executeUpdate(sqlQuery1.toString());
                intTotalResult += intResult;
            }

            strResult = String.valueOf(intTotalResult);
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return strResult;
    }

    public Hashtable fnGetKelurahan()
    {
        Hashtable htKelurahan = new Hashtable();
        String sqlQuery = "SELECT mKelurahan.KdKabupaten, mKelurahan.KdKecamatan, mKelurahan.Kode, mKelurahan.Keterangan FROM mKelurahan";
        jvc.fnPrint((new StringBuilder("fnGetKelurahan: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htKelurahan.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htKelurahan;
    }

    public Hashtable fnGetKecamatan()
    {
        Hashtable htKecamatan = new Hashtable();
        String sqlQuery = "SELECT mKecamatan.KdKabupaten, mKecamatan.Kode, mKecamatan.Keterangan FROM mKecamatan";
        jvc.fnPrint((new StringBuilder("fnGetKecamatan: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htKecamatan.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htKecamatan;
    }

    public Hashtable fnGetKabupaten()
    {
        Hashtable htKabupaten = new Hashtable();
        String sqlQuery = "SELECT mKabupaten.Kode, mKabupaten.Keterangan FROM mKabupaten";
        jvc.fnPrint((new StringBuilder("fnGetKabupaten: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htKabupaten.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htKabupaten;
    }

    public Hashtable fnGetPADLain()
    {
        Hashtable htRetribusi = new Hashtable();
        String sqlQuery = "SELECT mPAD.Kode, mPAD.Keterangan, mPAD.KodeNPWD FROM mPAD";
        jvc.fnPrint((new StringBuilder("fnGetPADLain: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htRetribusi.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htRetribusi;
    }

    public Hashtable fnGetRetribusi()
    {
        Hashtable htRetribusi = new Hashtable();
        String sqlQuery = "SELECT mRetribusi.Kode, mRetribusi.Keterangan, mRetribusi.KodeNPWR FROM mRetribusi";
        jvc.fnPrint((new StringBuilder("fnGetRetribusi: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htRetribusi.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htRetribusi;
    }

    public Hashtable fnGetPajak()
    {
        Hashtable htPajak = new Hashtable();
        String sqlQuery = "SELECT mPajak.Kode, mPajak.Keterangan, mPajak.KodeNPWP FROM mPajak";
        jvc.fnPrint((new StringBuilder("fnGetPajak: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htPajak.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htPajak;
    }

    public Hashtable fnGetBidUsaha()
    {
        Hashtable htBidUsaha = new Hashtable();
        String sqlQuery = "SELECT mBidUsaha.Kode, mBidUsaha.Keterangan FROM mBidUsaha";
        jvc.fnPrint((new StringBuilder("fnGetBidUsaha: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htBidUsaha.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htBidUsaha;
    }

    public Hashtable fnGetHakAkses(String strKdJabatan)
    {
        Hashtable htBidUsaha = new Hashtable();
        String sqlQuery = (new StringBuilder("SELECT mAkses.KdMenu, mMenu.Menu FROM mAkses, mMenu WHERE mAkses.KdMenu = mMenu.Kode AND mAkses.KdJabatan = '")).append(strKdJabatan).append("' ").toString();
        jvc.fnPrint((new StringBuilder("fnGetBidUsaha: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htBidUsaha.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htBidUsaha;
    }

    public Hashtable fnGetInfoLogin(String strLoginId, String strPassword)
    {
        Hashtable htInfoLogin = new Hashtable();
        String sqlQuery1 = (new StringBuilder("SELECT count(*) as jml FROM dataLogin WHERE dataLogin.LoginId = '")).append(strLoginId).append("'").toString();
        jvc.fnPrint((new StringBuilder("fnGetInfoLogin 1: ")).append(sqlQuery1).toString());
        String sqlQuery2 = (new StringBuilder("SELECT mPegawai.Nama, mPegawai.Jabatan, dataLogin.LoginId, dataLogin.Password , mPegawai.NIP, mJabatan.Keterangan FROM dataLogin, mPegawai, mJabatan WHERE 1 = 1 and dataLogin.LoginId = '")).append(strLoginId).append("' ").append("and dataLogin.Password = '").append(strPassword).append("' ").append("and dataLogin.FUkey = mPegawai.Ukey ").append("and mPegawai.Jabatan = mJabatan.Kode ").toString();
        jvc.fnPrint((new StringBuilder("fnGetInfoLogin 2: ")).append(sqlQuery2).toString());
        int intCount = 0;
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery1);
            if(rs.next())
                intCount = rs.getInt(1);
            if(intCount > 0)
            {
                rs = stmt.executeQuery(sqlQuery2);
                rsmd = rs.getMetaData();
                int intCols = rsmd.getColumnCount();
                int i = 1;
                if(rs.next())
                {
                    String strArray[] = new String[intCols];
                    for(int a = 0; a <= intCols - 1; a++)
                        strArray[a] = rs.getString(a + 1);

                    htInfoLogin.put((new StringBuilder()).append(i++).toString(), strArray);
                } else
                {
                    htInfoLogin.put("1", "ERROR");
                    htInfoLogin.put("2", "Login Id dan Password tidak sesuai!");
                }
            } else
            {
                htInfoLogin.put("1", "ERROR");
                htInfoLogin.put("2", "Login tidak ditemukan!");
            }
            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htInfoLogin;
    }

    public Hashtable fnGetListLogin()
    {
        Hashtable htInfoLogin = new Hashtable();
        String sqlQuery = "SELECT mPegawai.Ukey, mPegawai.Nama, LoginId, Password FROM mPegawai LEFT JOIN dataLogin ON mPegawai.Ukey = dataLogin.FUkey";
        jvc.fnPrint((new StringBuilder("fnGetListLogin: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htInfoLogin.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htInfoLogin;
    }

    public Hashtable fnGetListJabatan()
    {
        Hashtable htInfoPemda = new Hashtable();
        String sqlQuery = "SELECT mJabatan.Ukey, mJabatan.Kode, mJabatan.Keterangan, mJabatan.HakTTD FROM mJabatan";
        jvc.fnPrint((new StringBuilder("fnGetListJabatan: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htInfoPemda.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htInfoPemda;
    }

    public Hashtable fnGetInfoPemda()
    {
        Hashtable htInfoPemda = new Hashtable();
        String sqlQuery = "SELECT mPemerintah.Daerah, mPemerintah.Bidang, mPemerintah.Alamat, mPemerintah.[No], mPemerintah.RT, mPemerintah.RW, mPemerintah.RK, mPemerintah.Kelurahan, mPemerintah.Kecamatan, mPemerintah.Kabupaten, mPemerintah.KodePos, mPemerintah.Telepon, mPemerintah.Facs, mPemerintah.Ukey FROM mPemerintah";
        jvc.fnPrint((new StringBuilder("fnGetInfoPemda: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htInfoPemda.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htInfoPemda;
    }

    public Hashtable fnGetDaftarPegawai()
    {
        Hashtable htDaftarPegawai = new Hashtable();
        String sqlQuery = "SELECT mPegawai.Nama, mPegawai.Jabatan, mPegawai.NIP, mPegawai.Alamat, mPegawai.[No], mPegawai.RT, mPegawai.RW, mPegawai.RK, mPegawai.Kelurahan, mPegawai.Kecamatan, mPegawai.Kabupaten, mPegawai.KodePos, mPegawai.Telepon, mPegawai.Facs, mPegawai.Email, mPegawai.Agama, mPegawai.GolDarah, mPegawai.Ukey FROM mPegawai";
        jvc.fnPrint((new StringBuilder("fnGetDaftarPegawai: ")).append(sqlQuery).toString());
        try
        {
            OpenConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rsmd = rs.getMetaData();
            int intCols = rsmd.getColumnCount();
            int i = 1;
            String strArray[];
            for(; rs.next(); htDaftarPegawai.put((new StringBuilder()).append(i++).toString(), strArray))
            {
                strArray = new String[intCols];
                for(int a = 0; a <= intCols - 1; a++)
                    strArray[a] = rs.getString(a + 1);

            }

            CloseConnection();
        }
        catch(SQLException se)
        {
            jvc.fnPrint((new StringBuilder("SQLException: ")).append(se).toString());
        }
        catch(Exception e)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(e).toString());
        }
        return htDaftarPegawai;
    }

    public void CloseConnection()
    {
        try
        {
            if(rs != null)
            {
                rs.close();
                rs = null;
            }
            if(stmt != null)
            {
                stmt.close();
                stmt = null;
            }
            if(con != null)
            {
                con.close();
                con = null;
            }
        }
        catch(Exception ex)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(ex).toString());
        }
    }

    public void OpenConnection()
    {
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection(DBurl, "", "");
            con.setAutoCommit(true);
        }
        catch(Exception ex)
        {
            jvc.fnPrint((new StringBuilder("Exception: ")).append(ex).append("<br>").toString());
        }
    }

    static final String DSNname = "Simpada_v01";
    static final String USERname = "";
    static final String PASSword = "";
    String DBurl;
    Connection con;
    Statement stmt;
    ResultSet rs;
    ResultSetMetaData rsmd;
    jvCommon jvc;
}
