package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class CommunityCommentBody {

    /**
     * objectId : 1
     * objectType : 2
     * sort : 2
     * pageIndex : 1
     * pageSize : 10
     */

    private String objectId;
    private int objectType;
    private int sort;
    private int pageIndex;
    private int pageSize;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public CommunityCommentBody(String objectId, int objectType, int sort, int pageIndex, int pageSize) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.sort = sort;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
