package com.robertfenyiner.latstream.www;

import java.util.List;

/**
 * LATTeamAPIClient: Adaptador para lógica de películas usando la API de LAT-Team (UNIT3D)
 * Puede delegar a UNIT3DAPIClient o usarse como interfaz directa.
 */
public interface LATTeamAPIClient {
    // Métodos principales para películas (pueden delegar a UNIT3DAPIClient)
    List<UNIT3DAPIClient.UNIT3DTorrent> getLatestMovies(int page, int perPage);
    List<UNIT3DAPIClient.UNIT3DTorrent> getMostSeededMovies(int page, int perPage);
    List<UNIT3DAPIClient.UNIT3DTorrent> searchMovies(String search, int page, int perPage);
    UNIT3DAPIClient.UNIT3DTorrentDetails getMovieDetails(int id);
}
