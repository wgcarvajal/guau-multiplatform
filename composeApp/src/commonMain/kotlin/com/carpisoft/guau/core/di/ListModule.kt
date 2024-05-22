package com.carpisoft.guau.core.di

import com.carpisoft.guau.admission.di.admissionModule
import com.carpisoft.guau.core.database.di.databaseModule
import com.carpisoft.guau.core.datastore.di.dataStoreModule
import com.carpisoft.guau.core.network.di.networkModule
import com.carpisoft.guau.core.preferences.di.preferencesModule
import com.carpisoft.guau.customer.di.customerModule
import com.carpisoft.guau.di.guauModule
import com.carpisoft.guau.employee.di.employeeModule
import com.carpisoft.guau.initialsetup.di.initialSetupModule
import com.carpisoft.guau.login.di.loginModule
import com.carpisoft.guau.pet.di.petModule
import com.carpisoft.guau.splash.di.splashModule
import org.koin.core.module.Module

fun getListModule(): List<Module> {
    return listOf(
        guauModule,
        networkModule,
        dataStoreModule,
        databaseModule,
        preferencesModule,
        admissionModule,
        coreModule,
        splashModule,
        customerModule,
        employeeModule,
        initialSetupModule,
        loginModule,
        petModule
    )
}