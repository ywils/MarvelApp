package com.android.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.text.isDigitsOnly
import com.android.marvelapp.data.model.Result
import com.android.marvelapp.data.model.Thumbnail
import com.android.marvelapp.data.network.Resource
import com.android.marvelapp.databinding.ActivityMainBinding
import com.android.marvelapp.idling.FetcherListener
import com.android.marvelapp.idling.FetchingIdlingResource
import com.android.marvelapp.ui.ComicUtil
import com.android.marvelapp.ui.ComicViewModel
import com.android.marvelapp.ui.UiUtil
import com.android.marvelapp.ui.UiUtil.hideKeyboard
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ComicViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var fetcherListener: FetcherListener? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fetcherListener = FetchingIdlingResource()
        startObservers()
    }

    private fun startObservers() {
        viewModel.comics.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    fetcherListener?.doneFetching()
                    setView(resource.data)
                }
                is Resource.Loading -> {}

                is Resource.Error -> {
                    fetcherListener?.doneFetching()
                    Toast.makeText(this, "Comic Not Found", Toast.LENGTH_SHORT).show()
                }
            }


        }

        viewModel.spinner.observe(this) { res ->
            binding.prog.visibility = if (res) View.VISIBLE else View.GONE
        }
    }

    private fun prepareDesc(dsc: String?) : String {
        return if (dsc?.isNullOrEmpty() == true) "No description available" else dsc.replace("<br>", "")
    }

    private fun setView(result: Result) {
        binding.comicTitle.text = result.title
        binding.comicDsc.text = prepareDesc(result.description)
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {
                return true
            }
            override fun onQueryTextSubmit(comicID: String): Boolean {
                return if (comicID.isDigitsOnly()) {
                    // idling resource listener for espresso test
                    fetcherListener?.beginFetching()
                    viewModel.fetchComic(comicId = comicID.toInt())
                    hideKeyboard()
                    true
                } else {
                    false
                }
            }
        })

        loadImage(result.thumbnail, binding.mainImage)
        loadImageWithBlur(result.thumbnail, binding.baseImage)

    }

    private fun loadImage(thumbnail: Thumbnail, view: ImageView) {
        Glide.with(binding.root.context)
            .load(ComicUtil.prepareImageThumbnail(thumbnail))
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_baseline_error_24)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .fitCenter()
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    private fun loadImageWithBlur(thumbnail: Thumbnail, view: ImageView) {
        Glide.with(binding.root.context)
            .load(ComicUtil.prepareImageThumbnail(thumbnail))
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_baseline_error_24)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .fitCenter()
            )
            .apply(bitmapTransform(BlurTransformation(25, 3)))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    fun setFetcherListener(fetcherListener: FetcherListener) {
        this.fetcherListener = fetcherListener
    }
}