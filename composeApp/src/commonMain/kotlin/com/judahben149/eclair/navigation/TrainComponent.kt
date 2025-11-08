package com.judahben149.eclair.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface TrainComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onCategoryClicked(category: CategoryType)
    fun onCustomLearningPathClicked()
    fun onBackClicked()

    sealed class Child {
        data object Home : Child()
        data class Category(val type: CategoryType) : Child()
        data object CustomLearningPath : Child()
    }
}

enum class CategoryType {
    STAGE_BASICS,
    CONCERT_LIGHTING,
    THEATRE,
    EQUIPMENT,
    COLOUR_THEORY,
    DMX
}

class DefaultTrainComponent(
    componentContext: ComponentContext
) : TrainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, TrainComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override fun onCategoryClicked(category: CategoryType) {
        navigation.push(Config.Category(category))
    }

    @OptIn(DelicateDecomposeApi::class)
    override fun onCustomLearningPathClicked() {
        navigation.push(Config.CustomLearningPath)
    }

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): TrainComponent.Child = when (config) {
        Config.Home -> TrainComponent.Child.Home
        is Config.Category -> TrainComponent.Child.Category(config.type)
        Config.CustomLearningPath -> TrainComponent.Child.CustomLearningPath
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Home : Config()
        @Serializable
        data class Category(val type: CategoryType) : Config()
        @Serializable
        data object CustomLearningPath : Config()
    }
}
