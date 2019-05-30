package vn.com.ttc.ecommerce.service

import com.app.f49.model.BaseResponse
import com.app.f49.model.HopDongCamDoDTO
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.home.ItemHomeDTO
import com.app.f49.model.login.LoginDTO
import com.app.f49.model.nguoiQLHD.NguoiQLHDDTO
import com.app.f49.model.profile.UserProfileDTO
import com.app.f49.model.quanlythuchi.QuanLyThuChiDTO
import com.app.f49.model.quanlythuchi.QuanLyThuChiDetailDTO
import com.app.f49.model.status.StatusDTO
import com.app.f49.model.store.StoreDTO
import com.app.f49.model.tab.TabDTO
import com.app.f49.model.topmenu.TopMenuDTO
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*


interface ApiService {
    companion object {
        const val USERPROFILE = "api/UserProfile"
        const val API_DASHBOARD = "api/Dashboard/"
        const val API_HOPDONGCAMDO = "api/HopDong/"
        const val API_TIENICH = "api/TienIch/"
        const val API_TOPMENU = "api/TopMenu/"
        const val API_THUCHI = "api/ThuChi/"
        const val API_LOGIN = "token"
    }

    /**
     * Login
     */
    @FormUrlEncoded
    @POST(API_LOGIN)
    fun login(@Field("grant_type") grant_type: String, @Field("username") username: String, @Field("password") password: String): Observable<LoginDTO>

//    /**
//     * Register
//     */
//    @POST(API_ACCOUNT + "KiemTraThongTinDangKy")
//    fun checkRegisterInfo(@Body request: RegisterAccountRequest): Observable<DataStringResponse>
//
//    @POST(API_ACCOUNT + "KiemTraSoDienThoaiDaTonTai")
//    fun checkExistsPhoneNumber(@Body request: RegisterPhoneCheckRequest): Observable<DataBooleanResponse>
//
//    @POST(API_ACCOUNT + "KiemTraOTP")
//    fun checkOTP(@Body request: OTPCheckRequest): Observable<DataBooleanResponse>
//
//    @POST(API_ACCOUNT + "DangKy")
//    fun registerAccount(@Body request: RegisterAccountRequest): Observable<LoginResponse>
//
//    /**
//     * Left menu
//     */
//    @POST(API_CATEGORY + "LayMenuLeft")
//    fun getLeftMenuList(): Observable<LeftMenuListResponse>
//
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
    fun getHDCM(@Query("idCuaHang") idCuaHang: Int?, @Query("trangThai") idTab: String?, @Query("tuKhoa") tuKhoa: String?, @Query("timChinhXac") timChinhXac: Boolean?, @Query("idNguoiQuanLyHD") idNguoiQuanLyHD: Int?, @Query("thoiGian") thoiGian: String?): Observable<BaseResponse<MutableList<HopDongCamDoDTO>>>


    /*
    * Quan ly thu chi
    * */

    @GET(API_THUCHI + "GetData")
    fun getDataQuanLyThuChi(@Query("idCuaHang") idCuaHang: Int?, @Query("dtTuNgay") dtTuNgay: Date?, @Query("dtDenNgay") dtDenNgay: Date?): Observable<BaseResponse<MutableList<QuanLyThuChiDTO>>>

    @GET(API_THUCHI + "GetDetailByID")
    fun getDetailQuanLyThuChi(@Query("idItem") idItem: Int?): Observable<BaseResponse<QuanLyThuChiDetailDTO>>
}