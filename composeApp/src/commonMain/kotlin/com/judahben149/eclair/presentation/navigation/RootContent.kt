package com.judahben149.eclair.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.judahben149.eclair.navigation.BottomTab
import com.judahben149.eclair.navigation.RootComponent
import com.judahben149.eclair.presentation.screens.explore.ExploreScreen
import com.judahben149.eclair.presentation.screens.review.ReviewScreen
import com.judahben149.eclair.presentation.screens.studio.StudioScreen
import com.judahben149.eclair.presentation.screens.train.TrainScreen

@Composable
fun RootContent(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()
    val activeChild = childStack.active.instance

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentTab = when (activeChild) {
                    is RootComponent.Child.Studio -> BottomTab.STUDIO
                    is RootComponent.Child.Train -> BottomTab.TRAIN
                    is RootComponent.Child.Review -> BottomTab.REVIEW
                    is RootComponent.Child.Explore -> BottomTab.EXPLORE
                },
                onTabSelected = component::onTabSelected
            )
        }
    ) { paddingValues ->
        Children(
            stack = childStack,
            modifier = Modifier.padding(paddingValues)
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Studio -> StudioScreen(instance.component)
                is RootComponent.Child.Train -> TrainScreen(instance.component)
                is RootComponent.Child.Review -> ReviewScreen(instance.component)
                is RootComponent.Child.Explore -> ExploreScreen(instance.component)
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {
    NavigationBar {
        BottomTab.entries.forEach { tab ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = { Text(tab.title) },
                selected = currentTab == tab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

private val BottomTab.icon: ImageVector
    get() = when (this) {
        BottomTab.STUDIO -> Icons.Default.VideoLibrary
        BottomTab.TRAIN -> Icons.Default.FitnessCenter
        BottomTab.REVIEW -> Icons.Default.RateReview
        BottomTab.EXPLORE -> Icons.Default.Explore
    }

private val BottomTab.title: String
    get() = when (this) {
        BottomTab.STUDIO -> "Studio"
        BottomTab.TRAIN -> "Train"
        BottomTab.REVIEW -> "Review"
        BottomTab.EXPLORE -> "Explore"
    }
