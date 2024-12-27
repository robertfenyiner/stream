package com.akshathsaipittala.streamspace.user.watchlist;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@Repository
public interface WatchList extends ListCrudRepository<Watch, Integer> {

    boolean existsByName(String name);

}

