package com.judahben149.eclair.navigation

import com.arkivanov.decompose.ComponentContext

interface TrainComponent

class DefaultTrainComponent(
    componentContext: ComponentContext
) : TrainComponent, ComponentContext by componentContext
