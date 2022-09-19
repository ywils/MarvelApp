package com.android.marvelapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.employeeapp.TestCoroutineRule
import com.android.marvelapp.data.model.ComicResponse
import com.android.marvelapp.data.model.Data
import com.android.marvelapp.data.model.Result
import com.android.marvelapp.data.model.Thumbnail
import com.android.marvelapp.data.repository.ComicRepository
import com.android.marvelapp.ui.ComicViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

class ComicViewModelTest {

    private lateinit var viewModel: ComicViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val repository: ComicRepository = mockk()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = ComicViewModel(repository = repository)
   }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load screen success`()  {
        testCoroutineRule.runBlockingTest {
            val mockResult = mockResponse()

            coEvery { repository.getComic(comicID = 2001) } returns mockResult

            //test
            viewModel.fetchComic(2001)

            //verify
            coVerify { repository.getComic(2001) }

        }

    }

    companion object {
        private fun mockResult() = Result(
            id = 2001,
            title = "TITLE",
            description = "",
            thumbnail = Thumbnail("", ""),
            images = emptyList()
        )

        fun mockResponse() = ComicResponse(
            code = 200,
            status = "ok",
            data = Data(listOf(mockResult()))
        )
    }
}