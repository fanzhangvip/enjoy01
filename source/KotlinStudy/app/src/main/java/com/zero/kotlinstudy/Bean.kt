package com.zero.kotlinstudy

data class BaseResponse<T>(var errorCode: Int, var errorMsg: String, var data: T)

data class ProjectBean(
    var errorCode: Int = 0,
    var errorMsg: String? = null,
    var data: List<ProjectDataBean>? = null
)

data class ProjectDataBean(
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String? = null,
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var isUserControlSetTop: Boolean = false,
    var visible: Int = 0,
    var children: List<*>? = null
)

data class ProjectItem(
    var data: DataBean? = null,
    var errorCode: Int = 0,
    var errorMsg: String? = null
)


data class DataBean(
    var curPage: Int = 0,
    var offset: Int = 0,
    var isOver: Boolean = false,
    var pageCount: Int = 0,
    var size: Int = 0,
    var total: Int = 0,
    var datas: List<DatasBean>? = null
)


class DatasBean(
    var apkLink: String? = null,
    var author: String? = null,
    var chapterId: Int = 0,
    var chapterName: String? = null,
    var isCollect: Boolean = false,
    var courseId: Int = 0,
    var desc: String? = null,
    var envelopePic: String? = null,
    var isFresh: Boolean = false,
    var id: Int = 0,
    var link: String? = null,
    var niceDate: String? = null,
    var origin: String? = null,
    var prefix: String? = null,
    var projectLink: String? = null,
    var publishTime: Long = 0,
    var superChapterId: Int = 0,
    var superChapterName: String? = null,
    var title: String? = null,
    var type: Int = 0,
    var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0,
    var tags: List<TagsBean>? = null
)

data class TagsBean(
    var name: String? = null,
    var url: String? = null

)




