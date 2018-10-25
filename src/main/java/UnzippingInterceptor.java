import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;

import java.io.IOException;

/*
 Разжимание gzip
 Взял частично отсюда: https://stackoverflow.com/questions/37333498
 /retrofit-how-to-parse-gzipd-response-without-content-encoding-gzip-header
 и отсюда
 https://stackoverflow.com/questions/51901333
 /okhttp-3-how-to-decompress-gzip-deflate-response-manually-using-java-android

 */
public class UnzippingInterceptor implements Interceptor {

    private static final String TAG = "UnzippingInterceptor";
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return unzip(response);
    }

    // copied from okhttp3.internal.http.HttpEngine (because is private)
    private Response unzip(final Response response) throws IOException {
        if (response.body() == null) {
            return response;
        }

        Long contentLength = response.body().contentLength();
        GzipSource responseBody = new GzipSource(response.body().source());
        Headers strippedHeaders = response.headers().newBuilder().build();


        String sContType = response.body().contentType().toString();

        // разжимает но возвращает строку, а надо бъект
        RealResponseBody rBody = new RealResponseBody(sContType, contentLength, Okio.buffer(responseBody));

        return response.newBuilder().headers(strippedHeaders).body(rBody).build();


    }
}
