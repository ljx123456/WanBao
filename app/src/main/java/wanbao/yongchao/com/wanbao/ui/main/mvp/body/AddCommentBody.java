package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class AddCommentBody {

    /**
     * objectId : 1
     * objectType : 1
     * content : 这是一条评论
     */

    private int objectId;
    private int objectType;
    private String content;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AddCommentBody(int objectId, int objectType, String content) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.content = content;
    }
}
