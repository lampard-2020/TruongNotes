package com.notes.miniapp.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.notes.miniapp.TestMainCoroutineRule
import com.notes.miniapp.main.list.ListViewModel
import com.notes.miniapp.model.NoteModel
import com.notes.miniapp.model.NoteValueModel
import com.notes.miniapp.repository.DatabaseRepository
import com.notes.miniapp.utils.ResponseData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.util.*


@ExperimentalCoroutinesApi
class ListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestMainCoroutineRule()

    lateinit var databaseRepositoryMock: DatabaseRepository

    @Before
    fun setup() {
        databaseRepositoryMock = Mockito.mock(DatabaseRepository::class.java)
    }

    @Test
    fun getAllRestaurantsSuccess() {
        val viewModel = ListViewModel(databaseRepositoryMock, testDispatcher)

        testDispatcher.runBlockingTest {

            // GIVEN
            val mockData = listOf(
                NoteModel(
                    UUID.randomUUID().toString(),
                    NoteValueModel("Note 1")
                ),
                NoteModel(
                    UUID.randomUUID().toString(),
                    NoteValueModel("Note 2")
                )
            )
            whenever(databaseRepositoryMock.getListNotes()).thenReturn(mockData)

            // WHEN
            viewModel.getList()

            // THEN
            val actual = viewModel.listNotesLiveData.value
            Assert.assertEquals(ResponseData.Success(mockData), actual)
        }
    }

    @Test
    fun getAllRestaurantsFailure() {
        val viewModel = ListViewModel(databaseRepositoryMock, testDispatcher)

        testDispatcher.runBlockingTest {

            // GIVEN
            whenever(databaseRepositoryMock.getListNotes()).thenReturn(null)

            // WHEN
            viewModel.getList()

            // THEN
            val actual = viewModel.listNotesLiveData.value
            Assert.assertEquals(ResponseData.Error("Could not get list notes"), actual)
        }
    }
}
