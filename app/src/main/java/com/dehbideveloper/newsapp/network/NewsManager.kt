package com.dehbideveloper.newsapp.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.dehbideveloper.newsapp.models.ArticleCategory
import com.dehbideveloper.newsapp.models.TopNewsResponse
import com.dehbideveloper.newsapp.models.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _getArticlesByCategory = mutableStateOf(TopNewsResponse())
    val getArticlesByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticlesByCategory
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles(){
        val service = Api.retrofitService.getTopArticles("us", Api.API_KEY)
        service.enqueue(object : Callback<TopNewsResponse>{
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _newsResponse.value = response.body()!!
                    Log.d("news", "${_newsResponse.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun getArticlesByCategory(category: String){
        val service = Api.retrofitService.getArticlesByCategory(category, Api.API_KEY)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _getArticlesByCategory.value = response.body()!!
                    Log.d("Article_By_Category", "${_getArticlesByCategory.value}")
                } else {
                    Log.d("error", response.message())
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.message}")
            }

        } )
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}