package wanbao.yongchao.com.wanbao.utils.intent

import android.content.Intent
import android.util.Log
import com.blankj.utilcode.util.ActivityUtils
import wanbao.yongchao.com.wanbao.base.BaseContext.getContext
import wanbao.yongchao.com.wanbao.ui.explore.activity.*
import wanbao.yongchao.com.wanbao.ui.image.*
import wanbao.yongchao.com.wanbao.ui.login.activity.*
import wanbao.yongchao.com.wanbao.ui.main.activity.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommentDetailsBean
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.CommunityCommentBean
import wanbao.yongchao.com.wanbao.ui.mine.activity.*
import wanbao.yongchao.com.wanbao.ui.news.activity.*
import wanbao.yongchao.com.wanbao.ui.release.activity.*
import wanbao.yongchao.com.wanbao.ui.set.activity.*


object intentUtils{

    fun intentCity(first:String){
        var intent = Intent(getContext(), CityActivity::class.java)
        intent.putExtra("first", first)
        ActivityUtils.startActivity(intent)
    }

    fun intentCity(){
        ActivityUtils.startActivity(CityActivity::class.java)
    }

    fun intentLogin(){
        ActivityUtils.startActivity(LoginPhoneActivity::class.java)
    }

    fun intentLoginCode(phone:String){
        var intent = Intent(getContext(), LoginCodeActivity::class.java)
        intent.putExtra("phone", phone)
        ActivityUtils.startActivity(intent)
    }

    fun intentLoginCode(phone:String,code:String){
        var intent = Intent(getContext(), LoginCodeActivity::class.java)
        intent.putExtra("phone", phone)
        intent.putExtra("code", code)
        ActivityUtils.startActivity(intent)
    }

    fun intentLoginBind(code:String){
        var intent = Intent(getContext(), LoginBindPhoneActivity::class.java)
        intent.putExtra("code", code)
        ActivityUtils.startActivity(intent)
    }

    fun intentRegisterIdentity(){
        ActivityUtils.startActivity(RegisterIdentityActivity::class.java)
    }

    fun intentRegisterUser(){
        ActivityUtils.startActivity(RegisterUserActivity::class.java)
    }

    fun intentRegisterTags(){
        ActivityUtils.startActivity(RegisterTagsActivity::class.java)
    }

    fun intentRegisterTags(flag:String){
        var intent = Intent(getContext(), RegisterTagsActivity::class.java)
        intent.putExtra("flag", flag)
        ActivityUtils.startActivity(intent)
    }

    fun intentRegisterBusiness(){
        ActivityUtils.startActivity(RegisterBusinessActivity::class.java)
    }

    fun intentRegisterBusinessMap(province:String,city:String,area:String){
        var intent = Intent(getContext(), RegisterBusinessMapActivity::class.java)
        intent.putExtra("province", province)
        intent.putExtra("city", city)
        intent.putExtra("area", area)
        ActivityUtils.startActivity(intent)
    }

    fun intentRegisterBusinessMap(province:String,city:String,area:String,add:String){
        var intent = Intent(getContext(), RegisterBusinessMapActivity::class.java)
        intent.putExtra("province", province)
        intent.putExtra("city", city)
        intent.putExtra("area", area)
        intent.putExtra("address",add)
        ActivityUtils.startActivity(intent)
    }

    fun intentMain(){
        ActivityUtils.finishOtherActivities(MainActivity::class.java)
        ActivityUtils.startActivity(MainActivity::class.java)
    }

    fun intentMain(mine:String){
        var intent = Intent(getContext(), MainActivity::class.java)
        intent.putExtra("mine", mine)
        ActivityUtils.startActivity(intent)
    }

    fun intentMainCity(city:String){
        Log.e("测试阿水",city)
        var intent = Intent(getContext(), MainActivity::class.java)
        intent.putExtra("city", city)
        ActivityUtils.startActivity(intent)

    }

    /**
     * 图片详情-查看自己的
     */
    fun intentImage(delete: Boolean, list: ArrayList<ImageInfo>, index: Int) {
        val imageList = ArrayList<ImageBrowseInfo>()
        list.forEach { if (!it.isAddButton) imageList.add(ImageBrowseInfo(it.imageUrl!!, false, it.imageId)) }
        val intent = Intent(getContext(), ImageActivity::class.java)
        intent.putExtra("images", imageList)
        intent.putExtra("delete", delete)
        intent.putExtra("index", index)
        ActivityUtils.startActivity(intent)
    }

    fun intentVideo(video: String) {
        var intent = Intent(getContext(), VideoActivity::class.java)
        intent.putExtra("video", video)
        ActivityUtils.startActivity(intent)
    }

    fun intentCommunityDetails(id: String) {
        var intent = Intent(getContext(), CommunityDetailsActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentNoticeCommunityDetails(id: String,bean:CommunityCommentBean.DataBean.CommentsBean) {
        var intent = Intent(getContext(), CommunityDetailsActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("bean",bean)
        ActivityUtils.startActivity(intent)
    }

    fun intentTopicDetails(id:String,topic: String) {
        var intent = Intent(getContext(), TopicDetailsActivity::class.java)
        intent.putExtra("topic", topic)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentBusinessHomePage(id: String){
        var intent = Intent(getContext(), BusinessHomePageActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentBusinessHomePageForName(name: String){
        var intent = Intent(getContext(), BusinessHomePageActivity::class.java)
        intent.putExtra("name", name)
        ActivityUtils.startActivity(intent)
    }

    fun intentUserHomePage(id: String){
        var intent = Intent(getContext(), BusinessHomePageActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentUserHomePageForName(name: String){
        var intent = Intent(getContext(), BusinessHomePageActivity::class.java)
        intent.putExtra("name", name)
        ActivityUtils.startActivity(intent)
    }


    fun intentAddressHomePage(id: String){
        var intent = Intent(getContext(), AddressHomePageActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentCommentDetails(id: String){
        var intent = Intent(getContext(), CommentDetailsActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentNoticeCommentDetails(id: String,bean: CommentDetailsBean.DataBean.CommentsBean){
        var intent = Intent(getContext(), CommentDetailsActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("bean",bean)
        ActivityUtils.startActivity(intent)
    }

    fun intentCommunitySearch(){
        ActivityUtils.startActivity(CommunitySearchActivity::class.java)
    }

    fun intentFans(id:String,str: String) {
        var intent = Intent(getContext(), FansActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("fans", str)
        ActivityUtils.startActivity(intent)
    }

    fun intentWebBanner(id:String){
        var intent = Intent(getContext(), WebBannerActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentLandMark(){
        ActivityUtils.startActivity(LandMarkActivity::class.java)
    }

    fun intentLandMarkDetails(id:String){
        var intent = Intent(getContext(), LandMarkDetailsActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentShop(){
        ActivityUtils.startActivity(ShopActivity::class.java)
    }

    fun intentShopSearch(){
        ActivityUtils.startActivity(ShopSearchActivity::class.java)
    }

    fun intentActivities(){
        ActivityUtils.startActivity(ActivitiesActivity::class.java)
    }

    fun intentActivitiesSearch(){
        ActivityUtils.startActivity(ActivitiesSearchActivity::class.java)
    }

    fun intentActivitiesDetails(id:Int){
        var intent = Intent(getContext(), ActivitiesDetailsActivity::class.java)
        intent.putExtra("id", id)
        ActivityUtils.startActivity(intent)
    }

    fun intentNoticeActivitiesDetails(id:Int,bean:CommunityCommentBean.DataBean.CommentsBean){
        var intent = Intent(getContext(), ActivitiesDetailsActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("bean",bean)
        ActivityUtils.startActivity(intent)
    }

    fun intentWant(id:String,num:String){
        var intent = Intent(getContext(), WantActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("num", num)
        ActivityUtils.startActivity(intent)
    }

    fun intentWantActivity(id:String,num:String){
        var intent = Intent(getContext(), WantActivityGoActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("num", num)
        ActivityUtils.startActivity(intent)
    }

    fun intentRelease(){
        ActivityUtils.startActivity(ReleaseActivity::class.java)
    }

    fun intentRelease(type:String){
        var intent = Intent(getContext(), ReleaseActivity::class.java)
        intent.putExtra("type", type)
        ActivityUtils.startActivity(intent)
    }

    fun intentRelease(topicId:String,topic:String){
        var intent = Intent(getContext(), ReleaseActivity::class.java)
        intent.putExtra("topicId", topicId)
        intent.putExtra("topic", topic)
        ActivityUtils.startActivity(intent)
    }

    fun intentReleaseYe(){
        ActivityUtils.startActivity(ReleaseYeActivity::class.java)
    }

    fun intentPreview(images:ArrayList<String>,title:String,content:String,area:String,address:String,time:String,phone:ArrayList<String>){
        var intent = Intent(getContext(), PreviewYeActivity::class.java)
        intent.putStringArrayListExtra("images",images)
        intent.putExtra("title", title)
        intent.putExtra("content", content)
        intent.putExtra("area", area)
        intent.putExtra("address", address)
        intent.putExtra("time", time)
        intent.putStringArrayListExtra("phone",phone)
        ActivityUtils.startActivity(intent)
    }

    fun intentUserCover(url:String){
        var intent = Intent(getContext(), EditUserCoverActivity::class.java)
        intent.putExtra("url", url)
        ActivityUtils.startActivity(intent)
    }

    fun intentAuthCenter(){
        ActivityUtils.startActivity(UserAuthCenterActivity::class.java)
    }

    fun intentAuthRealName(){
        ActivityUtils.startActivity(AuthRealNameActivity::class.java)
    }

    fun intentAuthDetails(){
        ActivityUtils.startActivity(AuthDetailsActivity::class.java)
    }

    fun intentAuthBloggerRealName(){
        ActivityUtils.startActivity(AuthBloggerRealNameActivity::class.java)
    }

    fun intentAuthBlogger(name:String,card:String,front:String,back:String,hand:String){
        var intent = Intent(getContext(), AuthBloggerActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("card", card)
        intent.putExtra("front", front)
        intent.putExtra("back", back)
        intent.putExtra("hand", hand)
        ActivityUtils.startActivity(intent)
    }

    fun intentBusinessAuthCenter(){
        ActivityUtils.startActivity(BusinessAuthCenterActivity::class.java)
    }

    fun intentBusinessAuthRealName(){
        ActivityUtils.startActivity(BusinessAuthRealNameActivity::class.java)
    }

    fun intentBusinessAuth(name:String,card:String,front:String,back:String,hand:String){
        var intent = Intent(getContext(), BusinessAuthActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("card", card)
        intent.putExtra("front", front)
        intent.putExtra("back", back)
        intent.putExtra("hand", hand)
        ActivityUtils.startActivity(intent)
    }

    fun intentLikeCommunity(){
        ActivityUtils.startActivity(LikeCommunityActivity::class.java)
    }

    fun intentMineCollect(){
        ActivityUtils.startActivity(MineCollectActivity::class.java)
    }

    fun intentMineWant(){
        ActivityUtils.startActivity(MineWantActivity::class.java)
    }

    fun intentMineFans(id:String,str: String) {
        var intent = Intent(getContext(), MineFansActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("fans", str)
        ActivityUtils.startActivity(intent)
    }

    fun intentUserInfo(){
        ActivityUtils.startActivity(UserInfoActivity::class.java)
    }

    fun intentEditUserName(name: String){
        var intent = Intent(getContext(), EditUserNameActivity::class.java)
        intent.putExtra("name", name)
        ActivityUtils.startActivity(intent)
    }

    fun intentEditUserSign(sign:String){
        var intent = Intent(getContext(), EditUserSignActivity::class.java)
        intent.putExtra("sign", sign)
        ActivityUtils.startActivity(intent)
    }

    fun intentUserAvatar(url:String){
        var intent = Intent(getContext(), EditUserAvatarActivity::class.java)
        intent.putExtra("url", url)
        ActivityUtils.startActivity(intent)
    }

    fun intentBusinessAvatar(url:String){
        var intent = Intent(getContext(), EditBusinessAvatarActivity::class.java)
        intent.putExtra("url", url)
        ActivityUtils.startActivity(intent)
    }

    fun intentEditBusinessCover(list:ArrayList<String>){
        var intent = Intent(getContext(), EditBusinessCoverActivity::class.java)
        intent.putExtra("images", list)
        ActivityUtils.startActivity(intent)
    }

    fun intentBusinessInfo(){
        ActivityUtils.startActivity(BusinessInfoActivity::class.java)
    }

    fun intentEditBusinessName(name: String){
        var intent = Intent(getContext(), EditBusinessNameActivity::class.java)
        intent.putExtra("name", name)
        ActivityUtils.startActivity(intent)
    }

    fun intentEditBusinessPhone(phone: ArrayList<String>){
        var intent = Intent(getContext(), EditBusinessPhoneActivity::class.java)
        intent.putExtra("phone", phone)
        ActivityUtils.startActivity(intent)
    }

    fun intentBusinessMoney(){
        ActivityUtils.startActivity(EditBusinessMoneyActivity::class.java)
    }

    fun intentEditBusinessAddress(address: String){
        var intent = Intent(getContext(), EditBusinessAddressActivity::class.java)
        intent.putExtra("address", address)
        ActivityUtils.startActivity(intent)
    }

    fun intentBusinessSign(sign:String){
        var intent = Intent(getContext(), EditBusinessSignActivity::class.java)
        intent.putExtra("sign", sign)
        ActivityUtils.startActivity(intent)
    }

    fun intentSet(){
        ActivityUtils.startActivity(SetActivity::class.java)
    }

    fun intentAccount(){
        ActivityUtils.startActivity(AccountActivity::class.java)
    }

    fun intentChangePhone(token:String){
        var intent = Intent(getContext(), ChangePhoneActivity::class.java)
        intent.putExtra("token",token)
        ActivityUtils.startActivity(intent)
    }

    fun intentChangePhoneCode(phone:String,token:String){
        var intent = Intent(getContext(), ChangePhoneCodeActivity::class.java)
        intent.putExtra("phone", phone)
        intent.putExtra("token",token)
        ActivityUtils.startActivity(intent)
    }

    fun intentBlacklist(){
        ActivityUtils.startActivity(BlacklistActivity::class.java)
    }

    fun intentNoticeSet(){
        ActivityUtils.startActivity(NoticeSetActivity::class.java)
    }

    fun intentAbout(){
        ActivityUtils.startActivity(AboutActivity::class.java)
    }

    fun intentSuggest(){
        ActivityUtils.startActivity(SuggestActivity::class.java)
    }

    fun intentNotice(){
        ActivityUtils.startActivity(NoticeActivity::class.java)
    }

    fun intentNoticeFans(){
        ActivityUtils.startActivity(NoticeFansActivity::class.java)
    }

    fun intentNoticeLike(){
        ActivityUtils.startActivity(NoticeLikeActivity::class.java)
    }

    fun intentNoticeAt(){
        ActivityUtils.startActivity(NoticeAtActivity::class.java)
    }

    fun intentNoticeComment(){
        ActivityUtils.startActivity(NoticeCommentActivity::class.java)
    }

    fun intentAgreement(title: String){
        var intent = Intent(getContext(), AgreementActivity::class.java)
        intent.putExtra("title", title)
        ActivityUtils.startActivity(intent)
    }

}