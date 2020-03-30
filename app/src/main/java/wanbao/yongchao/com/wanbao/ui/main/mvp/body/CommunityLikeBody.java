package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class CommunityLikeBody {

    /**
     * id : 1
     * pageIndex : 1
     * pageSize : 10
     */

    private String id;
    private String pageIndex;
    private String pageSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CommunityLikeBody(String id, String pageIndex, String pageSize) {
        this.id = id;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}