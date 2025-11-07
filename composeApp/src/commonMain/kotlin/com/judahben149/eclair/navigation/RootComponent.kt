package com.judahben149.eclair.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onTabSelected(tab: BottomTab)

    sealed class Child {
        data class Studio(val component: StudioComponent) : Child()
        data class Train(val component: TrainComponent) : Child()
        data class Review(val component: ReviewComponent) : Child()
        data class Explore(val component: ExploreComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Studio,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun onTabSelected(tab: BottomTab) {
        val config = when (tab) {
            BottomTab.STUDIO -> Config.Studio
            BottomTab.TRAIN -> Config.Train
            BottomTab.REVIEW -> Config.Review
            BottomTab.EXPLORE -> Config.Explore
        }
        navigation.bringToFront(config)
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        Config.Studio -> RootComponent.Child.Studio(
            DefaultStudioComponent(componentContext)
        )
        Config.Train -> RootComponent.Child.Train(
            DefaultTrainComponent(componentContext)
        )
        Config.Review -> RootComponent.Child.Review(
            DefaultReviewComponent(componentContext)
        )
        Config.Explore -> RootComponent.Child.Explore(
            DefaultExploreComponent(componentContext)
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Studio : Config()
        @Serializable
        data object Train : Config()
        @Serializable
        data object Review : Config()
        @Serializable
        data object Explore : Config()
    }
}

enum class BottomTab {
    STUDIO, TRAIN, REVIEW, EXPLORE
}
