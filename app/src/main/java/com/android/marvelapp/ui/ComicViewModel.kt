package com.android.marvelapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.marvelapp.data.network.Resource
import com.android.marvelapp.data.model.Result
import com.android.marvelapp.data.repository.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val repository: ComicRepository
) : ViewModel() {

    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _comic = MutableLiveData<Resource<Result>>()
    val comics: LiveData<Resource<Result>>
        get() = _comic

    init {
        fetchComic()
    }

    fun fetchComic(comicId: Int = 1003) {
        viewModelScope.launch {
            try {
                _spinner.postValue(true)
                _comic.postValue(Resource.Loading)
                val res = repository.getComic(comicId)
                _comic.postValue(Resource.Success(res.data.results.first()))
            } catch (error: Exception) {
                _comic.postValue(Resource.Error(error))
            } finally {
                _spinner.postValue(false)
            }
        }
    }

}