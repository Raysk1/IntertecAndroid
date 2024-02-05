package android.print

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.ParcelFileDescriptor
import android.print.PrintAttributes.Resolution
import android.print.PrintDocumentAdapter.LayoutResultCallback
import android.print.PrintDocumentAdapter.WriteResultCallback
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import java.io.File


/**
 * Converts HTML to PDF.
 *
 *
 * Can convert only one task at a time, any requests to do more conversions before
 * ending the current task are ignored.
 */
class PdfConverter private constructor() : Runnable {
    private var mContext: Context? = null
    private var mUrl: String? = null
    private var mPdfFile: File? = null
    private var mPdfPrintAttrs: PrintAttributes? = null
    private var mIsCurrentlyConverting = false
    private var mWebView: WebView? = null
    @SuppressLint("SetJavaScriptEnabled")
    override fun run() {
        mWebView = WebView(mContext!!)
        val settings: WebSettings = mWebView!!.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true

        mWebView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val documentAdapter = mWebView!!.createPrintDocumentAdapter(mPdfFile!!.name)
                documentAdapter.onLayout(
                    null,
                    pdfPrintAttrs, null, object : LayoutResultCallback() {}, null
                )
                documentAdapter.onWrite(
                    arrayOf<PageRange>(PageRange.ALL_PAGES),
                    outputFileDescriptor(), null, object : WriteResultCallback() {
                        override fun onWriteFinished(pages: Array<PageRange>) {
                            val intent = Intent(Intent.ACTION_VIEW)
                            val fileUri = FileProvider.getUriForFile(
                                mContext!!,
                                "com.raysk.intertec.fileprovider",
                                mPdfFile!!
                            )
                            intent.setDataAndType(fileUri, "application/pdf")
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                            startActivity(mContext!!, intent, null)
                            destroy()
                        }
                    })
            }
        }
        mWebView!!.loadUrl(mUrl!!)
    }

    var pdfPrintAttrs: PrintAttributes?
        get() = if (mPdfPrintAttrs != null) mPdfPrintAttrs else defaultPrintAttrs
        set(printAttrs) {
            mPdfPrintAttrs = printAttrs
        }

    fun convert(context: Context?, url: String?, file: File?) {
        requireNotNull(context) { "context can't be null" }
        requireNotNull(url) { "url can't be null" }
        requireNotNull(file) { "file can't be null" }
        if (mIsCurrentlyConverting) return
        mContext = context
        mUrl = url
        mPdfFile = file
        mIsCurrentlyConverting = true
        runOnUiThread(this)
    }

    private fun outputFileDescriptor(): ParcelFileDescriptor? {
        try {
            mPdfFile!!.createNewFile()
            return ParcelFileDescriptor.open(
                mPdfFile,
                ParcelFileDescriptor.MODE_TRUNCATE or ParcelFileDescriptor.MODE_READ_WRITE
            )
        } catch (e: Exception) {
            Log.d(TAG, "Failed to open ParcelFileDescriptor", e)
        }
        return null
    }

    private val defaultPrintAttrs: PrintAttributes
        get() = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.NA_GOVT_LETTER)
            .setResolution(Resolution("RESOLUTION_ID", "RESOLUTION_ID", 600, 600))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            .build()

    private fun runOnUiThread(runnable: Runnable) {
        val handler = Handler(mContext!!.mainLooper)
        handler.post(runnable)
    }

    private fun destroy() {
        mContext = null
        mUrl = null
        mPdfFile = null
        mPdfPrintAttrs = null
        mIsCurrentlyConverting = false
        mWebView = null
    }

    companion object {
        private const val TAG = "PdfConverter"
        private var sInstance: PdfConverter? = null

        @get:Synchronized
        val instance: PdfConverter?
            get() {
                if (sInstance == null) sInstance = PdfConverter()
                return sInstance
            }
    }
}