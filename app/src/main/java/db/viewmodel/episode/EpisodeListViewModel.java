package db.viewmodel.episode;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import db.entity.Episode;
import db.repository.EpisodeRepository;

public class EpisodeListViewModel extends AndroidViewModel {

    private EpisodeRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Episode>> observableEpisodes;

    public EpisodeListViewModel(@NonNull Application application,
                                final String showName,
                                EpisodeRepository repository) {
        super(application);

        this.repository = repository;
        this.application = application;

        observableEpisodes = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableEpisodes.setValue(null);

        LiveData<List<Episode>> episodes = repository.getAllEpisodes(showName, application);

        // observe the changes of the entities from the database and forward them
        observableEpisodes.addSource(episodes, observableEpisodes::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final EpisodeRepository repository;
        private final String showName;

        public Factory(@NonNull Application application, String showName) {
            this.application = application;
            this.showName = showName;
            repository = EpisodeRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new EpisodeListViewModel(application, showName, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Episode>> getEpisodes() {
        return observableEpisodes;
    }
}
