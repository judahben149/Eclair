package com.judahben149.eclair.presentation.screens.train

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.judahben149.eclair.navigation.TrainComponent
import com.judahben149.eclair.presentation.screens.studio.EquipmentIdentificationScreen

@Composable
fun TrainScreen(component: TrainComponent) {
    val childStack by component.childStack.subscribeAsState()

    Children(
        stack = childStack,
        animation = stackAnimation(fade() + scale())
    ) { child ->
        when (val instance = child.instance) {
            is TrainComponent.Child.Home -> TrainHomeScreen(component)
            is TrainComponent.Child.Category -> CategoryScreen(component, instance.type)
            is TrainComponent.Child.CustomLearningPath -> CustomLearningPathScreen(component)
            is TrainComponent.Child.IdentifyEquipment -> EquipmentIdentificationScreen(
                onBackClick = component::onBackClicked
            )
        }
    }
}
