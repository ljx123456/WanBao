package wanbao.yongchao.com.wanbao.event

/**
 * Created by Administrator on 2018/11/8 0008.
 */
class ImageDownloadEvent {
    var tag: String
    var imageUrl: String

    constructor(tag: String, imageUrl: String) {
        this.tag = tag
        this.imageUrl = imageUrl
    }
}