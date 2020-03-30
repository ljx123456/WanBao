package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class FansBody {

    /**
     * focusType : 1
     * pageIndex : 1
     * pageSize : 10
     */

    private String id;
    private String focusType;
    private String pageIndex;
    private String pageSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFocusType() {
        return focusType;
    }

    public void setFocusType(String focusType) {
        this.focusType = focusType;
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

    public FansBody(String id, String focusType, String pageIndex, String pageSize) {
        this.id = id;
        this.focusType = focusType;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
