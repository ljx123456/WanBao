package wanbao.yongchao.com.wanbao.ui.mine.mvp.body;

public class BusinessActivityBody {

    /**
     * userId : 11345689
     */

    private String userId;
    private int pageIndex;
    private int pageSize;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
