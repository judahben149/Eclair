package com.judahben149.eclair.navigation

import com.arkivanov.decompose.ComponentContext

interface ReviewComponent

class DefaultReviewComponent(
    componentContext: ComponentContext
) : ReviewComponent, ComponentContext by componentContext
