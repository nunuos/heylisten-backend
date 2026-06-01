package com.heylisten.backend;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
@CrossOrigin(origins = "*")
public class SurveyController {

    private final SurveyRepository surveyRepository;

    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @PostMapping
    public Survey saveSurvey(@RequestBody Survey survey) {
        System.out.println("Encuesta recibida de: " + survey.getUsername());
        return surveyRepository.save(survey);
    }
}