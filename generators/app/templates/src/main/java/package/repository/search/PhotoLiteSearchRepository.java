//<--! package -->

//<--! import -->

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PhotoLite } entity.
 */
public interface PhotoLiteSearchRepository extends ElasticsearchRepository<PhotoLite, Long> {

    //  "{\"match\": {\"note\": \"?0\"}}]" +
    //  @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")

    @Query("{\"bool\": {" +
        "\"should\": [" +
            "{\"match\": {\"title\": \"?0\"}}," +
            "{\"match\": {\"metadata\": \"?0\"}}," +
            "{\"match\": {\"extractedText\": \"?0\"}}," +
            "{\"match\": {\"detectedObjects\": \"?0\"}}," +
            "{\"match\": {\"note\": \"?0\"}}]" +
        "}}")
    Page<PhotoLite> searchByIndex(String query, Pageable pageable);
}
