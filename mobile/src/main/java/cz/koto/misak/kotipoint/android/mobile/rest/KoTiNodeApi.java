package cz.koto.misak.kotipoint.android.mobile.rest;


import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.entity.GalleryItem;
import cz.koto.misak.kotipoint.android.mobile.entity.KoTiEvent;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface KoTiNodeApi {


    @GET("/api/kotinode/event")
    Observable<List<KoTiEvent>> eventList(@Query("offset") int offset,@Query("limit") int limit, @Query("delay") int delay);

    @GET("/api/kotinode/gallery")
    Observable<List<GalleryItem>> galleryList(@Query("offset") int offset,@Query("limit") int limit);

}
