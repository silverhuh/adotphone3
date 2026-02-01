package com.skt.adotphone3

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdotPhoneSmokeTest {

    private lateinit var device: UiDevice

    private val PACKAGE_NAME = "com.skt.adotphone3"
    private val LAUNCH_TIMEOUT = 10_000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(
            InstrumentationRegistry.getInstrumentation()
        )

        // 홈 화면으로 이동
        device.pressHome()
    }

    @Test
    fun adotPhone_fullFunctionalTest() {
        launchAppByPackage(PACKAGE_NAME)

        // 👉 여기부터는 실제 앱 검증 로직
        // 예시: 첫 화면에 특정 텍스트가 뜨는지 확인
        device.wait(
            Until.hasObject(By.textContains("에이닷")),
            15_000
        )
    }

    /**
     * ✅ 아이콘이 아니라 "패키지명"으로 앱 실행
     * 단말 / 런처 / 언어 영향 없음 (CI 최적)
     */
    private fun launchAppByPackage(packageName: String) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext

        val intent = context.packageManager
            .getLaunchIntentForPackage(packageName)
            ?: throw IllegalStateException(
                "❌ Launch Intent 못 찾음: $packageName (앱 설치/패키지명 확인)"
            )

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // 앱 프로세스가 뜰 때까지 대기
        device.wait(
            Until.hasObject(By.pkg(packageName).depth(0)),
            LAUNCH_TIMEOUT
        )
    }
}
