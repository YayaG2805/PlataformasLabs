package com.example.lab08.ui.navigation

// Helper visible para todo el package (y otros si quieres)
inline fun <reified T> routeOf(): String = T::class.qualifiedName!!
