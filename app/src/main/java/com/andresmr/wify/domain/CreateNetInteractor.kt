package com.andresmr.wify.domain

import com.andresmr.wify.entity.Net

class CreateNetInteractor {

  fun execute(ssid: String, password: String): Net {
    return Net(ssid, password)
  }
}