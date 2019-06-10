package vn.com.ttc.ecommerce.service

import com.app.f49.model.BaseResponse
import com.app.f49.model.HopDongCamDoDTO
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.home.ItemHomeDTO
import com.app.f49.model.infocontract.InfoContractDTO
import com.app.f49.model.login.LoginDTO
import com.app.f49.model.nguoiQLHD.NguoiQLHDDTO
import com.app.f49.model.notification.NotificationDTO
import com.app.f49.model.profile.UserProfileDTO
import com.app.f49.model.quanlythuchi.QuanLyThuChiDTO
import com.app.f49.model.quanlythuchi.QuanLyThuChiDetailDTO
import com.app.f49.model.rutlai.RutLaiDTO
import com.app.f49.model.status.StatusDTO
import com.app.f49.model.store.StoreDTO
import com.app.f49.model.tab.TabDTO
import com.app.f49.model.topmenu.TopMenuDTO
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    companion object {
        const val USERPROFILE = "api/UserProfile"
        const val API_DASHBOARD = "api/Dashboard/"
        const val API_HOPDONGCAMDO = "api/HopDong/"
        const val API_TIENICH = "api/TienIch/"
        const val API_TOPMENU = "api/TopMenu/"
        const val API_THUCHI = "api/ThuChi/"
        const val API_RUT_LAI = "api/RutLai/"
        const val API_RUT_VON_DAU_TU = "api/QuanLyVonDauTu/"
        const val API_NOTIFICATION = "api/Notification/"
        const val API_LOGIN = "token"
        const val API_FIREBASE = "api/ManageFireBase/"
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

    @GET(USERPROFILE)
    fun getProfile(): Observable<BaseResponse<MutableList<UserProfileDTO>>>
//
//    @POST(API_ACCOUNT + "UpdateCustomerProfile")
//    fun updateProfile(@Body rq: UpdateProfileRequest): Observable<BaseResponse<GetUserProfileResponseDTO>>
//
//
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

    @GET(API_HOPDONGCAMDO + "GetHopDongCamDo")
    fun getHopDongCamDo(@Query("idCuaHang") idCuaHang: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>

    @GET(API_HOPDONGCAMDO + "GetCamDoGiaDung")
    fun getCamdoGiaDung(@Query("idCuaHang") idCuaHang: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>

    @GET(API_HOPDONGCAMDO + "GetHopDongTraGop")
    fun getHopDongTraGop(@Query("idCuaHang") idCuaHang: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>

    @GET(API_HOPDONGCAMDO + "GetChiTietHopDongCamDo")
    fun getChiTietHDCD(@Query("idHopDong") idCuaHang: Int?): Observable<BaseResponse<InfoContractDTO>>


    /*
    * Quan ly thu chi
    * */

    @GET(API_THUCHI + "GetListThuChi")
    fun getDataQuanLyThuChi(@Query("idCuaHang") idCuaHang: Int?, @Query("dtTuNgay") dtTuNgay: String?, @Query("dtDenNgay") dtDenNgay: String?): Observable<BaseResponse<MutableList<QuanLyThuChiDTO>>>

    @GET(API_THUCHI + "GetDetailThuChiByID")
    fun getDetailQuanLyThuChi(@Query("idItem") idItem: Int?): Observable<BaseResponse<MutableList<QuanLyThuChiDetailDTO>>>


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
    * Push firebase token
    * */

    @PUT(API_FIREBASE + "PutFireBase")
    fun pushFirebaseToken(@Query("email") email: String?, @Query("token") token: String?, @Query("deviceName") deviceName: String?, @Query("flg") flg: Boolean?): Observable<BaseResponse<Int>>


    /*
    * Notification
    * */

    @GET(API_NOTIFICATION + "GetDetailNotification")
    fun getNotificationList(@Query("idCuaHang") idCuaHang: String?, @Query("user") user: String): Observable<BaseResponse<MutableList<NotificationDTO>>>

    @PUT(API_NOTIFICATION + "PutStatusNotify")
    fun changeReadNotification(@Query("idNotify") idNotify: Int?): Observable<BaseResponse<Int>>
}
