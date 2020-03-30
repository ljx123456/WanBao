package  wanbao.yongchao.com.wanbao.utils.http

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import wanbao.yongchao.com.wanbao.ui.explore.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.explore.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.login.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.login.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.main.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.mine.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.mine.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.news.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.news.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.release.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.release.mvp.body.*
import wanbao.yongchao.com.wanbao.ui.set.mvp.bean.*
import wanbao.yongchao.com.wanbao.ui.set.mvp.body.*

/**
 * Created by Administrator on 2017/12/18 0018.
 */
interface ApiService {

    //发送验证码
    @POST("loginRegistry/send/code")
    fun getByCode(@Body body: GetCodeBody): Observable<CodeBean>

    //验证码登录
    @POST("loginRegistry/login")
    fun getLoginCode(@Body body: LoginCodeBody): Observable<LoginCodeBean>

    //第三方登录
    @POST("loginRegistry/third/party/login")
    fun getLoginThird(@Body body: LoginThirdBody): Observable<LoginThirdBean>

    //获取地区列表
    @POST("loginRegistry/regions/list")
    fun getArea(@Body body: AreaBody): Observable<AreaBean>

    //获取七牛token
    @POST("loginRegistry/uploadToken")
    fun getQiniuToken(): Observable<QiniuTokenBean>

    //商家注册
    @POST("loginRegistry/registry/business")
    fun getRegisterBusiness(@Body body: BusinessBody): Observable<BusinessBean>

    //用户标签
    @POST("loginRegistry/taglist")
    fun getTags(): Observable<TagsBean>

    //用户注册
    @POST("loginRegistry/registry/user")
    fun getRegisterUser(@Body body: RegisterUserBody): Observable<UserBean>

    //城市列表
    @POST("common/open/city")
    fun getCity(@Body body: CityBody): Observable<CityBean>

    //话题列表
    @POST("user/dy/topic")
    fun getTopic(@Body body: TopicBody): Observable<TopicBean>

    //热门动态
    @POST("user/dy/home/hot")
    fun getCommunityHot(@Body body: CommunityBody): Observable<CommunityBean>

    //广场动态
    @POST("user/dy/list")
    fun getCommunitySquare(@Body body: CommunityBody): Observable<CommunityBean>

    //附近动态
    @POST("user/dy/home/nearby")
    fun getCommunityNearby(@Body body: CommunityBody): Observable<CommunityBean>

    //同城动态
    @POST("user/dy/home/citywide")
    fun getCommunityCity(@Body body: CommunityBody): Observable<CommunityBean>

    //关注动态
    @POST("user/dy/home/focus")
    fun getCommunityFocus(@Body body: CommunityBody): Observable<CommunityBean>

    //动态详情
    @POST("user/dy/details")
    fun getCommunityDetails(@Body body: CommunityDetailsBody): Observable<CommunityDetailsBean>

    //动态详情-点赞列表
    @POST("user/dy/details/likeList")
    fun getCommunityLike(@Body body: CommunityLikeBody): Observable<CommunityLikeBean>

    //评论列表
    @POST("con/get/list")
    fun getCommunityComment(@Body body: CommunityCommentBody): Observable<CommunityCommentBean>

    //获取单条评论
    @POST("con/get/comment")
    fun getComment(@Body body: CommentBody): Observable<CommentBean>

    //评论回复详情-列表
    @POST("con/get/reply")
    fun getCommentDetails(@Body body: CommentDetailsBody): Observable<CommentDetailsBean>

    //商家用户主页
    @POST("user/home")
    fun getUserHomePage(@Body body: UserHomePageBody): Observable<UserHomePageBean>
    //商家用户主页
    @POST("user/home")
    fun getUserHomePageForName(@Body body: UserHomePageForNameBody): Observable<UserHomePageBean>

    //商家用户主页动态
    @POST("user/dy/list")
    fun getHomePageCommunity(@Body body: HomePageCommunityBody): Observable<CommunityBean>

    //商家用户主页打卡列表
    @POST("user/dy/clock/list")
    fun getHomePageLocationCommunity(@Body body: HomePageLocationCommunityBody): Observable<CommunityBean>

    //城市坐标/地点主页及详情
    @POST("city/map/info")
    fun getAddressHomePage(@Body body: AddressHomePageBody): Observable<AddressHomePageBean>

    //关注/粉丝/黑名单列表
    @POST("user/center/focus/list")
    fun getFans(@Body body: FansBody): Observable<FansBean>

    //搜索用户
    @POST("user/search")
    fun getSearchUser(@Body body: SearchBody): Observable<FansBean>

    //热搜话题
    @POST("user/search/hotsearch")
    fun getHotTopic(): Observable<HotTopicBean>

    //搜索动态
    @POST("user/search")
    fun getSearchCommunity(@Body body: SearchBody): Observable<CommunityBean>

    //搜索话题
    @POST("user/search")
    fun getSearchTopic(@Body body: SearchBody): Observable<SearchTopicBean>

    //搜索地点
    @POST("user/search")
    fun getSearchAddress(@Body body: SearchBody): Observable<SearchAddressBean>

    //搜索活动
    @POST("user/search")
    fun getSearchActivity(@Body body: SearchBody): Observable<SearchActivityBean>

    //搜索地标
    @POST("user/search")
    fun getSearchLandMark(@Body body: SearchBody): Observable<SearchLandMarkBean>

    //删除动态
    @POST("user/dy/del")
    fun getCommunityDel(@Body body: CommunityDelBody): Observable<CommunityDelBean>

    //关注用户
    @POST("user/focus/user")
    fun getFocus(@Body body: FocusBody): Observable<CommunityDelBean>

    //取消关注
    @POST("user/focus/cancel")
    fun getUnFocus(@Body body: UnFocusBody): Observable<CommunityDelBean>

    //举报
    @POST("report/add")
    fun getReport(@Body body: ReportBody): Observable<CommunityDelBean>

    //举报 变化状态
    @POST("operation/change")
    fun getLike(@Body body: LikeBody): Observable<CommunityDelBean>

    //添加评论
    @POST("con/add/comment")
    fun getAddComment(@Body body: AddCommentBody): Observable<AddCommentBean>

    //添加评论
    @POST("con/add/reply")
    fun getAddReply(@Body body: AddReplyBody): Observable<AddReplyBean>

    //删除评论
    @POST("con/comment/del")
    fun getCommentDel(@Body body: CommentDelBody): Observable<CommentDelBean>

    //定位打卡位置列表
    @POST("user/dy/location")
    fun getClockAddress(@Body body: ClockAddressBody): Observable<ClockAddressBean>

    //搜索打卡位置列表
    @POST("user/dy/location/search")
    fun getSearchClock(@Body body: SearchClockBody): Observable<ClockAddressBean>

    //发布动态
    @POST("user/dy/create")
    fun getReleaseCommunity(@Body body: ReleaseCommunityBody): Observable<ReleaseBean>

    //发布夜计划
    @POST("user/info/create")
    fun getReleaseYe(@Body body: ReleaseYeBody): Observable<ReleaseYeBean>

    //探索banner
    @POST("explore/banner")
    fun getExploreBanner(@Body body: ExploreBannerBody): Observable<ExploreBannerBean>

    //banner Html 内容
    @POST("explore/banner/html")
    fun getWebBanner(@Body body: WebBannerBody): Observable<WebBannerBean>

    //探索地标列表
    @POST("city/map/list")
    fun getExploreLandMark(@Body body: ExploreLandMarkBody): Observable<ExploreLandMarkBean>

    //同城好店列表/搜索
    @POST("city/shop/list")
    fun getExploreShop(@Body body: ExploreShopBody): Observable<ExploreShopBean>

    //资讯活动列表
    @POST("info/get/list")
    fun getExploreActivity(@Body body: ExploreActivityBody): Observable<ExploreActivityBean>

    //热搜好店
    @POST("city/shop/hotsearch")
    fun getSearchHotShop(@Body body: SearchHotShopBody): Observable<SearchHotShopBean>

    //想去
    @POST("city/map/want/user/change")
    fun getWantGo(@Body body: WantGoBody): Observable<WantGoBean>

    //想去-活动用户列表
    @POST("info/want/go/user/list")
    fun getWantActivityGo(@Body body: WantActivityGoBody): Observable<WantUserBean>

    //想去-城市地标用户列表
    @POST("city/map/want/user/list")
    fun getWantUser(@Body body: WantUserBody): Observable<WantUserBean>

    //热搜计划
    @POST("info/hotsearch")
    fun getSearchHotActivities(@Body body: SearchHotActivitiesBody): Observable<SearchHotActivitiesBean>

    //资讯详情
    @POST("info/get/details")
    fun getActivitiesDetails(@Body body: ActivitiesDetailsBody): Observable<ActivitiesDetailsBean>

    //个人中心
    @POST("user/center/info")
    fun getUserInfo(@Body body: UserInfoBody): Observable<UserInfoBean>

    //编辑用户资料
    @POST("user/center/edit")
    fun getEditUser(@Body body: EditUserBody): Observable<EditUserBean>

    //编辑商家资料
    @POST("user/center/edit/business")
    fun getEditBusiness(@Body body: EditBusinessBody): Observable<EditBusinessBean>

    //提交认证
    @POST("user/center/auth/commit")
    fun getAuthRealName(@Body body: AuthRealNameBody): Observable<AuthRealNameBean>

    //认证信息
    @POST("user/center/auth/info")
    fun getAuthInfo(@Body body: AuthInfoBody): Observable<AuthInfoBean>

    //认证类型列表
    @POST("user/center/auth/type")
    fun getAuthType(@Body body: AuthTypeBody): Observable<AuthTypeBean>

    //收藏/点赞列表
    @POST("user/dy/like/collect")
    fun getLikeCommunity(@Body body: LikeCommunityBody): Observable<CommunityBean>

    //想去-资讯
    @POST("info/want/go/list")
    fun getWantActivities(@Body body: WantActivitiesBody): Observable<ExploreActivityBean>

    //想去-城市坐标
    @POST("city/map/want/go/list")
    fun getWantLandMark(@Body body: WantActivitiesBody): Observable<SearchLandMarkBean>

    //更换手机
    @POST("user/center/change/phone")
    fun getChangePhone(@Body body: ChangePhoneBody): Observable<ChangePhoneBean>

    //解绑第三方账号
    @POST("user/center/unbind/tp")
    fun getUnbind(@Body body: UnbindBody): Observable<UnbindBean>

    //通知设置-状态
    @POST("user/message/setting")
    fun getNoticeStatus(@Body body: NoticeStatusBody): Observable<NoticeStatusBean>

    //通知设置-开关
    @POST("user/message/setting/switch")
    fun getChangeNoticeStatus(@Body body: ChangeNoticeStatusBody): Observable<ChangePhoneBean>

    //帮助建议
    @POST("proposal/add")
    fun getSuggest(@Body body: SuggestBody): Observable<UnbindBean>

    //APP 是否更新
    @POST("common/version")
    fun getUpdate(@Body body: UpdateBody): Observable<UpdateBean>

    //退出登录
    @POST("loginRegistry/logout")
    fun getLogout(@Body body: LogoutBody): Observable<LogoutBean>

    //商家夜计划列表
    @POST("user/info/list")
    fun getBusinessActivity(@Body body: BusinessActivityBody): Observable<ExploreActivityBean>

    //消息
    @POST("user/message")
    fun getNews(@Body body: NewsBody): Observable<NewsBean>

    //删除系统消息
    @POST("user/message/del/sys")
    fun getDelSystem(@Body body: DelSystemBody): Observable<DelSystemBean>

    //删除消息
    @POST("user/message/del")
    fun getDelNotice(@Body body: DelNoticeBody): Observable<DelNoticeBean>

    //消息列表-系统
    @POST("user/message/list")
    fun getNotice(@Body body: NoticeBody): Observable<NoticeBean>

    //消息列表-粉丝
    @POST("user/message/list")
    fun getNoticeFans(@Body body: NoticeBody): Observable<NoticeFansBean>

    //消息列表-点赞
    @POST("user/message/list")
    fun getNoticeLike(@Body body: NoticeBody): Observable<NoticeLikeBean>

    //消息列表-@
    @POST("user/message/list")
    fun getNoticeAt(@Body body: NoticeBody): Observable<NoticeAtBean>

    //消息列表-评论
    @POST("user/message/list")
    fun getNoticeComment(@Body body: NoticeBody): Observable<NoticeCommentBean>

    //更新用户位置信息（增加app打开次数）
    @POST("user/count/update/location")
    fun getUpdateLocation(@Body body: UpdateLocationBody): Observable<UpdateLocationBean>

    //绑定第三方账号
    @POST("user/center/bind/tp")
    fun getThirdBind(@Body body: LoginThirdBody): Observable<LogoutBean>

}