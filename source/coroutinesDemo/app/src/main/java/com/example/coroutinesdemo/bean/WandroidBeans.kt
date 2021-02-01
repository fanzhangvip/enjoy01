package com.example.coroutinesdemo.bean

import java.io.Serializable

class ProjectBean {
    /**
     * data : [{"children":[],"courseId":13,"id":294,"name":"完整项目","order":145000,"parentChapterId":293,"userControlSetTop":false,"visible":0},{"children":[],"courseId":13,"id":402,"name":"跨平台应用","order":145001,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":367,"name":"资源聚合类","order":145002,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":323,"name":"动画","order":145003,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":314,"name":"RV列表动效","order":145004,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":358,"name":"项目基础功能","order":145005,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":328,"name":"网络&amp;文件下载","order":145011,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":331,"name":"TextView","order":145013,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":336,"name":"键盘","order":145015,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":337,"name":"快应用","order":145016,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":338,"name":"日历&amp;时钟","order":145017,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":339,"name":"K线图","order":145018,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":340,"name":"硬件相关","order":145019,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":357,"name":"表格类","order":145022,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":363,"name":"创意汇","order":145024,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":380,"name":"ImageView","order":145029,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":382,"name":"音视频&amp;相机","order":145030,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":383,"name":"相机","order":145031,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":310,"name":"下拉刷新","order":145032,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":385,"name":"架构","order":145033,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":387,"name":"对话框","order":145035,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":388,"name":"数据库","order":145036,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":391,"name":"AS插件","order":145037,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":400,"name":"ViewPager","order":145039,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":401,"name":"二维码","order":145040,"parentChapterId":293,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":312,"name":"富文本编辑器","order":145041,"parentChapterId":293,"userControlSetTop":false,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */
    var errorCode = 0
    var errorMsg: String? = null
    var data: List<DataBean>? = null

    override fun toString(): String {
        return "ProjectBean{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}'
    }

    class DataBean {
        /**
         * children : []
         * courseId : 13
         * id : 294
         * name : 完整项目
         * order : 145000
         * parentChapterId : 293
         * userControlSetTop : false
         * visible : 0
         */
        var courseId = 0
        var id = 0
        var name: String? = null
        var order = 0
        var parentChapterId = 0
        var isUserControlSetTop = false
        var visible = 0
        var children: List<*>? = null

        override fun toString(): String {
            return "DataBean{" +
                    "courseId=" + courseId +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", order=" + order +
                    ", parentChapterId=" + parentChapterId +
                    ", userControlSetTop=" + isUserControlSetTop +
                    ", visible=" + visible +
                    ", children=" + children +
                    '}'
        }

    }
}

class ProjectItem {
    /**
     * data : {"curPage":1,"datas":[{"apkLink":"","author":"lulululbj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Github 上关于 Wanandroid 的客户端也层出不穷，Java的，Kotlin 的，Flutter 的，Mvp 的，MVMM 的，各种各样，但是还没看到 Kotlin+MVVM+LiveData+协程 版本的，加上最近正在看 MVVM 和 LiveData，就着手把我之前写的 Mvp 版本的 Wanandroid 改造成 MVVM + Kotlin + LiveData + 协程 版本。","envelopePic":"https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png","fresh":false,"id":8273,"link":"http://www.wanandroid.com/blog/show/2554","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/lulululbj/wanandroid/tree/mvvm-kotlin","publishTime":1555593015000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"真香！Kotlin+MVVM+LiveData+协程 打造 Wanandroid！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"OnexZgj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"该应用程序是玩Android部分api和干货网站部分api的flutter版本的技术类文章查看APP。\r\n主要功能包括：首页、项目、公众号、搜索等。","envelopePic":"https://wanandroid.com/blogimgs/4681d6c0-0d76-4c69-a866-7ad66dde10cd.png","fresh":false,"id":8269,"link":"http://www.wanandroid.com/blog/show/2550","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/OnexZgj/flutter_onex","publishTime":1555592366000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"是时候体验一波Flutter啦","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"dlgchg","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"使用flutter开发的github客户端","envelopePic":"https://wanandroid.com/blogimgs/af4530e7-f244-4b3f-b278-9be41044e811.png","fresh":false,"id":8268,"link":"http://www.wanandroid.com/blog/show/2549","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/dlgchg/flutter_github","publishTime":1555592326000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"使用flutter开发的github客户端","type":0,"userId":-1,"visible":0,"zan":0},{"apkLink":"","author":"xiaotianzhen","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"此app为kotlin开发的android项目，使用了玩安卓的api（网址www.wanandroid.com），界面设计和图标，参考了玩安卓网中的项目。在此，感谢wanandroid网站的支持。该项目采用了mvvm的架构，rxjava+retrofit的网络请求框架等。这是我做的第一个kotlin的项目，欢迎各位大佬指点,欢迎star","envelopePic":"https://wanandroid.com/blogimgs/70030b5f-8f34-4be2-8ed9-1e524398d8c3.png","fresh":false,"id":8267,"link":"http://www.wanandroid.com/blog/show/2548","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/xiaotianzhen/wanandroidkotlin","publishTime":1555592253000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"一个高大上的kotlin玩安卓项目","type":0,"userId":-1,"visible":0,"zan":0},{"apkLink":"","author":"jsyjst","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"WanAndroid是一款基于MVP+Dagger2+Rxjava2+Retrofit+Material Design的应用，目前基本功能已经完成，更多功能还在持续开发中，如发现bug，欢迎来指正。\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n","envelopePic":"https://www.wanandroid.com/blogimgs/e2477aea-c6b2-4b4e-99cf-5e6b963c5134.png","fresh":false,"id":8250,"link":"http://www.wanandroid.com/blog/show/2543","niceDate":"2019-04-15","origin":"","prefix":"","projectLink":"https://github.com/jsyjst/Yuan-WanAndroid","publishTime":1555339627000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"WanAndroid，玩出精彩","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"sunnnydaydev","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"防电商app大部分功能的简单实现。\r\n文章发布：https://blog.csdn.net/qq_38350635/article/details/88830452","envelopePic":"https://www.wanandroid.com/blogimgs/af1cb19d-bde0-434e-b6c6-79a28a75e3ad.png","fresh":false,"id":8145,"link":"http://www.wanandroid.com/blog/show/2540","niceDate":"2019-03-26","origin":"","prefix":"","projectLink":"https://github.com/sunnnydaydev/ModelMall","publishTime":1553615560000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"简单的电商app练习","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"ForgetSky","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"项目基于 Material Design + MVP +dagger2 + RxJava + Retrofit + Glide + greendao 等架构进行设计实现，极力打造一款 优秀的玩Android  https://www.wanandroid.com  客户端，是一个不错的Android应用开发学习参考项目","envelopePic":"https://www.wanandroid.com/blogimgs/796709d5-6238-4fc7-bcbd-6346ea43cf81.png","fresh":false,"id":8120,"link":"http://www.wanandroid.com/blog/show/2538","niceDate":"2019-03-23","origin":"","prefix":"","projectLink":"https://github.com/ForgetSky/ForgetSkyWanAndroid","publishTime":1553342918000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"一款精致的玩Android客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"digtal","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Kotlin + MVP + Rxjava+ Retrofit2写一个玩Android客户端","envelopePic":"https://www.wanandroid.com/blogimgs/cf0d5bc9-50fa-4e8e-b2ff-4b8bc2b0c347.png","fresh":false,"id":8119,"link":"http://www.wanandroid.com/blog/show/2537","niceDate":"2019-03-23","origin":"","prefix":"","projectLink":"https://github.com/digtal/WanAndroid","publishTime":1553342871000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Kotlin玩Android客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"tangedegushi","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"1、使用MVVM框架；\r\n2、使用RxCache对数据进行缓存；\r\n3、网络请求对Rxjava + AutoDispose + ReTrofit + RxCache + Dagger2等封装成一个组件；\r\n4、将一些通用的界面封装到common_ui组件中；\r\n5、各组件间的协调、通用工具类以及通用自定义view等定义在commonlib组件中；\r\n6、各个module都可以单独运行。","envelopePic":"https://wanandroid.com/blogimgs/8d11ad00-ee61-4db7-91d2-9d332761199d.png","fresh":false,"id":8096,"link":"http://www.wanandroid.com/blog/show/2535","niceDate":"2019-03-20","origin":"","prefix":"","projectLink":"https://github.com/tangedegushi/wanandroid","publishTime":1553084449000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"kotlin MVVM组件化玩Android客户端","type":0,"userId":-1,"visible":0,"zan":0},{"apkLink":"","author":"ditclear","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"告别反复、冗余的自定义Adapter，让开发者的重点落在数据上，做到数据驱动UI\r\n\r\n只需要关心Item，编写RecyclerView.Adapter竟然如此简单（重新定义）","envelopePic":"https://www.wanandroid.com/blogimgs/fbea4f7e-0086-4287-a02e-361920f23cfe.png","fresh":false,"id":8094,"link":"http://www.wanandroid.com/blog/show/2534","niceDate":"2019-03-19","origin":"","prefix":"","projectLink":"https://github.com/ditclear/BindingListAdapter","publishTime":1553009142000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"为RecyclerView快速创建Adapter","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"chejdj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"一款每日推荐优质文章的APP,该项目是鸿洋大佬推荐Andorid开发者的一个开源项目，项目中的API为鸿洋大佬提供(API直通车)，此项目基于Java+Material Design+MVP+RxJava2+Retrofit等一些主流框架搭建而成。","envelopePic":"https://www.wanandroid.com/blogimgs/3caa37ed-e194-40d0-b51f-a2e519a393ec.png","fresh":false,"id":8091,"link":"http://www.wanandroid.com/blog/show/2531","niceDate":"2019-03-19","origin":"","prefix":"","projectLink":"https://github.com/chejdj/WanAndroid","publishTime":1553008872000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Java版本的WanAndroid客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"leavesC","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"这是一个纯 Kotlin 的项目，可以用于查看系统安装的所有应用的详细信息，包括应用包名、版本号、apk大小、首次安装时间、最后更新时间、apk路径、签名md5值等\r\n并且可以查看当前系统顶层 Activity 的全路径，方便在反编译应用的时候快速定位路径","envelopePic":"https://www.wanandroid.com/blogimgs/1caa756b-8aae-47f4-88cd-9e2e94bd49dc.png","fresh":false,"id":8078,"link":"http://www.wanandroid.com/blog/show/2528","niceDate":"2019-03-18","origin":"","prefix":"","projectLink":"https://github.com/leavesC/Activity","publishTime":1552923160000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"一个纯 Kotlin 的项目，用于查看当前系统顶层Activity，以及安装的所有应用信息","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"whataa","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"是一个Android第三方开源库。顾名思义，作为开发者的潘多拉魔盒，提供了各种辅助工具用以对各种问题方便快速地进行定位、提升开发和测试效率。Pandora1.0版本于2018年6月发布。围绕解决更多实际开发问题的需求，经过一段时间的开发，近期Pandora2.0正式发布。","envelopePic":"https://www.wanandroid.com/blogimgs/a54f923e-a105-4be9-97db-df7cd30a1d9c.png","fresh":false,"id":8075,"link":"http://www.wanandroid.com/blog/show/2526","niceDate":"2019-03-18","origin":"","prefix":"","projectLink":"https://github.com/whataa/pandora","publishTime":1552922803000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Android开发辅助工具Pandora2.0发布","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"lulululbj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Box，我的开发助手，提供查看当前 Acivity、备份已安装应用 Apk 文件、查看已安装应用 AndroidManifest.xml 文件、查看本机信息等功能。","envelopePic":"https://www.wanandroid.com/blogimgs/18de0ce3-e40a-4cea-a7dd-7d0777040a57.png","fresh":false,"id":8072,"link":"http://www.wanandroid.com/blog/show/2524","niceDate":"2019-03-16","origin":"","prefix":"","projectLink":"https://github.com/lulululbj/Box","publishTime":1552748503000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Box  我的开发助手","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"leavesC","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Monitor 是我刚开发完成的一个开源项目，适用于使用了 OkHttp 作为网络请求框架的项目，可以拦截并缓存应用内的所有 Http 请求和响应信息，且可以以 Notification 和 Activity 的形式来展示具体内容","envelopePic":"https://www.wanandroid.com/blogimgs/e8f85c9f-9db3-4988-9e52-a990cb0967d2.png","fresh":false,"id":8071,"link":"http://www.wanandroid.com/blog/show/2523","niceDate":"2019-03-16","origin":"","prefix":"","projectLink":"https://github.com/leavesC/Monitor","publishTime":1552748272000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Android OkHttp 网络请求调试利器 - Monitor","type":0,"userId":-1,"visible":1,"zan":0}],"offset":0,"over":false,"pageCount":9,"size":15,"total":135}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    override fun toString(): String {
        return "ProjectItem{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}'
    }

    class DataBean {
        /**
         * curPage : 1
         * datas : [{"apkLink":"","author":"lulululbj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Github 上关于 Wanandroid 的客户端也层出不穷，Java的，Kotlin 的，Flutter 的，Mvp 的，MVMM 的，各种各样，但是还没看到 Kotlin+MVVM+LiveData+协程 版本的，加上最近正在看 MVVM 和 LiveData，就着手把我之前写的 Mvp 版本的 Wanandroid 改造成 MVVM + Kotlin + LiveData + 协程 版本。","envelopePic":"https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png","fresh":false,"id":8273,"link":"http://www.wanandroid.com/blog/show/2554","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/lulululbj/wanandroid/tree/mvvm-kotlin","publishTime":1555593015000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"真香！Kotlin+MVVM+LiveData+协程 打造 Wanandroid！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"OnexZgj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"该应用程序是玩Android部分api和干货网站部分api的flutter版本的技术类文章查看APP。\r\n主要功能包括：首页、项目、公众号、搜索等。","envelopePic":"https://wanandroid.com/blogimgs/4681d6c0-0d76-4c69-a866-7ad66dde10cd.png","fresh":false,"id":8269,"link":"http://www.wanandroid.com/blog/show/2550","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/OnexZgj/flutter_onex","publishTime":1555592366000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"是时候体验一波Flutter啦","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"dlgchg","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"使用flutter开发的github客户端","envelopePic":"https://wanandroid.com/blogimgs/af4530e7-f244-4b3f-b278-9be41044e811.png","fresh":false,"id":8268,"link":"http://www.wanandroid.com/blog/show/2549","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/dlgchg/flutter_github","publishTime":1555592326000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"使用flutter开发的github客户端","type":0,"userId":-1,"visible":0,"zan":0},{"apkLink":"","author":"xiaotianzhen","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"此app为kotlin开发的android项目，使用了玩安卓的api（网址www.wanandroid.com），界面设计和图标，参考了玩安卓网中的项目。在此，感谢wanandroid网站的支持。该项目采用了mvvm的架构，rxjava+retrofit的网络请求框架等。这是我做的第一个kotlin的项目，欢迎各位大佬指点,欢迎star","envelopePic":"https://wanandroid.com/blogimgs/70030b5f-8f34-4be2-8ed9-1e524398d8c3.png","fresh":false,"id":8267,"link":"http://www.wanandroid.com/blog/show/2548","niceDate":"2019-04-18","origin":"","prefix":"","projectLink":"https://github.com/xiaotianzhen/wanandroidkotlin","publishTime":1555592253000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"一个高大上的kotlin玩安卓项目","type":0,"userId":-1,"visible":0,"zan":0},{"apkLink":"","author":"jsyjst","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"WanAndroid是一款基于MVP+Dagger2+Rxjava2+Retrofit+Material Design的应用，目前基本功能已经完成，更多功能还在持续开发中，如发现bug，欢迎来指正。\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n","envelopePic":"https://www.wanandroid.com/blogimgs/e2477aea-c6b2-4b4e-99cf-5e6b963c5134.png","fresh":false,"id":8250,"link":"http://www.wanandroid.com/blog/show/2543","niceDate":"2019-04-15","origin":"","prefix":"","projectLink":"https://github.com/jsyjst/Yuan-WanAndroid","publishTime":1555339627000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"WanAndroid，玩出精彩","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"sunnnydaydev","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"防电商app大部分功能的简单实现。\r\n文章发布：https://blog.csdn.net/qq_38350635/article/details/88830452","envelopePic":"https://www.wanandroid.com/blogimgs/af1cb19d-bde0-434e-b6c6-79a28a75e3ad.png","fresh":false,"id":8145,"link":"http://www.wanandroid.com/blog/show/2540","niceDate":"2019-03-26","origin":"","prefix":"","projectLink":"https://github.com/sunnnydaydev/ModelMall","publishTime":1553615560000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"简单的电商app练习","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"ForgetSky","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"项目基于 Material Design + MVP +dagger2 + RxJava + Retrofit + Glide + greendao 等架构进行设计实现，极力打造一款 优秀的玩Android  https://www.wanandroid.com  客户端，是一个不错的Android应用开发学习参考项目","envelopePic":"https://www.wanandroid.com/blogimgs/796709d5-6238-4fc7-bcbd-6346ea43cf81.png","fresh":false,"id":8120,"link":"http://www.wanandroid.com/blog/show/2538","niceDate":"2019-03-23","origin":"","prefix":"","projectLink":"https://github.com/ForgetSky/ForgetSkyWanAndroid","publishTime":1553342918000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"一款精致的玩Android客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"digtal","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Kotlin + MVP + Rxjava+ Retrofit2写一个玩Android客户端","envelopePic":"https://www.wanandroid.com/blogimgs/cf0d5bc9-50fa-4e8e-b2ff-4b8bc2b0c347.png","fresh":false,"id":8119,"link":"http://www.wanandroid.com/blog/show/2537","niceDate":"2019-03-23","origin":"","prefix":"","projectLink":"https://github.com/digtal/WanAndroid","publishTime":1553342871000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Kotlin玩Android客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"tangedegushi","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"1、使用MVVM框架；\r\n2、使用RxCache对数据进行缓存；\r\n3、网络请求对Rxjava + AutoDispose + ReTrofit + RxCache + Dagger2等封装成一个组件；\r\n4、将一些通用的界面封装到common_ui组件中；\r\n5、各组件间的协调、通用工具类以及通用自定义view等定义在commonlib组件中；\r\n6、各个module都可以单独运行。","envelopePic":"https://wanandroid.com/blogimgs/8d11ad00-ee61-4db7-91d2-9d332761199d.png","fresh":false,"id":8096,"link":"http://www.wanandroid.com/blog/show/2535","niceDate":"2019-03-20","origin":"","prefix":"","projectLink":"https://github.com/tangedegushi/wanandroid","publishTime":1553084449000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"kotlin MVVM组件化玩Android客户端","type":0,"userId":-1,"visible":0,"zan":0},{"apkLink":"","author":"ditclear","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"告别反复、冗余的自定义Adapter，让开发者的重点落在数据上，做到数据驱动UI\r\n\r\n只需要关心Item，编写RecyclerView.Adapter竟然如此简单（重新定义）","envelopePic":"https://www.wanandroid.com/blogimgs/fbea4f7e-0086-4287-a02e-361920f23cfe.png","fresh":false,"id":8094,"link":"http://www.wanandroid.com/blog/show/2534","niceDate":"2019-03-19","origin":"","prefix":"","projectLink":"https://github.com/ditclear/BindingListAdapter","publishTime":1553009142000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"为RecyclerView快速创建Adapter","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"chejdj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"一款每日推荐优质文章的APP,该项目是鸿洋大佬推荐Andorid开发者的一个开源项目，项目中的API为鸿洋大佬提供(API直通车)，此项目基于Java+Material Design+MVP+RxJava2+Retrofit等一些主流框架搭建而成。","envelopePic":"https://www.wanandroid.com/blogimgs/3caa37ed-e194-40d0-b51f-a2e519a393ec.png","fresh":false,"id":8091,"link":"http://www.wanandroid.com/blog/show/2531","niceDate":"2019-03-19","origin":"","prefix":"","projectLink":"https://github.com/chejdj/WanAndroid","publishTime":1553008872000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Java版本的WanAndroid客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"leavesC","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"这是一个纯 Kotlin 的项目，可以用于查看系统安装的所有应用的详细信息，包括应用包名、版本号、apk大小、首次安装时间、最后更新时间、apk路径、签名md5值等\r\n并且可以查看当前系统顶层 Activity 的全路径，方便在反编译应用的时候快速定位路径","envelopePic":"https://www.wanandroid.com/blogimgs/1caa756b-8aae-47f4-88cd-9e2e94bd49dc.png","fresh":false,"id":8078,"link":"http://www.wanandroid.com/blog/show/2528","niceDate":"2019-03-18","origin":"","prefix":"","projectLink":"https://github.com/leavesC/Activity","publishTime":1552923160000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"一个纯 Kotlin 的项目，用于查看当前系统顶层Activity，以及安装的所有应用信息","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"whataa","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"是一个Android第三方开源库。顾名思义，作为开发者的潘多拉魔盒，提供了各种辅助工具用以对各种问题方便快速地进行定位、提升开发和测试效率。Pandora1.0版本于2018年6月发布。围绕解决更多实际开发问题的需求，经过一段时间的开发，近期Pandora2.0正式发布。","envelopePic":"https://www.wanandroid.com/blogimgs/a54f923e-a105-4be9-97db-df7cd30a1d9c.png","fresh":false,"id":8075,"link":"http://www.wanandroid.com/blog/show/2526","niceDate":"2019-03-18","origin":"","prefix":"","projectLink":"https://github.com/whataa/pandora","publishTime":1552922803000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Android开发辅助工具Pandora2.0发布","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"lulululbj","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Box，我的开发助手，提供查看当前 Acivity、备份已安装应用 Apk 文件、查看已安装应用 AndroidManifest.xml 文件、查看本机信息等功能。","envelopePic":"https://www.wanandroid.com/blogimgs/18de0ce3-e40a-4cea-a7dd-7d0777040a57.png","fresh":false,"id":8072,"link":"http://www.wanandroid.com/blog/show/2524","niceDate":"2019-03-16","origin":"","prefix":"","projectLink":"https://github.com/lulululbj/Box","publishTime":1552748503000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Box  我的开发助手","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"leavesC","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"Monitor 是我刚开发完成的一个开源项目，适用于使用了 OkHttp 作为网络请求框架的项目，可以拦截并缓存应用内的所有 Http 请求和响应信息，且可以以 Notification 和 Activity 的形式来展示具体内容","envelopePic":"https://www.wanandroid.com/blogimgs/e8f85c9f-9db3-4988-9e52-a990cb0967d2.png","fresh":false,"id":8071,"link":"http://www.wanandroid.com/blog/show/2523","niceDate":"2019-03-16","origin":"","prefix":"","projectLink":"https://github.com/leavesC/Monitor","publishTime":1552748272000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Android OkHttp 网络请求调试利器 - Monitor","type":0,"userId":-1,"visible":1,"zan":0}]
         * offset : 0
         * over : false
         * pageCount : 9
         * size : 15
         * total : 135
         */
        var curPage = 0
        var offset = 0
        var isOver = false
        var pageCount = 0
        var size = 0
        var total = 0
        var datas: List<DatasBean>? = null

        override fun toString(): String {
            return "DataBean{" +
                    "curPage=" + curPage +
                    ", offset=" + offset +
                    ", over=" + isOver +
                    ", pageCount=" + pageCount +
                    ", size=" + size +
                    ", total=" + total +
                    ", datas=" + datas +
                    '}'
        }

        class DatasBean {
            /**
             * apkLink :
             * author : lulululbj
             * chapterId : 294
             * chapterName : 完整项目
             * collect : false
             * courseId : 13
             * desc : Github 上关于 Wanandroid 的客户端也层出不穷，Java的，Kotlin 的，Flutter 的，Mvp 的，MVMM 的，各种各样，但是还没看到 Kotlin+MVVM+LiveData+协程 版本的，加上最近正在看 MVVM 和 LiveData，就着手把我之前写的 Mvp 版本的 Wanandroid 改造成 MVVM + Kotlin + LiveData + 协程 版本。
             * envelopePic : https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png
             * fresh : false
             * id : 8273
             * link : http://www.wanandroid.com/blog/show/2554
             * niceDate : 2019-04-18
             * origin :
             * prefix :
             * projectLink : https://github.com/lulululbj/wanandroid/tree/mvvm-kotlin
             * publishTime : 1555593015000
             * superChapterId : 294
             * superChapterName : 开源项目主Tab
             * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
             * title : 真香！Kotlin+MVVM+LiveData+协程 打造 Wanandroid！
             * type : 0
             * userId : -1
             * visible : 1
             * zan : 0
             */
            var apkLink: String? = null
            var author: String? = null
            var chapterId = 0
            var chapterName: String? = null
            var isCollect = false
            var courseId = 0
            var desc: String? = null
            var envelopePic: String? = null
            var isFresh = false
            var id = 0
            var link: String? = null
            var niceDate: String? = null
            var origin: String? = null
            var prefix: String? = null
            var projectLink: String? = null
            var publishTime: Long = 0
            var superChapterId = 0
            var superChapterName: String? = null
            var title: String? = null
            var type = 0
            var userId = 0
            var visible = 0
            var zan = 0
            var tags: List<TagsBean>? = null

            override fun toString(): String {
                return "DatasBean{" +
                        "apkLink='" + apkLink + '\'' +
                        ", author='" + author + '\'' +
                        ", chapterId=" + chapterId +
                        ", chapterName='" + chapterName + '\'' +
                        ", collect=" + isCollect +
                        ", courseId=" + courseId +
                        ", desc='" + desc + '\'' +
                        ", envelopePic='" + envelopePic + '\'' +
                        ", fresh=" + isFresh +
                        ", id=" + id +
                        ", link='" + link + '\'' +
                        ", niceDate='" + niceDate + '\'' +
                        ", origin='" + origin + '\'' +
                        ", prefix='" + prefix + '\'' +
                        ", projectLink='" + projectLink + '\'' +
                        ", publishTime=" + publishTime +
                        ", superChapterId=" + superChapterId +
                        ", superChapterName='" + superChapterName + '\'' +
                        ", title='" + title + '\'' +
                        ", type=" + type +
                        ", userId=" + userId +
                        ", visible=" + visible +
                        ", zan=" + zan +
                        ", tags=" + tags +
                        '}'
            }

            class TagsBean {
                /**
                 * name : 项目
                 * url : /project/list/1?cid=294
                 */
                var name: String? = null
                var url: String? = null

                override fun toString(): String {
                    return "TagsBean{" +
                            "name='" + name + '\'' +
                            ", url='" + url + '\'' +
                            '}'
                }

            }
        }
    }
}

class BaseResponse<T> : Serializable {
    var errorCode = 0
    var errorMsg: String? = null
    var data: T? = null
        private set

    constructor() {}
    constructor(errorCode: Int, errorMsg: String?, data: T) {
        this.errorCode = errorCode
        this.errorMsg = errorMsg
        this.data = data
    }

    fun setData(data: T) {
        this.data = data
    }

    override fun toString(): String {
        return "BaseResponse{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}'
    }
}
