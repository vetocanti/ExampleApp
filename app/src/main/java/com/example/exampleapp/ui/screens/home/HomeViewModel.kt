package com.example.exampleapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersVM : ViewModel() {
    private val _users = MutableStateFlow<List<String>>(emptyList())
    val users = _users.asStateFlow()
    init {
        viewModelScope.launch {
            _users.value = listOf("Ada", "Linus", "Grace")
        }
    }
}