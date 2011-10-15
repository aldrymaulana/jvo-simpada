/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jvo.simpada.serv;

import jvo.simpada.common.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class srvPengaturan extends HttpServlet
{

    public srvPengaturan()
    {
        jvg = new jvGeneral();
        jvc = new jvCommon();
        JRXML_LOCAL_PATH = jvCommon.fnGetProperty("JRXML_LOCAL_PATH").toString();
        REPORT_LOCAL_PATH = jvCommon.fnGetProperty("REPORT_LOCAL_PATH").toString();
        OUTPUT_LOCAL_PATH = jvCommon.fnGetProperty("OUTPUT_LOCAL_PATH").toString();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strMode = "0";
        if(request.getParameter("mode") != null)
            strMode = request.getParameter("mode").toString();
        String strWidth = request.getParameter("hidWidth").toString();
        request.getSession().setAttribute("strWidth", strWidth);
        String strHeight = request.getParameter("hidHeight").toString();
        request.getSession().setAttribute("strHeight", strHeight);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String strToday = sdf.format(todayDate);
        String strDate = strToday.substring(0, 2);
        String strMonth = strToday.substring(3, 5);
        String strYear = strToday.substring(6, 10);
        ifcSetting ifcs = new ifcSetting();
        request.getSession().removeAttribute("MSG");
        request.getSession().removeAttribute("htDataPemda");
        request.getSession().removeAttribute("htListJabatan");
        request.getSession().removeAttribute("htDaftarPegawai");
        request.getSession().removeAttribute("htInfoLogin");
        request.getSession().removeAttribute("htBidUsaha");
        request.getSession().removeAttribute("htPajak");
        request.getSession().removeAttribute("htRetribusi");
        request.getSession().removeAttribute("htKabupaten");
        request.getSession().removeAttribute("htKecamatan");
        request.getSession().removeAttribute("htKelurahan");
        switch(Integer.parseInt(strMode))
        {
        case 0: // '\0'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                java.util.Hashtable htBidUsaha = jvg.fnGetBidUsaha();
                request.getSession().setAttribute("htBidUsaha", htBidUsaha);
                java.util.Hashtable htPajak = jvg.fnGetPajak();
                request.getSession().setAttribute("htPajak", htPajak);
                java.util.Hashtable htRetribusi = jvg.fnGetRetribusi();
                request.getSession().setAttribute("htRetribusi", htRetribusi);
                java.util.Hashtable htKabupaten = jvg.fnGetKabupaten();
                request.getSession().setAttribute("htKabupaten", htKabupaten);
                java.util.Hashtable htKecamatan = jvg.fnGetKecamatan();
                request.getSession().setAttribute("htKecamatan", htKecamatan);
                java.util.Hashtable htKelurahan = jvg.fnGetKelurahan();
                request.getSession().setAttribute("htKelurahan", htKelurahan);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 1: // '\001'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String uKeyPemda = jvc.fnGetValue(request.getParameter("uKeyPemda"));
                String txtNamaPemda = jvc.fnGetValue(request.getParameter("txtNamaPemda"));
                String txtNamaBidang = jvc.fnGetValue(request.getParameter("txtNamaBidang"));
                String txtAlamatPemda = jvc.fnGetValue(request.getParameter("txtAlamatPemda"));
                String txtNoPemda = jvc.fnGetValue(request.getParameter("txtNoPemda"));
                String txtRTPemda = jvc.fnGetValue(request.getParameter("txtRTPemda"));
                String txtRWPemda = jvc.fnGetValue(request.getParameter("txtRWPemda"));
                String txtRKPemda = jvc.fnGetValue(request.getParameter("txtRKPemda"));
                String txtKelurahanPemda = jvc.fnGetValue(request.getParameter("txtKelurahanPemda"));
                String txtKecamatanPemda = jvc.fnGetValue(request.getParameter("txtKecamatanPemda"));
                String txtKotamadyaPemda = jvc.fnGetValue(request.getParameter("txtKotamadyaPemda"));
                String txtKodePosPemda = jvc.fnGetValue(request.getParameter("txtKodePosPemda"));
                String txtTeleponPemda = jvc.fnGetValue(request.getParameter("txtTeleponPemda"));
                String txtFacsimilePemda = jvc.fnGetValue(request.getParameter("txtFacsimilePemda"));
                ifcs.setTxtUkeyPemda(uKeyPemda);
                ifcs.setTxtNamaPemda(txtNamaPemda);
                ifcs.setTxtNamaBidang(txtNamaBidang);
                ifcs.setTxtAlamatPemda(txtAlamatPemda);
                ifcs.setTxtNoPemda(txtNoPemda);
                ifcs.setTxtRTPemda(txtRTPemda);
                ifcs.setTxtRWPemda(txtRWPemda);
                ifcs.setTxtRKPemda(txtRKPemda);
                ifcs.setTxtKelurahanPemda(txtKelurahanPemda);
                ifcs.setTxtKecamatanPemda(txtKecamatanPemda);
                ifcs.setTxtKotamadyaPemda(txtKotamadyaPemda);
                ifcs.setTxtKodePosPemda(txtKodePosPemda);
                ifcs.setTxtTeleponPemda(txtTeleponPemda);
                ifcs.setTxtFacsimilePemda(txtFacsimilePemda);
                int intUpdatePemda = jvg.fnUpdatePemda(ifcs);
                if(intUpdatePemda > 0)
                    request.getSession().setAttribute("svPemda", "Data Pemda Berhasil Disimpan!");
                else
                    request.getSession().setAttribute("svPemda", "Data Pemda Gagal Disimpan!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 2: // '\002'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String idxKary = jvc.fnGetValue(request.getParameter("idxKary"));
                String txtUkeyKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtUkeyKary_")).append(idxKary).toString()));
                String txtNamaKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtNamaKary_")).append(idxKary).toString()));
                String txtJabatanKary = jvc.fnGetValue(request.getParameter("txtJabatanKary"));
                String txtNipKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtNipKary_")).append(idxKary).toString()));
                String txtAlamatKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtAlamatKary_")).append(idxKary).toString()));
                String txtNoKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtNoKary_")).append(idxKary).toString()));
                String txtRTKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtRTKary_")).append(idxKary).toString()));
                String txtRWKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtRWKary_")).append(idxKary).toString()));
                String txtRKKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtRKKary_")).append(idxKary).toString()));
                String txtKelurahanKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtKelurahanKary_")).append(idxKary).toString()));
                String txtKecamatanKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtKecamatanKary_")).append(idxKary).toString()));
                String txtKotamadyaKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtKotamadyaKary_")).append(idxKary).toString()));
                String txtKodePosKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtKodePosKary_")).append(idxKary).toString()));
                String txtTeleponKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtTeleponKary_")).append(idxKary).toString()));
                String txtFacsimileKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtFacsimileKary_")).append(idxKary).toString()));
                ifcs.setTxtUkeyKary(txtUkeyKary);
                ifcs.setTxtNamaKary(txtNamaKary);
                ifcs.setTxtJabatanKary(txtJabatanKary);
                ifcs.setTxtNipKary(txtNipKary);
                ifcs.setTxtAlamatKary(txtAlamatKary);
                ifcs.setTxtNoKary(txtNoKary);
                ifcs.setTxtRTKary(txtRTKary);
                ifcs.setTxtRWKary(txtRWKary);
                ifcs.setTxtRKKary(txtRKKary);
                ifcs.setTxtKelurahanKary(txtKelurahanKary);
                ifcs.setTxtKecamatanKary(txtKecamatanKary);
                ifcs.setTxtKotamadyaKary(txtKotamadyaKary);
                ifcs.setTxtKodePosKary(txtKodePosKary);
                ifcs.setTxtTeleponKary(txtTeleponKary);
                ifcs.setTxtFacsimileKary(txtFacsimileKary);
                int intUpdateKaryawan = jvg.fnUpdateKaryawan(ifcs);
                if(intUpdateKaryawan > 0)
                    request.getSession().setAttribute("MSG", "Data Karyawan Berhasil Disimpan!");
                else
                    request.getSession().setAttribute("MSG", "Data Karyawan Gagal Disimpan!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 3: // '\003'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String idxKary = jvc.fnGetValue(request.getParameter("idxKary"));
                String txtUkeyKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtUkeyKary_")).append(idxKary).toString()));
                ifcs.setTxtUkeyKary(txtUkeyKary);
                int intDeleteKaryawan = jvg.fnDeleteKaryawan(txtUkeyKary);
                if(intDeleteKaryawan > 0)
                    request.getSession().setAttribute("MSG", "Data Karyawan Berhasil Dihapus!");
                else
                    request.getSession().setAttribute("MSG", "Data Karyawan Gagal Dihapus!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 4: // '\004'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String txtNamaKary = jvc.fnGetValue(request.getParameter("txtNamaKary"));
                String txtJabatanKary = jvc.fnGetValue(request.getParameter("txtJabatanKary"));
                String txtNipKary = jvc.fnGetValue(request.getParameter("txtNipKary"));
                String txtAlamatKary = jvc.fnGetValue(request.getParameter("txtAlamatKary"));
                String txtNoKary = jvc.fnGetValue(request.getParameter("txtNoKary"));
                String txtRTKary = jvc.fnGetValue(request.getParameter("txtRTKary"));
                String txtRWKary = jvc.fnGetValue(request.getParameter("txtRWKary"));
                String txtRKKary = jvc.fnGetValue(request.getParameter("txtRKKary"));
                String txtKelurahanKary = jvc.fnGetValue(request.getParameter("txtKelurahanKary"));
                String txtKecamatanKary = jvc.fnGetValue(request.getParameter("txtKecamatanKary"));
                String txtKotamadyaKary = jvc.fnGetValue(request.getParameter("txtKotamadyaKary"));
                String txtKodePosKary = jvc.fnGetValue(request.getParameter("txtKodePosKary"));
                String txtTeleponKary = jvc.fnGetValue(request.getParameter("txtTeleponKary"));
                String txtFacsimileKary = jvc.fnGetValue(request.getParameter("txtFacsimileKary"));
                ifcs.setTxtNamaKary(txtNamaKary);
                ifcs.setTxtJabatanKary(txtJabatanKary);
                ifcs.setTxtNipKary(txtNipKary);
                ifcs.setTxtAlamatKary(txtAlamatKary);
                ifcs.setTxtNoKary(txtNoKary);
                ifcs.setTxtRTKary(txtRTKary);
                ifcs.setTxtRWKary(txtRWKary);
                ifcs.setTxtRKKary(txtRKKary);
                ifcs.setTxtKelurahanKary(txtKelurahanKary);
                ifcs.setTxtKecamatanKary(txtKecamatanKary);
                ifcs.setTxtKotamadyaKary(txtKotamadyaKary);
                ifcs.setTxtKodePosKary(txtKodePosKary);
                ifcs.setTxtTeleponKary(txtTeleponKary);
                ifcs.setTxtFacsimileKary(txtFacsimileKary);
                int intTambahKaryawan = jvg.fnTambahKaryawan(ifcs);
                if(intTambahKaryawan > 0)
                    request.getSession().setAttribute("MSG", "Data Karyawan Berhasil Ditambahkan!");
                else
                    request.getSession().setAttribute("MSG", "Data Karyawan Gagal Ditambahkan!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 5: // '\005'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String idxLogin = jvc.fnGetValue(request.getParameter("idxLogin"));
                String txtUkeyKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtUkey_")).append(idxLogin).toString()));
                String txtLogin = jvc.fnGetValue(request.getParameter((new StringBuilder("txtLogin_")).append(idxLogin).toString()));
                String txtPassword = jvc.fnGetValue(request.getParameter((new StringBuilder("txtPassword_")).append(idxLogin).toString()));
                ifcs.setTxtUkeyKary(txtUkeyKary);
                ifcs.setTxtLogin(txtLogin);
                ifcs.setTxtPassword(txtPassword);
                int intSimpanLogin = jvg.fnSimpanLogin(ifcs);
                if(intSimpanLogin > 0)
                    request.getSession().setAttribute("MSG", "Data Login Berhasil Ditambahkan!");
                else
                    request.getSession().setAttribute("MSG", "Data Login Gagal Ditambahkan!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 6: // '\006'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String idxLogin = jvc.fnGetValue(request.getParameter("idxLogin"));
                String txtUkeyKary = jvc.fnGetValue(request.getParameter((new StringBuilder("txtUkey_")).append(idxLogin).toString()));
                ifcs.setTxtUkeyKary(txtUkeyKary);
                int intSimpanLogin = jvg.fnHapusLogin(ifcs);
                if(intSimpanLogin > 0)
                    request.getSession().setAttribute("MSG", "Data Login Berhasil Dihapus!");
                else
                    request.getSession().setAttribute("MSG", "Data Login Gagal Dihapus!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 7: // '\007'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String idxLogin = jvc.fnGetValue(request.getParameter("idxLogin"));
                String txtKodeBank = jvc.fnGetValue(request.getParameter((new StringBuilder("txtKodeB_")).append(idxLogin).toString()));
                String txtNamaBank = jvc.fnGetValue(request.getParameter((new StringBuilder("txtNamaB_")).append(idxLogin).toString()));
                String txtNoRekBank = jvc.fnGetValue(request.getParameter((new StringBuilder("txtNoRekB_")).append(idxLogin).toString()));
                ifcs.setTxtUkeyKary(txtKodeBank);
                ifcs.setTxtLogin(txtNamaBank);
                ifcs.setTxtPassword(txtNoRekBank);
                int intSimpanLogin = jvg.fnSimpanBank(ifcs);
                if(intSimpanLogin > 0)
                    request.getSession().setAttribute("MSG", "Data Bank Berhasil Disimpan!");
                else
                    request.getSession().setAttribute("MSG", "Data Bank Gagal Disimpan!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;

        case 8: // '\b'
            try
            {
                String strSource = "pengaturan1.jsp";
                request.getSession().setAttribute("strSource", strSource);
                String idxAkses = jvc.fnGetValue(request.getParameter("idxAkses"));
                String txtKdJabatan = jvc.fnGetValue(request.getParameter((new StringBuilder("txtKdJabatan_")).append(idxAkses).toString()));
                String txt10000 = jvc.fnGetValue(request.getParameter((new StringBuilder("chk10000_")).append(idxAkses).toString()));
                String txt20000 = jvc.fnGetValue(request.getParameter((new StringBuilder("chk20000_")).append(idxAkses).toString()));
                String txt30000 = jvc.fnGetValue(request.getParameter((new StringBuilder("chk30000_")).append(idxAkses).toString()));
                String txt40000 = jvc.fnGetValue(request.getParameter((new StringBuilder("chk40000_")).append(idxAkses).toString()));
                String txt50000 = jvc.fnGetValue(request.getParameter((new StringBuilder("chk50000_")).append(idxAkses).toString()));
                String txt60000 = jvc.fnGetValue(request.getParameter((new StringBuilder("chk60000_")).append(idxAkses).toString()));
                ifcs.setTxtKdJabatan(txtKdJabatan);
                ifcs.setTxt10000(txt10000);
                ifcs.setTxt20000(txt20000);
                ifcs.setTxt30000(txt30000);
                ifcs.setTxt40000(txt40000);
                ifcs.setTxt50000(txt50000);
                ifcs.setTxt60000(txt60000);
                int intSimpanLogin = jvg.fnSimpanAkses(ifcs);
                if(intSimpanLogin > 0)
                    request.getSession().setAttribute("MSG", "Data Akses Berhasil Disimpan!");
                else
                    request.getSession().setAttribute("MSG", "Data Akses Gagal Disimpan!");
                java.util.Hashtable htDataPemda = jvg.fnGetInfoPemda();
                request.getSession().setAttribute("htDataPemda", htDataPemda);
                java.util.Hashtable htListJabatan = jvg.fnGetListJabatan();
                request.getSession().setAttribute("htListJabatan", htListJabatan);
                java.util.Hashtable htDaftarPegawai = jvg.fnGetDaftarPegawai();
                request.getSession().setAttribute("htDaftarPegawai", htDaftarPegawai);
                java.util.Hashtable htInfoLogin = jvg.fnGetListLogin();
                request.getSession().setAttribute("htInfoLogin", htInfoLogin);
                String strKode = "";
                java.util.Hashtable htInfoBank = jvg.fnGetInfoBank(strKode);
                request.getSession().setAttribute("htInfoBank", htInfoBank);
                java.util.Hashtable htListAkses = jvg.fnGetListAkses();
                request.getSession().setAttribute("htListAkses", htListAkses);
                java.util.Hashtable htMenu = jvg.fnGetMenu();
                request.getSession().setAttribute("htMenu", htMenu);
                response.sendRedirect((new StringBuilder("index.jsp?vWidth=")).append(strWidth).append("&vHeight=").append(strHeight).toString());
                return;
            }
            catch(Exception exp)
            {
                System.out.println((new StringBuilder("Exception: ")).append(exp).toString());
            }
            return;
        }
    }

    private static final long serialVersionUID = 1L;
    jvGeneral jvg;
    jvCommon jvc;
    String JRXML_LOCAL_PATH;
    String REPORT_LOCAL_PATH;
    String OUTPUT_LOCAL_PATH;
}
