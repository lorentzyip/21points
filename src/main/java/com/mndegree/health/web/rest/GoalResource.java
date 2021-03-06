package com.mndegree.health.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mndegree.health.domain.Goal;
import com.mndegree.health.repository.GoalRepository;
import com.mndegree.health.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Goal.
 */
@RestController
@RequestMapping("/api")
public class GoalResource {

    private final Logger log = LoggerFactory.getLogger(GoalResource.class);

    @Inject
    private GoalRepository goalRepository;

    /**
     * POST  /goals -> Create a new goal.
     */
    @RequestMapping(value = "/goals",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Goal> createGoal(@Valid @RequestBody Goal goal) throws URISyntaxException {
        log.debug("REST request to save Goal : {}", goal);
        if (goal.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new goal cannot already have an ID").body(null);
        }
        Goal result = goalRepository.save(goal);
        return ResponseEntity.created(new URI("/api/goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("goal", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goals -> Updates an existing goal.
     */
    @RequestMapping(value = "/goals",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Goal> updateGoal(@Valid @RequestBody Goal goal) throws URISyntaxException {
        log.debug("REST request to update Goal : {}", goal);
        if (goal.getId() == null) {
            return createGoal(goal);
        }
        Goal result = goalRepository.save(goal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("goal", goal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goals -> get all the goals.
     */
    @RequestMapping(value = "/goals",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Goal> getAllGoals() {
        log.debug("REST request to get all Goals");
        return goalRepository.findAll();
    }

    /**
     * GET  /goals/:id -> get the "id" goal.
     */
    @RequestMapping(value = "/goals/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Goal> getGoal(@PathVariable Long id) {
        log.debug("REST request to get Goal : {}", id);
        return Optional.ofNullable(goalRepository.findOne(id))
            .map(goal -> new ResponseEntity<>(
                goal,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /goals/:id -> delete the "id" goal.
     */
    @RequestMapping(value = "/goals/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        log.debug("REST request to delete Goal : {}", id);
        goalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("goal", id.toString())).build();
    }
}
