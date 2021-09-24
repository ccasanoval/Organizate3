package com.cesoft.organizate3

import android.content.Context
import com.cesoft.organizate3.data.Repository
import com.cesoft.organizate3.domain.repo.TaskRepository
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.domain.usecase.DeleteAllTasksUseCase
import com.cesoft.organizate3.domain.usecase.DeleteTaskUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskByIdUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskTypesFlowUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskTypesUseCase
import com.cesoft.organizate3.domain.usecase.GetTasksByTypeUseCase
import com.cesoft.organizate3.domain.usecase.GetTasksUseCase
import com.cesoft.organizate3.domain.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

//
//@Module
//@InstallIn(ActivityComponent::class)//ViewModelComponent
//abstract class AppModule {
//    @Binds
//    abstract fun bindRepository(impl: Repository): TaskRepository
//}

@Module
@InstallIn(SingletonComponent::class)//ViewModelComponent//ActivityComponent
object AppModule {

    //@Provides
    //fun providesRepository(application: Application): Repository {
    //    return Repository(application.applicationContext)
    //}

    @Singleton
    @Provides
    fun providesTaskRepository(@ApplicationContext appContext: Context): TaskRepository {
        return Repository(appContext)
    }

    @Provides
    fun providesGetTasksUseCase(repository: TaskRepository): GetTasksUseCase {
        return GetTasksUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesGetTaskTypesUseCase(repository: TaskRepository): GetTaskTypesUseCase {
        return GetTaskTypesUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesGetTaskTypesFlowUseCase(repository: TaskRepository): GetTaskTypesFlowUseCase {
        return GetTaskTypesFlowUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesGetTaskByIdUseCase(repository: TaskRepository): GetTaskByIdUseCase {
        return GetTaskByIdUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesGetTasksByTypeUseCase(repository: TaskRepository): GetTasksByTypeUseCase {
        return GetTasksByTypeUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase {
        return UpdateTaskUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesAddTaskUseCase(repository: TaskRepository): AddTaskUseCase {
        return AddTaskUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesDeleteAllTasksUseCase(repository: TaskRepository): DeleteAllTasksUseCase {
        return DeleteAllTasksUseCase(repository, Dispatchers.IO)
    }

    @Provides
    fun providesDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(repository, Dispatchers.IO)
    }
}
