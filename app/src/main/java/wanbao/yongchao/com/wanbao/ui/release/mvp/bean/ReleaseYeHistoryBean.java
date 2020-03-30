package wanbao.yongchao.com.wanbao.ui.release.mvp.bean;

import java.util.ArrayList;
import java.util.List;

public class ReleaseYeHistoryBean {


    /**
     * title : 标题
     * content : 内容
     * countyId : 1
     * startTime : 2019-11-06 13:30:43
     * endTime : 2019-12-06 13:30:43
     * previewImg : https://www.wulihub.com.cn/go/JmVBkq/images/%E7%94%A8%E6%88%B7%E8%AF%A6%E6%83%85-%E5%9F%BA%E6%9C%AC%E8%B5%84%E6%96%99-%E5%BE%85%E5%AE%A1%E6%A0%B8/u736.jpg
     * images : ["http://he.yinyuetai.com/uploads/videos/common/BEA7016878C525FA4927002AF997DBF6.mp4"]
     * address : 成都市武侯区商鼎国际
     * phones : ["123467985","12345645"]
     */

    private String title;
    private String content;
    private String countyId;
    private String area;
    private String startTime;
    private String endTime;
    private String time;
    private String previewImg;
    private String address;
    private ArrayList<String> images;
    private ArrayList<String> phones;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }
}
