package com.app.f49.service

import com.app.f49.model.BaseResponse
import com.app.f49.model.HopDongCamDoDTO
import com.app.f49.model.baocaotonghop.BaoCaoTongHopDTO
import com.app.f49.model.createcontract.*
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.history.BorrowHistoryDTO
import com.app.f49.model.history.DetailBorrowDTO
import com.app.f49.model.history.DetailExchangeDTO
import com.app.f49.model.history.ExchangeHistoryDTO
import com.app.f49.model.home.ItemHomeDTO
import com.app.f49.model.infocontract.InfoContractDTO
import com.app.f49.model.login.LoginDTO
import com.app.f49.model.nguoiQLHD.NguoiQLHDDTO
import com.app.f49.model.nhanvien.HoaHongDTO
import com.app.f49.model.nhanvien.NhanVienDTO
import com.app.f49.model.notification.NotificationDTO
import com.app.f49.model.notification.NotificationUnread
import com.app.f49.model.profile.UserProfileDTO
import com.app.f49.model.quanlythuchi.QuanLyThuChiDTO
import com.app.f49.model.quanlythuchi.QuanLyThuChiDetailDTO
import com.app.f49.model.rutlai.RutLaiDTO
import com.app.f49.model.status.StatusDTO
import com.app.f49.model.store.StoreDTO
import com.app.f49.model.tab.TabDTO
import com.app.f49.model.taisanthanhly.*
import com.app.f49.model.thulai.ThuLaiDTO
import com.app.f49.model.thulai.ValueResponse
import com.app.f49.model.timkiem.TimKiemDTO
import com.app.f49.model.topmenu.TopMenuDTO
import com.app.f49.model.uploadImage.UploadImageDTO
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    companion object {
        const val USER_PROFILE = "api/UserProfile"
        const val API_DASHBOARD = "api/Dashboard/"
        const val API_HOPDONGCAMDO = "api/HopDong/"
        const val API_TIENICH = "api/TienIch/"
        const val API_TOPMENU = "api/TopMenu/"
        const val API_THUCHI = "api/ThuChi/"
        const val API_RUT_LAI = "api/RutLai/"
        const val API_THU_LAI = "api/ThuLai/"
        const val API_BAO_CAO = "api/BaoCao/"
        const val API_RUT_VON = "api/RutVon/"
        const val API_RUT_VON_DAU_TU = "api/QuanLyVonDauTu/"
        const val API_NOTIFICATION = "api/Notification/"
        const val API_LOGIN = "token"
        const val API_FIREBASE = "api/ManageFireBase/"
        const val API_QUAN_LY_TAI_SAN = "api/QuanLyTaiSan/"
        const val API_LICHSU = "api/LichSu/"
        const val API_TIENHOAHONG = "api/TienHoaHong/"
        const val API_HOP_DONG_THE_CHAP = "api/HopDongTheChap/"
    }

    /**
     * Login
     */
    @FormUrlEncoded
    @POST(API_LOGIN)
    fun login(@Field("grant_type") grant_type: String, @Field("username") username: String, @Field("password") password: String): Observable<LoginDTO>

    /**
     * Dashboard
     */
    @GET(API_DASHBOARD + "GetDashBoard")
    fun getDashboard(@Query("idCuaHang") idStore: Int): Observable<BaseResponse<MutableList<ItemHomeDTO>>>

    @GET(API_DASHBOARD + "GetCuaHang")
    fun getAllStore(): Observable<BaseResponse<MutableList<StoreDTO>>>

    @GET(API_TIENICH + "GetTienIch")
    fun getTienIch(@Query("idCuaHang") idStore: Int): Observable<BaseResponse<MutableList<ItemHomeDTO>>>

    /**
     * User Profile
     */

    @GET(USER_PROFILE)
    fun getProfile(): Observable<BaseResponse<MutableList<UserProfileDTO>>>

    /**
     * Get topmenu
     */

    @GET(API_TOPMENU + "GetTrangThai")
    fun getStatus(): Observable<BaseResponse<MutableList<StatusDTO>>>

    @GET(API_TOPMENU + "GetListCamDO")
    fun getListCamDo(@Query("trangThai") idStore: String): Observable<BaseResponse<MutableList<CamdoDTO>>>

    @GET(API_TOPMENU + "GetListDinhGia")
    fun getListDinhGia(@Query("trangThai") idStore: String): Observable<BaseResponse<MutableList<CamdoDTO>>>

    @GET(API_TOPMENU + "GetListDoGiaDung")
    fun getListDoGiaDung(@Query("trangThai") idStore: String): Observable<BaseResponse<MutableList<CamdoDTO>>>

    @GET("api/TopMenu")
    fun getTopMenu(): Observable<BaseResponse<TopMenuDTO>>


    /*
    * Hop dong cam do
    * */
    @GET(API_HOPDONGCAMDO + "GetTab")
    fun getListTab(): Observable<BaseResponse<MutableList<TabDTO>>>

    @GET(API_HOPDONGCAMDO + "GetTrangThaiHD")
    fun getStatusContract(): Observable<BaseResponse<MutableList<TabDTO>>>

    @GET(API_HOPDONGCAMDO + "GetNguoiQLHD")
    fun getNguoiQLHD(@Query("idCuaHang") idCuaHang: String): Observable<BaseResponse<MutableList<NguoiQLHDDTO>>>

    @GET(API_HOPDONGCAMDO + "GetListHopDongTheoLoai")
    fun getHopDongCamDo(@Query("idCuaHang") idCuaHang: Int?, @Query("loaiHD") loaiHD: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>

//    @GET(API_HOPDONGCAMDO + "GetCamDoGiaDung")
//    fun getCamdoGiaDung(@Query("idCuaHang") idCuaHang: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>
//
//    @GET(API_HOPDONGCAMDO + "GetHopDongTraGop")
//    fun getHopDongTraGop(@Query("idCuaHang") idCuaHang: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>
//
//
//    @GET(API_HOPDONGCAMDO + "GetHopDongDuNoGiamDan")
//    fun getHopDongDuNoGiamDan(@Query("idCuaHang") idCuaHang: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>

    @GET(API_HOPDONGCAMDO + "GetChiTietHopDong")
    fun getChiTietHDCD(@Query("idHopDong") idCuaHang: Int?): Observable<BaseResponse<InfoContractDTO>>

    @POST(API_HOPDONGCAMDO + "UploadImage")
    fun uploadImage(@Body rq: UploadImageDTO): Observable<BaseResponse<Boolean>>

//    @GET(API_HOPDONGCAMDO + "GetChiTietCamDoGiaDung")
//    fun getChiTietCamDoGiaDung(@Query("idHopDong") idCuaHang: Int?): Observable<BaseResponse<MutableList<InfoContractDTO>>>
//
//    @GET(API_HOPDONGCAMDO + "GetChiTietHopDongTraGop")
//    fun getChiTietHopDongTraGop(@Query("idHopDong") idCuaHang: Int?): Observable<BaseResponse<MutableList<InfoContractDTO>>>
//
//    @GET(API_HOPDONGCAMDO + "GetChiTietHopDongDuNoGiamDan")
//    fun getChiTietHopDongDuNoGiamDan(@Query("idHopDong") idCuaHang: Int?): Observable<BaseResponse<MutableList<InfoContractDTO>>>


    /*
    * Quan ly thu chi
    * */

    @GET(API_THUCHI + "GetListThuChi")
    fun getDataQuanLyThuChi(@Query("idCuaHang") idCuaHang: Int?, @Query("dtTuNgay") dtTuNgay: String?, @Query("dtDenNgay") dtDenNgay: String?): Observable<BaseResponse<MutableList<QuanLyThuChiDTO>>>

    @GET(API_THUCHI + "GetDetailThuChiByID")
    fun getDetailQuanLyThuChi(@Query("idItem") idItem: Int?): Observable<BaseResponse<MutableList<QuanLyThuChiDetailDTO>>>

    @PUT(API_THUCHI + "PutDuyetThongTinChi")
    fun duyetChi(@Query("id") idItem: Int?, @Query("yKien") yKien: String?, @Query("trangThai") trangThai: Boolean): Observable<BaseResponse<RutLaiDTO>>
    /*
    *Rut von dau tu
    * */


    @GET(API_RUT_VON_DAU_TU + "GetListVonDauTu")
    fun getListVonDauTu(@Query("idCuaHang") idCuaHang: Int?, @Query("dtTuNgay") dtTuNgay: String?, @Query("dtDenNgay") dtDenNgay: String?): Observable<BaseResponse<MutableList<QuanLyThuChiDTO>>>

    @GET(API_RUT_VON_DAU_TU + "GetDetailVonDauTu")
    fun getDetailVonDauTu(@Query("id") idItem: Int?): Observable<BaseResponse<MutableList<QuanLyThuChiDetailDTO>>>

    /*
    * Rut lai cua hang
    * */

    @GET(API_RUT_LAI + "GetTabTrangThai")
    fun getTabRutLaiCuahang(): Observable<BaseResponse<MutableList<TabDTO>>>


    @GET(API_RUT_LAI + "GetListRutLai")
    fun getDataRutLai(@Query("idCuaHang") idCuaHang: Int?, @Query("idTab") idTab: Int?): Observable<BaseResponse<MutableList<RutLaiDTO>>>

    @GET(API_RUT_LAI + "GetDetailRutLaiByID")
    fun getDetaiRutLailById(@Query("idItem") idItem: Int?): Observable<BaseResponse<RutLaiDTO>>

    @PUT(API_RUT_LAI + "PutDuyetRutLai")
    fun duyetRutLai(@Query("id") idItem: Int?, @Query("yKien") yKien: String?, @Query("trangThai") trangThai: Boolean): Observable<BaseResponse<RutLaiDTO>>

    /*
  * Rut von cua hang
  * */
//
//    @GET(API_RUT_LAI + "GetTabTrangThai")
//    fun getTabRutLaiCuahang(): Observable<BaseResponse<MutableList<TabDTO>>>


    @GET(API_RUT_VON + "GetListRutVon")
    fun getListRutVon(@Query("idCuaHang") idCuaHang: Int?, @Query("idTab") idTab: Int?): Observable<BaseResponse<MutableList<RutLaiDTO>>>

    @GET(API_RUT_VON + "GetDetailRutVonByID")
    fun getDetaiRutVonlById(@Query("idItem") idItem: Int?): Observable<BaseResponse<RutLaiDTO>>

    @PUT(API_RUT_VON + "PutDuyetRutVon")
    fun duyetRutVon(@Query("id") idItem: Int?, @Query("yKien") yKien: String?, @Query("trangThai") trangThai: Boolean): Observable<BaseResponse<RutLaiDTO>>


    /*
    * Push firebase token
    * */

    @PUT(API_FIREBASE + "PutFireBase")
    fun pushFirebaseToken(@Query("email") email: String?, @Query("token") token: String?, @Query("deviceName") deviceName: String?, @Query("flg") flg: Boolean?): Observable<BaseResponse<Int>>


    /*
    * Notification
    * */

    @GET(API_NOTIFICATION + "GetListNotification")
    fun getNotificationList(@Query("idCuaHang") idCuaHang: Int?, @Query("pageIndex") pageIndex: Int?, @Query("pageSize") pageSize: Int?): Observable<BaseResponse<MutableList<NotificationDTO>>>

    @PUT(API_NOTIFICATION + "PutStatusNotify")
    fun changeReadNotification(@Query("idNotify") idNotify: Int?): Observable<BaseResponse<Int>>

    @PUT(API_NOTIFICATION + "PutReadAll")
    fun putReadAll(@Query("idCuaHang") idCuaHang: Int?): Observable<BaseResponse<Int>>

    @DELETE(API_NOTIFICATION + "DeleteNotify")
    fun deleteNotification(@Query("id") id: Int?): Observable<BaseResponse<Int>>

    @GET(API_NOTIFICATION + "GetCountNotify")
    fun getCountNotificationUnread(@Query("idCuaHang") idCuaHang: Int?): Observable<BaseResponse<NotificationUnread>>

    /*
    *
    * Quan ly tai san
    * */

    @GET(API_QUAN_LY_TAI_SAN + "GetDLNhomTS")
    fun getListNhomTaiSan(): Observable<BaseResponse<MutableList<NhomTaiSanDTO>>>

    @GET(API_QUAN_LY_TAI_SAN + "GetDLTenTaiSan")
    fun getListTenTaiSan(@Query("idNhom") idNhom: Int?): Observable<BaseResponse<MutableList<TenTaiSanDTO>>>

    @GET(API_QUAN_LY_TAI_SAN + "GetTabTrangThaiTaiSan")
    fun getListTrangThaiTaiSan(): Observable<BaseResponse<MutableList<TrangThaiTaiSanDTO>>>

    @GET(API_QUAN_LY_TAI_SAN + "GetDSTaiSan")
    fun getListTaiSan(@Query("idNhomVatCamDo") idNhomVatCamCo: Int?, @Query("idVatCamDo") idVatCamCo: Int?, @Query("trangThai") trangThai: Int?): Observable<BaseResponse<MutableList<TaiSanDTO>>>


    @GET(API_QUAN_LY_TAI_SAN + "GetDetailTaiSan")
    fun getTaiSanDetail(@Query("idItem") idNhom: Int?): Observable<BaseResponse<ThongTinTaiSanThanhLyDTO>>


    /*
    * Lich su giao dich
    * */

    @GET(API_LICHSU + "GetLichSuGiaoDich")
    fun getLichSuGiaoDich(@Query("idHopDong") idHopDong: Int?): Observable<BaseResponse<MutableList<ExchangeHistoryDTO>>>

    @GET(API_LICHSU + "GetLichSuVayNo")
    fun getLichSuVayNo(@Query("idKhachHang") idKhachHang: Int?): Observable<BaseResponse<MutableList<BorrowHistoryDTO>>>


    @GET(API_LICHSU + "GetChiTietLichSuGiaoDich")
    fun getChiTietLichSuGiaoDich(@Query("idGiaoDich") idGiaoDich: Int?, @Query("idHopDong") idHopDong: Int?): Observable<BaseResponse<DetailExchangeDTO>>

    @GET(API_LICHSU + "GetChiTietLichSuVayNo")
    fun getChiTietLichSuVayNo(@Query("idHopDong") idHopDong: Int?): Observable<BaseResponse<DetailBorrowDTO>>


    /*
    * Bao cao
    * */
    @GET(API_BAO_CAO + "GetBaoCaoTongHop")
    fun getBaoCaoTongHop(@Query("idCuaHang") idItem: Int?, @Query("dtTuNgay") dtTuNgay: String?, @Query("dtDenNgay") dtDenNgay: String?): Observable<BaseResponse<BaoCaoTongHopDTO>>

    //
    @GET(API_TIENHOAHONG + "GetDanhSachNhanVien")
    fun getListNhanVien(): Observable<BaseResponse<MutableList<NhanVienDTO>>>

    @GET(API_TIENHOAHONG + "GetTienHoaHong")
    fun getTienHoaHong(@Query("idNhanVien") idNhanVien: Int?, @Query("dtTuNgay") dtTuNgay: String?, @Query("dtDenNgay") dtDenNgay: String?): Observable<BaseResponse<HoaHongDTO>>

    @GET(API_TIENICH + "GetTimKiemHopDong")
    fun timKiemHopDong(@Query("tuKhoa") tuKhoa: String?, @Query("pageIndex") pageIndex: Int?): Observable<BaseResponse<MutableList<TimKiemDTO>>>


    /*
    * THU LAI
    * */

    @GET(API_THU_LAI + "GetLoaiGiaoDich")
    fun getLoaiGiaoDich(): Observable<BaseResponse<MutableList<ValueResponse>>>

    @GET(API_THU_LAI + "GetChiTietHopDongThuLai")
    fun getChiTietHopDongThuLai(@Query("idHopDong") idHopDong: Int?, @Query("ngayHieuLuc") ngayHieuLuc: String?): Observable<BaseResponse<ThuLaiDTO>>

    @PUT(API_THU_LAI + "PutThucHienThuLai")
    fun putThucHienThuLai(@Query("idHopDong") idHopDong: Int?, @Query("loaiGiaoDich") loaiGiaoDich: Int?, @Query("idCuaHangFormApp") idCuaHangFormApp: Int?, @Query("tienThuThucTe") tienThuThucTe: Double?, @Query("ngayHieuLuc") ngayHieuLuc: String?): Observable<BaseResponse<ThuLaiDTO?>>


    @GET("api/KhachHang/TimKiem")
    fun timKiem(@Query("key") key: String?): Observable<BaseResponse<MutableList<KhachHangDTO>>>

    @GET(API_HOP_DONG_THE_CHAP + "LayDanhSachTaiSan")
    fun layDanhSachTaiSan(): Observable<BaseResponse<MutableList<TaiSanInHDDTO>>>

    @GET(API_HOP_DONG_THE_CHAP + "LayThuocTinhTaiSan")
    fun layThuocTinhTaiSan(@Query("loaiTaiSan") loaiTaiSan: String?): Observable<BaseResponse<MutableList<ThuocTinhTaiSanDTO>>>

    @POST(API_HOP_DONG_THE_CHAP + "LoadTaoMoi")
    fun  loadTaoMoi(@Body rq : IDCuaHangDTO): Observable<BaseResponse<LoadTaoMoiDTO>>

    @POST(API_HOP_DONG_THE_CHAP + "TinhSoTienKhachNhan")
    fun tinhSoTienKhachNhan(@Body rq : InputTinhTienKhachNhanDTO) : Observable<BaseResponse<OutputTinhTienKhachNhanDTO>>
}
