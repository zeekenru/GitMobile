package com.kovapss.gitmobile.gists.create

import io.mironov.smuggler.AutoParcelable


data class GistFile(val filename : String, val content : String) : AutoParcelable

