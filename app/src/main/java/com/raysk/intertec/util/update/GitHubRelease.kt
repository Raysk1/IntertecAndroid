package com.raysk.intertec.util.update

import com.google.gson.annotations.SerializedName

data class GitHubRelease(
    @SerializedName("tag_name")
    val tagName: String?,
    @SerializedName("assets")
    val assets: List<GitHubAsset>
)

data class GitHubAsset(
    @SerializedName("browser_download_url")
    val url: String?
)
