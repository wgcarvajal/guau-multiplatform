package core.ui.screens.viewmodels

import admission.ui.screens.AdmissionRegisterViewModel
import androidx.compose.runtime.Composable
import core.domain.usecase.InitialsInCapitalLetterUseCase
import core.domain.usecase.IsMaxStringSizeUseCase
import core.domain.usecase.IsOnlyLettersUseCase
import core.domain.usecase.IsOnlyNumbersUseCase
import core.domain.usecase.RemoveInitialWhiteSpaceUseCase
import core.domain.usecase.ValidateEmailAndPasswordUseCase
import core.domain.usecase.ValidateEmailUseCase
import core.domain.usecase.ValidateNameUseCase
import customer.data.network.repository.CustomerRepository
import customer.data.network.repository.IdentificationTypeRepository
import customer.domain.usecase.GetAllIdentificationTypeUseCase
import customer.domain.usecase.GetCustomersByCenterIdAndNameWithPaginationAndSortUseCase
import customer.domain.usecase.GetCustomersByCenterIdWithPaginationAndSortUseCase
import customer.domain.usecase.SaveCustomerUseCase
import customer.ui.AddCustomerViewModel
import customer.ui.CustomerViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import employee.data.repository.EmployeePreferencesRepository
import employee.domain.usecase.GetCenterIdUseCase
import employee.domain.usecase.SaveCenterIdUseCase
import employee.domain.usecase.SaveEmployeeIdUseCase
import employee.domain.usecase.SaveRolUseCase
import initialsetup.data.network.repository.InitialSetupRepository
import initialsetup.domain.usecase.GetEmployeesUseCase
import initialsetup.domain.usecase.SaveCenterIdAndRolUseCase
import initialsetup.ui.screens.MyVetsViewModel
import io.ktor.client.HttpClient
import login.data.repository.LoginAuthorizationRepository
import login.data.repository.LoginRepository
import login.data.repository.SignUpRepository
import login.data.repository.SocialLoginRepository
import login.domain.usecase.DoLoginUseCase
import login.domain.usecase.DoRegisterUseCase
import login.domain.usecase.DoSocialLoginUseCase
import login.domain.usecase.GetEmailUseCase
import login.domain.usecase.GetTokenUseCase
import login.domain.usecase.SaveEmailUseCase
import login.domain.usecase.SaveNameUseCase
import login.domain.usecase.SaveTokenUseCase
import login.ui.screens.LoginViewModel
import login.ui.screens.SignUpViewModel
import pet.data.network.repository.BreedRepository
import pet.data.network.repository.GenderRepository
import pet.data.network.repository.PetRepository
import pet.data.network.repository.SpeciesRepository
import pet.domain.usecase.GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase
import pet.domain.usecase.GetBreedsBySpeciesIdWithPaginationAndSortUseCase
import pet.domain.usecase.GetGendersUseCase
import pet.domain.usecase.GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase
import pet.domain.usecase.GetPetsByCenterIdWithPaginationAndSortUseCase
import pet.domain.usecase.GetSpeciesUseCase
import pet.domain.usecase.SavePetUseCase
import pet.ui.screens.AddPetViewModel
import pet.ui.screens.PetsViewModel
import splash.domain.usecase.IsLoginTokenUseCase
import splash.domain.usecase.IsSelectedVetUseCase
import splash.ui.screens.SplashViewModel
import ui.AppViewModel


@Composable
fun GetAppViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository
): AppViewModel {
    return getViewModel(
        AppViewModel.KEY,
        viewModelFactory {
            AppViewModel(
                saveEmailUseCase = SaveEmailUseCase(
                    loginAuthorizationPort =
                    loginAuthorizationRepository
                ),
                saveNameUseCase = SaveNameUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                saveTokenUseCase = SaveTokenUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                saveCenterIdUseCase = SaveCenterIdUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                ),
                saveEmployeeIdUseCase = SaveEmployeeIdUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                ),
                saveRolUseCase = SaveRolUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                )
            )
        })
}

@Composable
fun GetSplashViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository
): SplashViewModel {
    return getViewModel(
        SplashViewModel.KEY,
        viewModelFactory {
            SplashViewModel(
                isLoginTokenUseCase = IsLoginTokenUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                isSelectedVetUseCase = IsSelectedVetUseCase(
                    employeePreferencesPort = employeePreferencesRepository
                )
            )
        })
}

@Composable
fun GetLoginViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    httpClient: HttpClient
): LoginViewModel {
    return getViewModel(
        LoginViewModel.KEY,
        viewModelFactory {
            LoginViewModel(
                validateEmailAndPasswordUseCase = ValidateEmailAndPasswordUseCase(),
                doLoginUseCase = DoLoginUseCase(
                    loginPort = LoginRepository(
                        httpClient = httpClient
                    )
                ),
                doSocialLoginUseCase = DoSocialLoginUseCase(
                    socialLoginPort = SocialLoginRepository(
                        httpClient = httpClient
                    )
                ),
                saveEmailUseCase = SaveEmailUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                saveNameUseCase = SaveNameUseCase(
                    loginAuthorizationPort = loginAuthorizationRepository
                ),
                saveTokenUseCase = SaveTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository)
            )
        })
}

@Composable
fun GetSignUpViewModel(httpClient: HttpClient): SignUpViewModel {
    return getViewModel(
        SignUpViewModel.KEY,
        viewModelFactory {
            SignUpViewModel(
                doRegisterUseCase = DoRegisterUseCase(
                    signUpPort = SignUpRepository(
                        httpClient = httpClient
                    )
                ),
                initialsInCapitalLetterUseCase = InitialsInCapitalLetterUseCase(),
                removeInitialWhiteSpaceUseCase = RemoveInitialWhiteSpaceUseCase(),
                isOnlyLettersUseCase = IsOnlyLettersUseCase(),
                isMaxStringSizeUseCase = IsMaxStringSizeUseCase(),
                validateEmailAndPasswordUseCase = ValidateEmailAndPasswordUseCase(),
                validateNameUseCase = ValidateNameUseCase()
            )
        })
}

@Composable
fun GetMyVetsViewModel(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository,
    httpClient: HttpClient
): MyVetsViewModel {
    return getViewModel(
        MyVetsViewModel.KEY,
        viewModelFactory {
            MyVetsViewModel(
                getTokenUseCase = GetTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                getEmailUseCase = GetEmailUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                getEmployeesUseCase = GetEmployeesUseCase(
                    initialSetupPort = InitialSetupRepository(
                        httpClient = httpClient
                    )
                ),
                saveCenterIdAndRolUseCase = SaveCenterIdAndRolUseCase(employeePreferencesPort = employeePreferencesRepository)
            )
        })
}


@Composable
fun GetAddPetViewModel(
    httpClient: HttpClient,
    loginAuthorizationRepository: LoginAuthorizationRepository,
): AddPetViewModel {
    return getViewModel(
        AddPetViewModel.KEY,
        viewModelFactory {
            AddPetViewModel(
                getSpeciesUseCase = GetSpeciesUseCase(
                    speciesPort = SpeciesRepository(
                        httpClient = httpClient
                    ),
                ),
                getBreedsBySpeciesIdWithPaginationAndSortUseCase = GetBreedsBySpeciesIdWithPaginationAndSortUseCase(
                    breedPort = BreedRepository(
                        httpClient = httpClient
                    )
                ),
                getBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase = GetBreedsBySpeciesIdAndNameWithPaginationAndSortUseCase(
                    breedPort = BreedRepository(
                        httpClient = httpClient
                    )
                ),
                getTokenUseCase = GetTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                getGendersUseCase = GetGendersUseCase(genderPort = GenderRepository(httpClient = httpClient)),
                isOnlyLettersUseCase = IsOnlyLettersUseCase(),
                isMaxStringSizeUseCase = IsMaxStringSizeUseCase(),
                removeInitialWhiteSpaceUseCase = RemoveInitialWhiteSpaceUseCase(),
                initialsInCapitalLetterUseCase = InitialsInCapitalLetterUseCase(),
                validateNameUseCase = ValidateNameUseCase(),
                savePetUseCase = SavePetUseCase(petPort = PetRepository(httpClient = httpClient))
            )
        }
    )
}


@Composable
fun GetAddCustomerViewModel(
    httpClient: HttpClient,
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository
): AddCustomerViewModel {
    return getViewModel(
        AddCustomerViewModel.KEY,
        viewModelFactory {
            AddCustomerViewModel(
                getAllIdentificationTypeUseCase = GetAllIdentificationTypeUseCase(
                    identificationTypePort = IdentificationTypeRepository(httpClient = httpClient)
                ),
                getTokenUseCase = GetTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                isOnlyLettersUseCase = IsOnlyLettersUseCase(),
                isOnlyNumbersUseCase = IsOnlyNumbersUseCase(),
                isMaxStringSizeUseCase = IsMaxStringSizeUseCase(),
                initialsInCapitalLetterUseCase = InitialsInCapitalLetterUseCase(),
                removeInitialWhiteSpaceUseCase = RemoveInitialWhiteSpaceUseCase(),
                validateNameUseCase = ValidateNameUseCase(),
                validateEmailUseCase = ValidateEmailUseCase(),
                saveCustomerUseCase = SaveCustomerUseCase(
                    customerPort = CustomerRepository(
                        httpClient = httpClient
                    )
                ),
                getCenterIdUseCase = GetCenterIdUseCase(employeePreferencesPort = employeePreferencesRepository)
            )
        }
    )
}

@Composable
fun GetCustomerViewModel(
    httpClient: HttpClient,
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository
): CustomerViewModel {
    return getViewModel(
        CustomerViewModel.KEY,
        viewModelFactory {
            CustomerViewModel(
                getTokenUseCase = GetTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                getCenterIdUseCase = GetCenterIdUseCase(employeePreferencesPort = employeePreferencesRepository),
                getCustomersByCenterIdAndNameWithPaginationAndSortUseCase = GetCustomersByCenterIdAndNameWithPaginationAndSortUseCase(
                    customerPort = CustomerRepository(
                        httpClient = httpClient
                    )
                ),
                getCustomersByCenterIdWithPaginationAndSortUseCase = GetCustomersByCenterIdWithPaginationAndSortUseCase(
                    customerPort = CustomerRepository(
                        httpClient = httpClient
                    )
                )
            )
        }
    )
}


@Composable
fun GetPetsViewModel(
    httpClient: HttpClient,
    loginAuthorizationRepository: LoginAuthorizationRepository,
    employeePreferencesRepository: EmployeePreferencesRepository
): PetsViewModel {
    return getViewModel(
        PetsViewModel.KEY,
        viewModelFactory {
            PetsViewModel(
                getTokenUseCase = GetTokenUseCase(loginAuthorizationPort = loginAuthorizationRepository),
                getCenterIdUseCase = GetCenterIdUseCase(employeePreferencesPort = employeePreferencesRepository),
                getPetsByCenterIdWithPaginationAndSortUseCase = GetPetsByCenterIdWithPaginationAndSortUseCase(
                    petPort = PetRepository(httpClient = httpClient)
                ),
                getPetsByCenterIdAndSearchWithPaginationAndSortUseCase = GetPetsByCenterIdAndSearchWithPaginationAndSortUseCase(
                    petPort = PetRepository(httpClient = httpClient)
                )
            )
        }
    )
}

@Composable
fun GetAdmissionRegisterViewModel(): AdmissionRegisterViewModel {
    return getViewModel(
        AdmissionRegisterViewModel.KEY,
        viewModelFactory {
            AdmissionRegisterViewModel()
        }
    )
}