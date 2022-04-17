package com.notes.miniapp.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.miniapp.model.NoteModel
import com.notes.miniapp.repository.DatabaseRepository
import com.notes.miniapp.utils.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val database: DatabaseRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _listNotesLiveData = MutableLiveData<ResponseData<List<NoteModel>>>()
    val listNotesLiveData: LiveData<ResponseData<List<NoteModel>>>
        get() = _listNotesLiveData

    fun getList() {
        viewModelScope.launch(dispatcher) {
            _listNotesLiveData.postValue(ResponseData.Loading)
            val listNotes = database.getListNotes()
            if (listNotes != null) {
                _listNotesLiveData.postValue(ResponseData.Success(listNotes))
            } else {
                _listNotesLiveData.postValue(
                    ResponseData.Error("Could not get list notes")
                )
            }
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch(dispatcher) {
            _listNotesLiveData.postValue(ResponseData.Loading)
            val isSuccess = database.addNote(text)
            if (isSuccess) {
                getList()
            } else {
                _listNotesLiveData.postValue(
                    ResponseData.Error("Could not add note")
                )
            }
        }
    }

    fun removeNote(note: NoteModel) {
        viewModelScope.launch(dispatcher) {
            _listNotesLiveData.postValue(ResponseData.Loading)
            val isSuccess = database.removeNote(note)
            if (isSuccess) {
                getList()
            } else {
                _listNotesLiveData.postValue(
                    ResponseData.Error("Could not add note")
                )
            }
        }
    }
}
