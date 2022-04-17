package com.notes.miniapp.main.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notes.miniapp.model.UserModel
import com.notes.miniapp.repository.AuthenticationRepository
import com.notes.miniapp.repository.DatabaseRepository
import com.notes.miniapp.utils.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authentication: AuthenticationRepository,
    private val database: DatabaseRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _signUpLiveData = MutableLiveData<ResponseData<Boolean>>()

    val signUpLiveData: LiveData<ResponseData<Boolean>>
        get() = _signUpLiveData

    fun register(email: String, password: String, userName: String) {
        viewModelScope.launch(dispatcher) {
            _signUpLiveData.postValue(ResponseData.Loading)
            val isSuccess = authentication.signUp(email, password)
            if (isSuccess) {
                val result = database.updateUserInfo(UserModel(userName, email))
                if (result) {
                    _signUpLiveData.postValue(ResponseData.Success(true))
                } else {
                    _signUpLiveData.postValue(ResponseData.Error("Update Username failure!"))
                }
            } else {
                _signUpLiveData.postValue(ResponseData.Error("Could not signup!"))
            }
        }
    }
}
