import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;

import java.io.IOException;

/*
 Разжимание gzip
 */
public class UnzippingInterceptor implements Interceptor {

    private static final String TAG = "UnzippingInterceptor";
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return unzip(response);
    }

    // copied from okhttp3.internal.http.HttpEngine (because is private)
    private Response unzip(final Response response) throws IOException
    {
        if (response.body() == null)
        {
            return response;
        }
        //check if we have gzip response
        String contentEncoding = response.headers().get("Content-Encoding");
        System.out.println( contentEncoding);
        //this is used to decompress gzipped responses

        // Content-Encoding говорят указывать не надо,
        // тогда использовать только тех методов которые точно имеют сжатые данные
        if (true)//contentEncoding != null && contentEncoding.equals("gzip"))
        {
            //System.out.println( "gzip contentEncoding");
            Long contentLength = response.body().contentLength();
            GzipSource responseBody = new GzipSource(response.body().source());
            Headers strippedHeaders = response.headers().newBuilder().build();


            //String contentTxt = response.body().string();
            //System.out.println("!!!!! unzip 1: "+ contentTxt);

            String sContType = response.body().contentType().toString();
            System.out.println("!!!!! unzip2: "+contentLength);

            // разжимает но возвращает строку, а надо бъект
            RealResponseBody rBody = new RealResponseBody(sContType, contentLength, Okio.buffer(responseBody));

            return response.newBuilder().headers(strippedHeaders).body(rBody).build();
        }
        else
        {
            return response;
        }
    }
}
