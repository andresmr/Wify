package com.andresmr.wify.ui.util

import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import com.andresmr.wify.entity.Net
import com.google.gson.Gson
import java.io.IOException

class NFCUtil {
  
  fun writeOnTag(net: Net, tag: Tag): Boolean {
    val nfcMessage = createNdefMessage(net)
    return writeMessageToTag(nfcMessage, tag)
  }

  private fun writeMessageToTag(nfcMessage: NdefMessage, tag: Tag?): Boolean {

    try {
      val nDefTag = Ndef.get(tag)

      nDefTag?.let {
        it.connect()
        if (it.maxSize < nfcMessage.toByteArray().size) {
          //Message to large to write to NFC tag
          return false
        }
        return if (it.isWritable) {
          it.writeNdefMessage(nfcMessage)
          it.close()
          //Message is written to tag
          true
        } else {
          //NFC tag is read-only
          false
        }
      }

      val nDefFormatableTag = NdefFormatable.get(tag)

      nDefFormatableTag?.let {
        return try {
          it.connect()
          it.format(nfcMessage)
          it.close()
          //The data is written to the tag
          true
        } catch (e: IOException) {
          //Failed to format tag
          false
        }
      }
      //NDEF is not supported
      return false

    } catch (e: Exception) {
      //Write operation has failed
    }
    return false
  }

  private fun createNdefMessage(net: Net): NdefMessage {
    val uri = Uri.parse("http://andresmr.com")
    val nfcUri = NdefRecord.createUri(uri)

    val json = Gson().toJson(net)
    val nfcMime = NdefRecord.createMime("text/plain", json.toString().toByteArray())

    return NdefMessage(arrayOf(nfcUri, nfcMime))
  }
}