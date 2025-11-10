package com.judahben149.eclair.presentation.screens.studio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.judahben149.eclair.navigation.StudioComponent

@Composable
fun StudioScreen(component: StudioComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(fade() + scale())
    ) { child ->
        when (child.instance) {
            is StudioComponent.Child.Home -> StudioHomeScreen(component)
            is StudioComponent.Child.AskQuestion -> AskQuestionScreen(component)
            is StudioComponent.Child.GeneratePlan -> GeneratePlanScreen(component)
        }
    }
}
