package saddam.com.netcache
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import saddam.com.netcache.model.PostData
import saddam.com.netcache.presenter.PostDataPresenter
import saddam.com.netcache.view.IPostData
import android.content.Intent
import android.os.Handler
import android.view.View
import saddam.com.netcache.sharepreference.SharedPreference


class MainActivity : AppCompatActivity(), IPostData {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onResume()
        setContentView(R.layout.activity_main)


        PostDataPresenter(this).getDataFromApi("")


        txtFilter.setOnKeyListener { _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                PostDataPresenter(this).getDataFromApi(txtFilter.text.toString())
            }
            false
        }


        ListPostData.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            val title = ""+ ListPostData.getItemAtPosition(position)
            intent.putExtra("title", title)
            this.startActivity(intent)
        }

        swipe_refresh_layout.setOnRefreshListener {
            Handler().postDelayed({
                swipe_refresh_layout.isRefreshing = false
                PostDataPresenter(this).getDataFromApi("")
            }, 1000)
        }

    }

    override fun onDataCompleteFromApi(postdata: List<PostData>, titles: String) {
        if (title=="") {
            txtPullRefresh.text = ""
            val list = arrayListOf<String>()
            postdata.forEachIndexed { _, postData -> list.add(postData.title) }

            val sortedList = list.sorted()

            val sSortedLIst = sortedList.toString()
            val sharedPreference = SharedPreference(this)
            sharedPreference.save("listdata", sSortedLIst)

            ListPostData.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedList)
            progressBar.visibility = View.INVISIBLE
        }else{
            txtPullRefresh.text = ""
            val list = arrayListOf<String>()
            postdata.forEachIndexed { _, postData -> list.add(postData.title) }
            val sortedList = list.sorted()
            ListPostData.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedList)
            progressBar.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onDataErrorFromApi(throwable: Throwable) {
        Toast.makeText(this,"Anda tidak terhubung ke internet",Toast.LENGTH_SHORT).show()
        txtPullRefresh.text = "Please pull to refresh"
        val sharedPreference = SharedPreference(this)
        val sSortedList = sharedPreference.getValueString("listdata")

        val items = sSortedList!!.replace("\\[".toRegex(), "").replace("]".toRegex(), "").split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        ListPostData.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)


    }


}

