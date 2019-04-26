package saddam.com.netcache
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import saddam.com.netcache.model.PostData
import saddam.com.netcache.presenter.PostDataPresenter
import saddam.com.netcache.view.IPostData

class DetailActivity : AppCompatActivity(), IPostData {
    override fun onDataErrorFromApi(throwable: Throwable) {
        Toast.makeText(this,"Anda tidak terhubung ke internet", Toast.LENGTH_SHORT).show()
        finishAffinity()
    }

    override fun onDataCompleteFromApi(postdata: List<PostData>, titles: String) {
            txtTitle.text = postdata[0].title
            txtBody.text = postdata[0].body
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.extras
        val title1 = bundle.getString("title")

        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true

        if(isConnected){
            PostDataPresenter(this).getDataFromApi(title1)
        }else{
            val intent = Intent(this@DetailActivity, MainActivity::class.java)
            Toast.makeText(this,"Anda tidak terhubung ke internet",Toast.LENGTH_SHORT).show()
            this.startActivity(intent)
        }

    }
}
