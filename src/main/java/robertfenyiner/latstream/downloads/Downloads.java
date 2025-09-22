package com.robertfenyiner.latstream.downloads;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Downloads extends ListCrudRepository<DownloadTask, String> {
}

