import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("test 0");
        try {
            String[] data = GetData();
            for (int i = 0; i < data.length; i++) {
                System.out.println(data[i]);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /*
    Получить и разжать GZip данные
     */
    private static  String[] GetData() throws IOException
    {
        GzipDataApiInterface gzipApi;
        Retrofit rf = APIClient.ApiClientGZip();
        gzipApi = rf.create(GzipDataApiInterface.class);

        Call<String[]> call = gzipApi.compressTest1();
        Response<String[]> response = call.execute();
        String[] result = response.body();
        return  result;
    }
}
