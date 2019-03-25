package com.andresmr.wify.ui.extensions

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.entity.WifiNetwork.Companion.AUTH_TYPE_FIELD_ID
import com.andresmr.wify.entity.WifiNetwork.Companion.AUTH_TYPE_OPEN
import com.andresmr.wify.entity.WifiNetwork.Companion.AUTH_TYPE_WPA2_EAP
import com.andresmr.wify.entity.WifiNetwork.Companion.AUTH_TYPE_WPA2_PSK
import com.andresmr.wify.entity.WifiNetwork.Companion.AUTH_TYPE_WPA_EAP
import com.andresmr.wify.entity.WifiNetwork.Companion.AUTH_TYPE_WPA_PSK
import com.andresmr.wify.entity.WifiNetwork.Companion.CREDENTIAL_FIELD_ID
import com.andresmr.wify.entity.WifiNetwork.Companion.MAX_MAC_ADDRESS_SIZE_BYTES
import com.andresmr.wify.entity.WifiNetwork.Companion.NETWORK_KEY_FIELD_ID
import com.andresmr.wify.entity.WifiNetwork.Companion.NFC_TOKEN_MIME_TYPE
import com.andresmr.wify.entity.WifiNetwork.Companion.PACKAGE_NAME
import com.andresmr.wify.entity.WifiNetwork.Companion.SSID_FIELD_ID
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.Charset

fun Tag.writeWifiNetwork(ndefMessage: NdefMessage): Boolean {
    val TAG = "NFC TAG"
    val messageSize = ndefMessage.toByteArray().size

    try {
        val ndef = Ndef.get(this)
        if (ndef != null) {
            ndef.connect()
            if (!ndef.isWritable) {
                Log.w(TAG, "Tag not writable")
                return false
            }
            val ndefMaxSize = ndef.maxSize
            if (messageSize > ndefMaxSize) {
                /* Try to write the NDEF message without the Android Application Record */
                val newMessage = NdefMessage(arrayOf(ndefMessage.records[0]))
                val newMessageSize = newMessage.toByteArray().size
                if (newMessageSize > ndefMaxSize) {
                    Log.w(TAG, "Tag too small")
                    return false
                } else {
                    Log.d(TAG, "Writing tag without AAR")
                    ndef.writeNdefMessage(newMessage)
                    return true
                }
            }
            ndef.writeNdefMessage(ndefMessage)
            return true
        } else {
            val ndefFormatable = NdefFormatable.get(this)
            if (ndefFormatable != null) {
                try {
                    ndefFormatable.connect()
                    ndefFormatable.format(ndefMessage)
                    return true
                } catch (e: IOException) {
                    Log.w(TAG, "Tag not formatted")
                    return false
                }

            } else {
                Log.d(TAG, "ndefFormatable is null")
                return false
            }
        }
    } catch (e: Exception) {
        Log.w(TAG, "Writing to tag failed", e)
        return false
    }

}

fun WifiNetwork.generateNdefMessage() : NdefMessage {
    val ssid = this.ssid
    val ssidSize = ssid.toByteArray().size.toShort()

    val authType = when (this.authType) {
        WifiAuthType.WPA_PSK -> AUTH_TYPE_WPA_PSK
        WifiAuthType.WPA2_PSK -> AUTH_TYPE_WPA2_PSK
        WifiAuthType.WPA_EAP -> AUTH_TYPE_WPA_EAP
        WifiAuthType.WPA2_EAP -> AUTH_TYPE_WPA2_EAP
        else -> AUTH_TYPE_OPEN
    }

    val networkKey = this.password
    val networkKeySize = networkKey.toByteArray().size.toShort()

    val macAddress = ByteArray(MAX_MAC_ADDRESS_SIZE_BYTES)
    for (i in 0 until MAX_MAC_ADDRESS_SIZE_BYTES) {
        macAddress[i] = 0xff.toByte()
    }


    val bufferSize = 18 + ssidSize.toInt() + networkKeySize.toInt() // size of required credential attributes

    val buffer = ByteBuffer.allocate(bufferSize).apply {
        putShort(CREDENTIAL_FIELD_ID)
        putShort((bufferSize - 4).toShort())

        putShort(SSID_FIELD_ID)
        putShort(ssidSize)
        put(ssid.toByteArray())

        putShort(AUTH_TYPE_FIELD_ID)
        putShort(2.toShort())
        putShort(authType)

        putShort(NETWORK_KEY_FIELD_ID)
        putShort(networkKeySize)
        put(networkKey.toByteArray())
    }

    val mimeRecord = NdefRecord(
            NdefRecord.TNF_MIME_MEDIA,
            NFC_TOKEN_MIME_TYPE.toByteArray(Charset.forName("US-ASCII")),
            ByteArray(0), buffer.array())
    val aarRecord = NdefRecord.createApplicationRecord(PACKAGE_NAME)

    return NdefMessage(arrayOf(mimeRecord, aarRecord))
}