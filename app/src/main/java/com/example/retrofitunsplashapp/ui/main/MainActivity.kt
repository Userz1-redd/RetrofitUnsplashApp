package com.example.retrofitunsplashapp.ui.main


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitunsplashapp.R
import com.example.retrofitunsplashapp.databinding.ActivityMainBinding
import com.example.retrofitunsplashapp.ui.result.ResultActivity
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private val TAG = "로그"
    private var searchType : String = "photoSearch"
    lateinit var mainViewModel: MainViewModel
    lateinit var mBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.viewModel = mainViewModel
        mBinding.searchRadioGroup.setOnCheckedChangeListener(this)
        mBinding.searchBtn.setOnClickListener(this)
        mainViewModel.search_term.observe(this, Observer{
            mBinding.searchText.setText(it.toString())
        })
        mBinding.viewModel = mainViewModel
    }

    override fun onCheckedChanged(group: RadioGroup?, id: Int) {
        when(group){
            mBinding.searchRadioGroup ->{
                when(id){
                    R.id.search_photo_rd ->{
                        Log.d(TAG, "사진검색 클릭")
                        mBinding.searchTextLayout.hint = "사진검색"
                        this.searchType = "photoSearch"
                    }
                    R.id.search_username_rd ->{
                        Log.d(TAG, "사용자검색 클릭")
                        mBinding.searchTextLayout.hint = "사용자검색"
                        this.searchType = "usernameSearch"
                    }
                }

            }
        }
    }
    override fun onClick(v: View?) {
        when(v) {
            mBinding.searchBtn -> mainViewModel.searchPhoto(mBinding.searchText.text.toString(), completion = {it ->
                var intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("data",it)
                intent.putExtra("searchTerm",mBinding.searchText.text.toString())
                startActivity(intent)
            })
        }
    }
}