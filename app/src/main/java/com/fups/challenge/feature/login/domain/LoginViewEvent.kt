package com.fups.challenge.feature.login.domain

sealed class LoginViewEvent {
    object NavigateToMain : LoginViewEvent()
    object GetInfo : LoginViewEvent()
}