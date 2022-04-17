package com.notes.miniapp.main.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.miniapp.repository.AuthenticationRepository
import com.notes.miniapp.utils.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authentication: AuthenticationRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _signInLiveData = MutableLiveData<ResponseData<Boolean>>()

    val signInLiveData: LiveData<ResponseData<Boolean>>
        get() = _signInLiveData

    fun login(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            _signInLiveData.postValue(ResponseData.Loading)
            val isSuccess = authentication.signIn(email, password)
            if (isSuccess) {
                _signInLiveData.postValue(ResponseData.Success(true))
            } else {
                _signInLiveData.postValue(ResponseData.Error("Could not login!"))
            }
        }
    }
}
