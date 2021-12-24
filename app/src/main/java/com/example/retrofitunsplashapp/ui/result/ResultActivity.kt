package com.example.retrofitunsplashapp.ui.result

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitunsplashapp.Constants.TAG
import com.example.retrofitunsplashapp.R
import com.example.retrofitunsplashapp.databinding.ActivityResultBinding
import com.example.retrofitunsplashapp.model.Photo
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ResultActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var mBinding : ActivityResultBinding
    lateinit var viewModel : ResultViewModel
    lateinit var adapter : ResultRecyclerViewAdapter
    lateinit var photoList : ArrayList<Photo>
    private lateinit var searchTerm : String
    private lateinit var mySearchView : SearchView

    private var compositeDisposable  = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_result)
        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)
        getApiResult()
        settingRecyclerView()
        setSupportActionBar(mBinding.topAppBarBoardSearchMenu)
    }
    fun settingRecyclerView(){
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        this.adapter = ResultRecyclerViewAdapter(photoList)
        mBinding.recyclerView.adapter=  this.adapter
    }
    fun getApiResult(){
        var bundle = intent.getBundleExtra("data")
        this.photoList = bundle!!.getSerializable("data") as ArrayList<Photo>
        this.searchTerm = intent.getStringExtra("searchTerm")!!
        Log.d(TAG, "getApiResult: ${searchTerm}")
        mBinding.topAppBarBoardSearchMenu.title = this.searchTerm
        Log.d(TAG, "getApiResult: ${photoList.size}")
    }


    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
            menuInflater.inflate(R.menu.top_app_bar_menu,menu)
        val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        this.mySearchView = menu?.findItem(R.id.searchview_btn)?.actionView as SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해주세요"
            this.setQuery(searchTerm,false)
            this.setOnQueryTextListener(this@ResultActivity)
            this.setOnQueryTextFocusChangeListener { _, hasExpaned ->
                when (hasExpaned) {
                    true -> {
                        Log.d(TAG, "서치뷰 열림")
                    }
                    false -> {
                        Log.d(TAG, "서치뷰 닫힘")
                    }
                }
            }
            val id: Int = this.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null)
            var mySearchViewEditText = this.findViewById(id) as EditText
            //서치뷰 텍스트 옵저버블
            val editTextChangeObservable = mySearchViewEditText.textChanges()

            val searchEditTextSubscription : Disposable =
                editTextChangeObservable.debounce(1000,TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribeBy(
                        onNext = {
                            Log.d(TAG, "onNext : $it")
                            if(it.toString().length>=2) {
                                viewModel.searchPhoto(it.toString(), completion = {
                                    photoList = it!!
                                    adapter.updateList(photoList)
                                })
                            }

                        },
                        onComplete = {
                            Log.d(TAG, "onComplete")
                        },
                        onError = {
                            Log.d(TAG, "onError")
                        }
                    )
            compositeDisposable.add(searchEditTextSubscription)
        }
        return super.onCreateOptionsMenu(menu)
        }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "onQueryTextSubmit: called")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "onQueryTextChange: ${newText}")
        var inputText = newText.let{
            it
        }?:""
        return true
    }

    override fun onDestroy() {
        this.compositeDisposable.clear()
        super.onDestroy()
    }

}
