package com.app.f49.model.nhanvien

class HoaHongDTO {
    var danhSachHopDong: MutableList<HoaHongItem>? = null
    var tongTien: Double? = null

    class HoaHongItem {
        var soHopDong: String? = null
        var stt: String? = null
        var tenKhachHang: String? = null
        var soTien: String? = null
        var soTienTinhTong: Double = 0.0
    }
}