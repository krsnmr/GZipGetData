import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.util.List;


public interface GzipDataApiInterface {


    @GET("api/v1/compress/test1")
    Call<String[]> compressTest1();


}
