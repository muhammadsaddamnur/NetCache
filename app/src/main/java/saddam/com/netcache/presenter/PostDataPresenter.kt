package saddam.com.netcache.presenter

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import saddam.com.netcache.model.PostData
import saddam.com.netcache.service.RetrofitService
import saddam.com.netcache.view.IPostData

class PostDataPresenter(context: Context) {
    val postdataView = context as IPostData

    fun getDataFromApi(titles: String){
        if (titles == "") {
            RetrofitService.create()
                .getApi()
                .enqueue(object : Callback<List<PostData>> {
                    override fun onFailure(call: Call<List<PostData>>, t: Throwable) {
                        postdataView.onDataErrorFromApi(t)
                    }

                    override fun onResponse(call: Call<List<PostData>>?, response: Response<List<PostData>>) {
                        postdataView.onDataCompleteFromApi(response.body()!!, titles)
                    }
                })
        }else{
            RetrofitService.create()
                .getApiFilter(titles)
                .enqueue(object : Callback<List<PostData>>{
                    override fun onFailure(call: Call<List<PostData>>, t: Throwable) {
                        postdataView.onDataErrorFromApi(t)
                    }

                    override fun onResponse(call: Call<List<PostData>>?, response: Response<List<PostData>>) {
                        postdataView.onDataCompleteFromApi(response.body()!!, titles)
                    }
                })
        }
    }

}

