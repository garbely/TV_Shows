package db;

import android.util.Log;
import android.os.AsyncTask;

import db.entity.Episode;
import db.entity.Show;


public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addShow(final AppDatabase db, final String name, final String description,
                                final int numberEpisodes) {
        Show show = new Show(name, description, numberEpisodes);
        db.showDao().insertNewShow(show);
    }

    private static void addEpisode(final AppDatabase db, final String name, final int number,
                                   final int lenght, final String showName) {
        Episode episode = new Episode(number, name, lenght, showName);
        db.episodeDao().insertNewEpisode(episode);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.showDao().deleteAll();

        addShow(db,
                "Prison Break",
                "Prison Break is an American television serial drama created by Paul Scheuring, that was broadcast on Fox for four seasons, with 81 episodes from August 29, 2005 to May 15, 2009, and a fifth season which aired from April 4, to May 30, 2017. The series revolves around two brothers, one of whom has been sentenced to death for a crime he did not commit, and the other who devises an elaborate plan to help his brother escape prison and clear his name. The series was produced by Adelstein-Parouse Productions, in association with Original Television and 20th Century Fox Television. Along with creator Paul Scheuring, the series is executive produced by Matt Olmstead, Kevin Hooks, Marty Adelstein, Dawn Parouse, Neal H. Moritz, and Brett Ratner who directed the pilot episode. The series' theme music, composed by Ramin Djawadi, was nominated for a Primetime Emmy Award in 2006.",
                90
        );

        addShow(db,
                "Scrubs",
                "Scrubs is an American medical comedy-drama television series created by Bill Lawrence that aired from October 2, 2001, to March 17, 2010, on NBC and later ABC. The series follows the lives of employees at the fictional Sacred Heart Hospital, which later becomes a Teaching Hospital. The title is a play on surgical scrubs and a term for a low-ranking person because at the beginning of the series, most of the main characters are medical interns. ",
                160
        );

        addShow(db,
                "Big Bang Theory",
                "The Big Bang Theory is an American television sitcom created by Chuck Lorre and Bill Prady, both of whom serve as executive producers on the series, along with Steven Molaro. All three also serve as head writers. The show premiered on CBS on September 24, 2007.[3] The twelfth and final season, which will run through 2018–19, premiered on September 24, 2018, consisting of 24 episodes.",
                281
        );

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addEpisode(db, "Pilot", 1, 30, "Prison Break");
        addEpisode(db, "Allen", 2, 32, "Prison Break");

        addEpisode(db, "My First Day", 1, 24, "Scrubs");
        addEpisode(db, "My Mentor", 2, 22, "Scrubs");

        addEpisode(db, "Pilot", 1, 18, "Big Bang Theory");
        addEpisode(db, "The Big Bran Hypothesis", 2, 19, "Big Bang Theory");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}