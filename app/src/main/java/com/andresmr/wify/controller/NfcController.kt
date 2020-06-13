package com.andresmr.wify.controller

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import com.andresmr.wify.BuildConfig
import com.andresmr.wify.entity.WifiAuthType
import com.andresmr.wify.entity.WifiNetwork
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.Charset

class NfcController {

    fun writeNetworkOnTag(wifiNetwork: WifiNetwork, tag: Tag): Boolean {
        val ndefMessage: NdefMessage = generateNdefMessage(wifiNetwork)
        val TAG = "NFC TAG"
        val messageSize = ndefMessage.toByteArray().size

        try {
            val ndef = Ndef.get(tag)
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
                val ndefFormatable = NdefFormatable.get(tag)
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

    private fun generateNdefMessage(wifiNetwork: WifiNetwork): NdefMessage {
        val ssid = wifiNetwork.ssid
        val ssidSize = ssid.toByteArray().size.toShort()

        val authType = when (wifiNetwork.authType) {
            WifiAuthType.WPA_PSK -> AUTH_TYPE_WPA_PSK
            WifiAuthType.WPA2_PSK -> AUTH_TYPE_WPA2_PSK
            WifiAuthType.WPA_EAP -> AUTH_TYPE_WPA_EAP
            WifiAuthType.WPA2_EAP -> AUTH_TYPE_WPA2_EAP
            else -> AUTH_TYPE_OPEN
        }

        val networkKey = wifiNetwork.password
        val networkKeySize = networkKey.toByteArray().size.toShort()

        val macAddress = ByteArray(MAX_MAC_ADDRESS_SIZE_BYTES)
        for (i in 0 until MAX_MAC_ADDRESS_SIZE_BYTES) {
            macAddress[i] = 0xff.toByte()
        }


        val bufferSize =
            18 + ssidSize.toInt() + networkKeySize.toInt() // size of required credential attributes

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
            ByteArray(0), buffer.array()
        )
        val aarRecord = NdefRecord.createApplicationRecord(PACKAGE_NAME)

        return NdefMessage(arrayOf(mimeRecord, aarRecord))
    }

    companion object {
        const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        const val NFC_TOKEN_MIME_TYPE = "application/vnd.wfa.wsc"

        const val AUTH_TYPE_OPEN: Short = 0x0001
        const val AUTH_TYPE_WPA_PSK: Short = 0x0002
        const val AUTH_TYPE_WPA_EAP: Short = 0x0008
        const val AUTH_TYPE_WPA2_EAP: Short = 0x0010
        const val AUTH_TYPE_WPA2_PSK: Short = 0x0020

        const val CREDENTIAL_FIELD_ID: Short = 0x100e
        const val SSID_FIELD_ID: Short = 0x1045
        const val AUTH_TYPE_FIELD_ID: Short = 0x1003
        const val NETWORK_KEY_FIELD_ID: Short = 0x1027

        const val MAX_MAC_ADDRESS_SIZE_BYTES = 6
        const val MAX_NETWORK_KEY_SIZE_BYTES = 64
    }
}