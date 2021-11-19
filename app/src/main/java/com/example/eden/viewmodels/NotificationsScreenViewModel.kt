package com.example.eden.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eden.data.Repository
import com.example.eden.data.domain.NotificationInfo

interface NotificationsScreenViewModel {
    val notificationList: LiveData<List<NotificationInfo>>
}

class EdenNotificationsScreenViewmodel(
    private val repository: Repository
) : ViewModel(), NotificationsScreenViewModel {
    override val notificationList: LiveData<List<NotificationInfo>>
        get() = TODO("Not yet implemented")

}
