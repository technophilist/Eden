package com.example.eden.utils


/**
 * Returns true if the string contains at least one uppercase letter.
 * Else, returns false.
 */
fun String.containsUppercase() = any { it.isUpperCase() }

/**
 * Returns true if the string contains at least one digit.
 * Else, returns false.
 */
fun String.containsDigit() = any { it.isDigit() }

/**
 * Returns true if the string contains at least one lowercase letter.
 * Else, returns false.
 */
fun String.containsLowercase() = any { it.isLowerCase() }