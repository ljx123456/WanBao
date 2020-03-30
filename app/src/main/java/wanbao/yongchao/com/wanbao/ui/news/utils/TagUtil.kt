package wanbao.yongchao.com.wanbao.ui.news.utils

object TagUtil{
    private var flag=false
    private var content=""
    private var time=""
    private var num=""

    public fun getTag():Boolean{
        return flag
    }

    public fun setTag(flag:Boolean){
        this.flag=flag
    }

    fun getContent():String{
        return  content
    }

    fun setContent(str:String){
        this.content=str
    }

    fun getTime():String{
        return  time
    }

    fun setTime(str:String){
        this.time=str
    }

    fun getNum():String{
        return  num
    }

    fun setNum(str:String){
        this.num=str
    }
}