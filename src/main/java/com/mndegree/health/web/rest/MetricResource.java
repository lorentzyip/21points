package com.mndegree.health.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mndegree.health.domain.Metric;
import com.mndegree.health.repository.MetricRepository;
import com.mndegree.health.web.rest.util.HeaderUtil;
import com.mndegree.health.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Metric.
 */
@RestController
@RequestMapping("/api")
public class MetricResource {

    private final Logger log = LoggerFactory.getLogger(MetricResource.class);

    @Inject
    private MetricRepository metricRepository;

    /**
     * POST  /metrics -> Create a new metric.
     */
    @RequestMapping(value = "/metrics",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Metric> createMetric(@Valid @RequestBody Metric metric) throws URISyntaxException {
        log.debug("REST request to save Metric : {}", metric);
        if (metric.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new metric cannot already have an ID").body(null);
        }
        Metric result = metricRepository.save(metric);
        return ResponseEntity.created(new URI("/api/metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("metric", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /metrics -> Updates an existing metric.
     */
    @RequestMapping(value = "/metrics",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Metric> updateMetric(@Valid @RequestBody Metric metric) throws URISyntaxException {
        log.debug("REST request to update Metric : {}", metric);
        if (metric.getId() == null) {
            return createMetric(metric);
        }
        Metric result = metricRepository.save(metric);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("metric", metric.getId().toString()))
            .body(result);
    }

    /**
     * GET  /metrics -> get all the metrics.
     */
    @RequestMapping(value = "/metrics",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Metric>> getAllMetrics(Pageable pageable)
        throws URISyntaxException {
        Page<Metric> page = metricRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/metrics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /metrics/:id -> get the "id" metric.
     */
    @RequestMapping(value = "/metrics/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Metric> getMetric(@PathVariable Long id) {
        log.debug("REST request to get Metric : {}", id);
        return Optional.ofNullable(metricRepository.findOne(id))
            .map(metric -> new ResponseEntity<>(
                metric,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /metrics/:id -> delete the "id" metric.
     */
    @RequestMapping(value = "/metrics/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMetric(@PathVariable Long id) {
        log.debug("REST request to delete Metric : {}", id);
        metricRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("metric", id.toString())).build();
    }
}
