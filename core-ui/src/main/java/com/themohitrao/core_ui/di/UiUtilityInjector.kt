package com.themohitrao.core_ui.di

import com.themohitrao.core_ui.BaseEdgeEffectFactory
import org.koin.dsl.module


val uiUtilityModule = module {
    single { BaseEdgeEffectFactory() }
}