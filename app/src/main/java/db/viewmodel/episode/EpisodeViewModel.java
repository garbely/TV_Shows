package db.viewmodel.episode;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import db.BaseApp;
import db.entity.Episode;
import db.repository.EpisodeRepository;
import db.util.OnAsyncEventListener;

public class EpisodeViewModel extends AndroidViewModel {

    private Application application;
    private EpisodeRepository repository;
    private final MediatorLiveData<Episode> observableEpisode;

    public EpisodeViewModel(@NonNull Application application,
                            final int idEpisode, EpisodeRepository repository) {
        super(application);

        this.application = application;
        this.repository = repository;

        observableEpisode = new MediatorLiveData<>();
        observableEpisode.setValue(null);

        LiveData<Episode> show = repository.getEpisode(idEpisode, application);

        observableEpisode.addSource(show, observableEpisode::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final int idEpisode;
        private final EpisodeRepository repository;

        public Factory(@NonNull Application application, int idEpisode) {
            this.application = application;
            this.idEpisode = idEpisode;
            repository = ((BaseApp) application).getEpisodeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new EpisodeViewModel(application, idEpisode, repository);
        }
    }

    public LiveData<Episode> getEpisode() {
        return observableEpisode;
    }

    public void createEpisode(Episode episode, OnAsyncEventListener callback) {
        repository.insert(episode, callback, application);
    }

    public void updateEpisode(Episode episode, OnAsyncEventListener callback) {
        repository.update(episode, callback, application);
    }

    public void deleteEpisode(Episode episode, OnAsyncEventListener callback) {
        repository.delete(episode, callback, application);
    }
}
