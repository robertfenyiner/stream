package com.akshathsaipittala.streamspace.www;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

/**
 * UNIT3D Private Tracker API Client
 * 
 * Interfaces with UNIT3D-based private trackers using API key authentication.
 * Replace the base URL with your tracker's domain.
 */
@HttpExchange(
    url = "https://lat-team.com/api/", 
    accept = MediaType.APPLICATION_JSON_VALUE, 
    contentType = MediaType.APPLICATION_JSON_VALUE
)
public interface UNIT3DAPIClient {

    /**
     * Get all torrents with pagination
     */
    @GetExchange("torrents?page={page}&perPage={perPage}")
    UNIT3DTorrentsResponse getTorrents(@PathVariable int page, @PathVariable int perPage);

    /**
     * Search/filter torrents by various criteria (usando parámetro 'name' según UNIT3D original)
     */
    @GetExchange("torrents/filter?name={search}&page={page}&perPage={perPage}")
    UNIT3DTorrentsResponse searchTorrents(
        @PathVariable String search, 
        @PathVariable int page, 
        @PathVariable int perPage
    );

    /**
     * Advanced filter with multiple parameters (usando parámetros correctos según UNIT3D)
     */
    @GetExchange("torrents/filter?name={search}&categoryIds[]={categoryId}&typeIds[]={typeId}&resolutionIds[]={resolutionId}&page={page}&perPage={perPage}")
    UNIT3DTorrentsResponse filterTorrents(
        @PathVariable String search,
        @PathVariable Integer categoryId,
        @PathVariable Integer typeId, 
        @PathVariable Integer resolutionId,
        @PathVariable int page,
        @PathVariable int perPage
    );

    /**
     * Get torrent details by ID
     */
    @GetExchange("torrents/{id}")
    UNIT3DTorrentDetailsResponse getTorrentDetails(@PathVariable int id);

    /**
     * Get latest torrents (usando sortField y sortDirection según UNIT3D)
     */
    @GetExchange("torrents/filter?sortField=created_at&sortDirection=desc&page={page}&perPage={perPage}")
    UNIT3DTorrentsResponse getLatestTorrents(@PathVariable int page, @PathVariable int perPage);

    /**
     * Get most seeded torrents
     */
    @GetExchange("torrents/filter?sortField=seeders&sortDirection=desc&page={page}&perPage={perPage}")
    UNIT3DTorrentsResponse getMostSeededTorrents(@PathVariable int page, @PathVariable int perPage);

    /**
     * Get freeleech torrents (usando parámetros correctos)
     */
    @GetExchange("torrents/filter?free=100&page={page}&perPage={perPage}")
    UNIT3DTorrentsResponse getFreeleechTorrents(@PathVariable int page, @PathVariable int perPage);

    /**
     * Get torrents by category (películas)
     */
    @GetExchange("torrents/filter?categoryIds[]={categoryId}&page={page}&perPage={perPage}")
    UNIT3DTorrentsResponse getTorrentsByCategory(@PathVariable int categoryId, @PathVariable int page, @PathVariable int perPage);

    // Data Transfer Objects (DTOs) - Adaptados para LAT-Team
    
    record UNIT3DTorrentsResponse(
        List<UNIT3DTorrent> data,
        UNIT3DPagination meta
    ) {}

    record UNIT3DTorrentDetailsResponse(
        UNIT3DTorrentDetails data
    ) {}

    record UNIT3DTorrentData(
        List<UNIT3DTorrent> data
    ) {}

    record UNIT3DPagination(
        int current_page,
        int last_page,
        int per_page,
        int total,
        String first_page_url,
        String last_page_url,
        String next_page_url,
        String prev_page_url
    ) {}

    record UNIT3DTorrent(
        String type,
        String id,
        UNIT3DTorrentAttributes attributes
    ) {}

    record UNIT3DTorrentAttributes(
        String name,
        long size,
        String info_hash,
        int seeders,
        int leechers,
        int times_completed,
        String created_at,
        String bumped_at,
        String category,
        String type,
        String resolution,
        String release_year,
        String description,
        String mediainfo,
        boolean free,
        boolean sticky,
        boolean featured,
        UNIT3DTorrentMeta meta
    ) {}

    record UNIT3DTorrentMeta(
        String poster,
        String backdrop,
        String genres,
        String overview,
        String imdb_id,
        String tmdb_id,
        Double vote_average,
        String runtime
    ) {}

    record UNIT3DTorrentDetails(
        String type,
        String id,
        UNIT3DTorrentAttributes attributes
    ) {}

    record UNIT3DCategory(
        int id,
        String name,
        String icon
    ) {}

    record UNIT3DType(
        int id,
        String name
    ) {}

    record UNIT3DResolution(
        int id,
        String name
    ) {}

    record UNIT3DFile(
        String name,
        long size
    ) {}

    record UNIT3DMediaInfo(
        String video_codec,
        String audio_codec,
        String resolution,
        String runtime
    ) {}
}