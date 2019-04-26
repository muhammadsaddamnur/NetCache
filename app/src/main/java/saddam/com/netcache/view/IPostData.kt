package saddam.com.netcache.view

import saddam.com.netcache.model.PostData

interface IPostData {
    fun onDataCompleteFromApi(postdata: List<PostData>, titles: String)
    fun onDataErrorFromApi(throwable: Throwable)
}