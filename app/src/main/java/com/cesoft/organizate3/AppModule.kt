package com.cesoft.organizate3

import android.app.Application
import com.cesoft.organizate3.data.Repository
import com.cesoft.organizate3.domain.repo.TaskRepository
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskByIdUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskTypesUseCase
import com.cesoft.organizate3.domain.usecase.UpdateTaskUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
//
//@Module
//@InstallIn(ActivityComponent::class)//ViewModelComponent
//abstract class AppModule {
//    @Binds
//    abstract fun bindRepository(impl: Repository): TaskRepository
//}

@Module
@InstallIn(SingletonComponent::class)//ViewModelComponent//ActivityComponent
object App2Module {

    @Provides
    fun providesRepository(application: Application): Repository {
        return Repository(application.applicationContext)
    }

    @Provides
    fun providesTaskRepository(application: Application): TaskRepository {
        return Repository(application.applicationContext)
    }

    @Provides
    fun providesGetTaskByIdUseCase(repository: Repository): GetTaskByIdUseCase {
        return GetTaskByIdUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesUpdateTaskUseCase(repository: Repository): UpdateTaskUseCase {
        return UpdateTaskUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesAddTaskUseCase(repository: Repository): AddTaskUseCase {
        return AddTaskUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesGetTaskTypesUseCase(repository: Repository): GetTaskTypesUseCase {
        return GetTaskTypesUseCase(repository, Dispatchers.IO)
    }
}