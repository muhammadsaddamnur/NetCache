package saddam.com.netcache.service

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import saddam.com.netcache.model.PostData

interface RetrofitService {

    @GET("posts")
    fun getApi() : Call<List<PostData>>

    @GET("posts")
    fun getApiFilter(@Query("title") titles:String) : Call<List<PostData>>



    companion object {

        fun create(): RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .build()
            return retrofit.create(RetrofitService::class.java)
        }


    }
}