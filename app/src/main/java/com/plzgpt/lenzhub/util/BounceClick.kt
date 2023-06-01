package com.plzgpt.lenzhub.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.pow

fun Modifier.bounceClick(onClick: () -> Unit) = composed {

    val interactionSource = MutableInteractionSource()

    val coroutineScope = rememberCoroutineScope()

    val scale = remember {
        Animatable(1f)
    }
    val scaleDown = 0.95f
    val animationDuration = 150
    val mContext = LocalContext.current

    val colorOverlay = LocalElevationOverlay.current?.apply(
        color = Color.Transparent,
        elevation = 0.dp
    ) ?: Color.Transparent

    this
        .drawBehind {
            drawRect(color = colorOverlay)
        }
        .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = {}
        )
        .alpha(
            (0.0012)
                .pow((1 - scale.value).toDouble())
                .toFloat()
        )
        .clickable(
            interactionSource = interactionSource, indication = null, onClick = {})
        .pointerInput(Unit) {
            while (true)
                awaitPointerEventScope {
                    awaitFirstDown()
                    coroutineScope.launch {
                        scale.animateTo(
                            scaleDown,
                            animationSpec = tween(animationDuration),
                        )
                    }
                    val up = waitForUpOrCancellation()
                    coroutineScope.launch {
                        scale.animateTo(
                            scaleDown,
                            animationSpec = tween(40),
                        )
                        scale.animateTo(
                            1f,
                            animationSpec = tween(animationDuration),
                        )
                    }
                    if (up.toString() != "null") {
                        onClick.invoke()
                    }
                }
        }
}