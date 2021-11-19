package com.example.eden.data.domain


/**
 * This class holds all information that is related to a notification.
 */
data class NotificationInfo(
    val id: Int,
    val type: NotificationType,
    val header: String,
    val content: String,
    val urlString: String? = null
) {
    enum class NotificationType { ORDERS, APPOINTMENTS, NGO }
}