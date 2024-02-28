package com.themohitrao.main_common.di

import com.themohitrao.main_common.repository.JokeRepository
import com.themohitrao.main_common.use_case.FetchJokesUseCase
import com.themohitrao.main_common.use_case.FetchJokesUseCaseImpl
import com.themohitrao.main_common.use_case.FetchOneJokeUseCase
import com.themohitrao.main_common.use_case.FetchOneJokeUseCaseImpl
import com.themohitrao.main_common.worker_util.JokeSyncUpWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mainFeatureModule = module {
    single {
        JokeRepository(get(), get())
    }
    singleOf(::FetchJokesUseCaseImpl) { bind<FetchJokesUseCase>() }
    singleOf(::FetchOneJokeUseCaseImpl) { bind<FetchOneJokeUseCase>() }
    workerOf(::JokeSyncUpWorker)
}