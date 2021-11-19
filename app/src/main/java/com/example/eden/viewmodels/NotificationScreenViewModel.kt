package com.example.eden.viewmodels

import androidx.lifecycle.LiveData
import com.example.eden.data.Repository
import com.example.eden.data.domain.NotificationInfo

interface NotificationScreenViewModel {
    val notificationList: LiveData<List<NotificationInfo>>
}

class EdenNotificationScreenViewmodel(
    private val repository: Repository
) : NotificationScreenViewModel {
    override val notificationList: LiveData<List<NotificationInfo>>
        get() = TODO("Not yet implemented")

}
