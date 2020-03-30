package wanbao.yongchao.com.wanbao.ui.news.mvp.body;

public class NoticeBody {


    /**
     * type : 1
     * pageIndex : 1
     * pageSize : 10
     */

    private String type;
    private String pageIndex;
    private String pageSize;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public NoticeBody(String type, String pageIndex, String pageSize) {
        this.type = type;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
