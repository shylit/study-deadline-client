package ru.mirea.shylit.studydeadline.data.remote.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.mirea.shylit.studydeadline.data.remote.dto.CreateSubjectRequest
import ru.mirea.shylit.studydeadline.data.remote.dto.SubjectDto
import ru.mirea.shylit.studydeadline.data.remote.dto.UpdateSubjectRequest
import ru.mirea.shylit.studydeadline.data.remote.dto.MessageResponse

interface SubjectApi {

    @GET("subjects")
    suspend fun getSubjects(): List<SubjectDto>

    @POST("subjects")
    suspend fun createSubject(
        @Body request: CreateSubjectRequest
    ): SubjectDto

    @PUT("subjects/{id}")
    suspend fun updateSubject(
        @Path("id") id: String,
        @Body request: UpdateSubjectRequest
    ): SubjectDto

    @DELETE("subjects/{id}")
    suspend fun deleteSubject(
        @Path("id") id: String
    ): MessageResponse
}