package vn.com.ttc.ecommerce.service

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    companion object {
        const val API_ACCOUNT = "customer/"
        const val API_CATEGORY = "category/"
        const val API_DASHBOARD = "dashboard/"
    }

//    /**
//     * Login
//     */
//    @POST(API_ACCOUNT + "DangNhap")
//    fun login(@Body request: LoginRequest): Observable<LoginResponse>
//
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
//    /**
//     * Dashboard
//     */
//    @POST(API_DASHBOARD + "LayTopKhuyenMaiHot")
//    fun getDashboardBanner(): Observable<BaseResponse<ArrayList<BannerData>>>
//
//    @POST(API_DASHBOARD + "LayDanhSachTopic")
//    fun getDashboardHeaderMenu(): Observable<BaseResponse<ArrayList<MenuHeaderData>>>
//
//    @POST(API_DASHBOARD + "LayDuLieuDashboard")
//    fun getDashboardSectionData(@Body request: HomeSectionRequest): Observable<BaseResponse<HomeSectionData>>
//
//    /**
//     * User Profile
//     */
//
//    @POST(API_ACCOUNT + "GetCustomerProfile")
//    fun getProfile(@Body rq: GetUserProfileRequest): Observable<BaseResponse<GetUserProfileResponseDTO>>
//
//    @POST(API_ACCOUNT + "UpdateCustomerProfile")
//    fun updateProfile(@Body rq: UpdateProfileRequest): Observable<BaseResponse<GetUserProfileResponseDTO>>
//
//
//    /**
//     * Get category
//     */
//
//    @POST(API_CATEGORY + "LayDanhMucSanPham")
//    fun getListCategory(@Body rq: GetCategoryRequestDTO): Observable<BaseResponse<MutableList<CategoryResponseDTO>>>
}