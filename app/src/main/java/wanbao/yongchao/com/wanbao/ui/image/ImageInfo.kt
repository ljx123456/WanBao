package wanbao.yongchao.com.wanbao.ui.image

/**
 * Created by Administrator on 2018/8/20 0020.
 */
data class ImageBannerInfo(val imageUrl: String?, var isAddButton: Boolean, var imageId: Int,var url:String,var title:String,var description:String)
data class ImageInfo(val imageUrl: String?, var isAddButton: Boolean, var imageId: Int)
data class ImageShopInfo(val imageUrl: String?, var isAddButton: Boolean, var imageId: Int,var id:String)
data class ImageExploreInfo(val imageUrl: String?, var isAddButton: Boolean, var imageId: Int,var id:Int,var type:Int,var objectId:Int?)

