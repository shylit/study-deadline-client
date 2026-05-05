package ru.mirea.shylit.studydeadline.data.remote.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mirea.shylit.studydeadline.data.remote.dto.CreateTaskRequest
import ru.mirea.shylit.studydeadline.data.remote.dto.TaskDto
import ru.mirea.shylit.studydeadline.data.remote.dto.UpdateTaskRequest
import ru.mirea.shylit.studydeadline.data.remote.dto.UpdateTaskStatusRequest

interface TaskApi {

    @GET("tasks")
    suspend fun getTasks(): List<TaskDto>

    @GET("tasks/today")
    suspend fun getTodayTasks(): List<TaskDto>

    @GET("subjects/{subjectId}/tasks")
    suspend fun getTasksBySubject(
        @Path("subjectId") subjectId: String
    ): List<TaskDto>

    @POST("tasks")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): TaskDto

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: String,
        @Body request: UpdateTaskRequest
    ): TaskDto

    @PATCH("tasks/{id}/status")
    suspend fun updateTaskStatus(
        @Path("id") id: String,
        @Body request: UpdateTaskStatusRequest
    ): TaskDto

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: String
    )

    @GET("tasks/search")
    suspend fun searchTasks(
        @Query("query") query: String
    ): List<TaskDto>
}