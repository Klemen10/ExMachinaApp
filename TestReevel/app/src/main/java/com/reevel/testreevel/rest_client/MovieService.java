package com.reevel.testreevel.rest_client;

import com.reevel.testreevel.entity.Certification;
import com.reevel.testreevel.entity.Movie;
import com.reevel.testreevel.entity.MovieRate;
import com.reevel.testreevel.entity.StatusCode;
import com.reevel.testreevel.entity.videos.Example;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Klemen on 17.8.2016.
 */

public interface MovieService {

    @GET("{id}")
    Observable<Movie> getMovie(@Path("id") String movieId, @Query("api_key") String api_key);

    @GET("{id}/release_dates")
    Observable<Certification> getMovieCertificationData(@Path("id") String movieId, @Query("api_key") String api_key);

    @GET("{id}/videos")
    Observable<Example> getMovieTrailerData(@Path("id") String movieId, @Query("api_key") String api_key);

    @POST("{id}/rating")
    Observable<StatusCode> rateMovie(@Path("id") String movieId, @Query("api_key") String api_key, @Query("guest_session_id") String guestSession, @Body MovieRate movieRate);
}
