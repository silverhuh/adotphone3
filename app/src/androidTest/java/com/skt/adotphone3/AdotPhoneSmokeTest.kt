package com.skt.adotphone3

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdotPhoneSmokeTest {

    private val device: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    /* ================= 공통 유틸 ================= */

    private fun relax(ms: Long = 500) {
        try {
            Thread.sleep(ms)
        } catch (_: InterruptedException) {
        }
    }

    private fun waitText(text: String, timeout: Long = 5_000) =
        device.wait(
            Until.findObject(By.text(text)),
            timeout
        ) ?: error("❌ '$text' 텍스트 찾지 못함")

    private fun tap(text: String) {
        waitText(text).click()
        device.waitForIdle()
        relax()
    }

    private fun back() {
        relax()                 // back 전 대기
        device.pressBack()
        device.waitForIdle()
        relax()                 // back 후 대기
    }

    /**
     * 설정 화면용 스크롤 + 탐색
     */
    private fun scrollAndFindText(
        text: String,
        maxScroll: Int = 6
    ) = run {
        repeat(maxScroll) {
            device.findObject(By.text(text))?.let { return@run it }

            device.swipe(
                device.displayWidth / 2,
                device.displayHeight * 3 / 4,
                device.displayWidth / 2,
                device.displayHeight / 4,
                20
            )
            device.waitForIdle()
            relax()
        }
        error("❌ 스크롤 후에도 '$text' 찾지 못함")
    }

    /* ================= 앱 실행 ================= */

    private fun launchAdotPhone() {
        device.pressHome()
        device.waitForIdle()
        relax()

        // 앱 서랍 열기
        device.swipe(
            device.displayWidth / 2,
            device.displayHeight * 4 / 5,
            device.displayWidth / 2,
            device.displayHeight / 5,
            20
        )
        device.waitForIdle()
        relax()

        val app = device.wait(
            Until.findObject(By.text("에이닷 전화")),
            5_000
        ) ?: error("❌ 홈 화면에서 '에이닷 전화' 앱 아이콘 못 찾음")

        app.click()
        device.waitForIdle()
        relax(800)

        // 메인 로딩 체크
        waitText("키패드", 10_000)
        relax()
    }

    /* ================= 실제 테스트 ================= */

    @Test
    fun adotPhone_fullFunctionalTest() {

        /* 1. 앱 실행 */
        launchAdotPhone()

        /* ================= 메인 탭 ================= */

        tap("에이닷")
        waitText("에이닷")

        tap("연락처")
        waitText("연락처")

        tap("키패드")
        waitText("키패드")

        tap("최근기록")
        waitText("최근기록")

        tap("설정")
        waitText("설정")

        /* ================= 설정 상세 ================= */

        // 설정 - 통화요약
        scrollAndFindText("통화요약").click()
        device.waitForIdle()
        relax()
        back()

        // 설정 - AI 보안 → AI 보이스피싱 탐지 → 다운로드
        scrollAndFindText("AI 보안").click()
        device.waitForIdle()
        relax()

        scrollAndFindText("AI 보이스피싱 탐지").click()
        device.waitForIdle()
        relax(800)

        scrollAndFindText("다운로드").click()
        device.waitForIdle()
        relax(1_000)
        back()
        back()
        back()

        // 설정 - 5GX 프리미엄(넷플릭스)
        scrollAndFindText("5GX 프리미엄(넷플릭스)").click()
        device.waitForIdle()
        relax(800)
        back()

        // 설정 - 테마 → 다운로드 → 실행하기
        scrollAndFindText("테마").click()
        device.waitForIdle()
        relax()

        scrollAndFindText("다운로드").click()
        device.waitForIdle()
        relax(1_000)

        back()
    }
}
