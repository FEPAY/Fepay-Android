package kr.hs.dgsw.smartschool.fepay_android.base

import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.Charset
import kotlin.experimental.and

abstract class NfcReadActivity: AppCompatActivity() {

    val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
    val ndefTag = Ndef.get(tag)

    // 태그 크기
    val size = ndefTag.maxSize

    // 쓰기 가능 여부
    val writable = ndefTag.isWritable

    // 태그 타입
    val type = ndefTag.type

    // 태그 ID
    val id = byteArrayToHexString(tag.id)

    override fun onResume() {
        super.onResume()
        val messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

        messages?.let {
            messages.forEach { setReadTagData(it as NdefMessage) }
        }
    }

    fun byteArrayToHexString(b: ByteArray): String {
        var data = ""

        b.forEach {
            data += Integer.toHexString(((it.toInt()/4).toByte().and(0xf.toByte()).toInt()))
            data += Integer.toHexString(it.and(0xf.toByte()).toInt())
        }
        return data
    }

    fun setReadTagData(ndefmsg: NdefMessage?)
            = ndefmsg?.let {
        var msgs = ""

        msgs += ndefmsg.toString() + "\n"

        val records = ndefmsg.records

        records.forEach {
            val payload = it.payload

            var textEncoding = "UTF-8"

            when {
                payload.isNotEmpty() -> {
                    textEncoding =
                        if (payload[0].and(0x0200.toByte()).equals(0)) "UTF-8"
                        else "UTF-16"

                }
            }

            val tnf = it.tnf

            val type = it.type.toString()

            // payloadString
            val payloadStr = String(it.payload, Charset.forName(textEncoding))
        }
    }
}
