package com.poly.core.di

import androidx.room.Room
import com.poly.BuildConfig.BASE_URL
import com.poly.BuildConfig.DATABASE_NAME
import com.poly.core.data.PolyRepository
import com.poly.core.data.source.local.LocalDataSource
import com.poly.core.data.source.local.room.PolyDatabase
import com.poly.core.data.source.remote.RemoteDataSource
import com.poly.core.data.source.remote.network.ApiService
import com.poly.core.domain.repository.IPolyRepository
import com.poly.core.domain.usecase.PolyInteractor
import com.poly.core.domain.usecase.PolyUseCase
import com.poly.core.viewmodel.*
import com.poly.utils.AppExecutors
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<PolyDatabase>().kitaSehatCartDao() }

    single {
        Room.databaseBuilder(get(), PolyDatabase::class.java, DATABASE_NAME)
            .build()
    }
}

val networkModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val useCaseModule = module {
    factory<PolyUseCase> { PolyInteractor(get()) }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    factory { AppExecutors() }
    single<IPolyRepository> {
        PolyRepository(get(), get(), get())
    }
}

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { ServiceViewModel(get()) }
    viewModel { JobViewModel(get()) }
    viewModel { BusinessViewModel(get()) }
    viewModel { StoreViewModel(get()) }
    viewModel { MomentViewModel(get()) }
}