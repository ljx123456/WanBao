package wanbao.yongchao.com.wanbao.ui.release.mvp.bean;

import java.util.ArrayList;
import java.util.List;

public class ReleaseHistoryBean {

    /**
     * content : @张三 &h;@lisi &h;@fjdsk &h;@nn &h;@fz &h;
     * video :
     * images : ["http://","http://"]
     * topicId : 1
     * locationId : B001786RF6
     */

    private String content;
    private String video;
    private Long duration;
    private String topicId;
    private String topic;
    private String locationId;
    private String address;
    private ArrayList<String> images;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
