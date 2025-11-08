package com.judahben149.eclair.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface StudioComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onAskQuestionClicked()
    fun onGeneratePlanClicked()
    fun onAnalyzeSetupClicked()
    fun onBackClicked()

    sealed class Child {
        data object Home : Child()
        data object AskQuestion : Child()
        data object GeneratePlan : Child()
    }
}

class DefaultStudioComponent(
    componentContext: ComponentContext,
    private val onNavigateToReview: () -> Unit
) : StudioComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, StudioComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun onAskQuestionClicked() {
        navigation.push(Config.AskQuestion)
    }

    override fun onGeneratePlanClicked() {
        navigation.push(Config.GeneratePlan)
    }

    override fun onAnalyzeSetupClicked() {
        onNavigateToReview()
    }

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): StudioComponent.Child = when (config) {
        Config.Home -> StudioComponent.Child.Home
        Config.AskQuestion -> StudioComponent.Child.AskQuestion
        Config.GeneratePlan -> StudioComponent.Child.GeneratePlan
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Home : Config()
        @Serializable
        data object AskQuestion : Config()
        @Serializable
        data object GeneratePlan : Config()
    }
}
