package wanbao.yongchao.com.wanbao.qiniu

import android.util.Log
import com.muzhi.camerasdk.LuBanSDK

import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.*
import org.json.JSONObject
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext
import java.util.*
import java.util.UUID.randomUUID


/**
 * Created by Administrator on 2018/10/12 0012.
 * 七牛云上传
 */
object SendAppFile : UpProgressHandler, UpCompletionHandler, UpCancellationSignal, LuBanSDK.LuBanImageFace {


    private var sendNumber = 0
    private var isCancelled = false
    private var sendKey = ""

    private var fileUrlList = ArrayList<String>()
    private var sendFileInterface: SendFileInterface? = null


    private lateinit var uploadManager: UploadManager
    private lateinit var uploadOptions: UploadOptions


    //初始化
    fun initConfiguration() {
        val config = Configuration.Builder().chunkSize(512 * 1024)
                .putThreshhold(1024 * 1024)
                .connectTimeout(10)
                .responseTimeout(60)
                .recorder(null).build()
        initUploadManager(config)
    }

    //初始化
    private fun initUploadManager(config: Configuration) {
        uploadManager = UploadManager(config)
        uploadOptions = UploadOptions(null, null, false, this, this)
    }


    //重置默认数据-上传前调用
    private fun resetDefaultSendData(sendFileInterface: SendFileInterface, sendKey: String) {
        SendAppFile.sendFileInterface = sendFileInterface
        SendAppFile.sendKey = sendKey
        sendNumber = 0
        fileUrlList.clear()
    }


    //上传图片
    fun sendImageFile(file: ArrayList<String>, token: String, face: SendFileInterface) {
        resetDefaultSendData(face, token)
        LuBanSDK.getInstance().compressImage(getContext(), file)
        LuBanSDK.getInstance().setLuBanImageFace(this)
    }


    //压缩成功
    override fun returnLuBanImageList(file: ArrayList<String>) {
        sendFileDataRequest(sendKey, file)
    }

    //压缩失败
    override fun onError(error: String) {
        sendFileInterface?.sendFileError(error)
    }

    override fun onStartImages() {}


    //上传视频
    fun sendVideoFile(file: ArrayList<String>, token: String, face: SendFileInterface) {
        Log.e("测试","开始上传方法")
        Log.e("测试","开始上传方法"+file.size)
        resetDefaultSendData(face, token)
//        sendKey = videoKey()
        sendFileDataRequest(token, file)
    }


    //上传
    private fun sendFileDataRequest(token: String, fileList: ArrayList<String>) {
        Log.e("测试","开始上传中")
        Log.e("测试","开始上传中"+fileList.size)
        sendNumber = fileList.size
        fileList.forEachIndexed { index, item ->
            val uuid = UUID.randomUUID()
            Log.e("测试","开始上传中uuid:${uuid}")
            uploadManager.put(item, "${uuid}", token, this, uploadOptions)
        }
    }


    //上传中
    override fun progress(key: String?, percent: Double) {}


    override fun complete(fileUrl: String, info: ResponseInfo, response: JSONObject) {
        if (info.isOK) sendSucceed(fileUrl) else sendError(info)
    }


    //上传成功
    private fun sendSucceed(fileUrl: String) {
//        Log.e("测试","上传成功")
        fileUrlList.add(fileUrl)
        if (fileUrlList.size >= sendNumber) sendFileInterface?.sendSucceed(fileUrlList)
    }


    //上传失败
    private fun sendError(info: ResponseInfo) {
//        Log.e("测试","上传失败")
        sendFileInterface?.sendFileError(info.error + "code:" + info.statusCode)
    }

    //取消上传
    fun setCancelled(boolean: Boolean) {
        isCancelled = boolean
    }


    override fun isCancelled(): Boolean {
        return isCancelled
    }


}