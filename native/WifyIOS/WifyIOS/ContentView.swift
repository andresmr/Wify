//
//  ContentView.swift
//  WifyIOS
//
//  Created by Andres Miguel Rubio on 06/10/2019.
//  Copyright © 2019 andresmr. All rights reserved.
//

import SwiftUI
import SharedCode

struct ContentView: View {
    var body: some View {
        //Text("Hello World")
        Text(CommonKt.createApplicationScreenMessage())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
