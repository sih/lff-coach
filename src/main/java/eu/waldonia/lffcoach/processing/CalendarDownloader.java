package eu.waldonia.lffcoach.processing;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** @author sih */
class CalendarDownloader {

  private static final HttpClient httpClient =
      HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .connectTimeout(Duration.ofSeconds(10))
          .build();

  public byte[] download(String location) {
    HttpRequest request =
        HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(location))
            .setHeader("User-Agent", "LFF calendar download bot")
            .build();

    CompletableFuture<HttpResponse<byte[]>> response =
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());

    byte[] result = null;
    try {
      result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
