package com.reevel.testreevel.rest_client;

import com.reevel.testreevel.entity.Certification;
import com.reevel.testreevel.entity.Movie;
import com.reevel.testreevel.entity.MovieRate;
import com.reevel.testreevel.entity.StatusCode;
import com.reevel.testreevel.entity.videos.Example;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Klemen on 17.8.2016.
 */

public class MovieServiceAdapter implements UrlConstants{

    private MovieService movieService;

    public MovieServiceAdapter(){

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MOVIE_SERVICE_ENDPOINT)
        .build();

        movieService = retrofit.create(MovieService.class);
    }


    public void getMovieData(Action1<Movie> movieObserverOnNext, Action1<Throwable> movieObserverOnError){

        Observable<Movie> movieObservable = movieService.getMovie(EX_MACHINA_ID, API_KEY);
        movieObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieObserverOnNext, movieObserverOnError);
    }

    public void getMovieCertification(Action1<Certification> certObserver){

        Observable<Certification> movieObservable = movieService.getMovieCertificationData(EX_MACHINA_ID, API_KEY);
        movieObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(certObserver);
    }

    public void getMovieTrailersData(Action1<Example> certObserver){

        Observable<Example> movieObservable = movieService.getMovieTrailerData(EX_MACHINA_ID, API_KEY);
        movieObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(certObserver);
    }

    public void rateMovie(MovieRate movieRate, Observer<StatusCode> certObserver){

        Observable<StatusCode> movieObservable = movieService.rateMovie(EX_MACHINA_ID, API_KEY, GUEST_SESSION_ID, movieRate);

        movieObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(certObserver);
    }

}
