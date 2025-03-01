package com.example.marsphotoskento.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class MarsApiServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var apiService: MarsApiService

    @Before
    fun setup() {
        // MockWebServerを設定
        server = MockWebServer()
        server.start()

        // MockWebServerのURLを使用してRetrofitインスタンスを作成
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(server.url("/"))
            .build()

        // APIサービスのインスタンスを作成
        apiService = retrofit.create(MarsApiService::class.java)
    }

    @After
    fun tearDown() {
        // テスト終了後にサーバーをシャットダウン
        server.shutdown()
    }

    @Test
    fun getPhotos_returnsListOfPhotos() = runBlocking {
        // モックレスポンスを準備
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                [
                    {
                        "id": "1",
                        "img_src": "https://example.com/photo1.jpg"
                    },
                    {
                        "id": "2",
                        "img_src": "https://example.com/photo2.jpg"
                    }
                ]
            """.trimIndent())

        // モックレスポンスをサーバーにエンキュー
        server.enqueue(mockResponse)

        // APIリクエストを実行
        val photos = apiService.getPhotos()

        // 結果を検証
        assertEquals(2, photos.size)
        assertEquals("1", photos[0].id)
        assertEquals("https://example.com/photo1.jpg", photos[0].imgSrc)
        assertEquals("2", photos[1].id)
        assertEquals("https://example.com/photo2.jpg", photos[1].imgSrc)

        // リクエストが正しいパスに送信されたことを検証
        val request = server.takeRequest()
        assertEquals("/photos", request.path)
    }

    @Test
    fun getPhotos_emptyResponse_returnsEmptyList() = runBlocking {
        // 空のレスポンスをモック
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("[]")

        server.enqueue(mockResponse)
        
        // APIリクエストを実行
        val photos = apiService.getPhotos()

        // 空のリストが返されることを検証
        assertEquals(0, photos.size)
    }

    @Test
    fun getPhotos_errorResponse() = runBlocking {
        // エラーレスポンスをモック
        val mockResponse = MockResponse()
            .setResponseCode(404)

        server.enqueue(mockResponse)

        try {
            // APIリクエストを実行（例外が発生するはず）
            apiService.getPhotos()
            // ここに到達するべきではない
            assert(false) { "Expected exception was not thrown" }
        } catch (e: Exception) {
            // 例外が発生することを検証
            assert(true)
        }
    }
} 