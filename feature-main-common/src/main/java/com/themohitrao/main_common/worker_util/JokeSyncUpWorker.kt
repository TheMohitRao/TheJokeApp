/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.themohitrao.main_common.worker_util

import android.content.Context
import androidx.work.*
import androidx.work.PeriodicWorkRequest.Companion.MIN_PERIODIC_INTERVAL_MILLIS
import com.google.common.util.concurrent.ListenableFuture
import com.themohitrao.main_common.events.NextJokeIn
import com.themohitrao.main_common.repository.JokeRepository
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

/**
 * Syncs the data layer by delegating to the appropriate repository instances with
 * sync functionality.
 */
class JokeSyncUpWorker(
    private val appContext: Context,
    workerParams: WorkerParameters,
    private val jokeRepository: JokeRepository,
    private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val syncedSuccessfully = awaitAll(
            async { fetch15Jokes() },
        ).all { it[0] }

        if (syncedSuccessfully) Result.success()
        else Result.retry()
    }

    private suspend fun fetch15Jokes(): List<Boolean> {
        val successList = mutableListOf<Boolean>()
        for(i in 0 until 1200){
            EventBus.getDefault().post(NextJokeIn((1200 - i)%60))
            delay(1000)
            if(i%60 == 0){
                successList.add(jokeRepository.getNewJoke())
            }
        }
        return successList
    }

    companion object {

        fun startUpSyncWork() = PeriodicWorkRequestBuilder<JokeSyncUpWorker>(
            MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MINUTES
        )
            .setConstraints(SyncConstraints)
            .addTag(SyncWorkName)
            .setInputData(JokeSyncUpWorker::class.delegatedData())
            .build()
    }
}

fun startJokeSyncWorker(context:Context){
    WorkManager.getInstance(context).apply {
        // Run sync on app startup and ensure only one sync worker runs at any time
        enqueueUniquePeriodicWork(
            SyncWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            JokeSyncUpWorker.startUpSyncWork()
        )
    }
}

fun stopJokeSyncWorker(context:Context){
    WorkManager.getInstance(context).cancelAllWorkByTag(SyncWorkName)
}

fun isWorkScheduled(tag: String,context:Context): Boolean {
    val instance: WorkManager = WorkManager.getInstance(context)
    val statuses: ListenableFuture<List<WorkInfo>> = instance.getWorkInfosByTag(tag)
    return try {
        var running = false
        val workInfoList: List<WorkInfo> = statuses.get()
        for (workInfo in workInfoList) {
            val state: WorkInfo.State = workInfo.state
            running = state === WorkInfo.State.RUNNING || state === WorkInfo.State.ENQUEUED
        }
        running
    } catch (e: ExecutionException) {
        e.printStackTrace()
        false
    } catch (e: InterruptedException) {
        e.printStackTrace()
        false
    }
}