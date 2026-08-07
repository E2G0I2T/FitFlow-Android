package com.fitflow.feature_auth.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitflow.core_common.TempSession
import com.fitflow.core_domain.repository.UserRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun onLoginClick() {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "이메일과 비밀번호를 모두 입력해주세요") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            delay(1000) // TODO: 백엔드 API 준비되면 core-network의 ApiService 호출로 교체
            userRepository.saveUser(
                id = TempSession.USER_ID,
                nickname = state.email.substringBefore("@"),
                profileImageUrl = null
            )
            _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
        }
    }

    fun onKakaoLoginClick(context: Context) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        val accountCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            handleKakaoResult(token, error)
        }

        val talkCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
                error != null -> {
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = accountCallback)
                }
                else -> {
                    handleKakaoResult(token, error)
                }
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = talkCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = accountCallback)
        }
    }

    private fun handleKakaoResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            android.util.Log.e("KakaoLogin", "카카오 로그인 실패", error)
            _uiState.update { it.copy(isLoading = false, errorMessage = "카카오 로그인에 실패했습니다") }
            return
        }
        if (token == null) return

        UserApiClient.instance.me { user, meError ->
            if (meError != null || user == null) {
                android.util.Log.e("KakaoLogin", "사용자 정보 조회 실패", meError)
                _uiState.update { it.copy(isLoading = false, errorMessage = "사용자 정보를 가져오지 못했습니다") }
                return@me
            }
            viewModelScope.launch {
                userRepository.saveUser(
                    id = TempSession.USER_ID,
                    nickname = user.kakaoAccount?.profile?.nickname ?: "사용자",
                    profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl
                )
                _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
            }
        }
    }
}