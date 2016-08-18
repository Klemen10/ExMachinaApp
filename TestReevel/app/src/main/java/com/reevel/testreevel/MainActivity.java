package com.reevel.testreevel;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.reevel.testreevel.entity.Certification;
import com.reevel.testreevel.entity.Genre;
import com.reevel.testreevel.entity.Movie;
import com.reevel.testreevel.entity.MovieRate;
import com.reevel.testreevel.entity.Result;
import com.reevel.testreevel.entity.StatusCode;
import com.reevel.testreevel.entity.videos.Example;
import com.reevel.testreevel.rest_client.MovieServiceAdapter;
import com.reevel.testreevel.rest_client.PicassoClient;
import com.reevel.testreevel.util.Util;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private MovieServiceAdapter mMovieService;


    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.movie_image)
    ImageView movieImage;
    @BindView(R.id.play_circle_shape)
    ImageView imagePlayCircle;
    @BindView(R.id.image_play_button)
    ImageView imagePlayButton;
    @BindView(R.id.play_button_frame)
    FrameLayout playButtonFrameLayout;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.text_votes)
    TextView movieVotes;
    @BindView(R.id.text_imdb_rating)
    TextView imdbRating;
    @BindView(R.id.text_revenu_value)
    TextView revenueValue;
    @BindView(R.id.text_release_date_value)
    TextView releaseDate;
    @BindView(R.id.text_run_time_value)
    TextView runTime;
    @BindView(R.id.text_rating_value)
    TextView movieRating;
    @BindView(R.id.text_storyline_value)
    TextView storyLine;
    @BindView(R.id.text_movie_description)
    TextView movieTagline;
    @BindView(R.id.genre_list)
    LinearLayout genreListLinearLayout;
    @BindView(R.id.rating_bar)
    RatingBar movieRatingBar;

    private int oldY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        oldY = 0;

        CustomAnimations.startAnimationsOnPlayButtonCircle(imagePlayCircle);

        setOnScrollListener();

        movieRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    MovieRate movieRate = new MovieRate(v);
                    rateMovie(movieRate);
                }
            }
        });

        if (isNetworkAvailable()) {
            runMovieService();
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setOnScrollListener() {

        final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new
                ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        int currentYPosition = scrollView.getScrollY();
                        CustomAnimations.startAnimationsOnImageView(oldY, currentYPosition, movieImage);
                        CustomAnimations.startAnimationsOnPlayButtonView(oldY, currentYPosition, imagePlayCircle, playButtonFrameLayout);
                        oldY = currentYPosition;
                    }
                };

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            private ViewTreeObserver observer;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (observer == null) {
                    observer = scrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                } else if (!observer.isAlive()) {
                    observer.removeOnScrollChangedListener(onScrollChangedListener);
                    observer = scrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void rateMovie(MovieRate rate) {

        mMovieService = new MovieServiceAdapter();
        mMovieService.rateMovie(rate, new Observer<StatusCode>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), "Rating unsuccessful." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(StatusCode statusCode) {
                Toast.makeText(getApplicationContext(), statusCode.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void runMovieService() {
        mMovieService = new MovieServiceAdapter();

        mMovieService.getMovieData(new Action1<Movie>() {
            @Override
            public void call(Movie movie) {
                setMovieData(movie);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Can not access to movie data!", Toast.LENGTH_SHORT).show();
            }
        });

        mMovieService.getMovieCertification(new Action1<Certification>() {
            @Override
            public void call(Certification certification) {
                setCertificationData(certification);
            }
        });

        mMovieService.getMovieTrailersData(new Action1<Example>() {
            @Override
            public void call(Example trailersExample) {

                if (trailersExample.getResults().size() > 0) {
                    String trailerKey = trailersExample.getResults().get(0).getKey();
                    String movieUrl = "http://www.youtube.com/embed/" + trailerKey + "?rel=0&autoplay=1";
                    setPlayOnClickListener(movieUrl);
                }
            }
        });
    }

    private void setPlayOnClickListener(final String movieUrl) {

        imagePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieUrl));
                startActivity(browserIntent);
            }
        });
    }

    private void setMovieData(Movie movie) {

        movieTitle.setText(movie.getTitle());
        String voteCount = "(" + movie.getVoteCount() + " votes)";
        movieVotes.setText(voteCount);
        imdbRating.setText(String.valueOf(movie.getVoteAverage()));
        String revenueString = "$" + NumberFormat.getNumberInstance(Locale.US).format(movie.getRevenue());
        revenueValue.setText(revenueString);
        String releaseDateString = Util.convertDate(movie.getReleaseDate());
        releaseDate.setText(releaseDateString);
        runTime.setText(Util.convertRuntime(movie.getRuntime()));
        storyLine.setText(movie.getOverview());
        movieTagline.setText(movie.getTagline());
        addGenresToList(movie.getGenres(), genreListLinearLayout);

        PicassoClient picassoClient = new PicassoClient(getApplicationContext());
        picassoClient.loadImageToView(movie.getPosterPath(), movieImage);
    }

    private void setCertificationData(Certification certification) {

        for (Result current : certification.getResults()) {
            if (current.getIso31661().equals("US")) {
                movieRating.setText(current.getReleaseDates().get(0).getCertification());
                return;
            }
        }
    }

    private void addGenresToList(List<Genre> genres, LinearLayout parrentView) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int dpValue = 16; // margin in dips
        float d = getApplicationContext().getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d); // margin in pixels
        layoutParams.setMargins(margin, 0, 0, 0);

        for (Genre currentGenre : genres) {
            String genreName = currentGenre.getName();
            TextView textGenre = new TextView(getApplicationContext());
            textGenre.setText(genreName);
            textGenre.setLayoutParams(layoutParams);
            textGenre.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_text_view2, null));
            textGenre.setTextAppearance(this, R.style.TextCaptionWhite);
            if (Build.VERSION.SDK_INT < 23) {
                textGenre.setTextAppearance(this, R.style.TextCaptionWhite);
            } else {
                textGenre.setTextAppearance(R.style.TextCaptionWhite);
            }
            parrentView.addView(textGenre);
            parrentView.invalidate();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
