package com.andresmr.wify.ui.util

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import com.andresmr.wify.BuildConfig
import com.andresmr.wify.entity.WifiNetwork
import com.andresmr.wify.entity.WifiAuthType
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.Charset

class NFCUtil {

    private val TAG = NFCUtil::class.java.simpleName
    private val PACKAGE_NAME = BuildConfig.APPLICATION_ID
    private val NFC_TOKEN_MIME_TYPE = "application/vnd.wfa.wsc"

    val CREDENTIAL_FIELD_ID: Short = 0x100e
    val SSID_FIELD_ID: Short = 0x1045
    val AUTH_TYPE_FIELD_ID: Short = 0x1003
    val NETWORK_KEY_FIELD_ID: Short = 0x1027

    val MAX_MAC_ADDRESS_SIZE_BYTES = 6
    val MAX_NETWORK_KEY_SIZE_BYTES = 64


    fun writeOnTag(wifiNetwork: WifiNetwork, tag: Tag): Boolean {
    val nfcMessage = createNdefMessage(wifiNetwork)
    return writeMessageToTag(nfcMessage, tag)
  }

  private fun writeMessageToTag(nfcMessage: NdefMessage, tag: Tag?): Boolean {

      val messageSize = nfcMessage.toByteArray().size

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
                  val newMessage = NdefMessage(arrayOf(nfcMessage.records[0]))
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
              ndef.writeNdefMessage(nfcMessage)
              return true
          } else {
              val ndefFormatable = NdefFormatable.get(tag)
              if (ndefFormatable != null) {
                  try {
                      ndefFormatable.connect()
                      ndefFormatable.format(nfcMessage) // FIXME: try without AAR if message too big
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

  private fun createNdefMessage(wifiNetwork: WifiNetwork): NdefMessage {
      val payload = generateNdefPayload(wifiNetwork)

      val mimeRecord = NdefRecord(
              NdefRecord.TNF_MIME_MEDIA,
              NFC_TOKEN_MIME_TYPE.toByteArray(Charset.forName("US-ASCII")),
              ByteArray(0),
              payload)
      val aarRecord = NdefRecord.createApplicationRecord(PACKAGE_NAME)

      return NdefMessage(arrayOf(mimeRecord, aarRecord))
  }

    private fun generateNdefPayload(wifiNetwork: WifiNetwork): ByteArray {
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


        val bufferSize = 18 + ssidSize.toInt() + networkKeySize.toInt() // size of required credential attributes

        val buffer = ByteBuffer.allocate(bufferSize)
        buffer.putShort(CREDENTIAL_FIELD_ID)
        buffer.putShort((bufferSize - 4).toShort())

        buffer.putShort(SSID_FIELD_ID)
        buffer.putShort(ssidSize)
        buffer.put(ssid.toByteArray())

        buffer.putShort(AUTH_TYPE_FIELD_ID)
        buffer.putShort(2.toShort())
        buffer.putShort(authType)

        buffer.putShort(NETWORK_KEY_FIELD_ID)
        buffer.putShort(networkKeySize)
        buffer.put(networkKey.toByteArray())

        return buffer.array()
    }

    companion object {
        const val AUTH_TYPE_OPEN: Short = 0x0001
        const val AUTH_TYPE_WPA_PSK: Short = 0x0002
        const val AUTH_TYPE_WPA_EAP: Short = 0x0008
        const val AUTH_TYPE_WPA2_EAP: Short = 0x0010
        const val AUTH_TYPE_WPA2_PSK: Short = 0x0020
    }

}