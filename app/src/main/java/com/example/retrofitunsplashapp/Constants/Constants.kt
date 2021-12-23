package com.example.retrofitunsplashapp.Constants

object API{
    const val BASE_URL = "https://api.unsplash.com/"
    const val CLIENT_ID : String = "gRQEYwHhWS5L3m8tdgVALFqOidChGIIkUNLY7mkR4PM"
    const val SEARCH_PHOTO : String ="search/photos"
    const val SEARCH_USERS : String ="search/users"
}
val TAG = "TAG"
enum class RESPONSE_STATE{
    OKAY,
    FAIL
}