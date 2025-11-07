package com.judahben149.eclair.navigation

import com.arkivanov.decompose.ComponentContext

interface StudioComponent

class DefaultStudioComponent(
    componentContext: ComponentContext
) : StudioComponent, ComponentContext by componentContext
