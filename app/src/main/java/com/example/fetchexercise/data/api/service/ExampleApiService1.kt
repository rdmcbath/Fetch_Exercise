//package com.example.fetchexercise.data.api.service
//
//import com.example.fetchexercise.data.model.User
//import retrofit2.Response
//import retrofit2.http.Body
//import retrofit2.http.GET
//import retrofit2.http.POST
//
//// Example of another API service
//interface AnotherApiService {
//    @GET("users")
//    suspend fun getUsers(): Response<List<User>>
//
//    @POST("users")
//    suspend fun createUser(@Body user: User): Response<User>
//}