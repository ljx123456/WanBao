package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class CommentDetailsBody {

    /**
     * commentId : 1
     * sort : 1
     * pageIndex : 1
     * pageSize : 10
     */

    private String commentId;
    private int sort;
    private int pageIndex;
    private int pageSize;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public CommentDetailsBody(String commentId, int sort, int pageIndex, int pageSize) {
        this.commentId = commentId;
        this.sort = sort;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
