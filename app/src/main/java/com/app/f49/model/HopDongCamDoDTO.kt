package com.app.f49.model

import com.app.f49.model.managercontract.ManagerContractDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class HopDongCamDoDTO : Serializable {
    val id: String? = null
    @SerializedName("soHopDong")
    @Expose
    val soHopDong: String? = null
    @SerializedName("idKhachHang")
    @Expose
    val idKhachHang: String? = null
    @SerializedName("ngayVay")
    @Expose
    val ngayVay: String? = null
    @SerializedName("ngayVaoSo")
    @Expose
    val ngayVaoSo: String? = null
    @SerializedName("idLoaiHopDong")
    @Expose
    val idLoaiHopDong: String? = null
    @SerializedName("soTienVay")
    @Expose
    val soTienVay: String? = null
    @SerializedName("laiXuat")
    @Expose
    val laiXuat: String? = null
    @SerializedName("laiXuatTrenHD")
    @Expose
    val laiXuatTrenHD: Any? = null
    @SerializedName("phiThamDinh")
    @Expose
    val phiThamDinh: Any? = null
    @SerializedName("phiBaoHiem")
    @Expose
    val phiBaoHiem: Any? = null
    @SerializedName("phiTrongGiuLuuKho")
    @Expose
    val phiTrongGiuLuuKho: Any? = null
    @SerializedName("phiLamHoSo")
    @Expose
    val phiLamHoSo: Any? = null
    @SerializedName("soNgayVay")
    @Expose
    val soNgayVay: String? = null
    @SerializedName("soNgayTrongKy")
    @Expose
    val soNgayTrongKy: Any? = null
    @SerializedName("soKyVay")
    @Expose
    val soKyVay: Any? = null
    @SerializedName("daThuNoDenKy")
    @Expose
    val daThuNoDenKy: Any? = null
    @SerializedName("ngayCatLai")
    @Expose
    val ngayCatLai: String? = null
    @SerializedName("catLaiTruoc")
    @Expose
    val catLaiTruoc: Boolean? = null
    @SerializedName("soTienCatLaiTruoc")
    @Expose
    val soTienCatLaiTruoc: String? = null
    @SerializedName("soTienThuPhi")
    @Expose
    val soTienThuPhi: String? = null
    @SerializedName("soTienKhachNhan")
    @Expose
    val soTienKhachNhan: String? = null
    @SerializedName("hoSoKemTheo")
    @Expose
    val hoSoKemTheo: String? = null
    @SerializedName("idNguoiLapHopDong")
    @Expose
    val idNguoiLapHopDong: String? = null
    @SerializedName("idNguoiDauTu")
    @Expose
    val idNguoiDauTu: Any? = null
    @SerializedName("viTriDeDo")
    @Expose
    val viTriDeDo: String? = null
    @SerializedName("ghiChu")
    @Expose
    val ghiChu: String? = null
    @SerializedName("soLanGiaHan")
    @Expose
    val soLanGiaHan: String? = null
    @SerializedName("duNoHienTai")
    @Expose
    val duNoHienTai: String? = null
    @SerializedName("ngayGiaoDichGanNhat")
    @Expose
    val ngayGiaoDichGanNhat: String? = null
    @SerializedName("daThuNoDenNgay")
    @Expose
    val daThuNoDenNgay: String? = null
    @SerializedName("ngayDenHan")
    @Expose
    val ngayDenHan: String? = null
    @SerializedName("trangThai")
    @Expose
    val trangThai: Any? = null
    @SerializedName("phanTramLaiVaPhi")
    @Expose
    val phanTramLaiVaPhi: String? = null
    @SerializedName("soNgayQuaHan")
    @Expose
    val soNgayQuaHan: String? = null
    @SerializedName("laiQuaHan")
    @Expose
    val laiQuaHan: String? = null
    @SerializedName("laiPhaiThu")
    @Expose
    val laiPhaiThu: String? = null
    @SerializedName("gocDaThu")
    @Expose
    val gocDaThu: String? = null
    @SerializedName("laiDaThu")
    @Expose
    val laiDaThu: String? = null
    @SerializedName("tenKhachHang")
    @Expose
    val tenKhachHang: String? = null
    @SerializedName("dienThoaiKhachHang")
    @Expose
    val dienThoaiKhachHang: String? = null
    @SerializedName("tenNguoiQuanLyHopDong")
    @Expose
    val tenNguoiQuanLyHopDong: String? = null
    @SerializedName("doDeLai")
    @Expose
    val doDeLai: String? = null
    @SerializedName("noiDung")
    @Expose
    val noiDung: String? = null
    @SerializedName("ngayNhacNo")
    @Expose
    val ngayNhacNo: String? = null
    @SerializedName("ngayHen")
    @Expose
    val ngayHen: String? = null
    @SerializedName("laiPhaiThuFake")
    @Expose
    val laiPhaiThuFake: String? = null
    @SerializedName("phiPhaiThuFake")
    @Expose
    val phiPhaiThuFake: String? = null
    var maMau: String? = null

    fun toContractDTO(): ManagerContractDTO {
        var managerContractDTO = ManagerContractDTO().apply {
            this.name = soHopDong + " - " + tenKhachHang
            this.upDown = soNgayQuaHan.toString()
            this.duNo = duNoHienTai.toString()
            this.total = laiPhaiThu.toString()
            this.bgColor = maMau
        }
        return managerContractDTO
    }
}