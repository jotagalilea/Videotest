package com.jg.videotest.di

import androidx.room.Room
import com.jg.videotest.data.content.interactor.GetCachedContentUseCase
import com.jg.videotest.data.content.interactor.GetRemoteContentUseCase
import com.jg.videotest.data.content.interactor.SaveContentUseCase
import com.jg.videotest.data.content.repository.ContentDataRepository
import com.jg.videotest.data.content.repository.ContentRepository
import com.jg.videotest.data.content.repository.datastore.ContentCacheDataStore
import com.jg.videotest.data.content.repository.datastore.ContentDataStore
import com.jg.videotest.data.content.repository.datastore.ContentDataStoreFactory
import com.jg.videotest.framework.FrameworkUtils.DATABASE_NAME
import com.jg.videotest.framework.local.mapper.CategoryCacheMapper
import com.jg.videotest.framework.local.mapper.VideoCacheMapper
import com.jg.videotest.framework.local.model.datastore.ContentCachedDataStore
import com.jg.videotest.framework.remote.datastore.Provider1RemoteDataStore
import com.jg.videotest.framework.remote.mapper.Provider1RemoteMapper
import com.jg.videotest.framework.remote.service.ServiceFactory
import com.jg.videotest.framework.sources.local.db.ContentDatabase
import com.jg.videotest.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ContentDatabase::class.java, DATABASE_NAME
        ).build()
    }

    factory { get<ContentDatabase>().categoriesDao() }
    factory { get<ContentDatabase>().videosDao() }

    factory { ServiceFactory.makeProvider1Service() }
    factory<ContentRepository> { ContentDataRepository(get()) }
    factory<ContentCacheDataStore>(named("local")) { ContentCachedDataStore(get(), get(), get()) }
    factory<ContentDataStore>(named("remote1")) { Provider1RemoteDataStore(get(), get()) }
    factory { ContentDataStoreFactory(get(named("local")), get(named("remote1"))) }

    factory { Provider1RemoteMapper() }
    factory { CategoryCacheMapper() }
    factory { VideoCacheMapper() }
}

val contentModule = module {
    factory { GetCachedContentUseCase(get()) }
    factory { GetRemoteContentUseCase(get()) }
    factory { SaveContentUseCase(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
}