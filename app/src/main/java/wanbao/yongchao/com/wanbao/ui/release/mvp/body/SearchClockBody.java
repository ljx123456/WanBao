package wanbao.yongchao.com.wanbao.ui.release.mvp.body;

public class SearchClockBody {

    /**
     * like : 鸿恩寺
     * pageIndex : 1
     * pageSize : 20
     */

    private String like;
    private String pageIndex;
    private String pageSize;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public SearchClockBody(String like, String pageIndex, String pageSize) {
        this.like = like;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
