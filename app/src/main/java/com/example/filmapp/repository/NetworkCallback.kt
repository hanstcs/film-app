package com.example.filmapp.repository

import com.android.volley.VolleyError

interface NetworkCallback<ResponseType> {
    fun onSuccess(response: ResponseType)
    fun onError(error: VolleyError)
}
