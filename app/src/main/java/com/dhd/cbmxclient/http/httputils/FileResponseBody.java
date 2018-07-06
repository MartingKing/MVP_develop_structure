package com.dhd.cbmxclient.http.httputils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by dhd on 2017/6/15.
 */

public class FileResponseBody extends ResponseBody {

    private Response originalResponse;//原结果
    private HttpOnNextListener listener;

    public FileResponseBody(Response originalResponse, HttpOnNextListener listener) {
        this.originalResponse = originalResponse;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            long bytesReaded = 0;

            //返回读取到的长度
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                int progress = (int) Math.round(bytesReaded / (double) contentLength() * 100);
                listener.onProgress(progress);
                return bytesRead;
            }
        });
    }
}
