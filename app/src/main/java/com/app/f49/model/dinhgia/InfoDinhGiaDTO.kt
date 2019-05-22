package com.app.f49.model.dinhgia

open class BaseCamdo {
    var title: String? = null
    var phoneNumber: String? = null
    var dateRegister: String? = null
    var isXyLy: Boolean? = false


}

class InfoDinhGiaDTO : BaseCamdo() {
    var fullName: String? = null
    var imageURL: String? = null

}

class InfoCamdoDTO : BaseCamdo() {
    var describe: String? = null
    var money: String? = null
    var nhanHieu: String? = null

}

class InfoDoGiaDungDTO : BaseCamdo() {
    var taiSan: String? = null
}