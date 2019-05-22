package vn.com.ttc.ecommerce.service

import com.app.f49.model.BaseResponse
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.home.ItemHomeDTO
import com.app.f49.model.login.LoginDTO
import com.app.f49.model.profile.UserProfileDTO
import com.app.f49.model.status.StatusDTO
import com.app.f49.model.store.StoreDTO
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    companion object {
        const val USERPROFILE = "api/UserProfile"
        const val API_DASHBOARD = "api/Dashboard/"
        const val API_HOPDONGCAMDO = "api/HopDong/"
        const val API_TIENICH = "api/TienIch/"
        const val API_TOPMENU = "api/TopMenu/"
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
}