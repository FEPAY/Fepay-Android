package kr.hs.dgsw.smartschool.fepay_android.base

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.Charset
import android.nfc.tech.NdefFormatable
import android.widget.Toast
import java.io.IOException


abstract class NfcWriteActivity: AppCompatActivity() {

    val TYPE_TEXT = 1
    val TYPE_URI = 2

    lateinit var nfcAdapter: NfcAdapter
    lateinit var pendingIntent: PendingIntent

    abstract val writeText: String


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        pendingIntent = PendingIntent.getActivity(this,0, intent,0)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val detectedTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)

        writeTag(getTextAsNdef(), detectedTag)
    }

    fun getTextAsNdef(): NdefMessage {
        val textBytes = writeText.toByteArray()

        val textRecord = NdefRecord(NdefRecord.TNF_MIME_MEDIA,
            "text/plain".toByteArray(), byteArrayOf(), textBytes)

        return NdefMessage(arrayOf(textRecord))

    }

    fun getUriAsNdef(): NdefMessage {
        val textBytes = writeText.toByteArray()

        val record1 = NdefRecord(NdefRecord.TNF_WELL_KNOWN,
            "U".toByteArray(Charset.forName("US-ASCII")),
            byteArrayOf(0), textBytes)

        return NdefMessage(arrayOf(record1))
    }

    fun writeTag(message: NdefMessage, tag: Tag): Boolean {
        val size = message.toByteArray().size

        try {
            val ndef = Ndef.get(tag)

            ndef?.let {
                ndef.connect()
                if (!ndef.isWritable) return false
                if (ndef.maxSize < size) return false

                ndef.writeNdefMessage(message)

                "쓰기 성공!".createToast(this).show()
            }.let {
                "포맷되지 않은 태그이므로 먼저 포맷하고 데이터를 씁니다.".createToast(this).show()

                val formatable = NdefFormatable.get(tag)

                formatable?.let {
                    try {
                        formatable.connect()
                        formatable.format(message)
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                }

                return false
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }

        return true
    }
}