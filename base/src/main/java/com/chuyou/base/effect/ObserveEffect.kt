package com.chuyou.base.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <E> ObserveEffect(flow: Flow<E>, onEffect: (E) -> Unit) {
    LaunchedEffect(Unit) {
        flow.collect { onEffect(it) }
    }
}