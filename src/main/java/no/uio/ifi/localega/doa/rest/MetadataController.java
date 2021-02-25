package no.uio.ifi.localega.doa.rest;

import lombok.extern.slf4j.Slf4j;
import no.uio.ifi.localega.doa.aspects.AAIAspect;
import no.uio.ifi.localega.doa.services.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.Base64;

/**
 * REST controller incorporating metadata-related endpoints.
 */
@Slf4j
@ConditionalOnProperty("rest.enabled")
@RequestMapping("/metadata")
@RestController
public class MetadataController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private MetadataService metadataService;

    /**
     * Lists datasets.
     *
     * @return List of datasets.
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/datasets")
    public ResponseEntity<?> datasets() {
        log.info("User has permissions to list datasets");
        Set<String> datasetIds = (Set<String>) request.getAttribute(AAIAspect.DATASETS);
        return ResponseEntity.ok(metadataService.datasets(datasetIds));
    }

    /**
     * Lists files in the dataset.
     *
     * @param datasetId Dataset ID.
     * @return List of files in the dataset.
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/datasets/{datasetId}/files")
    public ResponseEntity<?> files(@PathVariable(value = "datasetId") String datasetId) {
        Set<String> datasetIds = (Set<String>) request.getAttribute(AAIAspect.DATASETS);
        String datasetIdParsed = "";
        try {
            // attempt to base64 decode the path param
            datasetIdParsed = new String(Base64.getDecoder().decode(datasetId.getBytes()));
        } catch (Exception e) {
            // path param was not in base64 format
            datasetIdParsed = datasetId;
        }
        if (!datasetIds.contains(datasetIdParsed)) {
            log.info("User doesn't have permissions to list files in the requested dataset: {}", datasetIdParsed);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        log.info("User has permissions to list files in the requested dataset: {}", datasetIdParsed);
        return ResponseEntity.ok(metadataService.files(datasetIdParsed));
    }

}
