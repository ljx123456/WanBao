package wanbao.yongchao.com.wanbao.ui.main.mvp.body;

public class CommentDelBody {


    /**
     * id : 1
     * type : 1
     */

    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CommentDelBody(String id, String type) {
        this.id = id;
        this.type = type;
    }
}
