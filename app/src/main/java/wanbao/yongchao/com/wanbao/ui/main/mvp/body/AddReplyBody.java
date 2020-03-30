package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class AddReplyBody {

    /**
     * commentId : 11
     * content : 这是一条回复
     * toUserId : 5
     */

    private int commentId;
    private String content;
    private int toUserId;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public AddReplyBody(int commentId, String content, int toUserId) {
        this.commentId = commentId;
        this.content = content;
        this.toUserId = toUserId;
    }

    public AddReplyBody(int commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
