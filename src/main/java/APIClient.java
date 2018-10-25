import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit ApiClientGZip() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)// пишет в лог
                .addInterceptor(new UnzippingInterceptor())// !!! перехватывает и разжимает !!!!!
                .build();

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://vdkservice.npobaltros.ru/WebApiUip/")
                //.baseUrl("http://localhost:58624")
                .addConverterFactory(GsonConverterFactory.create(gson))//gson
                .client(client)
                .build();

        return retrofit;
    }

}
