package wanbao.yongchao.com.wanbao.ui.mine.mvp.body;

public class WantActivitiesBody {


    /**
     * pageIndex : 1
     * pageSize : 10
     */

    private int pageIndex;
    private int pageSize;

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

    public WantActivitiesBody(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
