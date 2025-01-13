//package com.example.fetchexercise.data.repository
//
//import com.example.fetchexercise.data.api.RetrofitClient
//import com.example.fetchexercise.data.model.User
//import retrofit2.HttpException
//
//
//class ExampleDataModel1RepositoryImpl : UserRepository {
//    private val api = RetrofitClient.anotherApiService
//
//    override suspend fun getUsers(): Result<List<User>> = runCatching {
//        val response = api.getUsers()
//        if (response.isSuccessful) {
//            response.body() ?: emptyList()
//        } else {
//            throw HttpException(response)
//        }
//    }
//
//    override suspend fun createUser(user: User): Result<User> = runCatching {
//        val response = api.createUser(user)
//        if (response.isSuccessful) {
//            response.body() ?: throw IllegalStateException("User creation failed")
//        } else {
//            throw HttpException(response)
//        }
//    }
//}