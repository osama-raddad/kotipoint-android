package cz.koto.misak.kotipoint.android.mobile.rest;


import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.model.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.model.KoTiEvent;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface KoTiNodeApi {


    @GET("/api/kotinode/event")
    Observable<List<KoTiEvent>> eventList(@Query("offset") int offset, @Query("limit") int limit, @Query("delay") int delay);

    @GET("/api/kotinode/gallery")
    Observable<List<GalleryItem>> galleryList(@Query("offset") int offset, @Query("limit") int limit, @Query("debug") boolean debug);

}
