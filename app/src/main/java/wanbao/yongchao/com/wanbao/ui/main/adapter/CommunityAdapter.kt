package wanbao.yongchao.com.wanbao.ui.main.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.makeramen.roundedimageview.RoundedImageView
import wanbao.yongchao.com.wanbao.R
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityBean
import wanbao.yongchao.com.wanbao.utils.image.ImageConfiguration
import wanbao.yongchao.com.wanbao.utils.image.ImageLoad
import wanbao.yongchao.com.wanbao.utils.intent.intentUtils
import wanbao.yongchao.com.wanbao.utils.utils.AtColorUtil
import wanbao.yongchao.com.wanbao.utils.utils.GlideCacheUtil
import wanbao.yongchao.com.wanbao.utils.utils.Toast
import wanbao.yongchao.com.wanbao.view.NineGrid.NineGridTestLayout
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllSpan
import wanbao.yongchao.com.wanbao.view.allTextView.ShowAllTextView
import java.math.BigDecimal

class CommunityAdapter(list: MutableList<CommunityBean.DataBean>,val flag:Boolean):BaseQuickAdapter<CommunityBean.DataBean,BaseViewHolder>(R.layout.item_community,list){
//    override fun complete() {
//
//    }
//
//    private var helper: BaseViewHolder?=null

    override fun convert(helper: BaseViewHolder, item: CommunityBean.DataBean) {
        helper.setText(R.id.tv_item_community_name,item.user.nickname)

        if (item.user.authType!=null&&item.user.authType!=0){
            when(item.user.authType){
                1 ->{//实名认证
                    helper.setVisible(R.id.iv_item_community_auth,true)
                            .setImageResource(R.id.iv_item_community_auth,R.mipmap.homepage_icon_authentication_realname)

                }
                2 ->{//行业大V认证
                    helper.setVisible(R.id.iv_item_community_auth,true)
                            .setImageResource(R.id.iv_item_community_auth,R.mipmap.homepage_icon_authentication_blogger)
                }
                3 ->{//商家认证
                    helper.setVisible(R.id.iv_item_community_auth,true)
                            .setImageResource(R.id.iv_item_community_auth,R.mipmap.homepage_icon_authentication_shop)
                }
            }
        }else{
            helper.setVisible(R.id.iv_item_community_auth,false)
        }

        ImageLoad.setUserHead(item.user.avatar,helper.getView(R.id.iv_item_community_header) as RoundedImageView)

        if (item.topicName!=null&&item.topicName!=""){
            helper.setGone(R.id.tv_item_community_topic,true)
                    .setText(R.id.tv_item_community_topic,"#"+item.topicName+"#")
        }else{
            helper.setGone(R.id.tv_item_community_topic,false)
        }
        if (flag){
            helper.setGone(R.id.tv_item_community_focus,false)
        }else{
            helper.setGone(R.id.tv_item_community_focus,true)
        }

        if (item.isFocus==0){//未关注
            helper.setText(R.id.tv_item_community_focus,"关注")
                    .setTextColor(R.id.tv_item_community_focus,Color.parseColor("#fcc725"))
        }else{
            helper.setText(R.id.tv_item_community_focus,"已关注")
                    .setTextColor(R.id.tv_item_community_focus,Color.parseColor("#40ffffff"))
        }

        if (item.content!=null&&item.content!=""){
            Log.e("测试","全文处理")
//            if (!helper.getView<ShowAllTextView>(R.id.tv_item_community_content).text.toString().contains("...全文")) {
                helper.getView<ShowAllTextView>(R.id.tv_item_community_content).setCallBack(object : ShowAllTextView.CallBack {
                    override fun complete() {
                        Log.e("测试", "全文处理完")

                    }
                })
                helper.getView<ShowAllTextView>(R.id.tv_item_community_content).setMovementMethod(LinkMovementMethod.getInstance())
                helper.getView<ShowAllTextView>(R.id.tv_item_community_content).maxShowLines = 3
                helper.getView<ShowAllTextView>(R.id.tv_item_community_content).setMyText(item.content, "全文")

                helper.getView<ShowAllTextView>(R.id.tv_item_community_content).setText(AtColorUtil.getShowAllText(item.content, object : ShowAllSpan.OnAllSpanClickListener {
                    override fun onClickAt(widget: View?, name: String) {
                        intentUtils.intentUserHomePageForName(name)
                    }

                    override fun onClick(widget: View?) {

                    }
                }))

                helper.getView<ShowAllTextView>(R.id.tv_item_community_content).setOnAllSpanClickListener(object : ShowAllSpan.OnAllSpanClickListener {
                    override fun onClickAt(widget: View?, name: String?) {
                    }

                    override fun onClick(widget: View?) {
                        intentUtils.intentCommunityDetails(item.id.toString())
                    }
                })
//            }


            helper.setGone(R.id.tv_item_community_content,true)

        }else{
            helper.setGone(R.id.tv_item_community_content,false)
        }

        if (item.location!=null&&item.location!=""){
            helper.setGone(R.id.tv_item_community_address,true)
                    .setText(R.id.tv_item_community_address,item.location)
        }else{
            helper.setGone(R.id.tv_item_community_address,false)
        }

        if (item.shareNum<1000){
            helper.setText(R.id.tv_item_community_share,item.shareNum.toString())
        }else{
            helper.setText(R.id.tv_item_community_share,"999+")
        }

        if (item.commentNum<1000){
            helper.setText(R.id.tv_item_community_comment,item.commentNum.toString())
        }else{
            helper.setText(R.id.tv_item_community_comment,"999+")
        }

        if (item.likeNum<1000){
            helper.setText(R.id.tv_item_community_like,item.likeNum.toString())
        }else{
            helper.setText(R.id.tv_item_community_like,"999+")
        }
        if (item.isLike==1){//已点赞
            helper.getView<TextView>(R.id.tv_item_community_like).setCompoundDrawablesWithIntrinsicBounds(mContext.resources.getDrawable(R.mipmap.homepage_button_list_like_pre),
                    null, null, null)
            helper.getView<TextView>(R.id.tv_item_community_like).setTextColor(Color.parseColor("#FC4625"))
        }else{
            helper.getView<TextView>(R.id.tv_item_community_like).setCompoundDrawablesWithIntrinsicBounds(mContext.resources.getDrawable(R.mipmap.homepage_button_list_like_nor),
                    null, null, null)
            helper.getView<TextView>(R.id.tv_item_community_like).setTextColor(Color.parseColor("#a6ffffff"))
        }

        if (item.images!=null&&item.images.size>0){
            helper.getView<NineGridTestLayout>(R.id.layout_nine_grid).setIsShowAll(false)
            helper.getView<NineGridTestLayout>(R.id.layout_nine_grid).setUrlList(item.images)
            helper.setGone(R.id.layout_nine_grid,true)
                    .setGone(R.id.layout_community_video,false)
                    .setGone(R.id.layout_community_vertical_video, false)
        }else if (item.video!=null&&item.video!=""){
//            if (getVideoH(item.video)) {



//                .diskCacheStrategy(DiskCacheStrategy.)

            Glide.with(mContext).load(item.video+"?vframe/jpg/offset/1")
                    .listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                            helper.setGone(R.id.iv_item_community_play,true)
                            ImageLoad.setResourceImage(R.mipmap.homepage_button_play,helper.getView(R.id.iv_item_community_play))
                            return false
                        }
                    })
                    .placeholder(ImageConfiguration.imagePlaceholder)
                    .dontAnimate()
                    .into(helper.getView(R.id.iv_item_community_video) as RoundedImageView)


//            ImageLoad.setImage(item.video+"?vframe/jpg/offset/1", helper.getView(R.id.iv_item_community_video) as RoundedImageView)
            helper.setGone(R.id.layout_nine_grid, false)
                    .setGone(R.id.layout_community_vertical_video, false)
                    .setGone(R.id.layout_community_video, true)
//                    .setGone(R.id.iv_item_community_play,true)
                    .setGone(R.id.iv_item_community_video,true)


//            }else{
//                helper.setGone(R.id.layout_nine_grid, false)
//                        .setGone(R.id.layout_community_vertical_video, true)
//                        .setGone(R.id.layout_community_video, false)
//                ImageLoad.setImage(item.video, helper.getView(R.id.iv_item_community_vertical_video) as RoundedImageView)
//            }
        }else{
            helper.setGone(R.id.layout_nine_grid,false)
                    .setGone(R.id.layout_community_video,false)
                    .setGone(R.id.layout_community_vertical_video, false)
        }


        helper.setText(R.id.tv_item_community_time,item.releaseTime)

        helper.addOnClickListener(R.id.iv_item_community_header)
                .addOnClickListener(R.id.tv_item_community_name)
                .addOnClickListener(R.id.iv_item_community_more)
                .addOnClickListener(R.id.tv_item_community_topic)
//                .addOnClickListener(R.id.tv_item_community_content)
                .addOnClickListener(R.id.iv_item_community_play)
//                .addOnClickListener(R.id.iv_item_community_vertical_play)
                .addOnClickListener(R.id.tv_item_community_address)
                .addOnClickListener(R.id.layout_share)
                .addOnClickListener(R.id.layout_comment)
                .addOnClickListener(R.id.layout_like)
                .addOnClickListener(R.id.tv_item_community_focus)

//        (helper.getView<NineGridTestLayout>(R.id.layout_nine_grid) as NineGridTestLayout
//        helper.getView<>()

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            onBindViewHolder(holder,position)
        else{
            var bundle =payloads[0] as Bundle
            bundle.keySet().forEach {
                if (it=="add"){
                    holder.getView<TextView>(R.id.tv_item_community_like).setCompoundDrawablesWithIntrinsicBounds(mContext.resources.getDrawable(R.mipmap.homepage_button_list_like_pre),
                            null, null, null)
                    holder.getView<TextView>(R.id.tv_item_community_like).setTextColor(Color.parseColor("#FC4625"))
                    holder.getView<TextView>(R.id.tv_item_community_like).text=bundle.get(it) as String
                }else if (it=="cancel"){
                    holder.getView<TextView>(R.id.tv_item_community_like).setCompoundDrawablesWithIntrinsicBounds(mContext.resources.getDrawable(R.mipmap.homepage_button_list_like_nor),
                            null, null, null)
                    holder.getView<TextView>(R.id.tv_item_community_like).setTextColor(Color.parseColor("#a6ffffff"))
                    holder.getView<TextView>(R.id.tv_item_community_like).text=bundle.get(it) as String
                }else if (it=="focus"){
                    holder.getView<TextView>(R.id.tv_item_community_focus).setTextColor(Color.parseColor("#40ffffff"))
                    holder.getView<TextView>(R.id.tv_item_community_focus).text="已关注"
                }else if (it=="unfocus"){
                    holder.getView<TextView>(R.id.tv_item_community_focus).setTextColor(Color.parseColor("#fcc725"))
                    holder.getView<TextView>(R.id.tv_item_community_focus).text="关注"
                }
            }
        }
    }

    fun getVideoH(video:String):Boolean{
        var isFlag=false
        var m=MediaMetadataRetriever()
        m.setDataSource(video,HashMap<String,String>())
//        Thread(object :Runnable{
//            override fun run() {
//            }
//        }).start()
        try {
            var h=m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).toFloat()
            var w=m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).toFloat()
            if (h>w){
                isFlag=false
            }else{
                isFlag=true
            }
        }catch (e:Exception){

        }finally {
            m.release()
        }
        return isFlag
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        var imageView = holder.getView<NineGridTestLayout>(R.id.layout_nine_grid)
        if (imageView != null) {
//            Glide.with(mContext).clear(imageView)
            GlideCacheUtil.getInstance().clearImageAllCache(mContext)//清除图片所有缓存
        }
    }
}