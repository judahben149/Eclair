package com.judahben149.eclair.navigation

import com.arkivanov.decompose.ComponentContext

interface ExploreComponent

class DefaultExploreComponent(
    componentContext: ComponentContext
) : ExploreComponent, ComponentContext by componentContext
