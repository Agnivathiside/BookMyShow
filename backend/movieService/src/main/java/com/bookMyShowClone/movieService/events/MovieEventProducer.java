package com.bookMyShowClone.movieService.events;

import com.bookmyshow.movie.events.MovieEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.movie-events}")
    private String topic;

    public void publish(MovieEvent event) {
        kafkaTemplate.send(
                topic,
                event.getMovieId().toString(), // key
                event                          // Avro value
        );
    }
}
